package Util;

import java.util.List;

public class Personagem {
    private int IdPersonagem;
    private String NomePersonagem;
    private List<Habilidade> Habilidades;
    private Float TempoVida;
    private boolean IndicadorEscolhido;

    public Personagem(int IdPersonagem, String NomePersonagem, List<Habilidade> Habilidades, Float TempoVida, boolean IndicadorEscolhido)
    {
        this.IdPersonagem = IdPersonagem;
        this.NomePersonagem = NomePersonagem;
        this.Habilidades = Habilidades;
        this.TempoVida = TempoVida;
        this.IndicadorEscolhido = IndicadorEscolhido;
    }
    public Personagem(){ }

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

    public List<Habilidade> getHabilidades() {
        return Habilidades;
    }

    public void setHabilidades(List<Habilidade> Habilidades) {
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
