package com.aykhan.streamapi.service;

import io.getstream.client.Client;
import io.getstream.core.http.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;

@Service
@Slf4j
@PropertySource("classpath:secret.properties")
public class AuthService {

    @Value(value = "stream.key")
    private String key;
    @Value(value = "stream.secret")
    private String secret;
    private Client client;

    @PostConstruct
    public void postConstruct() throws MalformedURLException {
        client = Client.builder(
                key,
                secret
        ).build();
    }

    public String login(String username) {
        Token token = client.frontendToken(username);
        return token.toString();
    }
}
