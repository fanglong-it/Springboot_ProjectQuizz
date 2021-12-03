/*
 *
 *
 * Project ProductManager
 * Copyright (C) $year by Fanglong-it. All Rights Reserved.
 * For more information : Fang.longpc@gmail.com
 * Example project exist at : https://github.com/fanglong-it/
 * 11/11/21, 2:15 PM
 *
 *
 */

package fa.training.mockup.controller;

import fa.training.mockup.entity.RoleEntity;
import fa.training.mockup.entity.UserEntity;
import fa.training.mockup.repository.RoleRepository;
import fa.training.mockup.repository.UserRepository;
import fa.training.mockup.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserRepository repo;

    @Autowired
    private RoleService roleService;

    @GetMapping("/login")
    public String loginpage() {
        return "login";
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("USER", new UserEntity());
        return "signup_form";
    }

    @PostMapping(value = "/process_register")
    public String processRegistration(UserEntity user, @Param("rePassword") String rePassword) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String rawPassword = user.getPassword();
        if (rawPassword.equals(rePassword)) {
            String newPassword = bCryptPasswordEncoder.encode(rawPassword);
            long index = 2;
            RoleEntity roleEntity = roleService.getRoleById(index);
            user.getRole().add(roleEntity);
            user.setPassword(newPassword);
            repo.save(user);
            return "redirect:/user/login?success";
        } else {

            return "redirect:/user/register?passwordIncorrect";
        }


    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";

    }

    @GetMapping("/access-denied")
    public String accessDeniedPage(){
        return "access-denied";
    }
}
