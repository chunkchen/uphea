package com.uphea.domain.constraint;

import jodd.datetime.JDateTime;
import jodd.typeconverter.Convert;
import jodd.vtor.ValidationConstraint;
import jodd.vtor.ValidationConstraintContext;

/**
 * Validation constraint for birth year.
 * Birth year must be 1900 or after and less or equal to current year.
 */
public class BirthYearConstraint implements ValidationConstraint<BirthYear> {

	/**
	 * Configures constraint from annotation. Since annotation doesn't holds
	 * any constraint specific values, there is nothing to do here.
	 */
	@Override
	public void configure(BirthYear annotation) {
	}

	/**
	 * Performs birth year validation.
	 */
	@Override
	public boolean isValid(ValidationConstraintContext vcc, Object value) {
		if (value == null) {
			return true;			// null values returns true (by Vtor specification) !!!
		}
		int year = Convert.toIntValue(value, 0);
		if (year < 1900) {
			return false;
		}
		return year <= new JDateTime().getYear();
	}
}