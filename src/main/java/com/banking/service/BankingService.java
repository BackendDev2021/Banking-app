package com.banking.service;

import java.util.List;

import javax.validation.Valid;

import com.banking.model.Customer;
import com.banking.model.Transaction;
import com.banking.model.TransactionRequest;

public interface BankingService {

	//Register customer and details
	public String registration(@Valid Customer customer);

	//Get transaction details by account number and date
	public List<Transaction> getTransactionsByDates(TransactionRequest request) throws Exception;

	public Object transferMoney(Transaction transaction) throws Exception;

}
