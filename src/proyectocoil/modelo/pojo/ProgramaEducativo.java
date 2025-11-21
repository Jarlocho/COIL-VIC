/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Modela el objeto para su uso dentro del programa
 * Versión: 1.0
 */

package proyectocoil.modelo.pojo;


public class ProgramaEducativo {
    Integer idProgramaEducativo;
    String nombre;
    Integer clave;
    Integer anioInicioOperacion;
    Integer idDependencia;

    public ProgramaEducativo(Integer idProgramaEducativo, String nombre, Integer clave, Integer anioInicioOperacion, Integer idDependencia) {
        this.idProgramaEducativo = idProgramaEducativo;
        this.nombre = nombre;
        this.clave = clave;
        this.anioInicioOperacion = anioInicioOperacion;
        this.idDependencia = idDependencia;
    }

    public ProgramaEducativo() {
    }

    public Integer getIdProgramaEducativo() {
        return idProgramaEducativo;
    }

    public void setIdProgramaEducativo(Integer idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getClave() {
        return clave;
    }

    public void setClave(Integer clave) {
        this.clave = clave;
    }

    public Integer getAnioInicioOperacion() {
        return anioInicioOperacion;
    }

    public void setAnioInicioOperacion(Integer anioInicioOperacion) {
        this.anioInicioOperacion = anioInicioOperacion;
    }

    public Integer getIdDependencia() {
        return idDependencia;
    }

    public void setIdDependencia(Integer idDependencia) {
        this.idDependencia = idDependencia;
    }
    
    @Override
    public String toString() {
        return this.nombre + " " + this.clave;
    }
}
