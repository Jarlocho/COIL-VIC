/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Modela el objeto para su uso dentro del programa
 * Versión: 1.0
 */

package proyectocoil.modelo.pojo;


public class Estudiante {
    Integer idEstudiante;
    String nombre;
    String matricula;
    String correo;

    public Estudiante(Integer idEstudiante, String nombre, String matricula, String correo) {
        this.idEstudiante = idEstudiante;
        this.nombre = nombre;
        this.matricula = matricula;
        this.correo = correo;
    }

    public Estudiante() {
    }

    public Integer getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
}
