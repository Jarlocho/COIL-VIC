/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 13/06/2024
 * Descripción: Recupera los profesores UV de la base de datos
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
import proyectocoil.utilidades.Constantes;

public class ProfesorUVDAO {
    
    public static HashMap<String, Object> obtenerProfesoresUV() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {                
                String consulta = "SELECT idProfesorUV, nombre, correo, numeroTelefono, titulo, numeroPersonal from profesorUV";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<ProfesorUV> profesoresUV = new ArrayList();
                while(resultado.next()){
                    ProfesorUV profesorUV = new ProfesorUV();
                    profesorUV.setIdProfesor(resultado.getInt("idProfesorUV"));
                    profesorUV.setNombre(resultado.getString("nombre"));
                    profesorUV.setCorreo(resultado.getString("correo"));
                    profesorUV.setNumTelefono(resultado.getString("numeroTelefono"));
                    profesorUV.setTitulo(resultado.getString("titulo"));
                    profesoresUV.add(profesorUV);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("profesoresUV", profesoresUV);
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