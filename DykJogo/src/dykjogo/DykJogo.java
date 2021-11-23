package dykjogo;

import java.util.Scanner;
import Util.Usuario;

public class DykJogo {
    //constantes
    static final int SIM = 1;
    static final int NAO = 2;
    static Scanner in = new Scanner(System.in);
    static Usuario usuario = new Usuario();
    
    public static void main(String[] args) {
        
        //interação com o usuário
        System.out.println("Seja Bem Vindo(a) ao DYK, seu jogo de Perguntas e respostas!!!\n");
        System.out.println("Lembrando que o DYK é um jogo para jogar em duplas!\n");
        System.out.println("Você e seu (sua) oponente já possuem uma conta conosco?");
        
        int existeUsuarioCadastrado = validacaoSimNao(); 
        
        if (existeUsuarioCadastrado == SIM)
        {
            //LOGAR EM SUAS CONTAS
            int tentativas = 0;
            boolean emailValido = false;                   
            do
            {
                emailValido = verificarEmail();
                if (!emailValido)
                {
                    System.out.println("Não encontramos o e-mail digitado. Tentar novamente?");
                    int tentarNovamente = validacaoSimNao();
                    if(tentarNovamente == NAO)
                        tentativas = 3;
                    else tentativas ++;
                }
                 
                tentativas ++;
            }
            while (!emailValido && tentativas < 3);
            
            if (!emailValido)
            {
                System.out.println("O email digitado não foi encontrado.\n");
                System.out.println("Voc");
                validacaoSimNao();
            }
            
            System.out.println("Digite sua senha");
            String senha = in.next();
            
            
        }
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
    public static boolean verificarEmail()
    {
        System.out.println("Digite seu e-mail");
        //String email = in.next();
        
        return usuario.validarEmail("email");
    }
}

