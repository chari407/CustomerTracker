package com.amaresh.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amaresh.springdemo.entity.Customer;
import com.amaresh.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model model)
	{
		//Retrieve the customers from database using the service layer -> delegates the work to DAO layer
		List<Customer> customers= customerService.getCustomers();
		
		//add that data to model
		model.addAttribute("customers",customers);
		
		//return the name of view
		return "listCustomers";
	}
	
	@GetMapping("showFormForAdd")
	public String showFormForAdd(Model model)
	{
		// create a new Customer object
		Customer customer= new Customer();
		
		// add that object to model
		model.addAttribute("customer", customer);
		
		// and return the appropriate view
		return "customerForm";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer)
	{
		// use the service layer to save the customer data attached to model attr.
		customerService.saveCustomer(customer);
		
		// redirect to the home page
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id, Model model)
	{
		// use service to retrieve customer based on id
		Customer customer= customerService.getCustomer(id);
		
		// add that customer object to model for later use
		model.addAttribute("customer", customer);
		
		// return the corresponding view
		return "customerForm";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("customerId") int id)
	{
		// use the service layer to delete the customer based on id
		customerService.deleteCustomer(id);
		
		//redirect to home page
		return "redirect:/customer/list";
	}
	
	@PostMapping("/searchCustomer")
	public String search(@RequestParam("searchName") String searchName, Model model)
	{
		// search for the customer using the service
		List<Customer> customers=customerService.searchCustomer(searchName);
		
		// add any retrieved data to the model
		model.addAttribute("customers", customers);
		
		// return corresponding view page
		return "listCustomers";
	}
}
