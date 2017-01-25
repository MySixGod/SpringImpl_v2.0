package com.lonton.core.io;

public class DefaultResourceLoader implements ResourceLoader{

	/*
	 * (non-Javadoc)
	 * @see com.lonton.core.io.ResourceLoader#getResource(java.lang.String)
	 * 默认获取资源的方式：Spring官方返回的是一个ClassPathContextResource，在这里，我统一使用
	 * 从文件系统中获取Resource的方式
	 */
	@Override
	public Resource getResource(String path){
		return new FileSystemResource(path);
	}
	
	
}
