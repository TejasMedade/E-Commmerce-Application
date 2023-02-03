package com.masai.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

	// Image

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer feedbackId;

	@Lob
	private String customerFeedback;

	private Boolean deliveredOrderFeedback;

	private Boolean cancelOrderFeedback;

	private Boolean replacementOrderFeedback;

	@Embedded
	private Image image;

	@Max(5)
	private Double rating;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime feebackTimeStamp;

	@ManyToOne
	private Customer customer;

	@OneToOne
	private Order order;

}
