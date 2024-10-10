package org.danyokhara.repositories;

import org.danyokhara.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> getAllBySessionId(String sessionId);
    Optional<User> findBySessionId(String sessionId);
}
