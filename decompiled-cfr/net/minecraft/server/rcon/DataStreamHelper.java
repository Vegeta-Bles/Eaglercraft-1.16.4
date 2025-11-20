/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.rcon;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DataStreamHelper {
    private final ByteArrayOutputStream byteArrayOutputStream;
    private final DataOutputStream dataOutputStream;

    public DataStreamHelper(int n) {
        this.byteArrayOutputStream = new ByteArrayOutputStream(n);
        this.dataOutputStream = new DataOutputStream(this.byteArrayOutputStream);
    }

    public void write(byte[] byArray) throws IOException {
        this.dataOutputStream.write(byArray, 0, byArray.length);
    }

    public void writeBytes(String string) throws IOException {
        this.dataOutputStream.writeBytes(string);
        this.dataOutputStream.write(0);
    }

    public void write(int n) throws IOException {
        this.dataOutputStream.write(n);
    }

    public void writeShort(short s) throws IOException {
        this.dataOutputStream.writeShort(Short.reverseBytes(s));
    }

    public byte[] bytes() {
        return this.byteArrayOutputStream.toByteArray();
    }

    public void reset() {
        this.byteArrayOutputStream.reset();
    }
}

