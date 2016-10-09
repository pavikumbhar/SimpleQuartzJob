/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavikumbhar.javaheart.quartz.jdbc;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 *
 * @author pavikumbhar
 */
public class CleanableSchedulerFactoryBean extends SchedulerFactoryBean {
    private boolean cleanupQuartzDbTables;  //enables or disables automatic clean up on start up
    private PlatformTransactionManager transactionManager;


    @Required
    public void setCleanupQuartzDbTables(boolean cleanupQuartzDbTables) {
        this.cleanupQuartzDbTables = cleanupQuartzDbTables;
    }

    @Override
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        super.setTransactionManager(transactionManager);
        this.transactionManager = transactionManager;
    }

    @Override
    protected Scheduler createScheduler(SchedulerFactory schedulerFactory, String schedulerName) throws SchedulerException {
        Scheduler scheduler = super.createScheduler(schedulerFactory, schedulerName);
        if (scheduler != null && cleanupQuartzDbTables) {
            cleanUpSchedulerInTransaction(scheduler);
        }
        return scheduler;
    }

    private void cleanUpSchedulerInTransaction(Scheduler scheduler) throws SchedulerException {
        TransactionStatus transactionStatus = null;
        if (this.transactionManager != null) {
            transactionStatus = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
        }

        try {
            //should delete all the data from the DB:
            scheduler.clear();
        }

        //Exception handling is copy-pasted from SchedulerAccessor.registerJobsAndTriggers():
        catch (Throwable ex) {
            if (transactionStatus != null) {
                try {
                    this.transactionManager.rollback(transactionStatus);
                }
                catch (TransactionException tex) {
                    System.out.println("Job registration exception overridden by rollback exception"+ ex);
                    throw tex;
                }
            }
            if (ex instanceof SchedulerException) {
                throw (SchedulerException) ex;
            }
            if (ex instanceof Exception) {
                throw new SchedulerException("Registration of jobs and triggers failed: " + ex.getMessage(), ex);
            }
            throw new SchedulerException("Registration of jobs and triggers failed: " + ex.getMessage());
        }

        if (transactionStatus != null) {
            this.transactionManager.commit(transactionStatus);
        }
    }
}

