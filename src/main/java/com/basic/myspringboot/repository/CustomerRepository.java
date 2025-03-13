package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // select * from customer where cust_Id = "AC0014";
    Optional<Customer> findByCustomerId(String custId);
    // select * from customer where cust_name like '%custName';
    List<Customer> findByCustomerNameContains(String custName);

}
