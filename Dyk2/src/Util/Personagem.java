package Util;

import java.util.List;

public class Personagem {
    private int IdPersonagem;
    private String NomePersonagem;
    private String StatusPersonagem;
    private List<String> Habilidades;
    private Float TempoVida;
    private boolean IndicadorEscolhido;

    public Personagem(int IdPersonagem, String NomePersonagem, String StatusPersonagem, List<String> Habilidades, Float TempoVida, boolean IndicadorEscolhido) {
        this.IdPersonagem = IdPersonagem;
        this.NomePersonagem = NomePersonagem;
        this.StatusPersonagem = StatusPersonagem;
        this.Habilidades = Habilidades;
        this.TempoVida = TempoVida;
        this.IndicadorEscolhido = IndicadorEscolhido;
    }

    public int getIdPersonagem() {
        return IdPersonagem;
    }

    public void setIdPersonagem(int IdPersonagem) {
        this.IdPersonagem = IdPersonagem;
    }

    public String getNomePersonagem() {
        return NomePersonagem;
    }

    public void setNomePersonagem(String NomePersonagem) {
        this.NomePersonagem = NomePersonagem;
    }

    public String getStatusPersonagem() {
        return StatusPersonagem;
    }

    public void setStatusPersonagem(String StatusPersonagem) {
        this.StatusPersonagem = StatusPersonagem;
    }

    public List<String> getHabilidades() {
        return Habilidades;
    }

    public void setHabilidades(List<String> Habilidades) {
        this.Habilidades = Habilidades;
    }

    public Float getTempoVida() {
        return TempoVida;
    }

    public void setTempoVida(Float TempoVida) {
        this.TempoVida = TempoVida;
    }

    public boolean isIndicadorEscolhido() {
        return IndicadorEscolhido;
    }

    public void setIndicadorEscolhido(boolean IndicadorEscolhido) {
        this.IndicadorEscolhido = IndicadorEscolhido;
    }
    
    
}
