package test;

import modelo.Medico;
import presentador.MedicoController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class MedicoControllerTest {

    @Test
    public void testAgregarMedico() {
        MedicoController controller = new MedicoController();
        Medico nuevoMedico = new Medico("Dr. Gómez", "Dermatología", true);

        controller.agregarMedico(nuevoMedico);

        List<Medico> medicos = controller.obtenerMedicos();
        assertTrue(medicos.contains(nuevoMedico), "El médico debería estar en la lista.");
    }

    @Test
    public void testBuscarMedicoExistente() {
        MedicoController controller = new MedicoController();

        Medico resultado = controller.buscarMedico("Dr. Pérez");

        assertNotNull(resultado, "El médico debería encontrarse.");
        assertEquals("Cardiología", resultado.getEspecialidad(), "La especialidad debería ser Cardiología.");
    }

    @Test
    public void testBuscarMedicoNoExistente() {
        MedicoController controller = new MedicoController();

        Medico resultado = controller.buscarMedico("Dr. Desconocido");

        assertNull(resultado, "El médico no debería encontrarse.");
    }

    @Test
    public void testListarMedicos() {
        MedicoController controller = new MedicoController();
        String listaMedicos = controller.listarMedicos();

        assertTrue(listaMedicos.contains("Dr. Pérez"), "La lista debería contener al Dr. Pérez.");
        assertTrue(listaMedicos.contains("Cardiología"), "La lista debería contener la especialidad Cardiología.");
    }

    @Test
    public void testObtenerMedicos() {
        MedicoController controller = new MedicoController();

        List<Medico> medicos = controller.obtenerMedicos();

        assertEquals(2, medicos.size(), "La lista inicial debería tener 2 médicos.");
    }
}
