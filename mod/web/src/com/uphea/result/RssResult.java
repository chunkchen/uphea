package com.uphea.result;

import com.uphea.rss.Feed;
import com.uphea.rss.FeedWriter;
import jodd.io.StreamUtil;
import jodd.madvoc.ActionRequest;
import jodd.madvoc.result.ActionResult;
import jodd.util.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * RSS result for feeds. It expects SyndFeed results.
 * Example of custom Madvoc action result.
 */
public class RssResult extends ActionResult {

	private static final Logger log = LoggerFactory.getLogger(RssResult.class);

	public static final String NAME = "rss";
	public final String rssMimeType;

	public RssResult() {
		super(NAME);
		rssMimeType = MimeTypes.getMimeType(NAME);
	}

	@Override
	public void render(ActionRequest request, Object resultObject, String resultValue, String resultPath) throws Exception {
		if (resultObject instanceof RssData == false) {
			return;
		}
		Feed feed = ((RssData) resultObject).getFeed();
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
