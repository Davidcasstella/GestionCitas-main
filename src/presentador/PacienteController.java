package presentador;

import modelo.Paciente;
import modelo.PacienteFisico;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Clase que controla la gestión de pacientes en el sistema.
 * Permite agregar, eliminar, buscar y listar pacientes.
 */
public class PacienteController {
    private List<Paciente> pacientes;

    /**
     * Constructor de la clase PacienteController.
     * Inicializa la lista de pacientes.
     */
    public PacienteController() {
        this.pacientes = new ArrayList<>();
    }

    /**
     * Agrega un nuevo paciente a la lista de pacientes.
     *
     * @param paciente Paciente a agregar.
     */
    public void agregarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    /**
     * Elimina un paciente de la lista utilizando su cédula.
     *
     * @param cedula Cédula del paciente a eliminar.
     * @return true si el paciente fue eliminado exitosamente, false si no se encontró.
     */
    public boolean eliminarPaciente(String cedula) {
        Paciente paciente = buscarPacientePorCedula(cedula);
        if (paciente != null) {
            pacientes.remove(paciente);
            return true;
        }
        return false;
    }

    /**
     * Busca un paciente por su cédula.
     *
     * @param cedula Cédula del paciente a buscar.
     * @return El paciente encontrado, o null si no se encuentra.
     */
    public Paciente buscarPacientePorCedula(String cedula) {
        for (Paciente paciente : pacientes) {
            if (paciente.getCedula().equals(cedula)) {
                return paciente;
            }
        }
        return null;
    }

    /**
     * Busca un paciente por su cédula utilizando un enfoque de stream.
     *
     * @param cedula Cédula del paciente a buscar.
     * @return El paciente encontrado, o null si no se encuentra.
     */
    public Paciente buscarPaciente(String cedula) {
        return pacientes.stream()
                .filter(p -> p.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene la lista de pacientes.
     *
     * @return Lista de pacientes.
     */
    public List<Paciente> obtenerPacientes() {
        return new ArrayList<>(pacientes);
    }

    /**
     * Obtiene la lista de pacientes ordenados por nivel de urgencia.
     *
     * @return Lista de pacientes ordenados.
     */
    public List<Paciente> obtenerPacientesOrdenados() {
        List<Paciente> pacientesOrdenados = new ArrayList<>(pacientes); 
        pacientesOrdenados.sort(Comparator.comparingInt(Paciente::getNivelUrgencia)); 
        return pacientesOrdenados;
    }

    /**
     * Obtiene la lista de pacientes físicos filtrados por especialidad.
     * Los pacientes se ordenan por nivel de urgencia de forma descendente.
     *
     * @param especialidad Especialidad para filtrar los pacientes.
     * @return Lista de pacientes físicos que coinciden con la especialidad.
     */
    public List<Paciente> obtenerPacientesPorEspecialidad(String especialidad) {
        return pacientes.stream()
                .filter(p -> p instanceof PacienteFisico) 
                .filter(p -> ((PacienteFisico) p).getEspecialidad().equalsIgnoreCase(especialidad)) 
                .sorted((p1, p2) -> Integer.compare(p2.getNivelUrgencia(), p1.getNivelUrgencia())) 
                .toList();
    }
}