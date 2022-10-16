package com.example.userservice;

import com.example.userservice.domain.DriverRacer;
import com.example.userservice.domain.Role;
import com.example.userservice.domain.Team;
import com.example.userservice.domain.UserApp;
import com.example.userservice.repo.UserRepo;
import com.example.userservice.service.DriverService;
import com.example.userservice.service.TeamService;
import com.example.userservice.service.UserService;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService, DriverService driverService, UserRepo userRepo, TeamService teamService) {
        return args -> {
            LocalTime local = LocalTime.of(0, 1, 17, 414 * 1000000);
            System.out.println("El tiempo asignado es: " + local);

            UserApp user = userRepo.findByUsername("cesardiaz24");
            if (user == null) {
                System.out.println("usuario nullo");
            } else {
                System.out.println(user.getId() + user.getUsername());
            }
            Date date = new Date();

            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new UserApp(null, "Cesar Diaz", "cesardiaz24", "12345", new ArrayList<>()));
            userService.saveUser(new UserApp(null, "Mario Perez", "mariop12", "12345", new ArrayList<>()));
            userService.saveUser(new UserApp(null, "Carlos perez", "calorss123", "12345", new ArrayList<>()));
            userService.saveUser(new UserApp(null, "Antiono martinez", "toniop123", "12345", new ArrayList<>()));

            userService.addRoleToUser("cesardiaz24", "ROLE_USER");
            userService.addRoleToUser("cesardiaz24", "ROLE_MANAGER");
            userService.addRoleToUser("cesardiaz24", "ROLE_ADMIN");
            userService.addRoleToUser("mariop12", "ROLE_MANAGER");
            userService.addRoleToUser("calorss123", "ROLE_MANAGER");
            userService.addRoleToUser("toniop123", "ROLE_MANAGER");
            userService.addRoleToUser("toniop123", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("toniop123", "ROLE_USER");

            teamService.saveTeam(new Team(null, "Oracle Red Bull Racing", "RB", "1997", "Christian Horner", "Pierre Waché", date, date));
            teamService.saveTeam(new Team(null, "Scuderia Ferrari", "FRR", "1950", "Mattia Binotto", "Enrico Cardile / Enrico Gualtieri", date, date));
            teamService.saveTeam(new Team(null, "Mercedes-AMG Petronas F1 Team", "MEC", "1970", "Toto Wolff", "Mike Elliott", date, date));
            teamService.saveTeam(new Team(null, "McLaren F1 Team", "MC", "1966", "Andreas Seidl", "James Key", date, date));
            teamService.saveTeam(new Team(null, "BWT Alpine F1 Team", "ALP", "1986", " Otmar Szafnauer", "Pat Fry", date, date));

            //driverService.saveNewDriver(new Driver_Racer(null, "Cesar", "Stricline", "27", date, date, user, null), "", Long.valueOf(9));
        };
    }

}
