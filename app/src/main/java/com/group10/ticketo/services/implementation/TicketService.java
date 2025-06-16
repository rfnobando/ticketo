package com.group10.ticketo.services.implementation;

import com.group10.ticketo.dtos.CreateTicketDTO;
import com.group10.ticketo.dtos.TicketDTO;
import com.group10.ticketo.entities.Customer;
import com.group10.ticketo.entities.Ticket;
import com.group10.ticketo.entities.TicketCategory;
import com.group10.ticketo.entities.TicketMessage;
import com.group10.ticketo.exceptions.TicketNotFoundException;
import com.group10.ticketo.repositories.ITicketRepository;
import com.group10.ticketo.services.ITicketService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.group10.ticketo.repositories.*;
import com.group10.ticketo.services.IStatusService;
import com.group10.ticketo.services.ITicketStatusService;
import java.util.List;

@Service
public class TicketService implements ITicketService {

    private final ITicketRepository ticketRepository;
    private final IStatusService statusService;
    private final ITicketStatusService ticketStatusService;
    private final ICustomerRepository customerRepository;
    private final ITicketCategoryRepository ticketCategoryRepository;
    private final ITicketMessageRepository ticketMessageRepository;

    public TicketService(ITicketRepository ticketRepository, IStatusService statusService,
                         ITicketStatusService ticketStatusService, ICustomerRepository customerRepository,
                         ITicketCategoryRepository ticketCategoryRepository, ITicketMessageRepository ticketMessageRepository){
        this.ticketRepository = ticketRepository;
        this.statusService = statusService;
        this.ticketStatusService = ticketStatusService;
        this.customerRepository = customerRepository;
        this.ticketCategoryRepository = ticketCategoryRepository;
        this.ticketMessageRepository = ticketMessageRepository;
    }

    @Override
    public List<TicketDTO> findByCustomerId(Long customerId){
        List<Ticket> tickets = ticketRepository.findByCustomerId(customerId);

        return tickets.stream()
                .map(ticket -> new TicketDTO(
                        ticket.getId(),
                        ticket.getTitle(),
                        ticket.getCreatedAt(),
                        ticket.getUpdatedAt(),
                        ticketStatusService.findByTicketIdOrderByCreatedAtDesc(ticket.getId()),
                        ticket.getTicketCategory().getName()
                ))
                .toList();
    }
    public Ticket findByIdTicket(Long ticketId) throws Exception {
        return ticketRepository.findById(ticketId).orElseThrow(
                () -> new Exception("ERROR: TicketNotFund"));
    };
    @Override
    public List<TicketDTO> findByCustomerIdAndFilters(Long customerId, String state, String order){
        List<Ticket> tickets;

        if (state != null && !state.isEmpty()) {
            if (order.equalsIgnoreCase("desc")) {
                tickets = ticketRepository.findByCustomerIdAndStatusOrderByCreatedAtDesc(customerId, state);
            } else {
                tickets = ticketRepository.findByCustomerIdAndStatusOrderByCreatedAtAsc(customerId, state);
            }
        } else {
            if (order.equalsIgnoreCase("desc")) {
                tickets = ticketRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
            } else {
                tickets = ticketRepository.findByCustomerIdOrderByCreatedAtAsc(customerId);
            }
        }

        return  tickets.stream()
                .map(ticket -> new TicketDTO(
                        ticket.getId(),
                        ticket.getTitle(),
                        ticket.getCreatedAt(),
                        ticket.getUpdatedAt(),
                        ticketStatusService.findByTicketIdOrderByCreatedAtDesc(ticket.getId()),
                        ticket.getTicketCategory().getName()
                ))
                .toList();
    }

