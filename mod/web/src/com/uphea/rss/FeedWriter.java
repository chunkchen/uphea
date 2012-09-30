package com.uphea.rss;

import jodd.util.StringPool;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.OutputStream;
import java.io.Writer;

import static jodd.util.StringPool.EMPTY;

/**
 * Feed writer using STAX.
 */
public class FeedWriter {

	private Feed rssfeed;

	public FeedWriter(Feed rssfeed) {
		this.rssfeed = rssfeed;
	}

	/**
	 * Writes feed to output stream.
	 */
	public void write(OutputStream out) throws XMLStreamException {
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		XMLEventWriter writer = outputFactory.createXMLEventWriter(out);
		write(writer);
	}

	/**
	 * Writes feed to writer.
	 */
	public void write(Writer out) throws XMLStreamException {
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		XMLEventWriter writer = outputFactory.createXMLEventWriter(out);
		write(writer);
	}

	/**
	 * Writes feed.
	 */
	protected void write(XMLEventWriter writer) throws XMLStreamException {
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD(StringPool.NEWLINE);

		StartDocument startDocument = eventFactory.createStartDocument();
		writer.add(startDocument);
		writer.add(end);

		StartElement rssStart = eventFactory.createStartElement(EMPTY, EMPTY, "rss");
		writer.add(rssStart);
		writer.add(eventFactory.createAttribute("version", "2.0"));
		writer.add(end);

		writer.add(eventFactory.createStartElement(EMPTY, EMPTY, "channel"));
		writer.add(end);

		if (rssfeed.getPubDate() != null) {
			createNode(writer, "pubDate", rssfeed.getPubDate());
		}
		if (rssfeed.getTitle() != null) {
			createNode(writer, "title", rssfeed.getTitle());
		}
		if (rssfeed.getDescription() != null) {
			createNode(writer, "description", rssfeed.getDescription());
		}
		if (rssfeed.getLink() != null) {
			createNode(writer, "link", rssfeed.getLink());
		}
		if (rssfeed.getLanguage() != null) {
			createNode(writer, "language", rssfeed.getLanguage());
		}
		if (rssfeed.getCopyright() != null) {
			createNode(writer, "copyright", rssfeed.getCopyright());
		}
		for (FeedEntry entry : rssfeed.getEntries()) {
			writer.add(eventFactory.createStartElement(EMPTY, EMPTY, "item"));
			writer.add(end);
			if (entry.getTitle() != null) {
				createNode(writer, "title", entry.getTitle());
			}
			if (entry.getDescription() != null) {
				createNode(writer, "description", entry.getDescription());
			}
			if (rssfeed.getPubDate() != null) {
				createNode(writer, "pubDate", entry.getPubDate());
			}
			if (entry.getLink() != null) {
				createNode(writer, "link", entry.getLink());
			}
			if (entry.getAuthor() != null) {
				createNode(writer, "author", entry.getAuthor());
			}
			if (entry.getGuid() != null) {
				createNode(writer, "guid", entry.getGuid());
			}
			writer.add(end);
			writer.add(eventFactory.createEndElement(EMPTY, EMPTY, "item"));
			writer.add(end);

		}

		writer.add(end);
		writer.add(eventFactory.createEndElement(EMPTY, EMPTY, "channel"));
		writer.add(end);
		writer.add(eventFactory.createEndElement(EMPTY, EMPTY, "rss"));
		writer.add(end);
		writer.add(eventFactory.createEndDocument());

		writer.close();
	}

	protected void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD(StringPool.NEWLINE);
		XMLEvent tab = eventFactory.createDTD(StringPool.TAB);

		StartElement startNode = eventFactory.createStartElement(EMPTY, EMPTY, name);
		eventWriter.add(tab);
		eventWriter.add(startNode);

		Characters content = eventFactory.createCharacters(value);
		eventWriter.add(content);

		EndElement endNode = eventFactory.createEndElement(EMPTY, EMPTY, name);
		eventWriter.add(endNode);
		eventWriter.add(end);
	}
}
