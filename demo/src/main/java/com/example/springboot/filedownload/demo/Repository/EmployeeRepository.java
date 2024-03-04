package com.example.springboot.filedownload.demo.Repository;

import com.example.springboot.filedownload.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query(value = "SELECT * FROM Employee e,Departement d Where e.id_departement=d.id_departement and e.id_departement=1?", nativeQuery = true)
    public Employee Employeewithdepartementid(Long id);
}
