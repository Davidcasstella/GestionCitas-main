package vista;

import javax.swing.JOptionPane;

/**
 * Clase que representa una excepción personalizada para la gestión de turnos.
 * Extiende la clase Exception y proporciona métodos para leer entradas y mostrar mensajes.
 */
public class TurnoException extends Exception {
    
    /**
     * Constructor de la clase TurnoException.
     *
     * @param message Mensaje de error que describe la excepción.
     */
    public TurnoException(String message) {
        super(message);
    }
    
    /**
     * Lee una entrada del usuario a través de un cuadro de diálogo.
     *
     * @param mensaje Mensaje que se mostrará en el cuadro de diálogo.
     * @return La entrada del usuario como una cadena.
     */
    public String leerEntrada(String mensaje) {
        return JOptionPane.showInputDialog(mensaje);
    }
    
    /**
     * Muestra un mensaje en un cuadro de diálogo.
     *
     * @param mensaje Mensaje que se mostrará en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}