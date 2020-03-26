package com.fpt.edu.schedule.common.util;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.edu.schedule.common.enums.Status;
import com.fpt.edu.schedule.model.GooglePojo;
import com.fpt.edu.schedule.model.Role;
import com.fpt.edu.schedule.model.Lecturer;
import com.fpt.edu.schedule.repository.base.RoleRepository;
import com.fpt.edu.schedule.repository.base.LecturerRepository;
import lombok.AllArgsConstructor;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
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
    private LecturerRepository lecturerRepository;
    private RoleRepository roleRepository;

    public String getToken(final String code) throws ClientProtocolException, IOException {
        String link = env.getProperty("google.link.get.token");
        String response = Request.Post(link)
                .bodyForm(Form.form().add("client_id", env.getProperty("google.app.id"))
                        .add("client_secret", env.getProperty("google.app.secret"))
                        .add("redirect_uri", env.getProperty("google.redirect.uri")).add("code", code)
                        .add("grant_type", "authorization_code").build())
                .execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("access_token");
        return node.textValue();
    }

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
        Lecturer lecturer;
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        Lecturer existedUser = lecturerRepository.findById(googlePojo.getId());
        if (existedUser == null) {
            lecturer = new Lecturer();
            lecturer.setFullName(googlePojo.getGiven_name());
            lecturer.setId(googlePojo.getId());
            lecturer.setEmail(googlePojo.getEmail());
            ArrayList<Role> roleList = new ArrayList<>();
            roleList.add(roleRepository.findByRoleName("ROLE_USER"));
            lecturer.setRoleList(roleList);
            lecturer.setStatus(Status.DEACTIVATE);
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            lecturerRepository.save(lecturer);
        } else {
            existedUser.getRoleList().forEach(i -> {
                authorities.add(new SimpleGrantedAuthority(i.getRoleName()));
            });
        }

        UserDetails userDetail = new org.springframework.security.core.userdetails.User(googlePojo.getEmail(),
                "", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        return userDetail;
    }

}