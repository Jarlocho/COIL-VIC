/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 13/06/2024
 * Descripción: Recupera las ofertas de colaboración externas de la base de datos para su manipulación
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
import proyectocoil.modelo.pojo.OfertaColabExt;
import proyectocoil.utilidades.Constantes;
public class OfertaColabExtDAO {
    public static HashMap<String, Object> obtenerOfertasColabExt() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {                
                String consulta = "SELECT oe.idOfertaColabExt, oe.tema, i.idIdioma AS Idioma_idIdioma, i.nombre AS nombreIdioma, DATE_FORMAT(oe.fechaInicio, '%d/%m/%Y') AS 'fechaInicio', DATE_FORMAT(oe.fechaTermino, '%d/%m/%Y') AS 'fechaTermino', pe.idProfesorExt AS ProfesorExt_idProfesorExt, pe.nombre AS nombreProfesorExt FROM ofertaColabExt oe JOIN Idioma i ON oe.Idioma_idIdioma = i.idIdioma JOIN ProfesorExt pe ON oe.ProfesorExt_idProfesorExt = pe.idProfesorExt LEFT JOIN colaboracion c ON oe.idOfertaColabExt = c.OfertaColabExt_idOfertaColabExt WHERE c.OfertaColabExt_idOfertaColabExt IS NULL;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<OfertaColabExt> ofertasColabExt = new ArrayList();
                while(resultado.next()){
                    OfertaColabExt ofertaColabExt = new OfertaColabExt();
                    ofertaColabExt.setIdOfertaColabExt(resultado.getInt("idOfertaColabExt"));
                    ofertaColabExt.setTema(resultado.getString("tema"));
                    ofertaColabExt.setIdIdioma(resultado.getInt("Idioma_idIdioma"));
                    ofertaColabExt.setFechaInicio(resultado.getString("fechaInicio"));
                    ofertaColabExt.setFechaTermino(resultado.getString("fechaTermino"));
                    ofertaColabExt.setIdProfesorExt(resultado.getInt("ProfesorExt_idProfesorExt"));
                    ofertaColabExt.setNombreIdioma(resultado.getString("nombreIdioma"));
                    ofertaColabExt.setNombreProfesorExt(resultado.getString("nombreProfesorExt"));
                    ofertasColabExt.add(ofertaColabExt);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("ofertasExt", ofertasColabExt);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } else{
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;  
    }

    public static HashMap<String, Object> eliminarOferta(Integer idOfertaColabExt) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE from ofertacolabext WHERE idOfertaColabext = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idOfertaColabExt);
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Oferta eliminada correctamente");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, hubo un error al eliminar la oferta.");
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
    
    public static HashMap<String, Object> modificarProfesor(Integer idOfertaColabExt, Integer idProfesorExt) {
    HashMap<String, Object> respuesta = new HashMap<>();
    respuesta.put(Constantes.KEY_ERROR, true); 
    Connection conexionBD = ConexionBD.obtenerConexion();
    if (conexionBD != null) {
        try {
            String sentencia = "UPDATE ofertacolabext SET ProfesorExt_idProfesorExt = ? WHERE idOfertaColabExt = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setInt(1, idProfesorExt);
            prepararSentencia.setInt(2, idOfertaColabExt);
            int filasAfectadas = prepararSentencia.executeUpdate();
            if (filasAfectadas > 0) {
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put(Constantes.KEY_MENSAJE, "Profesor de la oferta modificado correctamente");
            } else {
                respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, hubo un error al modificar el profesor de la oferta.");
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
    
    public static HashMap<String, Object> modificarOferta(OfertaColabExt oferta, Integer idOfertaColabExt) {
    HashMap<String, Object> respuesta = new HashMap<>();
    respuesta.put(Constantes.KEY_ERROR, true); // Inicializa la respuesta por defecto como error

    if (oferta == null) {
        respuesta.put(Constantes.KEY_MENSAJE, "La oferta proporcionada es nula");
        return respuesta;
    }

    Connection conexionBD = ConexionBD.obtenerConexion();

    if (conexionBD != null) {
        try {
            String sentencia = "UPDATE ofertacolabext SET tema = ?, Idioma_idIdioma = ?, fechaInicio = ?, fechaTermino = ?  WHERE idOfertaColabExt = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setString(1, oferta.getTema());
            prepararSentencia.setInt(2, oferta.getIdIdioma());
            prepararSentencia.setString(3, oferta.getFechaInicio());
            prepararSentencia.setString(4, oferta.getFechaTermino());
            prepararSentencia.setInt(5, idOfertaColabExt); 
            int filasAfectadas = prepararSentencia.executeUpdate();

            if (filasAfectadas > 0) {
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put(Constantes.KEY_MENSAJE, "Información de la oferta modificada correctamente");
            } else {
                respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, hubo un error al modificar la información de la oferta, por favor revisa la información.");
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
    public static HashMap<String, Object> registrarOfertaColabExt(OfertaColabExt ofertaExt, Integer idProfesorExt) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        if (ofertaExt == null) {
            respuesta.put(Constantes.KEY_MENSAJE, "La oferta de colaboración externa es nula.");
            return respuesta;
        }
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "INSERT INTO ofertaColabExt (tema, Idioma_idIdioma, fechaInicio, fechaTermino, ProfesorExt_idProfesorExt) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, ofertaExt.getTema());
                prepararSentencia.setInt(2, ofertaExt.getIdIdioma());
                prepararSentencia.setString(3, ofertaExt.getFechaInicio());
                prepararSentencia.setString(4, ofertaExt.getFechaTermino());
                prepararSentencia.setInt(5, ofertaExt.getIdProfesorExt());
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Oferta de colaboración externa registrada exitosamente.");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, ocurrió un error al registrar la oferta.");
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
    
    public static HashMap<String, Object> obtenerTemasOfertasExt() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            try {
                String consulta = " SELECT oc.tema, i.nombre AS idioma, oc.fechaInicio, oc.fechaTermino from ofertacolabext oc JOIN idioma i ON oc.Idioma_idIdioma = i.idIdioma";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();

                ArrayList<OfertaColabExt> ofertasext = new ArrayList();
                while (resultado.next()) {
                    OfertaColabExt oferta = new OfertaColabExt();
                    oferta.setTema(resultado.getString("tema"));
                    oferta.setNombreIdioma(resultado.getString("idioma"));
                    oferta.setFechaInicio(resultado.getString("fechaInicio"));
                    oferta.setFechaTermino(resultado.getString("fechaTermino"));
                    ofertasext.add(oferta);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("ofertasext", ofertasext);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());

            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;
    }  
}