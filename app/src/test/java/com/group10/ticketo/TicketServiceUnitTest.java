/*package com.group10.ticketo;

import com.group10.ticketo.dtos.TicketDTO;
import com.group10.ticketo.entities.Ticket;
import com.group10.ticketo.repositories.ITicketRepository;
import com.group10.ticketo.services.implementation.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceUnitTest {
    @Mock
    private ITicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    public void testFindByCustomerIdReturnsList() {
        Long customerId = 1L;

        Ticket ticket1 = new Ticket();
        ticket1.setId(100L);

        Ticket ticket2 = new Ticket();
        ticket2.setId(200L);

        List<Ticket> mockTickets = Arrays.asList(ticket1, ticket2);

        // Configurar comportamiento del mock
        when(ticketRepository.findByCustomerId(customerId)).thenReturn(mockTickets);

        // Ejecutar método del servicio
        List<TicketDTO> result = ticketService.findByCustomerId(customerId);

        // Validaciones
        assertEquals(2, result.size());
        assertEquals(100L, result.get(0).getId());
        assertEquals(200L, result.get(1).getId());

        // Verificar que el repositorio se haya llamado correctamente
        verify(ticketRepository, times(1)).findByCustomerId(customerId);
    }

    @Test
    public void testFindTicketsByDepartmentIdReturnsList() {
        Long departmentId = 1L;

        Ticket ticket1 = new Ticket();
        ticket1.setId(300L);

        Ticket ticket2 = new Ticket();
        ticket2.setId(400L);

        List<Ticket> mockTickets = Arrays.asList(ticket1, ticket2);

        // Simular comportamiento del repositorio
        when(ticketRepository.findTicketsByDepartmentId(departmentId)).thenReturn(mockTickets);

        // Llamar al método del servicio
        List<Ticket> result = ticketService.findTicketsByDepartmentId(departmentId);

        // Validaciones
        assertEquals(2, result.size());
        assertEquals(300L, result.get(0).getId());
        assertEquals(400L, result.get(1).getId());

        // Verificar interacción con el repositorio
        verify(ticketRepository, times(1)).findTicketsByDepartmentId(departmentId);
    }
}
*/