/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 13/06/2024
 * Descripción: Muestra los profesores externos registrados para agregar a la nueva oferta externa
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
import proyectocoil.modelo.dao.OfertaColabExtDAO;
import proyectocoil.modelo.dao.ProfesorExtDAO;
import proyectocoil.modelo.pojo.OfertaColabExt;
import proyectocoil.modelo.pojo.ProfesorExt;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLBusquedaProfExtController implements Initializable {
    @FXML
    private TextField tfBusquedaProfExt;
    @FXML
    private TableView<ProfesorExt> tvProfExterno;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colTitulo;
    private ObservableList<ProfesorExt> profesoresExt;
    private OfertaColabExt ofertaExt;
    private boolean registroExitoso = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    public void inicializarValores(OfertaColabExt ofertaExt) {
        this.ofertaExt = ofertaExt;
        cargarDatosProfesoresExt();
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
    }

    private void cargarDatosProfesoresExt() {
        profesoresExt = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = ProfesorExtDAO.obtenerProfesoresExt();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<ProfesorExt> profesoresExtBD = (ArrayList<ProfesorExt>) respuesta.get("profesoresExt");
            profesoresExt.addAll(profesoresExtBD);
            tvProfExterno.setItems(profesoresExt);
            configurarBusquedaProfesorExt();
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
        configurarBusquedaProfesorExt();
    }

    private void configurarBusquedaProfesorExt() {
        if (profesoresExt.size() > 0) {
            FilteredList<ProfesorExt> filtroProfesorExt = new FilteredList<>(profesoresExt, p -> true);
            tfBusquedaProfExt.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroProfesorExt.setPredicate(p -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String palabraFiltro = newValue.toLowerCase();
                        return p.getNombre().toLowerCase().contains(palabraFiltro);
                    });
                }
            });
            SortedList<ProfesorExt> listaOrdenada = new SortedList<>(filtroProfesorExt);
            listaOrdenada.comparatorProperty().bind(tvProfExterno.comparatorProperty());
            tvProfExterno.setItems(listaOrdenada);
        }
    }

    public void cerrarVentana() {
        ((Stage) tfBusquedaProfExt.getScene().getWindow()).close();
    }

    public boolean esRegistroExitoso() {
        return registroExitoso;
    }

    private void registrarOfertaColabExt() {
        ProfesorExt profesorExtSeleccionado = tvProfExterno.getSelectionModel().getSelectedItem();
        if (profesorExtSeleccionado == null) {
            Utils.mostrarAlertaSimple("Error", "Selecciona un profesor externo.", Alert.AlertType.WARNING);
        } else {
            ofertaExt.setIdProfesorExt(profesorExtSeleccionado.getIdProfesorExt());
            HashMap<String, Object> respuesta = OfertaColabExtDAO.registrarOfertaColabExt(ofertaExt,
                    profesorExtSeleccionado.getIdProfesorExt());
            if (!((boolean) respuesta.get(Constantes.KEY_ERROR))) {
                Utils.mostrarAlertaSimple("Oferta de colaboración registrada",
                        "" + respuesta.get(Constantes.KEY_MENSAJE),
                        Alert.AlertType.INFORMATION);
                HashMap<String, Object> resultado = OfertaColabExtDAO.obtenerOfertasColabExt();
                Integer idOfertaColabExt = (Integer) resultado.get("idOfertaColabExt");
                ofertaExt.setIdOfertaColabExt(idOfertaColabExt);
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
        registrarOfertaColabExt();
    }
}