package com.uphea.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PopupAction {

	String value() default "";

	/**
	 * Popup actions has a different extension,
	 * so not to be caught by sitemesh decorators.
	 */
	String extension() default "htm";

	String alias() default "";

	String method() default "";

}
