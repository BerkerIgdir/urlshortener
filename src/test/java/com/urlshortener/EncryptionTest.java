package com.urlshortener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.urlshortener.util.HashUtils;

@ExtendWith (SpringExtension.class)
class EncryptionTest {

    @Test
    void sessionBasedEncryptionTest(){
        var sessionId = "55436693-733e-450c-99dc-07ac5ac7c2c7";
        var linkToShorten = "www.linkedin.com";
        var sessionIdLong = HashUtils.sha256Hasher (sessionId).orElseThrow (RuntimeException::new);

    }
}
