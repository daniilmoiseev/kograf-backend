package ru.kograf.backend.service;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.ConfirmationLink;
import ru.kograf.backend.dto.LoginDto;
import ru.kograf.backend.dto.RegistrationDto;
import ru.kograf.backend.dto.UserDto;
import ru.kograf.backend.model.User;
import ru.kograf.backend.model.UserSecurity;
import ru.kograf.backend.model.enums.Role;
import ru.kograf.backend.model.enums.UserStatus;
import ru.kograf.backend.repository.UserRepository;
import ru.kograf.backend.security.JwtAuthenticationProvider;

@Slf4j
@Service("userServiceImpl")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationProvider jwtProvider;
    private final IKografConversionService conversionService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserDto createUser(RegistrationDto registrationDto) {
        if (isPresentEmail(registrationDto.getEmail())) {
            return null;
        }

        User user = conversionService.convert(registrationDto, User.class);
        user.setPassword(new BCryptPasswordEncoder(12).encode(registrationDto.getPassword()));
        user.setRole(Role.MEMBER);
        user.setStatus(UserStatus.ACTIVE);

        User savedUser = userRepository.save(user);
        return conversionService.convert(savedUser, UserDto.class);
    }

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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailWithStatusActive(email);
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exists");
        }
        return UserSecurity.fromUser(user);
    }

    @SneakyThrows
    public UserDto updateStatus(Long userId, UserStatus userStatus) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Unable to find user by id " + userId));
        user.setStatus(userStatus);
        return conversionService.convert(userRepository.save(user), UserDto.class);
    }

    public boolean isPresentEmail(String email) {
        return userRepository.existUserByEmail(email);
    }

    public boolean checkLink(String email, String code) {
        //TODO переделать возможно на редис
        return false;
    }

    public void sendLink(String email) {
        Thread thread = new Thread(() -> {
            ConfirmationLink code = new ConfirmationLink();
            code.setLink(generateLink());
            code.setEmail(email);
            emailService.sendEmail(code.getEmail(), code.getLink());
            //TODO переделать возможно на редис
        });
        thread.start();
    }

    private String generateLink() {
        return "aaa";
    }

    public UserDto getUser(String email) {
        User user = userRepository.findByEmail(email);
        return conversionService.convert(user, UserDto.class);
    }
}
