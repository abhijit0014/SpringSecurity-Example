package com.spring.security.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.security.entity.PersistentLogins;
import com.spring.security.repository.PersistentLoginRepository;


@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true) // for data fetch
public class PersistentLoginService {

	private final PersistentLoginRepository persistentLoginRepository;
	public PersistentLoginService(PersistentLoginRepository persistentLoginRepository) {
		this.persistentLoginRepository = persistentLoginRepository;
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false) // for data insert
	public PersistentLogins save(PersistentLogins persistentLogins) {
		return persistentLoginRepository.save(persistentLogins);
	}
	
	public PersistentLogins getBySeriesId(String seriesId) {
		return persistentLoginRepository.findBySeries(seriesId);
	}
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false) // for data insert
	public void delete(String username) {
		persistentLoginRepository.deleteByUsername(username);
	}
}
