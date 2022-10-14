package com.banking.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.model.Customer;
import com.banking.model.Transaction;
import com.banking.model.TransactionRequest;
import com.banking.service.BankingService;

/**
 * Banking application to register customer and their account infos and fetch
 * transaction histories using below apis
 * 
 * @author Madhu
 *
 */

@RestController
@RequestMapping("/banking-transaction-application")
@Validated
public class BankingController {

	@Autowired
	private BankingService service;

	/**
	 * Customer regsitration controller api to save to the Database and informations
	 * 
	 * @param customer
	 * @return
	 */
	@PostMapping("/customer-registration")
	public ResponseEntity<String> registration(@Valid @RequestBody Customer customer) {
		return ResponseEntity.ok().body(service.registration(customer));
	}

	@PostMapping("/transfer-money")
	public ResponseEntity<?> transferMoney(@RequestBody Transaction transaction) throws Exception{
		return ResponseEntity.ok().body(service.transferMoney(transaction));
	}
	
	
	/**
	 * Fetch customer's transactions using account number and dates
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/transactions-by-date")
	public ResponseEntity<List<Transaction>> getTransactionsByDates(@RequestBody TransactionRequest request)
			throws Exception {
		return ResponseEntity.ok(service.getTransactionsByDates(request));
	}
}
