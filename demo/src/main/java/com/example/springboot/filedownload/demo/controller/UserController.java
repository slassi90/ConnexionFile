package com.example.springboot.filedownload.demo.controller;



//import com.example.springboot.filedownload.demo.Repository.UserRepository;
import com.example.springboot.filedownload.demo.Exception.MissingParameterException;
import com.example.springboot.filedownload.demo.model.Employee;
import com.example.springboot.filedownload.demo.model.User;
import com.example.springboot.filedownload.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;


    //  @Autowired
    //   private UserRepository userRepository;


    @GetMapping("/api/users/generate")
    public ResponseEntity<byte[]> downloadfile(@RequestParam(value = "count") Integer count) {
        if (count != null) {
            String jsonUsers = userService.export(userService.generateusers(count));
            byte[] usersJsonBytes = jsonUsers.getBytes();

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=users.json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(usersJsonBytes.length)
                    .body(usersJsonBytes);
        } else {
            throw new MissingParameterException(" count parameter is required");

        }

    }

    @PostMapping("/api/users/batch")
    public ResponseEntity<Integer> uploaduser(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(userService.saveUsers(file));
    }

    @PostMapping("/api/auth")
    public ResponseEntity<AuthentificationResponse> auth(@RequestBody AuthentificationRequest request) {

        return ResponseEntity.ok().body(userService.auth(request));
    }

    @GetMapping("/api/users/me")
    public ResponseEntity<User> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok().body(user);
    }
    @GetMapping("/api/users/lastname")
    public ResponseEntity<Employee> last_NameBymaxSalary() {
        return ResponseEntity.ok().body(userService.lastNameBymaxSalary().get());
    }
    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/api/users/compte/{username}")
    public ResponseEntity<User> getbyemail(@PathVariable String username) {

//throw new ApiRequestException("not found");
        return ResponseEntity.ok().body(userService.findByEmail(username));
    }
    @GetMapping("/api/users/emp/{id}")
      public ResponseEntity<Employee> Employeewithdepartementid (@PathVariable Long id){
        return ResponseEntity.ok().body(userService.Employeewithdepartementid(id));
    }
@GetMapping("/api/users/firstname")
    public ResponseEntity<List<Employee>> displayfirstname(){
       return ResponseEntity.ok().body(userService.findfirstName());
}


}