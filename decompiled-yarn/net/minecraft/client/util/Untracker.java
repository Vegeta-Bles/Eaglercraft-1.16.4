package net.minecraft.client.util;

import com.mojang.blaze3d.platform.GLX;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.annotation.Nullable;
import org.lwjgl.system.Pointer;

public class Untracker {
   @Nullable
   private static final MethodHandle ALLOCATOR_UNTRACK = GLX.make(() -> {
      try {
         Lookup _snowman = MethodHandles.lookup();
         Class<?> _snowmanx = Class.forName("org.lwjgl.system.MemoryManage$DebugAllocator");
         Method _snowmanxx = _snowmanx.getDeclaredMethod("untrack", long.class);
         _snowmanxx.setAccessible(true);
         Field _snowmanxxx = Class.forName("org.lwjgl.system.MemoryUtil$LazyInit").getDeclaredField("ALLOCATOR");
         _snowmanxxx.setAccessible(true);
         Object _snowmanxxxx = _snowmanxxx.get(null);
         return _snowmanx.isInstance(_snowmanxxxx) ? _snowman.unreflect(_snowmanxx) : null;
      } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException | ClassNotFoundException var5) {
         throw new RuntimeException(var5);
      }
   });

   public static void untrack(long address) {
      if (ALLOCATOR_UNTRACK != null) {
         try {
            ALLOCATOR_UNTRACK.invoke((long)address);
         } catch (Throwable var3) {
            throw new RuntimeException(var3);
         }
      }
   }

   public static void untrack(Pointer pointer) {
      untrack(pointer.address());
   }
}
