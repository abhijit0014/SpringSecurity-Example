package com.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.spring.security.entity.User;
import java.lang.String;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long>{

	//List<User> findByEmailAndPass(String email, String pass);
	User findByEmail(String email);
	@Modifying
	@Transactional	
	@Query(value="update user set enabled = true where user_id = ?1", nativeQuery = true)	
	void enable(Long id);

}
