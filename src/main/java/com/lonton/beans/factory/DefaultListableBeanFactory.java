package com.lonton.beans.factory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lonton.beans.config.BeanDefinition;
import com.lonton.beans.factory.support.BeanDefinitionRegistry;
import com.lonton.core.io.FileSystemResource;
import com.lonton.core.io.Resource;
import com.lonton.core.io.XmlBeanDefinitionReader;
import com.lonton.exception.CircularDependException;

/*
 * @author chenwentao
 * @since  2017-01-25
 * 
 * 1.一個基本的容器实现,我這裡簡單實現，直接繼承AbstractBeanFactory
 * 这个工厂和XmlBeanDefinitionReader是联系在一起的，当调用XmlBeanDefinitionReader类
 * 中的loadBeanDefinitions()方法时，会调用registerBeanDefinition()方法，讲beandefinition
 * 注入到DefaultListableBeanFactory，后面我在拓展工厂的时候，只需要继承DefaultListableBeanFactory
 * 就拥有了完整的beandefinition集合
 * 3.这里我们只能加载FileSystemResource，如需拓展，继承此类就可以
 * 
 */
public class DefaultListableBeanFactory extends AbstractBeanFactory
        implements BeanDefinitionRegistry, ResourceLoaderBeanFactory {

    private static Logger log = LoggerFactory.getLogger(DefaultListableBeanFactory.class);
    protected Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(256);
    protected Resource resource;
    static {
        PropertyConfigurator.configure("log4j.properties");
    }

    /*
     * 当然了，我们所使用的ioc都是具有加载资源的能力的,所以给定两个基本的构造方法 你可以给工厂直接注入资源，当然也可以直接注入资源地址，因为它是具备资源加载的能力的
     */
    public DefaultListableBeanFactory(Resource resource) throws Exception {
        this.resource = resource;
        refresh();
    }

    public DefaultListableBeanFactory(String location) throws Exception {
        this.resource = getResource(location);
        refresh();
    }

    /*
     * 资源的问题解决了，我们还得具备读取beandefinition的能力,本来是应该继承的容器中就具有了 读取的能力，这里为了简便，我们使用内部类实现
     */
    protected class ResourceReaderBeanFactory extends XmlBeanDefinitionReader {
        public ResourceReaderBeanFactory(BeanDefinitionRegistry registry) {
            super(registry);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.lonton.beans.factory.support.BeanDefinitionRegistry#registerBeanDefinition(java.lang.String,
     * com.lonton.beans.config.BeanDefinition) 在这里我们已经获取到了beanName and beanDefinition，只需将其注入到Map集合中就行了
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        // 这里我就不对这两个参数进行验证了，直接将其put进map集合
        // System.out.println("正在注册"+beanName+beanDefinition);
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String beanName) {
        if (beanDefinitionMap.get(beanName) != null) {
            beanDefinitionMap.remove(beanName);
        } else {
            log.warn("需要移除的bean不存在！");
        }
    }

    // 给定一个地址
    @Override
    public Resource getResource(String location) {
        // 这里因为是通过地址获取资源，所以我们返回一个FileSystemResource
        return new FileSystemResource(location);
    }

    // 好了，最后给定一个初始化方法
    protected void refresh() throws Exception {
        // 在这里，我们完成容器的初始化
        // 1.资源我们已经在构造方法中获取
        // 2.资源的解析
        int count = new ResourceReaderBeanFactory(this).loadBeanDefinitions(resource);
        // 3.容器的注册方法会被自动调用，此时注册就完成了
        log.info("一共初注册了" + count + "个beanDefinition");
    }

    @Override
    protected Object createBean(String beanNmae, BeanDefinition beanDefinition) throws CircularDependException {
        // 如何通过beanDefinition获得一个完整的bean实例（我们已经获取了bean的依赖集合）
        // 当createBean的时候，它所依赖的bean一定已经创建完成了，并且已经放入了完成池
        // 反射获取方法，进行bean的注入
        List<String> depends = beanDefinition.getDepends();
        Class<?> cl = beanDefinition.getBeanClass();
        Object bean = null;
        try {
            // 通过反射生成bean的实例
            bean = cl.newInstance();
        } catch (Exception e1) {
            log.error("反射异常");
        }
        if (depends != null && depends.size() >= 1) {
            // 此时的bean还不完整，还没有注入它的依赖，我们将它放入新生池
            babyBeanPool.put(beanNmae, bean);
            for (String depend : depends) {
                if (babyBeanPool.get(depend) != null) {
                    log.error("beanDefinition中存在循环依赖");
                    throw new CircularDependException();
                }
                String methodName = "set" + depend.substring(0, 1).toUpperCase() + depend.substring(1);
                // 获取bean的class对象
                // 通过反射获取方法
                try {
                    Method method = cl.getMethod(methodName, completedBeanPool.get(depend).getClass());
                    // 调用set方法完成注入
                    method.invoke(bean, completedBeanPool.get(depend));
                } catch (NoSuchMethodException e) {
                    log.error("需要获取得bean中没有" + methodName + "方法");
                } catch (Exception e) {
                    log.error("获取到了set方法，没有能获取到需要注入的bean！");
                }
            }
            return bean;
        } else {
            // 如果是一个没有依赖的bean
            return bean;
        }
    }

    // 通过name获取beanDefinition
    @Override
    public BeanDefinition getBeanDefinition(String beanDefinitionName) {
        return beanDefinitionMap.get(beanDefinitionName);
    }

    @Override
    public boolean containsBeanDefintion(String beanDefinitionName) {
        return beanDefinitionMap.get(beanDefinitionName) != null;
    }

}
