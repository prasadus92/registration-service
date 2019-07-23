package com.gamesys.registration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesys.registration.model.dao.IssuerIdNumber;
import com.gamesys.registration.repository.BlockedIssuerIdNumberRepository;
import com.gamesys.registration.validation.IssuerIdNumberValidator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceStartup {

    private final BlockedIssuerIdNumberRepository blockedIssuerIdNumberRepository;
    private final IssuerIdNumberValidator issuerIdNumberValidator;
    @Value ("${iin.blocked.file:blocked_iin.json}")
    private String blockedIssuerIdNumberFile;

    public void initialize() {
        List<String> blockedIssuerIdNums = getBlockedIssuerIdNumbersFromFile();
        blockedIssuerIdNums.forEach(s -> validateAndSaveIssuerIdNumber(s));
    }

    private void validateAndSaveIssuerIdNumber(String blockedIssuerIdNumber) {
        // Ignoring the invalid
        if (issuerIdNumberValidator.isValid(blockedIssuerIdNumber)) {
            blockedIssuerIdNumberRepository.save(IssuerIdNumber.builder().value(blockedIssuerIdNumber).build());
        }
    }

    private List<String> getBlockedIssuerIdNumbersFromFile() {
        log.info("Loading blocked Issuer Id Numbers from file - {}", blockedIssuerIdNumberFile);
        ClassPathResource resource = new ClassPathResource(blockedIssuerIdNumberFile);
        try (InputStream inputStream = resource.getInputStream()) {
            return new ObjectMapper().readValue(inputStream, ArrayList.class);
        } catch (IOException e) {
            log.error("Error in reading the blocked Issuer Id Numbers from the file", e);
        }

        return Collections.emptyList();
    }
}
