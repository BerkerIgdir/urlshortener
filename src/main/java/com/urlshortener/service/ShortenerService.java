package com.urlshortener.service;

import java.net.URI;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.urlshortener.pojo.URLInfo;
import com.urlshortener.repository.CacheRepo;
import com.urlshortener.repository.URLRepo;
import com.urlshortener.util.HashUtils;

@Service
public class ShortenerService {
    private final CacheRepo cacheRepo;
    public static final String BASE_SHORTENED_URL = "http://localhost:8080/";
    private final URLRepo repo;

    public ShortenerService (CacheRepo cacheRepo , URLRepo repo) {
        this.cacheRepo = cacheRepo;
        this.repo = repo;
    }

    public String processUrl(String urlToProcess){
        var shortUrlOptional = cacheRepo.getUrlFromCache (urlToProcess);
        //This one will be moved to a validation class.
        var uri = stringToURL (urlToProcess);
        var id = HashUtils.sha256Hasher (uri.toString ())
                .map (longId -> longId & Long.MAX_VALUE)
                .orElseThrow (RuntimeException::new);

        Supplier<Optional<String>> urlFromDbSupplier = () -> repo.findById (id).map (URLInfo::getShortUrl);

        return shortUrlOptional
                .or (urlFromDbSupplier)
                .orElseGet (newUrlSupplier (urlToProcess,id));
    }

    public Optional<URI> getLongURL(String hashedString){
        var shortUrl = BASE_SHORTENED_URL.concat (hashedString);
        var cacheCheck = cacheRepo.getUrlFromCache (shortUrl);
        Supplier<Optional<String>> shortToLongUrlSupplier = () -> repo.findByShortUrl (shortUrl).map (URLInfo::getShortUrl);

        return cacheCheck.or(shortToLongUrlSupplier).map (this::stringToURL);
    }

    private Supplier <String> newUrlSupplier (String urlToProcess, long id){
        return () -> {
            var resultString = HashUtils.base62Hasher (id);
            var urlEntityToSave = new URLInfo (id,resultString,urlToProcess);
            cacheRepo.saveToCache (urlEntityToSave);
            repo.insert (urlEntityToSave);
            return resultString;
        };
    }

    //Throws unchecked runtime exception.
    private URI stringToURL(String url){

        if(!url.startsWith ("https://")){
            url = new StringBuilder (url)
                    .insert (0,"https://")
                    .toString ();
        }

        return UriComponentsBuilder.fromHttpUrl (url)
                .build ()
                .toUri ();
    }

}
