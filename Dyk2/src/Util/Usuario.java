package Util;

import Repository.JDBCConector;
import static dyk2.Dyk2.main;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class Usuario {
    private int CodigoUsuario;
    private String NomeUsuario;
    private String SobrenomeUsuario;
    private String Email;
    private String Apelido;
    private String Senha;
    private boolean UsuarioAtivo;
    private int NumeroJogador;
    private int CodigoPersonagem;
    private Personagem personagem;
    
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
    
    public int getNumeroJogador()
    {
        return NumeroJogador;
    }

    public void setNumeroJogador(int NumeroJogador)
    {
        this.NumeroJogador = NumeroJogador;
    }

    public Personagem getPersonagem()
    {
        return personagem;
    }

    public void setPersonagem(Personagem personagem)
    {
        this.personagem = personagem;
    }
    
    public Personagem getCodigoPersonagem()
    {
        return personagem;
    }

    public void setCodigoPersonagem(Personagem personagem)
    {
        this.personagem = personagem;
    }
    
    public Usuario preencherUsuario(int CodigoUsuario, String NomeUsuario, String SobrenomeUsuario, String Email, String Apelido, String Senha, boolean UsuarioAtivo,int CodigoPersonagem) {
        Personagem p = new Personagem();
        this.CodigoUsuario = CodigoUsuario;        
        this.NomeUsuario = NomeUsuario;
        this.SobrenomeUsuario = SobrenomeUsuario;
        this.Email = Email;
        this.Apelido = Apelido;
        this.Senha = Senha;
        this.UsuarioAtivo = UsuarioAtivo;
        this.CodigoPersonagem = CodigoPersonagem; 
        
        return this;
    }
    
    public int escolherUsuario(int quantidadeDeJogadores)
    {
       Random rand = new Random(); 
       int usuarioEscolhido = rand.nextInt(quantidadeDeJogadores)+1;
       System.out.println("O jogador Nº: " + usuarioEscolhido + " vai começar respondendo as perguntas!!");
       return usuarioEscolhido;
       
    }
    
    public static Usuario criarUsuario(String[] args,Personagem personagemEscolhido,JDBCConector conector) throws SQLException, InterruptedException
    {
        Usuario usuario;
        
        System.out.println("Bem vindo a tela de cadastro de Usuario!\n");
        
        System.out.print("Para começar, digite seu e-mail: ");
        
        String email = in.next();
        
        usuario = conector.buscarUsuario(email,"",2);
        
        if(usuario.getCodigoUsuario()<0)
            main(args); //volta ao menu inicial por opção do usuario
        else if (usuario.getCodigoUsuario()>0)
        {
            return buscarJogador(usuario,conector);
        }
        else
        {
            String senha, nome, sobrenome,apelido;
            
            System.out.println("Digite a senha: ");
            senha = in.next();
            usuario.setSenha(senha);
            
            System.out.println("Digite seu nome:");
            nome = in.next();
            usuario.setNomeUsuario(nome);
            
            System.out.println("Digite seu sobrenome:");
            sobrenome = in.next();
            usuario.setSobrenomeUsuario(sobrenome);
            
            System.out.println("Digite seu apelido:");
            apelido = in.next();
            usuario.setApelido(apelido);
            
            usuario.setPersonagem(personagemEscolhido);
            
            conector.buscarHabilidadesPersonagem(usuario);
            
            conector.inserirUsuario(usuario);
        }
        return usuario;
    }
    
        public static Usuario buscarJogador(Usuario jogador,JDBCConector conector) throws SQLException
    {
        if (jogador.getNomeUsuario() == null)
        {
            System.out.print("Digite o seu e-mail: ");
            String email = in.next();
            jogador.setEmail(email);

            System.out.print("Digite sua senha: ");
            String senha = in.next();
            jogador.setSenha(senha);

            jogador = conector.buscarUsuario(email,senha,1);
        
        }
        Personagem penrsonagem = conector.buscarAvatarDoUsuario(jogador);
        
        jogador.setPersonagem(penrsonagem);
        
        return jogador;
    }
    
}
