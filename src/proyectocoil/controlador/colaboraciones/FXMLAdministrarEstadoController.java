/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 19/06/2024
 * Descripción: Muestra un combo box con los estados que puede adoptar la colaboración seleccionada
 * Versión: 1.0
 */

package proyectocoil.controlador.colaboraciones;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import proyectocoil.modelo.dao.ColaboracionDAO;
import proyectocoil.modelo.pojo.Colaboracion;
import proyectocoil.utilidades.Utils;


public class FXMLAdministrarEstadoController implements Initializable {

    @FXML
    private Label lbInformacionColab;
    @FXML
    private ComboBox<String> cbEstado;

    private Colaboracion colaboracion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public void inicializarValores(Colaboracion colaboracion) {
        this.colaboracion = colaboracion;
        lbInformacionColab.setText("Colaboracion: " + colaboracion.getNombreColab());
        cargarTiposEstado();
    }
    
    ObservableList<String> tiposEstado;
    private void cargarTiposEstado() {
         tiposEstado = FXCollections.observableArrayList();
        switch (colaboracion.getEstado()) {
            case "Acordada":
                tiposEstado.add("Acordada");
                tiposEstado.add("Activa");
                tiposEstado.add("Cancelada");
                cbEstado.setItems(tiposEstado);
                cbEstado.setValue("Acordada");
                break;
            case "Activa":
                tiposEstado.add("Activa");
                tiposEstado.add("Concluida");
                tiposEstado.add("Cancelada");
                cbEstado.setItems(tiposEstado);
                cbEstado.setValue("Activa");
                break;
        }
    }
    
    @FXML
    private void btnClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    public void cerrarVentana() {
        ((Stage) lbInformacionColab.getScene().getWindow()).close();
    }

    @FXML
    private void btnGuardar(ActionEvent event) {
        if (cbEstado.getSelectionModel().getSelectedItem().equals("Concluida")) {
            if (Utils.mostrarAlertaConfirmacion("Confirmación", "¿Está seguro que desea dar por concluida esta colaboración? Esto es irreversible y se mostrará una ventana para actualizar la lista final de estudiantes.")) {
                ColaboracionDAO.actualizarEstadoColaboracion(cbEstado.getSelectionModel().getSelectedItem(), colaboracion.getIdColaboracion());
                Utils.mostrarAlertaSimple("Estado actualizado", "Se ha actualizado correctamente el estado de la colaboración a " + cbEstado.getSelectionModel().getSelectedItem(), Alert.AlertType.INFORMATION);
                irActualizarEstudiantes();
            }
        } else if (cbEstado.getSelectionModel().getSelectedItem() != null) {
            if (Utils.mostrarAlertaConfirmacion("Confirmación", "¿Está seguro que desea actualizar el estado de la colaboración? Esto es irreversible.")) {
                ColaboracionDAO.actualizarEstadoColaboracion(cbEstado.getSelectionModel().getSelectedItem(), colaboracion.getIdColaboracion());
                Utils.mostrarAlertaSimple("Estado actualizado", "Se ha actualizado correctamente el estado de la colaboración a " + cbEstado.getSelectionModel().getSelectedItem(), Alert.AlertType.INFORMATION);
                cerrarVentana();
            } 
        } else {
            Utils.mostrarAlertaSimple("Estado no seleccionado", "Por favor, seleccione un estado antes de guardar la colaboración.", Alert.AlertType.WARNING);
        }
    }
    
    private void irActualizarEstudiantes() {
        try {
            Stage escenarioPrincipal = (Stage) lbInformacionColab.getScene().getWindow();
            FXMLLoader loader = Utils.obtenerLoader("vista/colaboraciones/FXMLActualizarEstudiantes.fxml");
            Parent root = loader.load();
            FXMLActualizarEstudiantesController controlador = loader.getController();
            controlador.inicializarValores(colaboracion);
            Scene escenaPrincipal = new Scene(root);
            escenarioPrincipal.setTitle("Actualizar estudiantes");
            escenarioPrincipal.setScene(escenaPrincipal);
            escenarioPrincipal.show();
            
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
}
