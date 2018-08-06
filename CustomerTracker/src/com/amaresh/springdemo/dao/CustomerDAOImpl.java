package com.amaresh.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amaresh.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		
		// get current session
		Session session = sessionFactory.getCurrentSession();
		
		// create the necessary query to retrieve customers
		Query<Customer> query=session.createQuery("from Customer order by lastName", Customer.class);
		
		// execute that query
		List<Customer> customers=query.getResultList();
		
		// return the retrieved data 
		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {
		
		// get current session
		Session session=sessionFactory.getCurrentSession();
		
		// use save or update to save a new customer and if already exists, update that data
		session.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(int id) {
		
		// get current session
		Session session=sessionFactory.getCurrentSession();
		
		// query to get the customer whose id is 'id'
		Query<Customer> query=session.createQuery("from Customer where id="+id, Customer.class);
		
		// execute the query
		Customer customer=query.getSingleResult();
		
		
		// can also be done like this
		//Customer customer=session.get(Customer.class, id);
		
		// return the data
		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		
		// get current session
		Session session=sessionFactory.getCurrentSession();
		
		// query to delete customer with particular id
		Query query = session.createQuery("delete from Customer where id=:id");
		
		// set the id value
		query.setParameter("id", id);
		
		// execute the query
		query.executeUpdate();
		
		// can also be done like this
		//Customer customer=session.get(Customer.class, id);
		
		//session.delete(customer);		
	}

	@Override
	public List<Customer> searchCustomer(String searchName) {
		
		// get current session
		Session session = sessionFactory.getCurrentSession();
		
		Query<Customer> query = null;
		
		// work only if name is entered
		if(searchName!=null&&searchName.trim().length()>0)
		{
			//query to search for the given name-> might be first or last name 
			query = session.createQuery("from Customer where lower(firstName) like :name or lower(lastName) like :name",Customer.class);
			query.setParameter("name", "%" + searchName.trim().toLowerCase() + "%");
		}
		else
		{
			// query to retrieve all the data as name is not entered
			query = session.createQuery("from Customer", Customer.class);
		}	
		
		// execute query
		List<Customer> customers = query.getResultList();
		
		//return data
		return customers;
	}

}
