/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Muestra y habilita distintos botones que redirigen a submenús del programa
   (y a consultar numeralia) dependiendo de si el usuario es un profesorUV o no
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
import javafx.stage.Stage;
import proyectocoil.modelo.pojo.Usuario;
import proyectocoil.utilidades.Utils;


public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private Label lbMenuPrincipal;
    
    private Usuario usuario;
    
    @FXML
    private Button btnProfesoresExt;
    @FXML
    private Button btnConsultarNumeralia;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    
    
    private void configurarBotones() {
        if (usuario.getIdProfesorUV() != null) {
            btnProfesoresExt.setDisable(true);
            btnConsultarNumeralia.setDisable(true);
        }
    }
    
    void inicializarValores(Usuario usuario) {
        this.usuario = usuario;
        configurarBotones();
        
    }

    @FXML
    private void btnClicCerrarSesion(ActionEvent event) {
        irIniciarSesion();
        
    }

    private void irIniciarSesion(){
        try {
            Stage escenarioPrincipal = (Stage) lbMenuPrincipal.getScene().getWindow();
            Parent root = FXMLLoader.load(proyectocoil.ProyectoCoil.class.getResource("vista/FXMLIniciarSesion.fxml"));
            Scene escenaPrincipal = new Scene(root);
            escenarioPrincipal.setScene(escenaPrincipal);
            escenarioPrincipal.show();
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
    

    @FXML
    private void btnClicColaboraciones(ActionEvent event) {
        irMenuColaboraciones(usuario);
    }
    
    private void irMenuColaboraciones(Usuario usuario){
        try {
            Stage escenarioPrincipal = (Stage) btnProfesoresExt.getScene().getWindow();
            FXMLLoader loader = Utils.obtenerLoader("vista/FXMLMenuColaboraciones.fxml");
            Parent root = loader.load();
            FXMLMenuColaboracionesController controlador = loader.getController();
            controlador.inicializarValores(usuario);
            Scene escenaPrincipal = new Scene(root);
            escenarioPrincipal.setTitle("Menú Colaboraciones");
            escenarioPrincipal.setScene(escenaPrincipal);
            escenarioPrincipal.show();
            
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }

    @FXML
    private void btnClicProfesoresExt(ActionEvent event) {
        irMenuProfesoresExt(usuario);
    }

    private void irMenuProfesoresExt(Usuario usuario){
        try {
            Stage escenarioPrincipal = (Stage) btnProfesoresExt.getScene().getWindow();
            FXMLLoader loader = Utils.obtenerLoader("vista/FXMLMenuProfesoresExt.fxml");
            Parent root = loader.load();
            FXMLMenuProfesoresExtController controlador = loader.getController();
            controlador.inicializarValores(usuario);
            Scene escenaPrincipal = new Scene(root);
            escenarioPrincipal.setTitle("Menú Profesores Externos");
            escenarioPrincipal.setScene(escenaPrincipal);
            escenarioPrincipal.show();
            
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
    
    @FXML
    private void btnClicOfertasColab(ActionEvent event) {
        irMenuOfertas(usuario);
    }
    
    private void irMenuOfertas(Usuario usuario){
        try {
            Stage escenarioPrincipal = (Stage) btnProfesoresExt.getScene().getWindow();
            FXMLLoader loader = Utils.obtenerLoader("vista/FXMLMenuOfertas.fxml");
            Parent root = loader.load();
            FXMLMenuOfertasController controlador = loader.getController();
            controlador.inicializarValores(usuario);
            Scene escenaPrincipal = new Scene(root);
            escenarioPrincipal.setTitle("Menú Ofertas");
            escenarioPrincipal.setScene(escenaPrincipal);
            escenarioPrincipal.show();
            
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }

    @FXML
    private void btnClicConsultarNumeralia(ActionEvent event) {
        irNumeralia();
    }
    
    private void irNumeralia(){
        try {
            Stage escenarioPrincipal = (Stage) btnProfesoresExt.getScene().getWindow();
            FXMLLoader loader = Utils.obtenerLoader("vista/FXMLNumeralia.fxml");
            Parent root = loader.load();
            FXMLNumeraliaController controlador = loader.getController();
            controlador.inicializarValores(usuario);
            Scene escenaPrincipal = new Scene(root);
            escenarioPrincipal.setTitle("Numeralia");
            escenarioPrincipal.setScene(escenaPrincipal);
            escenarioPrincipal.show();
            
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
}
