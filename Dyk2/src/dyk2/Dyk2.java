package dyk2;

import Repository.JDBCConector;
import Util.Jogo;
import Util.Menu;
import Util.Personagem;
import Util.Ranking;
import java.util.Scanner;
import Util.Usuario;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class Dyk2
{

    //constantes
    public static final int SIM = 1;
    public static final int NAO = 2;
    public static final int PONTUACAO_DEBITADA = 10;
    public static final int PONTUACAO_CREDITADA = 10;
    static final int QUANTIDADE_JOGADORES = 2;

    //instancias
    static Scanner in = new Scanner(System.in);
    static JDBCConector conector = new JDBCConector();
    static Menu menu = new Menu();
    static Jogo jogo = new Jogo();

    public static void main(String[] args) throws SQLException, InterruptedException
    {
        conector.inciarConexao(); // ABRE CONEXAO COM BANCO

        //interação com o usuário
        System.out.println("Seja Bem Vindo(a) ao DYK, seu jogo de Perguntas e respostas!!!\n");

        

        int opcaoInicial = 0;
        do
        {
            menu.MenuInicializacao();
            System.out.print("Digite a opção desejada: ");
            opcaoInicial = in.nextInt();
            clearConsole();

            switch (opcaoInicial)
            {
                case 1 ->
                {
                    iniciaJogo(args);
                }
                case 2 ->
                {

                    menu.MenuDesenvolvedores();
                    esperar(5);
                    clearConsole();

                }
                case 3 ->
                {
                    menu.MenuRegras();
                    esperar(10);
                    clearConsole();
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
                default ->
                {
                    System.out.println("\n Opção inválida digite novamente!");
                    menu.MenuInicializacao();
                }
            }
        } while (opcaoInicial > 1 || opcaoInicial < 0);
        if (opcaoInicial == 1)
        {
            System.out.println("Fim de jogo!");
        }

        conector.encerrarConexao();
    }

    public static void iniciaJogo(String[] args) throws SQLException, InterruptedException
    {
        ArrayList<Usuario> jogadores = new ArrayList<Usuario>();
        
        System.out.println("Lembrando que o DYK é um jogo para jogar em duplas!\n");

        for (int i = 1; i <= QUANTIDADE_JOGADORES; i++)
        {

            System.out.println("Jogador n° " + i + ", seja bem vindo!!!\n");

            boolean opcaoValida = false;
            do
            {
                Usuario jogador = new Usuario();
                
                menu.MenuInicial();
                System.out.print("Digite a opção desejada: ");
                int opcaoDesejada = in.nextInt();
                clearConsole();

                switch (opcaoDesejada)
                {
                    case 1 ->//Usuario cadastrado
                    {
                        jogador = Usuario.buscarJogador(jogador, conector);

                        opcaoValida = validarSeJogadorCompleto(jogador);
                        if(opcaoValida)
                        {
                            jogador.setNumeroJogador(i);
                            jogadores.add(jogador);
                        }
                        esperar(3);
                        clearConsole();
                    }
                    case 2 ->//Criar Usuario
                    {
                        Personagem personagemEscolhido = new Personagem();

                        personagemEscolhido = personagemEscolhido.escolherPersonagem(conector);

                        jogador = Usuario.criarUsuario(args, personagemEscolhido, conector);

                        opcaoValida = validarSeJogadorCompleto(jogador);
                        if(opcaoValida)
                        {
                            jogador.setNumeroJogador(i);
                            jogadores.add(jogador);
                        }
                        esperar(3);
                        clearConsole();//TODO: cORRIGIR LOGICA DA CRIAÇÃO DO PERSONAGEM
                    }
                    case 3 ->
                    {
                        ArrayList<Ranking> rankings = new ArrayList<Ranking>();
                        rankings = conector.buscarScore();
                        clearConsole();
                        menu.MenuRanking(conector,rankings);
                        esperar(5);
                        clearConsole();
                    }
                    case 4 ->
                    {
                        jogadores.clear();
                        clearConsole();
                        main(args);
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
            } while (!opcaoValida);

        }


        clearConsole();
        //jogo.jogar(jogadores, conector);
        
        System.out.println("Fim das perguntas\n");
        menu.MenuEncerramento();
    }

    public static boolean validarSeJogadorCompleto(Usuario jogador) throws SQLException
    {
        if (jogador.getCodigoUsuario() > 0 && jogador.getPersonagem() != null)
        {
            System.out.println("Usuario logado com sucesso!");
            return true;
        } else
        {
            System.out.println("Não foi possível recuperar o seu Personagem");
            Personagem personagemEscolhido = new Personagem();

            personagemEscolhido = personagemEscolhido.escolherPersonagem(conector);
            jogador.setPersonagem(personagemEscolhido);
            return true;
        }
    }

    public static void esperar(int tempoEspera) throws InterruptedException
    {

        Thread.sleep(tempoEspera * 1000);

    }

    public final static void clearConsole()
    {
        try
        {
            Robot robot = new Robot();
            robot.setAutoDelay(10);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_L);
        } catch (AWTException ex)
        {
        }
    }
}
