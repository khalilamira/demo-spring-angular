package tn.amira.demospringangular.services;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

@Service
@Transactional
public class PaymentService {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;

    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment savePayment(MultipartFile file, LocalDate date, double amount,
                               PaymentType type, String studentCode) throws IOException {
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
    public Payment updatePaymentStatus(PaymentStatus status,Long id) {
        Payment payment = paymentRepository.findById(id).orElse(null);
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }
    public byte[] getPaymentFile(Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }

}
