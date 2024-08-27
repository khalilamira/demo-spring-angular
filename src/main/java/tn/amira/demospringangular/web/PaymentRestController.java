package tn.amira.demospringangular.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tn.amira.demospringangular.entites.Payment;
import tn.amira.demospringangular.repository.PaymentRepository;
import tn.amira.demospringangular.repository.StudentRepository;

import java.util.List;

@RestController
public class PaymentRestController {
    private PaymentRepository paymentRepository;
    private StudentRepository studentRepository;

    public PaymentRestController(PaymentRepository paymentRepository, StudentRepository studentRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping(path = "/payments")
    public List<Payment> allPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping(path = "/payments/{id}")
    public Payment findPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id).orElse(null);
    }
}
