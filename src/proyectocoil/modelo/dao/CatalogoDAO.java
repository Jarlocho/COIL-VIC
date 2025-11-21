/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 13/06/2024
 * Descripción: Recupera las entidades académicas
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
import proyectocoil.modelo.pojo.AreaAcademica;
import proyectocoil.modelo.pojo.Dependencia;
import proyectocoil.modelo.pojo.ExperienciaEducativa;
import proyectocoil.modelo.pojo.ProgramaEducativo;
import proyectocoil.modelo.pojo.Universidad;
import proyectocoil.utilidades.Constantes;

public class CatalogoDAO {
    public static HashMap<String, Object> obtenerAreasAcademicas() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {                
                String consulta = "Select idAreaAcademica, nombre, clave from AreaAcademica";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<AreaAcademica> areasAcademicas = new ArrayList();
                while(resultado.next()){
                    AreaAcademica areaAcademica = new AreaAcademica();
                    areaAcademica.setIdAreaAcademica(resultado.getInt("idAreaAcademica"));
                    areaAcademica.setNombre(resultado.getString("nombre"));
                    areaAcademica.setClave(resultado.getString("clave"));
                    areasAcademicas.add(areaAcademica);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("areasAcademicas", areasAcademicas);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } else{
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;  
    }
    
    public static HashMap<String, Object> obtenerDependencias(Integer idAreaAcademica) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {                
                String consulta = "Select idDependencia, nombre, clave from Dependencia where AreaAcademica_idAreaAcademica = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1,idAreaAcademica);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Dependencia> dependencias = new ArrayList();
                while(resultado.next()){
                    Dependencia dependencia = new Dependencia();
                    dependencia.setIdAreaAcademica(idAreaAcademica);
                    dependencia.setNombre(resultado.getString("nombre"));
                    dependencia.setClave(resultado.getInt("clave"));
                    dependencia.setIdDependencia(resultado.getInt("idDependencia"));
                    dependencias.add(dependencia);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("dependencias", dependencias);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } else{
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;  
    }
    
    public static HashMap<String, Object> obtenerProgramasEducativos(Integer idDependencia) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {                
                String consulta = "Select idProgramaEducativo, nombre, clave from ProgramaEducativo where Dependencia_idDependencia = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1,idDependencia);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<ProgramaEducativo> programasEducativos = new ArrayList();
                while(resultado.next()){
                    ProgramaEducativo programaEducativo = new ProgramaEducativo();
                    programaEducativo.setIdDependencia(idDependencia);
                    programaEducativo.setNombre(resultado.getString("nombre"));
                    programaEducativo.setClave(resultado.getInt("clave"));
                    programaEducativo.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                    programasEducativos.add(programaEducativo);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("programasEducativos", programasEducativos);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } else{
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;  
    }
    
    public static HashMap<String, Object> obtenerExperienciasEducativas(Integer idProgramaEducativo) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {                
                String consulta = "Select idExperienciaEducativa, nombre, claveAcademica from ExperienciaEducativa where ProgramaEducativo_idProgramaEducativo = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idProgramaEducativo);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<ExperienciaEducativa> experienciasEducativas = new ArrayList();
                while(resultado.next()){
                    ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                    experienciaEducativa.setIdProgramaEducativo(idProgramaEducativo);
                    experienciaEducativa.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                    experienciaEducativa.setNombre(resultado.getString("nombre"));
                    experienciaEducativa.setClaveAcademica(resultado.getString("claveAcademica"));
                    experienciasEducativas.add(experienciaEducativa);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("experienciasEducativas", experienciasEducativas);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } else{
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;  
    }
    public static HashMap<String, Object> obtenerUniversidades() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idUniversidad, nombre, pais from Universidad";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Universidad> universidades = new ArrayList();
                while(resultado.next()){
                    Universidad universidad = new Universidad();
                    universidad.setIdUniversidad(resultado.getInt("idUniversidad"));
                    universidad.setNombre(resultado.getString("nombre"));
                    universidad.setPais(resultado.getString("pais"));
                    universidades.add(universidad);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("universidades", universidades);
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