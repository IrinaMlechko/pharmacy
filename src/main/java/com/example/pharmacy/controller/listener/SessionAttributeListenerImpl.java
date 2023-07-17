package com.example.pharmacy.controller.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.example.pharmacy.command.constant.SessionAttributeName.CURRENT_PAGE;
import static com.example.pharmacy.command.constant.SessionAttributeName.USER_NAME;

@WebListener
public class SessionAttributeListenerImpl implements HttpSessionAttributeListener {
    static Logger logger = LogManager.getLogger();
    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        logger.log(Level.INFO, "Attribute added :" + sbe.getSession().getAttribute(USER_NAME));
        logger.log(Level.INFO, "Attribute added :" + sbe.getSession().getAttribute(CURRENT_PAGE));
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        logger.log(Level.INFO, "Attribute removed :" + sbe.getSession().getAttribute(USER_NAME));
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        logger.log(Level.INFO, "Attribute replaced :" + sbe.getSession().getAttribute(USER_NAME));
        logger.log(Level.INFO, "Attribute replaced :" + sbe.getSession().getAttribute(CURRENT_PAGE));
    }
}
