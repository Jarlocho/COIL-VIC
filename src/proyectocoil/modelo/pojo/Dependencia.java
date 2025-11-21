/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Modela el objeto para su uso dentro del programa
 * Versión: 1.0
 */

package proyectocoil.modelo.pojo;


public class Dependencia {
    Integer idDependencia;
    String campus;
    String region;
    String nombre;
    String municipio;
    Integer clave;
    Integer idAreaAcademica;

    public Dependencia(Integer idDependencia, String campus, String region, String nombre, String municipio, Integer clave, Integer idAreaAcademica) {
        this.idDependencia = idDependencia;
        this.campus = campus;
        this.region = region;
        this.nombre = nombre;
        this.municipio = municipio;
        this.clave = clave;
        this.idAreaAcademica = idAreaAcademica;
    }

    public Dependencia() {
    }

    public Integer getIdDependencia() {
        return idDependencia;
    }

    public void setIdDependencia(Integer idDependencia) {
        this.idDependencia = idDependencia;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Integer getClave() {
        return clave;
    }

    public void setClave(Integer clave) {
        this.clave = clave;
    }

    public Integer getIdAreaAcademica() {
        return idAreaAcademica;
    }

    public void setIdAreaAcademica(Integer idAreaAcademica) {
        this.idAreaAcademica = idAreaAcademica;
    }
    
    @Override
    public String toString() {
        return this.nombre + " " + this.clave;
    }
}
