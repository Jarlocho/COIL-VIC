/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Modela el objeto para su uso dentro del programa
 * Versión: 1.0
 */

package proyectocoil.modelo.pojo;
import java.util.List;

public class ProfesorExt {
    Integer idProfesorExt;
    String nombre;
    String correo;
    String titulo;
    Integer idUniversidad;
    List<Idioma> idiomas;

    public ProfesorExt(Integer idProfesorExt, String nombre, String correo, String titulo, Integer idUniversidad) {
        this.idProfesorExt = idProfesorExt;
        this.nombre = nombre;
        this.correo = correo;
        this.titulo = titulo;
        this.idUniversidad = idUniversidad;
    }

    public ProfesorExt() {
    }

    public Integer getIdProfesorExt() {
        return idProfesorExt;
    }

    public void setIdProfesorExt(Integer idProfesorExt) {
        this.idProfesorExt = idProfesorExt;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getIdUniversidad() {
        return idUniversidad;
    }

    public void setIdUniversidad(Integer idUniversidad) {
        this.idUniversidad = idUniversidad;
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        this.idiomas = idiomas;
    }
}