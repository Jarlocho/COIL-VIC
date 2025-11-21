/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 19/06/2024
 * Descripción: Muestra una lista de las colaboraciones
   ('Acordadas' y 'Activas' o 'Concluidas') existentes en la base que pueden ser seleccionadas
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.ColaboracionDAO;
import proyectocoil.modelo.pojo.Colaboracion;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;


public class FXMLSeleccionColaboracionController implements Initializable {

    @FXML
    private TableView<Colaboracion> tvColaboraciones;
    @FXML
    private TableColumn colFechaInicio;
    @FXML
    private TableColumn colFechaTermino;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colExperienciaEducativa;
    @FXML
    private TableColumn colProfesorUV;
    @FXML
    private TableColumn colProfesorExt;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private String motivo;
    private Integer idProfesorUV;
    
    public void inicializarValores(String motivo, Integer idProfesorUV) {
        this.motivo = motivo;
        this.idProfesorUV = idProfesorUV;
        configurarTabla();
    }
    
    private void configurarTabla() {
        colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        colExperienciaEducativa.setCellValueFactory(new PropertyValueFactory("nombreExperienciaEducativa"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        colFechaTermino.setCellValueFactory(new PropertyValueFactory("fechaTermino"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreColab"));
        colProfesorExt.setCellValueFactory(new PropertyValueFactory("nombreProfesorExt"));
        colProfesorUV.setCellValueFactory(new PropertyValueFactory("nombreProfesorUV"));
        
        switch (motivo) {
            case "Administrar estado":
                cargarDatosColaboracionesAdministrables();
                break;
            case "Subir evidencias":
                cargarDatosColaboracionesConcluidas();
                break;
        }
    }
    
    private ObservableList<Colaboracion> colaboraciones;
    
    private void cargarDatosColaboracionesAdministrables() {
        colaboraciones = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = ColaboracionDAO.ObtenerColaboracionesAdministrables(idProfesorUV);
        boolean isError =(boolean) respuesta.get(Constantes.KEY_ERROR);
        if(!isError){
            ArrayList<Colaboracion> colaboracionesBD = (ArrayList<Colaboracion>) respuesta.get("colaboraciones");
            colaboraciones.addAll(colaboracionesBD);
            tvColaboraciones.setItems(colaboraciones);   
        }else{
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }   
    }
    
    private void cargarDatosColaboracionesConcluidas() {
        colaboraciones = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = ColaboracionDAO.ObtenerColaboracionesConcluidas();
        boolean isError =(boolean) respuesta.get(Constantes.KEY_ERROR);
        if(!isError){
            ArrayList<Colaboracion> colaboracionesBD = (ArrayList<Colaboracion>) respuesta.get("colaboraciones");
            colaboraciones.addAll(colaboracionesBD);
            tvColaboraciones.setItems(colaboraciones);   
        }else{
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }   
    }
    
    @FXML
    private void btnClicSiguiente(ActionEvent event) {
        if (tvColaboraciones.getSelectionModel().getSelectedItem() != null) {
            switch (motivo) {
                case "Administrar estado":
                    irAdministrarEstado();
                    break;
                case "Subir evidencias":
                    irSubirEvidencias();
                    break;
            }
        } else {
            Utils.mostrarAlertaSimple("Colaboración no seleccionada", "No se ha seleccionado ninguna colaboración de la lista, por favor vuelva a intentarlo.", Alert.AlertType.WARNING);
        }
    }
    
    private void irSubirEvidencias() {
        try {
            Stage escenario = (Stage) tvColaboraciones.getScene().getWindow();
            FXMLLoader loader = Utils.obtenerLoader("vista/colaboraciones/FXMLSubirEvidencias.fxml");
            Parent root = loader.load();
            FXMLSubirEvidenciasController controlador = loader.getController();
            controlador.inicializarValores(tvColaboraciones.getSelectionModel().getSelectedItem().getIdColaboracion());
            Scene escena = new Scene(root);
            escenario.setTitle("Subir evidencias");
            escenario.setScene(escena);
            escenario.show();
            
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
    
    private void irAdministrarEstado() {
        try {
            Stage escenario = (Stage) tvColaboraciones.getScene().getWindow();
            FXMLLoader loader = Utils.obtenerLoader("vista/colaboraciones/FXMLAdministrarEstado.fxml");
            Parent root = loader.load();
            FXMLAdministrarEstadoController controlador = loader.getController();
            controlador.inicializarValores(tvColaboraciones.getSelectionModel().getSelectedItem());
            Scene escena = new Scene(root);
            escenario.setTitle("Administrar estado");
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
        ((Stage) tvColaboraciones.getScene().getWindow()).close();
    }

}
