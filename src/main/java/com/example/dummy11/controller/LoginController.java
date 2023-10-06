package com.example.dummy11.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.*;

@Controller
public class LoginController {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/dummy";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "password";

    @RequestMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String query = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return ResponseEntity.ok("Login success");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login fail");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}


class LoginRequest {

    private String username;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
