package dykjogo;

import java.sql.*;
/**
 *
 * @author thiago.f.santos
 */
public class JDBCConector {
   private final String URL = "jdbc:mysql://localhost:3306/";
   private final String USER = "root";
   private final String PASSWD = "Gafs1999@";
   private final String DRIVER_BANCO = "com.mysql.cj.jdbc.Driver";
   
   private Connection conexao;
   
   public void inciarConexao()
   {
       try {
           Class.forName(DRIVER_BANCO);
           conexao = DriverManager.getConnection(URL, USER, PASSWD);
           System.out.println("[OK] CONEXÃO ESTABELECIDA.");
       } catch (Exception ex) {
           System.out.println("[ERRO] NA CONEXÃO " + ex);
           System.exit(404);
       }
       
       System.out.println("ok");       
   }
   public void encerrarConexao()
   {
       try {
           conexao.close();
           System.out.println("[OK] CONEXÃO ENCERRADA.");
       } catch (SQLException ex) {
           System.out.println("[ERRO] AO ENCERRAR A CONEXÃO "+ ex);
           System.exit(404);
       }
   }
    
}
