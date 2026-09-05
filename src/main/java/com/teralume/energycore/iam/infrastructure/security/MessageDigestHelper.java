package com.teralume.energycore.iam.infrastructure.security;

import java.security.MessageDigest;

final class MessageDigestHelper {

    private MessageDigestHelper() {
    }

    static boolean constantTimeEquals(byte[] left, byte[] right) {
        return MessageDigest.isEqual(left, right);
    }
}
