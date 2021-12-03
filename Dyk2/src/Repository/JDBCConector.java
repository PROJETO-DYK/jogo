package Repository;

import Util.Habilidade;
import Util.Personagem;
import Util.Usuario;
import java.sql.*;
import java.util.ArrayList;

public class JDBCConector
{

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

    public ResultSet criarPreparedStatement(String query, int quantidadeParamentros, String[] listaParamentros)
    {
        try
        {
            PreparedStatement stmt = conexao.prepareStatement(query, listaParamentros);

            for (int i = 0; i < quantidadeParamentros; i++)
            {
                stmt.setString(i + 1, listaParamentros[i]);
            }

            return (ResultSet) stmt.executeQuery(query);
        } catch (SQLException ex)
        {
            System.out.println("[ERRO] AO EXECUTAR QUERY " + ex);
        }

        return null;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Querys Jogo">
    //<editor-fold defaultstate="collapsed" desc="Querys Usuario">
    public Usuario buscarUsuario(String email, String senha) throws SQLException
    {

        String query = "select * "
                + "from usuario "
                + "where email = '" + email
                + "' and senha = '" + senha + "'";

        ResultSet res = criarStatement(query);

        res.next();
        Usuario usuario = new Usuario(res.getInt("COD_USUARIO"),
                res.getString("NOME_USUARIO"),
                res.getString("SOBRENOME_USUARIO"),
                res.getString("EMAIL"),
                res.getString("APELIDO_USUARIO"),
                res.getString("SENHA"),
                res.getBoolean("IND_ATIVO"));
        return usuario;
    }
    
    public Personagem buscarAvatarDoUsuario(Usuario jogador) throws SQLException
    {
        ArrayList<Habilidade> habilidades = new ArrayList<Habilidade>();
        Personagem personagem = new Personagem();
        
        String query = "SELECT P.COD_PERSONAGEM, P.NOME_PERSONAGEM, P.TEMPO_VIDA, P. IND_ESCOLHIDO, H.COD_HABILIDADE,H.DESC_HABILIDADE "
                + "from personagem P "
                + "JOIN habilidade_personagem HP ON HP.COD_PERSONAGEM = P.COD_PERSONAGEM "
                + "JOIN habilidade H ON H.COD_HABILIDADE = HP.COD_HABILIDADE "
                + "where USUARIO_COD_USUARIO = " + jogador.getCodigoUsuario();

        ResultSet res = criarStatement(query);
        
        while(res.next())
        {
            if(!res.isLast()){

                habilidades.add(new Habilidade(
                                res.getInt("COD_HABILIDADE"),
                                res.getString("DESC_HABILIDADE")
                ));
            } else
            {   
                habilidades.add(new Habilidade(
                                res.getInt("COD_HABILIDADE"),
                                res.getString("DESC_HABILIDADE")
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

    public void criarUsuario(Usuario usuario)
    {
        String query = "INSERT INTO usuario\n "
                + "(NOME_USUARIO, SOBRENOME_USUARIO, EMAIL,APELIDO_USUARIO, SENHA)\n"
                + "VALUES (?,?,?,?,?,?)";

        String args[] =
        {
            usuario.getNomeUsuario(), 
            usuario.getSobrenomeUsuario(), 
            usuario.getEmail(), 
            usuario.getApelido(), 
            usuario.getSenha()
        };

        criarPreparedStatement(query, args.length, args);

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Querys Perguntas e Alternativas">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Querys Personagem">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Querys Gerais">
    //</editor-fold>
    //</editor-fold>
    
    
}
