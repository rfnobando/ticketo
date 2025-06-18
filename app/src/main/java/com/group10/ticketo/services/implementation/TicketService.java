package com.group10.ticketo.services.implementation;

import com.group10.ticketo.constants.TicketStatusConstants;
import com.group10.ticketo.dtos.CreateTicketDTO;
import com.group10.ticketo.dtos.TicketDTO;
import com.group10.ticketo.entities.*;
import com.group10.ticketo.exceptions.TicketNotFoundException;
import com.group10.ticketo.exceptions.ChangeTicketStatusNotAllowedException;
import com.group10.ticketo.repositories.ITicketRepository;
import com.group10.ticketo.services.IMailService;
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
    private final IMailService mailService;

    public TicketService(ITicketRepository ticketRepository, IStatusService statusService,
                         ITicketStatusService ticketStatusService, ICustomerRepository customerRepository,
                         ITicketCategoryRepository ticketCategoryRepository, ITicketMessageRepository ticketMessageRepository, IMailService mailService){
        this.ticketRepository = ticketRepository;
        this.statusService = statusService;
        this.ticketStatusService = ticketStatusService;
        this.customerRepository = customerRepository;
        this.ticketCategoryRepository = ticketCategoryRepository;
        this.ticketMessageRepository = ticketMessageRepository;
        this.mailService = mailService;
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

    @Override
    @Transactional
    public void resolveTicket (Long ticketId, Long employeeId) throws Exception {
        if (ticketId == null || employeeId == null) throw  new Exception("ERROR: Required data missing to create Ticket Status");

        ticketRepository.findById(ticketId).orElseThrow(()->new Exception("ERROR: ticket not found"));
        TicketStatus ticketStatus = ticketStatusService.findLastTicketStatusFromTicket(ticketId);
        if (ticketStatus.getStatus().getName().equals(TicketStatusConstants.IN_PROGRESS)){
            ticketStatusService.finishTicketStatus(ticketStatus.getId());
            ticketStatusService.createTicketStatus(ticketId,TicketStatusConstants.RESOLVED,employeeId);

            Ticket ticket = findByIdTicket(ticketId);
            if(ticket == null) throw new Exception("Error: The ticket is null");
            Customer customer = ticket.getCustomer();
            if(customer == null) throw new Exception("Error: The customer is null");
            User user = customer.getUser();
            if(user == null) throw new Exception("Error: The user is null");

            String subject = "[No Reply] Your Ticket Has Been Marked as Resolved – Ticketo";
            String body = "Dear " + customer.getName() + ",\n\n"
                    + "Your ticket titled \"" + ticket.getTitle() + "\" has been marked as Resolved by our support team.\n\n"
                    + "If you are satisfied with the resolution, no further action is required.\n\n"
                    + "However, if you still need assistance, you can continue the conversation by submitting a new message to your ticket within the next 48 hours. "
                    + "This will automatically change the ticket status back to In Progress.\n\n"
                    + "Please note: after 48 hours, the ticket will remain in Resolved status and it will no longer accept new messages, even if the issue remains unresolved.\n\n"
                    + "This is an automated message. Do not reply to this email.\n\n"
                    + "Thank you for using Ticketo.\n\n"
                    + "Best regards,\nThe Ticketo Support Team";
            mailService.sendMail(user.getEmail(),subject,body);
        }else throw new ChangeTicketStatusNotAllowedException("ERROR: The ticket needs to be ´in progress´ status to be resolved", ticketId);

    }
    @Override
    @Transactional
    public void closeTicket (Long ticketId, Long employeeId) throws Exception{
        if (ticketId == null || employeeId == null) throw  new Exception("ERROR: Required data missing to create Ticket Status");

        ticketRepository.findById(ticketId).orElseThrow(()->new Exception("ERROR: ticket not found"));
        TicketStatus ticketStatus = ticketStatusService.findLastTicketStatusFromTicket(ticketId);
        if (!ticketStatus.getStatus().getName().equals(TicketStatusConstants.CLOSED)){
            ticketStatusService.finishTicketStatus(ticketStatus.getId());
            ticketStatusService.createTicketStatus(ticketId,TicketStatusConstants.CLOSED,employeeId);

            Ticket ticket = findByIdTicket(ticketId);
            if(ticket == null) throw new Exception("Error: The ticket is null");
            Customer customer = ticket.getCustomer();
            if(customer == null) throw new Exception("Error: The customer is null");
            User user = customer.getUser();
            if(user == null) throw new Exception("Error: The user is null");

            String subject = "[No Reply] Your Ticket Has Been Closed – Ticketo";
            String body = "Dear " + customer.getName() + ",\n\n"
                    + "We are writing to inform you that your ticket titled \"" + ticket.getTitle() + "\" has been closed by our support team.\n\n"
                    + "This means the ticket has been finalized and no further messages or replies will be accepted.\n\n"
                    + "If you have a new or related issue, we kindly ask you to open a new ticket through your Ticketo account.\n\n"
                    + "This is an automated message. Do not reply to this email.\n\n"
                    + "Thank you for using Ticketo.\n\n"
                    + "Best regards,\nThe Ticketo Support Team";
            mailService.sendMail(user.getEmail(),subject,body);
        }else throw new ChangeTicketStatusNotAllowedException("ERROR: The ticket is already closed", ticketId);

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

    @Override
    public List<TicketDTO> findTicketsAnsweredByEmployeeAndFilters(Long employeeId,String state,String order){
        List<Ticket> tickets;

        if (state != null && !state.isEmpty()) {
            if (order.equalsIgnoreCase("desc")) {
                tickets = ticketRepository.findAnsweredTicketsByEmployeeAndStatusOrderByCreatedAtDesc(employeeId, state);
            } else {
                tickets = ticketRepository.findAnsweredTicketsByEmployeeAndStatusOrderByCreatedAtAsc(employeeId, state);
            }
        } else {
            if (order.equalsIgnoreCase("desc")) {
                tickets = ticketRepository.findAnsweredTicketsByEmployeeOrderByCreatedAtDesc(employeeId);
            } else {
                tickets = ticketRepository.findAnsweredTicketsByEmployeeOrderByCreatedAtAsc(employeeId);
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

}
