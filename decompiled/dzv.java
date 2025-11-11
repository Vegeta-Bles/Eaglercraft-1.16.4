import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import javax.annotation.Nullable;

public abstract class dzv {
   private static final Object2ObjectMap<vk, dzv> a = x.a(new Object2ObjectArrayMap(), var0 -> {
      dzv.c _snowman = new dzv.c();
      var0.defaultReturnValue(_snowman);
      var0.put(chd.a, _snowman);
      var0.put(chd.b, new dzv.b());
      var0.put(chd.c, new dzv.a());
   });
   private final float[] b = new float[4];
   private final float c;
   private final boolean d;
   private final dzv.d e;
   private final boolean f;
   private final boolean g;

   public dzv(float var1, boolean var2, dzv.d var3, boolean var4, boolean var5) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
   }

   public static dzv a(chd var0) {
      return (dzv)a.get(_snowman.p());
   }

   @Nullable
   public float[] a(float var1, float var2) {
      float _snowman = 0.4F;
      float _snowmanx = afm.b(_snowman * (float) (Math.PI * 2)) - 0.0F;
      float _snowmanxx = -0.0F;
      if (_snowmanx >= -0.4F && _snowmanx <= 0.4F) {
         float _snowmanxxx = (_snowmanx - -0.0F) / 0.4F * 0.5F + 0.5F;
         float _snowmanxxxx = 1.0F - (1.0F - afm.a(_snowmanxxx * (float) Math.PI)) * 0.99F;
         _snowmanxxxx *= _snowmanxxxx;
         this.b[0] = _snowmanxxx * 0.3F + 0.7F;
         this.b[1] = _snowmanxxx * _snowmanxxx * 0.7F + 0.2F;
         this.b[2] = _snowmanxxx * _snowmanxxx * 0.0F + 0.2F;
         this.b[3] = _snowmanxxxx;
         return this.b;
      } else {
         return null;
      }
   }

   public float a() {
      return this.c;
   }

   public boolean b() {
      return this.d;
   }

   public abstract dcn a(dcn var1, float var2);

   public abstract boolean a(int var1, int var2);

   public dzv.d c() {
      return this.e;
   }

   public boolean d() {
      return this.f;
   }

   public boolean e() {
      return this.g;
   }

   public static class a extends dzv {
      public a() {
         super(Float.NaN, false, dzv.d.c, true, false);
      }

      @Override
      public dcn a(dcn var1, float var2) {
         return _snowman.a(0.15F);
      }

      @Override
      public boolean a(int var1, int var2) {
         return false;
      }

      @Nullable
      @Override
      public float[] a(float var1, float var2) {
         return null;
      }
   }

   public static class b extends dzv {
      public b() {
         super(Float.NaN, true, dzv.d.a, false, true);
      }

      @Override
      public dcn a(dcn var1, float var2) {
         return _snowman;
      }

      @Override
      public boolean a(int var1, int var2) {
         return true;
      }
   }

   public static class c extends dzv {
      public c() {
         super(128.0F, true, dzv.d.b, false, false);
      }

      @Override
      public dcn a(dcn var1, float var2) {
         return _snowman.d((double)(_snowman * 0.94F + 0.06F), (double)(_snowman * 0.94F + 0.06F), (double)(_snowman * 0.91F + 0.09F));
      }

      @Override
      public boolean a(int var1, int var2) {
         return false;
      }
   }

   public static enum d {
      a,
      b,
      c;

      private d() {
      }
   }
}
