/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 13/06/2024
 * Descripción: Muestra una lista con los participantes necesarios para una colaboración
   (profesor UV, profesor Externo y estudiantes)
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.AsociaColabEstudianteDAO;
import proyectocoil.modelo.dao.ColaboracionDAO;
import proyectocoil.modelo.dao.EstudianteDAO;
import proyectocoil.modelo.dao.ProfesorExtDAO;
import proyectocoil.modelo.dao.ProfesorUVDAO;
import proyectocoil.modelo.pojo.Colaboracion;
import proyectocoil.modelo.pojo.Estudiante;
import proyectocoil.modelo.pojo.ProfesorExt;
import proyectocoil.modelo.pojo.ProfesorUV;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLSeleccionParticipantesController implements Initializable {
    @FXML
    private TableView<Object> tvParticipantes;
    @FXML
    private TableColumn colParticipantes;
    @FXML
    private Label lbTitulo;

    Colaboracion colaboracion;
    
    ObservableList<Estudiante> estudiantesSeleccionados;
    
    @FXML
    private Button btnSiguiente;
    @FXML
    private TableColumn colCampo1;
    @FXML
    private TableColumn colCampo2;
    @FXML
    private Label lbInstrucciones;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    
    
    void inicializarValores(Colaboracion colaboracion, ObservableList<Estudiante> estudiantesSeleccionados) {
        this.colaboracion = colaboracion;
        this.estudiantesSeleccionados = estudiantesSeleccionados;
        configurarVentana();
        
    }
    
    private void configurarVentana() {
        
        colParticipantes.setCellValueFactory(new PropertyValueFactory("nombre"));
        colCampo1.setCellValueFactory(new PropertyValueFactory("titulo"));
        colCampo1.setText("Título");
        colCampo2.setCellValueFactory(new PropertyValueFactory("correo"));
        colCampo2.setText("Correo");
        if (colaboracion.getIdProfesorUV() == null) {
            colParticipantes.setText("Profesor UV");
            cargarDatosProfesoresUV();
            lbTitulo.setText("Selección de Profesor UV");
            lbInstrucciones.setText("Seleccione el Profesor UV que desea asociar a la colaboración.");
        } else if (colaboracion.getIdProfesorExt() == null) {
            colParticipantes.setText("Profesor Ext");
            cargarDatosProfesoresExt();
            lbTitulo.setText("Selección de Profesor Externo");
            lbInstrucciones.setText("Seleccione el Profesor Externo que desea asociar a la colaboración.");
        } else {
            lbInstrucciones.setText("Seleccione los estudiantes que desea asociar a la colaboración.\nPresione la tecla \"ctrl\" para seleccionar varios al mismo tiempo.");
            lbTitulo.setText("Selección de Estudiantes");
            tvParticipantes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            colParticipantes.setText("Estudiante");
            colCampo1.setCellValueFactory(new PropertyValueFactory("matricula"));
            colCampo1.setText("Matrícula");
            colCampo2.setCellValueFactory(new PropertyValueFactory("correo"));
            colCampo2.setText("Correo");
            cargarDatosEstudiantes();
            btnSiguiente.setText("Guardar");
        }
    }
    
    private ObservableList<Estudiante> estudiantes;
    
    private void cargarDatosEstudiantes(){
        estudiantes = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = EstudianteDAO.obtenerEstudiantes();
        boolean isError =(boolean) respuesta.get(Constantes.KEY_ERROR);
        if(!isError){
            ArrayList<Estudiante> estudiantesBD = (ArrayList<Estudiante>) respuesta.get("estudiantes");
            estudiantes.addAll(estudiantesBD);
            ObservableList<Object> objetosList = (ObservableList<Object>) (ObservableList<?>) estudiantes;
            tvParticipantes.setItems(objetosList);   
        }else{
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }   
    }
    
    private ObservableList<ProfesorUV> profesoresUV;
    
    private void cargarDatosProfesoresUV(){
        profesoresUV = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = ProfesorUVDAO.obtenerProfesoresUV();
        boolean isError =(boolean) respuesta.get(Constantes.KEY_ERROR);
        if(!isError){
            ArrayList<ProfesorUV> profesoresUVBD = (ArrayList<ProfesorUV>) respuesta.get("profesoresUV");
            profesoresUV.addAll(profesoresUVBD);
            ObservableList<Object> objetosList = (ObservableList<Object>) (ObservableList<?>) profesoresUV;
            tvParticipantes.setItems(objetosList);   
        }else{
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }   
    }
    
    private ObservableList<ProfesorExt> profesoresExt;
    
    private void cargarDatosProfesoresExt(){
        profesoresExt = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = ProfesorExtDAO.obtenerProfesoresExt();
        boolean isError =(boolean) respuesta.get(Constantes.KEY_ERROR);
        if(!isError){
            ArrayList<ProfesorExt> profesoresExtBD = (ArrayList<ProfesorExt>) respuesta.get("profesoresExt");
            profesoresExt.addAll(profesoresExtBD);
            ObservableList<Object> objetosList = (ObservableList<Object>) (ObservableList<?>) profesoresExt;
            tvParticipantes.setItems(objetosList);   
        }else{
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }   
    }
    
    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    public void cerrarVentana() {
        ((Stage) lbTitulo.getScene().getWindow()).close();
    }
    
    @FXML
    private void btnClicSiguiente(ActionEvent event) {
        Object participante = tvParticipantes.getSelectionModel().getSelectedItem();
        if (participante != null) {
            if (colaboracion.getIdProfesorUV() == null) {
                ProfesorUV profesorUV = (ProfesorUV) participante;
                colaboracion.setIdProfesorUV(profesorUV.getIdProfesor());
                irSeleccionParticipantes();
            } else if (colaboracion.getIdProfesorExt() == null) {
                ProfesorExt profesorExt = (ProfesorExt) participante;
                colaboracion.setIdProfesorExt(profesorExt.getIdProfesorExt());
                irSeleccionParticipantes();
            } else {
                ObservableList<Object> objetosSeleccionados = tvParticipantes.getSelectionModel().getSelectedItems();
                ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList();
                for (Object obj : objetosSeleccionados) {
                    if (obj instanceof Estudiante) {
                        listaEstudiantes.add((Estudiante) obj);
                    }
                }
                estudiantesSeleccionados = listaEstudiantes;
                registrarColaboracion();
                cerrarVentana();
            }
        } else {
            Utils.mostrarAlertaSimple("Participante no seleccionado", "No se ha seleccionado ningún participante de la lista, por favor vuelva a intentarlo.", Alert.AlertType.WARNING);
        }
    }
    
    private void registrarColaboracion() {
        HashMap<String, Object> respuesta = ColaboracionDAO.guardarColaboracion(colaboracion);
        if(!((boolean) respuesta.get(Constantes.KEY_ERROR))) {
            Utils.mostrarAlertaSimple("Colaboración registrada", ""+respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.INFORMATION);
            HashMap<String, Object> resultado = ColaboracionDAO.ObtenerIdUltimaColaboracion();
            Integer idColaboracion = (Integer) resultado.get("idColaboracion");
            colaboracion.setIdColaboracion(idColaboracion);
            AsociaColabEstudianteDAO.GuardarEstudiantesDeColaboracion(colaboracion, estudiantesSeleccionados);
            cerrarVentana();
        } else{
            Utils.mostrarAlertaSimple("Error al guardar", ""+respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }

    private void irSeleccionParticipantes() {
        
        try {
            Stage escenarioPrincipal = (Stage) lbTitulo.getScene().getWindow();
            FXMLLoader loader = Utils.obtenerLoader("vista/colaboraciones/FXMLSeleccionParticipantes.fxml");
            Parent root = loader.load();
            FXMLSeleccionParticipantesController controlador = loader.getController();
            controlador.inicializarValores(colaboracion, estudiantesSeleccionados);
            Scene escenaPrincipal = new Scene(root);
            escenarioPrincipal.setTitle("Selección de Participantes");
            escenarioPrincipal.setScene(escenaPrincipal);
            escenarioPrincipal.show();
            
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
    
}
