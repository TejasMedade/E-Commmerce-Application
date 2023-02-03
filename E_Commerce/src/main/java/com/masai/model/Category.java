/**
 * 
 */
package com.masai.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tejas
 *
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;

	private String categoryName;

	@Lob
	private String categoryDescription;

	private Boolean active;
	
	@CreationTimestamp
	@Column(nullable = false,updatable = false)
	private LocalDateTime categoryAddedDateTime;

	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime categoryUpdatedDateTime;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Product> listOfProducts = new ArrayList<>();

}
