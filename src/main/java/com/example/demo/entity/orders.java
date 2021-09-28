package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public int id;
	@Column(name = "created_at")
	public Date created_at;
	@Column(name = "modified_at")
	public Date modified_at;
	@Column(name = "note")
	public String note;
	@Column(name = "product_price")
	public float product_price;
	@Column(name = "receiver_address")
	public String receiver_address;
	@Column(name = "receiver_name")
	public String receiver_name;
	@Column(name = "receiver_phone")
	public String receiver_phone;
	@Column(name = "size")
	public String size;

	@Column(name = "total_price")
	public float total_price;

	@ManyToOne
	@JoinColumn(name = "buyer")
	private User buyer;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User created_by;
	
	@ManyToOne
	@JoinColumn(name = "modified_by")
	private User modified_by;
}
