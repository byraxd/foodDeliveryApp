package com.example.foodDeliveryApp.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidateUtils {
    public static void validateId(Long id) {
        log.info("validating id {}", id);
        if(id == null || id <= 0) {
            log.error("id is null or contains incorrect value");
            throw new IllegalArgumentException("id is null or contains incorrect value");
        }
    }

    public static void validateDto(Object objectDto){
        log.info("validating objectDto {}", objectDto);
        if(objectDto == null){
            log.error("objectDto is null");
            throw new IllegalArgumentException("objectDto is null");
        }
    }
}
