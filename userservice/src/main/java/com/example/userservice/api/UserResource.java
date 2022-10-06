/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.userservice.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.userservice.domain.Role;
import com.example.userservice.domain.UserApp;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author cesaralejodiaz
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    
    @GetMapping("/home")
    public void Session(HttpServletRequest request, HttpServletResponse response) {
        
        Cookie cookie= new Cookie("session", "mario");
        cookie.setHttpOnly(true);
        cookie.setPath("/home");
        response.addCookie(cookie);
        
        /*
        HttpSession session = request.getSession();
        session.setAttribute("usuario", "ricardogeek");
        session.*/
    }

    @GetMapping("/users")
    public ResponseEntity<Collection<UserApp>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/getuser")
    @ResponseBody
    public ResponseEntity<UserApp> getUserSpecific(HttpServletResponse response, @RequestParam(name = "username") String username) throws IOException {
        try {
            UserApp user = userService.getUser(username);
            if (user == null) {
                Map<String, String> message = new HashMap<>();
                message.put("Driver not found: ", username);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            } else {
                return ResponseEntity.ok().body(userService.getUser(username));
            }
        } catch (Exception e) {
            Map<String, String> err = new HashMap<>();
            err.put("Exception: ", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), err);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/save")
    public ResponseEntity<UserApp> saveUser(HttpServletResponse response, @RequestBody UserApp user) throws IOException {

        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
            boolean flag = userService.existUsername(user.getUsername());
            System.out.println(flag);
            if (flag) {
                Map<String, String> message = new HashMap<>();
                message.put("Username exist, use other username: ", user.getUsername());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), message);
            } else {

                UserApp userAux = userService.saveUser(user);
                if (userAux != null) {
                    return ResponseEntity.created(uri).body(userAux);
                } else {
                    Map<String, String> message = new HashMap<>();
                    message.put("Error to save the user: ", user.getUsername());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), message);
                }
            }
        } catch (Exception e) {
            Map<String, String> err = new HashMap<>();
            err.put("Exception: ", e.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), err);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rol/save")
    public ResponseEntity<Role> saveRol(@RequestBody Role rol) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/rol/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(rol));
    }

    @PostMapping("/rol/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refresToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {

                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                UserApp user = userService.getUser(username);

                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is mising");
        }
    }
}

@Data
class RoleToUserForm {

    private String username;
    private String roleName;
}
