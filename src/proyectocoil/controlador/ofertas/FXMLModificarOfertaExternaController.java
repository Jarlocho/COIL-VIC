/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 21/06/2024
 * Descripción: Muestra un formulario con campos conteniendo la información de la oferta seleccionada
 * Versión: 1.0
 */

package proyectocoil.controlador.ofertas;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.IdiomaDAO;
import proyectocoil.modelo.dao.OfertaColabExtDAO;
import proyectocoil.modelo.pojo.Idioma;
import proyectocoil.modelo.pojo.OfertaColabExt;
import proyectocoil.observador.ObservadorOfertasExt;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;


public class FXMLModificarOfertaExternaController implements Initializable {

    @FXML
    private ComboBox<Idioma> cbIdioma;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private TextField tfTema;


    private OfertaColabExt ofertaEditable;
    private ObservableList<Idioma> idiomas;
    private ObservadorOfertasExt observador;
    private Integer idOfertaColabExt;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarIdiomas();
    }    

    void inicializarValores(Integer idOfertaColabExt, OfertaColabExt ofertaEditable, ObservadorOfertasExt observador) {
        this.idOfertaColabExt = idOfertaColabExt;
        this.ofertaEditable = ofertaEditable;
        this.observador = observador;
        if (ofertaEditable != null) {
            cargarInformacionOfertaColabExt(this.ofertaEditable);
        }
    }

    private void cargarIdiomas() {
        idiomas = FXCollections.observableArrayList();
        idiomas.addAll((ArrayList<Idioma>) IdiomaDAO.obtenerIdiomas().get("idiomas"));
        cbIdioma.setItems(idiomas);
    }
    
    private void cargarInformacionOfertaColabExt(OfertaColabExt ofertaExt) {
        tfTema.setText(ofertaExt.getTema());
        LocalDate fechaCalendarioInicio = LocalDate.parse(ofertaExt.getFechaInicio(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dpFechaInicio.setValue(fechaCalendarioInicio);
        LocalDate fechaCalendarioFin = LocalDate.parse(ofertaExt.getFechaTermino(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dpFechaFin.setValue(fechaCalendarioFin);
        cbIdioma.getSelectionModel().select(buscarIdIdioma(ofertaExt.getIdIdioma()));
    }
    
    private int buscarIdIdioma(int idIdioma) {
        for (int i = 0; i < idiomas.size(); i++) {
            if (idiomas.get(i).getIdIdioma() == idIdioma) {
                return i;
            }
        }
        return 0;
    }
    
    private void actualizarOferta(OfertaColabExt ofertaExt) {
        HashMap<String, Object> respuesta = OfertaColabExtDAO.modificarOferta(ofertaExt, ofertaExt.getIdOfertaColabExt());
        if (!((boolean) respuesta.get(Constantes.KEY_ERROR))) {
            Utils.mostrarAlertaSimple("Oferta actualizada", "Los datos de la oferta " + ofertaExt.getTema() + " han sido actualizados con éxito", Alert.AlertType.INFORMATION);
            observador.operacionExitosa("Actualización", ofertaExt.getTema());
            cerrarVentana();
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }
    
    private OfertaColabExt obtenerOfertaColabExtInformacion() {
        OfertaColabExt ofertaExt = null;
        if(validarCampos()){
            ofertaExt = new OfertaColabExt();
            ofertaExt.setIdOfertaColabExt(idOfertaColabExt);
            ofertaExt.setTema(tfTema.getText());
            ofertaExt.setIdIdioma(cbIdioma.getSelectionModel().getSelectedItem().getIdIdioma());
            ofertaExt.setFechaInicio(dpFechaInicio.getValue().toString());
            ofertaExt.setFechaTermino(dpFechaFin.getValue().toString());
        }
        
        return ofertaExt;
    }
    
    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        ((Stage) tfTema.getScene().getWindow()).close();
    }
    
    @FXML
    private void btnClicGuardar(ActionEvent event) {
        if (validarCampos()) {
            OfertaColabExt oferta = obtenerOfertaColabExtInformacion();
            actualizarOferta(oferta);
        }
    }
    
    private boolean validarCampos() {
        if (cbIdioma.getSelectionModel().getSelectedItem() == null ) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, llene el campo idioma para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (dpFechaInicio.getValue() == null) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, llene el campo Fecha de Inicio para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (dpFechaFin.getValue() == null) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, llene el campo Fecha de Término para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (tfTema.getText().isEmpty()) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, llene el campo Tema para continuar.", Alert.AlertType.WARNING);
            return false;
        } else {
            return Utils.validarFecha(dpFechaInicio.getValue(), dpFechaFin.getValue());
        } 
    }
}
