import javax.annotation.Nullable;

public class ebh {
   private static final float a = 1.0F / (float)Math.cos((float) (Math.PI / 8)) - 1.0F;
   private static final float b = 1.0F / (float)Math.cos((float) (Math.PI / 4)) - 1.0F;

   public ebh() {
   }

   public eba a(g var1, g var2, ebc var3, ekc var4, gc var5, elv var6, @Nullable ebd var7, boolean var8, vk var9) {
      ebe _snowman = _snowman.d;
      if (_snowman.c()) {
         _snowman = a(_snowman.d, _snowman, _snowman.b(), _snowman);
      }

      float[] _snowmanx = new float[_snowman.a.length];
      System.arraycopy(_snowman.a, 0, _snowmanx, 0, _snowmanx.length);
      float _snowmanxx = _snowman.p();
      float _snowmanxxx = (_snowman.a[0] + _snowman.a[0] + _snowman.a[2] + _snowman.a[2]) / 4.0F;
      float _snowmanxxxx = (_snowman.a[1] + _snowman.a[1] + _snowman.a[3] + _snowman.a[3]) / 4.0F;
      _snowman.a[0] = afm.g(_snowmanxx, _snowman.a[0], _snowmanxxx);
      _snowman.a[2] = afm.g(_snowmanxx, _snowman.a[2], _snowmanxxx);
      _snowman.a[1] = afm.g(_snowmanxx, _snowman.a[1], _snowmanxxxx);
      _snowman.a[3] = afm.g(_snowmanxx, _snowman.a[3], _snowmanxxxx);
      int[] _snowmanxxxxx = this.a(_snowman, _snowman, _snowman, this.a(_snowman, _snowman), _snowman.b(), _snowman, _snowman);
      gc _snowmanxxxxxx = a(_snowmanxxxxx);
      System.arraycopy(_snowmanx, 0, _snowman.a, 0, _snowmanx.length);
      if (_snowman == null) {
         this.a(_snowmanxxxxx, _snowmanxxxxxx);
      }

      return new eba(_snowmanxxxxx, _snowman.b, _snowmanxxxxxx, _snowman, _snowman);
   }

