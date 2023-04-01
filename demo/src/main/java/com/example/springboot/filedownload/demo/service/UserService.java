package com.example.springboot.filedownload.demo.service;


import ch.qos.logback.core.testUtil.RandomUtil;
import com.example.springboot.filedownload.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserService {
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

    public User getJson(MultipartFile file){

        User userJson = new User();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            userJson = objectMapper.readValue(file.getBytes(),User.class);
            List<User> user = new ArrayList<>();
            user.add(userJson);

        }catch (IOException err){
            System.out.println(err.toString());

        }

        return  userJson;
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
