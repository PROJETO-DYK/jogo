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
        
        perguntas = Pergunta.buscarPerguntas(conector);
        
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
                        jogador = jogador.buscarJogador(jogador,conector);
                                                
                        opcaoValida = validarSeJogadorCompleto(jogador,i);
                    }
                    case 2 ->
                    {
                        jogador = jogador.criarUsuario(args,conector);
                        
                        opcaoValida = validarSeJogadorCompleto(jogador,i);
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
    
    
    public static boolean validarSeJogadorCompleto(Usuario jogador, int numeroJogador)
    {
        if (jogador.getCodigoUsuario()>0 && jogador.getPersonagem() != null)
        {
            jogador.setNumeroJogador(numeroJogador);
            jogadores.add(jogador);
            System.out.println("Usuario logado com sucesso!");
            return true;
        }
        else
        {
            System.out.println("Não foi possível recuperar o seu Personagem");//TODO: Logica para criar personagem
            return true;
        }
    }
    

    
    //public static Usuario criarAvatar() throws SQLException
    //{
        // buscar avatar disponiveis
        // pedir para o usuario selecionar o avatar ou criar um novo
        // se for criar o usuario tem que nomear e as habilidades sao adiquiridas de forma aleatória
        // se ele selecionar retorno com o avatar escolhido e jogador criado
    //}            
}
