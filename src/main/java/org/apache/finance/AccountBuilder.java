package org.apache.finance;

public class AccountBuilder {
	private String id;
	private double amount;
	public AccountBuilder(String id) {
		this.id = id;
	}
	public AccountBuilder add(double amount) {
		this.amount += amount;
		return this;
	}
	
	public AccountBuilder minus(double amount) {
		this.amount -= amount;
		return this;
	}
	
	public AccountBuilder append(AccountBuilder accountBuilder) {
		return add(accountBuilder.toAccount().getAmount());
	}

	public Account toAccount() {
		return new Account(id, amount);
	}
}
