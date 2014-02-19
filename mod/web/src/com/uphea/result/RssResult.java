package com.uphea.result;

import com.uphea.rss.Feed;
import com.uphea.rss.FeedWriter;
import jodd.io.StreamUtil;
import jodd.madvoc.ActionRequest;
import jodd.madvoc.result.BaseActionResult;
import jodd.util.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * RSS result for feeds. It expects SyndFeed results.
 * Example of custom Madvoc action result.
 */
public class RssResult extends BaseActionResult<RssData> {

	private static final Logger log = LoggerFactory.getLogger(RssResult.class);

	public final String rssMimeType;

	public RssResult() {
		rssMimeType = MimeTypes.getMimeType("rss");
	}

	@Override
	public void render(ActionRequest request, RssData resultObject) throws Exception {
		Feed feed = resultObject.getFeed();
		FeedWriter feedWriter = new FeedWriter(feed);

		HttpServletResponse response = request.getHttpServletResponse();
		response.setContentType(rssMimeType);
		response.setCharacterEncoding(feed.getEncoding());
		Writer out = null;
		try {
			out = response.getWriter();
			feedWriter.write(out);
		} catch (Exception ex) {
			log.error("Could not write feed.", ex);
		} finally {
			StreamUtil.close(out);
		}
	}

}
