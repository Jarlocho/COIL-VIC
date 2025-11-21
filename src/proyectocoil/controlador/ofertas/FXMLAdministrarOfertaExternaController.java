/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 21/06/2024
 * Descripción: Muestra una lista con las distintas ofertas externas registradas
   en la base y funciones a hacer con ellas una vez seleccionada alguna (modificar Profesor y modificar oferta)
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.OfertaColabExtDAO;
import proyectocoil.modelo.pojo.OfertaColabExt;
import proyectocoil.observador.ObservadorOfertasExt;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLAdministrarOfertaExternaController implements Initializable, ObservadorOfertasExt {
    @FXML
    private TableView<OfertaColabExt> tvOfertas;
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

    private ObservableList<OfertaColabExt> ofertasExternas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }    

    public void inicializarValores() {
        cargarDatosOfertasExt();
    }
    
    private void configurarTabla() {
        colTema.setCellValueFactory(new PropertyValueFactory("tema"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        colFechaTermino.setCellValueFactory(new PropertyValueFactory("fechaTermino"));
        colIdioma.setCellValueFactory(new PropertyValueFactory("nombreIdioma"));
        colProfesor.setCellValueFactory(new PropertyValueFactory("nombreProfesorExt"));

    }
    
    private void cargarDatosOfertasExt() {
        ofertasExternas = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = OfertaColabExtDAO.obtenerOfertasColabExt();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<OfertaColabExt> ofertasExtBD = (ArrayList<OfertaColabExt>) respuesta.get("ofertasExt");
            ofertasExternas.addAll(ofertasExtBD);
            tvOfertas.setItems(ofertasExternas);
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void btnClicModificarOferta(ActionEvent event) {
        OfertaColabExt ofertaExtSeleccionada = tvOfertas.getSelectionModel().getSelectedItem();
        if (ofertaExtSeleccionada != null) {
            irModificarOferta(ofertaExtSeleccionada);
        } else {
            Utils.mostrarAlertaSimple("Selecciona una Oferta", "Para actualizar la información de una oferta debe seleccionar una de la tabla", Alert.AlertType.WARNING);
        }
    }

    private void irModificarOferta(OfertaColabExt ofertaEditable) {
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertas/FXMLModificarOfertaExterna.fxml");
            Parent root = loader.load();
            FXMLModificarOfertaExternaController controlador = loader.getController();
            controlador.inicializarValores(ofertaEditable.getIdOfertaColabExt(), ofertaEditable, this);
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("Oferta");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void btnClicModificarProfesor(ActionEvent event) {
        OfertaColabExt ofertaExtSeleccionada = tvOfertas.getSelectionModel().getSelectedItem();
        if(ofertaExtSeleccionada != null){
            irModificarProfesor(ofertaExtSeleccionada);
        }else{
            Utils.mostrarAlertaSimple("Selecciona una oferta", "Para actualizar el profesor de una oferta debe seleccionar una de la tabla", Alert.AlertType.WARNING);
        }
    }

    private void irModificarProfesor(OfertaColabExt ofertaEditable){
        try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertas/FXMLModificarProfesorExterno.fxml");
            Parent root = loader.load();
            FXMLModificarProfesorExternoController controlador = loader.getController();
            controlador.inicializarValores(ofertaEditable.getIdProfesorExt(),ofertaEditable,ofertaEditable.getIdOfertaColabExt(), this);
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("Profesores Externos");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void btnClicVolver(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        ((Stage) tvOfertas.getScene().getWindow()).close();
    }
    
    @FXML
    private void btnClicEliminar(ActionEvent event) {
        OfertaColabExt ofertaSeleccionada = tvOfertas.getSelectionModel().getSelectedItem();
        if (ofertaSeleccionada != null) {
            boolean respuesta = Utils.mostrarAlertaConfirmacion("Confirmar eliminación", "¿Desea eliminar la oferta " + ofertaSeleccionada.getTema() + " " + "?");
            if (respuesta) {
                eliminarOferta(ofertaSeleccionada);
            }
        } else {
            Utils.mostrarAlertaSimple("Selecciona una oferta", "Por favor. selecciona una oferta de la tabla para poder eliminarla", Alert.AlertType.WARNING);
        }
    }
    
    private void eliminarOferta(OfertaColabExt ofertaSeleccionada){
        HashMap<String, Object> respuesta = OfertaColabExtDAO.eliminarOferta(ofertaSeleccionada.getIdOfertaColabExt());
        if (!((boolean) respuesta.get(Constantes.KEY_ERROR))) {
            Utils.mostrarAlertaSimple("Oferta eliminada", "Los datos de la oferta " + ofertaSeleccionada.getTema() + " " + " han sido eliminados con éxito", Alert.AlertType.INFORMATION);
            cargarDatosOfertasExt();
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public void operacionExitosa(String tipoOperacion, String tema) {
        System.out.println("Operacion: " + tipoOperacion);
        System.out.println("Oferta: " + tema);
        cargarDatosOfertasExt();
    }
}