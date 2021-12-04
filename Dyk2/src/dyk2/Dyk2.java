package dyk2;

import Repository.JDBCConector;
import Util.Alternativa;
import Util.Menu;
import Util.Pergunta;
import Util.Personagem;
import java.util.Scanner;
import Util.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;

public class Dyk2 
{
    //constantes
    public static final int SIM = 1;
    public static final int NAO = 2;
    static final int QUANTIDADE_JOGADORES = 2;
    
    //instancias
    static Scanner in = new Scanner(System.in);
    static Menu menu = new Menu();
    static JDBCConector conector = new JDBCConector();
    static Usuario usuario = new Usuario();
    static ArrayList<Usuario> jogadores = new ArrayList<Usuario>();
    static ArrayList<Pergunta> perguntas = new ArrayList<Pergunta>();
    static ArrayList<Alternativa> alternativas = new ArrayList<Alternativa>();
    
    public static void main(String[] args) throws SQLException, InterruptedException
    {
        conector.inciarConexao(); // ABRE CONEXAO COM BANCO
                
        //interação com o usuário
        System.out.println("Seja Bem Vindo(a) ao DYK, seu jogo de Perguntas e respostas!!!\n");
        
        menu.MenuInicializacao();
                
        int opcaoInicial = 0;
        do 
        {
            System.out.print("Digite a opção desejada: ");
            opcaoInicial = in.nextInt();
            
            switch (opcaoInicial)
            {
                case 1 -> iniciaJogo(args);
                case 2 -> 
                {
                
                    menu.MenuDesenvolvedores();         //Menu de desenvolvedores
                    new Thread().sleep(5000);           //Tempo de espera antes de puxar o Menu Inicialização
                    for (int i = 0; i < 100; ++i)       //Gambiarra para limpar console
                    System.out.println();               //Gambiarra para limpar console
                    menu.MenuInicializacao();           //Puxa menu de inicialização
                
                }
                case 3 -> 
                {
                    menu.MenuRegras();
                    System.out.println("\n");
                    menu.MenuInicializacao();
                }
                case 4 ->
                    {
                        System.out.println("cadastrar perguntas");//TODO: Logica
                    }
                case 0 -> 
                {
                    menu.MenuEncerramento();
                    conector.encerrarConexao();
                    System.exit(0);
                }
                default -> {
                    System.out.println("\n Opção inválida digite novamente!");
                    menu.MenuInicializacao();
                }
            }
        }
        while (opcaoInicial >1 || opcaoInicial<0);
        if (opcaoInicial == 1)
            System.out.println("Fim de jogo!");
        
        conector.encerrarConexao();
    }//falta preenche o item 2 do menu
    
    
    
    public static void iniciaJogo(String[] args) throws SQLException, InterruptedException
    {
        System.out.println("Lembrando que o DYK é um jogo para jogar em duplas!\n");
        
        for (int i = 1; i <= QUANTIDADE_JOGADORES; i++){
            
            System.out.println("Jogador n° " + i + ", seja bem vindo!!!\n");
            
            boolean opcaoValida = false;
            do 
            {
                Usuario jogador = new Usuario();
                menu.MenuInicial();
                System.out.print("Digite a opção desejada: ");
                int opcaoDesejada = in.nextInt();
                
                switch (opcaoDesejada){
                    case 1 ->//Usuario cadastrado
                    {
                        jogador = buscarJogador(jogador);
                                                
                        if (jogador.getCodigoUsuario()>0 && jogador.getPersonagem() != null)
                        {
                            jogador.setNumeroJogador(i);
                            jogadores.add(jogador);
                            System.out.println("Usuario logado com sucesso!");
                            opcaoValida = true;
                        }
                        else
                        {
                            System.out.println("Não foi possível recuperar o seu Personagem");
                            //Criar fluxo para cadastro de Personagem
                        }
                    }
                    case 2 ->
                    {
                        jogador = criarUsuario(args);
                        
                        if (jogador.getCodigoUsuario()>0 && jogador.getPersonagem() != null)
                        {
                            jogador.setNumeroJogador(i);
                            jogadores.add(jogador);
                            System.out.println("Usuario logado com sucesso!");
                            opcaoValida = true;
                        }
                        else
                        {
                            System.out.println("Não foi possível recuperar o seu Personagem");
                            //Criar fluxo para cadastro de Personagem
                        }
                        
                        opcaoValida = true;
                    }
                    case 3 ->
                    {
                        System.out.println("chamar metodo para ranking");//TODO: Logica
                        opcaoValida = true;
                    }
                    case 0 -> 
                    {
                        menu.MenuEncerramento();
                        conector.encerrarConexao();
                        System.exit(0);
                    }
                    default -> 
                    {
                        System.out.println("\n Opção inválida digite novamente!");
                    }
                }
            }
            while (!opcaoValida);
            
        }
        
        //inicia o jogo(passando a lista de usuarios) Gladson
        buscarPerguntasEAlternativas();
       
       //INICIO RANDOM//
       int jogadorEscolhido = usuario.escolherUsuario(QUANTIDADE_JOGADORES);
       //FIM RANDOM//
       
       //INICIO PERGUNTAS//
      //CARREGAR PERGUNTAS E RESPOSTAS DO BANCO DE DADOS EM LOOPING PARA OS JOGADORES RESPONDEREM//
         //JOGO FAZ PERGUNTA
         //JOGO MOSTRA ALTERNATIVAS
         //JOGADOR SORTEADO RESPONDE                
         //MOSTRA PONTUAÇÃO E VIDA 
         //JOGO FAZ PERGUNTA
         //JOGO MOSTRA ALTERNATIVAS           
         //PROXIMO JOGADOR RESPONDE    ->    // SE JOGADOR SORTEADO FOR IGUAL A 1, PROXIMO JOGADOR E IGUAL A JOGADOR SORTEADO MAIS 1; 
                                            //SE JOGADOR SORTEADO FOR IGUAL A 2, PROXIMO JOGADOR SORTEADO MENOS 1 
         //MOSTRA PONTUAÇÃO E VIDA
      //FIM PERGUNTAS//
      
    }
    
    
    public static int validacaoSimNao()
    {
        int validacao = 0;
        do
        {
            System.out.println("Digite 1-SIM 2-NÃO: ");
            validacao = in.nextInt();
        }
        while (validacao > 2 || validacao < 1  );
                
        return validacao; 
    }
    
