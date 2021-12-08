package Util;

import Repository.JDBCConector;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Personagem
{

    private int IdPersonagem;
    private String NomePersonagem;
    private List<Habilidade> Habilidades;
    private Float TempoVida;
    private boolean IndicadorEscolhido;

    Scanner in = new Scanner(System.in);

    public Personagem(int IdPersonagem, String NomePersonagem, List<Habilidade> Habilidades, Float TempoVida, boolean IndicadorEscolhido)
    {
        this.IdPersonagem = IdPersonagem;
        this.NomePersonagem = NomePersonagem;
        this.Habilidades = Habilidades;
        this.TempoVida = TempoVida;
        this.IndicadorEscolhido = IndicadorEscolhido;
    }

    public Personagem(int IdPersonagem, String NomePersonagem, Float TempoVida, boolean IndicadorEscolhido)
    {
        this.IdPersonagem = IdPersonagem;
        this.NomePersonagem = NomePersonagem;
        this.TempoVida = TempoVida;
        this.IndicadorEscolhido = IndicadorEscolhido;
    }
    
    public Personagem(int IdPersonagem)
    {
        this.IdPersonagem = IdPersonagem;
    }

    public Personagem()
    {
    }

    public int getIdPersonagem()
    {
        return IdPersonagem;
    }

    public void setIdPersonagem(int IdPersonagem)
    {
        this.IdPersonagem = IdPersonagem;
    }

    public String getNomePersonagem()
    {
        return NomePersonagem;
    }

    public void setNomePersonagem(String NomePersonagem)
    {
        this.NomePersonagem = NomePersonagem;
    }

    public List<Habilidade> getHabilidades()
    {
        return Habilidades;
    }

    public void setHabilidades(List<Habilidade> Habilidades)
    {
        this.Habilidades = Habilidades;
    }

    public Float getTempoVida()
    {
        return TempoVida;
    }

    public void setTempoVida(Float TempoVida)
    {
        this.TempoVida = TempoVida;
    }

    public boolean isIndicadorEscolhido()
    {
        return IndicadorEscolhido;
    }

    public void setIndicadorEscolhido(boolean IndicadorEscolhido)
    {
        this.IndicadorEscolhido = IndicadorEscolhido;
    }

    public Personagem escolherPersonagem(JDBCConector conector) throws SQLException
    {
        ArrayList<Personagem> personagens = new ArrayList<Personagem>();

        personagens = conector.buscarPersonagens();
        
        boolean opcaoValida = false;
        do{
            if (!opcaoValida)
                System.out.println("Personagem inexistente!\n");
                
            System.out.println("Escolha o seu pesonagem:");
            for (Personagem personagem : personagens)
            {
                System.out.println("[" + personagem.getIdPersonagem() + "]" + " - " + personagem.getNomePersonagem());
            }

            int personagemEscolhido = in.nextInt()-1;
            
            if (personagemEscolhido >= 0 && personagemEscolhido<= personagens.size()-1)
            {
                Personagem personagem = personagens.get(personagemEscolhido);
        
                personagem = conector.buscarHabilidadesPersonagem(personagem);
                
                opcaoValida = true;
                
                return personagem;
            }
            
        }while (!opcaoValida);
        
        return null;
    }

}
