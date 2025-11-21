/*
 * Autor: Zaid Alexis Rodríguez Huescas
 * Fecha de Creación: 13/06/2024
 * Descripción: Muestra los profesores externos registrados para manipular su información
 * Versión: 1.0
 */

package proyectocoil.controlador.profesores;
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
import proyectocoil.modelo.dao.ProfesorExtDAO;
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
    private boolean registroExitoso = false;
    private boolean modoModificacion = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    public void setModoModificacion(boolean modo) {
        this.modoModificacion = modo;
    }

    public void inicializarValores() {
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

    private void irModificacionProfExt(ProfesorExt profesorExtSeleccionado){
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/profesores/FXMLModificacionProfExterno.fxml");
            Parent root = loader.load();
            FXMLModificacionProfExternoController controlador = loader.getController();
            controlador.inicializarValores(profesorExtSeleccionado);
            Scene escena = new Scene(root);
            escenario.setTitle("Modificación de Profesor Externo");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
            if (controlador.esModificacionExitosa()) {
                cerrarVentana();
            }
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
        ProfesorExt profesorExtSeleccionado = tvProfExterno.getSelectionModel().getSelectedItem();
        if (profesorExtSeleccionado != null) {
            if (modoModificacion) {
                irModificacionProfExt(profesorExtSeleccionado);
            }
        } else {
            Utils.mostrarAlertaSimple("Error", "Selecciona un profesor externo.", Alert.AlertType.WARNING);
        }
    }
}