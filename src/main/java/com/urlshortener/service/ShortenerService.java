package com.urlshortener.service;

import java.net.URI;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.urlshortener.business.UrlSupplier;
import com.urlshortener.pojo.URLInfo;
import com.urlshortener.repository.CacheRepo;
import com.urlshortener.repository.URLRepo;
import com.urlshortener.util.HashUtils;

@Service
public class ShortenerService {
    private final CacheRepo cacheRepo;
    public static final String BASE_SHORTENED_URL = "http://localhost:8080/";
    private final URLRepo repo;

    private final UrlSupplier supplyFromCache ;
    private final UrlSupplier supplyFromDB ;
    private final UrlSupplier supplyFromBusinessLogic = this::processBusinessLogic;

    public ShortenerService (CacheRepo cacheRepo , URLRepo repo) {
        this.cacheRepo = cacheRepo;
        this.repo = repo;
        this.supplyFromCache = cacheRepo::getUrlFromCache;
        this.supplyFromDB = s -> repo.findByShortUrl (s).map (URLInfo::getShortUrl);
    }

    public String processUrl(final String urlToProcess, final String sessionId){

        return supplyFromCache.getShortenedUrl (urlToProcess)
                .or(() -> supplyFromDB.getShortenedUrl (urlToProcess))
                .or (() -> supplyFromBusinessLogic.getShortenedUrl (urlToProcess))
                .orElseThrow (RuntimeException::new);
    }

    private Optional<String> processBusinessLogic (String urlToProcess) {
        //This one will be moved to a validation class.
        var uri = stringToURL (urlToProcess);
        var id = HashUtils.sha256Hasher (uri.toString ())
                .map (longId -> longId & Long.MAX_VALUE)
                .orElseThrow (RuntimeException::new);
        return Optional.of (newUrlSupplier (urlToProcess,id));
    }

    public Optional<URI> getLongURL(String hashedString){
        var shortUrl = BASE_SHORTENED_URL.concat (hashedString);
        var cacheCheck = cacheRepo.getUrlFromCache (shortUrl);
        Supplier<Optional<String>> shortToLongUrlSupplier = () -> repo.findByShortUrl (shortUrl).map (URLInfo::getShortUrl);

        return cacheCheck.or(shortToLongUrlSupplier).map (this::stringToURL);
    }

    private String newUrlSupplier (String urlToProcess, long id){
            var resultString = HashUtils.base62Hasher (id);
            var urlEntityToSave = new URLInfo (id,resultString,urlToProcess);
            cacheRepo.saveToCache (urlEntityToSave);
            repo.insert (urlEntityToSave);

            return resultString;
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
