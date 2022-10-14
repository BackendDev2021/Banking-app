package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.banking.model.Customer;

@EnableJpaRepositories
public interface BankingRepo extends JpaRepository<Customer, Long> {

	@Query("FROM Customer C WHERE C.account.accountNumber = :accountNumber")
	Customer findByAccountNumber(Long accountNumber);

}
