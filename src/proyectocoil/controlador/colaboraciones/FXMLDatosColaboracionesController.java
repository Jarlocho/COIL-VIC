/*
 * Autor: Jarly Hernández Romero
 * Fecha de Creación: 20/06/2024
 * Descripción: Muestra los datos de la colaboración seleccionada
 * Versión: 1.0
 */

package proyectocoil.controlador.colaboraciones;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.ColaboracionDAO;
import proyectocoil.modelo.pojo.Colaboracion;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLDatosColaboracionesController implements Initializable {

    private Colaboracion colaboracionSeleccionada;

    @FXML
    private Label lbNombre;
    @FXML
    private Label lbFechaInicio;
    @FXML
    private Label lbFechaTermino;
    @FXML
    private Label lbMateria;
    @FXML
    private Label lbEstado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void inicializarValores(Colaboracion colaboracionSeleccionada){
        this.colaboracionSeleccionada = colaboracionSeleccionada;
        cargarInformacionColab(colaboracionSeleccionada);
    }

    @FXML
    private void btnClicCerrarDatos(ActionEvent event) {
        ((Stage) lbNombre.getScene().getWindow()).close();
    }
    
    private void cargarInformacionColab(Colaboracion colaboracionSeleccionada) {
        lbNombre.setText(colaboracionSeleccionada.getNombreColab());
        lbFechaInicio.setText(colaboracionSeleccionada.getFechaInicio());
        lbFechaTermino.setText(colaboracionSeleccionada.getFechaTermino());
        lbMateria.setText(colaboracionSeleccionada.getNombreExperienciaEducativa());
        lbEstado.setText(colaboracionSeleccionada.getEstado());

    }

    private void descargarPlan(int idColaboracion){
        HashMap<String, Object> resultado = ColaboracionDAO.obtenerPlanDeActividades(idColaboracion);
        boolean error = (boolean) resultado.get(Constantes.KEY_ERROR);
        
        if (!error) {
            byte[] planDeActividades = (byte[]) resultado.get("planDeActividades");
            
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Descargar plan de Actividades");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
            File file = fileChooser.showSaveDialog(lbNombre.getScene().getWindow());
            
            if (file != null) {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(planDeActividades);
                    Utils.mostrarAlertaSimple("Descarga completa", "El plan de actividades se ha descargado correctamente.", Alert.AlertType.INFORMATION);
                } catch (IOException e) {
                    e.printStackTrace();
                    Utils.mostrarAlertaSimple("Error", "Ocurrió un error al descargar el plan de actividades.", Alert.AlertType.ERROR);
                }
            }
        } else {
            Utils.mostrarAlertaSimple("Error", "Hubo un error al obtener el plan de actividades", Alert.AlertType.ERROR);
        }
    }
    
    
    @FXML
    private void btnClicDescargarPlan(ActionEvent event) {
       if (colaboracionSeleccionada != null) {
            descargarPlan(colaboracionSeleccionada.getIdColaboracion());
        } else {
            Utils.mostrarAlertaSimple("Error", "No se ha seleccionado ninguna colaboración.", Alert.AlertType.ERROR);
        }
    }
}