/*
 * Autor: Jarly Hernández Romero
 * Fecha de Creación: 18/06/2024
 * Descripción: Despliega un formulario para modificar la oferta uv
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
import proyectocoil.modelo.dao.OfertaColabUVDAO;
import proyectocoil.modelo.pojo.Idioma;
import proyectocoil.modelo.pojo.OfertaColabUV;
import proyectocoil.observador.ObservadorOfertasUV;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLModificarOfertaColabUVController implements Initializable {

    private OfertaColabUV ofertaEditable;
    private ObservableList<Idioma> idiomas;
    private ObservadorOfertasUV observador;
    private int idOfertaColabUV;

    @FXML
    private TextField tfTema;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private ComboBox<Idioma> cbIdioma;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarIdiomas();
    }

    public void inicializarValores(int idOfertaColabUV, OfertaColabUV ofertaEditable, ObservadorOfertasUV observador) {
        this.idOfertaColabUV = idOfertaColabUV;
        this.ofertaEditable = ofertaEditable;
        this.observador = observador;
        if (ofertaEditable != null) {
            cargarInformacionOfertaColabUV(this.ofertaEditable);
        }
    }

    @FXML
    private void btnClicGuardar(ActionEvent event) {
        if (validarCampos()) {
            OfertaColabUV oferta = obtenerOfertaColabUVInformacion();
            actualizarOferta(oferta);
        }
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cargarInformacionOfertaColabUV(OfertaColabUV ofertaUV) {
        tfTema.setText(ofertaUV.getTema());
        LocalDate fechaCalendarioInicio = LocalDate.parse(ofertaUV.getFechaInicio(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dpFechaInicio.setValue(fechaCalendarioInicio);
        LocalDate fechaCalendarioFin = LocalDate.parse(ofertaUV.getFechaTermino(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dpFechaFin.setValue(fechaCalendarioFin);
        cbIdioma.getSelectionModel().select(buscarIdIdioma(ofertaUV.getIdIdioma()));
    }

    private void actualizarOferta(OfertaColabUV ofertaUV) {
        HashMap<String, Object> respuesta = OfertaColabUVDAO.modificarOferta(ofertaUV, ofertaUV.getIdOfertaColabUV());
        if (!((boolean) respuesta.get(Constantes.KEY_ERROR))) {
            Utils.mostrarAlertaSimple("Oferta actualizada", "Los datos de la oferta " + ofertaUV.getTema() + " han sido actualizados con éxito", Alert.AlertType.INFORMATION);
            observador.operacionExitosa("Actualización", ofertaUV.getTema());
            cerrarVentana();
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }

    private void cerrarVentana() {
        ((Stage) tfTema.getScene().getWindow()).close();
    }

    private int buscarIdIdioma(int idIdioma) {
        for (int i = 0; i < idiomas.size(); i++) {
            if (idiomas.get(i).getIdIdioma() == idIdioma) {
                return i;
            }
        }
        return 0;
    }

    private void cargarIdiomas() {
        idiomas = FXCollections.observableArrayList();
        idiomas.addAll((ArrayList<Idioma>) IdiomaDAO.obtenerIdiomas().get("idiomas"));
        cbIdioma.setItems(idiomas);
    }

    private OfertaColabUV obtenerOfertaColabUVInformacion() {
        OfertaColabUV ofertaUV = null;
        if(validarCampos()){
            ofertaUV = new OfertaColabUV();
            ofertaUV.setIdOfertaColabUV(idOfertaColabUV);
            ofertaUV.setTema(tfTema.getText());
            ofertaUV.setIdIdioma(cbIdioma.getSelectionModel().getSelectedItem().getIdIdioma());
            ofertaUV.setFechaInicio(dpFechaInicio.getValue().toString());
            ofertaUV.setFechaTermino(dpFechaFin.getValue().toString());
        }
        return ofertaUV;
    }

    private boolean validarCampos() {
        if (cbIdioma.getSelectionModel().getSelectedItem() == null ) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, seleccione un idioma para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (dpFechaInicio.getValue() == null) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, seleccione una Fecha de Inicio desde su calendario para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (dpFechaFin.getValue() == null) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, seleccione una Fecha de Término desde su calendario para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (tfTema.getText().isEmpty()) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, llene el campo Tema para continuar.", Alert.AlertType.WARNING);
            return false;
        } else {
            return Utils.validarFecha(dpFechaInicio.getValue(), dpFechaFin.getValue());
        } 
    }
}