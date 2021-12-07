package Util;

import Repository.JDBCConector;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Pergunta
{

    private int CodigoPergunta;
    private String Pergunta;
    private List<Alternativa> Alternativas;

    public Pergunta()
    {
    }

    public Pergunta(int CodigoPergunta, String Pergunta)
    {
        this.CodigoPergunta = CodigoPergunta;
        this.Pergunta = Pergunta;
        this.Alternativas = Alternativas;
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

}
