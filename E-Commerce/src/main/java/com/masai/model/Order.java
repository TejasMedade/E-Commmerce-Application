/**
 * 
 */
package com.masai.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.masai.dto.ProductDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tejas
 *
 */
@Entity
@Data
@NoArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	private LocalDate orderDate;
	private String orderStatus;
	private String location;

	@ManyToOne(cascade = CascadeType.ALL)
	private Customer customer;

	@ElementCollection
	private List<ProductDTO> productDtoList = new ArrayList<>();

	private Double total;

	@OneToOne
	private Address address;

}
