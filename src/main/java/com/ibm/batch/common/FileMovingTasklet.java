package com.ibm.batch.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class FileMovingTasklet implements Tasklet, InitializingBean {

	private Resource[] resources;
	private String moveFilePath;

	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		for (Resource r : resources) {
			File file = r.getFile();
			String inputFilePath = file.getAbsolutePath();
			if (moveFilePath.charAt(moveFilePath.length() - 1) == '\\') {
				moveFile(inputFilePath, moveFilePath + file.getName());
			} else {
				moveFile(inputFilePath, moveFilePath + "\\" + file.getName());
			}

		}
		return RepeatStatus.FINISHED;
	}

	private static void moveFile(String src, String dest) {
		Path result = null;
		try {
			result = Files.move(Paths.get(src), Paths.get(dest));
		} catch (IOException e) {
			System.out.println("Exception while moving file: " + e.getMessage());
		}
		if (result != null) {
			System.out.println("File moved successfully.");
		} else {
			System.out.println("File movement failed.");
		}
	}

	public String getMoveFilePath() {
		return moveFilePath;
	}

	public void setMoveFilePath(String moveFilePath) {
		this.moveFilePath = moveFilePath;
	}

	public void setResources(Resource[] resources) {
		this.resources = resources;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(resources, "directory must be set");
	}
}