package Util;

public class Habilidade
{

    private int CodigoHabilidade;
    private String Habilidade;
    private boolean habilidadeUtilizada;

    public Habilidade(int CodigoHabilidade, String Habilidade, boolean habilidadeUtilizada)
    {
        this.CodigoHabilidade = CodigoHabilidade;
        this.Habilidade = Habilidade;
        this.habilidadeUtilizada = habilidadeUtilizada;
    }

    public int getCodigoHabilidade()
    {
        return CodigoHabilidade;
    }

    public void setCodigoHabilidade(int CodigoHabilidade)
    {
        this.CodigoHabilidade = CodigoHabilidade;
    }

    public String getHabilidade()
    {
        return Habilidade;
    }

    public void setHabilidade(String Habilidade)
    {
        this.Habilidade = Habilidade;
    }

}
