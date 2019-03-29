package com.brightstar.trpgfate.component.staticly.uuid;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDHelper {

    public static UUID convertFromBytes(byte[] bytes) {
        if (bytes.length > 16) throw new IllegalArgumentException("Length of bytes is more than 16");
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong();
        long low = bb.getLong();
        return new UUID(high, low);
    }

    public static byte[] convertToBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
}
