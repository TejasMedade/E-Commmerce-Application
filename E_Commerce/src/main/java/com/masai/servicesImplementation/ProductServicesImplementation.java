/**
 * 
 */
package com.masai.servicesImplementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.masai.modelRequestDto.ProductRequestDto;
import com.masai.modelResponseDto.ProductResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.PageResponse;
import com.masai.repositories.CategoryRepo;
import com.masai.repositories.ProductRepo;
import com.masai.services.ProductServices;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Category;
import com.masai.model.Product;

/**
 * @author tejas
 *
 */
@Service
public class ProductServicesImplementation implements ProductServices {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public ProductResponseDto addProduct(Integer categoryId, ProductRequestDto productRequestDto)
			throws ResourceNotFoundException {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		Product product = this.toProduct(productRequestDto);

		product.setCategory(category);

		Product savedProduct = this.productRepo.save(product);

		return this.toProductResponseDto(savedProduct);
	}

	// Make Sure you don't give Validation to Update

	@Override
	public ProductResponseDto updateProductDetails(Integer productId, ProductRequestDto productRequestDto)
			throws ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		if (productRequestDto.getAvailable() != null) {
			product.setAvailable(productRequestDto.getAvailable());
		}

		if (productRequestDto.getBrand() != null) {
			product.setBrand(productRequestDto.getBrand());
		}

		if (productRequestDto.getBuyerschoice() != null) {
			product.setBuyerschoice(productRequestDto.getBuyerschoice());
		}

		if (productRequestDto.getDiscountPercentage() != null) {
			product.setDiscountPercentage(productRequestDto.getDiscountPercentage());
		}

		if (productRequestDto.getManufacturingMonthYear() != null) {
			product.setManufacturingMonthYear(productRequestDto.getManufacturingMonthYear());
		}

		if (productRequestDto.getMarketPrice() != null) {
			product.setMarketPrice(productRequestDto.getMarketPrice());
		}

		if (productRequestDto.getOnDiscountSale() != null) {
			product.setOnDiscountSale(productRequestDto.getOnDiscountSale());
		}

		if (productRequestDto.getProductDescription() != null) {
			product.setProductDescription(productRequestDto.getProductDescription());
		}

		if (productRequestDto.getProductName() != null) {
			product.setProductName(productRequestDto.getProductName());
		}

		if (productRequestDto.getSalePrice() != null) {
			product.setSalePrice(productRequestDto.getSalePrice());
		}

		if (productRequestDto.getStockQuantity() != null) {
			product.setStockQuantity(productRequestDto.getStockQuantity());
		}
		if (productRequestDto.getType() != null) {
			product.setType(productRequestDto.getType());
		}

		Product savedproduct = this.productRepo.save(product);

