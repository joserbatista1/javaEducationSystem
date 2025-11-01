package com.example.web.service;

import com.example.web.ConnectionManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try (Connection conn = ConnectionManager.getConnection("sql8805550", "sql8805550", "w7dmg5uMCA")) {
            // Note: This query should be case-sensitive based on your DB collation
            String sql = "SELECT contraseña,role FROM Users WHERE username = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    if (resultSet.next()) {
                        String hashedPassword = resultSet.getString("contraseña");
                        String role =resultSet.getString("role");
                        List<GrantedAuthority> authorities = Collections.singletonList(
                                new SimpleGrantedAuthority(role)); //

                        // Spring Security expects the password to be already HASHED
                        // Assign authorities (roles) if needed. Using empty list for simplicity.
                        return new User(
                                username,
                                hashedPassword,
                                authorities
                        );
                    } else {
                        throw new UsernameNotFoundException("User not found with username: " + username);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            throw new RuntimeException("Error loading user from database", e);
        }
    }
}
