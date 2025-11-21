/*
 * Autor: Jarly Hernández Romero
 * Fecha de Creación: 18/06/2024
 * Descripción: Despliega una tabla con los profesores uv registrados
 * Versión: 1.0
 */

package proyectocoil.controlador.ofertas;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import proyectocoil.modelo.dao.OfertaColabUVDAO;
import proyectocoil.modelo.pojo.OfertaColabUV;
import proyectocoil.modelo.pojo.ProfesorUV;
import proyectocoil.observador.ObservadorOfertasUV;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLModificarProfesorUVController implements Initializable {

    private OfertaColabUV ofertaEditable;
    private int idOfertaColabUV;
    private int idProfesorUV;
    private ObservadorOfertasUV observador;
    private ObservableList<ProfesorUV> profesoresUV;
    private OfertaColabUV OfertasUV;
    
    @FXML
    private TableView<ProfesorUV> tvProfesoresUV;
    @FXML
    private TableColumn colProfesor;
    @FXML
    private TableColumn colBoton;
    @FXML
    private TextField tfBusquedaProfesor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaProfesoresUV();
    }    

    public void inicializarValores(int idProfesorUV,OfertaColabUV ofertasUV ,int idOfertaColabUV, ObservadorOfertasUV observador) {
        this.idOfertaColabUV = idOfertaColabUV;
        this.observador = observador;
        this.OfertasUV = ofertasUV;
        this.idProfesorUV = idProfesorUV;
        cargarProfesores();
        configurarBusquedaProfesorUV();
    }
    
    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cargarProfesores(){
        profesoresUV = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = OfertaColabUVDAO.obtenerProfesoresUV();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<ProfesorUV> profesoresUVBD = (ArrayList<ProfesorUV>) respuesta.get("profesoresuv");
            profesoresUV.addAll(profesoresUVBD);
            tvProfesoresUV.setItems(profesoresUV);
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }
    
    public void configurarTablaProfesoresUV(){
     colProfesor.setCellValueFactory(new PropertyValueFactory("nombre"));
     colBoton.setCellFactory(new Callback<TableColumn<ProfesorUV, Void>, TableCell<ProfesorUV, Void>>(){
            @Override
            public TableCell<ProfesorUV, Void> call(TableColumn<ProfesorUV, Void> param) {
                final TableCell<ProfesorUV, Void> celda = new TableCell<ProfesorUV, Void>(){
                    private final Button btnSeleccionar = new Button("Seleccionar");
                    {
                        btnSeleccionar.setOnAction((event) ->{
                            ProfesorUV profesorUV = getTableView().getItems().get(getIndex());
                            actualizarProfesor(idOfertaColabUV, profesorUV.getIdProfesor());
                            
                        });
                    }
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnSeleccionar);
                        }
                    }
                    
                };
                return celda;
            }
        });
    }
    
    private void cerrarVentana() {
        ((Stage) tfBusquedaProfesor.getScene().getWindow()).close();
    }
    
    private void actualizarProfesor(int idOfertaColabUV, int idProfesorUV){
        HashMap<String, Object> respuesta = OfertaColabUVDAO.modificarProfesor(idOfertaColabUV, idProfesorUV);
        if (!((boolean) respuesta.get(Constantes.KEY_ERROR))) {
            Utils.mostrarAlertaSimple("Profesor actualizado", "El profesor ha sido actualizado con éxito", Alert.AlertType.INFORMATION);
            observador.operacionExitosa("Actualización", "");
            cerrarVentana();
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
        
    }
    
     private void configurarBusquedaProfesorUV() {
        if (profesoresUV.size() > 0) {
            FilteredList<ProfesorUV> filtroProfesorUV = new FilteredList<>(profesoresUV, p -> true);
            tfBusquedaProfesor.textProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroProfesorUV.setPredicate(p -> {
                        //Evaluacion cuando la cadena es vacia
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        //Evaluacion predicado nombre
                        String palabraFiltro = newValue.toLowerCase();
                        if (p.getNombre().toLowerCase().contains(palabraFiltro)) {
                            return true;
                        }else
                       
                        return false;
                    });
                }

            });
            SortedList<ProfesorUV> listaOrdenada = new SortedList<>(filtroProfesorUV);
            listaOrdenada.comparatorProperty().bind(tvProfesoresUV.comparatorProperty());
            tvProfesoresUV.setItems(listaOrdenada);
        }
    }
}