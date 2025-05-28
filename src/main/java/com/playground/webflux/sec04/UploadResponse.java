package com.playground.webflux.sec04;

import java.util.UUID;

public record UploadResponse(UUID confirmationId,
                             Long productsCount) {
}