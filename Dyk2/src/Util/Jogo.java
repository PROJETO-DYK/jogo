package Util;

import Repository.JDBCConector;
import static dyk2.Dyk2.PONTUACAO_CREDITADA;
import static dyk2.Dyk2.PONTUACAO_DEBITADA;
import static dyk2.Dyk2.clearConsole;
import static dyk2.Dyk2.esperar;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Jogo
{

    Scanner in = new Scanner(System.in);
    Menu menu = new Menu();

    public void jogar(ArrayList<Usuario> jogadores, JDBCConector conector) throws SQLException, InterruptedException
    {
        ArrayList<Pergunta> perguntas = new ArrayList<Pergunta>();

        perguntas = Pergunta.buscarPerguntas(conector);

        int jogadorEscolhido = Usuario.escolherUsuario(jogadores.size()-1);

        int rodada = 1;
        while (!perguntas.isEmpty())
        {
            if (rodada == 1)
            {
                int interacoes = 1;
                System.out.println("Desafio entre: ");
                for (Usuario jogador : jogadores)
                {
                    if (interacoes<jogadores.size()-1)
                        System.out.print("Jogador numero : "+ jogador.getNumeroJogador() + " Nome: "+ jogador.getNomeUsuario() + " - Score " + jogador.getScore() + " VS. \n");
                    else 
                        System.out.print("Jogador numero : "+ jogador.getNumeroJogador() + " Nome: "+ jogador.getNomeUsuario() + " - Score " + jogador.getScore() + "\n");
                    interacoes++;
                }
                esperar(5);
                clearConsole();
                System.out.println("Escolhendo Jogador para iniciar a partida...");
                esperar(10);
                clearConsole();
                System.out.println("Disafiante " + jogadores.get(jogadorEscolhido).getNomeUsuario()+ " você irá iniciar o jogo! E logo apos sera o proximo jogador");
                esperar(4);
                clearConsole();
            } else
            {
                jogadorEscolhido = trocarJogador(jogadorEscolhido);
            }
            Usuario jogadorAtual = jogadores.get(jogadorEscolhido);

            int perguntaEscolhida = escolherPegunta(perguntas.size());

            int respostaEscolhidaJogador = 0;

            boolean certeza = true;
            do
            {
                respostaEscolhidaJogador = realizarPergunta(rodada, perguntaEscolhida, perguntas.get(perguntaEscolhida), perguntas) - 1;

                if (respostaEscolhidaJogador >= 0)
                {
                    menu.MenuConfirmarResposta();
                    int res = in.nextInt();
                    if (res == 1)
                    {
                        certeza = false;
                    } else
                    {
                        clearConsole();
                    }
                } else
                {
                    certeza = false;
                }
            } while (certeza);

            if (respostaEscolhidaJogador >= 0)
            {
                if (perguntas.get(perguntaEscolhida).getAlternativas().get(respostaEscolhidaJogador).isCorreta())
                {
                    clearConsole();
                    menu.MenuRespostaCerta();
                    jogadorAtual.setScore(jogadorAtual.getScore()+PONTUACAO_CREDITADA);
                    System.out.println("VAMOS PARA A RODADA " + (rodada + 1) + "\n");
                    //esperar(10);
                    clearConsole();

                } else
                {
                    clearConsole();
                    menu.MenuRespostaErrada();
                    System.out.println("VAMOS PARA A RODADA " + (rodada + 1) + "\n");
                    jogadorAtual.getPersonagem().setTempoVida(jogadorAtual.getPersonagem().getTempoVida() - PONTUACAO_DEBITADA);

                    if (jogadorAtual.getPersonagem().getTempoVida() < 0)
                    {
                        int proximoJogador = trocarJogador(jogadorEscolhido);
                        salvarScores(jogadores, conector);
                        System.out.println("Jogador " + jogadorAtual.getNomeUsuario() + "infelizmente sua vida chegou ao fim.");

                        System.out.println("O Score dos atual do jogadores é :");
                        System.out.println(jogadorAtual.getNomeUsuario() + ": " + jogadorAtual.getScore());
                        System.out.println(jogadores.get(proximoJogador).getNomeUsuario() + ": " + jogadores.get(proximoJogador).getScore());

                        if (jogadorAtual.getScore() > jogadores.get(proximoJogador).getScore())
                        {
                            System.out.println("Jogador " + jogadorAtual.getNomeUsuario());
                        } else if ((jogadorAtual.getScore() == jogadores.get(proximoJogador).getScore()))
                        {
                            System.out.println("Houve um empate");
                        } else
                        {
                            System.out.println("Jogador " + jogadores.get(proximoJogador).getNomeUsuario());
                        }

                        //E ENCERRO O JOGO
                    } else
                    {
                        esperar(10);
                        clearConsole();
                    }
                }
            } else
            {
                //logica para sair do jogo
                clearConsole();
                System.out.println("Lembrando que ao escolher sair do jogo você poderá sofrer penalização\n"
                        + "(caso seu adversário não queira sair da partida)\n e seus pontos irão para o seu adiversário.");
                System.out.println("Deseja realmente sair? 1 - sim 2 -não");
                int parar = in.nextInt();

                if (parar == 1)
                {
                    int proximoJogador = trocarJogador(jogadorEscolhido);
                    System.out.println("Jogador " + jogadores.get(proximoJogador).getNomeUsuario() + " você deseja encerrar a partida também?");
                    System.out.println("Digite 1 - sim 2 -não");
                    int encerrar = in.nextInt();

                    if (encerrar == 1)
                    {

                        clearConsole();
                        System.out.println("Nesse caso nenhum ponto será debitado dos participantes, obrigado por jogar o DYK.\n");
                        menu.MenuEncerramento();
                        System.exit(0);
                    } else
                    {

                        clearConsole();
                        int pontuacaoJogadorAtual = jogadores.get(jogadorAtual.getNumeroJogador()).getScore();
                        int pontuacaoProximoJogador = jogadores.get(jogadorAtual.getNumeroJogador()).getScore();

                        System.out.println(jogadorAtual.getNomeUsuario()
                                + "\n você terá o seu score abaixado em " + PONTUACAO_DEBITADA + " pontos"
                                + "\n e repassado para " + jogadores.get(proximoJogador).getNomeUsuario() + "\n\n");

                        System.out.println("Seu score era de: " + pontuacaoJogadorAtual);
                        pontuacaoJogadorAtual =-PONTUACAO_DEBITADA;
                        System.out.println("Seu score é de: " + (pontuacaoJogadorAtual));

                        pontuacaoProximoJogador = +PONTUACAO_DEBITADA;
                        System.out.println("Jogador " + jogadores.get(proximoJogador).getNomeUsuario()
                                + "\nseu novo score é de : " + (pontuacaoProximoJogador + PONTUACAO_DEBITADA));

                        salvarScores(jogadores, conector);
                    }
                } else
                {
                    clearConsole();
                    System.out.println("Nesse caso então apenas perderá sua vez!");
                    esperar(3);
                    clearConsole();
                }
            }
            rodada++;
            perguntas.remove(perguntaEscolhida);
        }
        salvarScores(jogadores, conector);
        System.out.println("Fim das perguntas\n");
        menu.MenuEncerramento();
    }

    public static void salvarScores(ArrayList<Usuario> jogadores, JDBCConector conector)
    {
        for (int i = 0; i < jogadores.size(); i++)
        {
            conector.salvarScore(jogadores.get(i).getScore(), jogadores.get(i).getCodigoUsuario());
        }

    }

    public static int trocarJogador(int jogadorAtual)
    {
        if (jogadorAtual == 1)
        {
            jogadorAtual = 0;
        } else
        {
            jogadorAtual = 1;
        }

        return jogadorAtual;
    }

    public int realizarPergunta(int rodada, int perguntaEscolhida, Pergunta pergunta, ArrayList<Pergunta> perguntas)
    {
        int resposta = 1;
        do
        {
            if (!(resposta > 0 && resposta <= pergunta.getAlternativas().size()))
            {
                System.out.println("Não existe a alternativa escolhida, favor escolha uma das alternativas abaixo ou 0 para sair.\n\n");
            }

            System.out.println("RODADA " + (rodada));
            System.out.println("Pergunta n° " + (rodada) + " " + perguntas.get(perguntaEscolhida).getPergunta());

            int a = 1;
            for (Alternativa alternativa : pergunta.getAlternativas())
            {
                System.out.println(a + " - " + alternativa.getResposta());
                a++;
            }

            System.out.print("Digite a sua resposta ou 0 para sair: ");
            resposta = in.nextInt();

        } while (!(resposta >= 0 && resposta <= pergunta.getAlternativas().size()));//a resposta tem que ser de 1 a 4 
        return resposta;
    }

    public static int escolherPegunta(int quantidadeDeJogadores)
    {
        Random rand = new Random();
        int perguntaEscolhida = rand.nextInt(quantidadeDeJogadores);
        return perguntaEscolhida;

    }
}
