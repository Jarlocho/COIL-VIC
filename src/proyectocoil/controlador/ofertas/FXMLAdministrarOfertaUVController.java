/*
 * Autor: Jarly Hernández Romero
 * Fecha de Creación: 18/06/2024
 * Descripción: Llena la tabla con las distintas ofertas UV registradas
   además de agregar las funciones de la ventana (modificar oferta, eliminar, modificar profesor)
 * Versión: 1.0
 */

package proyectocoil.controlador.ofertas;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.OfertaColabUVDAO;
import proyectocoil.modelo.pojo.OfertaColabUV;
import proyectocoil.observador.ObservadorOfertasUV;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLAdministrarOfertaUVController implements Initializable, ObservadorOfertasUV {

    private ObservableList<OfertaColabUV> ofertasUv;

    @FXML
    private TableView<OfertaColabUV> tvOfertas;
    @FXML
    private TableColumn colTema;
    @FXML
    private TableColumn colFechaInicio;
    @FXML
    private TableColumn colFechaTermino;
    @FXML
    private TableColumn colIdioma;
    @FXML
    private TableColumn colProfesor;
    @FXML
    private Label lbTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();

    }

    public void inicializarValores() {
        cargarDatosOfertasUV();
    }

    private void configurarTabla() {
        colTema.setCellValueFactory(new PropertyValueFactory("tema"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        colFechaTermino.setCellValueFactory(new PropertyValueFactory("fechaTermino"));
        colIdioma.setCellValueFactory(new PropertyValueFactory("nombreIdioma"));
        colProfesor.setCellValueFactory(new PropertyValueFactory("nombreProfesorUV"));

    }

    private void cargarDatosOfertasUV() {
        ofertasUv = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = OfertaColabUVDAO.obtenerOfertasColabUV();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<OfertaColabUV> ofertasUvBD = (ArrayList<OfertaColabUV>) respuesta.get("ofertasUV");
            ofertasUv.addAll(ofertasUvBD);
            tvOfertas.setItems(ofertasUv);
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnClicEliminar(ActionEvent event) {
        OfertaColabUV ofertaSeleccionada = tvOfertas.getSelectionModel().getSelectedItem();
        if (ofertaSeleccionada != null) {
            boolean respuesta = Utils.mostrarAlertaConfirmacion("Confirmar eliminación", "¿Desea eliminar la oferta " + ofertaSeleccionada.getTema() + " " + "?");
            if (respuesta){
            eliminarOferta(ofertaSeleccionada);
            }
        } else {
            Utils.mostrarAlertaSimple("Selecciona una oferta", "Por favor. selecciona una oferta de la tabla para poder eliminarla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnClicVolver(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void btnClicModificarProfesor(ActionEvent event) {
        OfertaColabUV ofertaUVSeleccionada = tvOfertas.getSelectionModel().getSelectedItem();
        if(ofertaUVSeleccionada != null){
            irModificarProfesor(ofertaUVSeleccionada);
        }else{
            Utils.mostrarAlertaSimple("Selecciona una oferta", "Para actualizar el profesor de una oferta debe seleccionar una de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void btnClicModificarOferta(ActionEvent event) {
        OfertaColabUV ofertaUVSeleccionada = tvOfertas.getSelectionModel().getSelectedItem();
        if (ofertaUVSeleccionada != null) {
            irModificarOferta(ofertaUVSeleccionada);

        } else {
            Utils.mostrarAlertaSimple("Selecciona una Oferta", "Para actualizar la información de una oferta debe seleccionar una de la tabla", Alert.AlertType.WARNING);
        }
    }
    
    private void eliminarOferta(OfertaColabUV ofertaSeleccionada){
        HashMap<String, Object> respuesta = OfertaColabUVDAO.eliminarOfertas(ofertaSeleccionada.getIdOfertaColabUV());
        if (!((boolean) respuesta.get(Constantes.KEY_ERROR))) {
            Utils.mostrarAlertaSimple("Oferta eliminada", "Los datos de la oferta " + ofertaSeleccionada.getTema() + " " + " han sido eliminados con éxito", Alert.AlertType.INFORMATION);
            cargarDatosOfertasUV();
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }

    private void irModificarOferta(OfertaColabUV ofertaEditable) {
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertas/FXMLModificarOfertaColabUV.fxml");
            Parent root = loader.load();
            FXMLModificarOfertaColabUVController controlador = loader.getController();
            controlador.inicializarValores(ofertaEditable.getIdOfertaColabUV(), ofertaEditable, this);
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("Oferta");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void irModificarProfesor(OfertaColabUV ofertaEditable){
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertas/FXMLModificarProfesorUV.fxml");
            Parent root = loader.load();
            FXMLModificarProfesorUVController controlador = loader.getController();
            controlador.inicializarValores(ofertaEditable.getIdProfesorUV(),ofertaEditable,ofertaEditable.getIdOfertaColabUV(), this);
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("ProfesorUV");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void operacionExitosa(String tipoOperacion, String tema) {
        System.out.println("Operacion: " + tipoOperacion);
        System.out.println("Oferta: " + tema);
        cargarDatosOfertasUV();
    }

    private void cerrarVentana() {
        ((Stage) lbTitulo.getScene().getWindow()).close();
    }

}