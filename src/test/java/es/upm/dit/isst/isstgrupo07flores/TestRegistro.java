package es.upm.dit.isst.isstgrupo07flores;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import es.upm.dit.isst.isstgrupo07flores.controller.RegistroController;
import es.upm.dit.isst.isstgrupo07flores.model.Cliente;
import es.upm.dit.isst.isstgrupo07flores.model.Floricultor;
import es.upm.dit.isst.isstgrupo07flores.repository.ClienteRepository;
import es.upm.dit.isst.isstgrupo07flores.repository.FloricultorRepository;

@SpringBootTest
public class TestRegistro {

    @Autowired
    private RegistroController registroController;

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private FloricultorRepository floricultorRepository;

    // Lista de correos electrónicos utilizados en pruebas para limpiarlos después
    private final String[] emailsPrueba = {
        "testcliente@example.com", 
        "testfloricultor@example.com",
        "telefonoInvalido@example.com",
        "cpinvalido@example.com",
        "nombrevacio@example.com",
        "direccionvacia@example.com",
        "compartido@example.com"
    };

    // Limpiar antes de cada test
    @BeforeEach
    public void limpiarUsuariosDePrueba() {
        for (String email : emailsPrueba) {
            clienteRepository.findByCorreoElectronico(email)
                .ifPresent(clienteRepository::delete);
            
            floricultorRepository.findByCorreoElectronico(email)
                .ifPresent(floricultorRepository::delete);
        }
    }
    
    // Limpiar después de cada test para asegurar que no queden datos residuales
    @AfterEach
    public void limpiarDespuesDePrueba() {
        // Ejecutamos la misma limpieza que en BeforeEach para asegurar que no queden datos
        limpiarUsuariosDePrueba();
    }

    @Test
    public void testRegistroClienteCorrecto() {
        Cliente cliente = new Cliente();
        cliente.setCorreoElectronico("testcliente@example.com");
        cliente.setTelefono("612345678");
        cliente.setContrasena("password123");

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        String resultado = registroController.registrarCliente(cliente, redirectAttributes);

        assertEquals("redirect:/login", resultado);

        Optional<Cliente> clienteGuardadoOpt = clienteRepository.findByCorreoElectronico("testcliente@example.com");
        assertTrue(clienteGuardadoOpt.isPresent());

        Cliente clienteGuardado = clienteGuardadoOpt.get();
        assertNotEquals("password123", clienteGuardado.getContrasena());
        assertTrue(clienteGuardado.getContrasena().startsWith("$2a$"));
        
        // No es necesario eliminar manualmente aquí, se hará en @AfterEach
    }

    @Test
    public void testRegistroClienteConEmailYaRegistrado() {
        // Registrar el cliente por primera vez
        Cliente cliente1 = new Cliente();
        cliente1.setCorreoElectronico("testcliente@example.com");
        cliente1.setTelefono("612345678");
        cliente1.setContrasena("password123");
        clienteRepository.save(cliente1);

        // Intentar registrar con el mismo correo
        Cliente cliente2 = new Cliente();
        cliente2.setCorreoElectronico("testcliente@example.com"); // mismo correo
        cliente2.setTelefono("611223344");
        cliente2.setContrasena("otraClave");

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        String resultado = registroController.registrarCliente(cliente2, redirectAttributes);

        assertEquals("redirect:/registro", resultado);
        // Solo debe haber un cliente con ese correo
        assertTrue(clienteRepository.findByCorreoElectronico("testcliente@example.com").isPresent());
        
        // No es necesario eliminar manualmente aquí, se hará en @AfterEach
    }

    @Test
    public void testRegistroClienteConTelefonoInvalido() {
        Cliente cliente = new Cliente();
        cliente.setCorreoElectronico("telefonoInvalido@example.com");
        cliente.setTelefono("123ABCD"); // teléfono inválido
        cliente.setContrasena("password123");

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        String resultado = registroController.registrarCliente(cliente, redirectAttributes);

        assertEquals("redirect:/registro", resultado);
        assertTrue(clienteRepository.findByCorreoElectronico("telefonoInvalido@example.com").isEmpty());
        // No es necesaria la limpieza manual ya que no se guardó ningún registro
    }
    
    @Test
    public void testRegistroFloricultorCorrecto() {
        Floricultor floricultor = new Floricultor();
        floricultor.setCorreoElectronico("testfloricultor@example.com");
        floricultor.setNombre("Flores Test");
        floricultor.setDireccion("Calle Ejemplo 123");
        floricultor.setCp("28001");
        floricultor.setContrasena("password123");

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        String resultado = registroController.registrarFloricultor(floricultor, redirectAttributes);

        assertEquals("redirect:/login", resultado);

        Optional<Floricultor> floricultorGuardadoOpt = floricultorRepository.findByCorreoElectronico("testfloricultor@example.com");
        assertTrue(floricultorGuardadoOpt.isPresent());

        Floricultor floricultorGuardado = floricultorGuardadoOpt.get();
        assertNotEquals("password123", floricultorGuardado.getContrasena());
        assertTrue(floricultorGuardado.getContrasena().startsWith("$2a$"));
        assertEquals("Flores Test", floricultorGuardado.getNombre());
        assertEquals("28001", floricultorGuardado.getCp());
        
        // No es necesario eliminar manualmente aquí, se hará en @AfterEach
    }

