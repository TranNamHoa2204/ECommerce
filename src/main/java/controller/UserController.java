package controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DTO.request.LoginRequestDTO;
import DTO.request.RegisterRequestDTO;
import DTO.response.AuthResponseDTO;
import DTO.response.UserResponseDTO;
import entity.User;
import jakarta.validation.Valid;
import security.JwtTokenProvider;
import service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    public UserController(UserService userService, JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        User user = userService.dangKy(
                request.getFullName(),
                request.getEmail(),
                request.getPassword(),
                request.getPhone()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDTO.fromEntity(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        User user = userService.dangNhap(request.getEmail(), request.getPassword());
        
        // Sinh JWT Token
        String token = tokenProvider.generateToken(user.getUserId(), user.getEmail(), user.getRole());
        
        AuthResponseDTO response = new AuthResponseDTO(token, UserResponseDTO.fromEntity(user));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserResponseDTO.fromEntity(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers().stream()
                .map(UserResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(users);
    }
}
