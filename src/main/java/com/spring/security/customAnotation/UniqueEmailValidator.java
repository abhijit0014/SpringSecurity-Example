package com.spring.security.customAnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import com.spring.security.repository.UserRepository;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	private UserRepository userRepository;

	@Override
	public void initialize(UniqueEmail arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		if(userRepository==null)return true;
		return userRepository.findByEmail(email)==null;
	}

}
