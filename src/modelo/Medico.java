package modelo;

/**
 * Clase que representa a un médico en el sistema.
 * Contiene información sobre su nombre, especialidad y disponibilidad.
 */
public class Medico {
    private String nombre;
    private String especialidad;
    private boolean disponible;
    private boolean turno24Horas;

    /**
     * Constructor de la clase Medico.
     *
     * @param nombre        Nombre del médico.
     * @param especialidad  Especialidad del médico.
     * @param disponible    Indica si el médico está disponible.
     */
    public Medico(String nombre, String especialidad, boolean disponible) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.disponible = disponible;
        this.turno24Horas = false;
    }

    /**
     * Obtiene el nombre del médico.
     *
     * @return Nombre del médico.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la especialidad del médico.
     *
     * @return Especialidad del médico.
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Indica si el médico está disponible.
     *
     * @return true si está disponible, false en caso contrario.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Establece la disponibilidad del médico.
     *
     * @param disponible Nuevo estado de disponibilidad.
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Indica si el médico toma turnos de 24 horas.
     *
     * @return true si toma turnos de 24 horas, false en caso contrario.
     */
    public boolean isTurno24Horas() {
        return turno24Horas;
    }

    /**
     * Establece si el médico toma turnos de 24 horas.
     *
     * @param turno24Horas Indica si el médico tomará turnos de 24 horas.
     */
    public void setTurno24Horas(boolean turno24Horas) {
        this.turno24Horas = turno24Horas;
    }
}