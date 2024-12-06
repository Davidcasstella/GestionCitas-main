package modelo;

import java.time.LocalDateTime;

import presentador.PacienteController;

/**
 * Clase que representa un turno en el sistema.
 * Un turno está asociado a un paciente y un médico, junto con la fecha y hora del turno y su estado.
 */
public class Turno implements Comparable<Turno> {
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private String estado; 

    /**
     * Constructor de la clase Turno.
     *
     * @param paciente   Paciente asociado al turno.
     * @param medico     Médico asociado al turno.
     * @param fechaHora  Fecha y hora del turno.
     * @param estado     Estado del turno (por ejemplo, "Atendida" o "Perdida").
     */
    public Turno(Paciente paciente, Medico medico, LocalDateTime fechaHora, String estado) {
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    /**
     * Obtiene el paciente asociado al turno.
     *
     * @return Paciente asociado al turno.
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * Obtiene el médico asociado al turno.
     *
     * @return Médico asociado al turno.
     */
    public Medico getMedico() {
        return medico;
    }

    /**
     * Obtiene la fecha y hora del turno.
     *
     * @return Fecha y hora del turno.
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Establece la fecha y hora del turno.
     *
     * @param fechaHora Nueva fecha y hora del turno.
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    /**
     * Obtiene el estado del turno.
     *
     * @return Estado del turno.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del turno.
     *
     * @param estado Nuevo estado del turno. Debe ser "Atendida" o "Perdida".
     * @throws IllegalArgumentException Si el estado no es válido.
     */
    public void setEstado(String estado) {
        if (!estado.equals("Atendida") && !estado.equals("Perdida")) {
            throw new IllegalArgumentException("Estado no válido. Debe ser 'Atendida' o 'Perdida'.");
        }
        this.estado = estado;
    }
    
    /**
     * Elimina el paciente asociado al turno utilizando el PacienteController.
     * Se maneja cualquier excepción que pueda ocurrir durante la eliminación.
     */
    private void eliminarPacienteAsociado() {
        try {
            PacienteController pacienteController = new PacienteController();
            pacienteController.eliminarPaciente(this.paciente.getCedula());
            System.out.println("Paciente " + paciente.getNombre() + " eliminado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al eliminar el paciente: " + e.getMessage());
        }
    }

    /**
     * Compara este turno con otro turno basado en el nivel de urgencia del paciente.
     *
     * @param o Otro turno a comparar.
     * @return Un valor negativo, cero o positivo si este turno es menos, igual o más urgente que el turno especificado.
     */
    @Override
    public int compareTo(Turno o) {
        return Integer.compare(o.paciente.getNivelUrgencia(), this.paciente.getNivelUrgencia());
    }

    /**
     * Compara este objeto con otro para determinar si son iguales.
     *
     * @param obj Objeto a comparar.
     * @return true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Turno turno = (Turno) obj;
        return paciente.equals(turno.paciente) && medico.equals(turno.medico) && fechaHora.equals(turno.fechaHora);
    }

    /**
     * Devuelve una representación en forma de cadena del turno.
     *
     * @return Cadena que representa el turno.
     */
    @Override
    public String toString() {
        return "Turno{" +
               "Paciente=" + paciente.getNombre() +
               ", Médico=" + medico.getNombre() +
               ", Especialidad=" + medico.getEspecialidad() +
               ", FechaHora=" + fechaHora +
               ", Estado=" + estado +
 '}';
    }
}