package com.gamesys.registration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gamesys.registration.exception.IssuerIdNumberBlockedException;
import com.gamesys.registration.exception.UserAlreadyExistsException;
import com.gamesys.registration.model.dao.IssuerIdNumber;
import com.gamesys.registration.model.dao.User;
import com.gamesys.registration.model.dto.UserDto;
import com.gamesys.registration.repository.BlockedIssuerIdNumberRepository;
import com.gamesys.registration.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BlockedIssuerIdNumberRepository blockedIssuerIdNumberRepository;

    public void register(UserDto userDto) {
        String username = userDto.getUsername();
        checkForExistingUser(username);
        // Issuer Identification Number is the 1st 6 digits of Payment Card Number
        checkForBlockedIssuerIdNumber(userDto.getPaymentCardNumber().substring(0, 6));

        userRepository.save(User.builder().username(username).password(passwordEncoder.encode(userDto.getPassword())).dob(userDto.getDob())
                                .paymentCardNumber(userDto.getPaymentCardNumber()).build());
        log.info("New user created successfully");
    }

    private void checkForExistingUser(String username) {
        Optional<User> existing = userRepository.findByUsername(username);
        if (existing.isPresent()) {
            throw new UserAlreadyExistsException(username);
        }
    }

    private void checkForBlockedIssuerIdNumber(String issuerIdNumber) {
        Optional<IssuerIdNumber> existing = blockedIssuerIdNumberRepository.findById(issuerIdNumber);
        if (existing.isPresent()) {
            throw new IssuerIdNumberBlockedException(issuerIdNumber);
        }
    }
}
