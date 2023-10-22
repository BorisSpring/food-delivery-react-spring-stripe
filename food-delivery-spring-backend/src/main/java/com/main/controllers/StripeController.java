package com.main.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.entity.Order;
import com.main.entity.OrderItem;
import com.main.entity.Product;
import com.main.exceptions.OrderException;
import com.main.exceptions.ProductException;
import com.main.repository.OrderRepository;
import com.main.requests.StripeCartItem;
import com.main.requests.StripePaymentRequest;
import com.main.service.ProductService;
import com.main.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;


@RestController
public class StripeController {
	
    @Value("${stripe.apiKey}")
    private String stripeApiKey;
    
    @Value("${webSocket.secret")
    private String webhookSecret;
    
    private UserService userService;
    private OrderRepository orderRepo;
    private ProductService productService;

	
    


	public StripeController(UserService userService, OrderRepository orderRepo, ProductService productService) {
		this.userService = userService;
		this.orderRepo = orderRepo;
		this.productService = productService;
	}


	@PostMapping("/create-checkout-session")
    public ResponseEntity<?> hostedCheckout(@RequestBody StripePaymentRequest req, @RequestHeader("Authorization") String jwt) throws StripeException {

    	Stripe.apiKey = "sk_test_51Nqs5LHeveq0G9DVZIdJACluk0wDlhZsP9dZfB4JwE4CneoSPACzQnFkLIb2GXIhkE9zFiAs1Co6PfhK3SFyZS8v00Vic2XNKT";
    	
    	SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
    			
    			// urls
    			
    		    .setMode(SessionCreateParams.Mode.PAYMENT)
    		    .setSuccessUrl("http://localhost:5173/checkout-success")
    		    .setCancelUrl("http://localhost:5173/cancel")
    		    
    		    //shipping
    		    
    		    .setShippingAddressCollection(SessionCreateParams.ShippingAddressCollection.builder()
    		        .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.US)
    		        .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.SR)
    		        .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.DE)
    		        .addAllowedCountry(SessionCreateParams.ShippingAddressCollection.AllowedCountry.CA)
    		        .build()
    		    )
    		    .addShippingOption(SessionCreateParams.ShippingOption.builder()
    		        .setShippingRateData(SessionCreateParams.ShippingOption.ShippingRateData.builder()
    		            .setType(SessionCreateParams.ShippingOption.ShippingRateData.Type.FIXED_AMOUNT)
    		            .setFixedAmount(SessionCreateParams.ShippingOption.ShippingRateData.FixedAmount.builder()
    		                .setAmount(200L)
    		                .setCurrency("usd")
    		                .build()
    		            )
    		            .setDisplayName("Delivery time")
    		            .setDeliveryEstimate(SessionCreateParams.ShippingOption.ShippingRateData.DeliveryEstimate.builder()
    		                .setMinimum(SessionCreateParams.ShippingOption.ShippingRateData.DeliveryEstimate.Minimum.builder()
    		                    .setUnit(SessionCreateParams.ShippingOption.ShippingRateData.DeliveryEstimate.Minimum.Unit.HOUR)
    		                    .setValue(1L)
    		                    .build()
    		                )
    		                .setMaximum(SessionCreateParams.ShippingOption.ShippingRateData.DeliveryEstimate.Maximum.builder()
    		                    .setUnit(SessionCreateParams.ShippingOption.ShippingRateData.DeliveryEstimate.Maximum.Unit.HOUR)
    		                    .setValue(2L)
    		                    .build()
    		                )
    		                .build()
    		            )
    		            .build()
    		        )
    		        .build()
    		    );
    	 	
    		//line items

				List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
				for (StripeCartItem ci : req.getCart()) {
				    SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
				        .setQuantity((long) ci.getQuantity())
				        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
				            .setCurrency("usd")
				            .setUnitAmount((long) (ci.getTotalPrice() * 100))
				            .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
				                .setName(ci.getName())
				                .setDescription("Unit Price " + (ci.getTotalPrice() / ci.getQuantity()))
				                .putMetadata("id", ci.getId())
				                .addImage("http://localhost:8080/api/products/image/" + ci.getImage())
				                .build()
				            )
				            .build()
				        )
				        .build();
				    lineItems.add(lineItem);
				}
				
				//json class request
				
				 ObjectMapper objectMapper = new ObjectMapper();
				    String paymentRequestJson;
				    try {
				        paymentRequestJson = objectMapper.writeValueAsString(req);
				    } catch (JsonProcessingException e) {
				        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška prilikom serijalizacije zahteva.");
				    }
				
				    
				    paramsBuilder.setPaymentIntentData(SessionCreateParams.PaymentIntentData.builder() 
				    		.putMetadata("cart", paymentRequestJson)
		                    .putMetadata("userEmail", userService.getEmailFromToken(jwt))
		                    .build());
			               
				    

   	 
				SessionCreateParams params = paramsBuilder.addAllLineItem(lineItems).build();
				Session session = Session.create(params);
				
        return ResponseEntity.status(HttpStatus.OK).body(session.getUrl());
    }
 

	@PostMapping("/stripe-webhook")
    public HttpStatus handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) throws StripeException, JsonMappingException, JsonProcessingException, NumberFormatException, ProductException, OrderException {
      
		try {
            Stripe.apiKey = stripeApiKey;
            com.stripe.model.Event event = Webhook.constructEvent(payload, sigHeader, "whsec_ea263742ea008d1358ed65b35c793ac18aa87d22a480f0ad635a502fd6950778");

            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = null;

            
            if(event.getType().equals("payment_intent.succeeded")) {
            
            	if (dataObjectDeserializer.getObject().isPresent()) {
             
	            stripeObject = dataObjectDeserializer.getObject().get(); 
	              
	             PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
	
	             Map<String, String> metadata = paymentIntent.getMetadata();
	             ObjectMapper objectMapper = new ObjectMapper();
	             StripePaymentRequest request = objectMapper.readValue(metadata.get("cart"), StripePaymentRequest.class);
	             
				             Order order = new Order();
	             
	             
	             List<OrderItem> orderItems = new ArrayList<>();
	             
	             for(StripeCartItem cartItem : request.getCart()) {
	            	 
	            	 Product product = productService.findById(Integer.parseInt(cartItem.getId()));
	            	 
	            	 OrderItem orderItem = new OrderItem();
	            	 orderItem.setOrder(order);
	            	 orderItem.setProduct(product);
	            	 orderItem.setQuantity(cartItem.getQuantity());
	            	 orderItem.setTotalPrice(cartItem.getTotalPrice());
	            	 
	            	 orderItems.add(orderItem);
	             }
	             
	             			 order.setOrderItems(orderItems);
	             			 order.setCreated(LocalDateTime.now());
	             			 order.setUser(userService.findByEmail(metadata.get("userEmail"))); 
	             			 order.setPaid(true);
				             order.setTotalPrice(orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum());
				             order.setTotalQuantity(orderItems.stream().mapToInt(OrderItem::getQuantity).sum());
				             order.setOrderStatus("ACCEPTED");
				             order.setEstimatedDeliveryTime(LocalDateTime.now().plusHours(1));
				             order.setDeliveryAdress(paymentIntent.getShipping().getAddress().getLine1() + " , " + paymentIntent.getShipping().getAddress().getCity() + " , " + paymentIntent.getShipping().getAddress().getCountry() + " , " + paymentIntent.getShipping().getAddress().getPostalCode());
				             order = orderRepo.save(order);
		             
						             if(order == null)
						            	 throw new OrderException("Fail to create order");   
			             
		            }
	            }
	          
            return HttpStatus.OK;
            
        } catch (SignatureVerificationException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

	
}