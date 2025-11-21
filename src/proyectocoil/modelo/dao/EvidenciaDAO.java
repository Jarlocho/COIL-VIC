/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 13/06/2024
 * Descripción: Recupera las evidencias
 * Versión: 1.0
 */

package proyectocoil.modelo.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import proyectocoil.modelo.ConexionBD;
import proyectocoil.modelo.pojo.Evidencia;
import proyectocoil.utilidades.Constantes;


public class EvidenciaDAO {
    public static HashMap<String, Object> GuardarEvidencia(Evidencia evidencia, Integer idColaboracion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD!= null) {
            try {
                
                String sentencia = "INSERT INTO evidencia (nombreArchivo, descripcion, archivo)"
                         + " values (?,?,?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, evidencia.getNombreArchivo());
                prepararSentencia.setString(2, evidencia.getDescripcion());
                prepararSentencia.setBytes(3, evidencia.getArchivo());
                
                int filasAfectadas = prepararSentencia.executeUpdate();
                if(filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Se guardó la evidencia correctamente");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Error al guardar la evidencia");
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
    
    public static HashMap<String, Object> ObtenerIdUltimaEvidencia() {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD!= null) {
            try {
                String consulta = "SELECT idEvidencia FROM Evidencia ORDER BY idEvidencia DESC LIMIT 1";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) {
                    Integer idUltimaEvidencia = resultado.getInt("idEvidencia");
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put("idEvidencia", idUltimaEvidencia);
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "No se encontró esa evidencia");
                }
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } else{
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;  
    }
    
}
