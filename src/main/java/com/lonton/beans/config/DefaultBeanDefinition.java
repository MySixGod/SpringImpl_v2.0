package com.lonton.beans.config;

/*
 * @author chenwentao
 * @since  2017-01-25
 */
public class DefaultBeanDefinition extends AbstractBeanDefinition {

    /*
     * (non-Javadoc)
     * @see com.lonton.beans.config.AbstractBeanDefinition#getDescription()
     */
    @Override
    public String getDescription() {
        return getBeanClass().getName();
    }

    public DefaultBeanDefinition() {

    }

}
