/**
 * 
 */
package com.masai.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.masai.exceptions.ResourceNotAllowedException;
import com.masai.exceptions.ResourceNotFoundException;
import com.masai.model.CartProductDetails;
import com.masai.modelResponseDto.CartResponseDto;
import com.masai.modelResponseDto.OrderResponseDto;
import com.masai.payloads.ApiResponse;
import com.masai.payloads.AppConstants;
import com.masai.services.CartServices;

/**
 * @author tejas
 *
 */
@RestController
@RequestMapping("/bestbuy/carts")
public class CartController {

	@Autowired
	private CartServices cartServices;

	@PostMapping("/customers/{contact}/products/{productId}")
	public ResponseEntity<ApiResponse> addProducttoCartHandler(@PathVariable("contact") String contact,
			@PathVariable("productId") Integer productId,
			@RequestParam(defaultValue = AppConstants.QUANTITY, required = false) Integer productQuantity)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		ApiResponse apiResponse = this.cartServices.addProducttoCart(contact, productId, productQuantity);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@PutMapping("/customers/{contact}/products/{productId}")
	public ResponseEntity<ApiResponse> updateCartProductQuantityHandler(@PathVariable("contact") String contact,
			@PathVariable("productId") Integer productId,
			@RequestParam(defaultValue = AppConstants.QUANTITY, required = false) Integer productQuantity)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		ApiResponse apiResponse = this.cartServices.updateCartProductQuantity(contact, productId, productQuantity);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	@PutMapping("/customers/{contact}")
	public ResponseEntity<CartResponseDto> emptyCartHandler(@PathVariable("contact") String contact)
			throws ResourceNotFoundException {

		CartResponseDto cartResponseDto = this.cartServices.emptyCart(contact);

		return new ResponseEntity<CartResponseDto>(cartResponseDto, HttpStatus.GONE);
	}

	@DeleteMapping("/customers/{contact}/products/{productId}")
	public ResponseEntity<CartResponseDto> deleteProductfromCartHandler(@PathVariable("contact") String contact,
			@PathVariable("productId") Integer productId) throws ResourceNotFoundException {

		CartResponseDto cartResponseDto = this.cartServices.deleteProductfromCart(contact, productId);

		return new ResponseEntity<CartResponseDto>(cartResponseDto, HttpStatus.GONE);
	}

	@GetMapping("customers/{contact}")
	public ResponseEntity<CartResponseDto> getCartHandler(@PathVariable("contact") String contact)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		CartResponseDto cartResponseDto = this.cartServices.getCart(contact);

		List<CartProductDetails> listOfProducts = cartResponseDto.getListOfProducts();

		for (CartProductDetails c : listOfProducts) {

			// Self Link
			c.add(linkTo(methodOn(ProductController.class).getProductByIdHandler(c.getProductId())).withSelfRel());

		}

		// Buy Cart Link
		cartResponseDto.add(linkTo(methodOn(CartController.class).buyCart(contact, null)).withRel("buy cart"));

		return new ResponseEntity<CartResponseDto>(cartResponseDto, HttpStatus.OK);

	}

	@PostMapping("customers/{contact}/payments/{paymentId}")
	public ResponseEntity<OrderResponseDto> buyCart(@PathVariable("contact") String contact,
			@PathVariable("paymentId") Integer paymentId)
			throws ResourceNotFoundException, ResourceNotAllowedException {

		OrderResponseDto orderResponseDto = this.cartServices.buyCart(contact, paymentId);

		// Self Link
		orderResponseDto
				.add(linkTo(methodOn(OrderController.class).getOrderById(orderResponseDto.getOrderId())).withSelfRel());

		// Customer Link
		orderResponseDto.add(linkTo(
				methodOn(CustomerController.class).getCustomerHandler(orderResponseDto.getCustomer().getContact()))
				.withRel("customer"));

		// Payment Link
		orderResponseDto.add(linkTo(
				methodOn(PaymentController.class).getPaymentMethodHandler(orderResponseDto.getPayment().getPaymentId()))
				.withRel("payment"));

		return new ResponseEntity<OrderResponseDto>(orderResponseDto, HttpStatus.OK);
	}

}
