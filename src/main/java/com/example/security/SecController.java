package com.example.security;

import com.example.security.Model.Users;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SecController {
    private HttpSession session;

    private List<Users> DB = new ArrayList<>();

    //private final Logger log = LoggerFactory.getLogger(SecController.class);
    private final Logger log = LoggerFactory.getLogger(SecController.class);

    @GetMapping("/saveUser")
    public void saveUser(String username, String password, HttpServletResponse response) throws IOException {
        try {
            Users user = new Users(username, crypt(password));
            DB.add(user);
        } catch (Exception E) {
            log.error("Error: " + E);
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Error: " + E);
        }


    }

    @PostMapping("/login")
    public boolean logIn(Users acc, HttpServletResponse response) throws IOException {
        if (checkuser(acc)) {
            //    session.setAttribute("Inlogget", acc);
            return true;
        } else {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "errors inside the server, check the log for futher details");
            return false;
        }
    }

    public String crypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(14));
    }

    public boolean checkuser(Users auser) {
        //checks the password
        try {
            for (Users accounts : DB) {
                if (accounts.getUsername().equals(auser.getUsername())){
                    if (BCrypt.checkpw(auser.getPassword(), accounts.getPassword())) { //checks the password and username
                        System.out.println(accounts.getPassword()); //prints the hash string
                        return true;
                    } else{
                        return false; //returns false if the password is wrong
                    }
                }
                else {
                    return false; // returns false if the username is wrong
                }
            }
        } catch (Exception e) {
            log.error("wrong within checking username " + e);
            return false;
        }
        return false;
    }

}
