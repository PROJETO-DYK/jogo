package dyk2;

import Repository.JDBCConector;
import Util.Menu;
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
    static ArrayList<Usuario> usuariosLogados = new ArrayList<Usuario>();
    
    
    public static void main(String[] args) throws SQLException 
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
                case 1 -> iniciaJogo();
                case 2 -> System.out.println("opcao2");
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
    
    
    
    public static void iniciaJogo() throws SQLException
    {
        System.out.println("Lembrando que o DYK é um jogo para jogar em duplas!\n");
        
        for (int i = 1; i <= QUANTIDADE_JOGADORES; i++){
            
            System.out.println("Jogador n° " + i + ", seja bem vindo!!!\n");
            
            boolean opcaoValida = false;
            do 
            {
                menu.MenuInicial();
                System.out.print("Digite a opção desejada: ");
                int opcaoDesejada = in.nextInt();
                
                switch (opcaoDesejada){
                    case 1 ->//Usuario cadastrado
                    {
                        Usuario jogador = buscarJogador();//TODO: Logica
                                                
                        if (jogador.getCodigoUsuario()>0)
                        {
                            jogador.setNumeroJogador(i);
                            usuariosLogados.add(jogador);
                            System.out.println("Usuario logado com sucesso!");
                            opcaoValida = true;
                        }
                        
                    }
                    case 2 ->
                    {
                        System.out.println("chamar metodo para ja cadastrado");//TODO: Logica
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
        
        //inicia o jogo(passando a lista de usuarios)
                
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
    
    public static Usuario buscarJogador() throws SQLException
    {            
        System.out.print("Digite o seu e-mail: ");
        String email = in.next();
        usuario.setEmail(email);

        System.out.print("Digite sua senha: ");
        String senha = in.next();
        usuario.setSenha(senha);
        
        // Recupero o Personagem do usuário do banco

        return conector.buscarUsuario(email,senha);
    }
}

