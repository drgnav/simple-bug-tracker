package ru.drgnav.wellink.bugtracker.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.drgnav.wellink.bugtracker.dto.RoleDTO;
import ru.drgnav.wellink.bugtracker.dto.UserDTO;
import ru.drgnav.wellink.bugtracker.entity.Role;
import ru.drgnav.wellink.bugtracker.entity.User;
import ru.drgnav.wellink.bugtracker.util.Constants;

@Repository
@Transactional(readOnly = true)
public class CustomUserRepositoryImpl implements CustomUserRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<UserDTO> findAllMatching(UserDTO user) {
		String sql = buildUserDTOQuery(user) + " order by u.creationTime desc";
		TypedQuery<User> qry = em.createQuery(sql, User.class);
		setUserDTOQueryParams(user, qry);
		List<UserDTO> users = new ArrayList<>();
		qry.getResultList().forEach(u -> {
			Set<RoleDTO> roles = user.getRoles();
			if(roles.isEmpty()) {
				users.add(u.getDTOInstace());
			} else {
				for(Role r : u.getRoles()) {
					//Add user if any role in the list of expected roles
					if(roles.contains(r.getDTOInstace())) {
						users.add(u.getDTOInstace());
						break;
					}
				}
			}
		});
		return users;
	}

	private String buildUserDTOQuery(UserDTO user) {
		StringBuilder sb = new StringBuilder("from ru.drgnav.wellink.bugtracker.entity.User u where (1 = 1)");
		if (user == null) {
			return sb.toString();
		}
		if (!StringUtils.isEmpty(user.getUsername())) {
			sb.append(" and u.username like :namePattern escape '\\'");
		}
		if (user.isActive() != null) {
			sb.append(" and u.active = :active");
		}
		return sb.toString();
	}

	private void setUserDTOQueryParams(UserDTO user, Query query) {
		if (user == null) {
			return;
		}
		String name = user.getUsername();
		if (!StringUtils.isEmpty(name)) {
			query.setParameter("namePattern", name);
		}
		if (user.isActive() != null) {
			query.setParameter("active", user.isActive());
		}
		query.setMaxResults(Constants.MAX_DATA_FETCH_ROWS);
	}
}
