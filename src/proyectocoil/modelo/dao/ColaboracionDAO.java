/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 13/06/2024
 * Descripción: Recupera las colaboraciones
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
import proyectocoil.modelo.pojo.Colaboracion;
import proyectocoil.utilidades.Constantes;


public class ColaboracionDAO {
    
    public static HashMap<String, Object> ObtenerIdUltimaColaboracion() {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD!= null) {
            try {
                String consulta = "SELECT idcolaboracion FROM Colaboracion ORDER BY idColaboracion DESC LIMIT 1";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) {
                    Integer idUltimaColaboracion = resultado.getInt("idColaboracion");
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put("idColaboracion", idUltimaColaboracion);
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "No se encontró esa colab");
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
    
    public static HashMap<String, Object> ObtenerColaboracionesAdministrables(Integer idProfesorUV) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {                
                if (idProfesorUV != null) {
                    String consulta = "SELECT c.idColaboracion, c.nombreColab, c.estado, DATE_FORMAT(c.fechaInicio, '%d/%m/%Y') AS 'fechaInicio', DATE_FORMAT(c.fechaTermino, '%d/%m/%Y') AS 'fechaTermino', c.ProfesorUv_idProfesorUv AS idProfesorUv, c.ProfesorExt_idProfesorExt AS idProfesorExt, c.ExperienciaEducativa_idExperienciaEducativa AS idExperienciaEducativa, pUv.nombre AS nombreProfesorUv, pExt.nombre AS nombreProfesorExt, ee.nombre AS nombreExperienciaEducativa FROM colaboracion c JOIN ProfesorUv pUv ON c.ProfesorUv_idProfesorUv = pUv.idProfesorUv JOIN ProfesorExt pExt ON c.ProfesorExt_idProfesorExt = pExt.idProfesorExt JOIN ExperienciaEducativa ee ON c.ExperienciaEducativa_idExperienciaEducativa = ee.idExperienciaEducativa WHERE c.estado IN ('Activa', 'Acordada') AND c.ProfesorUv_idProfesorUv = ?";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                    prepararSentencia.setInt(1, idProfesorUV);
                    ResultSet resultado = prepararSentencia.executeQuery();
                    ArrayList<Colaboracion> colaboraciones = new ArrayList();
                    while(resultado.next()){
                        Colaboracion colaboracion = new Colaboracion();
                        colaboracion.setIdColaboracion(resultado.getInt("idColaboracion"));
                        colaboracion.setNombreColab(resultado.getString("nombreColab"));
                        colaboracion.setEstado(resultado.getString("estado"));
                        colaboracion.setFechaInicio(resultado.getString("fechaInicio"));
                        colaboracion.setFechaTermino(resultado.getString("fechaTermino"));
                        colaboracion.setIdProfesorUV(resultado.getInt("idProfesorUV"));
                        colaboracion.setIdProfesorExt(resultado.getInt("idProfesorExt"));
                        colaboracion.setNombreProfesorUV(resultado.getString("nombreProfesorUV"));
                        colaboracion.setNombreProfesorExt(resultado.getString("nombreProfesorExt"));
                        colaboracion.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                        colaboracion.setNombreExperienciaEducativa(resultado.getString("nombreExperienciaEducativa"));
                        
                        
                        colaboraciones.add(colaboracion);
                    }
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put("colaboraciones", colaboraciones);
                    conexionBD.close();
                } else {
                    String consulta = "SELECT c.idColaboracion, c.nombreColab, c.estado, DATE_FORMAT(c.fechaInicio, '%d/%m/%Y') AS 'fechaInicio', DATE_FORMAT(c.fechaTermino, '%d/%m/%Y') AS 'fechaTermino', c.ProfesorUv_idProfesorUv AS idProfesorUv, c.ProfesorExt_idProfesorExt AS idProfesorExt, c.ExperienciaEducativa_idExperienciaEducativa AS idExperienciaEducativa, pUv.nombre AS nombreProfesorUv, pExt.nombre AS nombreProfesorExt, ee.nombre AS nombreExperienciaEducativa FROM colaboracion c JOIN ProfesorUv pUv ON c.ProfesorUv_idProfesorUv = pUv.idProfesorUv JOIN ProfesorExt pExt ON c.ProfesorExt_idProfesorExt = pExt.idProfesorExt JOIN ExperienciaEducativa ee ON c.ExperienciaEducativa_idExperienciaEducativa = ee.idExperienciaEducativa WHERE c.estado IN ('Activa', 'Acordada')";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                    ResultSet resultado = prepararSentencia.executeQuery();
                    ArrayList<Colaboracion> colaboraciones = new ArrayList();
                    while(resultado.next()){
                        Colaboracion colaboracion = new Colaboracion();
                        colaboracion.setIdColaboracion(resultado.getInt("idColaboracion"));
                        colaboracion.setNombreColab(resultado.getString("nombreColab"));
                        colaboracion.setEstado(resultado.getString("estado"));
                        colaboracion.setFechaInicio(resultado.getString("fechaInicio"));
                        colaboracion.setFechaTermino(resultado.getString("fechaTermino"));
                        colaboracion.setIdProfesorUV(resultado.getInt("idProfesorUV"));
                        colaboracion.setIdProfesorExt(resultado.getInt("idProfesorExt"));
                        colaboracion.setNombreProfesorUV(resultado.getString("nombreProfesorUV"));
                        colaboracion.setNombreProfesorExt(resultado.getString("nombreProfesorExt"));
                        colaboracion.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                        colaboracion.setNombreExperienciaEducativa(resultado.getString("nombreExperienciaEducativa"));
                        

                        colaboraciones.add(colaboracion);
                    }
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put("colaboraciones", colaboraciones);
                    conexionBD.close();
                }
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } else{
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;  
    }
    
    public static HashMap<String, Object> ObtenerColaboracionesConcluidas() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {                
                String consulta = "SELECT c.idColaboracion, c.nombreColab, c.estado, DATE_FORMAT(c.fechaInicio, '%d/%m/%Y') AS 'fechaInicio', DATE_FORMAT(c.fechaTermino, '%d/%m/%Y') AS 'fechaTermino', c.ProfesorUv_idProfesorUv AS idProfesorUv, c.ProfesorExt_idProfesorExt AS idProfesorExt, c.ExperienciaEducativa_idExperienciaEducativa AS idExperienciaEducativa, pUv.nombre AS nombreProfesorUv, pExt.nombre AS nombreProfesorExt, ee.nombre AS nombreExperienciaEducativa FROM colaboracion c JOIN ProfesorUv pUv ON c.ProfesorUv_idProfesorUv = pUv.idProfesorUv JOIN ProfesorExt pExt ON c.ProfesorExt_idProfesorExt = pExt.idProfesorExt JOIN ExperienciaEducativa ee ON c.ExperienciaEducativa_idExperienciaEducativa = ee.idExperienciaEducativa WHERE c.estado = 'Concluida' AND c.Evidencia_idEvidencia IS NULL";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Colaboracion> colaboraciones = new ArrayList();
                while(resultado.next()){
                    Colaboracion colaboracion = new Colaboracion();
                    colaboracion.setIdColaboracion(resultado.getInt("idColaboracion"));
                    colaboracion.setNombreColab(resultado.getString("nombreColab"));
                    colaboracion.setEstado(resultado.getString("estado"));
                    colaboracion.setFechaInicio(resultado.getString("fechaInicio"));
                    colaboracion.setFechaTermino(resultado.getString("fechaTermino"));
                    colaboracion.setIdProfesorUV(resultado.getInt("idProfesorUV"));
                    colaboracion.setIdProfesorExt(resultado.getInt("idProfesorExt"));
                    colaboracion.setNombreProfesorUV(resultado.getString("nombreProfesorUV"));
                    colaboracion.setNombreProfesorExt(resultado.getString("nombreProfesorExt"));
                    colaboracion.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                    colaboracion.setNombreExperienciaEducativa(resultado.getString("nombreExperienciaEducativa"));
                    
                    
                    colaboraciones.add(colaboracion);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("colaboraciones", colaboraciones);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } else{
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;
    }

    public static HashMap<String, Object> guardarColaboracion(Colaboracion colaboracion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD!= null) {
            try {
                int filasAfectadas;
                if (colaboracion.getIdOfertaColabUV() != null) {
                    String sentencia = "INSERT INTO Colaboracion (nombreColab, estado, fechaInicio, fechaTermino, planDeActividades, ExperienciaEducativa_idExperienciaEducativa, ProfesorUV_idProfesorUV, ProfesorExt_idProfesorExt, OfertaColabUV_idOfertaColabUV) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                    prepararSentencia.setString(1, colaboracion.getNombreColab());
                    prepararSentencia.setString(2, colaboracion.getEstado());
                    prepararSentencia.setString(3, colaboracion.getFechaInicio());
                    prepararSentencia.setString(4, colaboracion.getFechaTermino());
                    prepararSentencia.setBytes(5, colaboracion.getPlanDeActividades());
                    prepararSentencia.setInt(6, colaboracion.getIdExperienciaEducativa());
                    prepararSentencia.setInt(7, colaboracion.getIdProfesorUV());
                    prepararSentencia.setInt(8, colaboracion.getIdProfesorExt());
                    prepararSentencia.setInt(9, colaboracion.getIdOfertaColabUV());
                    filasAfectadas = prepararSentencia.executeUpdate();
                } else if (colaboracion.getIdOfertaColabExt() != null) {
                    String sentencia = "INSERT INTO Colaboracion (nombreColab, estado, fechaInicio, fechaTermino, planDeActividades, ExperienciaEducativa_idExperienciaEducativa, ProfesorUV_idProfesorUV, ProfesorExt_idProfesorExt, OfertaColabExt_idOfertaColabExt) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                    prepararSentencia.setString(1, colaboracion.getNombreColab());
                    prepararSentencia.setString(2, colaboracion.getEstado());
                    prepararSentencia.setString(3, colaboracion.getFechaInicio());
                    prepararSentencia.setString(4, colaboracion.getFechaTermino());
                    prepararSentencia.setBytes(5, colaboracion.getPlanDeActividades());
                    prepararSentencia.setInt(6, colaboracion.getIdExperienciaEducativa());
                    prepararSentencia.setInt(7, colaboracion.getIdProfesorUV());
                    prepararSentencia.setInt(8, colaboracion.getIdProfesorExt());
                    prepararSentencia.setInt(9, colaboracion.getIdOfertaColabExt());
                    filasAfectadas = prepararSentencia.executeUpdate();
                } else {
                    String sentencia = "INSERT INTO Colaboracion (nombreColab, estado, fechaInicio, fechaTermino, planDeActividades, ExperienciaEducativa_idExperienciaEducativa, ProfesorUV_idProfesorUV, ProfesorExt_idProfesorExt) "
                    + "VALUES (?,?,?,?,?,?,?,?)";
                    PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                    prepararSentencia.setString(1, colaboracion.getNombreColab());
                    prepararSentencia.setString(2, colaboracion.getEstado());
                    prepararSentencia.setString(3, colaboracion.getFechaInicio());
                    prepararSentencia.setString(4, colaboracion.getFechaTermino());
                    prepararSentencia.setBytes(5, colaboracion.getPlanDeActividades());
                    prepararSentencia.setInt(6, colaboracion.getIdExperienciaEducativa());
                    prepararSentencia.setInt(7, colaboracion.getIdProfesorUV());
                    prepararSentencia.setInt(8, colaboracion.getIdProfesorExt());
                    filasAfectadas = prepararSentencia.executeUpdate();
                }
                if(filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Información del paciente guardada correctamente");
                    
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Error al guardar la informacion del paciente, favor de revisar la información");
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
    
    public static HashMap<String, Object> subirEvidenciaColaboracion(Integer idEvidencia, Integer idColaboracion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null){
            try {
                String sentencia = "UPDATE colaboracion SET Evidencia_idEvidencia = ? WHERE idColaboracion = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, idEvidencia);
                prepararSentencia.setInt(2, idColaboracion);
                
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "La evidencia de la colaboración ha sido actualizado correctamente.");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, hubo un error al actualizar "
                            + "la evidencia de la colaboración, revise sus datos.");
                }
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> actualizarEstadoColaboracion(String estado, Integer idColaboracion) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null){
            try {
                String sentencia = "UPDATE colaboracion SET estado = ? WHERE idColaboracion = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, estado);
                prepararSentencia.setInt(2, idColaboracion);
                
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "El estado de la colaboración ha sido actualizado correctamente.");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, hubo un error al actualizar "
                            + "el estado de la colaboración, revise sus datos.");
                }
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        }
        return respuesta;
    }
    
