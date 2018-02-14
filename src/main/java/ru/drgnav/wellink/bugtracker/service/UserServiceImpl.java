package ru.drgnav.wellink.bugtracker.service;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ru.drgnav.wellink.bugtracker.dto.UserDTO;
import ru.drgnav.wellink.bugtracker.entity.BugState;
import ru.drgnav.wellink.bugtracker.entity.Role;
import ru.drgnav.wellink.bugtracker.entity.User;
import ru.drgnav.wellink.bugtracker.repository.AttachmentRepository;
import ru.drgnav.wellink.bugtracker.repository.BugRepository;
import ru.drgnav.wellink.bugtracker.repository.BugStateRepository;
import ru.drgnav.wellink.bugtracker.repository.CustomUserRepository;
import ru.drgnav.wellink.bugtracker.repository.UserRepository;
import ru.drgnav.wellink.bugtracker.util.Constants;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CustomUserRepository cUserRepo;

	@Autowired
	private AttachmentRepository attachmentRepo;

	@Autowired
	private BugRepository bugRepo;

	@Autowired
	private BugStateRepository bugStateRepo;

	@Override
	public void save(UserDTO user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepo.save(new User(user));
	}

	@Override
	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public UserDTO findByUserId(Long id) {
		return userRepo.findOne(id).getDTOInstace();
	}

	@Override
	public List<UserDTO> findUsersMatching(UserDTO user) {
		return cUserRepo.findAllMatching(user);
	}

	@Override
	public boolean isUserDeletable(Long userId) {
		User user = userRepo.findOne(userId);
		return !(bugRepo.existsByAuthor(user) || bugRepo.existsByExecutor(user)
				|| attachmentRepo.existsByAuthor(user));
	}

	@Override
	public void deleteUser(Long userId) {
		userRepo.delete(userId);
	}

	@Override
	public List<SelectItem> getUsersForState(Long stateId, boolean withEmpty) {
		List<SelectItem> items = new ArrayList<>();
		if(withEmpty) {
			items.add(new SelectItem(0L, ""));
		}
		
		BugState state = null;
		if(stateId != null && stateId > 1) {
			state = bugStateRepo.getOne(stateId);
		}
		if(state == null) {
			LOG.warn("Can't get BugState for id = {}", stateId);
		} else if (state.getRoles() == null || state.getRoles().isEmpty()) {
			//Если для состояния отсутствуют роли, то берутся все пользователи
			Pageable page = new PageRequest(0, Constants.MAX_DATA_FETCH_ROWS);
			userRepo.findAll(page).forEach(u -> items.add(new SelectItem(u.getId(), u.getUsername())));
		} else {
			for( Role r : state.getRoles()) {
				r.getUsers().forEach(s -> items.add(new SelectItem(s.getId(), s.getUsername())));
			}
		}
		return items;
	}
}