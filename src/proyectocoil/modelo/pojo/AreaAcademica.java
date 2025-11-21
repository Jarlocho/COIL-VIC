/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Modela el objeto para su uso dentro del programa
 * Versión: 1.0
 */

package proyectocoil.modelo.pojo;


public class AreaAcademica {
    Integer idAreaAcademica;
    String nombre;
    String descripcion;
    String clave;

    public AreaAcademica(Integer idAreaAcademica, String nombre, String descripcion, String clave) {
        this.idAreaAcademica = idAreaAcademica;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.clave = clave;
    }

    public AreaAcademica() {
    }

    public Integer getIdAreaAcademica() {
        return idAreaAcademica;
    }

    public void setIdAreaAcademica(Integer idAreaAcademica) {
        this.idAreaAcademica = idAreaAcademica;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    @Override
    public String toString() {
        return this.nombre + " " + this.clave;
    }
    
}
