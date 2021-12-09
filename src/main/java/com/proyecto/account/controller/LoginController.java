package com.proyecto.account.controller;

import com.proyecto.account.dto.UserRegistrationDto;
import com.proyecto.account.model.User;
import com.proyecto.account.service.EmailService;
import com.proyecto.account.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    private IUserService userService;
    @Autowired
    EmailService emailService;
    @Autowired
    private JavaMailSender mailSender;
    @GetMapping("/login")
    public String login(Model model) {

        return "admin/auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        model.addAttribute("userRegistrationDto", userRegistrationDto);

        return "admin/auth/register";
    }
    //Metodo de resgistro
    @PostMapping("/register")
    public String registerUserAccount(@Valid @ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto, BindingResult result, Model model) {
        model.addAttribute("userRegistrationDto", userRegistrationDto);

        User userExists = userService.findByUsername(userRegistrationDto.getUserName());
        //Si ya esta rn base de datos
        if (userExists != null) {
            return "redirect:/register?username";
        }
        if(result.hasErrors()){
            return "admin/auth/register";
        }
        //envia email
        emailService.sendSimpleEmail(userRegistrationDto.getUserName(), "Welcome", "Esta es una prueba");
        //Alamacena en la DB
        userService.save(userRegistrationDto);

        return "redirect:/register?success";
    }
}
