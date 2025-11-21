/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 13/06/2024
 * Descripción: Recupera los idiomas de la base de datos
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
import proyectocoil.modelo.pojo.Idioma;
import proyectocoil.utilidades.Constantes;

public class IdiomaDAO {
    public static HashMap<String, Object> obtenerIdiomas() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null) {
            try {                
                String consulta = "SELECT idIdioma, nombre from Idioma";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Idioma> idiomas = new ArrayList();
                while(resultado.next()) {
                    Idioma idioma = new Idioma();
                    idioma.setIdIdioma(resultado.getInt("idIdioma"));
                    idioma.setNombre(resultado.getString("nombre"));
                    idiomas.add(idioma);
                }
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("idiomas", idiomas);
                conexionBD.close();
            } catch(SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
        }
        return respuesta;  
    }
}