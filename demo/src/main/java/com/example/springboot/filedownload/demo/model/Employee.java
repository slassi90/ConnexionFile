package com.example.springboot.filedownload.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue
    private Long id_employee;
    private String first_name;
    private String last_name;
    private double salary;
    @ManyToOne
    @JoinColumn(name = "id_departement", referencedColumnName = "id_departement")
    private Departement departement;
}
