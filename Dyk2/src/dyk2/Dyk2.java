package dyk2;

import Repository.JDBCConector;
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
                case 1 -> 
                {
                    clearConsole();
                    iniciaJogo(args);
                }
                case 2 -> 
                {
                
                    menu.MenuDesenvolvedores();
                    new Thread().sleep(5000);
                    clearConsole();
                    menu.MenuInicializacao();
                
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
    }
    
    
    
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
                        jogador = Usuario.buscarJogador(jogador,conector);
                                                
                        opcaoValida = validarSeJogadorCompleto(jogador,i);
                    }
                    case 2 ->//Criar Usuario
                    {
                        Personagem personagemEscolhido = new Personagem();
                        
                        personagemEscolhido = personagemEscolhido.escolherPersonagem(conector);
                        
                        jogador = Usuario.criarUsuario(args, personagemEscolhido, conector);
                        
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
        
        perguntas = Pergunta.buscarPerguntas(conector);
        
       int jogadorEscolhido = usuario.escolherUsuario(QUANTIDADE_JOGADORES);
       
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
    
    public static void clearConsole() throws InterruptedException
    {
        for (int i = 0; i < 100; ++i)
            System.out.println();    
    }
      
}
