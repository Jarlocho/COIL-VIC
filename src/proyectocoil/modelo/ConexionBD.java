/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Genera la conexión con la base de datos
 * Versión: 1.0
 */

package proyectocoil.modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import proyectocoil.utilidades.Constantes;

public class ConexionBD {
    public static final String URI_CONEXION = "jdbc:mysql://"+Constantes.HOSTNAME+":"+Constantes.PUERTO+"/"+Constantes.NOMBRE_BD+"?allowPublicKeyRetrieval=true&useSSL=false";

    public static Connection obtenerConexion() {
        Connection conexionBD = null;
        try {
            Class.forName(Constantes.DRIVER);
            conexionBD = DriverManager.getConnection(URI_CONEXION, Constantes.USUARIO, Constantes.PASSWORD);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException sl) {
            sl.printStackTrace();
        }
        return conexionBD;
    }
}