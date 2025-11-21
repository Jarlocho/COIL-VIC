/*
 * Autor: Jarly Hernández Romero
 * Fecha de Creación: 19/06/2024
 * Descripción: Muestra los datos de la oferta uv seleccionada
 * Versión: 1.0
 */

package proyectocoil.controlador.ofertas;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import proyectocoil.modelo.pojo.OfertaColabUV;

public class FXMLDatosOfertaColaboracionUVController implements Initializable {

    private OfertaColabUV ofertaSeleccionadaUV;

    @FXML
    private Label lbTema;
    @FXML
    private Label lbFechaInicio;
    @FXML
    private Label lbIdioma;
    @FXML
    private Label lbFechaTermino;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void inicializarValores(OfertaColabUV ofertaSeleccionadaUV) {
        this.ofertaSeleccionadaUV = ofertaSeleccionadaUV;
        cargarInformacionOfertaUV(ofertaSeleccionadaUV);
    }

    @FXML
    private void btnClicCerrarDatos(ActionEvent event) {
        ((Stage) lbTema.getScene().getWindow()).close();
    }

    private void cargarInformacionOfertaUV(OfertaColabUV ofertaSeleccionadaUV) {
        lbTema.setText(ofertaSeleccionadaUV.getTema());
        lbFechaInicio.setText(ofertaSeleccionadaUV.getFechaInicio());
        lbFechaTermino.setText(ofertaSeleccionadaUV.getFechaTermino());
        lbIdioma.setText(ofertaSeleccionadaUV.getNombreIdioma());

    }
}