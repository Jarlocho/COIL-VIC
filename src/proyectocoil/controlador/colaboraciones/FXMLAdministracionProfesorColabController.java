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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.ProfesorDAO;
import proyectocoil.modelo.pojo.Colaboracion;
import proyectocoil.modelo.pojo.ProfesorExt;
import proyectocoil.modelo.pojo.ProfesorUV;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLAdministracionProfesorColabController implements Initializable {
    @FXML
    private Label lbColab;
    @FXML
    private TableView<Object> tvProfesor;
    @FXML
    private TableColumn<Object, String> colNombre;
    @FXML
    private TableColumn<Object, String> colCorreo;
    @FXML
    private TableColumn<Object, String> colTitulo;
    private Colaboracion colaboracionSeleccionada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
    }

    public void inicializarValores(Colaboracion colaboracion) {
        this.colaboracionSeleccionada = colaboracion;
        lbColab.setText(colaboracion.getNombreColab());
        cargarProfesores();
    }

    private void cargarProfesores() {
        List<Object> profesores = new ArrayList<>();
        HashMap<String, Object> respuesta = ProfesorDAO.obtenerProfesoresPorColaboracion(colaboracionSeleccionada.getIdColaboracion());
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            ArrayList<ProfesorUV> profesoresUV = (ArrayList<ProfesorUV>) respuesta.get("profesoresUV");
            ArrayList<ProfesorExt> profesoresExt = (ArrayList<ProfesorExt>) respuesta.get("profesoresExt");
            profesores.addAll(profesoresUV);
            profesores.addAll(profesoresExt);
            tvProfesor.setItems(FXCollections.observableArrayList(profesores));
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }

    private void irBusquedaProfExtColab() {
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/colaboraciones/FXMLBusquedaProfExtColab.fxml");
            Parent root = loader.load();
            FXMLBusquedaProfExtColabController controlador = loader.getController();
            controlador.inicializarValores(this);
            Scene escena = new Scene(root);
            escenario.setTitle("Búsqueda de Profesor Externo para Colaboración");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void irBusquedaProfUVColab() {
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/colaboraciones/FXMLBusquedaProfUVColab.fxml");
            Parent root = loader.load();
            FXMLBusquedaProfUVColabController controlador = loader.getController();
            controlador.inicializarValores(this);
            Scene escena = new Scene(root);
            escenario.setTitle("Búsqueda de Profesor UV para Colaboración");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void actualizarColaboracionConNuevoProfesor(Object profesor, boolean esProfesorUV) {
        int idProfesor = esProfesorUV ? ((ProfesorUV) profesor).getIdProfesor() : ((ProfesorExt) profesor).getIdProfesorExt();
        HashMap<String, Object> respuesta = ProfesorDAO.actualizarColaboracionConProfesorNuevo(colaboracionSeleccionada.getIdColaboracion(), idProfesor, esProfesorUV);
        if (!(boolean) respuesta.get(Constantes.KEY_ERROR)) {
            Utils.mostrarAlertaSimple("Éxito", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.INFORMATION);
            cargarProfesores();
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }

    public void cerrarVentana() {
        ((Stage) tvProfesor.getScene().getWindow()).close();
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void btnClicReemplazar(ActionEvent event) {
        Object profesorSeleccionado = tvProfesor.getSelectionModel().getSelectedItem();
        if (profesorSeleccionado instanceof ProfesorUV) {
            irBusquedaProfUVColab();
        } else if (profesorSeleccionado instanceof ProfesorExt) {
            irBusquedaProfExtColab();
        } else {
            Utils.mostrarAlertaSimple("Error", "Selecciona un profesor.", Alert.AlertType.WARNING);
        }
    }
}