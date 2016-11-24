package t1;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class T1
{

    public static void main(String[] argv)
    {
            gui janela = new gui();
            janela.setVisible(true);
    }

    public static void connect(String username, String pass)
    {
        try
        {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e)
        {

            System.out.println("Driver nao encontrado");
            e.printStackTrace();
            return;
        }
        Connection connection = null;
        try
        {

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/trabalho", username,
                    pass);

        } catch (SQLException e)
        {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }

        if (connection != null)
        {
            System.out.println("Conectado");
        } else
        {
            System.out.println("Failed to make connection!");
        }
    }

}
