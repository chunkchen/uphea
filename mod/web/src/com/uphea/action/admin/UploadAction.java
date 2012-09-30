package com.uphea.action.admin;

import com.uphea.AppData;
import com.uphea.domain.Question;
import com.uphea.exception.UpheaException;
import com.uphea.service.QuestionService;
import jodd.io.FileNameUtil;
import jodd.io.FileUtil;
import jodd.joy.madvoc.action.AppAction;
import jodd.joy.madvoc.meta.PostAction;
import jodd.jtx.meta.Transaction;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.MadvocAction;
import jodd.petite.meta.PetiteInject;
import jodd.upload.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Handle question image uploads.
 */
@MadvocAction
public class UploadAction extends AppAction {

	private static final Logger log = LoggerFactory.getLogger(UploadAction.class);

	@PetiteInject
	QuestionService questionService;

	@PetiteInject
	AppData appData;

	@Action
	public void view() {
	}

	@In
	Long id;

	@In
	FileUpload file;

	@PostAction
	@Transaction
	public String image() {

		if (id == null) {
			String fileName = file.getHeader().getFileName();
			String baseName = FileNameUtil.getBaseName(fileName);
			id = Long.valueOf(baseName);
		}

		Question question = questionService.findQuestionById(id);
		if ((question != null) && file.isValid()) {
			System.out.println(file);
			try {
				FileUtil.writeBytes(new File(appData.getImgRoot(), question.getId() + ".jpg"), file.getFileContent());
			} catch (IOException ioex) {
				log.error("Unable to save uploaded file.", ioex);
				throw new UpheaException("Unable to save uploaded file.", ioex);
			}
		}
		return REDIRECT + alias(this, "view");
	}
}
