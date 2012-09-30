package com.uphea.action;

import com.uphea.service.ImageCache;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.result.RawData;
import jodd.madvoc.result.RawResultData;
import jodd.petite.meta.PetiteInject;

/**
 * Image action serves images. Simply read images from disk
 * and writes image bytecode to output stream.
 */
@MadvocAction("img/")
public class ImageAction {

	private static final String EXT_PNG = ".png";
	private static final String EXT_JPG = ".jpg";

	@PetiteInject
	ImageCache imageCache;

	@In
	String id;

	@Action(value = "${id}", extension = "png")
	public RawResultData viewPng() {
		return new RawData(imageCache.readImage(id + EXT_PNG));
	}

	@Action(value = "${id}", extension = "jpg")
	public RawResultData viewJpg() {
		return new RawData(imageCache.readImage(id + EXT_JPG));
	}

}
