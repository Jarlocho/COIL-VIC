/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 21/06/2024
 * Descripción: Muestra las colaboraciones de la bases de datos
 * Versión: 1.0
 */

package proyectocoil.controlador.colaboraciones;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.ColaboracionDAO;
import proyectocoil.modelo.pojo.Colaboracion;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLSeleccionColabController implements Initializable {
    @FXML
    private TableColumn colFechaInicio;
    @FXML
    private TableColumn colFechaTermino;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TextField tfBusquedaColab;
    @FXML
    private TableView<Colaboracion> tvColab;
    private ObservableList<Colaboracion> colaboraciones;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    public void inicializarValores() {
        cargarDatosColaboraciones();
    }

    private void configurarTabla() {
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colFechaTermino.setCellValueFactory(new PropertyValueFactory<>("fechaTermino"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreColab"));
    }

    private void cargarDatosColaboraciones() {
        colaboraciones = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = ColaboracionDAO.ObtenerColaboraciones(null);
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<Colaboracion> colaboracionesBD = (ArrayList<Colaboracion>) respuesta.get("colaboraciones");
            colaboraciones.addAll(colaboracionesBD);
            tvColab.setItems(colaboraciones);
            configurarBusquedaColaboracion();
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
        configurarBusquedaColaboracion();
    }

    private void configurarBusquedaColaboracion() {
        if (colaboraciones.size() > 0) {
            FilteredList<Colaboracion> filtroColaboracion = new FilteredList<>(colaboraciones, p -> true);
            tfBusquedaColab.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroColaboracion.setPredicate(p -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String palabraFiltro = newValue.toLowerCase();
                        return p.getNombreColab().toLowerCase().contains(palabraFiltro);
                    });
                }
            });
            SortedList<Colaboracion> listaOrdenada = new SortedList<>(filtroColaboracion);
            listaOrdenada.comparatorProperty().bind(tvColab.comparatorProperty());
            tvColab.setItems(listaOrdenada);
        }
    }

    public void cerrarVentana() {
        ((Stage) tvColab.getScene().getWindow()).close();
    }

    private void irAdministracionProfesor(Colaboracion colaboracionSeleccionada){
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/colaboraciones/FXMLAdministracionProfesorColab.fxml");
            Parent root = loader.load();
            FXMLAdministracionProfesorColabController controlador = loader.getController();
            controlador.inicializarValores(colaboracionSeleccionada);
            Scene escena = new Scene(root);
            escenario.setTitle("Administración de Profesor de Colaboración");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void btnClicSeleccionar(ActionEvent event) {
        Colaboracion colaboracionSeleccionada = tvColab.getSelectionModel().getSelectedItem();
        if (colaboracionSeleccionada != null) {
            irAdministracionProfesor(colaboracionSeleccionada);
        } else {
            Utils.mostrarAlertaSimple("Error", "Selecciona una colaboración.", Alert.AlertType.WARNING);
        }
    }
}