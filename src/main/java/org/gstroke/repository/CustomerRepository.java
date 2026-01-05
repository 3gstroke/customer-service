package org.gstroke.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.gstroke.entity.Customer;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    public boolean existsById(Long id) {
        return findByIdOptional(id).isPresent();
    }
}