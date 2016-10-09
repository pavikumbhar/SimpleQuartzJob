/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavikumbhar.javaheart.quartz.jdbc;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
 
public class SimpleQuartzJob implements Job {
    private String someParam;
    private int someParam2;
 
    public void setSomeParam(String someParam) {
        this.someParam = someParam;
    }
 
    public void setSomeParam2(int someParam2) {
        this.someParam2 = someParam2;
    }
 
    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        System.out.println("My job is running with " + someParam + ' '
                + someParam2);
 
    }
 
}