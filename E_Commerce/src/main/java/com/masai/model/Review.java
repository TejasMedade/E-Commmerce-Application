/**
 * 
 */
package com.masai.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ASUS
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

	// Image

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reviewId;

	@Lob
	private String customerReview;

	@Max(5)
	private Double customerRating;
	
	@ElementCollection
	private List<Image> images;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime reviewTimeStamp;

	@ManyToOne
	private Customer customer;

	@ManyToOne
	private Product product;
}
