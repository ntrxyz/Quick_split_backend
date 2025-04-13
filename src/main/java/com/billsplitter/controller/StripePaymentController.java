package com.billsplitter.controller;

import com.billsplitter.service.StripePaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
public class StripePaymentController {

    @Autowired
    private StripePaymentService stripePaymentService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<?> createCheckoutSession(@RequestParam String amount) {
        try {
            Session session = stripePaymentService.createCheckoutSession(amount);

            // Return JSON with sessionId
            return ResponseEntity.ok().body(new java.util.HashMap<String, String>() {{
                put("id", session.getId());
            }});
        } catch (StripeException e) {
            return ResponseEntity.status(500).body("Stripe error: " + e.getMessage());
        }
    }
}


