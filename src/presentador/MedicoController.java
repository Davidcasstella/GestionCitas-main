package presentador;

import modelo.Medico;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que controla la gestión de médicos en el sistema.
 * Permite agregar, buscar y listar médicos.
 */
public class MedicoController {
    private List<Medico> medicos;

    /**
     * Constructor de la clase MedicoController.
     * Inicializa la lista de médicos con algunos médicos predeterminados.
     */
    public MedicoController() {
        this.medicos = new ArrayList<>();
        medicos.add(new Medico("Dr. Pérez", "Cardiología", true));
        medicos.add(new Medico("Dr. Rodríguez", "Pediatría", false));
    }

    /**
     * Agrega un nuevo médico a la lista de médicos.
     *
     * @param medico Médico a agregar.
     */
    public void agregarMedico(Medico medico) {
        medicos.add(medico);
    }

    /**
     * Busca un médico por su nombre.
     *
     * @param nombre Nombre del médico a buscar.
     * @return El médico encontrado, o null si no se encuentra.
     */
    public Medico buscarMedico(String nombre) {
        return medicos.stream().filter(m -> m.getNombre().equalsIgnoreCase(nombre)).findFirst().orElse(null);
    }

    /**
     * Lista todos los médicos en un formato legible.
     *
     * @return Cadena que representa la lista de médicos.
     */
    public String listarMedicos() {
        StringBuilder sb = new StringBuilder();
        for (Medico medico : medicos) {
            sb.append(medico.getNombre()).append(" (").append(medico.getEspecialidad()).append(") - ¿Está disponible: ")
                .append(medico.isDisponible() ? "Sí" : "No").append("\n");
        }
        return sb.toString();
    }

    /**
     * Obtiene la lista de médicos.
     *
     * @return Lista de médicos.
     */
    public List<Medico> obtenerMedicos() {
        return medicos;
    }
}