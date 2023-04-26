package com.example.springboot.filedownload.demo.service;


import com.example.springboot.filedownload.demo.Config.JwtService;
import com.example.springboot.filedownload.demo.Exception.UserNotFoundException;
import com.example.springboot.filedownload.demo.Exception.UsernameandPasswordInCorrect;
import com.example.springboot.filedownload.demo.Repository.UserRepository;
import com.example.springboot.filedownload.demo.controller.AuthentificationRequest;
import com.example.springboot.filedownload.demo.controller.AuthentificationResponse;
import com.example.springboot.filedownload.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    Optional <User> user  = userRepository.findByEmail(email);
    if(user.isPresent()){
        return user.get();

    }
    else{
        throw new UserNotFoundException("user not found");
    }

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
