package com.gamesys.registration.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import com.gamesys.registration.exception.IssuerIdNumberBlockedException;
import com.gamesys.registration.exception.UserAlreadyExistsException;
import com.gamesys.registration.model.dao.IssuerIdNumber;
import com.gamesys.registration.model.dao.User;
import com.gamesys.registration.model.dto.UserDto;
import com.gamesys.registration.repository.BlockedIssuerIdNumberRepository;
import com.gamesys.registration.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith (SpringRunner.class)
public class RegistrationServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BlockedIssuerIdNumberRepository blockedIssuerIdNumberRepository;
    @Autowired
    private RegistrationService registrationService;
    @Captor
    private ArgumentCaptor<String> usernameArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> blockedIssuerIdNumberArgumentCaptor;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    public void testRegistrationSucceeds() {
        // Given
        User user = User.builder().username("Robin").password("$565Bn").dob(LocalDate.of(1992, 12, 13)).paymentCardNumber("456789032780459").build();
        when(userRepository.findByUsername("Robin")).thenReturn(Optional.empty());
        when(blockedIssuerIdNumberRepository.findById("456789")).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        // When
        registrationService
            .register(UserDto.builder().username("Robin").password("$565Bn").dob(LocalDate.of(1992, 12, 13)).paymentCardNumber("456789032780459").build());

        // Then
        verify(userRepository, times(1)).findByUsername(usernameArgumentCaptor.capture());
        assertEquals("Robin", usernameArgumentCaptor.getValue());
        verify(blockedIssuerIdNumberRepository, times(1)).findById(blockedIssuerIdNumberArgumentCaptor.capture());
        assertEquals("456789", blockedIssuerIdNumberArgumentCaptor.getValue());
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue(), is(samePropertyValuesAs(user)));
    }

    @Test (expected = UserAlreadyExistsException.class)
    public void testRegistrationFailsForExistingUser() {
        // Given
        User user = User.builder().username("Robin").password("$565Bn").dob(LocalDate.of(1992, 12, 13)).paymentCardNumber("456789032780459").build();
        when(userRepository.findByUsername("Robin")).thenReturn(Optional.of(user));

        // Then
        registrationService
            .register(UserDto.builder().username("Robin").password("$565Bn").dob(LocalDate.of(1992, 12, 13)).paymentCardNumber("456789032780459").build());
    }

    @Test (expected = IssuerIdNumberBlockedException.class)
    public void testRegistrationFailsForBlockedIssuerIdNumber() {
        // Given
        when(userRepository.findByUsername("Robin")).thenReturn(Optional.empty());
        when(blockedIssuerIdNumberRepository.findById("456789")).thenReturn(Optional.of(IssuerIdNumber.builder().build()));

        // Then
        registrationService
            .register(UserDto.builder().username("Robin").password("$565Bn").dob(LocalDate.of(1992, 12, 13)).paymentCardNumber("456789032780459").build());
    }

    @TestConfiguration
    static class RegistrationServiceTestContextConfiguration {

        @MockBean
        private UserRepository userRepository;

        @MockBean
        private BlockedIssuerIdNumberRepository blockedIssuerIdNumberRepository;

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public RegistrationService registrationService() {
            return new RegistrationService(userRepository, passwordEncoder(), blockedIssuerIdNumberRepository);
        }
    }
}
