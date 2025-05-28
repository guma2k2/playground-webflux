package com.playground.webflux.sec04;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductUploadDownloadTest {

    private final static Logger log = LoggerFactory.getLogger(ProductUploadDownloadTest.class);

    private final ProductClient productClient = new ProductClient();

    @Test
    public void upload() {
        var flux = Flux.range(1, 1_000_000)
                .map(i -> new ProductDto(null, "product-" + i, i));

        this.productClient.uploadProducts(flux)
                .doOnNext(r -> log.info("received: {}", r))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }


    @Test
    public void download() {
        productClient.downloadProducts()
                .map(ProductDto::toString)
                .as(flux -> FileWriter.create(flux, Path.of("product.txt")))
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    void testFileWriterDeletesFileAfterTest() throws IOException {
        Path tempFile = Files.createTempFile("product", ".txt");
        Flux<String> flux = Flux.just("Hello", "World");

        StepVerifier.create(FileWriter.create(flux, tempFile))
                .verifyComplete();

        assertTrue(Files.exists(tempFile)); // File should exist after writing

        Files.deleteIfExists(tempFile); // Clean up
        assertFalse(Files.exists(tempFile)); // Verify it was deleted
    }
}
