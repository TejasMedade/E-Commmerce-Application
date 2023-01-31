/**
 * 
 */
package com.masai.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masai.exceptions.FileTypeNotValidException;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.Product;
import com.masai.modelRequestDto.ProductRequestDto;
import com.masai.modelRequestDto.ProductUpdateRequestDto;
import com.masai.modelResponseDto.ProductResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.AppConstants;
import com.masai.payloads.ProductModelAssembler;
import com.masai.services.ProductServices;

/**
 * @author tejas
 *
 */
@RestController
@RequestMapping("/bestbuy/products")
public class ProductController {

	@Autowired
	private ProductServices productServices;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductModelAssembler productModelAssembler;

	@Autowired
	private PagedResourcesAssembler<Product> pagedResourcesAssembler;

	@GetMapping("/all")
	public ResponseEntity<List<ProductResponseDto>> getAllProductsHandler() {

		List<ProductResponseDto> allProducts = this.productServices.getAllProducts();

		return new ResponseEntity<List<ProductResponseDto>>(allProducts, HttpStatus.OK);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponseDto> getProductByIdHandler(@PathVariable("productId") Integer productId)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		ProductResponseDto productResponseDto = this.productServices.getProductById(productId);

		// Self Link
		productResponseDto
				.add(linkTo(methodOn(ProductController.class).getProductByIdHandler(productId)).withSelfRel());

		// Category Link
		productResponseDto.add(linkTo(
				methodOn(CategoryController.class).getCategoryHandler(productResponseDto.getCategory().getCategoryId()))
				.withRel("category"));

		// Product by Category Link
		productResponseDto.add(linkTo(methodOn(ProductController.class)
				.searchByCategoryHandler(productResponseDto.getCategory().getCategoryId()))
				.withRel("products by category "));

		// Collection Links
		productResponseDto.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		// AddtoCart
		productResponseDto.add(linkTo(methodOn(CartController.class).addProducttoCartHandler(null, productId, 1))
				.withRel("add to cart"));

		// BuyProduct
		productResponseDto.add(
				linkTo(methodOn(OrderController.class).orderProduct(null, null, productId, 1)).withRel("buy product"));

		return new ResponseEntity<ProductResponseDto>(productResponseDto, HttpStatus.OK);
	}

