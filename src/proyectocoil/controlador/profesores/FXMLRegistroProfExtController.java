/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 13/06/2024
 * Descripción: Muestra un formulario para registrar un nuevo profesor externo
 * Versión: 1.0
 */

package proyectocoil.controlador.profesores;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.IdiomaDAO;
import proyectocoil.modelo.pojo.Idioma;
import proyectocoil.modelo.pojo.ProfesorExt;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLRegistroProfExtController implements Initializable {
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfTitulo;
    @FXML
    private TableView<Idioma> tvIdioma;
    @FXML
    private TableColumn colIdioma;
    private ObservableList<ProfesorExt> profesoresExt;
    private ObservableList<Idioma> idiomas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        tvIdioma.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void inicializarValores() {
        cargarDatosIdiomas();
    }

    private ObservableList<Idioma> obtenerIdiomasSeleccionados() {
        return tvIdioma.getSelectionModel().getSelectedItems();
    }

    private ProfesorExt obtenerInformacionProfesorExt() {
        ProfesorExt profesorExt = new ProfesorExt();
        profesorExt.setNombre(tfNombre.getText());
        profesorExt.setCorreo(tfCorreo.getText());
        profesorExt.setTitulo(tfTitulo.getText());
        profesorExt.setIdiomas(new ArrayList<>(obtenerIdiomasSeleccionados()));
        return profesorExt;
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
        } else if (obtenerIdiomasSeleccionados().isEmpty()) {
            Utils.mostrarAlertaSimple("Sin Selección", "Por favor, seleccione al menos un Idioma para continuar.", Alert.AlertType.WARNING);
            return false;
        } else {
            return true;
        }
    }

    private boolean irBusquedaUniversidad() {
        ProfesorExt profesorExt = obtenerInformacionProfesorExt();
        boolean registroExitoso = false;
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/profesores/FXMLBusquedaUniversidad.fxml");
            Parent root = loader.load();
            FXMLBusquedaUniversidadController controlador = loader.getController();
            controlador.inicializarValores(profesorExt);
            Scene escena = new Scene(root);
            escenario.setTitle("Búsqueda de Universidad");
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
        ((Stage) tfNombre.getScene().getWindow()).close();
    }

    private void limpiarCampos() {
        tfNombre.clear();
        tfCorreo.clear();
        tfTitulo.clear();
        tvIdioma.getSelectionModel().clearSelection();
    }

    private void cargarDatosIdiomas(){
        idiomas = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = IdiomaDAO.obtenerIdiomas();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if(!isError) {
            ArrayList<Idioma> idiomasBD = (ArrayList<Idioma>) respuesta.get("idiomas");
            idiomas.addAll(idiomasBD);
            tvIdioma.setItems(idiomas);
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }   
    }

    private void configurarTabla() {
        colIdioma.setCellValueFactory(new PropertyValueFactory("nombre"));
    }

    @FXML
    private void btnClicAceptar(ActionEvent event) {
        if (validarCampos()) {
            if (irBusquedaUniversidad()) {
                limpiarCampos();
            }
        }
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }
}