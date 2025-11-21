/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 13/06/2024
 * Descripción: Recupera las credenciales del usuario para verificar su identidad
 * Versión: 1.0
 */

package proyectocoil.modelo.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import proyectocoil.modelo.ConexionBD;
import proyectocoil.modelo.pojo.RespuestaLogin;
import proyectocoil.modelo.pojo.Usuario;
import proyectocoil.utilidades.Constantes;

public class AutenticacionDAO {
    public static RespuestaLogin iniciarSesionUsuario(String numPersonal, String contrasenia) {
        RespuestaLogin respuesta = new RespuestaLogin();
        Connection conexionBD = ConexionBD.obtenerConexion();
        respuesta.setError(true);
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idUsuario, nombreUsuario, contrasenia, ProfesorUv_idProfesorUv as idProfesorUV FROM Usuario WHERE nombreUsuario = ? AND contrasenia = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, numPersonal);
                prepararSentencia.setString(2, contrasenia);
                ResultSet resultadoSentencia = prepararSentencia.executeQuery();
                if(resultadoSentencia.next()) {
                    respuesta.setError(false);
                    respuesta.setMensaje("Usuario identificado correctamente");
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(resultadoSentencia.getInt("idUsuario"));
                    usuario.setNombreUsuario(resultadoSentencia.getString("nombreUsuario"));
                    Integer llaveForanea = resultadoSentencia.getInt("idProfesorUV");
                    if (!resultadoSentencia.wasNull()) {
                        usuario.setIdProfesorUV(resultadoSentencia.getInt("idProfesorUV"));
                    }
                    respuesta.setUsuario(usuario);
                    respuesta.setError(false);
                } else {
                    respuesta.setMensaje("Nombre de usuario y/o contraseña incorrectos.");
                }
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setMensaje(e.getMessage());
            }
        } else {
            respuesta.setMensaje(Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;
    }
}