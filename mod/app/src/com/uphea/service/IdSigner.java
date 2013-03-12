package com.uphea.service;

import jodd.petite.meta.PetiteBean;
import jodd.util.BCrypt;
import jodd.util.Base64;
import jodd.util.StringUtil;

/**
 * Encodes and decodes entity IDs.
 */
@PetiteBean
public class IdSigner {

	protected String secretKey;

	protected int saltRounds = 11;

	/**
	 * Creates unique signature for a vote. 
	 */
	public String encodeSignature(Long id) {
		StringBuilder sb = new StringBuilder();
		if (id == null) {
			id = Long.valueOf(0);
		}
		sb.append(Base64.encodeToString(id.toString())).append(':');
		sb.append(BCrypt.hashpw(secretKey, BCrypt.gensalt(saltRounds)));
		return sb.toString();
	}


	/**
	 * Decodes signature and returns vote.
	 * Returns <code>null</code> if signature is invalid.
	 */
	public Long decodeSignature(String data) {
		String[] part = StringUtil.splitc(data, ':');
		if (part == null) {
			return null;
		}
		if (part.length != 2) {
			return null;
		}
		if (BCrypt.checkpw(secretKey, part[1]) == false) {
			return null;
		}
		return Long.valueOf(Base64.decodeToString(part[0]));
	}

}
