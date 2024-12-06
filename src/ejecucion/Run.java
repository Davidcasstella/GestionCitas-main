package ejecucion;

import modelo.*;
import presentador.*;
import vista.TurnoException;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Clase principal que gestiona el sistema de turnos médicos. Permite gestionar
 * pacientes, médicos y turnos a través de un menú interactivo.
 */
public class Run {
	private static final PacienteController pacienteController = new PacienteController();
	private static final MedicoController medicoController = new MedicoController();
	private static final TurnoController turnoController = new TurnoController(pacienteController);
	private static final TurnoException turnoException = new TurnoException("");

	static {
		// Carga inicial de médicos
		medicoController.agregarMedico(new Medico("Dr. Juan Pérez", "Cardiología", true));
		medicoController.agregarMedico(new Medico("Dra. Ana López", "Pediatría", false));
		medicoController.agregarMedico(new Medico("Dr. Carlos Ruiz", "Traumatología", true));
	}

	/**
	 * Método principal que inicia la ejecución del programa.
	 *
	 * @param args Argumentos de línea de comandos (no se utilizan).
	 */
	public static void main(String[] args) {
		while (true) {
			String[] opciones = { "Gestionar Pacientes", "Gestionar Médicos", "Gestionar Turnos", "Salir" };
			int eleccion = mostrarMenu("Seleccione una opción:", "Sistema de Turnos Médicos", opciones);

			switch (eleccion) {
			case 0 -> gestionarPacientes();
			case 1 -> gestionarMedicos();
			case 2 -> gestionarTurnos();
			case 3 -> System.exit(0);
			default -> turnoException.mostrarMensaje("Opción inválida.");
			}
		}
	}

