/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Modela el objeto para su uso dentro del programa
 * Versión: 1.0
 */

package proyectocoil.modelo.pojo;
public class OfertaColabUV {
    Integer idOfertaColabUV;
    String tema;
    String fechaInicio;
    String fechaTermino;
    Integer idIdioma;
    private Integer idProfesorUV;
    String nombreIdioma;
    String nombreProfesorUV;

    public OfertaColabUV(Integer idOfertaColabUV, String tema, String fechaInicio, String fechaTermino, Integer idIdioma, Integer idProfesorUV, String nombreIdioma, String nombreProfesorUV) {
        this.idOfertaColabUV = idOfertaColabUV;
        this.tema = tema;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.idIdioma = idIdioma;
        this.idProfesorUV = idProfesorUV;
        this.nombreIdioma = nombreIdioma;
        this.nombreProfesorUV = nombreProfesorUV;
    }

    public OfertaColabUV() {
    }

    public String getNombreIdioma() {
        return nombreIdioma;
    }

    public void setNombreIdioma(String nombreIdioma) {
        this.nombreIdioma = nombreIdioma;
    }

    public String getNombreProfesorUV() {
        return nombreProfesorUV;
    }

    public void setNombreProfesorUV(String nombreProfesorUV) {
        this.nombreProfesorUV = nombreProfesorUV;
    }

    public Integer getIdProfesorUV() {
        return idProfesorUV;
    }

    public void setIdProfesorUV(Integer idProfesorUV) {
        this.idProfesorUV = idProfesorUV;
    }

    public Integer getIdOfertaColabUV() {
        return idOfertaColabUV;
    }

    public void setIdOfertaColabUV(Integer idOfertaColabUV) {
        this.idOfertaColabUV = idOfertaColabUV;
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
