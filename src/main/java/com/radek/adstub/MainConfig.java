package com.radek.adstub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.ldap.server.ApacheDSContainer;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

@Configuration
public class MainConfig {

    static {
        //System.setProperty("ldap.port", Integer.toString(port));
        //System.setProperty("ldap.sslEnabled", "true");
    }

    @Bean(destroyMethod = "stop")
    public ApacheDSContainer apacheDSContainer() throws Exception {
        ApacheDSContainer apacheDSContainer = new ApacheDSContainer("dc=springframework,dc=org", "classpath:test-server.ldif");
        apacheDSContainer.setPort(636);
        apacheDSContainer.setKeyStoreFile(getFileFromResources("spring.keystore"));
        apacheDSContainer.setCertificatePassord("changeit");
        apacheDSContainer.setLdapOverSslEnabled(true);

        apacheDSContainer.afterPropertiesSet();
        apacheDSContainer.start();

        return apacheDSContainer;
    }

    private File getFileFromResources(final String name) throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource(name);
        return Paths.get(resource.toURI()).toFile();
    }
}
