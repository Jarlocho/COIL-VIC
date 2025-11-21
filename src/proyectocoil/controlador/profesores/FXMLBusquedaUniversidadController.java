/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 13/06/2024
 * Descripción: Muestra las universidades para asociarlo al nuevo profesor externo
 * Versión: 1.0
 */

package proyectocoil.controlador.profesores;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.ProfesorExtDAO;
import proyectocoil.modelo.dao.CatalogoDAO;
import proyectocoil.modelo.pojo.Idioma;
import proyectocoil.modelo.pojo.Universidad;
import proyectocoil.modelo.pojo.ProfesorExt;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLBusquedaUniversidadController implements Initializable {
    @FXML
    private TextField tfBusquedaUni;
    @FXML
    private TableView<Universidad> tvUniversidad;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colPais;
    private ObservableList<Universidad> universidades;
    private ProfesorExt profesorExt;
    private boolean registroExitoso = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    public void inicializarValores(ProfesorExt profesorExt) {
        this.profesorExt = profesorExt;
        cargarDatosUniversidades();
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colPais.setCellValueFactory(new PropertyValueFactory("pais"));
    }

    private void cargarDatosUniversidades() {
        universidades = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = CatalogoDAO.obtenerUniversidades();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<Universidad> universidadesBD = (ArrayList<Universidad>) respuesta.get("universidades");
            universidades.addAll(universidadesBD);
            tvUniversidad.setItems(universidades);
            configurarBusquedaUniversidad();
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
        configurarBusquedaUniversidad();
    }

    private void configurarBusquedaUniversidad() {
        if (universidades.size() > 0) {
            FilteredList<Universidad> filtroUniversidad = new FilteredList<>(universidades, p -> true);
            tfBusquedaUni.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroUniversidad.setPredicate(p -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String palabraFiltro = newValue.toLowerCase();
                        return p.getNombre().toLowerCase().contains(palabraFiltro);
                    });
                }
            });
            SortedList<Universidad> listaOrdenada = new SortedList<>(filtroUniversidad);
            listaOrdenada.comparatorProperty().bind(tvUniversidad.comparatorProperty());
            tvUniversidad.setItems(listaOrdenada);
        }
    }

    public void cerrarVentana() {
        ((Stage) tfBusquedaUni.getScene().getWindow()).close();
    }

    public boolean esRegistroExitoso() {
        return registroExitoso;
    }

    private void registrarProfExt() {
        if (profesorExt == null) {
            Utils.mostrarAlertaSimple("Error", "El profesor no está inicializado.", Alert.AlertType.WARNING);
            return;
        }
        Universidad universidadSeleccionada = tvUniversidad.getSelectionModel().getSelectedItem();
        if (universidadSeleccionada == null) {
            Utils.mostrarAlertaSimple("Error", "Selecciona una universidad.", Alert.AlertType.WARNING);
        } else {
            profesorExt.setIdUniversidad(universidadSeleccionada.getIdUniversidad());
            HashMap<String, Object> respuesta = ProfesorExtDAO.registrarProfesorExt(profesorExt, universidadSeleccionada.getIdUniversidad());
            if (!((boolean) respuesta.get(Constantes.KEY_ERROR))) {
                Utils.mostrarAlertaSimple("Profesor externo registrado", "" + respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.INFORMATION);
                Integer idProfesorExt = (Integer) respuesta.get("idProfesorExt");
                if (idProfesorExt != null) {
                    profesorExt.setIdProfesorExt(idProfesorExt);
                    for (Idioma idioma : profesorExt.getIdiomas()) {
                        ProfesorExtDAO.asociarIdiomaProfesor(idProfesorExt, idioma.getIdIdioma());
                    }
                }
                registroExitoso = true;
                cerrarVentana();
            } else {
                Utils.mostrarAlertaSimple("Error al registrar", "" + respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void btnClicSeleccionar(ActionEvent event) {
        registrarProfExt();
    }
}