package com.uphea.rss;

import jodd.datetime.JDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Feed definition.
 */
public class Feed {

	protected String title;
	protected String description;
	protected String link;
	protected String pubDate;
	protected String language;
	protected String copyright;
	protected String encoding;
	protected List<FeedEntry> entries = new ArrayList<FeedEntry>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void addEntry(FeedEntry feedEntry) {
		entries.add(feedEntry);
	}


	public void setEntries(List<FeedEntry> entries) {
		this.entries = entries;
	}

	/**
	 * Returns list of all messages.
	 */
	public List<FeedEntry> getEntries() {
		return entries;
	}

	/**
	 * Sets published date in GMT.
	 */
	public void setPublishedDate(JDateTime jdt) {
		jdt.setTimeZone(TimeZone.getTimeZone("GMT"));
		this.pubDate = jdt.toString(RSS_DATE_FORMAT);
	}

	public static final String RSS_DATE_FORMAT = "DS, DD MMS YYYY hh:mm:ss GMT";
}
