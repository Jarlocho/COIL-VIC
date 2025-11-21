/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 13/06/2024
 * Descripción: Muestra un formulario con algunos campos de información que componen una colaboración
 * Versión: 1.0
 */

package proyectocoil.controlador.colaboraciones;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.CatalogoDAO;
import proyectocoil.modelo.pojo.AreaAcademica;
import proyectocoil.modelo.pojo.Colaboracion;
import proyectocoil.modelo.pojo.Dependencia;
import proyectocoil.modelo.pojo.ExperienciaEducativa;
import proyectocoil.modelo.pojo.ProgramaEducativo;
import proyectocoil.utilidades.Utils;



public class FXMLRegistroColaboracionController implements Initializable {

    
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaTermino;
    @FXML
    private ComboBox<AreaAcademica> cbAreaAcademica;
    @FXML
    private ComboBox<Dependencia> cbDependencia;
    @FXML
    private ComboBox<ProgramaEducativo> cbProgramaEducativo;
    @FXML
    private ComboBox<ExperienciaEducativa> cbExperienciaEducativa;
    @FXML
    private Label lbArchivoSeleccionado;
    
    private File planDeActividades;
    @FXML
    private TextField tfNombre;

    private ObservableList<ExperienciaEducativa> experienciasEducativas;
    private ObservableList<ProgramaEducativo> programasEducativos;
    private ObservableList<Dependencia> dependencias;
    private ObservableList<AreaAcademica> areasAcademicas;
    private ObservableList<String> tiposOferta;
    
