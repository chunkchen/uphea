package com.uphea.service;

import com.uphea.AppData;
import com.uphea.exception.UpheaException;
import jodd.cache.FileLFUCache;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInitMethod;
import jodd.petite.meta.PetiteInject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

@PetiteBean
public class ImageCache {

	private static final Logger log = LoggerFactory.getLogger(ImageCache.class);

	@PetiteInject
	AppData appData;

	int size;
	int maxFileSize;
	protected FileLFUCache fileLRUCache;

	@PetiteInitMethod
	public void init() {
		fileLRUCache = new FileLFUCache(size, maxFileSize);
		if (log.isDebugEnabled()) {
			log.debug("FileLRUCache created: " + size + ", " + maxFileSize);
		}
	}

	/**
	 * Reads image content. It may be cached.
	 */
	public byte[] readImage(String fileName) {
		File file = new File(appData.getImgRoot(), fileName);
		try {
			return fileLRUCache.getFileBytes(file);
		} catch (IOException ioex) {
			throw new UpheaException("Unable to read file " + fileName, ioex);
		}
	}

}
