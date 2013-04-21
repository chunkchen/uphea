package com.uphea.action;

import com.uphea.service.ImageCache;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.result.RawData;
import jodd.madvoc.result.RawResultData;
import jodd.petite.meta.PetiteInject;
import jodd.util.MimeTypes;

/**
 * Image action serves images. Simply read images from disk
 * and writes image bytecode to output stream.
 */
@MadvocAction("img/")
public class ImageAction {

	private static final String EXT_PNG = "png";
	private static final String EXT_PNG_MIME = MimeTypes.getMimeType(EXT_PNG);
	private static final String EXT_JPG = "jpg";
	private static final String EXT_JPG_MIME = MimeTypes.getMimeType(EXT_JPG);

	@PetiteInject
	ImageCache imageCache;

	@In
	String id;

	@Action(value = "${id}", extension = EXT_PNG)
	public RawResultData viewPng() {
		byte[] bytes = imageCache.readImage(id + '.' + EXT_PNG);

		return new RawData(bytes, EXT_PNG_MIME);
	}

	@Action(value = "${id}", extension = EXT_JPG)
	public RawResultData viewJpg() {
		byte[] bytes = imageCache.readImage(id + '.' + EXT_JPG);

		return new RawData(bytes, EXT_JPG_MIME);
	}

}
