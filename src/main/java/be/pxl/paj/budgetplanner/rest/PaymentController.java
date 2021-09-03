package be.pxl.paj.budgetplanner.rest;

import be.pxl.paj.budgetplanner.dto.PaymentCreateResource;
import be.pxl.paj.budgetplanner.service.PaymentService;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    
    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    
    @PostMapping
    public RequestEntity<String> addPayment(@RequestBody PaymentCreateResource paymentCreateResource) {
        return paymentService.addPayment(paymentCreateResource);
    }
    
}
