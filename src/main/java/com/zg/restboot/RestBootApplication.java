package com.zg.restboot;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class RestBootApplication{

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(RestBootApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String contextPath = Strings.nullToEmpty(env.getProperty("server.servlet.context-path"));
        log.info("\n----------------------------------------------------------\n" +
                "Application running at:\n" +
                "- Local:    http://localhost:" + port + contextPath + "/\n" +
                "- Network:  http://" + ip + ":" + port + contextPath + "/\n" +
                "- Api-docs: http://" + ip + ":" + port + contextPath + "/v3/api-docs\n" +
                "- Api-ui:   http://" + ip + ":" + port + contextPath + "/swagger-ui/\n" +
                "----------------------------------------------------------");
    }
}
