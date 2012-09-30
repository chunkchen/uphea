package com.uphea.jsp;

import org.junit.Assert;
import org.junit.Test;

/**
 * Format test.
 */
public class FormatTest {

	@Test
	public void testFixedFloat() {
		Assert.assertEquals("12.34", Format.fixedFloat(1234));
		Assert.assertEquals("12.04", Format.fixedFloat(1204));
		Assert.assertEquals("12.00", Format.fixedFloat(1200));
		Assert.assertEquals("1.23", Format.fixedFloat(123));
		Assert.assertEquals("0.10", Format.fixedFloat(10));
		Assert.assertEquals("0.00", Format.fixedFloat(0));
	}

	@Test
	public void testTrimSpaceBlocks() {
		Assert.assertEquals("123", Format.trimWhitespaceBlocks("123"));
		Assert.assertEquals(" 1 2 3 ", Format.trimWhitespaceBlocks(" 1 2 3 "));
		Assert.assertEquals(" 1 2 3 ", Format.trimWhitespaceBlocks("  1  2  3  "));
		Assert.assertEquals(" 1 23 4", Format.trimWhitespaceBlocks("  1         23           4"));
	}
}