    @Override
    public List<TicketDTO> findTicketsByDepartmentId(Long departmentId) {
        List<Ticket> tickets = ticketRepository.findTicketsByDepartmentId(departmentId);

        return tickets.stream()
                .map(ticket -> new TicketDTO(
                        ticket.getId(),
                        ticket.getTitle(),
                        ticket.getCreatedAt(),
                        ticket.getUpdatedAt(),
                        ticketStatusService.findByTicketIdOrderByCreatedAtDesc(ticket.getId()),
                        ticket.getTicketCategory().getName()
                ))
                .toList();
    }
    public List<TicketDTO> findTicketsByDepartmentIdAndFilters(Long departmentId, String state, String order) {
        List<Ticket> tickets;

        if (state != null && !state.isEmpty()) {
            if (order.equalsIgnoreCase("desc")) {
                tickets = ticketRepository.findByDepartmentIdAndLatestStatusNameOrderByCreatedAtDesc(departmentId, state);
            } else {
                tickets = ticketRepository.findByDepartmentIdAndLatestStatusNameOrderByCreatedAtAsc(departmentId, state);
            }
        } else {
            if (order.equalsIgnoreCase("desc")) {
                tickets = ticketRepository.findByDepartmentIdOrderByCreatedAtDesc(departmentId);
            } else {
                tickets = ticketRepository.findByDepartmentIdOrderByCreatedAtAsc(departmentId);
            }
        }

        return  tickets.stream()
                .map(ticket -> new TicketDTO(
                        ticket.getId(),
                        ticket.getTitle(),
                        ticket.getCreatedAt(),
                        ticket.getUpdatedAt(),
                        ticketStatusService.findByTicketIdOrderByCreatedAtDesc(ticket.getId()),
                        ticket.getTicketCategory().getName()
                ))
                .toList();
    }

    @Override
    @Transactional
    public void createTicket(CreateTicketDTO createTicketDTO) throws Exception{
        if (createTicketDTO.getTitle()== null || createTicketDTO.getTitle().isEmpty() || createTicketDTO.getCustomerId() == null || createTicketDTO.getTicketCategoryId() == null ) {
            throw new Exception("ERROR: Some required ticket fields are missing.");
        }
        Customer customer = customerRepository.findById(createTicketDTO.getCustomerId())
                .orElseThrow(() -> new Exception("ERROR:Customer not found."));

        TicketCategory ticketCategory = ticketCategoryRepository.findById(createTicketDTO.getTicketCategoryId())
                .orElseThrow(() -> new Exception("ERROR:Ticket Category not found."));

        Ticket ticket = new Ticket();
        ticket.setTitle(createTicketDTO.getTitle());
        ticket.setCustomer(customer);
        ticket.setTicketCategory(ticketCategory);

        ticketRepository.save(ticket);

        ticketStatusService.createTicketStatus(ticket.getId(),"PENDING",null);


        TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setTicket(ticket);
        ticketMessage.setBody(createTicketDTO.getMessageBody());
        ticketMessage.setPictureUrl(createTicketDTO.getPictureUrl());
        ticketMessage.setPerson(customer);

        ticketMessageRepository.save(ticketMessage);
    }

    public TicketDTO findById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                ()-> new TicketNotFoundException("ERROR:Ticket con id" +ticketId+"not found.")
        );
        TicketDTO dto = new TicketDTO();

        dto.setId(ticket.getId());
        dto.setTitle(ticket.getTitle());
        dto.setCreatedAt(ticket.getCreatedAt());
        dto.setUpdatedAt(ticket.getUpdatedAt());
        dto.setCurrentStatus(ticketStatusService.findByTicketIdOrderByCreatedAtDesc(ticket.getId()));
        dto.setCategoryName(ticket.getTicketCategory().getName());

        return dto;
    }

    public Long findCustomerId(Long ticketId) throws Exception {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                () -> new Exception("ERROR:Ticket not found.")
        );
        return ticket.getCustomer().getId();
    }
    @Override
    public List<TicketDTO> findTicketsAnsweredByEmployee(Long employeeId) {
        List<Ticket> tickets = ticketRepository.findTicketsAnsweredByEmployee(employeeId);
        return tickets.stream()
                .map(ticket -> new TicketDTO(
                        ticket.getId(),
                        ticket.getTitle(),
                        ticket.getCreatedAt(),
                        ticket.getUpdatedAt(),
                        ticketStatusService.findByTicketIdOrderByCreatedAtDesc(ticket.getId()),
                        ticket.getTicketCategory().getName()
                ))
                .toList();
    }

}
