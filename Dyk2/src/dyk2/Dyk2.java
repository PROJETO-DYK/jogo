package dyk2;

import Repository.JDBCConector;
import Util.Menu;
import Util.Pergunta;
import Util.Personagem;
import java.util.Scanner;
import Util.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
       
       int count = 0;
   
       while(!perguntas.isEmpty())
       {
           if (count == 0)
           {
               //System.out.println("Jogador " + jogadores.get(jogadorEscolhido).getNomeUsuario()+ " você irá iniciar o jogo! E logo apos sera o proximo jogador");
           }
           
           int perguntaEscolhida = escolherPegunta(perguntas.size());
           
           System.out.println("RODADA " + (count + 1));
           System.out.println("Pergunta n° " + (count +1)+ " " + perguntas.get(perguntaEscolhida).getPergunta());
           
           //System.out.println(perguntas.get(perguntaEscolhida).getAlternativas().get(0).getResposta());       //O SISTEMA ESTA IMPRIMINDO DESNECESSARIAMENTE UMA DAS ALTERNATIVAS ANTES DE MOSTRAR AS 4 ALTERNATIVAS
           //selecionarPergunta()
           //logica de pergunta
           
           int alternativa = 1;
           for (var resposta : perguntas.get(perguntaEscolhida).getAlternativas()){                             //O JOGO ESTA SEMPRE IMPRIMINDO AS ULTIMAS 4 ALTERNATIVAS DO BANCO DE DADOS
               System.out.println(alternativa + " - " +resposta.getResposta());
               alternativa++;
           }
           
           System.out.print("Digite a resposta correta: ");
           int resposta = in.nextInt();
           
           if (perguntas.get(perguntaEscolhida).getAlternativas().get(resposta).isCorreta())
           {
               System.out.println("\nExcelente! Resposta correta, não gerou perda de vida e ganhou +10 pontos!\n");
               //pontosCalculados
               //Metode de Vida
               System.out.println("#####################################");
               System.out.println("## Atualmente a sua pontuação e:   ##");/*PUXAR METODO PARA IMPRIMIR A PONTUAÇÃO)*/
               System.out.println("## Atualmente a sua vida e:        ##");/*PUXAR METODO PARA IMPRIMIR A VIDA)*/
               System.out.println("#####################################\n");
               System.out.println("VAMOS PARA A RODADA " + (count + 2) + "\n");
               System.out.println("ONDE O PROXIMO JOGADOR IRA RESPONDER AS PERGUNTAS!");
               new Thread().sleep(10000);
               clearConsole();
               
           }
           else
           {
               System.out.println("\nOps!! Parece que você errou a resposta, perca de 10HP e nenhuma pontuacao gerada!\n");
               //pontosCalculados
               //Metode de Vida
               System.out.println("#####################################");
               System.out.println("## Atualmente a sua pontuação e:   ##");/*PUXAR METODO PARA IMPRIMIR A PONTUAÇÃO)*/
               System.out.println("## Atualmente a sua vida e:        ##");/*PUXAR METODO PARA IMPRIMIR A VIDA)*/
               System.out.println("#####################################\n");
               System.out.println("VAMOS PARA A RODADA " + (count + 2) + "\n");
               System.out.println("ONDE O PROXIMO JOGADOR IRA RESPONDER AS PERGUNTAS!");
               new Thread().sleep(10000);
               clearConsole();
           }
           count++;    
       }
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
   
    public static void clearConsole() throws InterruptedException
    {
        for (int i = 0; i < 100; ++i)
            System.out.println();    
    }

    private static int pontosCalculados() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}