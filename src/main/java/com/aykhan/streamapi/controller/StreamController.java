package com.aykhan.streamapi.controller;

import com.aykhan.streamapi.dto.stream.PostDTO;
import com.aykhan.streamapi.dto.stream.StreamAddDTO;
import com.aykhan.streamapi.service.StreamService;
import io.getstream.core.exceptions.StreamException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/streams")
public class StreamController {
    private final StreamService streamService;

    @PostMapping("/add")
    public String addTweet(@RequestBody StreamAddDTO streamAddDTO)
            throws StreamException, ExecutionException, InterruptedException {
        return streamService.add(streamAddDTO);
    }

    @PostMapping("/follow")
    public String followUser(@RequestBody String username) throws StreamException {
        return streamService.follow(username);
    }

    @GetMapping
    public List<PostDTO> getActivity(
            @RequestParam(value = "page", defaultValue = "1")int page,
            @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) throws StreamException {
        return streamService.getPosts(page, pageSize);
    }
}
