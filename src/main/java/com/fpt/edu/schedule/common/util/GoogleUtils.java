package com.fpt.edu.schedule.common.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.edu.schedule.common.constant.MessageResponse;
import com.fpt.edu.schedule.common.enums.StatusLecturer;
import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.GooglePojo;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.repository.base.LecturerRepository;
import lombok.AllArgsConstructor;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class GoogleUtils {
    private Environment env;
    private LecturerRepository lecturerRepo;


    public GooglePojo getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = env.getProperty("google.link.get.user_info") + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        GooglePojo googlePojo = mapper.readValue(response, GooglePojo.class);
        return googlePojo;
    }

    public UserDetails buildUser(GooglePojo googlePojo) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        Lecturer existedUser = lecturerRepo.findByEmailContainingIgnoreCase(googlePojo.getEmail());
        if (existedUser == null || existedUser.getStatus().equals(StatusLecturer.DEACTIVATE)) {
            throw new InvalidRequestException(MessageResponse.msgLogin);
        }
        if (existedUser.isLogin()) {
            authorities.add(new SimpleGrantedAuthority(existedUser.getRole().getRoleName()));
        } else {
            existedUser.setStatus(StatusLecturer.ACTIVATE);
            existedUser.setGoogleId(googlePojo.getId());
            existedUser.setFullName(googlePojo.getGiven_name());
//            existedUser.setShortName(existedUser.getEmail().substring(0, existedUser.getEmail().indexOf('@')));
            existedUser.setPicture(googlePojo.getPicture());
            existedUser.setLogin(true);
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            lecturerRepo.save(existedUser);
        }


        UserDetails userDetail = new org.springframework.security.core.userdetails.User(googlePojo.getEmail(),
                "", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        return userDetail;
    }

}
