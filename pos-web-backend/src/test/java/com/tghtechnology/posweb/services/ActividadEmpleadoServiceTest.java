package com.tghtechnology.posweb.services;

import com.tghtechnology.posweb.data.entities.ActividadEmpleado;
import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.entities.Venta;
import com.tghtechnology.posweb.data.repository.ActividadEmpleadoRepository;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;
import com.tghtechnology.posweb.data.repository.VentaRepository;
import com.tghtechnology.posweb.service.impl.ActividadEmpleadoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ActividadEmpleadoServiceTest {

    @Mock
    private ActividadEmpleadoRepository actividadEmpleadoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private ActividadEmpleadoServiceImpl actividadEmpleadoService;

    private Usuario empleado;
    private Venta venta;
    private ActividadEmpleado actividadEmpleado;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear instancias de los objetos para las pruebas
        empleado = new Usuario();
        empleado.setIdUsuario(1L);

        venta = new Venta();
        venta.setIdVenta(1L);

        actividadEmpleado = new ActividadEmpleado();
        actividadEmpleado.setEmpleado(empleado);
        actividadEmpleado.setVenta(venta);
    }

    @Test
    public void testRegistrarActividad() {
        // Arrange
        when(usuarioRepository.findById(empleado.getIdUsuario())).thenReturn(Optional.of(empleado));
        when(ventaRepository.findById(venta.getIdVenta())).thenReturn(Optional.of(venta));
        when(actividadEmpleadoRepository.save(any(ActividadEmpleado.class))).thenReturn(actividadEmpleado);

        // Act
        ActividadEmpleado resultado = actividadEmpleadoService.registrarActividad(empleado.getIdUsuario(),
                venta.getIdVenta());

        // Assert
        assertNotNull(resultado, "El resultado no debe ser null");
        assertEquals(empleado, resultado.getEmpleado());
        assertEquals(venta, resultado.getVenta());
        verify(actividadEmpleadoRepository, times(1)).save(any(ActividadEmpleado.class));
    }

    @Test
    public void testRegistrarActividadEmpleadoNoExistente() {
        // Arrange
        when(usuarioRepository.findById(empleado.getIdUsuario())).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            actividadEmpleadoService.registrarActividad(empleado.getIdUsuario(), venta.getIdVenta());
        });
        assertEquals("Empleado no encontrado", thrown.getMessage());
    }

    @Test
    public void testRegistrarActividadVentaNoExistente() {
        // Arrange
        when(usuarioRepository.findById(empleado.getIdUsuario())).thenReturn(Optional.of(empleado));
        when(ventaRepository.findById(venta.getIdVenta())).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            actividadEmpleadoService.registrarActividad(empleado.getIdUsuario(), venta.getIdVenta());
        });
        assertEquals("Venta no encontrada", thrown.getMessage());
    }

    @Test
    public void testActualizarActividad() {
        // Arrange
        Long idActividad = 1L;
        Long idEmpleadoNuevo = 2L;
        Long idVentaNueva = 2L;
        ActividadEmpleado nuevaActividad = new ActividadEmpleado();
        nuevaActividad.setEmpleado(new Usuario());
        nuevaActividad.setVenta(new Venta());

        when(actividadEmpleadoRepository.findById(idActividad)).thenReturn(Optional.of(actividadEmpleado));
        when(usuarioRepository.findById(idEmpleadoNuevo)).thenReturn(Optional.of(new Usuario()));
        when(ventaRepository.findById(idVentaNueva)).thenReturn(Optional.of(new Venta()));
        when(actividadEmpleadoRepository.save(any(ActividadEmpleado.class))).thenReturn(actividadEmpleado);

        // Act
        ActividadEmpleado resultado = actividadEmpleadoService.actualizarActividad(idActividad, idEmpleadoNuevo,
                idVentaNueva);

        // Assert
        assertNotNull(resultado);
        verify(actividadEmpleadoRepository, times(1)).save(any(ActividadEmpleado.class));
    }

    @Test
    public void testActualizarActividadNoExistente() {
        // Arrange
        Long idActividad = 1L;
        when(actividadEmpleadoRepository.findById(idActividad)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            actividadEmpleadoService.actualizarActividad(idActividad, 2L, 2L);
        });
        assertEquals("Actividad no encontrada", thrown.getMessage());
    }

    @Test
    public void testConsultarActividadesPorEmpleado() {
        // Arrange
        when(actividadEmpleadoRepository.findAllByEmpleado_IdUsuario(empleado.getIdUsuario()))
                .thenReturn(List.of(actividadEmpleado));

        // Act
        List<ActividadEmpleado> actividades = actividadEmpleadoService
                .consultarActividadesPorEmpleado(empleado.getIdUsuario());

        // Assert
        assertNotNull(actividades);
        assertFalse(actividades.isEmpty());
        verify(actividadEmpleadoRepository, times(1)).findAllByEmpleado_IdUsuario(empleado.getIdUsuario());
    }

    @Test
    public void testEliminarActividad() {
        // Arrange
        when(actividadEmpleadoRepository.findById(actividadEmpleado.getIdActividad()))
                .thenReturn(Optional.of(actividadEmpleado));

        // Act
        boolean resultado = actividadEmpleadoService.eliminarActividad(actividadEmpleado.getIdActividad());

        // Assert
        assertTrue(resultado);
        verify(actividadEmpleadoRepository, times(1)).deleteById(actividadEmpleado.getIdActividad());
    }

    @Test
    public void testEliminarActividadNoExistente() {
        // Arrange
        when(actividadEmpleadoRepository.findById(actividadEmpleado.getIdActividad())).thenReturn(Optional.empty());

        // Act
        boolean resultado = actividadEmpleadoService.eliminarActividad(actividadEmpleado.getIdActividad());

        // Assert
        assertFalse(resultado);
    }
}
