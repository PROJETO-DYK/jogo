package Util;

public class Alternativa
{

    private int CodigoResposta;
    private int CodigoPergunta;
    private String Resposta;
    private boolean Correta;

    public Alternativa(int CodigoResposta, int CodigoPergunta, String Resposta, boolean Correta)
    {
        this.CodigoResposta = CodigoResposta;
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

}
