/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 13/06/2024
 * Descripción: Recupera los profesores externos de la base de datos para su manipulación
 * Versión: 1.0
 */

package proyectocoil.modelo.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import proyectocoil.modelo.ConexionBD;
import proyectocoil.modelo.pojo.ProfesorExt;
import proyectocoil.utilidades.Constantes;

public class ProfesorExtDAO {
    public static HashMap<String, Object> obtenerProfesoresExt() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {                
                String consulta = "SELECT idProfesorExt, nombre, correo, titulo, Universidad_idUniversidad from profesorExt";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<ProfesorExt> profesoresExt = new ArrayList();
                while(resultado.next()){
                    ProfesorExt profesorExt = new ProfesorExt();
                    profesorExt.setIdProfesorExt(resultado.getInt("idProfesorExt"));
                    profesorExt.setNombre(resultado.getString("nombre"));
                    profesorExt.setCorreo(resultado.getString("correo"));
                    profesorExt.setTitulo(resultado.getString("titulo"));
                    profesorExt.setIdUniversidad(resultado.getInt("Universidad_idUniversidad"));
                    profesoresExt.add(profesorExt);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("profesoresExt", profesoresExt);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } else{
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;  
    }

    public static HashMap<String, Object> registrarProfesorExt(ProfesorExt profesorExt, Integer idUniversidad) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        if (profesorExt == null) {
            respuesta.put(Constantes.KEY_MENSAJE, "El profesor externo es nulo.");
            return respuesta;
        }
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "INSERT INTO profesorExt (nombre, correo, titulo, Universidad_idUniversidad) VALUES (?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, profesorExt.getNombre());
                prepararSentencia.setString(2, profesorExt.getCorreo());
                prepararSentencia.setString(3, profesorExt.getTitulo());
                prepararSentencia.setInt(4, profesorExt.getIdUniversidad());
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Profesor externo registrado exitosamente.");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, ocurrió un error al registrar el profesor.");
                }
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;
    }

    public static HashMap<String, Object> modificarProfesorExt(ProfesorExt profesorExt) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        if (profesorExt == null) {
            respuesta.put(Constantes.KEY_MENSAJE, "El profesor externo es nulo.");
            return respuesta;
        }
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "UPDATE profesorExt SET nombre = ?, correo = ?, titulo = ? WHERE idProfesorExt = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, profesorExt.getNombre());
                prepararSentencia.setString(2, profesorExt.getCorreo());
                prepararSentencia.setString(3, profesorExt.getTitulo());
                prepararSentencia.setInt(4, profesorExt.getIdProfesorExt());
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Profesor externo modificado exitosamente.");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, ocurrió un error al modificar el profesor.");
                }
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;
    }

    public static HashMap<String, Object> asociarIdiomaProfesor(Integer idProfesorExt, Integer idIdioma) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "INSERT INTO idioma_has_profesorext (Idioma_idIdioma, ProfesorExt_idProfesorExt) VALUES (?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idIdioma);
                prepararSentencia.setInt(2, idProfesorExt);
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, ocurrió un error al asociar los idiomas.");
                }
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;
    }
}