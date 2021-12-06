package dyk2;

import Repository.JDBCConector;
import Util.Alternativa;
import Util.Menu;
import Util.Pergunta;
import java.util.Scanner;
import Util.Usuario;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

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
                    esperar(5);
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
//        System.out.println("Lembrando que o DYK é um jogo para jogar em duplas!\n");
//        
//        for (int i = 1; i <= QUANTIDADE_JOGADORES; i++){
//            
//            System.out.println("Jogador n° " + i + ", seja bem vindo!!!\n");
//            
//            boolean opcaoValida = false;
//            do 
//            {
//                Usuario jogador = new Usuario();
//                menu.MenuInicial();
//                System.out.print("Digite a opção desejada: ");
//                int opcaoDesejada = in.nextInt();
//                
//                switch (opcaoDesejada){
//                    case 1 ->//Usuario cadastrado
//                    {
//                        jogador = Usuario.buscarJogador(jogador,conector);
//                                                
//                        opcaoValida = validarSeJogadorCompleto(jogador,i);
//                        //opcaoValida=true;
//                    }
//                    case 2 ->//Criar Usuario
//                    {
//                        Personagem personagemEscolhido = new Personagem();
//                        
//                        personagemEscolhido = personagemEscolhido.escolherPersonagem(conector);
//                        
//                        jogador = Usuario.criarUsuario(args, personagemEscolhido, conector);
//                        
//                        opcaoValida = validarSeJogadorCompleto(jogador,i);
//                    }
//                    case 3 ->
//                    {
//                        System.out.println("chamar metodo para ranking");//TODO: Logica
//                        opcaoValida = true;
//                    }
//                    case 0 -> 
//                    {
//                        menu.MenuEncerramento();
//                        conector.encerrarConexao();
//                        System.exit(0);
//                    }
//                    default -> 
//                    {
//                        System.out.println("\n Opção inválida digite novamente!");
//                    }
//                }
//            }
//            while (!opcaoValida);
//            
//        }
        
       perguntas = Pergunta.buscarPerguntas(conector);
        
       int jogadorEscolhido = usuario.escolherUsuario(QUANTIDADE_JOGADORES);
       
       
       int rodada = 1;
       while(!perguntas.isEmpty())
       {
           if (rodada == 1)
           {
               //System.out.println("Jogador " + jogadores.get(jogadorEscolhido).getNomeUsuario()+ " você irá iniciar o jogo! E logo apos sera o proximo jogador");
           }
           else 
           {
               //logica para buscar o outro jogador
               jogadorEscolhido = trocarJogador(jogadorEscolhido);
           }
           
           int perguntaEscolhida = escolherPegunta(perguntas.size());
           
           int respostaEscolhidaJogador = realizarPergunta(rodada,perguntaEscolhida,perguntas.get(perguntaEscolhida))-1;
           
           if (respostaEscolhidaJogador>-1)
           {
               if (perguntas.get(perguntaEscolhida).getAlternativas().get(respostaEscolhidaJogador).isCorreta())
                {
                    menu.MenuRespostaCerta();
                    System.out.println("VAMOS PARA A RODADA " + (rodada + 1) + "\n");
                    esperar(10);
                    clearConsole();
                   
                }
                else
                {
                    menu.MenuRespostaErrada();
                    System.out.println("VAMOS PARA A RODADA " + (rodada + 1) + "\n");
                    esperar(10);
                    clearConsole();
                  
                }
           }else
           {
               //logica para sair do jogo
               //perguntar se o outro jogado deseja sair tbm 
                  //caso sim encerra o jogo sem danos a nenhum jogador
                  //caso nao o jogador que esta respondendo perde ponto
               //
           }
           rodada++;
           perguntas.remove(perguntaEscolhida);
       }
        System.out.println("Fim das perguntas");
    }
    
    public static int trocarJogador(int jogadorAtual)
    {
        if (jogadorAtual == 1)
            jogadorAtual = 2;
        else
            jogadorAtual = 1;
        
        return jogadorAtual;
    }
    
    public static int realizarPergunta(int rodada, int perguntaEscolhida, Pergunta pergunta)
    {
        int resposta=-1;
        do
        {   
            if(!(resposta > 0 && resposta <= pergunta.getAlternativas().size()))
            {
                System.out.println("Não existe a alternativa escolhida, favor escolha uma das alternativas abaixo ou 0 para sair.\n\n");
            }
            
            System.out.println("RODADA " + (rodada));
            System.out.println("Pergunta n° " + (rodada)+ " " + perguntas.get(perguntaEscolhida).getPergunta());

            int a = 1;
            for (Alternativa alternativa : pergunta.getAlternativas())
            {                             //O JOGO ESTA SEMPRE IMPRIMINDO AS ULTIMAS 4 ALTERNATIVAS DO BANCO DE DADOS
                System.out.println(a + " - " +alternativa.getResposta());
                 a++;
            }

            System.out.print("Digite a sua resposta ou 0 para sair: ");
            resposta = in.nextInt();
            
        }
        while(!(resposta >= 0 && resposta <= pergunta.getAlternativas().size()));//a resposta tem que ser de 1 a 4 
        return resposta; 
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
    public static int escolherPegunta(int quantidadeDeJogadores)
    {
       Random rand = new Random(); 
       int perguntaEscolhida = rand.nextInt(quantidadeDeJogadores);
       return perguntaEscolhida;
       
    }
       
    public static void esperar(int tempoEspera) throws InterruptedException
    {     
        
        Thread.sleep(tempoEspera * 1000);
                
    }
    public final static void clearConsole() {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(10);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_L);
        } catch (AWTException ex) {
        }
    }
}