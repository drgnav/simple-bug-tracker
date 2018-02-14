package ru.drgnav.wellink.bugtracker.service;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.drgnav.wellink.bugtracker.entity.User;
import ru.drgnav.wellink.bugtracker.exception.BugTrackerRuntimeException;
import ru.drgnav.wellink.bugtracker.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			throw new BugTrackerRuntimeException("User with name '" + username + "' not found");
		}
		
		if(!user.isActive()) {
			throw new BugTrackerRuntimeException("User with name '" + username + "' is inactive");
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		user.getRoles().forEach((r) -> grantedAuthorities.add(new SimpleGrantedAuthority(r.getName())));

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				grantedAuthorities);
	}
}