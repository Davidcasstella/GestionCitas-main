package modelo;

/**
 * Clase abstracta que representa a un paciente en el sistema.
 * Contiene información básica sobre el paciente, como su nombre, cédula y nivel de urgencia.
 */
public abstract class Paciente {
    private String nombre;
    private String cedula;
    private int nivelUrgencia; 

    /**
     * Constructor de la clase Paciente.
     *
     * @param nombre        Nombre del paciente.
     * @param cedula       Cédula del paciente.
     * @param nivelUrgencia Nivel de urgencia del paciente.
     */
    public Paciente(String nombre, String cedula, int nivelUrgencia) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.nivelUrgencia = nivelUrgencia;
    }

    /**
     * Obtiene el nombre del paciente.
     *
     * @return Nombre del paciente.
     */
    public String getNombre() { 
        return nombre; 
    }

    /**
     * Obtiene la cédula del paciente.
     *
     * @return Cédula del paciente.
     */
    public String getCedula() { 
        return cedula; 
    }

    /**
     * Obtiene el nivel de urgencia del paciente.
     *
     * @return Nivel de urgencia del paciente.
     */
    public int getNivelUrgencia() { 
        return nivelUrgencia; 
    }

    /**
     * Método abstracto que debe ser implementado por las subclases para obtener el tipo de paciente.
     *
     * @return Tipo de paciente.
     */
    public abstract String getTipo();

    /**
     * Método abstracto que debe ser implementado por las subclases para obtener la especialidad del paciente.
     *
     * @return Especialidad del paciente.
     */
    public abstract String getEspecialidad();
}