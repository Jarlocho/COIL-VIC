/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Muestra y habilita distintos botones para realizar acciones relacionadas
   con las ofertas de colaboración dependiendo de si el usuario es un profesorUV o no
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
import proyectocoil.controlador.ofertas.FXMLAdministrarOfertaExternaController;
import proyectocoil.controlador.ofertas.FXMLAdministrarOfertaUVController;
import proyectocoil.controlador.ofertas.FXMLOfertasColaboracionRegistradasController;
import proyectocoil.controlador.ofertas.FXMLRegistroOfertaColabExtController;
import proyectocoil.controlador.ofertas.FXMLRegistroOfertaColabUVController;
import proyectocoil.modelo.pojo.Usuario;
import proyectocoil.utilidades.Utils;

public class FXMLMenuOfertasController implements Initializable {
    @FXML
    private Label lbMenu;
    @FXML
    private Button btnConsultarOfertas;
    @FXML
    private Button btnAdministrarOfertaExt;
    @FXML
    private Button btnRegistrarOfertaExt;
    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void inicializarValores(Usuario usuario) {
        this.usuario = usuario;
        configurarBotones();
    }

    private void configurarBotones() {
        if (usuario.getIdProfesorUV() != null) {
            btnAdministrarOfertaExt.setDisable(true);
            btnRegistrarOfertaExt.setDisable(true);
        } else {
            btnConsultarOfertas.setDisable(true);
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
    private void btnClicRegistrarOfertaUV(ActionEvent event) {
        irRegistroOfertaUV();
    }

    @FXML
    private void btnClicAdministrarOfertaExt(ActionEvent event) {
        irAdministrarExt();
    }

    private void irAdministrarExt() {
        try {
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertas/FXMLAdministrarOfertaExterna.fxml");
            Parent root = loader.load();
            FXMLAdministrarOfertaExternaController controlador = loader.getController();
            controlador.inicializarValores();
            Stage escenarioAdmin = new Stage();
            Scene escena = new Scene(root);
            escenarioAdmin.setTitle("Administrar Oferta Colaboración Externa");
            escenarioAdmin.setScene(escena);
            escenarioAdmin.initModality(Modality.APPLICATION_MODAL);
            escenarioAdmin.showAndWait();
        } catch (IOException ex) {
            System.err.println("Error al cargar la vista...");
        }
    }
    
    @FXML
    private void btnClicAdministrarOfertaUV(ActionEvent event) {
        irAdministrarOfertaUV();
    }

    private void irAdministrarOfertaUV() {
        try {
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertas/FXMLAdministrarOfertaUV.fxml");
            Parent root = loader.load();
            FXMLAdministrarOfertaUVController controlador = loader.getController();
            controlador.inicializarValores();
            Stage escenarioAdmin = new Stage();
            Scene escena = new Scene(root);
            escenarioAdmin.setTitle("Administrar Oferta Colaboración UV");
            escenarioAdmin.setScene(escena);
            escenarioAdmin.initModality(Modality.APPLICATION_MODAL);
            escenarioAdmin.showAndWait();
        } catch (IOException ex) {
            System.err.println("Error al cargar la vista...");
        }
    }
    
    @FXML
    private void btnClicRegistrarOfertaExt(ActionEvent event) {
        irRegistroOfertaExt();
    }

    @FXML
    private void btnClicConsultarOfertas(ActionEvent event) {
        irConsultarOfertas();
    }

    private void irConsultarOfertas() {
        try {
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertas/FXMLOfertasColaboracionRegistradas.fxml");
            Parent root = loader.load();
            FXMLOfertasColaboracionRegistradasController controlador = loader.getController();
            controlador.inicializarValores();
            Stage escenarioAdmin = new Stage();
            Scene escena = new Scene(root);
            escenarioAdmin.setTitle("Ofertas Colaboración Registradas");
            escenarioAdmin.setScene(escena);
            escenarioAdmin.initModality(Modality.APPLICATION_MODAL);
            escenarioAdmin.showAndWait();
        } catch (IOException ex) {
            System.err.println("Error al cargar la vista...");
        }
    }
    private void irRegistroOfertaExt() {
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertas/FXMLRegistroOfertaColabExt.fxml");
            Parent root = loader.load();
            FXMLRegistroOfertaColabExtController controlador = loader.getController();
            controlador.inicializarValores();
            Scene escena = new Scene(root);
            escenario.setTitle("Registro de Oferta de Colaboración Externa");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void irRegistroOfertaUV() {
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertas/FXMLRegistroOfertaColabUV.fxml");
            Parent root = loader.load();
            FXMLRegistroOfertaColabUVController controlador = loader.getController();
            controlador.inicializarValores();
            Scene escena = new Scene(root);
            escenario.setTitle("Registro de Oferta de Colaboración UV");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}