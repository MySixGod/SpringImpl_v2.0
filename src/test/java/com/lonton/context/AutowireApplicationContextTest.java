package com.lonton.context;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lonton.bean.AutowiredBean;
import com.lonton.bean.ComponentBean;
import com.lonton.bean.DependComponentBean;
import com.lonton.beans.factory.DefaultListableBeanFactory;
import com.lonton.core.io.FileSystemResource;
import com.lonton.tools.Assert;

public class AutowireApplicationContextTest {

    private static Logger log = LoggerFactory.getLogger(AutowireApplicationContextTest.class);
    DefaultListableBeanFactory defaultListableBeanFactory;
    static {
        PropertyConfigurator.configure("log4j.properties");
    }

    @Test
    public void testAutowireApplicationContext() {
        // 注入一个resource
        FileSystemResource fsr = new FileSystemResource("src\\resource\\test.xml");
        try {
            AutowireApplicationContext aac = new AutowireApplicationContext(fsr);
            AutowiredBean autowiredbean=aac.getBean("autowiredbean", AutowiredBean.class);
            ComponentBean cb = aac.getBean("componentbean", ComponentBean.class);
            DependComponentBean dcb = aac.getBean("dependcomponentbean", DependComponentBean.class);
            log.info(cb.toString());
            log.info(dcb.toString());
            log.info(Assert.isNull(autowiredbean.getBeana().getFood().say()));
            log.info(autowiredbean.getBeana().getPeople().getFood().say());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
