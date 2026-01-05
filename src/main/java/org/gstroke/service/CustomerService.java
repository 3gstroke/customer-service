package org.gstroke.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.gstroke.entity.Customer;
import org.gstroke.exception.CustomerInactiveException;
import org.gstroke.repository.CustomerRepository;

import java.util.Optional;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository repository;

    @Transactional
    public Optional<Customer> getById(Long id) {
        return repository.findByIdOptional(id);
    }

    @Transactional
    public boolean customerExists(Long id) {
        return repository.existsById(id);
    }

    @Transactional
    public Customer validateActiveCustomer(Long id) {
        Customer customer = repository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        if (!"ACTIVE".equals(customer.status)) {
            throw new CustomerInactiveException("Customer is not active");
        }
        return customer;
    }




}
