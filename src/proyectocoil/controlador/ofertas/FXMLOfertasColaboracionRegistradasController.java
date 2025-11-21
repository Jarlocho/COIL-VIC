/*
 * Autor: Jarly Hernández Romero
 * Fecha de Creación: 19/06/2024
 * Descripción: Muestra una tabla con las ofertas uv y externas registradas
 * Versión: 1.0
 */

package proyectocoil.controlador.ofertas;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import proyectocoil.modelo.dao.OfertaColabExtDAO;
import proyectocoil.modelo.dao.OfertaColabUVDAO;
 import proyectocoil.modelo.pojo.OfertaColabExt;
import proyectocoil.modelo.pojo.OfertaColabUV;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLOfertasColaboracionRegistradasController implements Initializable {
    
    private ObservableList<OfertaColabUV> ofertasUV;
    private ObservableList<OfertaColabExt> ofertasExt;
    
    @FXML
    private TableView<OfertaColabUV> tvOfertasColabUV;
    @FXML
    private TableColumn colTemaUV;
    @FXML
    private TableColumn colBotonUV;
    @FXML
    private TableView<OfertaColabExt> tvOfertasColabExt;
    @FXML
    private TableColumn colTemaExt;
    @FXML
    private TableColumn colBotonExt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaUV();
        configurarTablaExt();
    }    

    public void inicializarValores(){
        cargarTemasUV();
        cargarTemasExt();
    }
    
     private void cargarTemasUV() {
        ofertasUV = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = OfertaColabUVDAO.obtenerTemasOfertasUV();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<OfertaColabUV> ofertasUVBD = (ArrayList<OfertaColabUV>) respuesta.get("ofertasuv");
            ofertasUV.addAll(ofertasUVBD);
            tvOfertasColabUV.setItems(ofertasUV);
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }
    
    private void irDatosOfertaUV(OfertaColabUV ofertaUV){
         try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertas/FXMLDatosOfertaColaboracionUV.fxml");
            Parent root = loader.load();
            FXMLDatosOfertaColaboracionUVController controlador = loader.getController();
            controlador.inicializarValores(ofertaUV);
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("OfertasUV");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void configurarTablaUV(){
     colTemaUV.setCellValueFactory(new PropertyValueFactory("tema"));
     colBotonUV.setCellFactory(new Callback<TableColumn<OfertaColabUV, Void>, TableCell<OfertaColabUV, Void>>(){
            @Override
            public TableCell<OfertaColabUV, Void> call(TableColumn<OfertaColabUV, Void> param) {
                final TableCell<OfertaColabUV, Void> celda = new TableCell<OfertaColabUV, Void>(){
                    private final Button btnVerDatos = new Button("Ver datos");
                    {
                        btnVerDatos.setOnAction((event) ->{
                            OfertaColabUV ofertaUV = getTableView().getItems().get(getIndex());
                            irDatosOfertaUV(ofertaUV);
                        });
                    }
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnVerDatos);
                        }
                    }
                    
                };
                return celda;
            }
        });
    }
    
    private void cargarTemasExt() {
        ofertasExt = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = OfertaColabExtDAO.obtenerTemasOfertasExt();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<OfertaColabExt> ofertasExtBD = (ArrayList<OfertaColabExt>) respuesta.get("ofertasext");
            ofertasExt.addAll(ofertasExtBD);
            tvOfertasColabExt.setItems(ofertasExt);
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }
    
     private void irDatosOfertaExt(OfertaColabExt ofertaExt){
         try {
            Stage escenario = new Stage();
            FXMLLoader loader = Utils.obtenerLoader("vista/ofertas/FXMLDatosOfertaColaboracionExt.fxml");
            Parent root = loader.load();
            FXMLDatosOfertaColaboracionExtController controlador = loader.getController();
            controlador.inicializarValores(ofertaExt);
            Scene escena = new Scene(root);
            escenario.setScene(escena);
            escenario.setTitle("OfertasExt");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void configurarTablaExt(){
     colTemaExt.setCellValueFactory(new PropertyValueFactory("tema"));
     colBotonExt.setCellFactory(new Callback<TableColumn<OfertaColabExt, Void>, TableCell<OfertaColabExt, Void>>(){
            @Override
            public TableCell<OfertaColabExt, Void> call(TableColumn<OfertaColabExt, Void> param) {
                final TableCell<OfertaColabExt, Void> celda = new TableCell<OfertaColabExt, Void>(){
                    private final Button btnVerDatos = new Button("Ver datos");
                    {
                        btnVerDatos.setOnAction((event) ->{
                            OfertaColabExt ofertaExt = getTableView().getItems().get(getIndex());
                            irDatosOfertaExt(ofertaExt);
                        });
                    }
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnVerDatos);
                        }
                    }
                    
                };
                return celda;
            }
        });
    }
    @FXML
    private void btnClicVolver(ActionEvent event) {
    ((Stage) tvOfertasColabUV.getScene().getWindow()).close();
    }
}