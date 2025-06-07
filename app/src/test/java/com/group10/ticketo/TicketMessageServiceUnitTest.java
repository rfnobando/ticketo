package com.group10.ticketo;

import com.group10.ticketo.entities.TicketMessage;
import com.group10.ticketo.repositories.ITicketMessageRepository;
import com.group10.ticketo.services.implementation.TicketMessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}
