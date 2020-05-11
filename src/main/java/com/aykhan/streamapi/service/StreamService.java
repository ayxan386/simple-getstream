package com.aykhan.streamapi.service;

import com.aykhan.streamapi.dto.stream.PostDTO;
import com.aykhan.streamapi.dto.stream.StreamAddDTO;
import com.aykhan.streamapi.security.UserPrincipal;
import io.getstream.client.Client;
import io.getstream.client.FlatFeed;
import io.getstream.core.exceptions.StreamException;
import io.getstream.core.models.Activity;
import io.getstream.core.options.Limit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@PropertySource("classpath:secret.properties")
public class StreamService {

    @Value(value = "${stream.key}")
    private String key;
    @Value(value = "${stream.secret}")
    private String secret;

    private Client client;

    @PostConstruct
    public void postConstruct() throws MalformedURLException {
        client = Client.builder(
                key,
                secret
        ).build();
    }

    private UserPrincipal getUser() {
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String add(StreamAddDTO dto) throws StreamException, ExecutionException, InterruptedException {
        UserPrincipal user = getUser();
        FlatFeed userFeed = client.flatFeed("user", user.getUsername());
        Activity activity = userFeed.addActivity(Activity
                .builder()
                .actor(user.getUsername())
                .verb("add")
                .extraField("message", dto.getMessage())
                .foreignID(dto.getForeignID())
                .object(dto.getForeignID())
                .build()).get();
        return activity.getID();
    }

    public String follow(String username) throws StreamException {
        UserPrincipal user = getUser();
        FlatFeed userFeed = client.flatFeed("user", user.getUsername());
        FlatFeed toFollow = client.flatFeed("user", "aykhan");
        userFeed.follow(toFollow).join();
        return "success";
    }

    public List<PostDTO> getPosts(int pageNumber, int pageSize) throws StreamException {
        UserPrincipal user = getUser();
        FlatFeed userFeed = client.flatFeed("user", user.getUsername());
        //, new Offset(pageNumber - 1)
        return userFeed
                .getActivities(new Limit(pageSize))
                .join()
                .stream()
                .map(activity -> PostDTO
                        .builder()
                        .actor(activity.getActor())
                        .foreignID(activity.getForeignID())
                        .message((String) activity.getExtra().get("message"))
                        .build())
                .collect(toList());
    }
}
