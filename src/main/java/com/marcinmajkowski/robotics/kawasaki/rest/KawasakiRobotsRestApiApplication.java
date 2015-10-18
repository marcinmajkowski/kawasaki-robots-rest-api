package com.marcinmajkowski.robotics.kawasaki.rest;

import com.marcinmajkowski.robotics.kawasaki.client.tcp.TcpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KawasakiRobotsRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(KawasakiRobotsRestApiApplication.class, args);
    }

    @Bean
    TcpClient tcpClient(@Value("${controller.hostname:localhost}") String hostname,
                        @Value("${controller.port:9105}") int port,
                        @Value("${controller.login:as}") String login) {
        return new TcpClient(hostname, port, login);
    }
}
