package com.medicalcenterportal.storage;

public class StorageFileNotFoundException extends StorageException {

	private static final long serialVersionUID = -8848434569674049688L;

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
