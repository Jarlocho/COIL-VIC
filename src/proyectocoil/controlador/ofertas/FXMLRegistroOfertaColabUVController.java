/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 13/06/2024
 * Descripción: Muestra un formulario para registrar una nueva oferta UV
 * Versión: 1.0
 */

package proyectocoil.controlador.ofertas;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.IdiomaDAO;
import proyectocoil.modelo.pojo.Idioma;
import proyectocoil.modelo.pojo.OfertaColabUV;
import proyectocoil.utilidades.Utils;

public class FXMLRegistroOfertaColabUVController implements Initializable {
    @FXML
    private TextField tfTema;
    @FXML
    private ComboBox<Idioma> cbIdioma;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaTermino;
    private ObservableList<Idioma> idiomas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarIdiomas();
    }

    public void inicializarValores() {
    }

    private OfertaColabUV obtenerInformacionOfertaColabUV() {
        OfertaColabUV ofertaUV = new OfertaColabUV();
        ofertaUV.setTema(tfTema.getText());
        ofertaUV.setFechaInicio(dpFechaInicio.getValue().toString());
        ofertaUV.setFechaTermino(dpFechaTermino.getValue().toString());
        ofertaUV.setIdIdioma(cbIdioma.getSelectionModel().getSelectedItem().getIdIdioma());
        return ofertaUV;
    }

    private void cargarIdiomas() {
        idiomas = FXCollections.observableArrayList();
        idiomas.addAll((ArrayList<Idioma>) IdiomaDAO.obtenerIdiomas().get("idiomas"));
        cbIdioma.setItems(idiomas);
    }

    private boolean validarCampos() {
        if (tfTema.getText().isEmpty()) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, llene el campo Tema para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (cbIdioma.getSelectionModel().getSelectedItem() == null) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, seleccione el Idioma para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (dpFechaInicio.getValue() == null) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, llene el campo Fecha de Inicio para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (dpFechaTermino.getValue() == null) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, llene el campo Fecha de Término para continuar.", Alert.AlertType.WARNING);
            return false;
        } else {
            return Utils.validarFecha(dpFechaInicio.getValue(), dpFechaTermino.getValue());
        }
    }

    private boolean irBusquedaProfUV() {
        OfertaColabUV ofertaUV = obtenerInformacionOfertaColabUV();
        boolean registroExitoso = false;
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertas/FXMLBusquedaProfUV.fxml");
            Parent root = loader.load();
            FXMLBusquedaProfUVController controlador = loader.getController();
            controlador.inicializarValores(ofertaUV);
            Scene escena = new Scene(root);
            escenario.setTitle("Búsqueda de Profesor UV");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
            registroExitoso = controlador.esRegistroExitoso();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return registroExitoso;
    }

    public void cerrarVentana() {
        ((Stage) tfTema.getScene().getWindow()).close();
    }

    private void limpiarCampos() {
        tfTema.clear();
        cbIdioma.getSelectionModel().clearSelection();
        dpFechaInicio.setValue(null);
        dpFechaTermino.setValue(null);
    }

    @FXML
    private void btnClicAceptar(ActionEvent event) {
        if (validarCampos()) {
            if (irBusquedaProfUV()) {
                limpiarCampos();
            }
        }
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }
}