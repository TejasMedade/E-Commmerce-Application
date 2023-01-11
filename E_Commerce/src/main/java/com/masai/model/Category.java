/**
 * 
 */
package com.masai.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catgeoryId_generator")
	@SequenceGenerator(name = "catgeoryId_generator", sequenceName = "categoryId_sequence", allocationSize = 5001)
	private Integer categoryId;

	private String categoryName;

	private String subCategory;
	
	@Column(length = Integer.MAX_VALUE)
	private String categoryDescription;

	private Boolean active;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Product> listOfProducts = new ArrayList<>();

}
