package spring.SpringImpl;

import com.lonton.bean.BeanA;
import com.lonton.beans.factory.support.XmlParser;

public class test{
	
	public static void main(String[] args) {
		
		Object object=new BeanA();
		
		String BenaName=
				object.getClass().getName().split("\\.")[(object.getClass().getName().split("\\.").length)-1];	
		System.out.println(object.getClass().getName().split("\\.").length);
		System.out.println(BenaName);
		
		
		XmlParser.getComponentPackageNames();
		
	}
	
}
