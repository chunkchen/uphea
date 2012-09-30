package com.uphea.worker;

import com.uphea.domain.Question;
import com.uphea.domain.Vote;
import com.uphea.service.IdSigner;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;

/**
 * Works with cookies.
 */
@PetiteBean
public class CookieWorker {

	private static final Logger log = LoggerFactory.getLogger(CookieWorker.class);

	private static final String COOKIE_VOTE_NAME = "upheav-";

	@PetiteInject
	IdSigner idSigner;

	public Long processAppCookiesForUserVote(Question question, Cookie[] cookies) {
		log.debug("process cookies: " + cookies.length);
		for (Cookie c : cookies) {
			String cookieName = c.getName();
			if (cookieName.startsWith(COOKIE_VOTE_NAME)) {
				try {
					long answeredQuestionId = Long.parseLong(cookieName.substring(COOKIE_VOTE_NAME.length()));
					if (answeredQuestionId == question.getId().longValue()) {
						Long userVoteId = idSigner.decodeSignature(c.getValue());
						if (userVoteId != null) {
							return userVoteId;
						}
					}
				} catch (NumberFormatException ignore) {
				}
			}
		}
		return null;
	}

	public Cookie createVoteCookie(Question question, Vote vote) {
		Cookie cookie = new Cookie(COOKIE_VOTE_NAME + question.getId(), idSigner.encodeSignature(vote.getId()));
		cookie.setPath("/");
		cookie.setMaxAge(86400 * 30);
		return cookie;
	}
}
