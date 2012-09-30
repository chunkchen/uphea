package com.uphea;

import com.uphea.domain.Question;
import jodd.bean.BeanUtil;

/**
 * Prints question for certain date.
 * An example how to use actions without a container and to
 * simulate request and read the results.
 */
public class IndexAction extends AppWebRunner {

	public static void main(String[] args) {
		new IndexAction().runWebApp();
	}

	@Override
	public void run() {
		com.uphea.action.IndexAction indexAction = petite.createBean(com.uphea.action.IndexAction.class);
		BeanUtil.setDeclaredProperty(indexAction, "date", "20100720");
		indexAction.view();
		Question q = (Question) BeanUtil.getDeclaredProperty(indexAction, "question");
		System.out.println(q.getText());
	}
}
