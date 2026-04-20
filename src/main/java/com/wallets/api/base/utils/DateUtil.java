package com.wallets.api.base.utils;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private DateUtil() {
    }

    public final static String SMART_ISO_PATTERN = "[yyyyMMdd][yyyy-MM-dd][yyyy-DDD]['T'[HHmmss][HHmm][HH:mm:ss][HH:mm][.SSSSSSSSS][.SSSSSS][.SSS][.SS][.S]][OOOO][O][z][XXXXX][XXXX]['['VV']']";
    public final static DateTimeFormatter SMART_ISO_FORMATER = DateTimeFormatter.ofPattern(SMART_ISO_PATTERN);

    public static LocalDateTime now() {
        return LocalDateTime.now(Clock.systemUTC());
    }

    public static LocalDateTime dateTimeFrom(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    public static long toMillis(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
    }
}

