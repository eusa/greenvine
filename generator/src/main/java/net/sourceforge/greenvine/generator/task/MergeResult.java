package net.sourceforge.greenvine.generator.task;

import java.io.File;

public class MergeResult {
	
	private final String directory;
	private final String fileName;
	private final Exception exception;
	private final MergeStatus mergeStatus;




	public MergeResult(String directory, String fileName) {
		super();
		this.directory = directory;
		this.fileName = fileName;
		this.mergeStatus = MergeStatus.SUCCESS;
		this.exception = null;
	}




	public MergeResult(String directory, String fileName, Exception e) {
		super();
		this.directory = directory;
		this.fileName = fileName;
		this.mergeStatus = MergeStatus.FAILURE;
		this.exception = e;
	}
	
	

	public String getDirectory() {
		return directory;
	}


	public String getFileName() {
		return fileName;
	}




	public Exception getException() {
		return exception;
	}




	public MergeStatus getMergeStatus() {
		return mergeStatus;
	}

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		toString.append("File: " + this.directory + File.separatorChar + this.fileName);
		toString.append(", Status: " + this.mergeStatus);
		toString.append(", Exception: " + exception);
		return toString.toString();
	}	


	public enum MergeStatus { SUCCESS, FAILURE }
	
}