    public static HashMap<String, Object> obtenerPlanDeActividades(int idColaboracion) {
    HashMap<String, Object> respuesta = new LinkedHashMap<>();
    respuesta.put(Constantes.KEY_ERROR, true);
    Connection conexionBD = ConexionBD.obtenerConexion();

    if (conexionBD != null) {
        try {
            String consulta = "SELECT planDeActividades FROM colaboracion WHERE idColaboracion = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            prepararSentencia.setInt(1, idColaboracion);
            ResultSet resultado = prepararSentencia.executeQuery();

            if (resultado.next()) {
                byte[] planDeActividades = resultado.getBytes("planDeActividades");
                respuesta.put("planDeActividades", planDeActividades);
                respuesta.put(Constantes.KEY_ERROR, false);
            } else {
                respuesta.put(Constantes.KEY_MENSAJE, "No se encontró la colaboración");
            }

            conexionBD.close();
        } catch (SQLException e) {
            e.printStackTrace();
            respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
        }
    } else {
        respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
    }

    return respuesta;
    }
    
     public static HashMap<String, Object> obtenerColaboracionesTerminadas() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            try {
                String consulta = "SELECT colaboracion.idColaboracion, colaboracion.nombreColab, colaboracion.fechaInicio, colaboracion.fechaTermino, experienciaeducativa.nombre AS experienciaEducativa, colaboracion.estado, colaboracion.planDeActividades FROM colaboracion JOIN experienciaeducativa ON colaboracion.ExperienciaEducativa_idExperienciaEducativa = experienciaeducativa.idExperienciaEducativa WHERE colaboracion.estado IN ('Cancelada', 'Concluida')";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();

                ArrayList<Colaboracion> colaboraciones = new ArrayList();
                while (resultado.next()) {
                    Colaboracion colaboracion = new Colaboracion();
                    colaboracion.setIdColaboracion(resultado.getInt("idColaboracion"));
                    colaboracion.setNombreColab(resultado.getString("nombreColab"));
                    colaboracion.setFechaInicio(resultado.getString("fechaInicio"));
                    colaboracion.setFechaTermino(resultado.getString("fechaTermino"));
                    colaboracion.setNombreExperienciaEducativa(resultado.getString("experienciaEducativa"));
                    colaboracion.setEstado(resultado.getString("estado"));
                    colaboracion.setPlanDeActividades(resultado.getBytes("planDeActividades"));
                    colaboraciones.add(colaboracion);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("colaboraciones", colaboraciones);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());

            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;
    }
    public static HashMap<String, Object> ObtenerColaboraciones(Integer idProfesorUV) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {                
                String consulta;
                if (idProfesorUV != null) {
                    consulta = "SELECT c.idColaboracion, c.nombreColab, c.estado, DATE_FORMAT(c.fechaInicio, '%d/%m/%Y') AS 'fechaInicio', DATE_FORMAT(c.fechaTermino, '%d/%m/%Y') AS 'fechaTermino', c.ProfesorUv_idProfesorUv AS idProfesorUv, c.ProfesorExt_idProfesorExt AS idProfesorExt, c.ExperienciaEducativa_idExperienciaEducativa AS idExperienciaEducativa, pUv.nombre AS nombreProfesorUv, pExt.nombre AS nombreProfesorExt, ee.nombre AS nombreExperienciaEducativa FROM colaboracion c JOIN ProfesorUv pUv ON c.ProfesorUv_idProfesorUv = pUv.idProfesorUv JOIN ProfesorExt pExt ON c.ProfesorExt_idProfesorExt = pExt.idProfesorExt JOIN ExperienciaEducativa ee ON c.ExperienciaEducativa_idExperienciaEducativa = ee.idExperienciaEducativa WHERE c.estado IN ('Activa', 'Acordada') AND c.ProfesorUv_idProfesorUv = ?";
                } else {
                    consulta = "SELECT c.idColaboracion, c.nombreColab, c.estado, DATE_FORMAT(c.fechaInicio, '%d/%m/%Y') AS 'fechaInicio', DATE_FORMAT(c.fechaTermino, '%d/%m/%Y') AS 'fechaTermino', c.ProfesorUv_idProfesorUv AS idProfesorUv, c.ProfesorExt_idProfesorExt AS idProfesorExt, c.ExperienciaEducativa_idExperienciaEducativa AS idExperienciaEducativa, pUv.nombre AS nombreProfesorUv, pExt.nombre AS nombreProfesorExt, ee.nombre AS nombreExperienciaEducativa FROM colaboracion c JOIN ProfesorUv pUv ON c.ProfesorUv_idProfesorUv = pUv.idProfesorUv JOIN ProfesorExt pExt ON c.ProfesorExt_idProfesorExt = pExt.idProfesorExt JOIN ExperienciaEducativa ee ON c.ExperienciaEducativa_idExperienciaEducativa = ee.idExperienciaEducativa WHERE c.estado IN ('Activa', 'Acordada')";
                }
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                if (idProfesorUV != null) {
                    prepararSentencia.setInt(1, idProfesorUV);
                }
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Colaboracion> colaboraciones = new ArrayList();
                while(resultado.next()){
                    Colaboracion colaboracion = new Colaboracion();
                    colaboracion.setIdColaboracion(resultado.getInt("idColaboracion"));
                    colaboracion.setNombreColab(resultado.getString("nombreColab"));
                    colaboracion.setEstado(resultado.getString("estado"));
                    colaboracion.setFechaInicio(resultado.getString("fechaInicio"));
                    colaboracion.setFechaTermino(resultado.getString("fechaTermino"));
                    colaboracion.setIdProfesorUV(resultado.getInt("idProfesorUv"));
                    colaboracion.setIdProfesorExt(resultado.getInt("idProfesorExt"));
                    colaboracion.setNombreProfesorUV(resultado.getString("nombreProfesorUv"));
                    colaboracion.setNombreProfesorExt(resultado.getString("nombreProfesorExt"));
                    colaboracion.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                    colaboracion.setNombreExperienciaEducativa(resultado.getString("nombreExperienciaEducativa"));
                    colaboraciones.add(colaboracion);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("colaboraciones", colaboraciones);
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