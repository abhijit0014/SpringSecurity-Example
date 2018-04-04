package com.spring.security.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.security.entity.PersistentLogins;
import java.lang.String;

import javax.transaction.Transactional;
@Repository
public interface PersistentLoginRepository extends JpaRepository<PersistentLogins, Long> {
	PersistentLogins findBySeries(String series);
	@Modifying
	@Transactional
	@Query(value="delete from LOGINS_PERSISTENT where username = ?1", nativeQuery = true)
	void deleteByUsername(String username);
}
