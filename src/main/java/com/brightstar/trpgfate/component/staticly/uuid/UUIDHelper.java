package com.brightstar.trpgfate.component.staticly.uuid;

import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.regex.Pattern;

public class UUIDHelper {

    public static UUID fromBytes(byte[] bytes) {
        if (bytes.length > 16) throw new IllegalArgumentException("Length of bytes is more than 16");
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong();
        long low = bb.getLong();
        return new UUID(high, low);
    }

    public static byte[] toBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static boolean isUUIDString(String uuid) {
        Pattern pattern = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
        return pattern.matcher(uuid).matches();
    }
}
