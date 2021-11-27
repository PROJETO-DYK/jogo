package dykjogo;

import Util.Menu;
import java.util.Scanner;
import Util.Usuario;

public class DykJogo {
    //constantes
    static final int SIM = 1;
    static final int NAO = 2;
    static Scanner in = new Scanner(System.in);
    static Usuario usuario = new Usuario();
    static Menu menu = new Menu();
    static JDBCConector conector;
    
    public static void main(String[] args) 
    {
        //Teste de conexao com o Banco de dados//
        conector = new JDBCConector();
        conector.inciarConexao();
        
        conector.encerrarConexao();
        
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
                case 1:
                    iniciaJogo();
                  break;
                case 2:
                    System.out.println("opcao2");
                  break;
                case 3:
                    menu.MenuRegras();
                    System.out.println("\n");
                    menu.MenuInicializacao();
                  break;
                case 0:
                    menu.MenuEncerramento();
                  break;
                default:
                    System.out.println("\n Opção inválida digite novamente!");
                    menu.MenuInicial();
            }
        }
        while (opcaoInicial >1 || opcaoInicial<0);
        
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
   
    /*public static boolean verificarEmail()
    {
        System.out.println("Digite seu e-mail");
        //String email = in.next();
        return usuario.ValidarEmail("email");
    }*/
    
    public static void iniciaJogo()
    {
        System.out.print("""
                         Lembrando que o DYK é um jogo para jogar em duplas!
                         Você e seu oponente já possuem conta conosco?    
                           """);
        int existeUsuarioCadastrado = validacaoSimNao();
        
        if(existeUsuarioCadastrado == NAO)
        {
            //cadastrar a conta
        }
        else
        {
            //recupera os dados do usuário
        }
        
    }
}

