package com.uphea.action;

import com.uphea.domain.Question;
import com.uphea.jsp.Format;
import com.uphea.result.RssData;
import com.uphea.rss.Feed;
import com.uphea.rss.FeedEntry;
import com.uphea.service.QuestionService;
import com.uphea.util.DateUtil;
import jodd.datetime.JDateTime;
import jodd.jtx.meta.Transaction;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.MadvocAction;
import jodd.petite.meta.PetiteInject;
import jodd.util.StringPool;

import java.util.List;

/**
 * Creates a feed.
 */
@MadvocAction
public class FeedAction {

	@PetiteInject
	QuestionService questionService;

	/**
	 * Creates RSS feed.
	 * This action does not uses standard Madvoc convention,
	 * but directly maps to an URL. Generally, this should be avoid.
	 * <p>
	 * Madvoc actions can return any type, on returned objects <code>toString()</code>
	 * will be performed in order to get the return path string. That's why here
	 * feed is wrapped with {@link RssData}.
	 * <p> 
	 * Returned objects from actions are available in the Madvoc result handlers
	 * (ActionResults). So, having wrapper such as {@link RssData} is convenient for
	 * two things:
	 * <li>it returns result type in toString
	 * <li>it holds custom data that will be used for rendering. 
	 */
	@Action("/uphea.xml")
	@Transaction
	public RssData view() {
		Question latestQuestion = questionService.findQuestionForDate(new JDateTime());
		List<Question> questions = questionService.findPreviousQuestions(latestQuestion, 24);
		questions.add(0, latestQuestion);

		Feed feed = new Feed();
		feed.setTitle("uphea.com");
		feed.setLink("http://uphea.com");
		feed.setDescription("the nice way of asking smart questions");
		feed.setEncoding(StringPool.UTF_8);
		feed.setPublishedDate(DateUtil.toJDateTime(latestQuestion.getDate()));

		for (Question q : questions) {
			FeedEntry entry = new FeedEntry();
			entry.setPublishedDate(DateUtil.toJDateTime(q.getDate()));
			entry.setTitle(Format.textPlain(q.getText()));
			String link = feed.getLink() + "/q/" + q.getDate();
			entry.setLink(link);
			entry.setGuid(link);
			entry.setDescription("What do you think about?");
			feed.addEntry(entry);
		}
		return new RssData(feed);
	}
}
