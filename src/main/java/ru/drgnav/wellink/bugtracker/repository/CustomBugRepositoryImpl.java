package ru.drgnav.wellink.bugtracker.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.drgnav.wellink.bugtracker.dto.BugDTO;
import ru.drgnav.wellink.bugtracker.entity.Bug;
import ru.drgnav.wellink.bugtracker.util.Constants;

@Repository
@Transactional(readOnly = true)
public class CustomBugRepositoryImpl implements CustomBugRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<BugDTO> findAllMatching(BugDTO bug) {
		String sql = buildBugDTOQuery(bug) + " order by b.creationTime desc";
		List<BugDTO> bugs = new ArrayList<>();
		TypedQuery<Bug> qry = em.createQuery(sql, Bug.class);
		setBugDTOQueryParameters(qry, bug);
		qry.getResultList().forEach(b -> bugs.add(b.getDTOInstace()));
		return bugs;
	}

	private String buildBugDTOQuery(BugDTO bug) {
		StringBuilder sb = new StringBuilder("from ru.drgnav.wellink.bugtracker.entity.Bug b where (b.parent is null)");
		if (!StringUtils.isEmpty(bug.getBugNumber())) {
			sb.append(" and b.bugNumber like :number");
		}
		if (!StringUtils.isEmpty(bug.getDescription())) {
			sb.append(" and b.description like :description");
		}
		if (bug.getAuthor() != null && !StringUtils.isEmpty(bug.getAuthor().getUsername())) {
			sb.append(" and b.author.username like :author");
		}
		if (bug.getExecutor() != null && !StringUtils.isEmpty(bug.getExecutor().getUsername())) {
			sb.append(" and b.executor.username like :executor");
		}
		if (bug.getState().getId() > 0) {
			sb.append(" and b.state.id = :stateId");
		}
		return sb.toString();
	}

	private void setBugDTOQueryParameters(Query qry, BugDTO bug) {
		if (!StringUtils.isEmpty(bug.getBugNumber())) {
			qry.setParameter("number", bug.getBugNumber());
		}
		if (!StringUtils.isEmpty(bug.getDescription())) {
			qry.setParameter("description", bug.getDescription());
		}
		if (bug.getAuthor() != null && !StringUtils.isEmpty(bug.getAuthor().getUsername())) {
			qry.setParameter("author", bug.getAuthor().getUsername());
		}
		if (bug.getExecutor() != null && !StringUtils.isEmpty(bug.getExecutor().getUsername())) {
			qry.setParameter("executor", bug.getExecutor().getUsername());
		}
		if (bug.getState().getId() > 0) {
			qry.setParameter("stateId", bug.getState().getId());
		}
		qry.setMaxResults(Constants.MAX_DATA_FETCH_ROWS);
	}
}
