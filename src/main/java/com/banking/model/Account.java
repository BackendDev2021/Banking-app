package com.banking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_account")
public class Account {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "account_holder_name")
	private String accountHolder;
	
	@Column(name = "account_number",unique = true)
	private Integer accountNumber;
	
	@Column(name = "account_balance")
	private Double balance;
	
	@Column(name = "comments")
	private String comments;
	
}
