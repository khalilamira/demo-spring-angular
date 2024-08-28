package tn.amira.demospringangular.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tn.amira.demospringangular.entites.Payment;
import tn.amira.demospringangular.entites.PaymentStatus;
import tn.amira.demospringangular.entites.PaymentType;
import tn.amira.demospringangular.entites.Student;
import tn.amira.demospringangular.repository.PaymentRepository;
import tn.amira.demospringangular.repository.StudentRepository;

import java.nio.file.Path;
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
    @GetMapping(path = "/payments/{code}/payments")
    public List<Payment> paymentsByStudents(@PathVariable String code) {
        return paymentRepository.findByStudentCode(code);
    }
    @GetMapping(path = "/payments/byStatus")
    public List<Payment> paymentsByStatus(@PathVariable PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }
    @GetMapping(path = "/payments/byType")
    public List<Payment> paymentsByType(@PathVariable PaymentType type) {
        return paymentRepository.findByType(type);
    }
    @GetMapping(path = "/payments/{id}")
    public Payment findPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id).orElse(null);
    }
    @GetMapping(path="/students")
    public List<Student>  getListOfStudents() {
        return studentRepository.findAll();
    }
    @GetMapping(path = "/students/{code}")
    public Student getStudentByCode(@PathVariable String code ) {
        return studentRepository.findByCode(code);
    }
}
