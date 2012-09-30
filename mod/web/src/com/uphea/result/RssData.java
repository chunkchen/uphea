package com.uphea.result;

import com.uphea.rss.Feed;

/**
 * Simple wrapper over RSS feed that is used as result type of Madvoc actions.
 */
public class RssData {
	private static final String RESULT = RssResult.NAME + ':';

	private final Feed feed;

	public RssData(Feed feed) {
		this.feed = feed;
	}

	public Feed getFeed() {
		return feed;
	}

	/**
	 * Returns Rss result type.
	 */
	@Override
	public String toString() {
		return RESULT;
	}
}
