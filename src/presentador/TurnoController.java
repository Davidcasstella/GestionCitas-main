package presentador;

import modelo.Medico;
import modelo.Paciente;
import modelo.Turno;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Clase que controla la gestión de turnos en el sistema.
 * Permite asignar, modificar, cancelar y listar turnos, así como gestionar el historial de turnos.
 */
public class TurnoController {
    private PriorityQueue<Turno> turnos; 
    private List<Turno> historialTurnos = new ArrayList<>(); 
    private List<Turno> turnosActivos = new ArrayList<>();
    private PacienteController pacienteController;

    /**
     * Constructor de la clase TurnoController.
     *
     * @param pacienteController Controlador de pacientes asociado.
     */
    public TurnoController(PacienteController pacienteController) {
        this.turnos = new PriorityQueue<>();
        this.pacienteController = pacienteController; 
    }

    /**
     * Asigna un turno automáticamente a un paciente y médico, y registra el estado.
     *
     * @param paciente Paciente al que se le asigna el turno.
     * @param medico   Médico que atenderá al paciente.
     * @param estado   Estado inicial del turno.
     * @return El turno asignado.
     */
    public Turno asignarTurnoAutomatico(Paciente paciente, Medico medico, String estado) {
        boolean yaAtendido = historialTurnos.stream()
                .anyMatch(t -> t.getPaciente().getCedula().equals(paciente.getCedula()) && t.getEstado().equals("Atendida"));
        LocalDateTime ahora = LocalDateTime.now();
        Turno turno = new Turno(paciente, medico, ahora, estado);
        turnos.add(turno);
        turnosActivos.add(turno);
        return turno;
    }

    /**
     * Verifica si un paciente tiene un turno anterior atendido o perdido.
     *
     * @param cedulaPaciente Cédula del paciente a verificar.
     * @return true si el paciente tiene un turno anterior atendido o perdido, false en caso contrario.
     */
    public boolean pacienteTieneTurnoAnterior(String cedulaPaciente) {
        List<Turno> historialTurnos = listarHistorialTurnos(); 
        for (Turno turno : historialTurnos) {
            if (turno.getPaciente().getCedula().equals(cedulaPaciente)) {
                String estado = turno.getEstado();
                if ("Atendida".equals(estado) || "Perdida".equals(estado)) {
                    return true; 
                }
            }
        }
        return false; 
    }

    /**
     * Busca un turno por la cédula del paciente.
     *
     * @param cedulaPaciente Cédula del paciente.
     * @return El turno encontrado, o null si no se encuentra.
     */
    public Turno buscarTurnoPorPaciente(String cedulaPaciente) {
        return turnos.stream()
                .filter(t -> t.getPaciente().getCedula().equals(cedulaPaciente))
                .findFirst()
                .orElse(null);
    }

    /**
     * Modifica la fecha y hora de un turno.
     *
     * @param turno      Turno a modificar.
     * @param nuevaFecha Nueva fecha en formato ISO (YYYY-MM-DD).
     * @param nuevaHora  Nueva hora en formato ISO (HH:MM).
     * @return true si el turno fue modificado exitosamente, false en caso contrario.
     */
    public boolean modificarTurno(Turno turno, String nuevaFecha, String nuevaHora) {
        try {
            if (turnos.remove(turno)) { 
                LocalDateTime nuevaFechaHora = LocalDateTime.parse(nuevaFecha + "T" + nuevaHora); 
                turno.setFechaHora(nuevaFechaHora);
                turnos.add(turno); 
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cancela un turno basado en la cédula del paciente.
     *
     * @param cedulaPaciente Cédula del paciente cuyo turno se desea cancelar.
     * @return true si el turno fue cancelado exitosamente, false si no se encontró.
     */
    public boolean cancelarTurno(String cedulaPaciente) {
        Turno turno = buscarTurnoPorPaciente(cedulaPaciente);
        return turno != null && turnos.remove(turno); 
    }

    /**
     * Lista todos los turnos ordenados por prioridad (nivel de urgencia).
     *
     * @return Cadena que representa la lista de turnos.
     */
    public String listarTurnos()
    {
        List<Turno> listaOrdenada = new ArrayList<>(turnos);
        listaOrdenada.sort((t1, t2) -> Integer.compare(t1.getPaciente().getNivelUrgencia(), t2.getPaciente().getNivelUrgencia()));

        StringBuilder sb = new StringBuilder("Lista de Turnos (Ordenados por Prioridad):\n");
        for (Turno turno : listaOrdenada) {
            sb.append(turno).append("\n");
        }
        return sb.toString();
    }

    /**
     * Obtiene el siguiente turno en la cola de turnos.
     *
     * @return El siguiente turno, o null si no hay turnos disponibles.
     */
    public Turno siguienteTurno() {
        return turnos.poll(); 
    }

    /**
     * Registra un turno en el historial de turnos.
     *
     * @param turno Turno a registrar en el historial.
     */
    public void registrarHistorial(Turno turno) {
        historialTurnos.add(turno);
    }

    /**
     * Lista el historial de turnos.
     *
     * @return Cadena que representa el historial de turnos.
     */
    public String listarHistorial() {
        StringBuilder sb = new StringBuilder("Historial de Turnos:\n");
        for (Turno turno : historialTurnos) {
            sb.append(turno).append("\n");
        }
        return sb.toString();
    }

    /**
     * Lista todos los turnos activos.
     *
     * @return Lista de turnos activos.
     */
    public List<Turno> listarTurnosActivos() {
        return turnosActivos;
    }

    /**
     * Lista el historial de turnos.
     *
     * @return Lista de turnos en el historial.
     */
    public List<Turno> listarHistorialTurnos() {
        return historialTurnos;
    }

    /**
     * Agrega un turno al historial si no está ya presente.
     *
     * @param turno Turno a agregar al historial.
     */
    public void agregarAlHistorial(Turno turno) {
        if (!historialTurnos.contains(turno)) {
            historialTurnos.add(turno);
        }
    }

    /**
     * Elimina un turno activo de la lista de turnos activos y de la cola de turnos.
     *
     * @param turno Turno a eliminar.
     */
    public void eliminarTurnoActivo(Turno turno) {
        turnosActivos.remove(turno);
        turnos.remove(turno);
    }

    /**
     * Cambia el estado de un turno y lo registra en el historial.
     *
     * @param turno      Turno cuyo estado se desea cambiar.
     * @param nuevoEstado Nuevo estado del turno.
     * @return true si el estado fue cambiado exitosamente y el paciente fue eliminado, false en caso contrario.
     */
    public boolean cambiarEstadoTurno(Turno turno, String nuevoEstado) {
        turno.setEstado(nuevoEstado);
        agregarAlHistorial(turno);
        eliminarTurnoActivo(turno);
        if (pacienteController != null && turno.getPaciente() != null) {
            return pacienteController.eliminarPaciente(turno.getPaciente().getCedula());
        }
        return false;
    }
}