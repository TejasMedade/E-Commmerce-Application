/**
 * 
 */
package com.masai.model;

import javax.persistence.Embeddable;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tejas
 *
 */

@Data
@NoArgsConstructor
@Embeddable
public class Category {

	private Integer catId;

	private String categoryName;

}
