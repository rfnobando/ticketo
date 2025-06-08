package com.group10.ticketo.configuration.seeders;

import com.group10.ticketo.entities.*;
import com.group10.ticketo.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsersSeeder implements CommandLineRunner {

    private static final String passwordGeneric = "1234567";

    private final IUserRepository userRepository;

    private final IRoleRepository roleRepository;

    private final IEmployeeRepository employeeRepository;

    private final IDepartmentRepository departmentRepository;

    private final IStatusRepository statusRepository;

    private final ITicketCategoryRepository ticketCategoryRepository;


    public UsersSeeder(IUserRepository userRepository, IRoleRepository roleRepository, IEmployeeRepository employeeRepository,
                       IDepartmentRepository departmentRepository, IStatusRepository statusRepository, ITicketCategoryRepository ticketCategoryRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.statusRepository = statusRepository;
        this.ticketCategoryRepository = ticketCategoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadRoles();
        loadDepartments();
        loadUsers();
        loadStatuses();
        loadTicketCategories();
    }

    private void loadUsers() {
        if (userRepository.count() == 0) {
            loadEmployeeAdmin();
        }
    }

    private void loadEmployeeAdmin() {
        employeeRepository.save(buildEmployeeAdmin("Juan", "Perez", "admin@gmail.com", passwordGeneric));
    }

    private Employee buildEmployeeAdmin(String name, String surname, String email, String password) {
        User user = User.builder()
                .email(email)
                .password(encryptPassword(password))
                .roles(List.of(roleRepository.findByRole("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Admin role not found"))))
                .build();
        userRepository.save(user);
        Employee employee = new Employee();
        employee.setName(name);
        employee.setSurname(surname);
        employee.setFileNumber("0001");
        employee.setDepartment(departmentRepository.findByName("Administracion").orElseThrow(() -> new RuntimeException("Development department not found")));
        employee.setUser(user);
        return employee;
    }

    private void loadRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(buildRole("ROLE_ADMIN"));
            roleRepository.save(buildRole("ROLE_CUSTOMER"));
        }
    }

    private Role buildRole(String roleType) {
        Role role = new Role();
        role.setRole(roleType);
        return role;
    }
    private void loadStatuses() {
        if (statusRepository.count() == 0) {
            statusRepository.save(buildStatus("PENDING"));
            statusRepository.save(buildStatus("IN_PROGRESS"));
            statusRepository.save(buildStatus("RESOLVED"));
            statusRepository.save(buildStatus("CLOSE"));
        }
    }
    private Status buildStatus(String name) {
        Status status = new Status();
        status.setName(name);
        return status;
    }

    private void loadTicketCategories() {
        if (ticketCategoryRepository.count() == 0) {
            Department soporte = departmentRepository.findByName("Soporte")
                    .orElseThrow(() -> new RuntimeException("Soporte department not found"));
            Department desarrollo = departmentRepository.findByName("Desarrollo")
                    .orElseThrow(() -> new RuntimeException("Desarrollo department not found"));
            Department administracion = departmentRepository.findByName("Administracion")
                    .orElseThrow(() -> new RuntimeException("Administracion department not found"));

            ticketCategoryRepository.save(buildCategory("Problemas técnicos", List.of(soporte)));
            ticketCategoryRepository.save(buildCategory("Sugerencias de mejora", List.of(desarrollo, administracion)));
            ticketCategoryRepository.save(buildCategory("Reclamos administrativos", List.of(administracion)));
            ticketCategoryRepository.save(buildCategory("Errores del sistema", List.of(desarrollo, soporte)));
        }
    }

    private TicketCategory buildCategory(String name, List<Department> departments) {
        TicketCategory category = new TicketCategory();
        category.setName(name);
        category.setDepartments(departments);
        return category;
    }


    private void loadDepartments() {
        if (departmentRepository.count() == 0) {
            departmentRepository.save(buildDepartment("Administracion"));
            departmentRepository.save(buildDepartment("Soporte"));
            departmentRepository.save(buildDepartment("Desarrollo"));
        }
    }

    private Department buildDepartment(String name) {
        Department department = new Department();
        department.setName(name);
        return department;
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(7);
        return passwordEncoder.encode(password);
    }
}