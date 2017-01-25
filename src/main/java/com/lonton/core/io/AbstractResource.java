package com.lonton.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractResource implements Resource{

	@Override
	public boolean exists() {
		// Try file existence: can we find the file in the file system?
		try {
			return getFile().exists();
		}
		catch (IOException ex) {
			// Fall back to stream existence: can we open the stream?
			try {
				InputStream is = getInputStream();
				is.close();
				return true;
			}
			catch (Throwable isEx) {
				return false;
			}
		}
	}

	@Override
	public File getFile() throws IOException {
		throw new FileNotFoundException(getDescription() + " cannot be resolved to absolute file path");
	}

	@Override
	public String toString() {
		return getDescription();
	}

}
