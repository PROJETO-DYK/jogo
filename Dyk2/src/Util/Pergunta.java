package Util;

import java.util.List;

public class Pergunta {
    private int CodigoPergunta;
    private String Pergunta;
    private List<Alternativa> Alternativas;

    public Pergunta(){}
    
    public Pergunta(int CodigoPergunta, String Pergunta) {
        this.CodigoPergunta = CodigoPergunta;
        this.Pergunta = Pergunta;
        this.Alternativas = Alternativas;
    }
    
    public int getCodigoPergunta() {
        return CodigoPergunta;
    }

    public void setCodigoPergunta(int CodigoPergunta) {
        this.CodigoPergunta = CodigoPergunta;
    }

    public String getPergunta() {
        return Pergunta;
    }

    public void setPergunta(String Pergunta) {
        this.Pergunta = Pergunta;
    }

    public List<Alternativa> getAlternativas() {
        return Alternativas;
    }

    public void setAlternativas(List<Alternativa> Alternativas) {
        this.Alternativas = Alternativas;
    }
    
    }