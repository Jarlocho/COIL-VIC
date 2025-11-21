/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 21/06/2024
 * Descripción: Despliega una lista con los profesores externos registrados en la base
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
import proyectocoil.modelo.dao.OfertaColabExtDAO;
import proyectocoil.modelo.dao.ProfesorExtDAO;
import proyectocoil.modelo.pojo.OfertaColabExt;
import proyectocoil.modelo.pojo.ProfesorExt;
import proyectocoil.observador.ObservadorOfertasExt;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLModificarProfesorExternoController implements Initializable {
    @FXML
    private TextField tfBusquedaProfesor;
    @FXML
    private TableColumn colProfesor;
    @FXML
    private TableColumn colBoton;
    @FXML
    private TableView<ProfesorExt> tvProfesoresExt;


    private OfertaColabExt ofertaEditable;
    private int idOfertaColabExt;
    private int idProfesorExt;
    private ObservadorOfertasExt observador;
    private ObservableList<ProfesorExt> profesoresExt;
    private OfertaColabExt OfertasExt;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaProfesoresExt();
    }    

    void inicializarValores(int idProfesorExt,OfertaColabExt ofertasExt ,int idOfertaColabExt, ObservadorOfertasExt observador) {
        this.idOfertaColabExt = idOfertaColabExt;
        this.observador = observador;
        this.OfertasExt = ofertasExt;
        this.idProfesorExt = idProfesorExt;
        cargarProfesores();
        configurarBusquedaProfesorExt();
    }
    
    private void cargarProfesores(){
        profesoresExt = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = ProfesorExtDAO.obtenerProfesoresExt();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<ProfesorExt> profesoresExtBD = (ArrayList<ProfesorExt>) respuesta.get("profesoresExt");
            profesoresExt.addAll(profesoresExtBD);
            tvProfesoresExt.setItems(profesoresExt);
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }
    
    public void configurarTablaProfesoresExt(){
    colProfesor.setCellValueFactory(new PropertyValueFactory("nombre"));
    colBoton.setCellFactory(new Callback<TableColumn<ProfesorExt, Void>, TableCell<ProfesorExt, Void>>(){
            @Override
            public TableCell<ProfesorExt, Void> call(TableColumn<ProfesorExt, Void> param) {
                final TableCell<ProfesorExt, Void> celda = new TableCell<ProfesorExt, Void>(){
                    private final Button btnSeleccionar = new Button("Seleccionar");
                    {
                        btnSeleccionar.setOnAction((event) ->{
                            ProfesorExt profesorExt = getTableView().getItems().get(getIndex());
                            actualizarProfesor(idOfertaColabExt, profesorExt.getIdProfesorExt());
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
    
    private void actualizarProfesor(int idOfertaColabExt, int idProfesorExt){
        HashMap<String, Object> respuesta = OfertaColabExtDAO.modificarProfesor(idOfertaColabExt, idProfesorExt);
        if (!((boolean) respuesta.get(Constantes.KEY_ERROR))) {
            Utils.mostrarAlertaSimple("Profesor actualizado", "El profesor ha sido actualizado con éxito", Alert.AlertType.INFORMATION);
            observador.operacionExitosa("Actualización", "");
            cerrarVentana();
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }
    
    private void configurarBusquedaProfesorExt() {
        if (profesoresExt.size() > 0) {
            FilteredList<ProfesorExt> filtroProfesorExt = new FilteredList<>(profesoresExt, p -> true);
            tfBusquedaProfesor.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filtroProfesorExt.setPredicate(p -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String palabraFiltro = newValue.toLowerCase();
                        if (p.getNombre().toLowerCase().contains(palabraFiltro)) {
                            return true;
                        }else
                       
                        return false;
                    });
                }

            });
            SortedList<ProfesorExt> listaOrdenada = new SortedList<>(filtroProfesorExt);
            listaOrdenada.comparatorProperty().bind(tvProfesoresExt.comparatorProperty());
            tvProfesoresExt.setItems(listaOrdenada);
        }
    }
    
    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        ((Stage) tfBusquedaProfesor.getScene().getWindow()).close();
    }
    
}