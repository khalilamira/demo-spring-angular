package tn.amira.demospringangular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.amira.demospringangular.entites.Payment;
import tn.amira.demospringangular.entites.PaymentStatus;
import tn.amira.demospringangular.entites.PaymentType;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStudentCode(String code);
    List<Payment> findByStatus(PaymentStatus paymentStatus);
    List<Payment> findByType(PaymentType paymentType);

}
