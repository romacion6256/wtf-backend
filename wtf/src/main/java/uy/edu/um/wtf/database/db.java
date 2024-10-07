package uy.edu.um.wtf.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db {

    // Configura tu URL de la base de datos y las credenciales de acceso
    private static final String URL = "jdbc:postgresql://34.95.128.190:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "grupo6";

    // Método para establecer la conexión
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }

    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                System.out.println("Conexión exitosa a la base de datos");
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar a la base de datos");
        }
    }

}
