package com.spring.security.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.security.entity.VerificationToken;
import java.util.List;
import java.lang.String;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
	List<VerificationToken> findByToken(String token);
}
