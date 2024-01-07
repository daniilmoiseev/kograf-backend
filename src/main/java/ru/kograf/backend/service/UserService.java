package ru.kograf.backend.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kograf.backend.conversation.service.IKografConversionService;
import ru.kograf.backend.dto.ConfirmationLink;
import ru.kograf.backend.dto.RegistrationDto;
import ru.kograf.backend.dto.UserDto;
import ru.kograf.backend.dto.UserWithPasswordDto;
import ru.kograf.backend.model.User;
import ru.kograf.backend.model.UserSecurity;
import ru.kograf.backend.model.enums.Role;
import ru.kograf.backend.model.enums.UserStatus;
import ru.kograf.backend.repository.UserRepository;

@Slf4j
@Service("userServiceImpl")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final IKografConversionService conversionService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserDto createUser(RegistrationDto registrationDto) {
        if (isPresentEmail(registrationDto.getEmail())) {
            return null;
        }

        User byEmailWithStatusBanned = userRepository.findByEmailWithStatusBanned(registrationDto.getEmail());
        User user;
        if (byEmailWithStatusBanned == null) {
            user = conversionService.convert(registrationDto, User.class);
        } else {
            user = byEmailWithStatusBanned;
            user.setFullName(registrationDto.getFullName());
            user.setPhone(registrationDto.getPhone());
            user.setOrganization(registrationDto.getOrganization());
            user.setAcademicDegree(registrationDto.getAcademicDegree());
            user.setAcademicTitle(registrationDto.getAcademicTitle());
        }

        user.setPassword(new BCryptPasswordEncoder(12).encode(registrationDto.getPassword()));
        user.setRole(Role.MEMBER);
        user.setStatus(UserStatus.ACTIVE);

        User savedUser = userRepository.save(user);
        return conversionService.convert(savedUser, UserDto.class);
    }

    @SneakyThrows
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new Exception("Unable to find user by id " + userDto.getId()));

        user.setFullName(userDto.getFullName());
        user.setPhone(userDto.getPhone());
        user.setOrganization(userDto.getOrganization());
        user.setAcademicDegree(userDto.getAcademicDegree());
        user.setAcademicTitle(userDto.getAcademicTitle());
        user.setOrcId(userDto.getOrcId());
        user.setRincId(userDto.getRincId());

        User savedUser = userRepository.save(user);
        return conversionService.convert(savedUser, UserDto.class);
    }

    @SneakyThrows
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("Unable to find user by id " + id));

        user.setStatus(UserStatus.BANNED);
        user.setPassword("");
        userRepository.save(user);
    }

    @SneakyThrows
    public UserDto updatePassword(UserWithPasswordDto userWithPasswordDto) {
        User user = userRepository.findById(userWithPasswordDto.getId())
                .orElseThrow(() -> new Exception("Unable to find user by id " + userWithPasswordDto.getId()));

        user.setPassword(new BCryptPasswordEncoder(12).encode(userWithPasswordDto.getPassword()));

        User savedUser = userRepository.save(user);
        return conversionService.convert(savedUser, UserDto.class);
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailWithStatusActive(email);
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exists");
        }
        return UserSecurity.fromUser(user);
    }

    @SneakyThrows
    public boolean appointRole(Long userId, Role role) {
        if (!Role.SUPER_ADMIN.equals(role)) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new Exception("Unable to find user by id " + userId));
            user.setRole(role);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAllUsers().stream()
                .map(e -> conversionService.convert(e, UserDto.class))
                .toList();
    }
}
