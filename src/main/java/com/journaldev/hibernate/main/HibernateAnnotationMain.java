package com.journaldev.hibernate.main;

import java.util.Date;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.journaldev.hibernate.model.User;
import com.journaldev.hibernate.util.HibernateUtil;

public class HibernateAnnotationMain {

	public static void main(String[] args) {
		User emp = new User();
		emp.setName("David");
		emp.setGuid(UUID.randomUUID());

		//Get Session
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
		//start transaction
		session.beginTransaction();
		//Save the Model object
		session.save(emp);
		//Commit transaction
		session.getTransaction().commit();
		System.out.println("Employee ID="+emp.getId());
		
		//terminate session factory, otherwise program won't end
		sessionFactory.close();
	}

}
