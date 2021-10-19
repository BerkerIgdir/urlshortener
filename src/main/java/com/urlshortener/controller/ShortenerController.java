package com.urlshortener.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urlshortener.dto.ShortenerRequestDTO;
import com.urlshortener.dto.ShortenerResponseDTO;
import com.urlshortener.pojo.URLInfo;
import com.urlshortener.service.ShortenerService;

@RestController
@RequestMapping("api/v1")
public class ShortenerController {
    private final ShortenerService service;

    public ShortenerController (ShortenerService service) {
        this.service = service;
    }

    @PostMapping(value = "/shorten", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortenerResponseDTO> shortenerEndpoint(@RequestBody ShortenerRequestDTO requestDTO){
        var responseDTO = new ShortenerResponseDTO (service.processUrl (requestDTO.getUrl ()));
        var cookie = ResponseCookie.from ("client","demoCookie");
        return ResponseEntity.status (HttpStatus.OK)
                .header ("cookie",cookie.build ().toString ())
                .body (responseDTO);
    }

}
