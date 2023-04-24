package com.example.springboot.filedownload.demo.controller;



//import com.example.springboot.filedownload.demo.Repository.UserRepository;
import com.example.springboot.filedownload.demo.Exception.ApiRequestException;
import com.example.springboot.filedownload.demo.auth.AuthenticationRequest;
import com.example.springboot.filedownload.demo.model.User;
import com.example.springboot.filedownload.demo.service.UserService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
public class UserController {

@Autowired
private UserService userService;

  //  @Autowired
 //   private UserRepository userRepository;
    public List<User> generateusers (int count){
      List<User> users = new ArrayList<>();
        for (int i=0;i<count;i++) {
            User user = new User();
            user.setUsername(userService.genarateStringRandom());
            user.setJobposition(userService.genarateStringRandom());
            user.setLastname(userService.genarateStringRandom());
            user.setCity(userService.genarateStringRandom());
            user.setEmail(userService.genarateStringRandom());
            user.setCountry(userService.genarateStringRandom());
            user.setUsername(userService.genarateStringRandom());
            user.setFirstname(userService.genarateStringRandom());
            user.setBirthDate(userService.genarateStringRandom());
            user.setAvatar(userService.genarateStringRandom());
            user.setMobile(userService.genarateStringRandom());
            user.setRole(userService.userrole());
            user.setCompany(userService.genarateStringRandom());
            user.setPassword(userService.genarateStringRandom());
            users.add(user);

        }
        return users;
    }


         @GetMapping("/api/users/generate" )
        public ResponseEntity<byte[]> downloadfile(@RequestParam (value = "count") int count){

            String jsonUsers = userService.export(generateusers(count));
            byte[] usersJsonBytes = jsonUsers.getBytes();

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=users.json")
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(usersJsonBytes.length)
                    .body(usersJsonBytes);

        }
    @PostMapping ("/api/users/batch")
    public ResponseEntity<Integer> uploaduser(@RequestParam ("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(userService.saveUsers(file));
    }
    @PostMapping ("/api/auth")
    public ResponseEntity<AuthentificationResponse> auth(@RequestBody AuthentificationRequest request){
        return ResponseEntity.ok().body(userService.auth(request));
    }
    @GetMapping("/api/users/me")
    public ResponseEntity<User> getUser (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok().body(user);
    }
@GetMapping ("/api/users/{username}")
    public ResponseEntity<User> getbyemail (@PathVariable String username ){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();
throw new ApiRequestException("not found");
//    return ResponseEntity.ok().body(userService.findByEmail(username));
}


}











