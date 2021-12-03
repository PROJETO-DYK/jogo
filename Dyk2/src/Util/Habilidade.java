
package Util;

public class Habilidade
{
    private int CodigoHabilidade;
    private String Habilidade;

    public Habilidade(int CodigoHabilidade, String Habilidade)
    {
        this.CodigoHabilidade = CodigoHabilidade;
        this.Habilidade = Habilidade;
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
