/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 23/06/2024
 * Descripción: Recupera los profesores de la colaboración
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
import proyectocoil.modelo.pojo.ProfesorUV;
import proyectocoil.modelo.pojo.ProfesorExt;
import proyectocoil.utilidades.Constantes;

public class ProfesorDAO {
    public static HashMap<String, Object> obtenerProfesoresPorColaboracion(int idColaboracion) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT c.nombreColab AS Nombre_Colaboracion, pUv.nombre AS Nombre_Profesor_Uv, pUv.correo AS Correo_Profesor_Uv, pUv.titulo AS Titulo_Profesor_Uv, pExt.nombre AS Nombre_Profesor_Ext, pExt.correo AS Correo_Profesor_Ext, pExt.titulo AS Titulo_Profesor_Ext FROM colaboracion c LEFT JOIN profesoruv pUv ON c.ProfesorUv_idProfesorUv = pUv.idProfesorUv LEFT JOIN profesorext pExt ON c.ProfesorExt_idProfesorExt = pExt.idProfesorExt WHERE c.idColaboracion = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idColaboracion);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<ProfesorUV> profesoresUV = new ArrayList<>();
                ArrayList<ProfesorExt> profesoresExt = new ArrayList<>();
                while (resultado.next()) {
                    String nombreProfesorUV = resultado.getString("Nombre_Profesor_Uv");
                    if (nombreProfesorUV != null) {
                        ProfesorUV profesorUV = new ProfesorUV();
                        profesorUV.setNombre(nombreProfesorUV);
                        profesorUV.setCorreo(resultado.getString("Correo_Profesor_Uv"));
                        profesorUV.setTitulo(resultado.getString("Titulo_Profesor_Uv"));
                        profesoresUV.add(profesorUV);
                    }
                    String nombreProfesorExt = resultado.getString("Nombre_Profesor_Ext");
                    if (nombreProfesorExt != null) {
                        ProfesorExt profesorExt = new ProfesorExt();
                        profesorExt.setNombre(nombreProfesorExt);
                        profesorExt.setCorreo(resultado.getString("Correo_Profesor_Ext"));
                        profesorExt.setTitulo(resultado.getString("Titulo_Profesor_Ext"));
                        profesoresExt.add(profesorExt);
                    }
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("profesoresUV", profesoresUV);
                respuesta.put("profesoresExt", profesoresExt);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;
    }

    public static HashMap<String, Object> actualizarColaboracionConProfesorNuevo(int idColaboracion, int idProfesor, boolean esProfesorUV) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = esProfesorUV ? "UPDATE colaboracion SET ProfesorUv_idProfesorUv = ? WHERE idColaboracion = ?" : "UPDATE colaboracion SET ProfesorExt_idProfesorExt = ? WHERE idColaboracion = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idProfesor);
                prepararSentencia.setInt(2, idColaboracion);
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Colaboración actualizada exitosamente.");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, ocurrió un error al actualizar la colaboración.");
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