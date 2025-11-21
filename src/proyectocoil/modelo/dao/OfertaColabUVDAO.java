/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 13/06/2024
 * Descripción: Recupera las ofertas de colaboración UV de la base de datos para su manipulación
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
import proyectocoil.modelo.pojo.OfertaColabUV;
import proyectocoil.modelo.pojo.ProfesorUV;
import proyectocoil.utilidades.Constantes;


public class OfertaColabUVDAO {
    public static HashMap<String, Object> obtenerOfertasColabUV() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {                
                String consulta = "SELECT oe.idOfertaColabUV, oe.tema, i.idIdioma AS Idioma_idIdioma, i.nombre AS nombreIdioma, DATE_FORMAT(oe.fechaInicio, '%d/%m/%Y') AS 'fechaInicio', DATE_FORMAT(oe.fechaTermino, '%d/%m/%Y') AS 'fechaTermino', p.idProfesorUV AS ProfesorUV_idProfesorUV, p.nombre AS nombreProfesorUV FROM ofertaColabUV oe JOIN Idioma i ON oe.Idioma_idIdioma = i.idIdioma JOIN ProfesorUV p ON oe.ProfesorUV_idProfesorUV = p.idProfesorUV LEFT JOIN colaboracion c ON oe.idOfertaColabUV = c.OfertaColabUv_idOfertaColabUv WHERE c.OfertaColabUv_idOfertaColabUv IS NULL;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<OfertaColabUV> ofertasColabUV = new ArrayList();
                while(resultado.next()){
                    OfertaColabUV ofertaColabUV = new OfertaColabUV();
                    ofertaColabUV.setIdOfertaColabUV(resultado.getInt("idOfertaColabUV"));
                    ofertaColabUV.setTema(resultado.getString("tema"));
                    ofertaColabUV.setIdIdioma(resultado.getInt("Idioma_idIdioma"));
                    ofertaColabUV.setFechaInicio(resultado.getString("fechaInicio"));
                    ofertaColabUV.setFechaTermino(resultado.getString("fechaTermino"));
                    ofertaColabUV.setIdProfesorUV(resultado.getInt("ProfesorUV_idProfesorUV"));
                    ofertaColabUV.setNombreIdioma(resultado.getString("nombreIdioma"));
                    ofertaColabUV.setNombreProfesorUV(resultado.getString("nombreProfesorUV"));
                    ofertasColabUV.add(ofertaColabUV);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("ofertasUV", ofertasColabUV);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } else{
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;  
    }
    
    public static HashMap<String, Object> modificarOferta(OfertaColabUV oferta, Integer idOfertaColabUV) {
    HashMap<String, Object> respuesta = new HashMap<>();
    respuesta.put(Constantes.KEY_ERROR, true); // Inicializa la respuesta por defecto como error

    if (oferta == null) {
        respuesta.put(Constantes.KEY_MENSAJE, "La oferta proporcionada es nula");
        return respuesta;
    }

    Connection conexionBD = ConexionBD.obtenerConexion();

    if (conexionBD != null) {
        try {
            String sentencia = "UPDATE ofertacolabuv SET tema = ?, Idioma_idIdioma = ?, fechaInicio = ?, fechaTermino = ?  WHERE idOfertaColabUv = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setString(1, oferta.getTema());
            prepararSentencia.setInt(2, oferta.getIdIdioma());
            prepararSentencia.setString(3, oferta.getFechaInicio());
            prepararSentencia.setString(4, oferta.getFechaTermino());
            prepararSentencia.setInt(5, idOfertaColabUV); 
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

    public static HashMap<String, Object> eliminarOfertas(Integer idOfertaColabUV) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String sentencia = "DELETE from ofertacolabuv WHERE idOfertaColabUv = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idOfertaColabUV);
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
     public static HashMap<String, Object> obtenerProfesoresUV() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            try {
                String consulta = "SELECT idProfesorUv, nombre, correo, numeroTelefono, titulo, numeroPersonal from profesoruv ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();

                ArrayList<ProfesorUV> profesoresuv = new ArrayList();
                while (resultado.next()) {
                    ProfesorUV profesor = new ProfesorUV();
                    profesor.setIdProfesor(resultado.getInt("idProfesorUv"));
                    profesor.setNombre(resultado.getString("nombre"));
                    profesor.setCorreo(resultado.getString("correo"));
                    profesor.setNumTelefono(resultado.getString("numeroTelefono"));
                    profesor.setTitulo(resultado.getString("titulo"));
                    profesoresuv.add(profesor);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("profesoresuv", profesoresuv);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());

            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;
    }  
    
     public static HashMap<String, Object> modificarProfesor(Integer idOfertaColabUV, Integer idProfesorUV) {
    HashMap<String, Object> respuesta = new HashMap<>();
    respuesta.put(Constantes.KEY_ERROR, true); 

    Connection conexionBD = ConexionBD.obtenerConexion();

    if (conexionBD != null) {
        try {
            String sentencia = "UPDATE ofertacolabuv SET ProfesorUv_idProfesorUv = ? WHERE idOfertaColabUv = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setInt(1, idProfesorUV);
            prepararSentencia.setInt(2, idOfertaColabUV);
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
    public static HashMap<String, Object> registrarOfertaColabUV(OfertaColabUV ofertaUV, Integer idProfesorUV) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        if (ofertaUV == null) {
            respuesta.put(Constantes.KEY_MENSAJE, "La oferta de colaboración UV es nula.");
            return respuesta;
        }
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "INSERT INTO ofertaColabUV (tema, Idioma_idIdioma, fechaInicio, fechaTermino, ProfesorUV_idProfesorUV) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, ofertaUV.getTema());
                prepararSentencia.setInt(2, ofertaUV.getIdIdioma());
                prepararSentencia.setString(3, ofertaUV.getFechaInicio());
                prepararSentencia.setString(4, ofertaUV.getFechaTermino());
                prepararSentencia.setInt(5, ofertaUV.getIdProfesorUV());
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Oferta de colaboración UV registrada exitosamente.");
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
    
     public static HashMap<String, Object> obtenerTemasOfertasUV() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            try {
                String consulta = "SELECT oc.tema, i.nombre AS idioma, oc.fechaInicio, oc.fechaTermino from ofertacolabuv oc JOIN idioma i ON oc.Idioma_idIdioma = i.idIdioma";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();

                ArrayList<OfertaColabUV> ofertasuv = new ArrayList();
                while (resultado.next()) {
                    OfertaColabUV oferta = new OfertaColabUV();
                    oferta.setTema(resultado.getString("tema"));
                    oferta.setNombreIdioma(resultado.getString("idioma"));
                    oferta.setFechaInicio(resultado.getString("fechaInicio"));
                    oferta.setFechaTermino(resultado.getString("fechaTermino"));
                    ofertasuv.add(oferta);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("ofertasuv", ofertasuv);
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