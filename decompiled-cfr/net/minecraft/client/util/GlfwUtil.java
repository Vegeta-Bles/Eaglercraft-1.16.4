/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.system.MemoryUtil
 */
package net.minecraft.client.util;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

public class GlfwUtil {
    public static void makeJvmCrash() {
        MemoryUtil.memSet((long)0L, (int)0, (long)1L);
    }

    public static double getTime() {
        return GLFW.glfwGetTime();
    }
}

