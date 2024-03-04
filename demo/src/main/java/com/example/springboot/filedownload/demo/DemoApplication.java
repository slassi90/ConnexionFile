package com.example.springboot.filedownload.demo;

import com.example.springboot.filedownload.demo.Repository.DepartementRepository;
import com.example.springboot.filedownload.demo.Repository.EmployeeRepository;
import com.example.springboot.filedownload.demo.model.Departement;
import com.example.springboot.filedownload.demo.model.Employee;
import com.example.springboot.filedownload.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DemoApplication implements CommandLineRunner {
@Autowired
private DepartementRepository departementRepository;
@Autowired
private EmployeeRepository employeeRepository;
@Autowired
private UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Departement dep1 = new Departement(1L,"informatique",null);
		Departement dep2 = new Departement(2L,"RH",null);
		Employee emp1 = new Employee(1L,"Achraf","Slassi",12000,dep1);
		Employee emp2 = new Employee(2L,"Amine","Elaouni",30000,dep1);
		Employee emp3 = new Employee(3L,"Marouane","Slassi",11000,dep2);
		Employee emp4 = new Employee(4L,"hamza","Elaouni",27000,dep2);
		departementRepository.saveAll(Arrays.asList(dep1,dep2));
		employeeRepository.saveAll(Arrays.asList(emp1,emp2,emp3,emp4));
		List<Employee> employes = employeeRepository.findAll();
				employes.stream()
						.map(Employee::getFirst_name)
						.forEach(System.out::println);

						/*.filter(employee -> "Achraf".equals(employee))
						.collect(Collectors.toList());
				employes.forEach(System.out::println);*/
userService.lastNameBymaxSalary();
userService.findfirstName();

	}
}
