package com.group10.ticketo;

import com.group10.ticketo.entities.Ticket;
import com.group10.ticketo.entities.TicketMessage;
import com.group10.ticketo.repositories.ITicketMessageRepository;
import com.group10.ticketo.services.implementation.TicketMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketMessageServiceUnitTest {

    @Mock
    private ITicketMessageRepository ticketMessageRepository;

    @InjectMocks
    private TicketMessageService ticketMessageService;

    @Test
    public void testFindByTicketIdReturnsEmptyList() {
        Long ticketId = 123L;

        when(ticketMessageRepository.findByTicketId(ticketId)).thenReturn(Collections.emptyList());

        List<TicketMessage> result = ticketMessageService.findByTicketId(ticketId);

        assertTrue(result.isEmpty());
        verify(ticketMessageRepository).findByTicketId(ticketId);
    }
    @Test
    public void testFindByTicketIdOrderByCreatedAtAscReturnsEmptyList() {
        Long ticketId = 123L;
        when(ticketMessageRepository.findByTicketIdOrderByCreatedAtAsc(ticketId)).thenReturn(Collections.emptyList());

        List<TicketMessage> result = ticketMessageService.findByTicketIdOrderByCreatedAtAsc(ticketId);

        assertTrue(result.isEmpty());
        verify(ticketMessageRepository).findByTicketIdOrderByCreatedAtAsc(ticketId);
    }
    @Test
    public void testFindByTicketIdAndPersonIdReturnsEmptyList() {
        Long ticketId = 123L;
        Long personId = 456L;
        when(ticketMessageRepository.findByTicketIdAndPersonId(ticketId, personId)).thenReturn(Collections.emptyList());

        List<TicketMessage> result = ticketMessageService.findByTicketIdAndPersonId(ticketId, personId);

        assertTrue(result.isEmpty());
        verify(ticketMessageRepository).findByTicketIdAndPersonId(ticketId, personId);
    }
    @Test
    public void testFindFirstByTicketIdOrderByCreatedAtDescReturnsEmptyOptional() {
        Long ticketId = 123L;
        when(ticketMessageRepository.findFirstByTicketIdOrderByCreatedAtDesc(ticketId)).thenReturn(Optional.empty());

        Optional<TicketMessage> result = ticketMessageService.findFirstByTicketIdOrderByCreatedAtDesc(ticketId);

        assertFalse(result.isPresent());
        verify(ticketMessageRepository).findFirstByTicketIdOrderByCreatedAtDesc(ticketId);
    }

    @Test
    public void testFindTicketsByEmployeeId() {
        // Simular un ID de empleado
        Long employeeId = 1L;

        // Simular ticket esperado
        Ticket ticket1 = new Ticket();
        ticket1.setId(10L);

        Ticket ticket2 = new Ticket();
        ticket2.setId(20L);

        List<Ticket> mockTickets = Arrays.asList(ticket1, ticket2);

        // Configurar mock del repository
        Mockito.when(ticketMessageRepository.findTicketsByEmployeeId(employeeId))
                .thenReturn(mockTickets);

        // Ejecutar método del service
        List<Ticket> resultado = ticketMessageService.findTicketsByEmployeeId(employeeId);

        // Validar que el resultado sea el esperado
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals(10L, resultado.get(0).getId());
        Assertions.assertEquals(20L, resultado.get(1).getId());

        // Verificar que se llamó al repository con el ID correcto
        Mockito.verify(ticketMessageRepository, Mockito.times(1)).findTicketsByEmployeeId(employeeId);
    }


}
