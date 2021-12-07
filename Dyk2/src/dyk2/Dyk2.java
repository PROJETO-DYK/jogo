package dyk2;

import Repository.JDBCConector;
import Util.Jogo;
import Util.Menu;
import Util.Personagem;
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
    static Menu menu = new Menu();
    static JDBCConector conector = new JDBCConector();
    static Usuario usuario = new Usuario();
    static ArrayList<Usuario> jogadores = new ArrayList<Usuario>();
    static Jogo jogo = new Jogo();

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

                switch (opcaoDesejada)
                {
                    case 1 ->//Usuario cadastrado
                    {
                        jogador = Usuario.buscarJogador(jogador, conector);

                        opcaoValida = validarSeJogadorCompleto(jogador, i);
                        clearConsole();
                        System.out.println("Usuario Logado com sucesso");
                        esperar(3);
                        clearConsole();
                    }
                    case 2 ->//Criar Usuario
                    {
                        Personagem personagemEscolhido = new Personagem();

                        personagemEscolhido = personagemEscolhido.escolherPersonagem(conector);

                        jogador = Usuario.criarUsuario(args, personagemEscolhido, conector);

                        opcaoValida = validarSeJogadorCompleto(jogador, i);
                        clearConsole();
                        System.out.println("Usuario Logado com sucesso");
                        esperar(3);
                        clearConsole();
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
            } while (!opcaoValida);

        }

        clearConsole();
        jogo.jogar(jogadores, conector);
    }

    public static boolean validarSeJogadorCompleto(Usuario jogador, int numeroJogador) throws SQLException
    {
        if (jogador.getCodigoUsuario() > 0 && jogador.getPersonagem() != null)
        {
            jogador.setNumeroJogador(numeroJogador);
            jogadores.add(jogador);
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
