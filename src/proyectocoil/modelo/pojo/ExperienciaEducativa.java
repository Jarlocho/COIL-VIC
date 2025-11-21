/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Modela el objeto para su uso dentro del programa
 * Versión: 1.0
 */

package proyectocoil.modelo.pojo;


public class ExperienciaEducativa {
    Integer idExperienciaEducativa;
    String nombre;
    String descripcion;
    Integer creditos;
    Integer nrc;
    String claveAcademica;
    String fechaInicio;
    String fechaTermino;
    Integer idProgramaEducativo;

    public ExperienciaEducativa(Integer idExperienciaEducativa, String nombre, String descripcion, Integer creditos, Integer nrc, String claveAcademica, String fechaInicio, String fechaTermino, Integer idProgramaEducativo) {
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creditos = creditos;
        this.nrc = nrc;
        this.claveAcademica = claveAcademica;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.idProgramaEducativo = idProgramaEducativo;
    }

    public ExperienciaEducativa() {
    }

    public Integer getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(Integer idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public Integer getNrc() {
        return nrc;
    }

    public void setNrc(Integer nrc) {
        this.nrc = nrc;
    }

    public String getClaveAcademica() {
        return claveAcademica;
    }

    public void setClaveAcademica(String claveAcademica) {
        this.claveAcademica = claveAcademica;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public Integer getIdProgramaEducativo() {
        return idProgramaEducativo;
    }

    public void setIdProgramaEducativo(Integer idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }
    
    @Override
    public String toString() {
        return this.nombre + " " + this.claveAcademica;
    }
}
