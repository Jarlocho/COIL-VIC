/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Modela el objeto para su uso dentro del programa
 * Versión: 1.0
 */

package proyectocoil.modelo.pojo;


public class OfertaColabExt {
    private Integer idOfertaColabExt;
    String tema;
    String fechaInicio;
    String fechaTermino;
    Integer idIdioma;
    Integer idProfesorExt;
    String nombreIdioma;
    String nombreProfesorExt;

    public OfertaColabExt(Integer idOfertaColabExt, String tema, String fechaInicio, String fechaTermino, Integer idIdioma, Integer idProfesorExt) {
        this.idOfertaColabExt = idOfertaColabExt;
        this.tema = tema;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.idIdioma = idIdioma;
        this.idProfesorExt = idProfesorExt;
    }

    public OfertaColabExt(Integer idOfertaColabExt, String tema, String fechaInicio, String fechaTermino, Integer idIdioma, Integer idProfesorExt, String nombreIdioma, String nombreProfesorExt) {
        this.idOfertaColabExt = idOfertaColabExt;
        this.tema = tema;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.idIdioma = idIdioma;
        this.idProfesorExt = idProfesorExt;
        this.nombreIdioma = nombreIdioma;
        this.nombreProfesorExt = nombreProfesorExt;
    }

    public String getNombreIdioma() {
        return nombreIdioma;
    }

    public void setNombreIdioma(String nombreIdioma) {
        this.nombreIdioma = nombreIdioma;
    }

    public String getNombreProfesorExt() {
        return nombreProfesorExt;
    }

    public void setNombreProfesorExt(String nombreProfesorExt) {
        this.nombreProfesorExt = nombreProfesorExt;
    }

    public OfertaColabExt() {
    }
    
    public Integer getIdProfesorExt() {
        return idProfesorExt;
    }

    public void setIdProfesorExt(Integer idProfesorExt) {
        this.idProfesorExt = idProfesorExt;
    }

    public Integer getIdOfertaColabExt() {
        return idOfertaColabExt;
    }

    public void setIdOfertaColabExt(Integer idOfertaColabExt) {
        this.idOfertaColabExt = idOfertaColabExt;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
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

    public Integer getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(Integer idIdioma) {
        this.idIdioma = idIdioma;
    }
    
}
