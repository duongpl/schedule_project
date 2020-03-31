package com.fpt.edu.schedule.controller;

import com.fpt.edu.schedule.common.util.GoogleUtils;
import com.fpt.edu.schedule.model.GooglePojo;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.service.base.LecturerService;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/google")
public class GoogleController {

    private JavaMailSender javaMailSender;
    private GoogleUtils googleUtils;
    LecturerService lecturerService;


    @GetMapping("login")
    public Lecturer getAccessToken(@RequestParam("accessToken") String accessToken, HttpServletRequest request) throws ClientProtocolException, IOException {

        GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
//        if (!googlePojo.getHd().equalsIgnoreCase("fpt.edu.vn")) {
//            throw new IllegalArgumentException("not have permission");
//        }
        UserDetails userDetail = googleUtils.buildUser(googlePojo);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
                userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return lecturerService.getLecturerGoogleId(googlePojo.getId());
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
