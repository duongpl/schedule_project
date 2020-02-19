package com.fpt.edu.schedule.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fpt.edu.schedule.model.GooglePojo;
import com.fpt.edu.schedule.common.util.GoogleUtils;
import lombok.AllArgsConstructor;
import org.apache.http.client.ClientProtocolException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@AllArgsConstructor
@Controller
@SessionAttributes("name")
public class GoogleController {

    private JavaMailSender javaMailSender;
    private GoogleUtils googleUtils;

    @RequestMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    @RequestMapping("/login-google")
    public String loginGoogle(HttpServletRequest request, ModelMap modelMap) throws ClientProtocolException, IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            return "redirect:/login?google=error";
        }
        String accessToken = googleUtils.getToken(code);
        GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
//        if (!googlePojo.getHd().equalsIgnoreCase("fpt.edu.vn")) {
//            throw new IllegalArgumentException("not have permission");
//        }
        UserDetails userDetail = googleUtils.buildUser(googlePojo);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
                userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        modelMap.addAttribute("name", googlePojo.getEmail());
        return "redirect:/user";
    }

    @RequestMapping("/user")
    public String user() {

        return "user";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "403";
    }


    @RequestMapping("/logout1")
    public String logout(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
        SimpleMailMessage msg = new SimpleMailMessage();
        try {

            msg.setTo(modelMap.getAttribute("name").toString());
            msg.setSubject("Logout");
            msg.setText("Ban vua logout");
            javaMailSender.send(msg);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("done");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }


}
