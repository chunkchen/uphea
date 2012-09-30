package com.uphea.rss;

import jodd.datetime.JDateTime;

import java.util.TimeZone;

/**
 * Feeds message.
 */
public class FeedEntry {

	protected String title;
	protected String description;
    protected String link;
    protected String author;
    protected String guid;
	protected String pubDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	/**
	 * Sets published date in GMT.
	 */
	public void setPublishedDate(JDateTime jdt) {
		jdt.setTimeZone(TimeZone.getTimeZone("GMT"));
		this.pubDate = jdt.toString(Feed.RSS_DATE_FORMAT);
	}

}