    @FXML
    private ComboBox<String> cbTipoOferta;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarAreasAcademicas();
        configurarSeleccionarCB();
        cargarTiposOferta();
    }    

    public void cargarTiposOferta() {
        tiposOferta = FXCollections.observableArrayList();
        tiposOferta.add("Oferta UV");
        tiposOferta.add("Oferta Externa");
        tiposOferta.add("Ninguna");
        cbTipoOferta.setItems(tiposOferta);
    }
    
    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    public void cerrarVentana() {
        ((Stage) lbArchivoSeleccionado.getScene().getWindow()).close();
    }
    
    @FXML
    private void btnClicSiguiente(ActionEvent event) {
        if (validarCampos()) {
            switch (cbTipoOferta.getSelectionModel().getSelectedItem()) {
                case "Ninguna":
                    irSeleccionParticipantes();
                    break;
                    
                default:
                    irSeleccionOferta();
                    break;
                    
            }
            
        }
        
        
    }

    private void irSeleccionOferta() {
        Colaboracion colaboracion = obtenerInformacionColaboracion();
        try {
            
            Stage escenario = (Stage) lbArchivoSeleccionado.getScene().getWindow();
            FXMLLoader loader = Utils.obtenerLoader("vista/colaboraciones/FXMLSeleccionOferta.fxml");
            Parent root = loader.load();
            FXMLSeleccionOfertaController controlador = loader.getController();
            controlador.inicializarValores(colaboracion, cbTipoOferta.getSelectionModel().getSelectedItem());
            Scene escena = new Scene(root);
            escenario.setTitle("Selección de " + cbTipoOferta.getSelectionModel().getSelectedItem());
            escenario.setScene(escena);
            escenario.show();
            
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
    
    private void irSeleccionParticipantes() {
        Colaboracion colaboracion = obtenerInformacionColaboracion();
        try {
            
            Stage escenario = (Stage) lbArchivoSeleccionado.getScene().getWindow();
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
    
    

    public void inicializarValores() {
    }

    @FXML
    private void btnClicSubirPlan(ActionEvent event) {
        lbArchivoSeleccionado.setText("No ha seleccionado ningún archivo");
        lbArchivoSeleccionado.setStyle("");
        FileChooser dialogoSeleccion = new FileChooser();
        dialogoSeleccion.setTitle("Seleccionar Plan de Actividades");
        String etiquetaTipoDato = "Archivo PDF(*.pdf)";
        FileChooser.ExtensionFilter filtroPDF = new FileChooser.ExtensionFilter(etiquetaTipoDato, "*.pdf");
        dialogoSeleccion.getExtensionFilters().add(filtroPDF);
        Stage escenarioActual = (Stage) lbArchivoSeleccionado.getScene().getWindow();
        planDeActividades = dialogoSeleccion.showOpenDialog(escenarioActual);
        if (planDeActividades != null) {
            lbArchivoSeleccionado.setText("Archivo Cargado");
            lbArchivoSeleccionado.setStyle("-fx-text-fill: green;");
        }

    }
    
    private Colaboracion obtenerInformacionColaboracion() {
        Colaboracion colab = new Colaboracion();
        colab.setNombreColab(tfNombre.getText());
        colab.setFechaInicio(dpFechaInicio.getValue().toString());
        colab.setFechaTermino(dpFechaTermino.getValue().toString());
        colab.setEstado("Acordada");
        colab.setIdExperienciaEducativa(cbExperienciaEducativa.getSelectionModel().getSelectedItem().getIdExperienciaEducativa());
        
        try {
            byte [] planBytes = Files.readAllBytes(planDeActividades.toPath());
            colab.setPlanDeActividades(planBytes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return colab;
    }
    
    private void cargarAreasAcademicas() {
        areasAcademicas = FXCollections.observableArrayList();
        areasAcademicas.addAll((ArrayList<AreaAcademica>)CatalogoDAO.obtenerAreasAcademicas().get("areasAcademicas"));
        cbAreaAcademica.setItems(areasAcademicas);
    }
    
    public void configurarSeleccionarCB() {
        cbAreaAcademica.valueProperty().addListener(new ChangeListener<AreaAcademica>() {
            @Override
            public void changed(ObservableValue<? extends AreaAcademica> observable, AreaAcademica oldValue, AreaAcademica newValue) {
                if(newValue != null) {
                    cargarDependencias(newValue.getIdAreaAcademica());
                } else {
                    cargarDependencias(-1);
                }
            }
            
        });
        
        cbDependencia.valueProperty().addListener(new ChangeListener<Dependencia>() {
            @Override
            public void changed(ObservableValue<? extends Dependencia> observable, Dependencia oldValue, Dependencia newValue) {
                if(newValue != null) {
                    cargarProgramasEducativos(newValue.getIdDependencia());
                } else {
                    cargarProgramasEducativos(-1);
                }
            }
            
        });
        cbProgramaEducativo.valueProperty().addListener(new ChangeListener<ProgramaEducativo>() {
            @Override
            public void changed(ObservableValue<? extends ProgramaEducativo> observable, ProgramaEducativo oldValue, ProgramaEducativo newValue) {
                if(newValue != null) {
                    cargarExperienciasEducativas(newValue.getIdProgramaEducativo());
                } else {
                    cargarExperienciasEducativas(-1);
                }
            }
            
        });
    }
    
    private void cargarDependencias(int idAreaAcademica) {
        dependencias = FXCollections.observableArrayList();
        dependencias.addAll((ArrayList<Dependencia>)CatalogoDAO.obtenerDependencias(idAreaAcademica).get("dependencias"));
        cbDependencia.setItems(dependencias);
    }
    
    private void cargarProgramasEducativos(int idDependencia) {
        programasEducativos = FXCollections.observableArrayList();
        programasEducativos.addAll((ArrayList<ProgramaEducativo>)CatalogoDAO.obtenerProgramasEducativos(idDependencia).get("programasEducativos"));
        cbProgramaEducativo.setItems(programasEducativos);
    }
    
    private void cargarExperienciasEducativas(int idProgramaEducativo) {
        
        experienciasEducativas = FXCollections.observableArrayList();
        experienciasEducativas.addAll((ArrayList<ExperienciaEducativa>)CatalogoDAO.obtenerExperienciasEducativas(idProgramaEducativo).get("experienciasEducativas"));
        cbExperienciaEducativa.setItems(experienciasEducativas);
        
    }
    
    private boolean validarCampos() {
        if (tfNombre.getText().isEmpty()) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, llene el campo Nombre para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (cbExperienciaEducativa.getSelectionModel().getSelectedItem() == null) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, seleccione la Experiencia Educativa para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (dpFechaInicio.getValue() == null) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, llene el campo Fecha de Inicio para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (dpFechaTermino.getValue() == null) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, llene el campo Fecha de Término para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (planDeActividades == null) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, suba el plan de actividades para continuar.", Alert.AlertType.WARNING);
            return false;
        } else if (cbTipoOferta.getSelectionModel().getSelectedItem() == null) {
            Utils.mostrarAlertaSimple("Campos Vacíos", "Por favor, seleccione el tipo de oferta para continuar.", Alert.AlertType.WARNING);
            return false;
        } else {
            return Utils.validarFecha(dpFechaInicio.getValue(), dpFechaTermino.getValue());
        }
    }
    
}
