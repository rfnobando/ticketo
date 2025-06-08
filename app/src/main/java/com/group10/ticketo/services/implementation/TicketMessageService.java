package com.group10.ticketo.services.implementation;

import com.group10.ticketo.dtos.CreateTicketMessageDTO;
import com.group10.ticketo.dtos.TicketMessageDTO;
import com.group10.ticketo.entities.Employee;
import com.group10.ticketo.entities.Person;
import com.group10.ticketo.entities.Ticket;
import com.group10.ticketo.entities.TicketMessage;
import com.group10.ticketo.repositories.IPersonRepository;
import com.group10.ticketo.repositories.ITicketMessageRepository;
import com.group10.ticketo.repositories.ITicketRepository;
import com.group10.ticketo.services.IMailService;
import com.group10.ticketo.services.ITicketMessageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketMessageService implements ITicketMessageService {


    private final ITicketMessageRepository ticketMessageRepository;
    private final ITicketRepository ticketRepository;
    private final IPersonRepository personRepository;
    private final IMailService mailService;

    public TicketMessageService(ITicketMessageRepository ticketMessageRepository, ITicketRepository ticketRepository,
                                IPersonRepository personRepository, IMailService mailService){
        this.ticketMessageRepository = ticketMessageRepository;
        this.ticketRepository = ticketRepository;
        this.personRepository = personRepository;
        this.mailService = mailService;
    }

    @Override
    //Trae todos los mensajes por ID de ticket
    public List<TicketMessageDTO> findByTicketId(Long ticketId){
        List<TicketMessage> ticketMessages =  ticketMessageRepository.findByTicketId(ticketId);

        return ticketMessages.stream()
                .map(ticketMessage -> new TicketMessageDTO(
                        ticketMessage.getBody(),
                        ticketMessage.getPictureUrl(),
                        ticketMessage.getCreatedAt(),
                        ticketMessage.getPerson().getId(),
                        ticketMessage.getPerson().getName()
                )).toList();
    }
    @Override
    //Traer mensajes por ticket y ordenados por fecha
    public List<TicketMessage> findByTicketIdOrderByCreatedAtAsc(Long ticketId){
        return ticketMessageRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);
    }

    @Override
    //Filtrar por ticket y persona
    public List<TicketMessage> findByTicketIdAndPersonId(Long ticketId, Long personId){
        return ticketMessageRepository.findByTicketIdAndPersonId(ticketId,personId);
    }

    @Override
    //Ultimo mensaje de un ticket
    public Optional<TicketMessage> findFirstByTicketIdOrderByCreatedAtDesc(Long ticketId){
        return ticketMessageRepository.findFirstByTicketIdOrderByCreatedAtDesc(ticketId);
    }
    @Override
    public List<Ticket> findTicketsByEmployeeId(Long employeeId){
        return ticketMessageRepository.findTicketsByEmployeeId(employeeId);
    }

    @Override
    public void createTicketMessage(CreateTicketMessageDTO createTicketMessageDTO) throws Exception {
        if (createTicketMessageDTO.getBody() == null || createTicketMessageDTO.getBody().isEmpty() ||
                createTicketMessageDTO.getTicketId() == null ||
                createTicketMessageDTO.getPersonId() == null) {
            throw new Exception("ERROR: Some required ticket message fields are missing");
        }

        Ticket ticket = ticketRepository.findById(createTicketMessageDTO.getTicketId()).orElseThrow(
                ()-> new Exception("ERROR: Ticket not found."));

        Person person = personRepository.findById(createTicketMessageDTO.getPersonId()).orElseThrow(
                () -> new Exception("ERROR: Person not found")
        );

        TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setTicket(ticket);
        ticketMessage.setBody(createTicketMessageDTO.getBody());
        ticketMessage.setPerson(person);
        ticketMessage.setPictureUrl(createTicketMessageDTO.getPictureUrl());

        ticketMessageRepository.save(ticketMessage);

        if(person instanceof Employee){
            mailService.sendTicketMessageToClient(ticketMessage);
        }

    }

}
