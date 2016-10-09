/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavikumbhar.javaheart.quartz.jdbc;

import org.quartz.spi.TriggerFiredBundle;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 *
 * @author pavikumbhar
 */
public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory
        implements ApplicationContextAware {
 
    private transient AutowireCapableBeanFactory beanFactory;
 
    @Override
    public void setApplicationContext(final ApplicationContext context) {
        beanFactory = context.getAutowireCapableBeanFactory();
    }
 
    @Override
    public Object createJobInstance(final TriggerFiredBundle bundle)
            throws Exception {
        final Object job = super.createJobInstance(bundle);
        beanFactory.autowireBean(job); // the magic is done here
        return job;
    }

  
}
