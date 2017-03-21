package com.lonton.beans.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lonton.beans.config.BeanDefinition;
import com.lonton.beans.factory.support.DefaultSingletonBeanRegistry;
import com.lonton.exception.BeansException;
import com.lonton.exception.NoSuchBeanDefinitionException;

/*
 * @author chenwentao
 * @since  2017-01-25
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 创建完成bean池，我会将创建已完成的bean放入其中
    protected Map<String, Object> completedBeanPool = new HashMap<>();

    // 创建bean新生池，将正在创建的bean放入其中
    protected Map<String, Object> babyBeanPool = new HashMap<>();

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, Object.class);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return doGetBean(name, requiredType);
    }

    // 获得bean的实例
    @SuppressWarnings("unchecked")
    protected <T> T doGetBean(String name, Class<T> requiredType) {
        Object bean;
        // 首先我们急切的去单例池里面去找,如果单例池里面有
        if ((bean = getSingleton(name)) != null) {
            // 如果传入的requiredType==null || 不是所要求类型的实例
            if (requiredType == null || !requiredType.isInstance(bean)) {
                logger.error("传入的requiredType==null  ||  不是所要求类型的实例");
                throw new ClassCastException("类型转换错误");
            }
        } else {
            // 获得beanDefinition
            BeanDefinition beanDefinition = getBeanDefinition(name);
            if (beanDefinition == null)
                try {
                    throw new NoSuchBeanDefinitionException("");
                } catch (NoSuchBeanDefinitionException e1) {
                    logger.error("bean不存在");
                    return null;
                }
            /*
             * 在创建bean之前，我先得将它依赖的bean进行创建 1.获取beanDefintionName
             */
            List<String> depends = beanDefinition.getDepends();
            // 如果当前创建的bean是依赖于其他bean的
            if (depends != null && depends.size() >= 1) {
                for (String depend : depends) {
                    // 如果发现该bean的某些依赖不存在
                    if (!containsBeanDefintion(depend)) {
                        logger.warn(name + "may be you will create  a  incomplete bean");
                        // 直接跳过，进入下一次循环
                        continue;
                    } else {
                        // 存在该bean依赖的beanDefinition，我们必须先创建它所依赖的bean
                        try {
                            getBean(depend);
                        } catch (Exception e) {
                            logger.error("无法获取:" + name + "的依赖bean！");
                        }
                    }
                }
            }
            // 此时bean已经创建完毕
            bean = createBean(name, beanDefinition);
            // 放入完成池
            completedBeanPool.put(name, bean);
        }
        return (T) bean;
    }

    @Override
    public boolean containsBeanDefintion(String beanDefinitionName) {
        try {
            if (getBean(beanDefinitionName) != null) {
                return true;
            }
        } catch (BeansException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isSingleton(String beanDefinitionName) throws NoSuchBeanDefinitionException {
        if (!containsBeanDefintion(beanDefinitionName)) {
            throw new NoSuchBeanDefinitionException("容器中不存在该beanDefinition");
        } else {
            return getBeanDefinition(beanDefinitionName).isSingleton();
        }
    }

    /*
     * Create a bean instance for the given merged(混合的，模糊的) bean definition (and arguments)
     * 我这里不再需要给出参数值，基本类型的属性会在xmlparse解析的时候进行注入， 我这里的createBean 只处理bean之间的依赖关系
     */
    protected abstract Object createBean(String BeanName, BeanDefinition beanDefinition);

}
