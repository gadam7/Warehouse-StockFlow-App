package com.adamidis.learning.warehousestockflow.Service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class SkuService {
    private static final SecureRandom RNG = new SecureRandom();
    private static final char[] BASE36 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public String prefixFromName(String name) {
        String prefix = (name == null || name.isBlank())
                ? "CAT"
                : name.replaceAll("[^A-Za-z0-9]", "").toUpperCase();

        return (prefix + "XXX").substring(0, 3);
    }

    public String randomSuffix5() {
        char[] out = new char[5];
        for (int i = 0; i < 5; i++) out[i] = BASE36[RNG.nextInt(36)];
        return new String(out);
    }

    public String generateSku(String name) {
        return prefixFromName(name) + randomSuffix5();
    }
}