/**
 * 
 */
package com.masai.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ASUS
 *
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;

	private LocalDateTime orderDateTime;

	private LocalDate expectedDeliveryDate;

	private Double orderTotalAmount;

	@ManyToOne
	private Customer customer;

	@OneToOne
	private Payment payment;

	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderDetails> listOfOrderDetails = new ArrayList<>();
}
