package com.urlshortener.controller;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.urlshortener.service.ShortenerService;


@RestController
@RequestMapping("/")
public class RedirectController {

    private final ShortenerService service;

    public RedirectController (ShortenerService service) {
        this.service = service;
    }

    @GetMapping("/{shortenedHash}")
    public ResponseEntity<Void> redirectEndPoint(@PathVariable String shortenedHash) {
        return ResponseEntity.status (HttpStatus.PERMANENT_REDIRECT)
                .location (service.getLongURL (shortenedHash).orElseThrow (IllegalArgumentException::new))
                .build ();
    }
}
