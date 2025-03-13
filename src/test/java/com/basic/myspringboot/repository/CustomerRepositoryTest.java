package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository repository;

    @Test
    @Rollback(value = false)
    void selectUpdate() {
        Customer customer = new Customer();
        customer.setCustomerId("A001");
        customer.setCustomerName("스프링");
        Customer saveCustomer = repository.save(customer);
        assertThat(saveCustomer).isNotNull();

        Customer customer1 = repository.findByCustomerId("A001")
                .orElseThrow();
        //update 요청하기
        customer1.setCustomerName("스프링부트");
    }

    @Test
    //value=false 이면 rollback 처리 되지 않음
    @Rollback(value = false)
    void insertSelectCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId("A001");
        customer.setCustomerName("스프링");
        //CrudRepository save() 호출해서 Insert 하기
        Customer saveCustomer = repository.save(customer);
        assertThat(saveCustomer).isNotNull();
        assertThat(saveCustomer.getCustomerName()).isEqualTo("스프링");
        assertEquals("A001",saveCustomer.getCustomerId());

        //PK로 조회하기
        Optional<Customer> optionalById = repository.findById(1L);
        if(optionalById.isPresent()){
            Customer existCustomer = optionalById.get();
            assertThat(existCustomer.getCustomerName()).isEqualTo("스프링");
        }else {
            System.out.println("customer not found");
        }

        //ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)
        //Consumer : void accept(T), Runnable : void run()
        optionalById.ifPresentOrElse(
                cust -> System.out.println(cust.getCustomerName()), //Customer
                () -> System.out.println((">> customer not found")) //Runnable
        );
        //T orElseThrow(Supplier<? extends X> exceptionSupplier)  X extends Throwable
        //Supplier : T get()
        Customer aCustomer = repository.findByCustomerId("A001") //Optional<Customer>
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
        //assertThat(aCustomer).isNull();
    }
}