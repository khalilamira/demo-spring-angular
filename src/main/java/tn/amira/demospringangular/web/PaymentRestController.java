package tn.amira.demospringangular.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.amira.demospringangular.entites.Payment;
import tn.amira.demospringangular.entites.PaymentStatus;
import tn.amira.demospringangular.entites.PaymentType;
import tn.amira.demospringangular.entites.Student;
import tn.amira.demospringangular.repository.PaymentRepository;
import tn.amira.demospringangular.repository.StudentRepository;
import tn.amira.demospringangular.services.PaymentService;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@RestController
public class PaymentRestController {
    private PaymentRepository paymentRepository;
    private StudentRepository studentRepository;
    private PaymentService paymentService;

    public PaymentRestController(PaymentRepository paymentRepository, StudentRepository studentRepository,PaymentService paymentService) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
        this.paymentService = paymentService;
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
    @PutMapping(path = "/payment/{id}")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status, @PathVariable Long id) {
        return this.paymentService.updatePaymentStatus(status, id);
    }
    @PostMapping(path = "/payments",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam MultipartFile file, LocalDate date,double amount,
                               PaymentType type,String studentCode) throws IOException {
        return this.paymentService.savePayment(file,date,amount,type,studentCode);
    }
    @GetMapping(path = "/paymentFile/{paymentId}",produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {Payment payment = paymentRepository.findById(paymentId).orElse(null);
        return paymentService.getPaymentFile(paymentId);
    }
}
