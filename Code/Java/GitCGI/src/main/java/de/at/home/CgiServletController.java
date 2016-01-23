package de.at.home;

import org.apache.catalina.servlets.CGIServlet;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Configuration
public class CgiServletController implements InitializingBean
{
    @Value("${cgiPathPrefix}")
    private String cgiPathPrefix;

    @Value("${cgiPathMapping:/*}")
    private String cgiPathMapping;

    @Bean
    public ServletRegistrationBean servletRegistrationBean()
    {
        ServletRegistrationBean result = new ServletRegistrationBean(new CGIServlet(), this.cgiPathMapping);

        result.addInitParameter("executable", "");
        result.addInitParameter("cgiPathPrefix", this.cgiPathPrefix);
        result.addInitParameter("parameterEncoding", StandardCharsets.UTF_8.displayName());
        result.addInitParameter("passShellEnvironment", "true");

        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        String gitProjectRoot = System.getenv("GIT_PROJECT_ROOT");
        String gitHttpExportAll = System.getenv("GIT_HTTP_EXPORT_ALL");

        if (gitProjectRoot == null) throw new IllegalStateException("Environment variable 'GIT_PROJECT_ROOT' not set!");
        if (gitHttpExportAll == null) throw new IllegalStateException("Environment variable 'GIT_HTTP_EXPORT_ALL' not set!");

        if (!(new File(gitProjectRoot)).isDirectory()) throw new IllegalStateException("Environment variable 'GIT_PROJECT_ROOT' is not valid!");
    }
}