/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 13/06/2024
 * Descripción: Recupera los estudiantes y en que colaboraciones están
 * Versión: 1.0
 */

package proyectocoil.modelo.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.collections.ObservableList;
import proyectocoil.modelo.ConexionBD;
import proyectocoil.modelo.pojo.Colaboracion;
import proyectocoil.modelo.pojo.Estudiante;
import proyectocoil.utilidades.Constantes;


public class AsociaColabEstudianteDAO {
    
    public static HashMap<String, Object> GuardarEstudiantesDeColaboracion(Colaboracion colaboracion, ObservableList<Estudiante> listaEstudiantes) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD!= null) {
            try {
                for (int i = 0; i < listaEstudiantes.size(); i++) {
                    Estudiante estudiante = listaEstudiantes.get(i);
                    String sentencia = "INSERT INTO asociaColabEstudiante (Colaboracion_idColaboracion, Estudiante_idEstudiante)"
                         + " values (?,?)";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                    prepararSentencia.setInt(1, colaboracion.getIdColaboracion());
                    prepararSentencia.setInt(2, estudiante.getIdEstudiante());
                    int filasAfectadas = prepararSentencia.executeUpdate();
                    if(filasAfectadas > 0) {
                        respuesta.put(Constantes.KEY_ERROR, false);
                        respuesta.put(Constantes.KEY_MENSAJE, "Se guardaron los estudiantes de la colaboración correctamente");
                    } else {
                        respuesta.put(Constantes.KEY_MENSAJE, "Error al asociar a los estudiantes con la colaboración");
                        break;
                    }
                }
                conexionBD.close();
            } catch (SQLException ex) {
                respuesta.put(Constantes.KEY_MENSAJE, ex.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
    return respuesta;
    }
    
    public static HashMap<String, Object> EliminarEstudiantesDeColaboracion(Colaboracion colaboracion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD!= null) {
            try {
                String consulta = "DELETE FROM AsociaColabEstudiante WHERE Colaboracion_idColaboracion = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, colaboracion.getIdColaboracion());
                int filasAfectadas = prepararSentencia.executeUpdate();
                if(filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Estudiantes eliminados correctamente");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Error al eliminar los estudiantes, favor de revisar la información");
                }
                conexionBD.close();
            } catch (SQLException ex) {
                respuesta.put(Constantes.KEY_MENSAJE, ex.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
    return respuesta;
    }
}
