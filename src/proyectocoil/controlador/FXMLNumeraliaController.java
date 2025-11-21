/*
 * Autor: Jarly Hernández Romero
 * Fecha de Creación: 21/06/2024
 * Descripción: Llena la tabla de Numeralia con los profesores y alumnos que participaron en este periodo
 * Versión: 1.0
 */

package proyectocoil.controlador;
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
import proyectocoil.modelo.dao.NumeraliaDAO;
import proyectocoil.modelo.pojo.Numeralia;
import proyectocoil.modelo.pojo.Usuario;
import proyectocoil.utilidades.Constantes;
import proyectocoil.utilidades.Utils;

public class FXMLNumeraliaController implements Initializable {

    private Usuario usuario;
    private ObservableList<Numeralia> numeralia;

    @FXML
    private TableView<Numeralia> tvNumeralia;
    @FXML
    private TableColumn colAreaAcademica;
    @FXML
    private TableColumn colAlumnos;
    @FXML
    private TableColumn colProfesores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatosNumeralia();

    }

    public void inicializarValores(Usuario usuario) {
        this.usuario = usuario;
        cargarDatosNumeralia();
    }

    private void configurarTabla() {
        colAreaAcademica.setCellValueFactory(new PropertyValueFactory("areaAcademica"));
        colAlumnos.setCellValueFactory(new PropertyValueFactory("alumnos"));
        colProfesores.setCellValueFactory(new PropertyValueFactory("profesores"));
    }

    private void cargarDatosNumeralia() {
        numeralia = FXCollections.observableArrayList();
        HashMap<String, Object> respuesta = NumeraliaDAO.obtenerNumeralia();
        boolean isError = (boolean) respuesta.get(Constantes.KEY_ERROR);
        if (!isError) {
            ArrayList<Numeralia> numeraliaBD = (ArrayList<Numeralia>) respuesta.get("numeralia");
            numeralia.addAll(numeraliaBD);
            tvNumeralia.setItems(numeralia);
        } else {
            Utils.mostrarAlertaSimple("Error", (String) respuesta.get(Constantes.KEY_MENSAJE), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnClicCerrar(ActionEvent event) {
          irMenuPrincipal();
    }
    
    private void irMenuPrincipal(){
        try {
            Stage escenarioPrincipal = (Stage) tvNumeralia.getScene().getWindow();
            FXMLLoader loader = Utils.obtenerLoader("vista/FXMLMenuPrincipal.fxml");
            Parent root = loader.load();
            FXMLMenuPrincipalController controlador = loader.getController();
            controlador.inicializarValores(usuario);
            Scene escenaPrincipal = new Scene(root);
            escenarioPrincipal.setTitle("Menú Principal");
            escenarioPrincipal.setScene(escenaPrincipal);
            escenarioPrincipal.show();
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
}