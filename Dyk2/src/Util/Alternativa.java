package Util;

import Repository.JDBCConector;
import static dyk2.Dyk2.clearConsole;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Alternativa
{
    

    private int CodigoResposta;
    private int CodigoPergunta;
    private String Resposta;
    private boolean Correta;

    public Alternativa(int CodigoResposta, int CodigoPergunta, String Resposta, boolean Correta)
    {
        this.CodigoPergunta = CodigoPergunta;
        this.Resposta = Resposta;
        this.Correta = Correta;
    }

    public Alternativa()
    {
    }

    public int getCodigoResposta()
    {
        return CodigoResposta;
    }

    public void setCodigoResposta(int CodigoResposta)
    {
        this.CodigoResposta = CodigoResposta;
    }

    public String getResposta()
    {
        return Resposta;
    }

    public void setResposta(String Resposta)
    {
        this.Resposta = Resposta;
    }

    public int getCodigoPergunta()
    {
        return CodigoPergunta;
    }

    public void setCodigoPergunta(int CodigoPergunta)
    {
        this.CodigoPergunta = CodigoPergunta;
    }

    public boolean isCorreta()
    {
        return Correta;
    }

    public void setCorreta(boolean Correta)
    {
        this.Correta = Correta;
    }
    
    static public void cadastrarAlternativas(JDBCConector conector,ArrayList <Pergunta> perguntas) throws SQLException
    {
        
        Scanner in = new Scanner(System.in);
        System.out.println("Agora vamos cadastrar as 4 alternativas: \n");
        
        for (Pergunta pergunta : perguntas)
        {
            System.out.println("Pergunta: " + pergunta);
            ArrayList<Alternativa> alternativas = new ArrayList<Alternativa>();
            for (int i = 0; i < 4; i++)
            {
                
                Alternativa alternativa = new Alternativa();
                System.out.println("Digite a alternativa " + (i+1)+ ".");
                
                String alt = in.nextLine();

                System.out.println("Essa é a alternativa correta? 1- Sim 2- Não.");
                int t = in.nextInt();
                
                boolean indResposta;
                if (t==1)
                    indResposta = true;
                else
                    indResposta = false;
                
                alternativa.setCodigoPergunta(pergunta.getCodigoPergunta());
                alternativa.setResposta(alt);
                alternativa.setCorreta(indResposta);
                alternativas.add(alternativa);
            }
            conector.salvarAlternativa(alternativas);
            
            pergunta.setAlternativas(alternativas);
            clearConsole();
        }
        perguntas = conector.buscarCodigoRespostas(perguntas);
        
        
    }

}
