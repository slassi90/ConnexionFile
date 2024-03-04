package com.example.springboot.filedownload.demo.service;


import com.example.springboot.filedownload.demo.Config.JwtService;
import com.example.springboot.filedownload.demo.Exception.EmployeeNotFoundException;
import com.example.springboot.filedownload.demo.Exception.UserNotFoundException;
import com.example.springboot.filedownload.demo.Exception.UsernameandPasswordInCorrect;
import com.example.springboot.filedownload.demo.Repository.DepartementRepository;
import com.example.springboot.filedownload.demo.Repository.EmployeeRepository;
import com.example.springboot.filedownload.demo.Repository.UserRepository;
import com.example.springboot.filedownload.demo.controller.AuthentificationRequest;
import com.example.springboot.filedownload.demo.controller.AuthentificationResponse;
import com.example.springboot.filedownload.demo.model.Employee;
import com.example.springboot.filedownload.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartementRepository departementRepository;
    public String export(List<User> users) {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                //.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .disableHtmlEscaping()
                .create();
        return gson.toJson(users).replace("\\\\", "\\");
    }
    public String userrole(){

        String[] s = {"user", "admin"};

        Random ran = new Random();
        String s_ran = s[ran.nextInt(s.length)];
        return s_ran;
        }
    public List<User> generateusers (int count){
        List<User> users = new ArrayList<>();
        for (int i=0;i<count;i++) {
            User user = new User();
            user.setUsername(genarateStringRandom());
            user.setJobposition(genarateStringRandom());
            user.setLastname(genarateStringRandom());
            user.setCity(genarateStringRandom());
            user.setEmail(genarateStringRandom());
            user.setCountry(genarateStringRandom());
            user.setUsername(genarateStringRandom());
            user.setFirstname(genarateStringRandom());
            user.setBirthDate(genarateStringRandom());
            user.setAvatar(genarateStringRandom());
            user.setMobile(genarateStringRandom());
            user.setRole(userrole());
            user.setCompany(genarateStringRandom());
            user.setPassword(genarateStringRandom());
            users.add(user);

        }
        return users;
    }

    public int saveUsers(MultipartFile file) throws IOException{
       // User[] users =objectMapper.readValue(file.getBytes(),User[].class);
        User[] users = objectMapper.readValue(file.getBytes(),User[].class);
           List<User> usersList = List.of(users);
           usersList.stream().forEach(u -> u.setPassword(passwordEncoder.encode(u.getPassword())));
           userRepository.saveAll(usersList);
            return  usersList.size();
    }

    public AuthentificationResponse auth(AuthentificationRequest request){
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        }catch (BadCredentialsException ex){
            throw new UsernameandPasswordInCorrect("Username and Passowrd Incorrect");
        }
        String token = jwtService.generateToken((User) authenticate.getPrincipal());
        return AuthentificationResponse.builder().accessToken(token).build();
    }
    public User findByEmail(String email){
      //  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail()
        //        ,request.getPassword()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userss = (User) authentication.getPrincipal();
        Optional <User> user  = userRepository.findByEmail(email);
    if(user.isPresent()){
        return user.get();

    }
    else{
        throw new UserNotFoundException("user not found");
    }

    }
    public Employee Employeewithdepartementid (Long id){
       Optional<Employee> employee1 = Optional.ofNullable(employeeRepository.Employeewithdepartementid(id));
        if (employee1.isPresent()){
            return employee1.get();
        }
    else {
        throw  new EmployeeNotFoundException("employee not found ");
        }
    }

    public Optional <Employee> lastNameBymaxSalary() {
       List<Employee> employees = employeeRepository.findAll();

      Optional <Employee> emp =employees.stream()
               .max(Comparator.comparing(Employee::getSalary));
                emp.ifPresent(employee -> System.out.println(employee.getLast_name()) );

return emp;



    }

   public List<Employee> findfirstName(){
List <Employee>employes = employeeRepository.findAll();
        employes.stream()
                .map(Employee::getFirst_name)
                .forEach(System.out::println);

return employes;
    }
        public String genarateStringRandom(){
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }
            String generatedString = buffer.toString();
            return  generatedString;
        }




}
