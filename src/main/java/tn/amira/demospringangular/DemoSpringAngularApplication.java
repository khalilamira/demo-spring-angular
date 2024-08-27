package tn.amira.demospringangular;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tn.amira.demospringangular.entites.Payment;
import tn.amira.demospringangular.entites.PaymentType;
import tn.amira.demospringangular.entites.Student;
import tn.amira.demospringangular.repository.PaymentRepository;
import tn.amira.demospringangular.repository.StudentRepository;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class DemoSpringAngularApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringAngularApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                            PaymentRepository paymentRepository){
        return args -> {
           studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                           .firstName("Khalil").lastName("AMIRA").code("1").code("KMJ")
                   .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Jad").lastName("AMIRA").code("2").code("K")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Maryem").lastName("AMIRA").code("2").code("M")
                    .build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Med").lastName("AMIRA").code("1").code("J")
                    .build());

            PaymentType[] paymentTypes = PaymentType.values();
            Random random = new Random();
            studentRepository.findAll().forEach(st -> {
                for (int i = 0; i < 10 ; i++) {
                    Payment payment = Payment.builder()
                            .amount(Math.random())
                            .type(paymentTypes[random.nextInt(paymentTypes.length)])
                            .date(LocalDate.now())
                            .student(st)
                            .build();
                    paymentRepository.save(payment);
                }
            });
        };
    }

}
