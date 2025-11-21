/*
 * Autor: Jarly Hernández Romero
 * Fecha de Creación: 20/06/2024
 * Descripción: Llena la tabla con la colaboraciones canceladas y concluidas
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import proyectocoil.modelo.dao.ColaboracionDAO;
import proyectocoil.modelo.pojo.Colaboracion;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLHistorialDeColaboracionesController implements Initializable {

    private ObservableList<Colaboracion> colaboraciones;
    
    @FXML
    private TableView<Colaboracion> tvColaboraciones;
    @FXML
    private TableColumn colNombreColab;
    @FXML
    private TableColumn colBoton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaColaboraciones();

    }    
    
    public void inicializarValores(){
        cargarColaboraciones();
    }
    
    private void cargarColaboraciones() {
        colaboraciones = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = ColaboracionDAO.obtenerColaboracionesTerminadas();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<Colaboracion> colaboracionesBD = (ArrayList<Colaboracion>) respuesta.get("colaboraciones");
            colaboraciones.addAll(colaboracionesBD);
            tvColaboraciones.setItems(colaboraciones);
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }

    public void configurarTablaColaboraciones() {
    colNombreColab.setCellValueFactory(new PropertyValueFactory("nombreColab"));
    colBoton.setCellFactory(new Callback<TableColumn<Colaboracion, Void>, TableCell<Colaboracion, Void>>() {
        @Override
        public TableCell<Colaboracion, Void> call(TableColumn<Colaboracion, Void> param) {
            final TableCell<Colaboracion, Void> celda = new TableCell<Colaboracion, Void>() {
                private final Button btnVerDatos = new Button("Ver datos");
                {
                    btnVerDatos.setOnAction((event) -> {
                        Colaboracion colaboracion = getTableView().getItems().get(getIndex());
                        irDatosColaboracion(colaboracion);
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
     
     private void irDatosColaboracion(Colaboracion colaboracion) {
    try {
        Stage escenario = new Stage();
        FXMLLoader loader = Utils.obtenerLoader("vista/colaboraciones/FXMLDatosColaboraciones.fxml");
        Parent root = loader.load();
        FXMLDatosColaboracionesController controlador = loader.getController();
        controlador.inicializarValores(colaboracion);
        Scene escena = new Scene(root);
        escenario.setScene(escena);
        escenario.setTitle("Colaboración");
        escenario.initModality(Modality.APPLICATION_MODAL);
        escenario.showAndWait();
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}
     
    @FXML
    private void btnClicVolver(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        ((Stage) tvColaboraciones.getScene().getWindow()).close();
    }

}
