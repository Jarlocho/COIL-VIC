/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 19/06/2024
 * Descripción: Muestra una lista con los estudiantes registrados en la base
   para actualizar la lista final de aquellos inscritos en la colaboración seleccionada
 * Versión: 1.0
 */

package proyectocoil.controlador.colaboraciones;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.AsociaColabEstudianteDAO;
import proyectocoil.modelo.dao.EstudianteDAO;
import proyectocoil.modelo.pojo.Colaboracion;
import proyectocoil.modelo.pojo.Estudiante;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;


public class FXMLActualizarEstudiantesController implements Initializable {

    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colMatricula;
    @FXML
    private TableColumn colCorreo;
    
    private ObservableList<Estudiante> estudiantes;
    private Colaboracion colaboracion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }    
    
    public void inicializarValores(Colaboracion colaboracion) {
        this.colaboracion = colaboracion;
        cargarDatosEstudiantes();
    }

    private void configurarTabla() {
        tvEstudiantes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
    }
    
    private void cargarDatosEstudiantes() {
        estudiantes = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = EstudianteDAO.obtenerEstudiantes();
        boolean isError =(boolean) respuesta.get(Constantes.KEY_ERROR);
        if(!isError){
            ArrayList<Estudiante> estudiantesBD = (ArrayList<Estudiante>) respuesta.get("estudiantes");
            estudiantes.addAll(estudiantesBD);
            tvEstudiantes.setItems(estudiantes);   
        }else{
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }  
    }
    
    @FXML
    private void btnClicSiguiente(ActionEvent event) {
        if (tvEstudiantes.getSelectionModel().getSelectedItems() != null) {
            if (Utils.mostrarAlertaConfirmacion("Confirmación", "¿Está seguro que desea actualizar la lista de estudiantes?")) {
                estudiantes = tvEstudiantes.getSelectionModel().getSelectedItems();
                AsociaColabEstudianteDAO.EliminarEstudiantesDeColaboracion(colaboracion);
                AsociaColabEstudianteDAO.GuardarEstudiantesDeColaboracion(colaboracion, estudiantes);
                Utils.mostrarAlertaSimple("Estudiantes actualizados", "Se ha actualizado exitosamente la lista de estudiantes", Alert.AlertType.INFORMATION);
                cerrarVentana();
            }
        }
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    public void cerrarVentana() {
        ((Stage) tvEstudiantes.getScene().getWindow()).close();
    }
    
    
    
    
}
