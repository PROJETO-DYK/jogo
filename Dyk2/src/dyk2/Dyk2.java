package dyk2;

import Repository.JDBCConector;
import Util.Menu;
import java.util.Scanner;
import Util.Usuario;
import java.sql.SQLException;

public class Dyk2 {
    //constantes
    public static final int SIM = 1;
    public static final int NAO = 2;
    static final int QUANTIDADE_JOGADORES = 2;
    
    //instancias
    static Scanner in = new Scanner(System.in);
    static Menu menu = new Menu();
    static JDBCConector conector = new JDBCConector();
    static Usuario usuario = new Usuario();
    
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
                case 3 -> {
                    menu.MenuRegras();
                    System.out.println("\n");
                    menu.MenuInicializacao();
                }
                case 0 -> menu.MenuEncerramento();
                default -> {
                    System.out.println("\n Opção inválida digite novamente!");
                    menu.MenuInicial();
                }
            }
        }
        while (opcaoInicial >1 || opcaoInicial<0);
        conector.encerrarConexao();
        if (opcaoInicial == 1)
            System.out.println("Fim de jogo!");
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
    
    public static void iniciaJogo() throws SQLException
    {
        System.out.print("""
                         Lembrando que o DYK é um jogo para jogar em duplas!  
                           """);
        
        for (int i = 1; i <= QUANTIDADE_JOGADORES; i++){
            System.out.println("Jogado n° "+ i + "");
        }
        int existeUsuarioCadastrado = validacaoSimNao();
        
        if(existeUsuarioCadastrado == SIM)
        {
            System.out.print("Digite o seu e-mail: ");
            String email = in.next();
            usuario.setEmail(email);
            
            System.out.print("Digite sua senha: ");
            String senha = in.next();
            usuario.setSenha(senha);
            
            Usuario teste = conector.buscarUsuario(email,senha);
        }
        
    }
}

