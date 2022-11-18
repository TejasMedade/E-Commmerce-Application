/**
 * 
 */
package com.masai.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class CurrentAdminSession {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer currentSessionId;

	private Integer adminId;

	private String key;

	private LocalDateTime localDateTime;

}
