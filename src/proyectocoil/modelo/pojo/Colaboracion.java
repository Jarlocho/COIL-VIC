/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Modela el objeto para su uso dentro del programa
 * Versión: 1.0
 */

package proyectocoil.modelo.pojo;
import java.util.List;

public class Colaboracion {
    Integer idColaboracion;
    String nombreColab;
    String estado;
    String fechaInicio;
    String fechaTermino;
    byte [] planDeActividades;
    Integer idExperienciaEducativa;
    Integer idProfesorUV;
    Integer idProfesorExt;
    Integer idOfertaColabUV;
    Integer idOfertaColabExt;
    String nombreProfesorUV;
    String nombreProfesorExt;
    String nombreExperienciaEducativa;
    private List<Integer> idProfesoresUV;
    private List<Integer> idProfesoresExt;

    public Colaboracion(Integer idColaboracion, String nombreColab, String estado, String fechaInicio, String fechaTermino, byte[] planDeActividades, Integer idExperienciaEducativa, Integer idProfesorUV, Integer idProfesorExt, Integer idOfertaColabUV, Integer idOfertaColabExt, String nombreProfesorUV, String nombreProfesorExt, String nombreExperienciaEducativa, List<Integer> idProfesoresUV, List<Integer> idProfesoresExt) {
        this.idColaboracion = idColaboracion;
        this.nombreColab = nombreColab;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.planDeActividades = planDeActividades;
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.idProfesorUV = idProfesorUV;
        this.idProfesorExt = idProfesorExt;
        this.idOfertaColabUV = idOfertaColabUV;
        this.idOfertaColabExt = idOfertaColabExt;
        this.nombreProfesorUV = nombreProfesorUV;
        this.nombreProfesorExt = nombreProfesorExt;
        this.nombreExperienciaEducativa = nombreExperienciaEducativa;
        this.idProfesoresUV = idProfesoresUV;
        this.idProfesoresExt = idProfesoresExt;
    }

    public Colaboracion() {
    }

    public String getNombreExperienciaEducativa() {
        return nombreExperienciaEducativa;
    }

    public void setNombreExperienciaEducativa(String nombreExperienciaEducativa) {
        this.nombreExperienciaEducativa = nombreExperienciaEducativa;
    }

    public String getNombreProfesorUV() {
        return nombreProfesorUV;
    }

    public void setNombreProfesorUV(String nombreProfesorUV) {
        this.nombreProfesorUV = nombreProfesorUV;
    }

    public String getNombreProfesorExt() {
        return nombreProfesorExt;
    }

    public void setNombreProfesorExt(String nombreProfesorExt) {
        this.nombreProfesorExt = nombreProfesorExt;
    }

    public Integer getIdColaboracion() {
        return idColaboracion;
    }

    public void setIdColaboracion(Integer idColaboracion) {
        this.idColaboracion = idColaboracion;
    }

    public String getNombreColab() {
        return nombreColab;
    }

    public void setNombreColab(String nombreColab) {
        this.nombreColab = nombreColab;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public byte[] getPlanDeActividades() {
        return planDeActividades;
    }

    public void setPlanDeActividades(byte[] planDeActividades) {
        this.planDeActividades = planDeActividades;
    }

    public Integer getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(Integer idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public Integer getIdProfesorUV() {
        return idProfesorUV;
    }

    public void setIdProfesorUV(Integer idProfesorUV) {
        this.idProfesorUV = idProfesorUV;
    }

    public Integer getIdProfesorExt() {
        return idProfesorExt;
    }

    public void setIdProfesorExt(Integer idProfesorExt) {
        this.idProfesorExt = idProfesorExt;
    }

    public Integer getIdOfertaColabUV() {
        return idOfertaColabUV;
    }

    public void setIdOfertaColabUV(Integer idOfertaColabUV) {
        this.idOfertaColabUV = idOfertaColabUV;
    }

    public Integer getIdOfertaColabExt() {
        return idOfertaColabExt;
    }

    public void setIdOfertaColabExt(Integer idOfertaColabExt) {
        this.idOfertaColabExt = idOfertaColabExt;
    }

    public List<Integer> getIdProfesoresUV() {
        return idProfesoresUV;
    }

    public void setIdProfesoresUV(List<Integer> idProfesoresUV) {
        this.idProfesoresUV = idProfesoresUV;
    }

    public List<Integer> getIdProfesoresExt() {
        return idProfesoresExt;
    }

    public void setIdProfesoresExt(List<Integer> idProfesoresExt) {
        this.idProfesoresExt = idProfesoresExt;
    }
}