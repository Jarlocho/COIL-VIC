/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Muestra botones para realizar acciones relacionadas
   con el manejo de los Profesores Externos de la base dependiendo de si el usuario es un profesorUV o no
 * Versión: 1.0
 */

package proyectocoil.controlador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectocoil.controlador.profesores.FXMLBusquedaProfExtController;
import proyectocoil.controlador.profesores.FXMLRegistroProfExtController;
import proyectocoil.modelo.pojo.Usuario;
import proyectocoil.utilidades.Utils;

public class FXMLMenuProfesoresExtController implements Initializable {
    @FXML
    private Label lbMenu;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    private Usuario usuario;
    
    public void inicializarValores(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @FXML
    private void btnClicVolver(ActionEvent event) {
        irMenuPrincipal();
    }
    
    private void irMenuPrincipal(){
        try {
            Stage escenarioPrincipal = (Stage) lbMenu.getScene().getWindow();
            FXMLLoader loader = Utils.obtenerLoader("vista/FXMLMenuPrincipal.fxml");
            Parent root = loader.load();
            FXMLMenuPrincipalController controlador = loader.getController();
            controlador.inicializarValores(usuario);
            Scene escenaPrincipal = new Scene(root);
            escenarioPrincipal.setTitle("Menú Principal");
            escenarioPrincipal.setScene(escenaPrincipal);
            escenarioPrincipal.show();
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }

    private void irRegistroProfExt(){
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/profesores/FXMLRegistroProfExt.fxml");
            Parent root = loader.load();
            FXMLRegistroProfExtController controlador = loader.getController();
            controlador.inicializarValores();
            Scene escena = new Scene(root);
            escenario.setTitle("Registro de Profesor Externo");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void irModificacionProfExtBusqueda(){
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/profesores/FXMLBusquedaProfExt.fxml");
            Parent root = loader.load();
            FXMLBusquedaProfExtController controlador = loader.getController();
            controlador.setModoModificacion(true);
            controlador.inicializarValores();
            Scene escena = new Scene(root);
            escenario.setTitle("Búsqueda de Profesor Externo");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @FXML
    private void btnClicRegistrarProfesorExt(ActionEvent event) {
        irRegistroProfExt();
    }

    @FXML
    private void btnClicModificarProfesorExt(ActionEvent event) {
        irModificacionProfExtBusqueda();
    }
}