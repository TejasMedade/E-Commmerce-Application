package com.masai.payloads;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {

	private List<?> content;

	private Integer pageNumber;

	private Integer pageSize;

	private Integer totalPages;

	private Long totalElements;

	private boolean lastPage;
}