   public static ebe a(ebe var0, gc var1, f var2, vk var3) {
      b _snowman = fw.a(_snowman, _snowman, () -> "Unable to resolve UVLock for model: " + _snowman).c();
      float _snowmanx = _snowman.a(_snowman.c(0));
      float _snowmanxx = _snowman.b(_snowman.c(0));
      h _snowmanxxx = new h(_snowmanx / 16.0F, _snowmanxx / 16.0F, 0.0F, 1.0F);
      _snowmanxxx.a(_snowman);
      float _snowmanxxxx = 16.0F * _snowmanxxx.a();
      float _snowmanxxxxx = 16.0F * _snowmanxxx.b();
      float _snowmanxxxxxx = _snowman.a(_snowman.c(2));
      float _snowmanxxxxxxx = _snowman.b(_snowman.c(2));
      h _snowmanxxxxxxxx = new h(_snowmanxxxxxx / 16.0F, _snowmanxxxxxxx / 16.0F, 0.0F, 1.0F);
      _snowmanxxxxxxxx.a(_snowman);
      float _snowmanxxxxxxxxx = 16.0F * _snowmanxxxxxxxx.a();
      float _snowmanxxxxxxxxxx = 16.0F * _snowmanxxxxxxxx.b();
      float _snowmanxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxx;
      if (Math.signum(_snowmanxxxxxx - _snowmanx) == Math.signum(_snowmanxxxxxxxxx - _snowmanxxxx)) {
         _snowmanxxxxxxxxxxx = _snowmanxxxx;
         _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx;
      } else {
         _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx;
         _snowmanxxxxxxxxxxxx = _snowmanxxxx;
      }

      float _snowmanxxxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxx;
      if (Math.signum(_snowmanxxxxxxx - _snowmanxx) == Math.signum(_snowmanxxxxxxxxxx - _snowmanxxxxx)) {
         _snowmanxxxxxxxxxxxxx = _snowmanxxxxx;
         _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx;
      } else {
         _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx;
         _snowmanxxxxxxxxxxxxxx = _snowmanxxxxx;
      }

      float _snowmanxxxxxxxxxxxxxxx = (float)Math.toRadians((double)_snowman.b);
      g _snowmanxxxxxxxxxxxxxxxx = new g(afm.b(_snowmanxxxxxxxxxxxxxxx), afm.a(_snowmanxxxxxxxxxxxxxxx), 0.0F);
      a _snowmanxxxxxxxxxxxxxxxxx = new a(_snowman);
      _snowmanxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxx);
      int _snowmanxxxxxxxxxxxxxxxxxx = Math.floorMod(
         -((int)Math.round(Math.toDegrees(Math.atan2((double)_snowmanxxxxxxxxxxxxxxxx.b(), (double)_snowmanxxxxxxxxxxxxxxxx.a())) / 90.0)) * 90, 360
      );
      return new ebe(new float[]{_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx}, _snowmanxxxxxxxxxxxxxxxxxx);
   }

   private int[] a(ebe var1, ekc var2, gc var3, float[] var4, f var5, @Nullable ebd var6, boolean var7) {
      int[] _snowman = new int[32];

      for (int _snowmanx = 0; _snowmanx < 4; _snowmanx++) {
         this.a(_snowman, _snowmanx, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      return _snowman;
   }

   private float[] a(g var1, g var2) {
      float[] _snowman = new float[gc.values().length];
      _snowman[dzx.a.f] = _snowman.a() / 16.0F;
      _snowman[dzx.a.e] = _snowman.b() / 16.0F;
      _snowman[dzx.a.d] = _snowman.c() / 16.0F;
      _snowman[dzx.a.c] = _snowman.a() / 16.0F;
      _snowman[dzx.a.b] = _snowman.b() / 16.0F;
      _snowman[dzx.a.a] = _snowman.c() / 16.0F;
      return _snowman;
   }

   private void a(int[] var1, int var2, gc var3, ebe var4, float[] var5, ekc var6, f var7, @Nullable ebd var8, boolean var9) {
      dzx.b _snowman = dzx.a(_snowman).a(_snowman);
      g _snowmanx = new g(_snowman[_snowman.a], _snowman[_snowman.b], _snowman[_snowman.c]);
      this.a(_snowmanx, _snowman);
      this.a(_snowmanx, _snowman);
      this.a(_snowman, _snowman, _snowmanx, _snowman, _snowman);
   }

   private void a(int[] var1, int var2, g var3, ekc var4, ebe var5) {
      int _snowman = _snowman * 8;
      _snowman[_snowman] = Float.floatToRawIntBits(_snowman.a());
      _snowman[_snowman + 1] = Float.floatToRawIntBits(_snowman.b());
      _snowman[_snowman + 2] = Float.floatToRawIntBits(_snowman.c());
      _snowman[_snowman + 3] = -1;
      _snowman[_snowman + 4] = Float.floatToRawIntBits(_snowman.a((double)_snowman.a(_snowman)));
      _snowman[_snowman + 4 + 1] = Float.floatToRawIntBits(_snowman.b((double)_snowman.b(_snowman)));
   }

   private void a(g var1, @Nullable ebd var2) {
      if (_snowman != null) {
         g _snowman;
         g _snowmanx;
         switch (_snowman.b) {
            case a:
               _snowman = new g(1.0F, 0.0F, 0.0F);
               _snowmanx = new g(0.0F, 1.0F, 1.0F);
               break;
            case b:
               _snowman = new g(0.0F, 1.0F, 0.0F);
               _snowmanx = new g(1.0F, 0.0F, 1.0F);
               break;
            case c:
               _snowman = new g(0.0F, 0.0F, 1.0F);
               _snowmanx = new g(1.0F, 1.0F, 0.0F);
               break;
            default:
               throw new IllegalArgumentException("There are only 3 axes");
         }

         d _snowman = new d(_snowman, _snowman.c, true);
         if (_snowman.d) {
            if (Math.abs(_snowman.c) == 22.5F) {
               _snowmanx.b(a);
            } else {
               _snowmanx.b(b);
            }

            _snowmanx.c(1.0F, 1.0F, 1.0F);
         } else {
            _snowmanx.a(1.0F, 1.0F, 1.0F);
         }

         this.a(_snowman, _snowman.a.e(), new b(_snowman), _snowmanx);
      }
   }

   public void a(g var1, f var2) {
      if (_snowman != f.a()) {
         this.a(_snowman, new g(0.5F, 0.5F, 0.5F), _snowman.c(), new g(1.0F, 1.0F, 1.0F));
      }
   }

   private void a(g var1, g var2, b var3, g var4) {
      h _snowman = new h(_snowman.a() - _snowman.a(), _snowman.b() - _snowman.b(), _snowman.c() - _snowman.c(), 1.0F);
      _snowman.a(_snowman);
      _snowman.a(_snowman);
      _snowman.a(_snowman.a() + _snowman.a(), _snowman.b() + _snowman.b(), _snowman.c() + _snowman.c());
   }

   public static gc a(int[] var0) {
      g _snowman = new g(Float.intBitsToFloat(_snowman[0]), Float.intBitsToFloat(_snowman[1]), Float.intBitsToFloat(_snowman[2]));
      g _snowmanx = new g(Float.intBitsToFloat(_snowman[8]), Float.intBitsToFloat(_snowman[9]), Float.intBitsToFloat(_snowman[10]));
      g _snowmanxx = new g(Float.intBitsToFloat(_snowman[16]), Float.intBitsToFloat(_snowman[17]), Float.intBitsToFloat(_snowman[18]));
      g _snowmanxxx = _snowman.e();
      _snowmanxxx.b(_snowmanx);
      g _snowmanxxxx = _snowmanxx.e();
      _snowmanxxxx.b(_snowmanx);
      g _snowmanxxxxx = _snowmanxxxx.e();
      _snowmanxxxxx.d(_snowmanxxx);
      _snowmanxxxxx.d();
      gc _snowmanxxxxxx = null;
      float _snowmanxxxxxxx = 0.0F;

      for (gc _snowmanxxxxxxxx : gc.values()) {
         gr _snowmanxxxxxxxxx = _snowmanxxxxxxxx.p();
         g _snowmanxxxxxxxxxx = new g((float)_snowmanxxxxxxxxx.u(), (float)_snowmanxxxxxxxxx.v(), (float)_snowmanxxxxxxxxx.w());
         float _snowmanxxxxxxxxxxx = _snowmanxxxxx.c(_snowmanxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxx >= 0.0F && _snowmanxxxxxxxxxxx > _snowmanxxxxxxx) {
            _snowmanxxxxxxx = _snowmanxxxxxxxxxxx;
            _snowmanxxxxxx = _snowmanxxxxxxxx;
         }
      }

      return _snowmanxxxxxx == null ? gc.b : _snowmanxxxxxx;
   }

   private void a(int[] var1, gc var2) {
      int[] _snowman = new int[_snowman.length];
      System.arraycopy(_snowman, 0, _snowman, 0, _snowman.length);
      float[] _snowmanx = new float[gc.values().length];
      _snowmanx[dzx.a.f] = 999.0F;
      _snowmanx[dzx.a.e] = 999.0F;
      _snowmanx[dzx.a.d] = 999.0F;
      _snowmanx[dzx.a.c] = -999.0F;
      _snowmanx[dzx.a.b] = -999.0F;
      _snowmanx[dzx.a.a] = -999.0F;

      for (int _snowmanxx = 0; _snowmanxx < 4; _snowmanxx++) {
         int _snowmanxxx = 8 * _snowmanxx;
         float _snowmanxxxx = Float.intBitsToFloat(_snowman[_snowmanxxx]);
         float _snowmanxxxxx = Float.intBitsToFloat(_snowman[_snowmanxxx + 1]);
         float _snowmanxxxxxx = Float.intBitsToFloat(_snowman[_snowmanxxx + 2]);
         if (_snowmanxxxx < _snowmanx[dzx.a.f]) {
            _snowmanx[dzx.a.f] = _snowmanxxxx;
         }

         if (_snowmanxxxxx < _snowmanx[dzx.a.e]) {
            _snowmanx[dzx.a.e] = _snowmanxxxxx;
         }

         if (_snowmanxxxxxx < _snowmanx[dzx.a.d]) {
            _snowmanx[dzx.a.d] = _snowmanxxxxxx;
         }

         if (_snowmanxxxx > _snowmanx[dzx.a.c]) {
            _snowmanx[dzx.a.c] = _snowmanxxxx;
         }

         if (_snowmanxxxxx > _snowmanx[dzx.a.b]) {
            _snowmanx[dzx.a.b] = _snowmanxxxxx;
         }

         if (_snowmanxxxxxx > _snowmanx[dzx.a.a]) {
            _snowmanx[dzx.a.a] = _snowmanxxxxxx;
         }
      }

      dzx _snowmanxx = dzx.a(_snowman);

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 4; _snowmanxxxxxxx++) {
         int _snowmanxxxxxxxx = 8 * _snowmanxxxxxxx;
         dzx.b _snowmanxxxxxxxxx = _snowmanxx.a(_snowmanxxxxxxx);
         float _snowmanxxxxxxxxxx = _snowmanx[_snowmanxxxxxxxxx.a];
         float _snowmanxxxxxxxxxxx = _snowmanx[_snowmanxxxxxxxxx.b];
         float _snowmanxxxxxxxxxxxx = _snowmanx[_snowmanxxxxxxxxx.c];
         _snowman[_snowmanxxxxxxxx] = Float.floatToRawIntBits(_snowmanxxxxxxxxxx);
         _snowman[_snowmanxxxxxxxx + 1] = Float.floatToRawIntBits(_snowmanxxxxxxxxxxx);
         _snowman[_snowmanxxxxxxxx + 2] = Float.floatToRawIntBits(_snowmanxxxxxxxxxxxx);

         for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxxx++) {
            int _snowmanxxxxxxxxxxxxxx = 8 * _snowmanxxxxxxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxxx = Float.intBitsToFloat(_snowman[_snowmanxxxxxxxxxxxxxx]);
            float _snowmanxxxxxxxxxxxxxxxx = Float.intBitsToFloat(_snowman[_snowmanxxxxxxxxxxxxxx + 1]);
            float _snowmanxxxxxxxxxxxxxxxxx = Float.intBitsToFloat(_snowman[_snowmanxxxxxxxxxxxxxx + 2]);
            if (afm.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx) && afm.a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx) && afm.a(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx)) {
               _snowman[_snowmanxxxxxxxx + 4] = _snowman[_snowmanxxxxxxxxxxxxxx + 4];
               _snowman[_snowmanxxxxxxxx + 4 + 1] = _snowman[_snowmanxxxxxxxxxxxxxx + 4 + 1];
            }
         }
      }
   }
}
