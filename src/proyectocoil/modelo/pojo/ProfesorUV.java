/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Modela el objeto para su uso dentro del programa
 * Versión: 1.0
 */

package proyectocoil.modelo.pojo;


public class ProfesorUV {
    Integer idProfesor;
    String nombre;
    String correo;
    String titulo;
    String numTelefono;
    String numPersonal;

    public ProfesorUV(Integer idProfesor, String nombre, String correo, String titulo, String numTelefono, String numPersonal) {
        this.idProfesor = idProfesor;
        this.nombre = nombre;
        this.correo = correo;
        this.titulo = titulo;
        this.numTelefono = numTelefono;
        this.numPersonal = numPersonal;
    }

    public ProfesorUV() {
    }

    public Integer getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Integer idProfesor) {
        this.idProfesor = idProfesor;
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

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public String getNumPersonal() {
        return numPersonal;
    }

    public void setNumPersonal(String numPersonal) {
        this.numPersonal = numPersonal;
    }
}