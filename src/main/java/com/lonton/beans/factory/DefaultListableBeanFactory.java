package com.lonton.beans.factory;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lonton.beans.config.BeanDefinition;
import com.lonton.beans.factory.support.BeanDefinitionRegistry;
import com.lonton.core.io.FileSystemResource;
import com.lonton.core.io.Resource;
import com.lonton.core.io.XmlBeanDefinitionReader;
import com.lonton.enums.BasicType;
import com.lonton.exception.CircularDependException;
import com.lonton.exception.XmlConfigurationErrorException;

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
        implements BeanDefinitionRegistry, ResourceLoaderBeanFactory ,ListableBeanFactory{

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
                // 在这里分两种情况，先确定需要注入的是基本类型还是bean
                if (isBaiscType(depend)) {
                    // 注入基本属性
                    try {
                        bean = beanBasicTypeAutowired(bean, depend);
                    } catch (Exception e) {
                        // 不做任何处理
                    }
                } else {
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
                        log.error("获取到了set方法，没有能获取到需要注入的bean:" + depend);
                    }
                }

            }
        }
        return bean;
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

    private boolean isBaiscType(String depend) {
        return depend.charAt(0) == '.';
    }

    private Object invokeMethod(Object bean, String methodName, Class<?> parameterTypes, Object args) {
        try {
            Method method = bean.getClass().getMethod(methodName, parameterTypes);
            method.invoke(bean, args);
        } catch (Exception e) {
            log.error("基本类型注入时方法调用错误，可能原因：属性名配置错误，类型不匹配\n"+
        "方法名："+methodName+"参数："+args);
        }
        return null;
    }

    private Object beanBasicTypeAutowired(Object bean, String depend) throws XmlConfigurationErrorException {
        // 将字符串进行分割解析 name+type+value
        String realDepend = depend.substring(1);
        String[] values = realDepend.split("\\+");
        String name = values[0];
        String type = values[1];
        String value = values[2];
        // 我先需要先创建一个基本类型的对象 TODO
        String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        try {
            Class<?> typeClss = Class.forName(type);
            String typeName = typeClss.getSimpleName();
            if (typeName.equals(BasicType.Boolean.simpleTypeName)) {
                if (Objects.equals(value, "true") || Objects.equals(value, "1")) {
                    Boolean bool = true;
                    invokeMethod(bean, methodName, Boolean.class, bool);
                } else if (Objects.equals(value, "false") || Objects.equals(value, "0")) {
                    Boolean bool = false;
                    invokeMethod(bean, methodName, Boolean.class, bool);
                } else {
                    throw new XmlConfigurationErrorException("xml配置的属性值和其类型不匹配！");
                }
            } else if (typeName.equals(BasicType.String.simpleTypeName)) {
                String str = value;
                invokeMethod(bean, methodName, String.class, str);
            } else if (typeName.equals(BasicType.Long.simpleTypeName)) {
                Long l = Long.parseLong(value);
                invokeMethod(bean, methodName, Long.class, l);
            } else if (typeName.equals(BasicType.Character.simpleTypeName)) {
                // 先判断下value的长度
                if (value.length() > 1) {
                    throw new XmlConfigurationErrorException("xml配置的属性值和其类型不匹配！");
                }
                char ch = value.charAt(0);
                invokeMethod(bean, methodName, Character.class, ch);
            } else if (typeName.equals(BasicType.Integer.simpleTypeName)) {
                Integer i = Integer.parseInt(value);
                invokeMethod(bean, methodName, Integer.class, i);
            } else if (typeName.equals(BasicType.Byte.simpleTypeName)) {
                Byte b = value.getBytes()[0];
                invokeMethod(bean, methodName, Byte.class, b);
            } else if (typeName.equals(BasicType.Float.simpleTypeName)) {
                Float f = Float.parseFloat(value);
                invokeMethod(bean, methodName, Float.class, f);
            } else if (typeName.equals(BasicType.Double.simpleTypeName)) {
                Double d = Double.parseDouble(value);
                invokeMethod(bean, methodName, Double.class, d);
            } else {
                throw new XmlConfigurationErrorException("beanName：" + bean.getClass().getSimpleName() + "的类型配置错误");
            }
        } catch (ClassNotFoundException e) {
            log.error("错误的基本类型：" + type);
        }
        return bean;
    }
}
