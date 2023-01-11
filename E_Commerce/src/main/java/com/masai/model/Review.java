/**
 * 
 */
package com.masai.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author ASUS
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Review {

	// Image

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reviewId;

	@Column(length = Integer.MAX_VALUE)
	private String customerReview;

	private Integer customerRating;

	private LocalDateTime timestamp;

	@ManyToOne
	private Customer customer;

	@ManyToOne
	private Product product;
}
