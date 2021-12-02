package Util;

public class Alternativa {
     private int CodigoResposta;
     private String Resposta;

    public Alternativa(int CodigoResposta, String Resposta) {
        this.CodigoResposta = CodigoResposta;
        this.Resposta = Resposta;
    }

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
    
}
