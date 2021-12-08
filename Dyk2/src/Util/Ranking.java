/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

public class Ranking {
    private int Score;
    private int CodigoUsuario;
    private String NomeUsuario;

    public Ranking(int Score, int CodigoUsuario, String NomeUsuario) {
        this.Score = Score;
        this.CodigoUsuario = CodigoUsuario;
        this.NomeUsuario = NomeUsuario;
    }

    public Ranking(int Score) {
        this.Score = Score;
    }
    
    
    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    public int getCodigoUsuario() {
        return CodigoUsuario;
    }

    public void setCodigoUsuario(int CodigoUsuario) {
        this.CodigoUsuario = CodigoUsuario;
    }

    public String getNomeUsuario() {
        return NomeUsuario;
    }

    public void setNomeUsuario(String NomeUsuario) {
        this.NomeUsuario = NomeUsuario;
    }

    
}
