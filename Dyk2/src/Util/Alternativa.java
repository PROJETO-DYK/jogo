package Util;

public class Alternativa {
     private int CodigoResposta;
     private int CodigoPergunta;
     private String Resposta;

    public Alternativa(int CodigoResposta, int CodigoPergunta, String Resposta) {
        this.CodigoResposta = CodigoResposta;
        this.CodigoPergunta = CodigoResposta;
        this.Resposta = Resposta;
    }
    
    public Alternativa(){}

    public int getCodigoResposta() {
        return CodigoResposta;
    }

    public void setCodigoResposta(int CodigoResposta) {
        this.CodigoResposta = CodigoResposta;
    }

    public String getResposta() {
        return Resposta;
    }

    public void setResposta(String Resposta) {
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
    
}
