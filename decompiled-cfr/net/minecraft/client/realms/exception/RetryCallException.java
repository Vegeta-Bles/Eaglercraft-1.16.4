/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.realms.exception;

import net.minecraft.client.realms.exception.RealmsServiceException;

public class RetryCallException
extends RealmsServiceException {
    public final int delaySeconds;

    public RetryCallException(int delaySeconds, int httpResultCode) {
        super(httpResultCode, "Retry operation", -1, "");
        this.delaySeconds = delaySeconds < 0 || delaySeconds > 120 ? 5 : delaySeconds;
    }
}

