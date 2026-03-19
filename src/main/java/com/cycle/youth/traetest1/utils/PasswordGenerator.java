package com.cycle.youth.traetest1.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "admin123";
        String hashedPassword = encoder.encode(password);
        System.out.println("Password: " + password);
        System.out.println("Hashed: " + hashedPassword);
        
        // Test the hash
        boolean matches = encoder.matches(password, hashedPassword);
        System.out.println("Matches: " + matches);
    }
}