/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Muestra y habilita distintos botones para realizar acciones relacionadas
   con las colaboraciones dependiendo de si el usuario es un profesorUV o no
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectocoil.controlador.colaboraciones.FXMLHistorialDeColaboracionesController;
import proyectocoil.controlador.colaboraciones.FXMLRegistroColaboracionController;
import proyectocoil.controlador.colaboraciones.FXMLSeleccionColabController;
import proyectocoil.controlador.colaboraciones.FXMLSeleccionColaboracionController;
import proyectocoil.modelo.pojo.Usuario;
import proyectocoil.utilidades.Utils;


public class FXMLMenuColaboracionesController implements Initializable {

    @FXML
    private Label lbMenu;
    @FXML
    private Button btnConsultarHistorial;
    @FXML
    private Button btnSubirEvidencias;

    private Usuario usuario;
    @FXML
    private Button btnAdministrarProfesor;
    @FXML
    private Button btnRegistrarColab;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    
    
    void inicializarValores(Usuario usuario) {
        this.usuario = usuario;
        configurarBotones();
    }
    
    private void configurarBotones() {
        if (usuario.getIdProfesorUV() != null) {
            btnAdministrarProfesor.setDisable(true);
            btnRegistrarColab.setDisable(true);
            
        } else {
            btnConsultarHistorial.setDisable(true);
            btnSubirEvidencias.setDisable(true);
        }
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

    @FXML
    private void btnClicAdministrarEstado(ActionEvent event) {
        irSeleccionColaboracion("Administrar estado");
    }

    @FXML
    private void btnClicAdministrarProfesor(ActionEvent event) {
        irSeleccionColab(); 
    }

    private void irSeleccionColab(){
        try {
            FXMLLoader loader = Utils.obtenerLoader("vista/colaboraciones/FXMLSeleccionColab.fxml");
            Parent root = loader.load();
            FXMLSeleccionColabController controlador = loader.getController();
            controlador.inicializarValores();
            Stage escenarioAdmin = new Stage();
            Scene escena = new Scene(root);
            escenarioAdmin.setTitle("Selección de Colaboración");
            escenarioAdmin.setScene(escena);
            escenarioAdmin.initModality(Modality.APPLICATION_MODAL);
            escenarioAdmin.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error :"+ex.getMessage());
        }
    }
    
    @FXML
    private void btnClicConsultarHistorial(ActionEvent event) {
        irConsultarHistorial();
    }

    private void irConsultarHistorial() {
        try {
            FXMLLoader loader = Utils.obtenerLoader("vista/colaboraciones/FXMLHistorialDeColaboraciones.fxml");
            Parent root = loader.load();
            FXMLHistorialDeColaboracionesController controlador = loader.getController();
            controlador.inicializarValores();
            Stage escenarioAdmin = new Stage();
            Scene escena = new Scene(root);
            escenarioAdmin.setTitle("Historial de Colaboraciones");
            escenarioAdmin.setScene(escena);
            escenarioAdmin.initModality(Modality.APPLICATION_MODAL);
            escenarioAdmin.showAndWait();
        } catch (IOException ex) {
            System.err.println("Error al cargar la vista...");
        }
    }
    
    @FXML
    private void btnClicRegistrarColab(ActionEvent event) {
        irRegistroColab();
    }

    private void irRegistroColab() {
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/colaboraciones/FXMLRegistroColaboracion.fxml");
            Parent root = loader.load();
            FXMLRegistroColaboracionController controlador = loader.getController();
            controlador.inicializarValores();
            Scene escena = new Scene(root);
            escenario.setTitle("Registrar Colaboración");
            escenario.setScene(escena);
            escenario.showAndWait();
            
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
    
    private void irSeleccionColaboracion(String motivo) {
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/colaboraciones/FXMLSeleccionColaboracion.fxml");
            Parent root = loader.load();
            FXMLSeleccionColaboracionController controlador = loader.getController();
            controlador.inicializarValores(motivo, usuario.getIdProfesorUV());
            Scene escena = new Scene(root);
            escenario.setTitle("Seleccionar Colaboración");
            escenario.setScene(escena);
            escenario.showAndWait();
            
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
    
    @FXML
    private void btnClicSubirEvidencias(ActionEvent event) {
        irSeleccionColaboracion("Subir evidencias");
    }
    
}
