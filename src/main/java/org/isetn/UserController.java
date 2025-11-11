package org.isetn;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
@RestController

public class UserController {

    @Autowired
    private UserRepository userRepository;

  /*  @PostMapping("/register")
    public User Register(@RequestBody User user) {
        return userRepository.save(user);
    }
    @PostMapping("/login")
    public User Login(@RequestBody User user) {
        User oldUSer = userRepository.findByEmailAndPassword(user.email, user.password);
        return oldUSer;
    }*/
    
        @PostMapping("/register")
        public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
            Map<String, Object> response = new HashMap<>();
            try {
                User savedUser = userRepository.save(user);
                response.put("success", true);
                response.put("user", savedUser);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                response.put("success", false);
                response.put("error", e.getMessage());
                return ResponseEntity.badRequest().body(response);
            }
        }

        @PostMapping("/login")
        public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
            Map<String, Object> response = new HashMap<>();
            User existingUser = userRepository.findByEmailAndPassword(user.email, user.password);
            if (existingUser != null) {
                response.put("success", true);
                response.put("user", existingUser);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Invalid credentials");
                return ResponseEntity.status(401).body(response);
            }
        }
    }

