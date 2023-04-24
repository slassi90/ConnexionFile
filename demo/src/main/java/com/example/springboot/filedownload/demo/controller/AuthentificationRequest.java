package com.example.springboot.filedownload.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthentificationRequest {
    private String username;
    private String password;
    private String email;
}
