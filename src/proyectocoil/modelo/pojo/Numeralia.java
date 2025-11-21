/*
 * Autor: Jarly Hernández Romero
 * Fecha de Creación: 21/06/2024
 * Descripción: Modela el objeto numeralia para su uso dentro del programa
 * Versión: 1.0
 */

package proyectocoil.modelo.pojo;

public class Numeralia {

    String areaAcademica;
    int alumnos;
    int profesores;

    public Numeralia(String areaAcademica, int alumnos, int profesores) {
        this.areaAcademica = areaAcademica;
        this.alumnos = alumnos;
        this.profesores = profesores;
    }
    
    public Numeralia() {
    }

    public String getAreaAcademica() {
        return areaAcademica;
    }

    public void setAreaAcademica(String areaAcademica) {
        this.areaAcademica = areaAcademica;
    }


    public int getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(int alumnos) {
        this.alumnos = alumnos;
    }

    public int getProfesores() {
        return profesores;
    }

    public void setProfesores(int profesores) {
        this.profesores = profesores;
    }
}