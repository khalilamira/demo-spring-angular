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
    @PutMapping(path = "/payment/{id}")
    public Payment updatePayementStatus(@RequestParam PaymentStatus status, @PathVariable Long id) {
        Payment payment = paymentRepository.findById(id).orElse(null);
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
    @PostMapping(path = "/payments",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam MultipartFile file, LocalDate date,double amount,
                               PaymentType type,String studentCode) throws IOException {
    Path folderPath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "payments");
    if (!Files.exists(folderPath)) {
        Files.createDirectories(folderPath);
    }
    Path filePath = Paths.get(folderPath.toString(), file.getOriginalFilename());
    Files.copy(file.getInputStream(), filePath);
    Student student = studentRepository.findByCode(studentCode);
    Payment payment = Payment.builder()
            .date(date).type(type).student(student)
            .amount(amount)
            .file(filePath.toUri().toString())
            .status(PaymentStatus.CREATED)
            .build();
    return paymentRepository.save(payment);
    }
    @GetMapping(path = "/paymentFile/{paymentId}",produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }
}
