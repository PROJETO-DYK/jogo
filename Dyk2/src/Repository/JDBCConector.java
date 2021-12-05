package Repository;

import Util.Alternativa;
import Util.Habilidade;
import Util.Personagem;
import Util.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import Util.Pergunta;

public class JDBCConector
{
    Scanner in = new Scanner(System.in);
    
    private final String URL = "jdbc:mysql://localhost:3306/dyk_db";
    private final String USER = "admin";
    private final String PASSWD = "12345";
    private final String DRIVER_BANCO = "com.mysql.cj.jdbc.Driver";

    private Connection conexao;

    //<editor-fold defaultstate="collapsed" desc="Conexão">
    public void inciarConexao()
    {
        try
        {
            Class.forName(DRIVER_BANCO);
            conexao = DriverManager.getConnection(URL, USER, PASSWD);
            System.out.println("[OK] CONEXÃO ESTABELECIDA.");
        } catch (Exception ex)
        {
            System.out.println("[ERRO] NA CONEXÃO " + ex);
            System.exit(404);
        }

        System.out.println("ok");
    }

    public void encerrarConexao()
    {
        try
        {
            conexao.close();
            System.out.println("[OK] CONEXÃO ENCERRADA.");
        } catch (SQLException ex)
        {
            System.out.println("[ERRO] AO ENCERRAR A CONEXÃO " + ex);
            System.exit(404);
        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Statements">
    public ResultSet criarStatement(String query)
    {
        try
        {
            Statement stmt = conexao.createStatement();

            ResultSet teste = stmt.executeQuery(query);

            return stmt.executeQuery(query);
        } catch (SQLException ex)
        {
            System.out.println("[ERRO] AO EXECUTAR QUERY " + ex);
        }

        return null;
    }

    public int criarPreparedStatement(String query, int quantidadeParametros, String[] listaParamentros)
    {
        try
        {
            PreparedStatement stmt = conexao.prepareStatement(query);

            for (int i = 0; i < quantidadeParametros; i++)
            {
                stmt.setString(i + 1, listaParamentros[i]);
            }
            
            System.out.println(stmt);

            return stmt.executeUpdate();
        } catch (SQLException ex)
        {
            System.out.println("[ERRO] AO EXECUTAR QUERY " + ex);
        }

        return 0;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Querys Jogo">
    //<editor-fold defaultstate="collapsed" desc="Querys Usuario">
    public Usuario buscarUsuario(String email, String senha, int controleProcessamento) throws SQLException //parenteses vazio - alternativas arraylist<Alternativa>buscarAlternativas
    {
        Usuario usuario = new Usuario();
        /*
            Controle de processamento indica de onde foi feita a chamada do método
            1 = significa que chamou do usuario cadastrado
            2 = significa que chamou do criar usuario 
            Os numeros são definidos de acordo com o menu
        */
        
        String query = "select * "
                + "from usuario "
                + "where email = '" + email + "'"; //quary sem parametro select *from alternativas 

        ResultSet res = criarStatement(query);
        
        if (res.next())
        {
            usuario = usuario.preencherUsuario(res.getInt("COD_USUARIO"),
                                     res.getString("NOME_USUARIO"),
                                     res.getString("SOBRENOME_USUARIO"),
                                     res.getString("EMAIL"),
                                     res.getString("APELIDO_USUARIO"),
                                     res.getString("SENHA"),
                                     res.getBoolean("IND_ATIVO"),
                                     res.getInt("COD_PERSONAGEM"));

            validarSeSenhaEstaCorreta(senha, res,controleProcessamento,usuario);
        }else
        {
            usuario.setEmail(email);
        }
        return usuario;
    }
    
    public void validarSeSenhaEstaCorreta(String senha,ResultSet res,int controleProcessamento, Usuario usuario) throws SQLException
    {
        int tentativas = 0;
        do 
        {
            if (controleProcessamento == 2 && tentativas == 0)
            {
                System.out.println("Encontramos uma conta cadastrada com o email informado");
                System.out.println("Digite a senha ou SAIR a qualquer momento para voltar ao menu inicial");
                senha = in.next();
            }
            
            if (tentativas > 0)
            {
                System.out.println("Senha incorreta! Digite novamente");
                senha = in.next();
            }
            tentativas ++;
        }while (!senha.equals(res.getString("SENHA")) && !senha.equals("SAIR"));
        
        if (senha.equals("SAIR"))
        {
            usuario.setCodigoUsuario(-1);
        }
    }
    
    public int inserirUsuario(Usuario usuario)
    {
        String query = "INSERT INTO usuario "
                + "(NOME_USUARIO, SOBRENOME_USUARIO, EMAIL,APELIDO_USUARIO, SENHA, COD_PERSONAGEM)"
                + " VALUES (?,?,?,?,?,?)";

       try
       {
            PreparedStatement stmt = conexao.prepareStatement(query);

            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getNomeUsuario());
            stmt.setString(3, usuario.getNomeUsuario());
            stmt.setString(4, usuario.getNomeUsuario());
            stmt.setString(5, usuario.getNomeUsuario());
            stmt.setInt(6, usuario.getPersonagem().getIdPersonagem());
            
            return stmt.executeUpdate();
            
            
        } catch (SQLException ex)
        {
            System.out.println("[ERRO] AO EXECUTAR QUERY " + ex);
        }

        return 0;

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Querys Personagem">
    public Personagem buscarAvatarDoUsuario(Usuario jogador) throws SQLException
    {
        ArrayList<Habilidade> habilidades = new ArrayList<Habilidade>();
        Personagem personagem = new Personagem();
        
        String query = "SELECT P.COD_PERSONAGEM, P.NOME_PERSONAGEM, P.TEMPO_VIDA, P. IND_ESCOLHIDO, H.COD_HABILIDADE,H.DESC_HABILIDADE "
                + "from personagem P "
                + "join usuario U on U.COD_PERSONAGEM = P.COD_PERSONAGEM "
                + "JOIN habilidade H ON p.cod_personagem = h.PERSONAGEM_COD_PERSONAGEM "
                + "where U.COD_USUARIO = " + jogador.getCodigoUsuario();

        ResultSet res = criarStatement(query);
        
        while(res.next())
        {
            if(!res.isLast()){

                habilidades.add(new Habilidade(
                                res.getInt("COD_HABILIDADE"),
                                res.getString("DESC_HABILIDADE"),
                                false
                ));
            } else
            {   
                habilidades.add(new Habilidade(
                                res.getInt("COD_HABILIDADE"),
                                res.getString("DESC_HABILIDADE"),
                                false
                ));
                personagem = new Personagem(res.getInt("COD_PERSONAGEM"),
                                                       res.getString("NOME_PERSONAGEM"),
                                                       habilidades,
                                                       res.getFloat("TEMPO_VIDA"),
                                                       res.getBoolean("IND_ESCOLHIDO"));                
            }
        }
                
        return personagem;
    }
    
    public Personagem buscarHabilidadesPersonagem(Usuario jogador) throws SQLException
    {
        ArrayList<Habilidade> habilidades = new ArrayList<Habilidade>();
        Personagem personagem = new Personagem();
        String query = "SELECT P.COD_PERSONAGEM, P.NOME_PERSONAGEM, P.TEMPO_VIDA, P. IND_ESCOLHIDO, H.COD_HABILIDADE,H.DESC_HABILIDADE "
                + " from habilidade h "
                + "join personagem p on p.COD_PERSONAGEM = h.PERSONAGEM_COD_PERSONAGEM  "
                + "where p.COD_PERSONAGEM = " + jogador.getPersonagem().getIdPersonagem();

        ResultSet res = criarStatement(query);
        
        while(res.next())
        {
            if(!res.isLast()){

                habilidades.add(new Habilidade(
                                res.getInt("COD_HABILIDADE"),
                                res.getString("DESC_HABILIDADE"),
                                false
                ));
            } else
            {   
                habilidades.add(new Habilidade(
                                res.getInt("COD_HABILIDADE"),
                                res.getString("DESC_HABILIDADE"),
                                false
                ));
                personagem.setHabilidades(habilidades);                
            }
        }
                
        return personagem;
    }
    
    public ArrayList<Personagem> buscarPersonagens() throws SQLException
    {
        ArrayList<Personagem> personagens = new ArrayList<Personagem>();
        
        String query = "select * "
                     + "from personagem";
        
         ResultSet res = criarStatement(query);
         
         while (res.next())
        {
            personagens.add(new Personagem(
                             res.getInt("COD_PERSONAGEM"),
                             res.getString("NOME_PERSONAGEM"),
                             res.getFloat("TEMPO_VIDA"),
                             res.getBoolean("IND_ESCOLHIDO")
                             
            ));
        }    
         
        return personagens;
    }
    
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Querys Perguntas e Alternativas">
    public ArrayList<Alternativa> buscarAlternativas() throws SQLException
    {
      
        ArrayList<Alternativa> alternativas = new ArrayList<Alternativa>();
        
        String query = "select r.*, pr.COD_PERGUNTA "
                     + "from resposta r "
                     + "join pergunta_resposta pr on pr.COD_RESPOSTA = r.COD_RESPOSTA "
                     + "order by pr.COD_PERGUNTA;";
                
        ResultSet res = criarStatement(query);
                
        while (res.next())
        {
            alternativas.add(new Alternativa(
                             res.getInt("COD_RESPOSTA"),
                             res.getInt("COD_PERGUNTA"),
                             res.getString("RESPOSTA")
            ));
        }    
        return alternativas;
    }
        
    public ArrayList<Pergunta> buscarPerguntas() throws SQLException
    {
        ArrayList<Pergunta> perguntas = new ArrayList<Pergunta>();
        
        String query = "select * "
                     + "from pergunta";
        
         ResultSet res = criarStatement(query);
         
         while (res.next())
        {
            perguntas.add(new Pergunta(
                             res.getInt("COD_PERGUNTA"),
                             res.getString("PERGUNTA")
            ));
        }    
         
         return perguntas;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Querys Gerais">
    //</editor-fold>
    //</editor-fold>
    
    
}