    @Test
    public void testRegistroFloricultorConEmailYaRegistrado() {
        // Registrar floricultor por primera vez
        Floricultor floricultor1 = new Floricultor();
        floricultor1.setCorreoElectronico("testfloricultor@example.com");
        floricultor1.setNombre("Flores Test");
        floricultor1.setDireccion("Calle Ejemplo 123");
        floricultor1.setCp("28001");
        floricultor1.setContrasena("password123");
        floricultorRepository.save(floricultor1);

        // Intentar registrar con el mismo correo
        Floricultor floricultor2 = new Floricultor();
        floricultor2.setCorreoElectronico("testfloricultor@example.com"); // mismo correo
        floricultor2.setNombre("Otro Nombre");
        floricultor2.setDireccion("Otra Calle");
        floricultor2.setCp("28002");
        floricultor2.setContrasena("otraClave");

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        String resultado = registroController.registrarFloricultor(floricultor2, redirectAttributes);

        assertEquals("redirect:/registro", resultado);
        // Solo debe haber un floricultor con ese correo
        assertTrue(floricultorRepository.findByCorreoElectronico("testfloricultor@example.com").isPresent());
        
        // No es necesario eliminar manualmente aquí, se hará en @AfterEach
    }

    @Test
    public void testRegistroFloricultorConCodigoPostalInvalido() {
        Floricultor floricultor = new Floricultor();
        floricultor.setCorreoElectronico("cpinvalido@example.com");
        floricultor.setNombre("Flores Test");
        floricultor.setDireccion("Calle Ejemplo 123");
        floricultor.setCp("123"); // código postal inválido (no tiene 5 dígitos)
        floricultor.setContrasena("password123");

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        String resultado = registroController.registrarFloricultor(floricultor, redirectAttributes);

        assertEquals("redirect:/registro", resultado);
        assertTrue(floricultorRepository.findByCorreoElectronico("cpinvalido@example.com").isEmpty());
        // No es necesaria la limpieza manual ya que no se guardó ningún registro
    }

    @Test
    public void testRegistroFloricultorConNombreVacio() {
        Floricultor floricultor = new Floricultor();
        floricultor.setCorreoElectronico("nombrevacio@example.com");
        floricultor.setNombre(""); // nombre vacío
        floricultor.setDireccion("Calle Ejemplo 123");
        floricultor.setCp("28001");
        floricultor.setContrasena("password123");

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        String resultado = registroController.registrarFloricultor(floricultor, redirectAttributes);

        assertEquals("redirect:/registro", resultado);
        assertTrue(floricultorRepository.findByCorreoElectronico("nombrevacio@example.com").isEmpty());
        // No es necesaria la limpieza manual ya que no se guardó ningún registro
    }

    @Test
    public void testRegistroFloricultorConDireccionVacia() {
        Floricultor floricultor = new Floricultor();
        floricultor.setCorreoElectronico("direccionvacia@example.com");
        floricultor.setNombre("Flores Test");
        floricultor.setDireccion(""); // dirección vacía
        floricultor.setCp("28001");
        floricultor.setContrasena("password123");

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        String resultado = registroController.registrarFloricultor(floricultor, redirectAttributes);

        assertEquals("redirect:/registro", resultado);
        assertTrue(floricultorRepository.findByCorreoElectronico("direccionvacia@example.com").isEmpty());
        // No es necesaria la limpieza manual ya que no se guardó ningún registro
    }
    
    @Test
    public void testRegistroClienteEmail_FloricultorRegistrado() {
        // Registrar floricultor primero
        Floricultor floricultor = new Floricultor();
        floricultor.setCorreoElectronico("compartido@example.com");
        floricultor.setNombre("Flores Test");
        floricultor.setDireccion("Calle Ejemplo 123");
        floricultor.setCp("28001");
        floricultor.setContrasena("password123");
        floricultorRepository.save(floricultor);

        // Intentar registrar cliente con el mismo email
        Cliente cliente = new Cliente();
        cliente.setCorreoElectronico("compartido@example.com");
        cliente.setTelefono("612345678");
        cliente.setContrasena("password123");

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        String resultado = registroController.registrarCliente(cliente, redirectAttributes);

        assertEquals("redirect:/registro", resultado);
        assertTrue(clienteRepository.findByCorreoElectronico("compartido@example.com").isEmpty());
        
        // No es necesario eliminar manualmente aquí, se hará en @AfterEach
    }
    
    @Test
    public void testRegistroFloricultorEmail_ClienteRegistrado() {
        // Registrar cliente primero
        Cliente cliente = new Cliente();
        cliente.setCorreoElectronico("compartido@example.com");
        cliente.setTelefono("612345678");
        cliente.setContrasena("password123");
        clienteRepository.save(cliente);

        // Intentar registrar floricultor con el mismo email
        Floricultor floricultor = new Floricultor();
        floricultor.setCorreoElectronico("compartido@example.com");
        floricultor.setNombre("Flores Test");
        floricultor.setDireccion("Calle Ejemplo 123");
        floricultor.setCp("28001");
        floricultor.setContrasena("password123");

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        String resultado = registroController.registrarFloricultor(floricultor, redirectAttributes);

        assertEquals("redirect:/registro", resultado);
        assertTrue(floricultorRepository.findByCorreoElectronico("compartido@example.com").isEmpty());
        
        // No es necesario eliminar manualmente aquí, se hará en @AfterEach
    }
}
