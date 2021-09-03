package be.pxl.paj.budgetplanner.service;

import be.pxl.paj.budgetplanner.dao.PaymentRepository;
import be.pxl.paj.budgetplanner.dto.PaymentCreateResource;
import be.pxl.paj.budgetplanner.entity.Payment;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;


    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


}