	/**
	 * Muestra un menú de opciones y devuelve la opción seleccionada por el usuario.
	 *
	 * @param mensaje  Mensaje a mostrar en el diálogo.
	 * @param titulo   Título del diálogo.
	 * @param opciones Opciones a mostrar en el menú.
	 * @return Índice de la opción seleccionada.
	 */
	private static int mostrarMenu(String mensaje, String titulo, String[] opciones) {
		return JOptionPane.showOptionDialog(null, mensaje, titulo, JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
	}

	/**
	 * Gestiona las opciones relacionadas con los pacientes.
	 */
	private static void gestionarPacientes() {
		String[] opciones = { "Agregar Paciente", "Eliminar Paciente", "Listar Pacientes", "Regresar" };
		int eleccion = mostrarMenu("Seleccione una opción:", "Gestión de Pacientes", opciones);

		switch (eleccion) {
		case 0 -> agregarPaciente();
		case 1 -> eliminarPaciente();
		case 2 -> listarPacientes();
		case 3 -> {
			/* Regresar */
		}
		}
	}

	/**
	 * Agrega un nuevo paciente al sistema.
	 */
	private static void agregarPaciente() {
		try {
			String nombre = turnoException.leerEntrada("Ingrese el nombre del paciente:");
			if(nombre.isBlank()) {
				turnoException.mostrarMensaje("Debe ingresar un nombre");
				return;
			}
			String cedula = turnoException.leerEntrada("Ingrese la cédula:");
			if (cedula.isBlank()) {
				turnoException.mostrarMensaje("Debe ingresar una cédula");
				return;
			}
			Paciente pacienteExistente = pacienteController.buscarPaciente(cedula);
			if (pacienteExistente != null) {
				turnoException.mostrarMensaje("El paciente con cédula " + cedula + " ya existe.");
				return;
			}

			int urgencia = Integer
					.parseInt(turnoException.leerEntrada("Ingrese nivel de urgencia (1-Alta, 2-Media, 3-Baja):"));
			if(String.valueOf(urgencia).isBlank()) {
				turnoException.mostrarMensaje("Debe ingresar una urgencia");
				return;
			}
			List<Medico> medicos = medicoController.obtenerMedicos();
			String[] especialidades = medicos.stream().map(Medico::getEspecialidad).distinct().toArray(String[]::new);
			String especialidad = (String) JOptionPane.showInputDialog(null, "Seleccione la especialidad médica:",
					"Especialidades", JOptionPane.QUESTION_MESSAGE, null, especialidades, especialidades[0]);
			if (especialidad == null)
				throw new IllegalArgumentException("Debe seleccionar una especialidad.");
			pacienteController.agregarPaciente(new PacienteFisico(nombre, cedula, urgencia, especialidad));
			turnoException.mostrarMensaje("Paciente agregado exitosamente.");
		} catch (Exception e) {
			turnoException.mostrarMensaje("Error al agregar paciente: " + e.getMessage());
		}
	}

	/**
	 * Elimina un paciente del sistema.
	 */
	private static void eliminarPaciente() {
		try {
			String cedula = turnoException.leerEntrada("Ingrese la cédula del paciente a eliminar:");
			if (pacienteController.eliminarPaciente(cedula)) {
				turnoException.mostrarMensaje("Paciente eliminado exitosamente.");
			} else {
				turnoException.mostrarMensaje("Paciente no encontrado.");
			}
		} catch (Exception e) {
			turnoException.mostrarMensaje("Error al eliminar paciente: " + e.getMessage());
		}
	}

	/**
	 * Lista todos los pacientes registrados en el sistema.
	 */
	private static void listarPacientes() {
		List<Paciente> pacientes = pacienteController.obtenerPacientes();
		if (pacientes.isEmpty()) {
			turnoException.mostrarMensaje("No hay pacientes registrados.");
		} else {
			StringBuilder listado = new StringBuilder("Pacientes:\n");
			for (Paciente p : pacientes) {
				listado.append("- ").append(p.getNombre()).append(" (").append(p.getCedula()).append(", Urgencia: ")
						.append(p.getNivelUrgencia()).append(")\n");
			}
			turnoException.mostrarMensaje(listado.toString());
		}
	}

	/**
	 * Gestiona las opciones relacionadas con los médicos.
	 */
	private static void gestionarMedicos() {
		String[] opciones = { "Agregar Médico", "Actualizar Disponibilidad", "Listar Médicos", "Regresar" };
		int eleccion = mostrarMenu("Seleccione una opción:", "Gestión de Médicos", opciones);

		switch (eleccion) {
		case 0 -> agregarMedico();
		case 1 -> actualizarDisponibilidad();
		case 2 -> listarMedicos();
		case 3 -> {
		}
		}
	}

	/**
	 * Agrega un nuevo médico al sistema.
	 */
	private static void agregarMedico() {
		try {
			String nombre = turnoException.leerEntrada("Ingrese el nombre del médico:");
			String especialidad = turnoException.leerEntrada("Ingrese la especialidad del médico:");
			boolean turno24Horas = JOptionPane.showConfirmDialog(null, "¿Se encuentra disponible actualmente?",
					"Turnos 24H", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
			medicoController.agregarMedico(new Medico(nombre, especialidad, turno24Horas));
			turnoException.mostrarMensaje("Médico agregado exitosamente.");
		} catch (Exception e) {
			turnoException.mostrarMensaje("Error al agregar médico: " + e.getMessage());
		}
	}

	/**
	 * Actualiza la disponibilidad de un médico en una fecha específica.
	 */
	private static void actualizarDisponibilidad() {
	    try {
	        String nombre = turnoException.leerEntrada("Ingrese el nombre del médico:");
	        Medico medico = medicoController.buscarMedico(nombre);
	        if (medico == null)
	            throw new Exception("Médico no encontrado.");

	        boolean disponible = JOptionPane.showConfirmDialog(null, "¿Está disponible?",
	                "Actualizar Disponibilidad", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	        
	        medico.setDisponible(disponible);
	        turnoException.mostrarMensaje("Disponibilidad actualizada correctamente.");
	    } catch (Exception e) {
	        turnoException.mostrarMensaje("Error al actualizar disponibilidad: " + e.getMessage());
	    }
	}

	/**
	 * Lista todos los médicos registrados en el sistema.
	 */
	private static void listarMedicos() {
		turnoException.mostrarMensaje(medicoController.listarMedicos());
	}

	/**
	 * Gestiona las opciones relacionadas con los turnos.
	 */
	private static void gestionarTurnos() {
		String[] opciones = { "Asignar Turno", "Modificar Turno", "Cancelar Turno", "Listar Turnos",
				"Cambiar Estado de Cita", "Historial de Citas", "Regresar" };
		int eleccion = mostrarMenu("Seleccione una opción:", "Gestión de Turnos", opciones);

		switch (eleccion) {
		case 0 -> asignarTurno();
		case 1 -> modificarTurno();
		case 2 -> cancelarTurno();
		case 3 -> listarTurnos();
		case 4 -> gestionarEstadoTurno();
		case 5 -> listarHistorialTurnos();
		case 6 -> {
			/* Regresar */
		}
		}
	}

	/**
	 * Asigna turnos automáticamente a los pacientes según su especialidad y
	 * disponibilidad de médicos.
	 */
	private static void asignarTurno() {
	    try {
	        List<Paciente> pacientes = pacienteController.obtenerPacientes();
	        List<Medico> medicos = medicoController.obtenerMedicos();
	        LocalDate fechaActual = LocalDate.now();
	        String estado = "Pendiente";

	        for (Paciente paciente : pacientes) {
	            Turno turnoExistente = turnoController.buscarTurnoPorPaciente(paciente.getCedula());
	            if (turnoExistente != null) {
	                continue;
	            }

	            boolean turnoAsignado = false;
	            for (Medico medico : medicos) {
	                if (medico.getEspecialidad().equalsIgnoreCase(paciente.getEspecialidad()) 
	                    && medico.isDisponible()) {
	                    turnoController.asignarTurnoAutomatico(paciente, medico, estado);
	                    turnoException.mostrarMensaje(
	                        "Turno asignado a " + paciente.getNombre() + " con el Dr. " + medico.getNombre());
	                    turnoAsignado = true;
	                    break;
	                }
	            }
	            if (!turnoAsignado) {
	                turnoException.mostrarMensaje("No se pudo asignar turno al paciente " + paciente.getNombre()
	                    + ": No hay médicos disponibles de la especialidad " + paciente.getEspecialidad());
	            }
	        }
	    } catch (Exception e) {
	        turnoException.mostrarMensaje("Error al asignar turno: " + e.getMessage());
	    }
	}
	/**
	 * Modifica un turno existente para un paciente.
	 */
	private static void modificarTurno() {
		try {
			String cedulaPaciente = turnoException.leerEntrada("Ingrese la cédula del paciente:");
			Turno turno = turnoController.buscarTurnoPorPaciente(cedulaPaciente);
			if (turno == null)
				throw new Exception("Turno no encontrado.");
			String nuevaFecha = turnoException.leerEntrada("Ingrese la nueva fecha (YYYY-MM-DD):");
			String nuevaHora = turnoException.leerEntrada("Ingrese la nueva hora (HH:MM):");
			turnoController.modificarTurno(turno, nuevaFecha, nuevaHora);
			turnoException.mostrarMensaje("Turno modificado exitosamente.");
		} catch (Exception e) {
			turnoException.mostrarMensaje("Error al modificar turno: " + e.getMessage());
		}
	}

	/**
	 * Cancela un turno existente para un paciente.
	 */
	private static void cancelarTurno() {
		try {
			String cedulaPaciente = turnoException.leerEntrada("Ingrese la cédula del paciente:");
			if (turnoController.cancelarTurno(cedulaPaciente)) {
				turnoException.mostrarMensaje("Turno cancelado exitosamente.");
			} else {
				turnoException.mostrarMensaje("Turno no encontrado.");
			}
		} catch (Exception e) {
			turnoException.mostrarMensaje("Error al cancelar turno: " + e.getMessage());
		}
	}

	/**
	 * Lista todos los turnos asignados en el sistema.
	 */
	private static void listarTurnos() {
		String lista = turnoController.listarTurnos();
		if (lista.isBlank()) {
			turnoException.mostrarMensaje("No hay turnos asignados.");
		} else {
			turnoException.mostrarMensaje("Turnos Asignados:\n" + lista);
		}
	}

	/**
	 * Cambia el estado de un turno para un paciente específico.
	 */
	private static void gestionarEstadoTurno() {
		try {
			String cedula = turnoException
					.leerEntrada("Ingrese la cédula del paciente para cambiar el estado de la cita:");
			Turno turno = turnoController.buscarTurnoPorPaciente(cedula);

			if (turno == null) {
				turnoException.mostrarMensaje("No se encontró un turno para la cédula proporcionada.");
				return;
			}

			String[] opcionesEstado = { "Atendida", "Perdida" };
			String nuevoEstado = (String) JOptionPane.showInputDialog(null, "Seleccione el nuevo estado de la cita:",
					"Cambiar Estado de Cita", JOptionPane.QUESTION_MESSAGE, null, opcionesEstado, opcionesEstado[0]);

			if (nuevoEstado == null || nuevoEstado.isBlank()) {
				turnoException.mostrarMensaje("Debe seleccionar un estado válido.");
				return;
			}

			if (turnoController.cambiarEstadoTurno(turno, nuevoEstado)) {
				turnoException.mostrarMensaje(
						"El estado de la cita ha sido actualizado y el paciente eliminado exitosamente.");
			} else {
				turnoException.mostrarMensaje("Error al actualizar el estado de la cita o eliminar al paciente.");
			}
		} catch (Exception e) {
			turnoException.mostrarMensaje("Error al cambiar el estado de la cita: " + e.getMessage());
		}
	}

	/**
	 * Lista el historial de citas de los pacientes.
	 */
	private static void listarHistorialTurnos() {
		List<Turno> historial = (List<Turno>) turnoController.listarHistorialTurnos();

		if (historial.isEmpty()) {
			turnoException.mostrarMensaje("No hay citas en el historial.");
		} else {
			StringBuilder mensaje = new StringBuilder("Historial de Citas:\n");
			for (Turno turno : historial) {
				mensaje.append("Paciente: ").append(turno.getPaciente().getNombre()).append(", Estado: ")
						.append(turno.getEstado()).append(", Fecha: ").append(turno.getFechaHora()).append("\n");
			}
			turnoException.mostrarMensaje(mensaje.toString());
		}
	}

	/**
	 * Asigna citas a pacientes según su especialidad médica.
	 */
	private static void asignarCitasPorEspecialidad() {
		try {
			String especialidad = turnoException.leerEntrada("Ingrese la especialidad médica:");
			List<Paciente> pacientes = pacienteController.obtenerPacientesPorEspecialidad(especialidad);
			List<Medico> medicos = medicoController.obtenerMedicos();
			String estado = "Pendiente";

			for (Paciente paciente : pacientes) {
				for (Medico medico : medicos) {
					if (medico.getEspecialidad().equalsIgnoreCase(especialidad)
							&& medico.isDisponible()) {
						turnoController.asignarTurnoAutomatico(paciente, medico, estado);
						turnoException.mostrarMensaje("Turno asignado exitosamente al paciente " + paciente.getNombre()
								+ " con el médico " + medico.getNombre());
						break;
					}
				}
			}
		} catch (Exception e) {
			turnoException.mostrarMensaje("Error al asignar citas por especialidad: " + e.getMessage());
		}
	}
}