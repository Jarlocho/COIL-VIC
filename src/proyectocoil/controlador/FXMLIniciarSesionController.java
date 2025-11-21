/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Pide y valida las credenciales para iniciar sesión y guarda el usuario
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
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.AutenticacionDAO;
import proyectocoil.modelo.pojo.RespuestaLogin;
import proyectocoil.modelo.pojo.Usuario;
import proyectocoil.utilidades.Utils;


public class FXMLIniciarSesionController implements Initializable {

    @FXML
    private TextField tfNombreUsuario;
    @FXML
    private PasswordField pfContrasenia;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void btnClicIniciarSesion(ActionEvent event) {
        String nombreUsuario = tfNombreUsuario.getText();
        String password = pfContrasenia.getText();
        if (validarCampos(nombreUsuario, password)) {
            RespuestaLogin respuesta = AutenticacionDAO.iniciarSesionUsuario(nombreUsuario, password);
            if (!respuesta.getError()) {
                Utils.mostrarAlertaSimple("Bienvenida", "Bienvenido(a) al sistema " + nombreUsuario + ".",
                        Alert.AlertType.INFORMATION);
                irMenuPrincipal(respuesta.getUsuario());
            } else {
                Utils.mostrarAlertaSimple("Error", respuesta.getMensaje(),
                    Alert.AlertType.WARNING);
            }
        }
    }
    
    private boolean validarCampos(String numPersonal, String password){
        if(!numPersonal.isEmpty() && !password.isEmpty()){
            return true;
        } else {
            Utils.mostrarAlertaSimple("Campos Incompletos", "Todos los campos deben ser llenados para proceder.",
                    Alert.AlertType.WARNING);
            return false;
        }
    }
    
    
    private void irMenuPrincipal(Usuario usuario){
        try {
            Stage escenarioPrincipal = (Stage) tfNombreUsuario.getScene().getWindow();
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
}