package com.example.webplaner.service;

import com.example.webplaner.dto.UserDto;
import com.example.webplaner.exception.UnauthorizedAccessException;
import com.example.webplaner.model.entity.User;
import com.example.webplaner.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<String> getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return Optional.of(authentication.getName());
        }
        return Optional.empty();
    }

    public Optional<Long> getCurrentUserId() {
        return findUserByUsername(getCurrentUsername()
                .orElseThrow(UnauthorizedAccessException::new))
                .map(User::getId);
    }

    public boolean isUserExist(String username) {
        return userRepository.existsByUsername(username);
    }

    public Optional<User> getCurrentUser() {
        return findUserByUsername(getCurrentUsername().orElseThrow(UnauthorizedAccessException::new));
    }

}
