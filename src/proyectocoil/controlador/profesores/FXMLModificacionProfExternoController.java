/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 13/06/2024
 * Descripción: Muestra un formulario para modificar un profesor externo ya existente
 * Versión: 1.0
 */

package proyectocoil.controlador.profesores;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.ProfesorExtDAO;
import proyectocoil.modelo.pojo.ProfesorExt;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLModificacionProfExternoController implements Initializable {
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfTitulo;
    private ProfesorExt profesorExt;
    private boolean modificacionExitosa = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void inicializarValores(ProfesorExt profesorExt) {
        this.profesorExt = profesorExt;
        cargarDatosProfesor();
    }

    private void cargarDatosProfesor() {
        tfNombre.setText(profesorExt.getNombre());
        tfCorreo.setText(profesorExt.getCorreo());
        tfTitulo.setText(profesorExt.getTitulo());
    }

    private boolean validarCampos() {
        if (tfNombre.getText().isEmpty()) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, llene el campo Nombre para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (tfCorreo.getText().isEmpty()) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, llene el campo Correo para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (tfTitulo.getText().isEmpty()) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, llene el campo Título para continuar.", Alert.AlertType.WARNING);
            return false;
        } else {
            return true;
        }
    }

    private ProfesorExt obtenerInformacionProfesorExt() {
        ProfesorExt profesorExt = new ProfesorExt();
        profesorExt.setIdProfesorExt(this.profesorExt.getIdProfesorExt());
        profesorExt.setNombre(tfNombre.getText());
        profesorExt.setCorreo(tfCorreo.getText());
        profesorExt.setTitulo(tfTitulo.getText());
        return profesorExt;
    }

    public void cerrarVentana() {
        ((Stage) tfNombre.getScene().getWindow()).close();
    }

    public boolean esModificacionExitosa() {
        return modificacionExitosa;
    }

    @FXML
    private void btnClicAceptar(ActionEvent event) {
        if (validarCampos()) {
            ProfesorExt profesorModificado = obtenerInformacionProfesorExt();
            HashMap<String, Object> respuesta = ProfesorExtDAO.modificarProfesorExt(profesorModificado);
            boolean error = (boolean) respuesta.get(Constantes.KEY_ERROR);
            if (!error) {
                modificacionExitosa = true;
                Utils.mostrarAlertaSimple("Profesor Modificado", "El profesor ha sido modificado exitosamente.", Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }
}