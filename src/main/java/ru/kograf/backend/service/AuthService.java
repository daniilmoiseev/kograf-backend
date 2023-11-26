package ru.kograf.backend.service;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kograf.backend.dto.LoginDto;
import ru.kograf.backend.model.User;
import ru.kograf.backend.repository.UserRepository;
import ru.kograf.backend.security.JwtAuthenticationProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationProvider jwtProvider;
    private final UserRepository userRepository;

    public ResponseEntity<?> authenticate(LoginDto userDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
            User user = userRepository.findByEmailWithStatusActive(userDto.getEmail());
            if (user == null) {
                throw new UsernameNotFoundException("User doesn't exists");
            }
            String token = jwtProvider.createToken(userDto.getEmail(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", userDto.getEmail());
            response.put("token", token);
            response.put("role", user.getRole());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            log.error("Invalid email/password combination", e);
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }
}
