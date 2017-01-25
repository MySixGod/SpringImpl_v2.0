package com.lonton.core.io;

import java.io.File;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

public class XmlDocumentResource extends DocumentResource{

	public XmlDocumentResource(File file)
	{
		super(file);
	}

	public XmlDocumentResource(String path)
	{
		super(path);
	}
	
	public Document getDocument() throws Exception{
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(getFile());// 获得文档对象
		return document;
	}
	
}
