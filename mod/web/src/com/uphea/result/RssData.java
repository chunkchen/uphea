package com.uphea.result;

import com.uphea.rss.Feed;
import jodd.madvoc.meta.RenderWith;

/**
 * Simple wrapper over RSS feed that is used as result type of Madvoc actions.
 */
@RenderWith(RssResult.class)
public class RssData {

	private final Feed feed;

	public RssData(Feed feed) {
		this.feed = feed;
	}

	public Feed getFeed() {
		return feed;
	}

}
