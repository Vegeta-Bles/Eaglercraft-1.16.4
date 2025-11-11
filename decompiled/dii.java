import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Either;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dii extends eoo {
   private static final Logger a = LogManager.getLogger();
   private static final vk b = new vk("realms", "textures/gui/realms/link_icons.png");
   private static final vk c = new vk("realms", "textures/gui/realms/trailer_icons.png");
   private static final vk p = new vk("realms", "textures/gui/realms/slot_frame.png");
   private static final nr q = new of("mco.template.info.tooltip");
   private static final nr r = new of("mco.template.trailer.tooltip");
   private final dig s;
   private dii.b t;
   private int u = -1;
   private nr v;
   private dlj w;
   private dlj x;
   private dlj y;
   @Nullable
   private nr z;
   private String A;
   private final dgq.c B;
   private int C;
   @Nullable
   private nr[] D;
   private String E;
   private boolean F;
   private boolean G;
   @Nullable
   private List<diu.a> H;

   public dii(dig var1, dgq.c var2) {
      this(_snowman, _snowman, null);
   }

   public dii(dig var1, dgq.c var2, @Nullable dhf var3) {
      this.s = _snowman;
      this.B = _snowman;
      if (_snowman == null) {
         this.t = new dii.b();
         this.a(new dhf(10));
      } else {
         this.t = new dii.b(Lists.newArrayList(_snowman.a));
         this.a(_snowman);
      }

      this.v = new of("mco.template.title");
   }

   public void a(nr var1) {
      this.v = _snowman;
   }

   public void a(nr... var1) {
      this.D = _snowman;
      this.F = true;
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (this.G && this.E != null) {
         x.i().a("https://www.minecraft.net/realms/adventure-maps-in-1-9");
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void b() {
      this.i.m.a(true);
      this.t = new dii.b(this.t.g());
      this.x = this.a((dlj)(new dlj(this.k / 2 - 206, this.l - 32, 100, 20, new of("mco.template.button.trailer"), var1x -> this.t())));
      this.w = this.a((dlj)(new dlj(this.k / 2 - 100, this.l - 32, 100, 20, new of("mco.template.button.select"), var1x -> this.q())));
      nr _snowman = this.B == dgq.c.b ? nq.d : nq.h;
      dlj _snowmanx = new dlj(this.k / 2 + 6, this.l - 32, 100, 20, _snowman, var1x -> this.p());
      this.a((dlj)_snowmanx);
      this.y = this.a((dlj)(new dlj(this.k / 2 + 112, this.l - 32, 100, 20, new of("mco.template.button.publisher"), var1x -> this.u())));
      this.w.o = false;
      this.x.p = false;
      this.y.p = false;
      this.d(this.t);
      this.c(this.t);
      Stream<nr> _snowmanxx = Stream.of(this.v);
      if (this.D != null) {
         _snowmanxx = Stream.concat(Stream.of(this.D), _snowmanxx);
      }

      eoj.a(_snowmanxx.filter(Objects::nonNull).map(nr::getString).collect(Collectors.toList()));
   }

   private void k() {
      this.y.p = this.m();
      this.x.p = this.o();
      this.w.o = this.l();
   }

   private boolean l() {
      return this.u != -1;
   }

   private boolean m() {
      return this.u != -1 && !this.n().e.isEmpty();
   }

   private dhe n() {
      return this.t.b(this.u);
   }

   private boolean o() {
      return this.u != -1 && !this.n().g.isEmpty();
   }

   @Override
   public void d() {
      super.d();
      this.C--;
      if (this.C < 0) {
         this.C = 0;
      }
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.p();
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   private void p() {
      this.s.a(null);
      this.i.a(this.s);
   }

   private void q() {
      if (this.r()) {
         this.s.a(this.n());
      }
   }

   private boolean r() {
      return this.u >= 0 && this.u < this.t.l();
   }

   private void t() {
      if (this.r()) {
         dhe _snowman = this.n();
         if (!"".equals(_snowman.g)) {
            x.i().a(_snowman.g);
         }
      }
   }

   private void u() {
      if (this.r()) {
         dhe _snowman = this.n();
         if (!"".equals(_snowman.e)) {
            x.i().a(_snowman.e);
         }
      }
   }

   private void a(final dhf var1) {
      (new Thread("realms-template-fetcher") {
         @Override
         public void run() {
            dhf _snowman = _snowman;
            dgb _snowmanx = dgb.a();

            while (_snowman != null) {
               Either<dhf, String> _snowmanxx = dii.this.a(_snowman, _snowmanx);
               _snowman = dii.this.i.a(() -> {
                  if (_snowman.right().isPresent()) {
                     dii.a.error("Couldn't fetch templates: {}", _snowman.right().get());
                     if (dii.this.t.f()) {
                        dii.this.H = diu.a(ekx.a("mco.template.select.failure"));
                     }

                     return null;
                  } else {
                     dhf _snowmanxxx = (dhf)_snowman.left().get();

                     for (dhe _snowmanx : _snowmanxxx.a) {
                        dii.this.t.a(_snowmanx);
                     }

                     if (_snowmanxxx.a.isEmpty()) {
                        if (dii.this.t.f()) {
                           String _snowmanx = ekx.a("mco.template.select.none", "%link");
                           diu.b _snowmanxx = diu.b.a(ekx.a("mco.template.select.none.linkTitle"), "https://aka.ms/MinecraftRealmsContentCreator");
                           dii.this.H = diu.a(_snowmanx, _snowmanxx);
                        }

                        return null;
                     } else {
                        return _snowmanxxx;
                     }
                  }
               }).join();
            }
         }
      }).start();
   }

   private Either<dhf, String> a(dhf var1, dgb var2) {
      try {
         return Either.left(_snowman.a(_snowman.b + 1, _snowman.c, this.B));
      } catch (dhi var4) {
         return Either.right(var4.getMessage());
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.z = null;
      this.A = null;
      this.G = false;
      this.a(_snowman);
      this.t.a(_snowman, _snowman, _snowman, _snowman);
      if (this.H != null) {
         this.a(_snowman, _snowman, _snowman, this.H);
      }

      a(_snowman, this.o, this.v, this.k / 2, 13, 16777215);
      if (this.F) {
         nr[] _snowman = this.D;

         for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
            int _snowmanxx = this.o.a(_snowman[_snowmanx]);
            int _snowmanxxx = this.k / 2 - _snowmanxx / 2;
            int _snowmanxxxx = j(-1 + _snowmanx);
            if (_snowman >= _snowmanxxx && _snowman <= _snowmanxxx + _snowmanxx && _snowman >= _snowmanxxxx && _snowman <= _snowmanxxxx + 9) {
               this.G = true;
            }
         }

         for (int _snowmanxx = 0; _snowmanxx < _snowman.length; _snowmanxx++) {
            nr _snowmanxxx = _snowman[_snowmanxx];
            int _snowmanxxxx = 10526880;
            if (this.E != null) {
               if (this.G) {
                  _snowmanxxxx = 7107012;
                  _snowmanxxx = _snowmanxxx.e().a(k.s);
               } else {
                  _snowmanxxxx = 3368635;
               }
            }

            a(_snowman, this.o, _snowmanxxx, this.k / 2, j(-1 + _snowmanxx), _snowmanxxxx);
         }
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
      this.a(_snowman, this.z, _snowman, _snowman);
   }

   private void a(dfm var1, int var2, int var3, List<diu.a> var4) {
      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         diu.a _snowmanx = _snowman.get(_snowman);
         int _snowmanxx = j(4 + _snowman);
         int _snowmanxxx = _snowmanx.a.stream().mapToInt(var1x -> this.o.b(var1x.a())).sum();
         int _snowmanxxxx = this.k / 2 - _snowmanxxx / 2;

         for (diu.b _snowmanxxxxx : _snowmanx.a) {
            int _snowmanxxxxxx = _snowmanxxxxx.b() ? 3368635 : 16777215;
            int _snowmanxxxxxxx = this.o.a(_snowman, _snowmanxxxxx.a(), (float)_snowmanxxxx, (float)_snowmanxx, _snowmanxxxxxx);
            if (_snowmanxxxxx.b() && _snowman > _snowmanxxxx && _snowman < _snowmanxxxxxxx && _snowman > _snowmanxx - 3 && _snowman < _snowmanxx + 8) {
               this.z = new oe(_snowmanxxxxx.c());
               this.A = _snowmanxxxxx.c();
            }

            _snowmanxxxx = _snowmanxxxxxxx;
         }
      }
   }

   protected void a(dfm var1, @Nullable nr var2, int var3, int var4) {
      if (_snowman != null) {
         int _snowman = _snowman + 12;
         int _snowmanx = _snowman - 12;
         int _snowmanxx = this.o.a(_snowman);
         this.a(_snowman, _snowman - 3, _snowmanx - 3, _snowman + _snowmanxx + 3, _snowmanx + 8 + 3, -1073741824, -1073741824);
         this.o.a(_snowman, _snowman, (float)_snowman, (float)_snowmanx, 16777215);
      }
   }

   class a extends dlv.a<dii.a> {
      private final dhe b;

      public a(dhe var2) {
         this.b = _snowman;
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.a(_snowman, this.b, _snowman, _snowman, _snowman, _snowman);
      }

      private void a(dfm var1, dhe var2, int var3, int var4, int var5, int var6) {
         int _snowman = _snowman + 45 + 20;
         dii.this.o.b(_snowman, _snowman.b, (float)_snowman, (float)(_snowman + 2), 16777215);
         dii.this.o.b(_snowman, _snowman.d, (float)_snowman, (float)(_snowman + 15), 7105644);
         dii.this.o.b(_snowman, _snowman.c, (float)(_snowman + 227 - dii.this.o.b(_snowman.c)), (float)(_snowman + 1), 7105644);
         if (!"".equals(_snowman.e) || !"".equals(_snowman.g) || !"".equals(_snowman.h)) {
            this.a(_snowman, _snowman - 1, _snowman + 25, _snowman, _snowman, _snowman.e, _snowman.g, _snowman.h);
         }

         this.a(_snowman, _snowman, _snowman + 1, _snowman, _snowman, _snowman);
      }

      private void a(dfm var1, int var2, int var3, int var4, int var5, dhe var6) {
         dir.a(_snowman.a, _snowman.f);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         dkw.a(_snowman, _snowman + 1, _snowman + 1, 0.0F, 0.0F, 38, 38, 38, 38);
         dii.this.i.M().a(dii.p);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         dkw.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 40, 40, 40, 40);
      }

      private void a(dfm var1, int var2, int var3, int var4, int var5, String var6, String var7, String var8) {
         if (!"".equals(_snowman)) {
            dii.this.o.b(_snowman, _snowman, (float)_snowman, (float)(_snowman + 4), 5000268);
         }

         int _snowman = "".equals(_snowman) ? 0 : dii.this.o.b(_snowman) + 2;
         boolean _snowmanx = false;
         boolean _snowmanxx = false;
         boolean _snowmanxxx = "".equals(_snowman);
         if (_snowman >= _snowman + _snowman && _snowman <= _snowman + _snowman + 32 && _snowman >= _snowman && _snowman <= _snowman + 15 && _snowman < dii.this.l - 15 && _snowman > 32) {
            if (_snowman <= _snowman + 15 + _snowman && _snowman > _snowman) {
               if (_snowmanxxx) {
                  _snowmanxx = true;
               } else {
                  _snowmanx = true;
               }
            } else if (!_snowmanxxx) {
               _snowmanxx = true;
            }
         }

         if (!_snowmanxxx) {
            dii.this.i.M().a(dii.b);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.pushMatrix();
            RenderSystem.scalef(1.0F, 1.0F, 1.0F);
            float _snowmanxxxx = _snowmanx ? 15.0F : 0.0F;
            dkw.a(_snowman, _snowman + _snowman, _snowman, _snowmanxxxx, 0.0F, 15, 15, 30, 15);
            RenderSystem.popMatrix();
         }

         if (!"".equals(_snowman)) {
            dii.this.i.M().a(dii.c);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.pushMatrix();
            RenderSystem.scalef(1.0F, 1.0F, 1.0F);
            int _snowmanxxxx = _snowman + _snowman + (_snowmanxxx ? 0 : 17);
            float _snowmanxxxxx = _snowmanxx ? 15.0F : 0.0F;
            dkw.a(_snowman, _snowmanxxxx, _snowman, _snowmanxxxxx, 0.0F, 15, 15, 30, 15);
            RenderSystem.popMatrix();
         }

         if (_snowmanx) {
            dii.this.z = dii.q;
            dii.this.A = _snowman;
         } else if (_snowmanxx && !"".equals(_snowman)) {
            dii.this.z = dii.r;
            dii.this.A = _snowman;
         }
      }
   }

   class b extends eon<dii.a> {
      public b() {
         this(Collections.emptyList());
      }

      public b(Iterable<dhe> var2) {
         super(dii.this.k, dii.this.l, dii.this.F ? dii.j(1) : 32, dii.this.l - 40, 46);
         _snowman.forEach(this::a);
      }

      public void a(dhe var1) {
         this.a((dii.a)(dii.this.new a(_snowman)));
      }

      @Override
      public boolean a(double var1, double var3, int var5) {
         if (_snowman == 0 && _snowman >= (double)this.i && _snowman <= (double)this.j) {
            int _snowman = this.d / 2 - 150;
            if (dii.this.A != null) {
               x.i().a(dii.this.A);
            }

            int _snowmanx = (int)Math.floor(_snowman - (double)this.i) - this.n + (int)this.m() - 4;
            int _snowmanxx = _snowmanx / this.c;
            if (_snowman >= (double)_snowman && _snowman < (double)this.e() && _snowmanxx >= 0 && _snowmanx >= 0 && _snowmanxx < this.l()) {
               this.a(_snowmanxx);
               this.a(_snowmanx, _snowmanxx, _snowman, _snowman, this.d);
               if (_snowmanxx >= dii.this.t.l()) {
                  return super.a(_snowman, _snowman, _snowman);
               }

               dii.this.C = dii.this.C + 7;
               if (dii.this.C >= 10) {
                  dii.this.q();
               }

               return true;
            }
         }

         return super.a(_snowman, _snowman, _snowman);
      }

      @Override
      public void a(int var1) {
         this.j(_snowman);
         if (_snowman != -1) {
            dhe _snowman = dii.this.t.b(_snowman);
            String _snowmanx = ekx.a("narrator.select.list.position", _snowman + 1, dii.this.t.l());
            String _snowmanxx = ekx.a("mco.template.select.narrate.version", _snowman.c);
            String _snowmanxxx = ekx.a("mco.template.select.narrate.authors", _snowman.d);
            String _snowmanxxxx = eoj.b(Arrays.asList(_snowman.b, _snowmanxxx, _snowman.h, _snowmanxx, _snowmanx));
            eoj.a(ekx.a("narrator.select", _snowmanxxxx));
         }
      }

      public void a(@Nullable dii.a var1) {
         super.a(_snowman);
         dii.this.u = this.au_().indexOf(_snowman);
         dii.this.k();
      }

      @Override
      public int c() {
         return this.l() * 46;
      }

      @Override
      public int d() {
         return 300;
      }

      @Override
      public void a(dfm var1) {
         dii.this.a(_snowman);
      }

      @Override
      public boolean b() {
         return dii.this.aw_() == this;
      }

      public boolean f() {
         return this.l() == 0;
      }

      public dhe b(int var1) {
         return this.au_().get(_snowman).b;
      }

      public List<dhe> g() {
         return this.au_().stream().map(var0 -> var0.b).collect(Collectors.toList());
      }
   }
}
