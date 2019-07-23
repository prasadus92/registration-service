package com.gamesys.registration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gamesys.registration.model.dao.IssuerIdNumber;

public interface BlockedIssuerIdNumberRepository extends MongoRepository<IssuerIdNumber, String> {
}
