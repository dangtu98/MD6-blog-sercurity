package com.codegym.controller.auth;

import com.codegym.dto.request.LoginForm;
import com.codegym.dto.request.SignUpForm;
import com.codegym.dto.response.JwtResponse;
import com.codegym.dto.response.ResponseMessage;
import com.codegym.model.Role;
import com.codegym.model.RoleName;
import com.codegym.model.User;
import com.codegym.security.jwt.JwtAuthTokenFilter;
import com.codegym.security.jwt.JwtProvider;
import com.codegym.security.service.UserPrinciple;
import com.codegym.service.Account.role.IRoleService;
import com.codegym.service.Account.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    JwtAuthTokenFilter jwtAuthTokenFilter;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getId(), userDetails.getFullName(), userDetails.getEmail(),
                userDetails.getPhone(), userDetails.getAddress(), userDetails.getAvatar(), userDetails.getAuthorities()
        ));
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("User existed, please entry other user"),
                    HttpStatus.OK);
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Email existed, please entry other email"),
                    HttpStatus.OK);
        }

        // Creating user's account
        User user = new User(signUpRequest.getFullName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);

                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        userService.save(user);

        return new ResponseEntity<>(new ResponseMessage("Thành Công"), HttpStatus.OK);
    }

//    @PutMapping("changeProfile")
//    public ResponseEntity<?> changeProfile(HttpServletRequest request, @Valid @RequestBody ChangeProfileForm changeProfileForm) {
//        String jwt = jwtAuthTokenFilter.getJwt(request);
//        String username = jwtProvider.getUserNameFromJwtToken(jwt);
//        User user;
//        try {
//            if (userService.existsByEmail(changeProfileForm.getEmail())) {
//                return new ResponseEntity<>(new ResponseMessage("noemail"), HttpStatus.OK);
//            }
//            user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found" + username));
//            user.setEmail(changeProfileForm.getEmail());
//            user.setAddress(changeProfileForm.getAddress());
//            user.setPhone(changeProfileForm.getPhone());
//            user.setFullName(changeProfileForm.getFullName());
//            userService.save(user);
//            return new ResponseEntity<>(new ResponseMessage("change successfully"), HttpStatus.OK);
//        } catch (UsernameNotFoundException e) {
//            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping("changePassword")
//    public ResponseEntity<?> changePassword(HttpServletRequest request, @RequestBody ChangePasswordForm changePassword) {
//        String jwt = jwtAuthTokenFilter.getJwt(request);
//        String username = jwtProvider.getUserNameFromJwtToken(jwt);
//        User user;
//        try {
//            user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found" + username));
//            if (!passwordEncoder.matches(changePassword.getCurrentPassword(), user.getPassword())) {
////            Mã 600 là lỗi sai mật khẩu hiện tại
//                return new ResponseEntity<>(new ResponseMessage("600"), HttpStatus.OK);
//            } else if (!changePassword.getNewPassword().equals(changePassword.getConfirmNewPassword())) {
////            Mã 601 là lỗi xác nhận mật khẩu mới sai
//                return new ResponseEntity<>(new ResponseMessage("601"), HttpStatus.OK);
//            }
//            user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
//            userService.save(user);
//            return new ResponseEntity<>(new ResponseMessage("ok"), HttpStatus.OK);
//
//        } catch (UsernameNotFoundException e) {
//            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping("changeAvatar")
//    public ResponseEntity<?> changeAvatar(HttpServletRequest request, @RequestBody ChangeAvatar changeAvatar) {
//        String jwt = jwtAuthTokenFilter.getJwt(request);
//        String username = jwtProvider.getUserNameFromJwtToken(jwt);
//        User user;
//        try {
//            if (changeAvatar.getAvatar() == null) {
//                return new ResponseEntity<>(new ResponseMessage("not found"), HttpStatus.OK);
//            } else {
//                user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found" + username));
//                user.setAvatar(changeAvatar.getAvatar());
//                userService.save(user);
//            }
//            return new ResponseEntity<>(new ResponseMessage("change avatar successfully"), HttpStatus.OK);
//
//        } catch (UsernameNotFoundException e) {
//            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("findUserByUsername/{username}")
//    public ResponseEntity<?> findUserByuserName(@PathVariable("username") String username) {
//        Optional<User> user = userService.findByUsername(username);
//        if (!user.isPresent()) {
//            return  new ResponseEntity<>(new ResponseMessage("notfounduser"),HttpStatus.OK);
//        }
//        return new ResponseEntity<>(user.get(), HttpStatus.OK);
//    }

}
