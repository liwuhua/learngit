/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yjdj.view.core.web.listener;

import javax.servlet.ServletContextEvent;
import org.springframework.web.util.Log4jConfigListener;

/**
 *
 * @author lenovo
 */
public class Log4jConfigListenerExt extends Log4jConfigListener{
    @Override
    public void contextInitialized(ServletContextEvent event) {

        //设置日志记录的路径，对应于log4j.properties中的$(log4j_path)
        System.setProperty("log4j_path",event.getServletContext().getRealPath("/"));
        super.contextInitialized(event);
    }    
}
