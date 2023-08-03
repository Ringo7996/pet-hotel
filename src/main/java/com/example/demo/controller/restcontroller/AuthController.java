package com.example.demo.controller.restcontroller;


import com.example.demo.model.entity.User;
import com.example.demo.model.request.*;
import com.example.demo.repository.UserRepository;

import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;


    @PostMapping("login-handle")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session, HttpServletRequest req, HttpServletResponse response) {
        // Tạo đối tượng xác thực
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        try {

            // Tiến hành xác thực
            Authentication authentication = authenticationManager.authenticate(token);

            User user = userService.findByEmail(request.getEmail());
            Boolean isActivity  = userService.isActivity(user.getId());
            if(!isActivity){
                return ResponseEntity.badRequest().body("Account has been locked");
            }

            // Lưu vào Context Holder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Lưu vào trong session
            session.setAttribute("MY_SESSION", authentication.getName()); // Lưu email -> session

            SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(req, response);
            return ResponseEntity.ok("Login success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Tài khoản hoặc mật khẩu không đúng");
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> sendResetPwEmail(@RequestBody String email) {
        System.out.println(email);
        try {
            userService.sendResetPwEmail(email);
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body("Cannot send email");
        }

        return ResponseEntity.ok("Send mail success");
    }


    @PatchMapping("/reset-password")
    public ResponseEntity<?> resetPw(@Valid @RequestBody ResetPasswodRequest password) {
        String encodedPassword = encoder.encode(password.getPassword());
        try {
            userService.resetPw(password.getEmail(), encodedPassword);
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.badRequest().body("Cannot update password");
        }
        return ResponseEntity.ok("Update password success");
    }
    @PatchMapping("/update-password")
    public ResponseEntity<?> updatePw(@Valid @RequestBody UpdatePasswordRequest request, HttpSession session) {
        try {
            userService.updatePassword(request,session);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
        return ResponseEntity.ok("Update password success");
    }
    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserRequest request) {
        try {
            System.out.println(request);
            userService.createUser(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
        return ResponseEntity.ok("Create Success");
    }

    @PostMapping("update-info")
    public ResponseEntity<?> updateInfo( @Valid @RequestBody UpdateUserRequest request, HttpSession session) {
        try {
            userService.updateInfo(request,session);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
        return ResponseEntity.ok("Update Success");
    }


}
// Client -> Server -> Tạo session -> Sesson_id lưu vào trong cookie -> Client
