/*
 * Autor: Jarly Hernández Romero
 * Fecha de Creación: 19/06/2024
 * Descripción: Muestra los datos de la oferta externa seleccionada
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
import proyectocoil.modelo.pojo.OfertaColabExt;

public class FXMLDatosOfertaColaboracionExtController implements Initializable {

    private OfertaColabExt ofertaSeleccionadaExt;
    
    @FXML
    private Label lbTema;
    @FXML
    private Label lbFechaInicio;
    @FXML
    private Label lbFechaTermino;
    @FXML
    private Label lbIdioma;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void inicializarValores(OfertaColabExt ofertaSeleccionadaExt){
        this.ofertaSeleccionadaExt = ofertaSeleccionadaExt;
        cargarInformacionOfertaUV(ofertaSeleccionadaExt);
    }
    
    @FXML
    private void btnClicCerrarDatos(ActionEvent event) {
        ((Stage) lbTema.getScene().getWindow()).close();
    }
    
    
     private void cargarInformacionOfertaUV(OfertaColabExt OfertaSeleccionadaExt) {
        lbTema.setText(ofertaSeleccionadaExt.getTema());
        lbFechaInicio.setText(ofertaSeleccionadaExt.getFechaInicio());
        lbFechaTermino.setText(ofertaSeleccionadaExt.getFechaTermino());
        lbIdioma.setText(ofertaSeleccionadaExt.getNombreIdioma());
       
     }
}