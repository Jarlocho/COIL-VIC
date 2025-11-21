/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 13/06/2024
 * Descripción: Muestra los profesores UV registrados para agregar a la nueva oferta UV
 * Versión: 1.0
 */

package proyectocoil.controlador.ofertas;
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
import proyectocoil.modelo.dao.OfertaColabUVDAO;
import proyectocoil.modelo.dao.ProfesorUVDAO;
import proyectocoil.modelo.pojo.OfertaColabUV;
import proyectocoil.modelo.pojo.ProfesorUV;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLBusquedaProfUVController implements Initializable {
    @FXML
    private TextField tfBusquedaProfUV;
    @FXML
    private TableView<ProfesorUV> tvProfUV;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colTitulo;
    private ObservableList<ProfesorUV> profesoresUV;
    private OfertaColabUV ofertaUV;
    private boolean registroExitoso = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    public void inicializarValores(OfertaColabUV ofertaUV) {
        this.ofertaUV = ofertaUV;
        cargarDatosProfesoresUV();
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
    }

    private void cargarDatosProfesoresUV() {
        profesoresUV = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = ProfesorUVDAO.obtenerProfesoresUV();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<ProfesorUV> profesoresUVBD = (ArrayList<ProfesorUV>) respuesta.get("profesoresUV");
            profesoresUV.addAll(profesoresUVBD);
            tvProfUV.setItems(profesoresUV);
            configurarBusquedaProfesorUV();
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
        configurarBusquedaProfesorUV();
    }

    private void configurarBusquedaProfesorUV() {
        if (profesoresUV.size() > 0) {
            FilteredList<ProfesorUV> filtroProfesorUV = new FilteredList<>(profesoresUV, p -> true);
            tfBusquedaProfUV.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroProfesorUV.setPredicate(p -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String palabraFiltro = newValue.toLowerCase();
                        return p.getNombre().toLowerCase().contains(palabraFiltro);
                    });
                }
            });
            SortedList<ProfesorUV> listaOrdenada = new SortedList<>(filtroProfesorUV);
            listaOrdenada.comparatorProperty().bind(tvProfUV.comparatorProperty());
            tvProfUV.setItems(listaOrdenada);
        }
    }

    public void cerrarVentana() {
        ((Stage) tfBusquedaProfUV.getScene().getWindow()).close();
    }

    public boolean esRegistroExitoso() {
        return registroExitoso;
    }

    private void registrarOfertaColabUV() {
        ProfesorUV profesorUVSeleccionado = tvProfUV.getSelectionModel().getSelectedItem();
        if (profesorUVSeleccionado == null) {
            Utils.mostrarAlertaSimple("Error", "Selecciona un profesor UV.", Alert.AlertType.WARNING);
        } else {
            ofertaUV.setIdProfesorUV(profesorUVSeleccionado.getIdProfesor());
            HashMap<String, Object> respuesta = OfertaColabUVDAO.registrarOfertaColabUV(ofertaUV,
                    profesorUVSeleccionado.getIdProfesor());
            if (!((boolean) respuesta.get(Constantes.KEY_ERROR))) {
                Utils.mostrarAlertaSimple("Oferta de colaboración registrada",
                        "" + respuesta.get(Constantes.KEY_MENSAJE),
                        Alert.AlertType.INFORMATION);
                HashMap<String, Object> resultado = OfertaColabUVDAO.obtenerOfertasColabUV();
                Integer idOfertaColabUV = (Integer) resultado.get("idOfertaColabUV");
                ofertaUV.setIdOfertaColabUV(idOfertaColabUV);
                registroExitoso = true;
                cerrarVentana();
            } else {
                Utils.mostrarAlertaSimple("Error al registrar", "" + respuesta.get(Constantes.KEY_MENSAJE),
                        Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void btnClicSeleccionar(ActionEvent event) {
        registrarOfertaColabUV();
    }
}