/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 23/06/2024
 * Descripción: Muestra los profesores externos para cambiarlo en la colaboración
 * Versión: 1.0
 */

package proyectocoil.controlador.colaboraciones;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.ProfesorExtDAO;
import proyectocoil.modelo.pojo.ProfesorExt;
import proyectocoil.utilidades.Utils;

public class FXMLBusquedaProfExtColabController implements Initializable {
    @FXML
    private TextField tfBusquedaProfExt;
    @FXML
    private TableView<ProfesorExt> tvProfExterno;
    @FXML
    private TableColumn<ProfesorExt, String> colNombre;
    @FXML
    private TableColumn<ProfesorExt, String> colCorreo;
    @FXML
    private TableColumn<ProfesorExt, String> colTitulo;
    private FXMLAdministracionProfesorColabController controladorPrincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarProfesoresExternos();
    }

    public void inicializarValores(FXMLAdministracionProfesorColabController controladorPrincipal) {
        this.controladorPrincipal = controladorPrincipal;
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
    }

    private void cargarProfesoresExternos() {
        ArrayList<ProfesorExt> profesoresExternos = (ArrayList<ProfesorExt>) ProfesorExtDAO.obtenerProfesoresExt().get("profesoresExt");
        tvProfExterno.getItems().setAll(profesoresExternos);
    }

    public void cerrarVentana() {
        ((Stage) tfBusquedaProfExt.getScene().getWindow()).close();
    }

    @FXML
    private void btnClicSeleccionar(ActionEvent event) {
        ProfesorExt profesorSeleccionado = tvProfExterno.getSelectionModel().getSelectedItem();
        if (profesorSeleccionado != null) {
            controladorPrincipal.actualizarColaboracionConNuevoProfesor(profesorSeleccionado, false);
            cerrarVentana();
        } else {
            Utils.mostrarAlertaSimple("Error", "Selecciona un profesor externo.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }
}