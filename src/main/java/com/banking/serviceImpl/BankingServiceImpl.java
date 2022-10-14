package com.banking.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.banking.model.Customer;
import com.banking.model.Transaction;
import com.banking.model.TransactionRequest;
import com.banking.repository.BankingRepo;
import com.banking.service.BankingService;

/**
 * Service Implementation class to execute the business logic of register and
 * fetch transaction history of customer
 * 
 * @author Madhu
 *
 */
@Service
public class BankingServiceImpl implements BankingService {

	@Autowired
	BankingRepo repository;

	@Override
	public String registration(@Valid Customer customer) {
		Customer saveCustomerToDb = new Customer(customer.getId(), customer.getName(), customer.getMobile(),
				customer.getEmailId(), customer.getAge(), customer.getGender(), customer.getAccount(), null);
		Random accountNumber = new Random(100000000);
		customer.getAccount().setAccountNumber(accountNumber.nextInt(99999999));
		repository.save(saveCustomerToDb);
		return "Customer resgistered successfully";
	}

	@Override
	public List<Transaction> getTransactionsByDates(TransactionRequest request) throws Exception {

		// Find customer by account number
		//Customer existCustomer = repository.findByAccountNumber(request.getAccountNumber());
		Optional.ofNullable(repository.findByAccountNumber(request.getAccountNumber()))
				.map(customer -> customer.getTransactions())
				.orElseThrow(() -> new Exception("Ooops....!Requesting customer not found"));
//		if (ObjectUtils.isEmpty(existCustomer)) {
//			// Handling not found or Internal server Error exception
//			throw new Exception("Ooops....!Requesting customer not found");
//		}
		// Fetching transaction using dates
//		LocalDate presentdate = LocalDate.now();
//		Period days_30 = Period.ofDays(30);
//		List<Transaction> transactions = existCustomer.getTransactions().stream()
//				.filter(dates -> dates.getTransactionDate().isAfter(request.getTransactionDate()))
//				.collect(Collectors.toList());
		return null;
	}

	@Override
	public Transaction transferMoney(Transaction transaction) throws Exception {
		// From customer
		Customer existFromCustomer = repository.findByAccountNumber(transaction.getFromAccNo());
		if (ObjectUtils.isEmpty(existFromCustomer)) {
			// Handling not found or Internal server Error exception
			throw new Exception("Hey....!You aren't authorized customer for money transferring");
		}
		Double existingBalanceOfFromCustomer = existFromCustomer.getAccount().getBalance() - transaction.getAmount();
		existFromCustomer.getAccount().setBalance(existingBalanceOfFromCustomer);
		existFromCustomer.getAccount().setComments(transaction.getComments());
		existFromCustomer.getTransactions().add(transaction);
		repository.save(existFromCustomer);

		// To Customer
		Customer findCustomer = repository.findByAccountNumber(transaction.getToAccNo());
		if (ObjectUtils.isEmpty(findCustomer)) {
			// Handling not found or Internal server Error exception
			throw new Exception("Ooops....!Requesting customer is not there for money transferring");
		}
		Double existingBalanceOfCustomer = findCustomer.getAccount().getBalance() + transaction.getAmount();
		findCustomer.getAccount().setBalance(existingBalanceOfCustomer);
		findCustomer.getAccount().setComments(transaction.getComments());
		findCustomer.getTransactions().add(transaction);
		repository.save(findCustomer);
		return transaction;
	}

}
