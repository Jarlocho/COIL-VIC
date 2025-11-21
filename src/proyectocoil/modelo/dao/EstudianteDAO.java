/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 13/06/2024
 * Descripción: Recupera los estudiantes
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
import proyectocoil.modelo.pojo.Estudiante;
import proyectocoil.utilidades.Constantes;


public class EstudianteDAO {
    
    public static HashMap<String, Object> obtenerEstudiantes() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {                
                String consulta = "Select idEstudiante, nombre, matricula, correo from estudiante";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Estudiante> estudiantes = new ArrayList();
                while(resultado.next()){
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdEstudiante(resultado.getInt("idEstudiante"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setCorreo(resultado.getString("correo"));
                    estudiantes.add(estudiante);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("estudiantes", estudiantes);
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
