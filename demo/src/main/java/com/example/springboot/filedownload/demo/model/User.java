    package com.example.springboot.filedownload.demo.model;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;

    import java.io.Serializable;
    import java.util.Collection;
    import java.util.List;

    @Table(name = "users_gen")
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    @Entity
    public class User implements UserDetails {
         @Id
         @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;
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
        private  String password;
        private String role;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority(role.toLowerCase()));
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername(){
            return email;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
