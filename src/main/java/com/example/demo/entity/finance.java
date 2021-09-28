package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "finance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class finance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public int id;
	@Column(name = "price")
	public float price;
	@Column(name = "create_at")
	public Date create_at;
	@Column(name = "create_by")
	public String create_by;

	@OneToOne
	@JoinColumn(name = "order_id")
	private finance finance;
	
}
