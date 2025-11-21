/*
 * Autor: Jarly Hernández Romero
 * Fecha de Creación: 21/06/2024
 * Descripción: Recupera los profesores y alumnos que participaron en colaboraciones en el periodo actual
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
import proyectocoil.modelo.pojo.Numeralia;
import proyectocoil.utilidades.Constantes;

public class NumeraliaDAO {
    public static HashMap<String, Object> obtenerNumeralia(){
    HashMap<String, Object> respuesta = new LinkedHashMap<>();
    respuesta.put(Constantes.KEY_ERROR, true);
    Connection conexionBD = ConexionBD.obtenerConexion();
    ArrayList<Numeralia> datosNumeralia = new ArrayList<>();
        int totalAlumnos = 0;
        int totalProfesores = 0;
    if (conexionBD != null){
        try {
            String consulta = "SELECT aa.nombre AS areaAcademica, COALESCE(COUNT(DISTINCT pu.idProfesorUv), 0) AS numProfesores, COALESCE(COUNT(DISTINCT e.idEstudiante), 0) AS numEstudiantes FROM areaacademica aa LEFT JOIN dependencia d ON aa.idAreaAcademica = d.AreaAcademica_idAreaAcademica LEFT JOIN programaeducativo pe ON d.idDependencia = pe.Dependencia_idDependencia LEFT JOIN experienciaeducativa ee ON pe.idProgramaEducativo = ee.ProgramaEducativo_idProgramaEducativo LEFT JOIN colaboracion c ON ee.idExperienciaEducativa = c.ExperienciaEducativa_idExperienciaEducativa AND (c.fechaInicio BETWEEN '2024-02-10' AND '2024-06-04' OR c.fechaTermino BETWEEN '2024-02-10' AND '2024-06-04') AND c.estado = 'Concluida' LEFT JOIN profesoruv pu ON c.ProfesorUv_idProfesorUv = pu.idProfesorUv LEFT JOIN asociacolabestudiante ace ON c.idColaboracion = ace.Colaboracion_idColaboracion LEFT JOIN estudiante e ON ace.Estudiante_idEstudiante = e.idEstudiante GROUP BY aa.nombre ORDER BY aa.nombre";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            ResultSet resultado = prepararSentencia.executeQuery();
            while(resultado.next()){
                   Numeralia numeralia = new Numeralia();
                    numeralia.setAreaAcademica(resultado.getString("AreaAcademica"));
                    numeralia.setAlumnos(resultado.getInt("numEstudiantes"));
                    numeralia.setProfesores(resultado.getInt("numProfesores"));
                    totalAlumnos += resultado.getInt("numEstudiantes");
                    totalProfesores += resultado.getInt("numProfesores");
                    datosNumeralia.add(numeralia);

            }
           Numeralia filaTotal = new Numeralia();
                filaTotal.setAreaAcademica("Total");
                filaTotal.setAlumnos(totalAlumnos);
                filaTotal.setProfesores(totalProfesores);
                datosNumeralia.add(filaTotal);
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("numeralia", datosNumeralia);
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