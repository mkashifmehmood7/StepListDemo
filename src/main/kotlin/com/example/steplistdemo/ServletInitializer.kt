package com.example.steplistdemo

import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

/**
 * Enables deployment of this application as a WAR file on an external servlet container
 * (for example Tomcat), instead of only running as an executable JAR.
 *
 * [SpringBootServletInitializer] hooks the app into the servlet container lifecycle.
 */
class ServletInitializer : SpringBootServletInitializer() {

    /**
     * Tells the external container which Spring Boot application class to use as the source.
     *
     * [@Override] replaces the parent method so Spring Boot knows where to start configuration.
     */
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(StepListDemoApplication::class.java)
    }
}
