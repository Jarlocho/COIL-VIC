/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 16/06/2024
 * Descripción: Muestra una lista con las ofertas (pueden ser UV o Externas)
   existentes en la base que pueden ser seleccionadas para el registro de una colaboración
 * Versión: 1.0
 */

package proyectocoil.controlador.colaboraciones;

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
import javafx.stage.Stage;
import proyectocoil.modelo.dao.OfertaColabExtDAO;
import proyectocoil.modelo.dao.OfertaColabUVDAO;
import proyectocoil.modelo.pojo.Colaboracion;
import proyectocoil.modelo.pojo.OfertaColabExt;
import proyectocoil.modelo.pojo.OfertaColabUV;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;


public class FXMLSeleccionOfertaController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private TableColumn colProfesor;
    @FXML
    private TableColumn colTema;
    @FXML
    private TableColumn colIdioma;
    @FXML
    private TableColumn colFechaInicio;
    @FXML
    private TableColumn colFechaTermino;

    private Colaboracion colaboracion;
    private String tipoOferta;
    @FXML
    private TableView<Object> tvOfertas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public void inicializarValores(Colaboracion colaboracion, String tipoOferta) {
        this.colaboracion = colaboracion;
        this.tipoOferta = tipoOferta;
        configurarVentana();
    }
    
    private void configurarVentana(){
        lbTitulo.setText("Selección de " + tipoOferta);
        colTema.setCellValueFactory(new PropertyValueFactory("tema"));
        colIdioma.setCellValueFactory(new PropertyValueFactory("nombreIdioma"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        colFechaTermino.setCellValueFactory(new PropertyValueFactory("fechaTermino"));
        switch (tipoOferta) {
            case "Oferta UV":
                colProfesor.setCellValueFactory(new PropertyValueFactory("nombreProfesorUV"));
                cargarDatosOfertasUV();
                break;
            case "Oferta Externa":
                colProfesor.setCellValueFactory(new PropertyValueFactory("nombreProfesorExt"));
                cargarDatosOfertasExt();
                break;
        }
        
    }
    
    private ObservableList<OfertaColabUV> ofertasUV;
    
    private void cargarDatosOfertasUV() {
        ofertasUV = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = OfertaColabUVDAO.obtenerOfertasColabUV();
        boolean isError =(boolean) respuesta.get(Constantes.KEY_ERROR);
        if(!isError){
            ArrayList<OfertaColabUV> ofertasUVBD = (ArrayList<OfertaColabUV>) respuesta.get("ofertasUV");
            ofertasUV.addAll(ofertasUVBD);
            ObservableList<Object> objetosList = (ObservableList<Object>) (ObservableList<?>) ofertasUV;
            tvOfertas.setItems(objetosList);   
        }else{
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }   
    }
    
    private ObservableList<OfertaColabExt> ofertasExt;
    private void cargarDatosOfertasExt() {
        ofertasExt = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = OfertaColabExtDAO.obtenerOfertasColabExt();
        boolean isError =(boolean) respuesta.get(Constantes.KEY_ERROR);
        if(!isError){
            ArrayList<OfertaColabExt> ofertasExtBD = (ArrayList<OfertaColabExt>) respuesta.get("ofertasExt");
            ofertasExt.addAll(ofertasExtBD);
            ObservableList<Object> objetosList = (ObservableList<Object>) (ObservableList<?>) ofertasExt;
            tvOfertas.setItems(objetosList);   
        }else{
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }   
    }
    
    @FXML
    private void btnClicSiguiente(ActionEvent event) {
        Object oferta = tvOfertas.getSelectionModel().getSelectedItem();
        if (oferta != null) {
            switch (tipoOferta) {
                case "Oferta UV":
                    OfertaColabUV ofertaColabUV = (OfertaColabUV) oferta;
                    colaboracion.setIdOfertaColabUV(ofertaColabUV.getIdOfertaColabUV());
                    colaboracion.setIdProfesorUV(ofertaColabUV.getIdProfesorUV());
                    irSeleccionParticipantes();
                    break;
                case "Oferta Externa":
                    OfertaColabExt ofertaColabExt = (OfertaColabExt) oferta;
                    colaboracion.setIdOfertaColabExt(ofertaColabExt.getIdOfertaColabExt());
                    colaboracion.setIdProfesorExt(ofertaColabExt.getIdProfesorExt());
                    irSeleccionParticipantes();
                    break;
            }
        } else {
            Utils.mostrarAlertaSimple("Oferta no seleccionada", "No se ha seleccionado ninguna oferta de la lista, por favor vuelva a intentarlo.", Alert.AlertType.WARNING);
        }
    }

    private void irSeleccionParticipantes() {
        try {
            
            Stage escenario = (Stage) lbTitulo.getScene().getWindow();
            FXMLLoader loader = Utils.obtenerLoader("vista/colaboraciones/FXMLSeleccionParticipantes.fxml");
            Parent root = loader.load();
            FXMLSeleccionParticipantesController controlador = loader.getController();
            controlador.inicializarValores(colaboracion, null);
            Scene escena = new Scene(root);
            escenario.setTitle("Selección de Participantes");
            escenario.setScene(escena);
            escenario.show();
            
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
    
    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    public void cerrarVentana() {
        ((Stage) lbTitulo.getScene().getWindow()).close();
    }
    
}
