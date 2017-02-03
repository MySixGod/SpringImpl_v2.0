package com.lonton.anntotion.handle;

import java.lang.reflect.Method;

import com.lonton.beans.factory.BeanFactory;
import com.lonton.ioc.annotation.Autowired;

/*
 * @author chenwentao
 * @since  2017-01-25
 */
public class AutowiredHandle {
	/*
	 * @param AutowiredClasss:注解的类类型
	 * 
	 * @BeanName 带有@Autowired注解的类的名字
	 */
	public static void AutowiredHandleMethod(Class<?> AutowiredClass,
			BeanFactory beanFactory, String beanName) throws Exception {
		// 遍历AutowiredClass的方法
		for (Method m : AutowiredClass.getDeclaredMethods()) {
			Autowired t = m.getAnnotation(Autowired.class);
			if (t != null) {
				// 有的话我们就将bean进行注入
				String proName = t.value();
				// 获取注入bean
				Object bean = beanFactory.getBean(t.value());
				System.out.println(t.value() + bean);
				String methodName = "set"
						+ proName.substring(0, 1).toUpperCase()
						+ proName.substring(1);
				// System.out.println("methodName:" + methodName);
				Class<?> cl = beanFactory.getBean(beanName).getClass();
				// 通过反射获取方法
				Method method = cl.getMethod(methodName, bean.getClass());
				if (method != null) {
					method.invoke(beanFactory.getBean(beanName), bean);
				} else {
					System.out.println("方法为空！");
				}
			}
		}
	}

}
