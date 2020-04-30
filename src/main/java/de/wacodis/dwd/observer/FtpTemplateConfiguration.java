package de.wacodis.dwd.observer;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

@Log4j2
@Configuration
class FtpTemplateConfiguration {

    @Bean
    DefaultFtpSessionFactory defaultFtpSessionFactory(
            @Value("${ftp1.username}") String username,
            @Value("${ftp1.host}") String host,
            @Value("${ftp1.port}") int port) {
        DefaultFtpSessionFactory defaultFtpSessionFactory = new DefaultFtpSessionFactory();
        defaultFtpSessionFactory.setUsername(username);
        defaultFtpSessionFactory.setHost(host);
        defaultFtpSessionFactory.setPort(port);
        defaultFtpSessionFactory.setClientMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);
        return defaultFtpSessionFactory;
    }

    @Bean
    FtpRemoteFileTemplate ftpRemoteFileTemplate(DefaultFtpSessionFactory dsf) {
        return new FtpRemoteFileTemplate(dsf);
    }

    /**
     * This method uses the FtpRemoteFileTemplate to copy the content of one file in the FTP server and copies it to a
     * local directory.
     * @// TODO: 30/04/2020
     * Improve what the method returns
     */
    @Bean
    InitializingBean copyContentOfOneFile(FtpRemoteFileTemplate template) {
        return () -> template
                .execute(session -> {
                    File file = new File(new File(System.getProperty("user.home"), "Desktop"), "testfile.txt");
                    try (FileOutputStream fout = new FileOutputStream(file)) {
                        session.read("/climate_environment/CDC/Nutzungsbedingungen_German.txt", fout);
                    }
                    log.info("read " + file.getAbsolutePath());
                    return null;
                });
    }

    /**
     * This method reads the content in a directory on the remote FTP server and lists the folder namas in addition to
     * other information such as the flder size and its date of creation
     */
    @Bean
    InitializingBean initializingBean1(FtpRemoteFileTemplate template) {
        FTPFile[] list = template.list("/climate_environment/");
        template.execute(session -> session.listNames("/climate_environment/"));
        log.info("read " + list.toString());
        Arrays.stream(list).forEach(System.out::println);
        return null;
    }

    /**
     * This method lists the names of the folders availave in a directory on the remote FTP server
     */
    @Bean
    InitializingBean initializingBean2(FtpRemoteFileTemplate template) {
        return () -> template
                .execute(session -> {
                    String[] listnames = session.listNames("/climate_environment/");
                    Arrays.stream(listnames).forEach(System.out::println);
                    return null;
                });
    }
}
