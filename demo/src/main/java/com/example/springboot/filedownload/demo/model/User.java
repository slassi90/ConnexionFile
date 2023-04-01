    package com.example.springboot.filedownload.demo.model;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public class User {
        private String firstname;
        private String lastname;
        private String birthDate;

        private String city;
        private String country;
        private String avatar;
        private String company;
        private String jobposition;
        private String mobile;
        private String username;
        private String email;
        private String role;
    }
