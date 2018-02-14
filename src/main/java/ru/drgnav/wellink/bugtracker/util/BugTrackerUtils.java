package ru.drgnav.wellink.bugtracker.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.drgnav.wellink.bugtracker.dto.BugDTO;
import ru.drgnav.wellink.bugtracker.repository.BugStateRepository;
import ru.drgnav.wellink.bugtracker.service.BugService;

@Component(value = "btUtils")
public class BugTrackerUtils {
	private static final Logger LOG = LoggerFactory.getLogger(BugTrackerUtils.class);

	public static final SelectItem ANY_BUG_STATE = new SelectItem(0l, "ANY");

	@Autowired
	private MessageSource msgSource;

	@Autowired
	private BugStateRepository stateRepository;

	@Autowired
	private BugService bugService;

	public String getMessage(String msg, Object... params) {
		getRequestLocale();
		return msgSource.getMessage(msg, params, getRequestLocale());
	}

	public Locale getRequestLocale() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			return request.getLocale();
		}
		LOG.warn("Called in not the context of an HTTP request, returns default locale");
		return Locale.getDefault();
	}

	public List<SelectItem> getActiveList(boolean withAny) {
		List<SelectItem> list = new ArrayList<>();
		if (withAny) {
			list.add(new SelectItem(0, getMessage("any")));
		}
		list.add(new SelectItem(1, getMessage("list.active.yes")));
		list.add(new SelectItem(2, getMessage("list.active.no")));
		return list;
	}

	/**
	 * Отображает сообщение '<rich:message for=...' об ошибке для заданного элемента
	 * формы
	 * 
	 * @param msg
	 *            сообщение
	 * @param element
	 *            идентификатор элемента для которого выводится сообщение
	 */
	public void displayErrorMessage(String msg, String element) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "");
		FacesContext.getCurrentInstance().addMessage(element, message);
	}

	/**
	 * Очишает все соообщения на форме
	 */
	public void clearMessage() {
		Iterator<FacesMessage> msgs = FacesContext.getCurrentInstance().getMessages();
		while (msgs.hasNext()) {
			msgs.next();
			msgs.remove();
		}
	}

	public List<SelectItem> getStates(boolean withAny) {
		List<SelectItem> list = new ArrayList<>();
		if (withAny) {
			list.add(ANY_BUG_STATE);
		}
		stateRepository.findAll().forEach(s -> list.add(new SelectItem(s.getId(), s.getName())));
		return list;
	}

	public boolean isBugValidForPersist(BugDTO bugFilter) {
		boolean isValid = true;
		clearMessage();
		if (StringUtils.isEmpty(bugFilter.getBugNumber())) {
			displayErrorMessage(getMessage("bug.number.empty"), "bug_number");
			isValid = false;
		}
		if (StringUtils.isEmpty(bugFilter.getDescription())) {
			displayErrorMessage(getMessage("bug.description.empty"), "bug_description");
			isValid = false;
		}
		if (!bugService.findMainByBugNumber(bugFilter).isEmpty()) {
			displayErrorMessage(getMessage("bag.exists"), "bug_number");
			isValid = false;
		}
		return isValid;
	}
}
