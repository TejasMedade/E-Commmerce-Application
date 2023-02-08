package com.masai.payloads;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {

	private Object content;

	private Boolean isEmpty;

	private Integer pageNumber;

	private Integer pageSize;

	private Integer totalPages;

	private Long totalElements;

	private Boolean lastPage;

	private Sort sort;

}
