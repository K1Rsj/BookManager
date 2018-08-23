package net.proselyte.bookmanager;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebMVCApplicationInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(Initializer.class);

        servletContext.addListener(new ContextLoaderListener(applicationContext));
        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", new DispatcherServlet(applicationContext));
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}