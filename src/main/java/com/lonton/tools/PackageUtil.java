package com.lonton.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
/*
 * @author cwt
 * @since  2017-02-03
 * @description  通过包名返回该包下所有的类名
 */
public class PackageUtil {

	public static List<String> getClassName(String packageName) {
		
		String filePath ="test\\java\\"+packageName.replace(".", "\\");
		List<String> fileNames = getClassName(filePath, null);
		return fileNames;	
	}

	private static List<String> getClassName(String filePath,
			List<String> className) {
		
		List<String> myClassName = new ArrayList<String>();
		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		for (File childFile : childFiles) {
			if (childFile.isDirectory()) {
				myClassName.addAll(getClassName(childFile.getPath(),
						myClassName));
			} else {
				String childFilePath = childFile.getPath();
				childFilePath = childFilePath.substring(
						childFilePath.indexOf("\\classes") + 9,
						childFilePath.lastIndexOf("."));
				childFilePath = childFilePath.replace("\\", ".");
				childFilePath=childFilePath.substring(6,childFilePath.length());
				myClassName.add("com."+childFilePath);
			}
		}
		return myClassName;
	}
}
