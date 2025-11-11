import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class btk extends bsy {
   public static final Codec<btk> e = RecordCodecBuilder.create(
      var0 -> var0.group(vg.a(gm.ay).forGetter(var0x -> var0x.g), Codec.LONG.fieldOf("seed").stable().forGetter(var0x -> var0x.h))
            .apply(var0, var0.stable(btk::new))
   );
   private final cud f;
   private final gm<bsv> g;
   private final long h;
   private final bsv i;
   private final bsv j;
   private final bsv k;
   private final bsv l;
   private final bsv m;

   public btk(gm<bsv> var1, long var2) {
      this(_snowman, _snowman, _snowman.d(btb.j), _snowman.d(btb.Q), _snowman.d(btb.P), _snowman.d(btb.O), _snowman.d(btb.R));
   }

   private btk(gm<bsv> var1, long var2, bsv var4, bsv var5, bsv var6, bsv var7, bsv var8) {
      super(ImmutableList.of(_snowman, _snowman, _snowman, _snowman, _snowman));
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.m = _snowman;
      chx _snowman = new chx(_snowman);
      _snowman.a(17292);
      this.f = new cud(_snowman);
   }

   @Override
   protected Codec<? extends bsy> a() {
      return e;
   }

   @Override
   public bsy a(long var1) {
      return new btk(this.g, _snowman, this.i, this.j, this.k, this.l, this.m);
   }

   @Override
   public bsv b(int var1, int var2, int var3) {
      int _snowman = _snowman >> 2;
      int _snowmanx = _snowman >> 2;
      if ((long)_snowman * (long)_snowman + (long)_snowmanx * (long)_snowmanx <= 4096L) {
         return this.i;
      } else {
         float _snowmanxx = a(this.f, _snowman * 2 + 1, _snowmanx * 2 + 1);
         if (_snowmanxx > 40.0F) {
            return this.j;
         } else if (_snowmanxx >= 0.0F) {
            return this.k;
         } else {
            return _snowmanxx < -20.0F ? this.l : this.m;
         }
      }
   }

   public boolean b(long var1) {
      return this.h == _snowman;
   }

   public static float a(cud var0, int var1, int var2) {
      int _snowman = _snowman / 2;
      int _snowmanx = _snowman / 2;
      int _snowmanxx = _snowman % 2;
      int _snowmanxxx = _snowman % 2;
      float _snowmanxxxx = 100.0F - afm.c((float)(_snowman * _snowman + _snowman * _snowman)) * 8.0F;
      _snowmanxxxx = afm.a(_snowmanxxxx, -100.0F, 80.0F);

      for (int _snowmanxxxxx = -12; _snowmanxxxxx <= 12; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = -12; _snowmanxxxxxx <= 12; _snowmanxxxxxx++) {
            long _snowmanxxxxxxx = (long)(_snowman + _snowmanxxxxx);
            long _snowmanxxxxxxxx = (long)(_snowmanx + _snowmanxxxxxx);
            if (_snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx > 4096L && _snowman.a((double)_snowmanxxxxxxx, (double)_snowmanxxxxxxxx) < -0.9F) {
               float _snowmanxxxxxxxxx = (afm.e((float)_snowmanxxxxxxx) * 3439.0F + afm.e((float)_snowmanxxxxxxxx) * 147.0F) % 13.0F + 9.0F;
               float _snowmanxxxxxxxxxx = (float)(_snowmanxx - _snowmanxxxxx * 2);
               float _snowmanxxxxxxxxxxx = (float)(_snowmanxxx - _snowmanxxxxxx * 2);
               float _snowmanxxxxxxxxxxxx = 100.0F - afm.c(_snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx) * _snowmanxxxxxxxxx;
               _snowmanxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxx, -100.0F, 80.0F);
               _snowmanxxxx = Math.max(_snowmanxxxx, _snowmanxxxxxxxxxxxx);
            }
         }
      }

      return _snowmanxxxx;
   }
}
