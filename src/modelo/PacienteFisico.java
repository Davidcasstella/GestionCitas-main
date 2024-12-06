package modelo;

/**
 * Clase que representa a un paciente físico en el sistema.
 * Hereda de la clase abstracta Paciente e incluye información sobre su especialidad.
 */
public class PacienteFisico extends Paciente {
    private String especialidad;

    /**
     * Constructor de la clase PacienteFisico.
     *
     * @param nombre        Nombre del paciente.
     * @param cedula       Cédula del paciente.
     * @param nivelUrgencia Nivel de urgencia del paciente.
     * @param especialidad  Especialidad del paciente físico.
     */
    public PacienteFisico(String nombre, String cedula, int nivelUrgencia, String especialidad) {
        super(nombre, cedula, nivelUrgencia);
        this.especialidad = especialidad;
    }

    /**
     * Obtiene la especialidad del paciente físico.
     *
     * @return Especialidad del paciente físico.
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Obtiene el tipo de paciente.
     *
     * @return Tipo de paciente, en este caso "Físico".
     */
    @Override
    public String getTipo() {
        return "Físico";
    }
}