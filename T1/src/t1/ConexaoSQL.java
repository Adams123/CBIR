/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author pri
 */
public class ConexaoSQL {
    Connection connection = null;
    
    public Connection connect(String username, String pass)
    {
        try
        {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e)
        {

            System.err.println("[ERRO]\tDriver não encontrado.");
            e.printStackTrace();
            return null;
        }
        //Connection connection = null;
        try
        {

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/trabalho", username,
                    pass);

        } catch (SQLException e)
        {

            System.err.println("[ERRO]\tFalha na conexão!");
            e.printStackTrace();
            return null;

        }

        if (connection != null)
        {
            System.out.println("[INFO]\tConexão com a Base de Dados EFETUADA com sucesso.");
            return connection;
        } else
        {
            System.err.println("[ERRO]\tFalha ao estabelecer conexão!");
        }
        return null;
    }
    
    public void disconnect() {
        try {
            connection.close();
            System.out.println("[INFO]\tConexão com a Base de Dados ENCERRADA com sucesso.");
        } catch (SQLException ex) {
            System.err.println("[ERRO]\tErro ao fechar a conexão.");
        }
    }
    
    public ResultSet querySQL(String command) {

        ResultSet rs = null;
        try {
            Statement st = connection.createStatement();
            rs = st.executeQuery(command);
            
        } catch (SQLException ex) {
            //Logger.getLogger(T1.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[ERRO]\tErro na consulta.");
        }        
        return rs;
        
    } 
}