	@GetMapping("/all/page")
	public ResponseEntity<CollectionModel<ProductResponseDto>> getProductsByPageHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize

	) {

		Page<Product> pageResponse = this.productServices.getProductsByPage(pageNumber, pageSize);

		PagedModel<ProductResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, productModelAssembler);

		// Collection Links
		model.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<ProductResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/all/onsale")
	public ResponseEntity<CollectionModel<ProductResponseDto>> getProductsOnDiscountSale(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.PRODUCTSORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		Page<Product> pageResponse = this.productServices.getProductsOnDiscountSale(pageNumber, pageSize, sortBy,
				sortDirection);

		PagedModel<ProductResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, productModelAssembler);

		// Collection Links
		model.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<ProductResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/all/onchoice")
	public ResponseEntity<CollectionModel<ProductResponseDto>> getProductsOnBuyersChoice(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.PRODUCTSORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		Page<Product> pageResponse = this.productServices.getProductsOnBuyersChoice(pageNumber, pageSize, sortBy,
				sortDirection);

		PagedModel<ProductResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, productModelAssembler);

		// Collection Links
		model.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<ProductResponseDto>>(model, HttpStatus.OK);

	}

	@GetMapping("/all/discount")
	public ResponseEntity<CollectionModel<ProductResponseDto>> getProductsByDiscountPercentage(
			@PathVariable("discountPercentage") Integer discountPercentage,
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.PRODUCTSORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		Page<Product> pageResponse = this.productServices.getProductsByDiscountPercentage(discountPercentage,
				pageNumber, pageSize, sortBy, sortDirection);

		PagedModel<ProductResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, productModelAssembler);

		// Collection Links
		model.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<ProductResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/all/sort/rating")
	public ResponseEntity<CollectionModel<ProductResponseDto>> getSortedProductsByRatingHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection) {

		Page<Product> pageResponse = this.productServices.getSortedProductsByRating(pageNumber, pageSize,
				sortDirection);

		PagedModel<ProductResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, productModelAssembler);

		// Collection Links
		model.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<ProductResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/all/filter/category/{categoryId}/sortby/rating")
	public ResponseEntity<CollectionModel<ProductResponseDto>> findByCategorySortByRating(
			@PathVariable("categoryId") Integer categoryId,
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		Page<Product> pageResponse = this.productServices.findByCategorySortByRating(categoryId, pageNumber, pageSize,
				sortDirection);

		PagedModel<ProductResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, productModelAssembler);

		// Product by Category Link
		model.add(linkTo(methodOn(ProductController.class).searchByCategoryHandler(categoryId))
				.withRel("products by category "));

		// Collection Links
		model.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<ProductResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/all/filter/category/{categoryId}/sortby/addeddate")
	public ResponseEntity<CollectionModel<ProductResponseDto>> findByCategorySortByProductAddedDate(
			@PathVariable("categoryId") Integer categoryId,
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.RATINGSORTDIRECTION, required = false) String sortDirection)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		Page<Product> pageResponse = this.productServices.findByCategorySortByProductAddedDate(categoryId, pageNumber,
				pageSize, sortDirection);

		PagedModel<ProductResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, productModelAssembler);

		// Product by Category Link
		model.add(linkTo(methodOn(ProductController.class).searchByCategoryHandler(categoryId))
				.withRel("products by category "));

		// Collection Links
		model.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<ProductResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/all/sort/price")
	public ResponseEntity<CollectionModel<ProductResponseDto>> getSortedProductsBySalePriceHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		Page<Product> pageResponse = this.productServices.getSortedProductsBySalePrice(pageNumber, pageSize,
				sortDirection);

		PagedModel<ProductResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, productModelAssembler);

		// Collection Links
		model.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<ProductResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/all/sort/manufacturingyear")
	public ResponseEntity<CollectionModel<ProductResponseDto>> getSortedProductsByManufacturingYearHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		Page<Product> pageResponse = this.productServices.getSortedProductsByManufacturingYear(pageNumber, pageSize,
				sortDirection);

		PagedModel<ProductResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, productModelAssembler);

		// Collection Links
		model.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<ProductResponseDto>>(model, HttpStatus.OK);

	}

	@GetMapping("/all/sort/manufacturingmonth")
	public ResponseEntity<CollectionModel<ProductResponseDto>> getSortedProductsByManufacturingMonthHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		Page<Product> pageResponse = this.productServices.getSortedProductsByManufacturingMonth(pageNumber, pageSize,
				sortDirection);

		PagedModel<ProductResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, productModelAssembler);

		// Collection Links
		model.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<ProductResponseDto>>(model, HttpStatus.OK);

	}

	@GetMapping("/all/sortby")
	public ResponseEntity<CollectionModel<ProductResponseDto>> getSortedByAnyProductDetailsByPageHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.PRODUCTSORTBY, required = false) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		Page<Product> pageResponse = this.productServices.getSortedByAnyProductDetailsByPage(pageNumber, pageSize,
				sortBy, sortDirection);

		PagedModel<ProductResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, productModelAssembler);

		// Collection Links
		model.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<ProductResponseDto>>(model, HttpStatus.OK);

	}

	@GetMapping("/all/sort/dateadded")
	public ResponseEntity<CollectionModel<ProductResponseDto>> getSortedProductsByAddedDateHandler(
			@RequestParam(defaultValue = AppConstants.PAGENUMBER, required = false) Integer pageNumber,
			@RequestParam(defaultValue = AppConstants.PAGESIZE, required = false) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORTDIRECTION, required = false) String sortDirection) {

		Page<Product> pageResponse = this.productServices.getSortedProductsByAddedDate(pageNumber, pageSize,
				sortDirection);

		PagedModel<ProductResponseDto> model = pagedResourcesAssembler.toModel(pageResponse, productModelAssembler);

		// Collection Links
		model.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<ProductResponseDto>>(model, HttpStatus.OK);

	}

	@GetMapping("/all/search/{keyword}")
	public ResponseEntity<CollectionModel<ProductResponseDto>> searchProductsByProductNameKeywordHandler(
			@PathVariable("keyword") String keyword) throws ResourceNotFoundException, ResourceNotAllowedException {

		List<ProductResponseDto> productsByProductNameKeyword = this.productServices
				.searchProductsByProductNameKeyword(keyword);

		for (ProductResponseDto productResponseDto : productsByProductNameKeyword) {

			// Self Link
			productResponseDto.add(
					linkTo(methodOn(ProductController.class).getProductByIdHandler(productResponseDto.getProductId()))
							.withSelfRel());

			// AddtoCart
			productResponseDto.add(linkTo(
					methodOn(CartController.class).addProducttoCartHandler(null, productResponseDto.getProductId(), 1))
					.withRel("add to cart"));

			// BuyProduct
			productResponseDto.add(linkTo(
					methodOn(OrderController.class).orderProduct(null, null, productResponseDto.getProductId(), 1))
					.withRel("buy product"));

		}

		CollectionModel<ProductResponseDto> model = CollectionModel.of(productsByProductNameKeyword);

		// Collection Links
		model.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<ProductResponseDto>>(model, HttpStatus.OK);
	}

	@GetMapping("/all/categories/{categoryId}")
	public ResponseEntity<CollectionModel<ProductResponseDto>> searchByCategoryHandler(
			@PathVariable("categoryId") Integer categoryId)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		List<ProductResponseDto> searchByCategory = this.productServices.searchByCategory(categoryId);

		for (ProductResponseDto productResponseDto : searchByCategory) {

			// Self Link
			productResponseDto.add(
					linkTo(methodOn(ProductController.class).getProductByIdHandler(productResponseDto.getProductId()))
							.withSelfRel());

			// AddtoCart
			productResponseDto.add(linkTo(
					methodOn(CartController.class).addProducttoCartHandler(null, productResponseDto.getProductId(), 1))
					.withRel("add to cart"));

			// BuyProduct
			productResponseDto.add(linkTo(
					methodOn(OrderController.class).orderProduct(null, null, productResponseDto.getProductId(), 1))
					.withRel("buy product"));

		}

		CollectionModel<ProductResponseDto> model = CollectionModel.of(searchByCategory);

		// Product by Category Link
		model.add(linkTo(methodOn(ProductController.class).searchByCategoryHandler(categoryId))
				.withRel("products by category"));

		// Category Link
		model.add(linkTo(methodOn(CategoryController.class).getCategoryHandler(categoryId)).withRel("category"));

		// Collection Links
		model.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<CollectionModel<ProductResponseDto>>(model, HttpStatus.OK);
	}

	@PostMapping("/admin/{categoryId}")
	public ResponseEntity<ProductResponseDto> addProductHandler(@PathVariable("categoryId") Integer categoryId,
			@Valid @RequestParam String productRequestDto, @RequestParam MultipartFile[] images)
			throws ResourceNotFoundException, ResourceNotAllowedException, IOException, FileTypeNotValidException {

		// converting String into JSON
		ProductRequestDto productrequestDto = objectMapper.readValue(productRequestDto, ProductRequestDto.class);

		ProductResponseDto productResponseDto = this.productServices.addProduct(categoryId, productrequestDto, images);

		// Self Link
		productResponseDto
				.add(linkTo(methodOn(ProductController.class).getProductByIdHandler(productResponseDto.getProductId()))
						.withSelfRel());

		// Product by Category Link
		productResponseDto
				.add(linkTo(methodOn(ProductController.class).searchByCategoryHandler(categoryId)).withSelfRel());

		// Category Link
		productResponseDto
				.add(linkTo(methodOn(CategoryController.class).getCategoryHandler(categoryId)).withRel("category"));

		// Collection Links
		productResponseDto.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<ProductResponseDto>(productResponseDto, HttpStatus.CREATED);
	}

	@PutMapping("/admin/{productId}/categories/{categoryId}")
	public ResponseEntity<ProductResponseDto> updateProductCategoryHandler(@PathVariable("productId") Integer productId,
			@PathVariable("categoryId") Integer categoryId)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		ProductResponseDto productResponseDto = this.productServices.updateProductCategory(productId, categoryId);

		// Self Link
		productResponseDto
				.add(linkTo(methodOn(ProductController.class).getProductByIdHandler(productResponseDto.getProductId()))
						.withSelfRel());

		// Category Link
		productResponseDto
				.add(linkTo(methodOn(CategoryController.class).getCategoryHandler(categoryId)).withRel("category"));

		// Product by Category Link
		productResponseDto.add(linkTo(methodOn(ProductController.class).searchByCategoryHandler(categoryId))
				.withRel("product by category"));

		// Collection Links
		productResponseDto.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<ProductResponseDto>(productResponseDto, HttpStatus.OK);
	}

	@PutMapping("/admin/{productId}/onsale")
	ResponseEntity<ProductResponseDto> revokeProductOnDiscountSale(@PathVariable("productId") Integer productId)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		ProductResponseDto productResponseDto = this.productServices.revokeProductOnDiscountSale(productId);

		// Self Link
		productResponseDto
				.add(linkTo(methodOn(ProductController.class).getProductByIdHandler(productResponseDto.getProductId()))
						.withSelfRel());

		// Category Link
		productResponseDto.add(linkTo(
				methodOn(CategoryController.class).getCategoryHandler(productResponseDto.getCategory().getCategoryId()))
				.withRel("category"));

		// Product by Category Link
		productResponseDto.add(linkTo(methodOn(ProductController.class)
				.searchByCategoryHandler(productResponseDto.getCategory().getCategoryId()))
				.withRel("product by category"));

		// Collection Links
		productResponseDto.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<ProductResponseDto>(productResponseDto, HttpStatus.OK);
	}

	@PutMapping("/admin/{productId}")
	public ResponseEntity<ProductResponseDto> updateProductDetailsHandler(@PathVariable("productId") Integer productId,
			@Valid @RequestBody ProductUpdateRequestDto productRequestDto)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		ProductResponseDto productResponseDto = this.productServices.updateProductDetails(productId, productRequestDto);

		// Self Link
		productResponseDto
				.add(linkTo(methodOn(ProductController.class).getProductByIdHandler(productResponseDto.getProductId()))
						.withSelfRel());

		// Category Link
		productResponseDto.add(linkTo(
				methodOn(CategoryController.class).getCategoryHandler(productResponseDto.getCategory().getCategoryId()))
				.withRel("category"));

		// Product by Category Link
		productResponseDto.add(linkTo(methodOn(ProductController.class)
				.searchByCategoryHandler(productResponseDto.getCategory().getCategoryId()))
				.withRel("product by category"));

		// Collection Links
		productResponseDto.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<ProductResponseDto>(productResponseDto, HttpStatus.OK);

	}

	@PutMapping("/admin/images/{productId}")
	public ResponseEntity<ProductResponseDto> updateProductImage(@RequestParam MultipartFile[] images,
			@PathVariable("productId") Integer productId)
			throws ResourceNotFoundException, IOException, FileTypeNotValidException, ResourceNotAllowedException {

		ProductResponseDto productResponseDto = this.productServices.updateProductImage(images, productId);

		// Self Link
		productResponseDto
				.add(linkTo(methodOn(ProductController.class).getProductByIdHandler(productResponseDto.getProductId()))
						.withSelfRel());
		// Category Link
		productResponseDto.add(linkTo(
				methodOn(CategoryController.class).getCategoryHandler(productResponseDto.getCategory().getCategoryId()))
				.withRel("category"));

		// Product by Category Link
		productResponseDto.add(linkTo(methodOn(ProductController.class)
				.searchByCategoryHandler(productResponseDto.getCategory().getCategoryId()))
				.withRel("product by category"));

		// Collection Links
		productResponseDto.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<ProductResponseDto>(productResponseDto, HttpStatus.OK);
	}

	@PutMapping("/admin/{productId}/available")
	public ResponseEntity<ProductResponseDto> revokeAvailability(@PathVariable("productId") Integer productId)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		ProductResponseDto productResponseDto = this.productServices.revokeAvailability(productId);

		// Self Link
		productResponseDto
				.add(linkTo(methodOn(ProductController.class).getProductByIdHandler(productResponseDto.getProductId()))
						.withSelfRel());

		// Category Link
		productResponseDto.add(linkTo(
				methodOn(CategoryController.class).getCategoryHandler(productResponseDto.getCategory().getCategoryId()))
				.withRel("category"));

		// Product by Category Link
		productResponseDto.add(linkTo(methodOn(ProductController.class)
				.searchByCategoryHandler(productResponseDto.getCategory().getCategoryId()))
				.withRel("product by category"));

		// Collection Links
		productResponseDto.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<ProductResponseDto>(productResponseDto, HttpStatus.OK);
	}

	@PutMapping("/admin/{productId}/buyerschoice")
	public ResponseEntity<ProductResponseDto> revokeBuyersChoice(@PathVariable("productId") Integer productId)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		ProductResponseDto productResponseDto = this.productServices.revokeBuyersChoice(productId);

		// Self Link
		productResponseDto
				.add(linkTo(methodOn(ProductController.class).getProductByIdHandler(productResponseDto.getProductId()))
						.withSelfRel());

		// Category Link
		productResponseDto.add(linkTo(
				methodOn(CategoryController.class).getCategoryHandler(productResponseDto.getCategory().getCategoryId()))
				.withRel("category"));

		// Product by Category Link
		productResponseDto.add(linkTo(methodOn(ProductController.class)
				.searchByCategoryHandler(productResponseDto.getCategory().getCategoryId()))
				.withRel("product by category"));

		// Collection Links
		productResponseDto.add(linkTo(methodOn(ProductController.class).getProductsByPageHandler(null, null))
				.withRel(IanaLinkRelations.COLLECTION));

		return new ResponseEntity<ProductResponseDto>(productResponseDto, HttpStatus.OK);
	}

	@DeleteMapping("/admin/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProductByIdHandler(@PathVariable("productId") Integer productId)
			throws ResourceNotFoundException {

		ApiResponse apiResponse = this.productServices.deleteProductById(productId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.GONE);
	}

	// method to serve images
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveImageHandler(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException, ResourceNotFoundException, FileTypeNotValidException {

		this.productServices.serveProductImage(imageName, response);

	}

	// method to serve images
	@GetMapping(value = "{productId}/images")
	public ResponseEntity<ApiResponse> serveImageHandler(@PathVariable("productId") Integer productId)
			throws IOException, ResourceNotFoundException {

		ApiResponse apiResponse = this.productServices.serveImageByProductId(productId);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	// method to delete images
	@DeleteMapping("/admin/images/{fileName}")
	public ResponseEntity<ApiResponse> deleteImage(@PathVariable("fileName") String fileName) throws IOException {

		ApiResponse apiResponse = this.productServices.deleteProductImage(fileName);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.GONE);
	}

}