    public static Usuario buscarJogador(Usuario jogador) throws SQLException
    {
        if (jogador.getNomeUsuario() == null)
        {
            System.out.print("Digite o seu e-mail: ");
            String email = in.next();
            usuario.setEmail(email);

            System.out.print("Digite sua senha: ");
            String senha = in.next();
            usuario.setSenha(senha);

            jogador = conector.buscarUsuario(email,senha,1);
        
        }
        Personagem penrsonagem = conector.buscarAvatarDoUsuario(jogador);
        
        jogador.setPersonagem(penrsonagem);
        
        return jogador;
    }
    
    //public static Usuario criarAvatar() throws SQLException
    {
        // buscar avatar disponiveis
        // pedir para o usuario selecionar o avatar ou criar um novo
        // se for criar o usuario tem que nomear e as habilidades sao adiquiridas de forma aleatória
        // se ele selecionar retorno com o avatar escolhido e jogador criado
    }
    
    public static Usuario criarUsuario(String[] args) throws SQLException, InterruptedException
    {
        Usuario usuario;
        
        System.out.println("Bem vindo a tela de cadastro de Usuario!\n");
        
        System.out.print("Para começar, digite seu e-mail: ");
        
        String email = in.next();
        
        usuario = conector.buscarUsuario(email,"",2);
        
        if(usuario.getCodigoUsuario()<0)
            main(args); //volta ao menu inicial por opção do usuario
        else if (usuario.getCodigoUsuario()>0)
        {
            return buscarJogador(usuario);
        }
        else
        {
            String senha, nome, sobrenome,apelido;
            
            System.out.println("Digite a senha: ");
            senha = in.next();
            usuario.setSenha(senha);
            
            System.out.println("Digite seu nome:");
            nome = in.next();
            usuario.setNomeUsuario(nome);
            
            System.out.println("Digite seu sobrenome:");
            sobrenome = in.next();
            usuario.setSobrenomeUsuario(sobrenome);
            
            System.out.println("Digite seu apelido:");
            apelido = in.next();
            usuario.setApelido(apelido);
            
            conector.inserirUsuario(usuario);;
        }
        return usuario;
    }
    
    static public void buscarPerguntasEAlternativas() throws SQLException
    {
        
        ArrayList<Alternativa> generica = new ArrayList<Alternativa>();
        
        alternativas = conector.buscarAlternativas();
        
        perguntas = conector.buscarPerguntas();
        
        for (Pergunta pergunta : perguntas)
        {
            
            for(Alternativa alternativa : alternativas)
            {
                
                if (alternativa.getCodigoPergunta() == pergunta.getCodigoPergunta())
                {
                    generica.add(alternativa);
                }
            }
            pergunta.setAlternativas(generica);
            generica.clear();
        }

    }
            
}
