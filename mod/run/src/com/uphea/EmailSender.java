package com.uphea;

import jodd.util.ThreadUtil;

public class EmailSender {

	public static void main(String[] args) {
		new AppWebRunner() {
			@Override
			public void run() {
				ThreadUtil.sleep(80000);
			}
		}.runWebApp();
	}

}
