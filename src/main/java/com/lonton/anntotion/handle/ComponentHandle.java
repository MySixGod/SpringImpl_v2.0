package com.lonton.anntotion.handle;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lonton.beans.config.BeanDefinition;
import com.lonton.beans.config.DefaultBeanDefinition;
import com.lonton.exception.AnnotationBenaConfigurationErrorException;
import com.lonton.ioc.annotation.Autowired;
import com.lonton.ioc.annotation.Component;

/*
 * @author chenwentao
 * @since  2017-01-25
 */
public class ComponentHandle {
    /*
     * 将带有@Componnet注解的类注入到IOC容器，这里我应该返回beanDefinitions，而不是bean
     */
    private static Logger logger = LoggerFactory.getLogger(ComponentHandle.class);

    protected static Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    public static Map<String, BeanDefinition> getBeanDefinitionMap(List<Class<?>> ComponentClass) throws Exception {

        ComponentClass.forEach((beanClass) -> {
            // 处理class对象为空的情况
            if (beanClass == null) {
                try {
                    throw new AnnotationBenaConfigurationErrorException("在解析component注解的过程中，传入了空的Class对象！");
                } catch (AnnotationBenaConfigurationErrorException e) {
                    logger.error("在解析component注解的过程中，传入了空的Class对象！");
                }
            }
            Component component = beanClass.getAnnotation(Component.class);
            if (component != null) {
                BeanDefinition beanDefinition = new DefaultBeanDefinition();
                String beanName = null;
                try {
                    beanName = beanClass.getName().split("\\.")[beanClass.getName().split("\\.").length - 1];
                    if (beanClass != null && beanName != null) {
                        beanDefinition.setBeanClass(beanClass);
                        // 在获取了bean的名字和初始实例之后，我还需要获得bean的依赖
                        Method[] methods = beanClass.getDeclaredMethods();
                        // 如果没有
                        for (Method method : methods) {
                            Autowired autowured = method.getAnnotation(Autowired.class);
                            if (autowured != null) {
                                // 添加依赖的bean的名字
                                beanDefinition.addDepend(autowured.value());
                            }
                        }
                    } else {
                        throw new InstantiationException("注解解析异常");
                    }
                } catch (InstantiationException e) {
                    logger.error("无法通过空的构造方法获取bean实例！");
                }
                beanDefinitions.put(beanName, beanDefinition);
            }
        });
        return beanDefinitions;
    }

}
