package tn.amira.demospringangular.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.amira.demospringangular.entites.Student;

import java.util.List;

public interface StudentRepository  extends JpaRepository<Student, String> {

    Student findByCode(String code);
    List<Student> findByPaymentId(String paymentId);
}
