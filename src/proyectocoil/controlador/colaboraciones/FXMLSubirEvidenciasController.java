/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 19/06/2024
 * Descripción: Muestra un botón para subir el archivo de las evidencias
   de la colaboración seleccionada y un campo para llenar su descripción
 * Versión: 1.0
 */

package proyectocoil.controlador.colaboraciones;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.ColaboracionDAO;
import proyectocoil.modelo.dao.EvidenciaDAO;
import proyectocoil.modelo.pojo.Evidencia;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;


public class FXMLSubirEvidenciasController implements Initializable {

    @FXML
    private Label lbArchivoSeleccionado;
    
    @FXML
    private TextField tfDescripcion;

    private Integer idColaboracion;
    private File evidenciaSeleccionada;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    public void inicializarValores(Integer idColaboracion) {
        this.idColaboracion = idColaboracion;
    }
    

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    public void cerrarVentana() {
        ((Stage) lbArchivoSeleccionado.getScene().getWindow()).close();
    }

    @FXML
    private void btnClicGuardar(ActionEvent event) {
        if (validarDatos()) {
            Evidencia evidencia = obtenerInformacionEvidencia(evidenciaSeleccionada);
            HashMap<String, Object> respuestaEvidencia = EvidenciaDAO.GuardarEvidencia(evidencia, idColaboracion);
            if (!((boolean) respuestaEvidencia.get(Constantes.KEY_ERROR))) {
                HashMap<String, Object> respuestaIdEvidencia = EvidenciaDAO.ObtenerIdUltimaEvidencia();
                Integer idEvidencia = (Integer) respuestaIdEvidencia.get("idEvidencia");
                ColaboracionDAO.subirEvidenciaColaboracion(idEvidencia, idColaboracion);
                Utils.mostrarAlertaSimple("Evidencia guardada", (String) respuestaEvidencia.get(Constantes.KEY_MENSAJE), Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                Utils.mostrarAlertaSimple("Error al gauardar", (String) respuestaEvidencia.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
            }
            
        } else {
            
        }
    }
    
    private boolean validarDatos() {
        if (!(evidenciaSeleccionada == null || tfDescripcion.getText().isEmpty())) {
            return true;
        } else {
            return false;
        }
    }
    private Evidencia obtenerInformacionEvidencia(File archivo) {
        Evidencia evidencia = new Evidencia();
        evidencia.setDescripcion(tfDescripcion.getText());
        try {
            byte [] evidenciaBytes = Files.readAllBytes(archivo.toPath());
            evidencia.setArchivo(evidenciaBytes);
            evidencia.setNombreArchivo(archivo.getName());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return evidencia;
    }
    
    @FXML
    private void btnClicSubirEvidencias(ActionEvent event) {
        lbArchivoSeleccionado.setText("No ha seleccionado ningún archivo");
        lbArchivoSeleccionado.setStyle("");
        FileChooser dialogoSeleccion = new FileChooser();
        dialogoSeleccion.setTitle("Seleccionar Evidencias");
        String etiquetaTipoDato = "Archivo PDF o ZIP(*.pdf, *.zip)";
        FileChooser.ExtensionFilter filtroPDF = new FileChooser.ExtensionFilter(etiquetaTipoDato, "*.pdf", "*.zip");
        dialogoSeleccion.getExtensionFilters().add(filtroPDF);
        Stage escenarioActual = (Stage) lbArchivoSeleccionado.getScene().getWindow();
        evidenciaSeleccionada = dialogoSeleccion.showOpenDialog(escenarioActual);
        if (evidenciaSeleccionada != null) {
            lbArchivoSeleccionado.setText("Archivo Cargado");
            lbArchivoSeleccionado.setStyle("-fx-text-fill: green;");
        }
    }
    
}
