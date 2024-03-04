package com.example.springboot.filedownload.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Departement {
    @Id
    @GeneratedValue
    private Long id_departement;
    private String nom_departement;
@OneToMany
    private List<Employee> employees;

}
