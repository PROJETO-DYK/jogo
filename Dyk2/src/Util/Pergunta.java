package Util;

import Repository.JDBCConector;
import static dyk2.Dyk2.clearConsole;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pergunta
{

    private int CodigoPergunta;
    private String Pergunta;
    private List<Alternativa> Alternativas;

    public Pergunta()
    {
    }

    public Pergunta(String Pergunta)
    {
        this.Pergunta = Pergunta;
    }

    public Pergunta(int CodigoPergunta, String Pergunta)
    {
        this.CodigoPergunta = CodigoPergunta;
        this.Pergunta = Pergunta;
    }
    
     public int getCodigoPergunta()
    {
        return CodigoPergunta;
    }

    public void setCodigoPergunta(int CodigoPergunta)
    {
        this.CodigoPergunta = CodigoPergunta;
    }

    public String getPergunta()
    {
        return Pergunta;
    }

    public void setPergunta(String Pergunta)
    {
        this.Pergunta = Pergunta;
    }

    public List<Alternativa> getAlternativas()
    {
        return Alternativas;
    }

    public void setAlternativas(List<Alternativa> Alternativas)
    {
        this.Alternativas = Alternativas;
    }

    static public ArrayList<Pergunta> buscarPerguntas(JDBCConector conector) throws SQLException
    {

        ArrayList<Pergunta> perguntas = new ArrayList<Pergunta>();
        ArrayList<Alternativa> alternativas = new ArrayList<Alternativa>();

        alternativas = conector.buscarAlternativas();

        perguntas = conector.buscarPerguntas();

        for (Pergunta pergunta : perguntas)
        {
            ArrayList<Alternativa> generica = new ArrayList<Alternativa>();
            generica.clear();
            for (Alternativa alternativa : alternativas)
            {
                if (alternativa.getCodigoPergunta() == pergunta.getCodigoPergunta())
                {
                    generica.add(alternativa);
                }
            }
            pergunta.setAlternativas(generica);
        }
        return perguntas;
    }
    
    static public ArrayList<Pergunta> cadastrarPergunta(JDBCConector conector) throws SQLException
    {
        ArrayList<Pergunta> perguntas = new ArrayList<Pergunta>();
        Scanner in = new Scanner(System.in);
        
        System.out.println("Seja bem vindo(a) ao cadastro de perguntas!\n");
        
        
        
        
        
        System.out.println("Afim de agilizar e trazer uma boa experiencia durante o cadastro,\n"
                + " pedimos que insira todas as perguntas de forma previa. Mas não se preocupe\n"
                + "iremos exibir as perguntas no momento do cadastro das alternativas.");
        
//        int cadastrar = 1;
//        int count = 0;
//        
//        while(cadastrar == 1)
//        {
//            count ++;
//            Pergunta pergunta = new Pergunta();
//            if(count == 1)
//            {
//                System.out.println("Digite a primeira pergunta:");
//            }else
//            {
//                System.out.println("Digite a proxima pergunta:");
//            }
//            
//            String p = new String(in.nextLine());
//            
//            pergunta.setPergunta(p);
//            
//            perguntas.add(pergunta);
//            
//            System.out.println("Deseja cadastrar uma nova pergunta?");
//            System.out.println("Digite 1 - sim 2 - nao");
//            cadastrar = in.nextInt();
//            
//            clearConsole();
//        }
//        String mensagem = "";
//        
//        if (perguntas.size()>1)
//        {
//            mensagem = "Foram cadastradas "+ perguntas.size()+" perguntas.";
//        }else
//        {
//            mensagem = "Foi cadastrada apenas 1 pergunta.";
//        }

          perguntas.add(new Pergunta("Qual a utilidade do JAVA?"));
          perguntas.add(new Pergunta("Qual a utilidade do Banco de Dados?"));
        
        conector.salvarPergunta(perguntas);
        
        perguntas = conector.buscarCodigoPerguntas(perguntas);
        
        //System.out.println(mensagem);
        return perguntas;
    }

}
