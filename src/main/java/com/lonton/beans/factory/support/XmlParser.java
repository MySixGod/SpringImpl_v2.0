package com.lonton.beans.factory.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lonton.beans.config.BeanDefinition;
import com.lonton.beans.config.DefaultBeanDefinition;
import com.lonton.exception.XmlConfigurationErrorException;

/*
 * @author chenwentao
 * @since  2017-01-25
 */
//解析方法，返回BeanDefinition對象集合
//改写XmlParser，解析xml返回的应该是beandefinition，而不是直接就生成了bean
//在获取了beandefinition之后，在通过beandefinition创建bean

public class XmlParser {

    private static Map<String, BeanDefinition> beanDefinitions = new HashMap<>();
    private static List<String> ComponentPackageNames = new ArrayList<String>();
    private static Logger logger = LoggerFactory.getLogger(XmlParser.class);

    public final static Map<String, BeanDefinition> parser(Document doc) throws Exception {
        Element root = doc.getRootElement();// 获得根节点
        @SuppressWarnings("unchecked")
        List<Element> list = root.getChildren();
        for (Element elements : list) {
            BeanDefinition beanDefinition = new DefaultBeanDefinition();
            String PackageName = elements.getAttributeValue("packagename");
            if (PackageName != null) {
                ComponentPackageNames.add(PackageName);
            }
            // 获取属性值，即为对象的名字
            String beanDefinitonName = elements.getAttributeValue("id");
            if(beanDefinitonName==null){
                continue;
            }
            // 在获取类的路径，在通过java反射获取类的类类型，在获取该类的对象
            String classpath = elements.getAttributeValue("class");
            Class<?> beanClass = null;
            if (classpath != null && beanDefinitonName != null) {
                beanClass = Class.forName(classpath);
                // 保存最初的对象
                beanDefinition.setBeanClass(beanClass);
            }
            // 获取子节点下的property节点
            @SuppressWarnings("unchecked")
            List<Element> eles = elements.getChildren("property");
            if (eles != null && eles.size() >= 1) {
                // 进行遍历
                for (Element e : eles) {
                    // 属性名
                    String proName = e.getAttributeValue("name");
                    // bean
                    String beanDepend = e.getAttributeValue("ref");
                    // 基本类型
                    String str = e.getAttributeValue("value");
                    // 注入基本类型属性
                    if (beanDepend == null && str != null) {
                        // 然后调用构造方法
                        String methodName = "set" + proName.substring(0, 1).toUpperCase() + proName.substring(1);
                        // 通过反射获取方法(注意：一个参数，也只能获取String基本类型进行注入)
                        Method method = beanClass.getMethod(methodName, str.getClass());
                        method.invoke(beanClass.newInstance(), str);
                        // TODO
                    }
                    // 需要注入bean
                    if (beanDepend != null && str == null) {
                        // 在这里我直接让生成的beandefinied持有depends就可以，正真的注入发生在createBean //TODO
                        beanDefinition.addDepend(beanDepend);
                    }
                    if ((beanDepend == null && str == null) || (beanDepend != null && str != null)) {
                        // 上个版本，如果bean创建顺序不对，则无法初始化bean
                        logger.error("请检查property元素配置的正确性，ref和value不能同时为空或者同时有值");
                        throw new XmlConfigurationErrorException("At XmlParser,请删除property元素或添加可用的属性值");
                    }
                }
            }
            beanDefinitions.put(beanDefinitonName, beanDefinition);
        }
        return beanDefinitions;
    }

    public static BeanDefinition getBeanDefinition(String name) {
        return beanDefinitions.get(name);
    }

    // 获取配置文件中的包名
    public static List<String> getComponentPackageNames() {
        return ComponentPackageNames;
    }
}
