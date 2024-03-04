package com.example.springboot.filedownload.demo.Repository;

import com.example.springboot.filedownload.demo.model.Departement;
import com.example.springboot.filedownload.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {

}
