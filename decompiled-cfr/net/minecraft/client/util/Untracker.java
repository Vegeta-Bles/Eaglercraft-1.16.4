/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.lwjgl.system.Pointer
 */
package net.minecraft.client.util;

import com.mojang.blaze3d.platform.GLX;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.annotation.Nullable;
import org.lwjgl.system.Pointer;

public class Untracker {
    @Nullable
    private static final MethodHandle ALLOCATOR_UNTRACK = GLX.make(() -> {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            Class<?> _snowman2 = Class.forName("org.lwjgl.system.MemoryManage$DebugAllocator");
            Method _snowman3 = _snowman2.getDeclaredMethod("untrack", Long.TYPE);
            _snowman3.setAccessible(true);
            Field _snowman4 = Class.forName("org.lwjgl.system.MemoryUtil$LazyInit").getDeclaredField("ALLOCATOR");
            _snowman4.setAccessible(true);
            Object _snowman5 = _snowman4.get(null);
            if (_snowman2.isInstance(_snowman5)) {
                return lookup.unreflect(_snowman3);
            }
            return null;
        }
        catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    });

    public static void untrack(long address) {
        if (ALLOCATOR_UNTRACK == null) {
            return;
        }
        try {
            ALLOCATOR_UNTRACK.invoke(address);
        }
        catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public static void untrack(Pointer pointer) {
        Untracker.untrack(pointer.address());
    }
}

