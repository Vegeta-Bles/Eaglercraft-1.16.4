/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;

public class GlDebugInfo {
    public static String getVendor() {
        return GlStateManager.getString(7936);
    }

    public static String getCpuInfo() {
        return GLX._getCpuInfo();
    }

    public static String getRenderer() {
        return GlStateManager.getString(7937);
    }

    public static String getVersion() {
        return GlStateManager.getString(7938);
    }
}

