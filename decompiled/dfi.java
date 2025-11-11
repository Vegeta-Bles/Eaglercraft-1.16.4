import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.lwjgl.system.MemoryUtil;

public class dfi {
   public static void a(dfh var0) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> {
            Pair<dfh.a, ByteBuffer> _snowman = _snowman.f();
            dfh.a _snowmanx = (dfh.a)_snowman.getFirst();
            a((ByteBuffer)_snowman.getSecond(), _snowmanx.c(), _snowmanx.a(), _snowmanx.b());
         });
      } else {
         Pair<dfh.a, ByteBuffer> _snowman = _snowman.f();
         dfh.a _snowmanx = (dfh.a)_snowman.getFirst();
         a((ByteBuffer)_snowman.getSecond(), _snowmanx.c(), _snowmanx.a(), _snowmanx.b());
      }
   }

   private static void a(ByteBuffer var0, int var1, dfr var2, int var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      ((Buffer)_snowman).clear();
      if (_snowman > 0) {
         _snowman.a(MemoryUtil.memAddress(_snowman));
         dem.f(_snowman, 0, _snowman);
         _snowman.d();
      }
   }
}
