package test;

import modelo.Paciente;
import modelo.PacienteFisico;
import presentador.PacienteController;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PacienteControllerTest {

    @Test
    public void testAgregarPaciente() {
        PacienteController controller = new PacienteController();
        Paciente nuevoPaciente = new PacienteFisico("Juan Pérez", "12345", 3, "Ortopedia");

        controller.agregarPaciente(nuevoPaciente);

        List<Paciente> pacientes = controller.obtenerPacientes();
        assertTrue(pacientes.contains(nuevoPaciente), "El paciente debería estar en la lista.");
    }

    @Test
    public void testBuscarPacientePorCedulaExistente() {
        PacienteController controller = new PacienteController();
        Paciente paciente = new PacienteFisico("Ana López", "54321", 2, "Cardiología");
        controller.agregarPaciente(paciente);

        Paciente resultado = controller.buscarPacientePorCedula("54321");

        assertNotNull(resultado, "El paciente debería encontrarse.");
        assertEquals("Ana López", resultado.getNombre(), "El nombre debería coincidir.");
    }

    @Test
    public void testBuscarPacientePorCedulaNoExistente() {
        PacienteController controller = new PacienteController();

        Paciente resultado = controller.buscarPacientePorCedula("00000");

        assertNull(resultado, "El paciente no debería encontrarse.");
    }

    @Test
    public void testEliminarPacienteExistente() {
        PacienteController controller = new PacienteController();
        Paciente paciente = new PacienteFisico("Carlos García", "11111", 4, "Neurología");
        controller.agregarPaciente(paciente);

        boolean eliminado = controller.eliminarPaciente("11111");

        assertTrue(eliminado, "El paciente debería ser eliminado.");
        assertNull(controller.buscarPacientePorCedula("11111"), "El paciente no debería estar en la lista.");
    }

    @Test
    public void testEliminarPacienteNoExistente() {
        PacienteController controller = new PacienteController();

        boolean eliminado = controller.eliminarPaciente("99999");

        assertFalse(eliminado, "No debería eliminarse ningún paciente.");
    }

    @Test
    public void testObtenerPacientesOrdenadosPorUrgencia() {
        PacienteController controller = new PacienteController();
        controller.agregarPaciente(new PacienteFisico("Paciente 1", "001", 5, "Gastroenterología"));
        controller.agregarPaciente(new PacienteFisico("Paciente 2", "002", 2, "Dermatología"));
        controller.agregarPaciente(new PacienteFisico("Paciente 3", "003", 3, "Oncología"));

        List<Paciente> ordenados = controller.obtenerPacientesOrdenados();

        assertEquals(2, ordenados.get(0).getNivelUrgencia(), "El primer paciente debería tener el nivel de urgencia más bajo.");
        assertEquals(5, ordenados.get(2).getNivelUrgencia(), "El último paciente debería tener el nivel de urgencia más alto.");
    }

    @Test
    public void testObtenerPacientesPorEspecialidad() {
        PacienteController controller = new PacienteController();
        controller.agregarPaciente(new PacienteFisico("Paciente 1", "001", 3, "Cardiología"));
        controller.agregarPaciente(new PacienteFisico("Paciente 2", "002", 5, "Cardiología"));
        controller.agregarPaciente(new PacienteFisico("Paciente 3", "003", 2, "Dermatología"));

        List<Paciente> cardiologia = controller.obtenerPacientesPorEspecialidad("Cardiología");

        assertEquals(2, cardiologia.size(), "Debería haber 2 pacientes en Cardiología.");
        assertEquals(5, cardiologia.get(0).getNivelUrgencia(), "El paciente más urgente debería aparecer primero.");
    }
}
