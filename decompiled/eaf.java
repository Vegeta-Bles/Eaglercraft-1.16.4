import com.mojang.blaze3d.systems.RenderSystem;

public class eaf implements AutoCloseable {
   private final ejs a;
   private final det b;
   private final vk c;
   private boolean d;
   private float e;
   private final dzz f;
   private final djz g;

   public eaf(dzz var1, djz var2) {
      this.f = _snowman;
      this.g = _snowman;
      this.a = new ejs(16, 16, false);
      this.c = this.g.M().a("light_map", this.a);
      this.b = this.a.e();

      for (int _snowman = 0; _snowman < 16; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
            this.b.a(_snowmanx, _snowman, -1);
         }
      }

      this.a.a();
   }

   @Override
   public void close() {
      this.a.close();
   }

   public void a() {
      this.e = (float)((double)this.e + (Math.random() - Math.random()) * Math.random() * Math.random() * 0.1);
      this.e = (float)((double)this.e * 0.9);
      this.d = true;
   }

   public void b() {
      RenderSystem.activeTexture(33986);
      RenderSystem.disableTexture();
      RenderSystem.activeTexture(33984);
   }

   public void c() {
      RenderSystem.activeTexture(33986);
      RenderSystem.matrixMode(5890);
      RenderSystem.loadIdentity();
      float _snowman = 0.00390625F;
      RenderSystem.scalef(0.00390625F, 0.00390625F, 0.00390625F);
      RenderSystem.translatef(8.0F, 8.0F, 8.0F);
      RenderSystem.matrixMode(5888);
      this.g.M().a(this.c);
      RenderSystem.texParameter(3553, 10241, 9729);
      RenderSystem.texParameter(3553, 10240, 9729);
      RenderSystem.texParameter(3553, 10242, 10496);
      RenderSystem.texParameter(3553, 10243, 10496);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableTexture();
      RenderSystem.activeTexture(33984);
   }

   public void a(float var1) {
      if (this.d) {
         this.d = false;
         this.g.au().a("lightTex");
         dwt _snowman = this.g.r;
         if (_snowman != null) {
            float _snowmanx = _snowman.g(1.0F);
            float _snowmanxx;
            if (_snowman.s() > 0) {
               _snowmanxx = 1.0F;
            } else {
               _snowmanxx = _snowmanx * 0.95F + 0.05F;
            }

            float _snowmanxxx = this.g.s.N();
            float _snowmanxxxx;
            if (this.g.s.a(apw.p)) {
               _snowmanxxxx = dzz.a(this.g.s, _snowman);
            } else if (_snowmanxxx > 0.0F && this.g.s.a(apw.C)) {
               _snowmanxxxx = _snowmanxxx;
            } else {
               _snowmanxxxx = 0.0F;
            }

            g _snowmanxxxxx = new g(_snowmanx, _snowmanx, 1.0F);
            _snowmanxxxxx.a(new g(1.0F, 1.0F, 1.0F), 0.35F);
            float _snowmanxxxxxx = this.e + 1.5F;
            g _snowmanxxxxxxx = new g();

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 16; _snowmanxxxxxxxx++) {
               for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < 16; _snowmanxxxxxxxxx++) {
                  float _snowmanxxxxxxxxxx = this.a(_snowman, _snowmanxxxxxxxx) * _snowmanxx;
                  float _snowmanxxxxxxxxxxx = this.a(_snowman, _snowmanxxxxxxxxx) * _snowmanxxxxxx;
                  float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx * ((_snowmanxxxxxxxxxxx * 0.6F + 0.4F) * 0.6F + 0.4F);
                  float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx * (_snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx * 0.6F + 0.4F);
                  _snowmanxxxxxxx.a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
                  if (_snowman.a().d()) {
                     _snowmanxxxxxxx.a(new g(0.99F, 1.12F, 1.0F), 0.25F);
                  } else {
                     g _snowmanxxxxxxxxxxxxxx = _snowmanxxxxx.e();
                     _snowmanxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxx);
                     _snowmanxxxxxxx.a(_snowmanxxxxxxxxxxxxxx);
                     _snowmanxxxxxxx.a(new g(0.75F, 0.75F, 0.75F), 0.04F);
                     if (this.f.b(_snowman) > 0.0F) {
                        float _snowmanxxxxxxxxxxxxxxx = this.f.b(_snowman);
                        g _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxx.e();
                        _snowmanxxxxxxxxxxxxxxxx.b(0.7F, 0.6F, 0.6F);
                        _snowmanxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
                     }
                  }

                  _snowmanxxxxxxx.a(0.0F, 1.0F);
                  if (_snowmanxxxx > 0.0F) {
                     float _snowmanxxxxxxxxxxxxxxx = Math.max(_snowmanxxxxxxx.a(), Math.max(_snowmanxxxxxxx.b(), _snowmanxxxxxxx.c()));
                     if (_snowmanxxxxxxxxxxxxxxx < 1.0F) {
                        float _snowmanxxxxxxxxxxxxxxxx = 1.0F / _snowmanxxxxxxxxxxxxxxx;
                        g _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx.e();
                        _snowmanxxxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxxxxxx);
                        _snowmanxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxx);
                     }
                  }

                  float _snowmanxxxxxxxxxxxxxxx = (float)this.g.k.aR;
                  g _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxx.e();
                  _snowmanxxxxxxxxxxxxxxxx.a(this::b);
                  _snowmanxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
                  _snowmanxxxxxxx.a(new g(0.75F, 0.75F, 0.75F), 0.04F);
                  _snowmanxxxxxxx.a(0.0F, 1.0F);
                  _snowmanxxxxxxx.b(255.0F);
                  int _snowmanxxxxxxxxxxxxxxxxx = 255;
                  int _snowmanxxxxxxxxxxxxxxxxxx = (int)_snowmanxxxxxxx.a();
                  int _snowmanxxxxxxxxxxxxxxxxxxx = (int)_snowmanxxxxxxx.b();
                  int _snowmanxxxxxxxxxxxxxxxxxxxx = (int)_snowmanxxxxxxx.c();
                  this.b.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxx, 0xFF000000 | _snowmanxxxxxxxxxxxxxxxxxxxx << 16 | _snowmanxxxxxxxxxxxxxxxxxxx << 8 | _snowmanxxxxxxxxxxxxxxxxxx);
               }
            }

            this.a.a();
            this.g.au().c();
         }
      }
   }

   private float b(float var1) {
      float _snowman = 1.0F - _snowman;
      return 1.0F - _snowman * _snowman * _snowman * _snowman;
   }

   private float a(brx var1, int var2) {
      return _snowman.k().a(_snowman);
   }

   public static int a(int var0, int var1) {
      return _snowman << 4 | _snowman << 20;
   }

   public static int a(int var0) {
      return _snowman >> 4 & 65535;
   }

   public static int b(int var0) {
      return _snowman >> 20 & 65535;
   }
}
