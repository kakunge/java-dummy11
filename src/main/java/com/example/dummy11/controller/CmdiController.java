package com.example.dummy11.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CmdiController {

    @RequestMapping("/cmdi")
    public String showCommandForm() {
        return "cmdi";
    }

    @PostMapping("/cmdi")
    public String executeCommand(String command, Model model) {
        StringBuilder output = new StringBuilder();
        Process process;
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                process = Runtime.getRuntime().exec("cmd.exe /c " + command);
            } else {
                process = Runtime.getRuntime().exec(command);
            }

            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        model.addAttribute("output", output.toString());
        return "cmdi";
    }
}
