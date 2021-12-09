package Repository;

import Util.Alternativa;
import Util.Habilidade;
import Util.Personagem;
import Util.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import Util.Pergunta;
import Util.Ranking;
import java.util.List;

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

            return stmt.executeQuery(query);
        } catch (SQLException ex)
        {
            System.out.println("[ERRO] AO EXECUTAR QUERY " + ex);
        }

        return null;
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
                + "from usuario u "
                + "join ranking r on u.COD_USUARIO = r.COD_USUARIO "
                + "where u.email = ?";

        PreparedStatement stmt = conexao.prepareStatement(query);

        stmt.setString(1, email);

        ResultSet res = stmt.executeQuery();
        
        System.out.println(stmt);

        if (res.next())
        {
            usuario = new Usuario (res.getInt("COD_USUARIO"),
                    res.getString("NOME_USUARIO"),
                    res.getString("SOBRENOME_USUARIO"),
                    res.getString("EMAIL"),
                    res.getString("APELIDO_USUARIO"),
                    res.getString("SENHA"),
                    res.getBoolean("IND_ATIVO"),
                    0,
                    new Ranking(res.getInt("PONTUACAO")),
                    new Personagem (res.getInt("COD_PERSONAGEM"))
                    );

            validarSeSenhaEstaCorreta(senha, res, controleProcessamento, usuario);
        }else
        {
            usuario.setEmail(email);
        }
        return usuario;
    }
    
     public Usuario buscarRanking(Usuario usuario) throws SQLException
    {
        String query = "select u.COD_USUARIO,r.PONTUACAO "
                + "from usuario u "
                + "join ranking r on u.COD_USUARIO = r.COD_USUARIO "
                + "where u.email = ?";
        
        PreparedStatement stmt = conexao.prepareStatement(query);

        stmt.setString(1, usuario.getEmail());

        ResultSet res = stmt.executeQuery();
        
        res.next();
        
        usuario.setCodigoUsuario(res.getInt(1));
        
        Ranking score = new Ranking (res.getInt(2));
        
        usuario.setScore(score);
        
        return usuario;
    }

    public void validarSeSenhaEstaCorreta(String senha, ResultSet res, int controleProcessamento, Usuario usuario) throws SQLException
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
            tentativas++;
        } while (!senha.equals(res.getString("SENHA")) && !senha.equals("SAIR"));

        if (senha.equals("SAIR"))
        {
            usuario.setCodigoUsuario(-1);
        }
    }

    public void inserirUsuario(Usuario usuario)
    {
        String query = "INSERT INTO usuario "
                + "(NOME_USUARIO, SOBRENOME_USUARIO, EMAIL,APELIDO_USUARIO, SENHA, COD_PERSONAGEM)"
                + " VALUES (?,?,?,?,?,?)";

        try
        {
            PreparedStatement stmt = conexao.prepareStatement(query);

            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getSobrenomeUsuario());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getApelido());
            stmt.setString(5, usuario.getSenha());
            stmt.setInt(6, usuario.getPersonagem().getIdPersonagem());

            stmt.executeUpdate();

        } catch (SQLException ex)
        {
            System.out.println("[ERRO] AO EXECUTAR QUERY " + ex);
        }
    }

    public int salvarScore(int score, int codigoUsuario)
    {
        String query = "update ranking "
                + "set pontuacao = ? "
                + "where cod_usuario = ?";

        try
        {
            PreparedStatement stmt = conexao.prepareStatement(query);

            stmt.setInt(1, score);
            stmt.setInt(2, codigoUsuario);

            return stmt.executeUpdate();

        } catch (SQLException ex)
        {
            System.out.println("[ERRO] AO EXECUTAR QUERY " + ex);
        }
        return 0;
    }
    
    public ArrayList<Ranking> buscarScore () throws SQLException
    {
        ArrayList<Ranking> rankings = new ArrayList<>();
        
        String query = "select * "
                + "from Ranking r "
                + "join usuario u on u.COD_USUARIO = r.COD_USUARIO "
                + "order by r.PONTUACAO desc, u.NOME_USUARIO";
        
         PreparedStatement stmt = conexao.prepareStatement(query);

         ResultSet res = stmt.executeQuery();
         
         while (res.next())
        {
            rankings.add(new Ranking(res.getInt("PONTUACAO"),
                                     res.getInt("COD_USUARIO"),
                                     res.getString("NOME_USUARIO")));
        }            
        return rankings;
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
                + "JOIN habilidade H ON p.cod_personagem = h.COD_PERSONAGEM "
                + "where U.COD_USUARIO = " + jogador.getCodigoUsuario();

        ResultSet res = criarStatement(query);

        while (res.next())
        {
            if (!res.isLast())
            {

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

    public Personagem buscarHabilidadesPersonagem(Personagem personagem) throws SQLException
    {
        ArrayList<Habilidade> habilidades = new ArrayList<Habilidade>();
        String query = "SELECT P.COD_PERSONAGEM, P.NOME_PERSONAGEM, P.TEMPO_VIDA, P. IND_ESCOLHIDO, H.COD_HABILIDADE,H.DESC_HABILIDADE "
                + " from habilidade h "
                + "join personagem p on p.COD_PERSONAGEM = h.COD_PERSONAGEM  "
                + "where p.COD_PERSONAGEM = " + personagem.getIdPersonagem();

        ResultSet res = criarStatement(query);

        while (res.next())
        {
            if (!res.isLast())
            {

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

        String query = "select r.*, pr.COD_PERGUNTA,pr.IND_RESPOSTA_CORRETA "
                + "from resposta r "
                + "join pergunta_resposta pr on pr.COD_RESPOSTA = r.COD_RESPOSTA "
                + "order by pr.COD_PERGUNTA;";

        ResultSet res = criarStatement(query);

        while (res.next())
        {
            alternativas.add(new Alternativa(
                    res.getInt("COD_RESPOSTA"),
                    res.getInt("COD_PERGUNTA"),
                    res.getString("RESPOSTA"),
                    res.getBoolean("IND_RESPOSTA_CORRETA")
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
    
    public ArrayList<Pergunta> buscarCodigoPerguntas(ArrayList <Pergunta> perguntas) throws SQLException
    {
        String query = "select pergunta "
                + "from pergunta  "
                + "where pergunta in (?)";

        PreparedStatement stmt = conexao.prepareStatement(query);

        int i=0;
        for(Pergunta pergunta : perguntas)
        {
            stmt.setString(i += 1, pergunta.getPergunta());
        }
        ResultSet res = stmt.executeQuery();
        
        while (res.next())
        {
            for(Pergunta pergunta : perguntas)
            {
                for (Alternativa alternativa : pergunta.getAlternativas())
                {
                    if (alternativa.getResposta().equals(res.getString("PERGUNTA")))
                    {
                        alternativa.setCodigoResposta(res.getInt("COD_PERGUNTA"));
                    }
                }
            }
        }

        return perguntas;
    }
    
    public ArrayList<Pergunta> buscarCodigoRespostas(ArrayList <Pergunta> perguntas) throws SQLException
    {
        String query = "select RESPOSTA "
                + "from resposta  "
                + "where resposta in (?)";

        PreparedStatement stmt = conexao.prepareStatement(query);

        int i=0;
        for(Pergunta pergunta : perguntas)
        {
            for (Alternativa alternativa : pergunta.getAlternativas())
            {
                stmt.setString(i += 1, alternativa.getResposta());
            }
        }
        ResultSet res = stmt.executeQuery();
        
        while (res.next())
        {
            for(Pergunta pergunta : perguntas)
            {
                for (Alternativa alternativa : pergunta.getAlternativas())
                {
                    if (alternativa.getResposta().equals(res.getString("Resposta")))
                    {
                        alternativa.setCodigoResposta(res.getInt("COD_RESPOSTA"));
                    }
                }
            }
        }

        return perguntas;
    }
        
    public void salvarPergunta(ArrayList<Pergunta> perguntas) throws SQLException
    {
      
    
        String query = "insert into pergunta "
                + "(PERGUNTA) VALUES ";
        for(Pergunta pergunta : perguntas)
        {
            if(perguntas.lastIndexOf(pergunta) == perguntas.size()-1)
                query += "(?)";
             else
                query += "(?),";
        }
            System.out.println(query); //todo

        try
        {
            PreparedStatement stmt = conexao.prepareStatement(query);
            
            int i = 1;
            for(Pergunta pergunta : perguntas)
            {
                stmt.setString(i, pergunta.getPergunta());
                i++;
            }
            
            System.out.println(stmt); //todo
            
            stmt.executeUpdate();

        } catch (SQLException ex)
        {
            System.out.println("[ERRO] AO EXECUTAR QUERY " + ex);
        }
    }
    
    public void salvarAlternativa(ArrayList<Alternativa> alternativas) throws SQLException
    {
    
        String query = "insert into resposta "
                    + "(resposta) VALUES ";
        for(Alternativa alternativa : alternativas)
        {
            if(alternativas.lastIndexOf(alternativa) ==alternativas.size()-1)
                query += "(?)";
             else
                query += "(?), ";
        }
            System.out.println(query);    

        try
        {
            PreparedStatement stmt = conexao.prepareStatement(query);
            
            int i = 1;
            for(Alternativa alternativa : alternativas)
            {
                stmt.setString(i, alternativa.getResposta());
                i++;
            }
            
            System.out.println(stmt);
            
            stmt.executeUpdate();

        } catch (SQLException ex)
        {
            System.out.println("[ERRO] AO EXECUTAR QUERY " + ex);
        }
    }
    
    public void salvarRelacionamentoPerguntaResposta(ArrayList<Pergunta> perguntas) throws SQLException
    {
        String query = "insert into pergunta_resposta "
                + "((COD_PERGUNTA,COD_RESPOSTA,IND_RESPOSTA_CORRETA)) VALUES ";
        for(Pergunta pergunta : perguntas)
        {
            for (Alternativa alternativa : pergunta.getAlternativas())
            {
                if(pergunta.getAlternativas().lastIndexOf(alternativa) == pergunta.getAlternativas().size()-1)
                    query += "(?,?,?)";
                 else
                    query += "(?,?,?), ";
            }
        }
            System.out.println(query);    

        try
        {
            PreparedStatement stmt = conexao.prepareStatement(query);
            
            int i = 1;
            int j = 2;
            int k = 3;
            
            for(Pergunta pergunta : perguntas)
            {
                for (Alternativa alternativa : pergunta.getAlternativas())
                if(perguntas.lastIndexOf(pergunta) == 0)
                {
                    stmt.setInt(i, alternativa.getCodigoPergunta());
                    stmt.setInt(j, alternativa.getCodigoResposta());
                    stmt.setBoolean(k, alternativa.isCorreta());
                }else
                {
                    stmt.setInt(i += 2, alternativa.getCodigoPergunta());
                    stmt.setInt(j += 2, alternativa.getCodigoResposta());
                    stmt.setBoolean(k =+ 2, alternativa.isCorreta());
                }
            }
            
            System.out.println(stmt);
            
            stmt.executeUpdate();

        } catch (SQLException ex)
        {
            System.out.println("[ERRO] AO EXECUTAR QUERY " + ex);
        }
    }

    
    //</editor-fold>
    //</editor-fold>

}
