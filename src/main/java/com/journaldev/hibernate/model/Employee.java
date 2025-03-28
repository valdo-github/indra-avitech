package com.journaldev.hibernate.model;

import lombok.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="Employee")
@Data
public class Employee {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="ID", nullable=false, unique=true, length=11)
	private int id;
	
	@Column(name="NAME", length=20, nullable=true)
	private String name;
	
	@Column(name="ROLE", length=20, nullable=true)
	private String role;
	
	@Column(name="insert_time", nullable=true)
	private Date insertTime;
	
}
