package com.nqt.spring_boot_espada_store.repository;

import com.nqt.spring_boot_espada_store.entity.Customer;
import com.nqt.spring_boot_espada_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findByUser(User user);

    void deleteCustomerByUser(User user);
}
