/**
 * 
 */
package com.masai.servicesImplementation;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.masai.exceptions.FileTypeNotValidException;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Category;
import com.masai.model.Image;
import com.masai.model.Product;
import com.masai.modelRequestDto.ProductRequestDto;
import com.masai.modelRequestDto.ProductUpdateRequestDto;
import com.masai.modelResponseDto.ProductResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.ImageResponse;
import com.masai.repositories.CategoryRepo;
import com.masai.repositories.ProductRepo;
import com.masai.services.FileServices;
import com.masai.services.ProductServices;

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

	@Autowired
	private FileServices fileServices;

	@Value("${project.image}")
	private String path;

	@Override
	public ProductResponseDto addProduct(Integer categoryId, ProductRequestDto productRequestDto,
			MultipartFile[] images)
			throws ResourceNotFoundException, ResourceNotAllowedException, IOException, FileTypeNotValidException {

		List<MultipartFile> list = Arrays.asList(images);

		if (list.size() > 0) {

			Category category = this.categoryRepo.findById(categoryId)
					.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

			Integer discountPercentage;

			if (Boolean.TRUE.equals(category.getActive())) {

				discountPercentage = productRequestDto.getDiscountPercentage();

				Double marketPrice = productRequestDto.getMarketPrice();

				Double discountOnMarketPrice = marketPrice * discountPercentage / 100;

				Product product = this.toProduct(productRequestDto);
				Long l = (long) 0;
				product.setSalePrice(marketPrice - discountOnMarketPrice);
				product.setCategory(category);
				product.setAvailable(true);
				product.setOnDiscountSale(false);
				product.setBuyerschoice(false);
				product.setTotalSales(l);
				// Adding Images to Product

				List<Image> listOfProductImages = new ArrayList<>();

				for (MultipartFile file : list) {

					ImageResponse imageResponse = this.fileServices.addImage(path, file);

					String url = "http://localhost:8088/bestbuy/products/image/";

					Image image = new Image();

					image.setImageName(imageResponse.getFileName());
					image.setImageUrl(url.concat(imageResponse.getFileName()));

					listOfProductImages.add(image);
				}

				product.setImages(listOfProductImages);

				Product savedProduct = this.productRepo.save(product);

				return this.toProductResponseDto(savedProduct);

			} else {

				throw new ResourceNotAllowedException("Category", "Category Id", categoryId);

			}

		} else {

			throw new IllegalArgumentException("Products Images List Should Not be Empty");

		}

	}

	// Make Sure you don't give Validation to Update

	@Override
	public ProductResponseDto updateProductDetails(Integer productId, ProductUpdateRequestDto productRequestDto)
			throws ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		if (productRequestDto.getBrand() != null) {
			product.setBrand(productRequestDto.getBrand());
		}

		if (productRequestDto.getDiscountPercentage() != null) {

			product.setDiscountPercentage(productRequestDto.getDiscountPercentage());

			Double discountOnMarketPrice = product.getMarketPrice() * productRequestDto.getDiscountPercentage() / 100;

			product.setSalePrice(product.getMarketPrice() - discountOnMarketPrice);
		}

		if (productRequestDto.getMarketPrice() != null) {
			product.setMarketPrice(productRequestDto.getMarketPrice());
		}

		if (productRequestDto.getProductDescription() != null) {
			product.setProductDescription(productRequestDto.getProductDescription());
		}

		if (productRequestDto.getProductName() != null) {
			product.setProductName(productRequestDto.getProductName());
		}

		if (productRequestDto.getStockQuantity() != null) {
			product.setStockQuantity(productRequestDto.getStockQuantity());
		}
		if (productRequestDto.getType() != null) {
			product.setType(productRequestDto.getType());
		}

		if (productRequestDto.getManufacturingMonth() != null) {
			product.setManufacturingMonth(productRequestDto.getManufacturingMonth());
		}

		if (productRequestDto.getManufacturingYear() != null) {
			product.setManufacturingYear(productRequestDto.getManufacturingYear());
		}

		Product savedproduct = this.productRepo.save(product);

		return this.toProductResponseDto(savedproduct);

	}

	@Override
	public ApiResponse deleteProductById(Integer productId) throws ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		this.productRepo.delete(product);

		return new ApiResponse(LocalDateTime.now(), "Product Successfully Deleted !", true, product);
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
	public Page<Product> getProductsByPage(Integer page, Integer size) {

		Pageable pageable = PageRequest.of(page, size);

		return this.productRepo.findAll(pageable);

	}

	@Override
	public Page<Product> getProductsOnDiscountSale(Integer page, Integer size, String sortBy, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return this.productRepo.findByOnDiscountSale(true, pageable);
	}

	@Override
	public Page<Product> getProductsOnBuyersChoice(Integer page, Integer size, String sortBy, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return this.productRepo.findByBuyerschoice(true, pageable);

	}

	@Override
	public Page<Product> getProductsByDiscountPercentage(Integer discountPercentage, Integer page, Integer size,
			String sortBy, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return this.productRepo.findByDiscountPercentage(discountPercentage, pageable);

	}

	@Override
	public Page<Product> getSortedByAnyProductDetailsByPage(Integer page, Integer size, String sortBy,
			String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return this.productRepo.findAll(pageable);

	}

	@Override
	public Page<Product> getSortedProductsByRating(Integer page, Integer size, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("rating").ascending()
				: Sort.by("rating").descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return this.productRepo.findAll(pageable);

	}

	@Override
	public Page<Product> getSortedProductsBySalePrice(Integer page, Integer size, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("salePrice").ascending()
				: Sort.by("salePrice").descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return this.productRepo.findAll(pageable);

	}

	@Override
	public Page<Product> getSortedProductsByManufacturingYear(Integer page, Integer size, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("manufacturingYear").ascending()
				: Sort.by("manufacturingYear").descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return this.productRepo.findAll(pageable);

	}

	@Override
	public Page<Product> getSortedProductsByManufacturingMonth(Integer page, Integer size, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("manufacturingMonth").ascending()
				: Sort.by("manufacturingMonth").descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return this.productRepo.findAll(pageable);

	}

	@Override
	public Page<Product> getSortedProductsByAddedDate(Integer page, Integer size, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by("productAddedDate").ascending()
				: Sort.by("productAddedDate").descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return this.productRepo.findAll(pageable);

	}

	@Override
	public Page<Product> findByCategorySortByRating(Integer categoryId, Integer page, Integer size,
			String sortDirection) throws ResourceNotFoundException {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		Pageable pageable = PageRequest.of(page, size);

		return sortDirection.equalsIgnoreCase("asc") ? this.productRepo.findByCategoryOrderByRating(category, pageable)
				: this.productRepo.findByCategoryOrderByRatingDesc(category, pageable);

	}

	@Override
	public Page<Product> findByCategorySortByProductAddedDate(Integer categoryId, Integer page, Integer size,
			String sortDirection) throws ResourceNotFoundException {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		Pageable pageable = PageRequest.of(page, size);

		return sortDirection.equalsIgnoreCase("asc")
				? this.productRepo.findByCategoryOrderByProductAddedDateTime(category, pageable)
				: this.productRepo.findByCategoryOrderByProductAddedDateTimeDesc(category, pageable);

	}

	@Override
	public List<ProductResponseDto> searchProductsByProductNameKeyword(String keyword) {

		List<Product> listOfProducts = this.productRepo.findByproductNameContaining(keyword);

		return listOfProducts.stream().map(this::toProductResponseDto).collect(Collectors.toList());

	}

	@Override
	public ProductResponseDto updateProductCategory(Integer productId, Integer categoryId)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		if (Boolean.TRUE.equals(category.getActive())) {

			product.setCategory(category);

			Product savedProduct = this.productRepo.save(product);

			return this.toProductResponseDto(savedProduct);

		} else {

			throw new ResourceNotAllowedException("Category", "Category Id", categoryId);

		}

	}

	@Override
	public List<ProductResponseDto> searchByCategory(Integer categoryId) throws ResourceNotFoundException {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		List<Product> list = this.productRepo.findByCategory(category);

		return list.stream().map(this::toProductResponseDto).collect(Collectors.toList());
	}

	@Override
	public ProductResponseDto revokeProductOnDiscountSale(Integer productId) throws ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		if (!product.getOnDiscountSale()) {

			Double discountOnMarketPrice = product.getMarketPrice() * (product.getDiscountPercentage() + 2) / 100;

			product.setSalePrice(product.getMarketPrice() - discountOnMarketPrice);
			product.setOnDiscountSale(true);

		} else {

			Double discountOnMarketPrice = product.getMarketPrice() * (product.getDiscountPercentage() - 2) / 100;

			product.setSalePrice(product.getMarketPrice() + discountOnMarketPrice);
			product.setOnDiscountSale(false);
		}

		Product savedproduct = this.productRepo.save(product);

		return this.toProductResponseDto(savedproduct);

	}

	@Override
	public ProductResponseDto revokeAvailability(Integer productId) throws ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		if (product.getAvailable()) {

			product.setAvailable(false);

		} else {

			product.setAvailable(true);

		}

		Product savedProduct = this.productRepo.save(product);

		return this.toProductResponseDto(savedProduct);
	}

	@Override
	public ProductResponseDto revokeBuyersChoice(Integer productId) throws ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		if (!product.getBuyerschoice()) {

			product.setBuyerschoice(true);

		} else {

			product.setBuyerschoice(false);
		}

		Product savedProduct = this.productRepo.save(product);

		return this.toProductResponseDto(savedProduct);

	}

	@Override
	public List<ProductResponseDto> searchByTypeKeyword(String keyword) throws ResourceNotFoundException {

		List<Product> findByTypeContaining = this.productRepo.findByTypeContaining(keyword);

		return findByTypeContaining.stream().map(this::toProductResponseDto).collect(Collectors.toList());
	}

	@Override
	public ProductResponseDto updateProductRating(Integer productId, Double rating) throws ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		product.setRating(rating);

		Product savedProduct = this.productRepo.save(product);

		return this.toProductResponseDto(savedProduct);

	}

	@Override
	public ProductResponseDto updateProductStockQuantity(Integer productId, Integer quantity)
			throws ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		product.setStockQuantity(quantity);

		Product savedProduct = this.productRepo.save(product);

		return this.toProductResponseDto(savedProduct);
	}

	@Override
	public ProductResponseDto updateProductImage(MultipartFile[] images, Integer productId)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		List<MultipartFile> list = Arrays.asList(images);

		if (list.size() > 0) {

			// Adding Images to Product

			List<Image> listOfProductImages = new ArrayList<>();

			for (MultipartFile file : list) {

				ImageResponse imageResponse = this.fileServices.addImage(path, file);

				String url = "http://localhost:8088/bestbuy/products/image/";

				Image image = new Image();

				image.setImageName(imageResponse.getFileName());
				image.setImageUrl(url.concat(imageResponse.getFileName()));

				listOfProductImages.add(image);
			}

			product.setImages(listOfProductImages);

			Product savedProduct = this.productRepo.save(product);

			return this.toProductResponseDto(savedProduct);

		} else {

			throw new IllegalArgumentException("Products Images List Should Not be Empty");
		}
	}

	@Override
	public void serveProductImage(String imageName, HttpServletResponse response)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException {

		InputStream resource = this.fileServices.serveImage(path, imageName);

		response.setContentType(MediaType.IMAGE_JPEG_VALUE);

		StreamUtils.copy(resource, response.getOutputStream());

	}

	@Override
	public ApiResponse serveImageByProductId(Integer productId) throws IOException, ResourceNotFoundException {

		Product product = this.productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Product Id", productId));

		List<Image> images = product.getImages();

		return new ApiResponse(LocalDateTime.now(), true, images);
	}

	@Override
	public ApiResponse deleteProductImage(String fileName) throws IOException {

		boolean delete = this.fileServices.delete(fileName);

		if (delete) {
			return new ApiResponse(LocalDateTime.now(), "Image File Deleted Successfully !", true);
		} else {
			return new ApiResponse(LocalDateTime.now(), "Requested Image File Does Not Exist !", false);
		}

	}

	// Model Mapper Methods

	private Product toProduct(ProductRequestDto productRequestDto) {

		return this.modelMapper.map(productRequestDto, Product.class);

	}

	private ProductResponseDto toProductResponseDto(Product product) {

		return this.modelMapper.map(product, ProductResponseDto.class);

	}

	
	//Add these To Controller Methods
	@Override
	public ApiResponse highestSoldProduct() {

		Optional<Product> product = this.productRepo.findFirstByOrderByTotalSalesDesc();

		if (product.isPresent()) {

			return new ApiResponse(LocalDateTime.now(), "Highest Sold Product", true,
					this.toProductResponseDto(product.get()));
		} else {
			return new ApiResponse(LocalDateTime.now(), "No Highest Sold Product Found", true);
		}
	}

	@Override
	public ApiResponse leastSoldProduct() {
		Optional<Product> product = this.productRepo.findFirstByOrderByTotalSalesAsc();

		if (product.isPresent()) {

			return new ApiResponse(LocalDateTime.now(), "Least Sold Product", true,
					this.toProductResponseDto(product.get()));
		} else {
			return new ApiResponse(LocalDateTime.now(), "No Least Sold Product Found", true);
		}
	}

	@Override
	public Page<Product> highestSoldProductSortedByRating(Double rating, Integer page, Integer size,
			String sortDirection) {

		Pageable pageable = PageRequest.of(page, size);

		return sortDirection.equalsIgnoreCase("asc")
				? this.productRepo.findByRatingOrderByTotalSalesAsc(rating, pageable)
				: this.productRepo.findByRatingOrderByTotalSalesDesc(rating, pageable);

	}

	@Override
	public Page<Product> findBySalePriceOrderByTotalSales(Double salePrice, Integer page, Integer size,
			String sortDirection) {

		Pageable pageable = PageRequest.of(page, size);

		return sortDirection.equalsIgnoreCase("asc")
				? this.productRepo.findBySalePriceOrderByTotalSalesAsc(salePrice, pageable)
				: this.productRepo.findBySalePriceOrderByTotalSalesAsc(salePrice, pageable);

	}

}
