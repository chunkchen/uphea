package com.uphea.interceptor;

import jodd.joy.madvoc.interceptor.DefaultPreparableInterceptorStack;
import jodd.madvoc.interceptor.ActionInterceptorStack;
import jodd.madvoc.interceptor.EchoInterceptor;

/**
 * Preparable app interceptor stack.
 */
public class AppPreparableInterceptorStack extends ActionInterceptorStack {

	public AppPreparableInterceptorStack() {
		super(
				EchoInterceptor.class,
				AppAuthenticationInterceptor.class,
				AppAuthorizationInterceptor.class,
				DefaultPreparableInterceptorStack.class
		);
	}
}