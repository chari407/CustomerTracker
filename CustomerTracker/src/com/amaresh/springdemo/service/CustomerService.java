package com.amaresh.springdemo.service;

import java.util.List;

import com.amaresh.springdemo.entity.Customer;

public interface CustomerService {

	List<Customer> getCustomers();

	void saveCustomer(Customer customer);

	Customer getCustomer(int id);

	void deleteCustomer(int id);

	List<Customer> searchCustomer(String searchName);
}
