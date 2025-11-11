import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.MemoryStack;

public interface dfq {
   Logger f = LogManager.getLogger();

   dfq a(double var1, double var3, double var5);

   dfq a(int var1, int var2, int var3, int var4);

   dfq a(float var1, float var2);

   dfq a(int var1, int var2);

   dfq b(int var1, int var2);

   dfq b(float var1, float var2, float var3);

   void d();

   default void a(
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      int var10,
      int var11,
      float var12,
      float var13,
      float var14
   ) {
      this.a((double)_snowman, (double)_snowman, (double)_snowman);
      this.a(_snowman, _snowman, _snowman, _snowman);
      this.a(_snowman, _snowman);
      this.b(_snowman);
      this.a(_snowman);
      this.b(_snowman, _snowman, _snowman);
      this.d();
   }

   default dfq a(float var1, float var2, float var3, float var4) {
      return this.a((int)(_snowman * 255.0F), (int)(_snowman * 255.0F), (int)(_snowman * 255.0F), (int)(_snowman * 255.0F));
   }

   default dfq a(int var1) {
      return this.b(_snowman & 65535, _snowman >> 16 & 65535);
   }

   default dfq b(int var1) {
      return this.a(_snowman & 65535, _snowman >> 16 & 65535);
   }

   default void a(dfm.a var1, eba var2, float var3, float var4, float var5, int var6, int var7) {
      this.a(_snowman, _snowman, new float[]{1.0F, 1.0F, 1.0F, 1.0F}, _snowman, _snowman, _snowman, new int[]{_snowman, _snowman, _snowman, _snowman}, _snowman, false);
   }

   default void a(dfm.a var1, eba var2, float[] var3, float var4, float var5, float var6, int[] var7, int var8, boolean var9) {
      int[] _snowman = _snowman.b();
      gr _snowmanx = _snowman.e().p();
      g _snowmanxx = new g((float)_snowmanx.u(), (float)_snowmanx.v(), (float)_snowmanx.w());
      b _snowmanxxx = _snowman.a();
      _snowmanxx.a(_snowman.b());
      int _snowmanxxxx = 8;
      int _snowmanxxxxx = _snowman.length / 8;
      MemoryStack _snowmanxxxxxx = MemoryStack.stackPush();
      Throwable var17 = null;

      try {
         ByteBuffer _snowmanxxxxxxx = _snowmanxxxxxx.malloc(dfk.h.b());
         IntBuffer _snowmanxxxxxxxx = _snowmanxxxxxxx.asIntBuffer();

         for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxxx++) {
            ((Buffer)_snowmanxxxxxxxx).clear();
            _snowmanxxxxxxxx.put(_snowman, _snowmanxxxxxxxxx * 8, 8);
            float _snowmanxxxxxxxxxx = _snowmanxxxxxxx.getFloat(0);
            float _snowmanxxxxxxxxxxx = _snowmanxxxxxxx.getFloat(4);
            float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx.getFloat(8);
            float _snowmanxxxxxxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxxx;
            if (_snowman) {
               float _snowmanxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxx.get(12) & 255) / 255.0F;
               float _snowmanxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxx.get(13) & 255) / 255.0F;
               float _snowmanxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxx.get(14) & 255) / 255.0F;
               _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx * _snowman[_snowmanxxxxxxxxx] * _snowman;
               _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx * _snowman[_snowmanxxxxxxxxx] * _snowman;
               _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx * _snowman[_snowmanxxxxxxxxx] * _snowman;
            } else {
               _snowmanxxxxxxxxxxxxx = _snowman[_snowmanxxxxxxxxx] * _snowman;
               _snowmanxxxxxxxxxxxxxx = _snowman[_snowmanxxxxxxxxx] * _snowman;
               _snowmanxxxxxxxxxxxxxxx = _snowman[_snowmanxxxxxxxxx] * _snowman;
            }

            int _snowmanxxxxxxxxxxxxxxxx = _snowman[_snowmanxxxxxxxxx];
            float _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx.getFloat(16);
            float _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx.getFloat(20);
            h _snowmanxxxxxxxxxxxxxxxxxxx = new h(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 1.0F);
            _snowmanxxxxxxxxxxxxxxxxxxx.a(_snowmanxxx);
            this.a(
               _snowmanxxxxxxxxxxxxxxxxxxx.a(),
               _snowmanxxxxxxxxxxxxxxxxxxx.b(),
               _snowmanxxxxxxxxxxxxxxxxxxx.c(),
               _snowmanxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxx,
               1.0F,
               _snowmanxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxx,
               _snowman,
               _snowmanxxxxxxxxxxxxxxxx,
               _snowmanxx.a(),
               _snowmanxx.b(),
               _snowmanxx.c()
            );
         }
      } catch (Throwable var38) {
         var17 = var38;
         throw var38;
      } finally {
         if (_snowmanxxxxxx != null) {
            if (var17 != null) {
               try {
                  _snowmanxxxxxx.close();
               } catch (Throwable var37) {
                  var17.addSuppressed(var37);
               }
            } else {
               _snowmanxxxxxx.close();
            }
         }
      }
   }

   default dfq a(b var1, float var2, float var3, float var4) {
      h _snowman = new h(_snowman, _snowman, _snowman, 1.0F);
      _snowman.a(_snowman);
      return this.a((double)_snowman.a(), (double)_snowman.b(), (double)_snowman.c());
   }

   default dfq a(a var1, float var2, float var3, float var4) {
      g _snowman = new g(_snowman, _snowman, _snowman);
      _snowman.a(_snowman);
      return this.b(_snowman.a(), _snowman.b(), _snowman.c());
   }
}
