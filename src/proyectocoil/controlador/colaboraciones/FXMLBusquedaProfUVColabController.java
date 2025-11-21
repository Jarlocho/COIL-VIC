/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 23/06/2024
 * Descripción: Muestra los profesores UV para cambiarlo en la colaboración
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
import proyectocoil.modelo.dao.ProfesorUVDAO;
import proyectocoil.modelo.pojo.ProfesorUV;
import proyectocoil.utilidades.Utils;

public class FXMLBusquedaProfUVColabController implements Initializable {
    @FXML
    private TextField tfBusquedaProfUV;
    @FXML
    private TableView<ProfesorUV> tvProfUV;
    @FXML
    private TableColumn<ProfesorUV, String> colNombre;
    @FXML
    private TableColumn<ProfesorUV, String> colCorreo;
    @FXML
    private TableColumn<ProfesorUV, String> colTitulo;
    private FXMLAdministracionProfesorColabController controladorPrincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarProfesoresUV();
    }

    public void inicializarValores(FXMLAdministracionProfesorColabController controladorPrincipal) {
        this.controladorPrincipal = controladorPrincipal;
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
    }

    private void cargarProfesoresUV() {
        ArrayList<ProfesorUV> profesoresUV = (ArrayList<ProfesorUV>) ProfesorUVDAO.obtenerProfesoresUV().get("profesoresUV");
        tvProfUV.getItems().setAll(profesoresUV);
    }

    public void cerrarVentana() {
        ((Stage) tfBusquedaProfUV.getScene().getWindow()).close();
    }

    @FXML
    private void btnClicSeleccionar(ActionEvent event) {
        ProfesorUV profesorSeleccionado = tvProfUV.getSelectionModel().getSelectedItem();
        if (profesorSeleccionado != null) {
            controladorPrincipal.actualizarColaboracionConNuevoProfesor(profesorSeleccionado, true);
            cerrarVentana();
        } else {
            Utils.mostrarAlertaSimple("Error", "Selecciona un profesor UV.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }
}