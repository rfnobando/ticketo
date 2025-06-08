package com.group10.ticketo;

import com.group10.ticketo.entities.*;
import com.group10.ticketo.repositories.ITicketRepository;
import com.group10.ticketo.services.IStatusService;
import com.group10.ticketo.services.ITicketStatusService;
import com.group10.ticketo.services.implementation.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketServiceTest {

    @Mock
    private ITicketRepository ticketRepository;

    @Mock
    private IStatusService statusService;

    @Mock
    private ITicketStatusService ticketStatusService;

    @InjectMocks
    private TicketService ticketService;

    private Customer customer;
    private TicketCategory category;
    private Status pendingStatus;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Simular un Customer y una categoría
        customer = new Customer();
        customer.setId(1L);

        category = new TicketCategory();
        category.setId(1L);

        // Simular un estado "PENDING"
        pendingStatus = new Status();
        pendingStatus.setId(1L);
        pendingStatus.setName("PENDING");
    }

    @Test
    public void testCreateTicket_ok() throws Exception {
        // Arrange
        Ticket ticketMock = new Ticket();
        ticketMock.setId(1L);
        ticketMock.setStates(new ArrayList<>());
        ticketMock.setMessages(new ArrayList<>());

        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticketMock);
        when(statusService.findByName("PENDING")).thenReturn(pendingStatus);
        when(ticketStatusService.createTicketStatus(any(), any(), isNull())).thenReturn(new TicketStatus());

        // Act
        ticketService.createTicket("Soporte técnico", customer, category);

        // Assert
        verify(ticketRepository, times(2)).save(any(Ticket.class)); // una vez antes y otra después
        verify(statusService).findByName("PENDING");
        verify(ticketStatusService).createTicketStatus(any(), eq(pendingStatus), isNull());
    }

    @Test
    public void testCreateTicket_missingTitle_throwsException() {
        Exception e = assertThrows(Exception.class, () ->
                ticketService.createTicket(null, customer, category));

        assertTrue(e.getMessage().contains("required ticket fields"));
    }
}