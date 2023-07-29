package com.example.demo.controller;


import com.example.demo.model.entity.User;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;


    @PostMapping("login-handle")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        // Tạo đối tượng xác thực
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        try {
            // Tiến hành xác thực
            Authentication authentication = authenticationManager.authenticate(token);

            // Lưu vào Context Holder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Lưu vào trong session
            session.setAttribute("MY_SESSION", authentication.getName()); // Lưu email -> session

            return ResponseEntity.ok("Login success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login fail");
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> sendResetPwEmail(@RequestParam String email) {
        try {
            userService.sendResetPwEmail(email);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot send email");
        }

        return ResponseEntity.ok("Send mail success");
    }


    @PatchMapping("/update-password")
    public ResponseEntity<?> resetPw(@RequestParam(name = "email") String email, @RequestBody String password) {

        String encodedPassword = encoder.encode(password);

        try {
            userService.resetPw(email, encodedPassword);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cannot update password");
        }
        return ResponseEntity.ok("Update password success");
    }

    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        System.out.println(user.toString());
        try {
            userService.createUser(user);
        } catch ( Exception e){
            return ResponseEntity.badRequest().body(e);
        }
        return ResponseEntity.ok("Create Success");
    }

}
// Client -> Server -> Tạo session -> Sesson_id lưu vào trong cookie -> Client