		return this.toProductResponseDto(savedproduct);

	}

	@Override
	public ApiResponse deleteProductById(Integer productId) throws ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		this.productRepo.delete(product);

		return new ApiResponse(LocalDateTime.now(), "Product Successfully Deleted !", true);
	}

	@Override
	public ProductResponseDto getProductById(Integer productId) throws ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		return this.toProductResponseDto(product);

	}

	@Override
	public List<ProductResponseDto> getAllProducts() {

		List<Product> allProducts = this.productRepo.findAll();

		return allProducts.stream().map(this::toProductResponseDto).collect(Collectors.toList());
	}

	@Override
	public PageResponse getProductsByPage(Integer page, Integer size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<Product> pagePost = this.productRepo.findAll(pageable);

		List<Product> content = pagePost.getContent();

		List<ProductResponseDto> listofproducts = content.stream().map(this::toProductResponseDto)
				.collect(Collectors.toList());

		PageResponse pageResponse = new PageResponse();

		pageResponse.setContent(listofproducts);
		pageResponse.setTotalElements(pagePost.getTotalElements());
		pageResponse.setPageSize(pagePost.getSize());
		pageResponse.setPageNumber(pagePost.getNumber());
		pageResponse.setTotalPages(pagePost.getTotalPages());
		pageResponse.setLastPage(pagePost.isLast());

		return pageResponse;
	}

	@Override
	public PageResponse getSortedByAnyProductDetailsByPage(Integer page, Integer size, String sortDirection,
			String sortBy) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Product> pagePost = this.productRepo.findAll(pageable);

		List<Product> content = pagePost.getContent();

		List<ProductResponseDto> listofproducts = content.stream().map(this::toProductResponseDto)
				.collect(Collectors.toList());

		PageResponse pageResponse = new PageResponse();

		pageResponse.setContent(listofproducts);
		pageResponse.setTotalElements(pagePost.getTotalElements());
		pageResponse.setPageSize(pagePost.getSize());
		pageResponse.setPageNumber(pagePost.getNumber());
		pageResponse.setTotalPages(pagePost.getTotalPages());
		pageResponse.setLastPage(pagePost.isLast());

		return pageResponse;

	}

	@Override
	public PageResponse getSortedProductsByRating(Integer page, Integer size, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("rating").ascending()
				: Sort.by("rating").descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Product> pagePost = this.productRepo.findAll(pageable);

		List<Product> content = pagePost.getContent();

		List<ProductResponseDto> listofproducts = content.stream().map(this::toProductResponseDto)
				.collect(Collectors.toList());

		PageResponse pageResponse = new PageResponse();

		pageResponse.setContent(listofproducts);
		pageResponse.setTotalElements(pagePost.getTotalElements());
		pageResponse.setPageSize(pagePost.getSize());
		pageResponse.setPageNumber(pagePost.getNumber());
		pageResponse.setTotalPages(pagePost.getTotalPages());
		pageResponse.setLastPage(pagePost.isLast());

		return pageResponse;
	}

	@Override
	public PageResponse getSortedProductsBySalePrice(Integer page, Integer size, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("salePrice").ascending()
				: Sort.by("salePrice").descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Product> pagePost = this.productRepo.findAll(pageable);

		List<Product> content = pagePost.getContent();

		List<ProductResponseDto> listofproducts = content.stream().map(this::toProductResponseDto)
				.collect(Collectors.toList());

		PageResponse pageResponse = new PageResponse();

		pageResponse.setContent(listofproducts);
		pageResponse.setTotalElements(pagePost.getTotalElements());
		pageResponse.setPageSize(pagePost.getSize());
		pageResponse.setPageNumber(pagePost.getNumber());
		pageResponse.setTotalPages(pagePost.getTotalPages());
		pageResponse.setLastPage(pagePost.isLast());

		return pageResponse;

	}

	@Override
	public PageResponse getSortedProductsByManufacturingYear(Integer page, Integer size, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("manufacturingMonthYear").ascending()
				: Sort.by("manufacturingMonthYear").descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Product> pagePost = this.productRepo.findAll(pageable);

		List<Product> content = pagePost.getContent();

		List<ProductResponseDto> listofproducts = content.stream().map(this::toProductResponseDto)
				.collect(Collectors.toList());

		PageResponse pageResponse = new PageResponse();

		pageResponse.setContent(listofproducts);
		pageResponse.setTotalElements(pagePost.getTotalElements());
		pageResponse.setPageSize(pagePost.getSize());
		pageResponse.setPageNumber(pagePost.getNumber());
		pageResponse.setTotalPages(pagePost.getTotalPages());
		pageResponse.setLastPage(pagePost.isLast());

		return pageResponse;

	}

	@Override
	public PageResponse getSortedProductsByAddedDate(Integer page, Integer size, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("productAddedDate").ascending()
				: Sort.by("productAddedDate").descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Product> pagePost = this.productRepo.findAll(pageable);

		List<Product> content = pagePost.getContent();

		List<ProductResponseDto> listofproducts = content.stream().map(this::toProductResponseDto)
				.collect(Collectors.toList());

		PageResponse pageResponse = new PageResponse();

		pageResponse.setContent(listofproducts);
		pageResponse.setTotalElements(pagePost.getTotalElements());
		pageResponse.setPageSize(pagePost.getSize());
		pageResponse.setPageNumber(pagePost.getNumber());
		pageResponse.setTotalPages(pagePost.getTotalPages());
		pageResponse.setLastPage(pagePost.isLast());

		return pageResponse;

	}

	@Override
	public List<ProductResponseDto> searchProductsByProductNameKeyword(String keyword) {

		List<Product> listOfProducts = this.productRepo.findByproductNameContaining(keyword);

		return listOfProducts.stream().map(this::toProductResponseDto).collect(Collectors.toList());

	}

	@Override
	public ProductResponseDto updateProductCategory(Integer productId, Integer categoryId)
			throws ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		product.setCategory(category);

		Product savedProduct = this.productRepo.save(product);

		return this.toProductResponseDto(savedProduct);
	}

	@Override
	public List<ProductResponseDto> searchByCategory(Integer categoryId) throws ResourceNotFoundException {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		List<Product> list = this.productRepo.findByCategory(category);

		return list.stream().map(this::toProductResponseDto).collect(Collectors.toList());
	}

	private Product toProduct(ProductRequestDto productRequestDto) {

		return this.modelMapper.map(productRequestDto, Product.class);

	}

	private ProductResponseDto toProductResponseDto(Product product) {

		return this.modelMapper.map(product, ProductResponseDto.class);

	}

}
