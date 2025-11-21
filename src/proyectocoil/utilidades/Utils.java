/*
 * Autor: Luis Pablo Lagunes Noriega
 * Fecha de Creación: 12/06/2024
 * Descripción: Declara métodos independientes que son usados constantemente a lo largo del programa
 * Versión: 1.0
 */

package proyectocoil.utilidades;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Utils {
    public static FXMLLoader obtenerLoader(String ruta) {
        return new FXMLLoader(proyectocoil.ProyectoCoil.class.getResource(ruta));
    }

     public static void mostrarAlertaSimple(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static boolean mostrarAlertaConfirmacion(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        Optional<ButtonType> botonSeleccionado = alerta.showAndWait();
        return (botonSeleccionado.get() == ButtonType.OK);
    }

    public static boolean validarFecha(LocalDate FechaInicio, LocalDate FechaTermino) {
        if (FechaInicio.isBefore(FechaTermino)) {
                LocalDate inicioPeriodoFebreroJunio = LocalDate.of(FechaInicio.getYear(), Month.FEBRUARY, Constantes.DIA_INICIO_PERIODO_FEBRERO_JUNIO);
                LocalDate terminoPeriodoFebreroJunio = LocalDate.of(FechaInicio.getYear(), Month.JUNE, Constantes.DIA_TERMINO_PERIODO_FEBRERO_JUNIO);
                LocalDate inicioPeriodoAgostoDiciembre = LocalDate.of(FechaInicio.getYear(), Month.AUGUST, Constantes.DIA_INICIO_PERIODO_AGOSTO_DICIEMBRE);
                LocalDate terminoPeriodoAgostoDiciembre = LocalDate.of(FechaInicio.getYear(), Month.DECEMBER, Constantes.DIA_TERMINO_PERIODO_AGOSTO_DICIEMBRE);
                boolean perteneceAlPeriodoFebreroJunio = (FechaInicio.isAfter(inicioPeriodoFebreroJunio) || FechaInicio.isEqual(inicioPeriodoFebreroJunio)) && (FechaTermino.isBefore(terminoPeriodoFebreroJunio) || FechaTermino.isEqual(terminoPeriodoFebreroJunio));
                boolean perteneceAlPeriodoAgostoDiciembre = (FechaInicio.isAfter(inicioPeriodoAgostoDiciembre) || FechaInicio.isEqual(inicioPeriodoAgostoDiciembre)) && (FechaTermino.isBefore(terminoPeriodoAgostoDiciembre) || FechaTermino.isEqual(terminoPeriodoAgostoDiciembre));
                if (perteneceAlPeriodoFebreroJunio || perteneceAlPeriodoAgostoDiciembre) {
                    return true;
                } else {
                    Utils.mostrarAlertaSimple("Fechas incorrectas", "Las fechas deben estar dentro del rango aceptado del periodo.\n"
                        +"- Periodo Febrero-Junio: "+Constantes.DIA_INICIO_PERIODO_FEBRERO_JUNIO+" de Febrero al "+Constantes.DIA_TERMINO_PERIODO_FEBRERO_JUNIO+" de Junio.\n"
                        +"- Periodo Agosto-Diciembre: "+Constantes.DIA_INICIO_PERIODO_AGOSTO_DICIEMBRE+" de Agosto al "+Constantes.DIA_TERMINO_PERIODO_AGOSTO_DICIEMBRE+" de Diciembre.", Alert.AlertType.WARNING);
                    return false;
                }
        } else {
            Utils.mostrarAlertaSimple("Fechas incorrectas", "La fecha de inicio no puede ser posterior o igual a la de término.", Alert.AlertType.WARNING);
            return false;
        }
    }
}