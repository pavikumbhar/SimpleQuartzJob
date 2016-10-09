/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pavikumbhar
 */
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
public class Test {
public static void main(String[] args) {
    ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-quartz-jdbc.xml");
 
}
}