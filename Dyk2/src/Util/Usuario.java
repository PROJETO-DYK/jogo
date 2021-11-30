package Util;

import Repository.JDBCConector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Usuario {
    private int CodigoUsuario;
    private String NomeUsuario;
    private String SobrenomeUsuario;
    private String Email;
    private String Apelido;
    private String Senha;
    private boolean UsuarioAtivo;
    
    static Scanner in = new Scanner(System.in);
    
    public Usuario(int CodigoUsuario, String NomeUsuario, String SobrenomeUsuario, String Email, String Apelido, String Senha, boolean UsuarioAtivo) {
        this.CodigoUsuario = CodigoUsuario;
        this.NomeUsuario = NomeUsuario;
        this.SobrenomeUsuario = SobrenomeUsuario;
        this.Email = Email;
        this.Apelido = Apelido;
        this.Senha = Senha;
        this.UsuarioAtivo = UsuarioAtivo;
    }
    public Usuario(){}

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

    public String getSobrenomeUsuario() {
        return SobrenomeUsuario;
    }

    public void setSobrenomeUsuario(String SobrenomeUsuario) {
        this.SobrenomeUsuario = SobrenomeUsuario;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getApelido() {
        return Apelido;
    }

    public void setApelido(String Apelido) {
        this.Apelido = Apelido;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }

    public boolean isUsuarioAtivo() {
        return UsuarioAtivo;
    }

    public void setUsuarioAtivo(boolean UsuarioAtivo) {
        this.UsuarioAtivo = UsuarioAtivo;
    }
    
}
