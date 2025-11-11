import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class doq extends dot {
   private static final Logger a = LogManager.getLogger();
   private static final List<doq.a> b = Lists.newArrayList();
   private final dnv c;
   private nr p;
   private nr q;
   private doq.b r;
   private dlj s;
   private dlq t;
   private cpf u;

   public doq(dnv var1) {
      super(new of("createWorld.customize.presets.title"));
      this.c = _snowman;
   }

   @Nullable
   private static cpe a(String var0, int var1) {
      String[] _snowman = _snowman.split("\\*", 2);
      int _snowmanx;
      if (_snowman.length == 2) {
         try {
            _snowmanx = Math.max(Integer.parseInt(_snowman[0]), 0);
         } catch (NumberFormatException var10) {
            a.error("Error while parsing flat world string => {}", var10.getMessage());
            return null;
         }
      } else {
         _snowmanx = 1;
      }

      int _snowmanxx = Math.min(_snowman + _snowmanx, 256);
      int _snowmanxxx = _snowmanxx - _snowman;
      String _snowmanxxxx = _snowman[_snowman.length - 1];

      buo _snowmanxxxxx;
      try {
         _snowmanxxxxx = gm.Q.b(new vk(_snowmanxxxx)).orElse(null);
      } catch (Exception var9) {
         a.error("Error while parsing flat world string => {}", var9.getMessage());
         return null;
      }

      if (_snowmanxxxxx == null) {
         a.error("Error while parsing flat world string => Unknown block, {}", _snowmanxxxx);
         return null;
      } else {
         cpe _snowmanxxxxxx = new cpe(_snowmanxxx, _snowmanxxxxx);
         _snowmanxxxxxx.a(_snowman);
         return _snowmanxxxxxx;
      }
   }

   private static List<cpe> b(String var0) {
      List<cpe> _snowman = Lists.newArrayList();
      String[] _snowmanx = _snowman.split(",");
      int _snowmanxx = 0;

      for (String _snowmanxxx : _snowmanx) {
         cpe _snowmanxxxx = a(_snowmanxxx, _snowmanxx);
         if (_snowmanxxxx == null) {
            return Collections.emptyList();
         }

         _snowman.add(_snowmanxxxx);
         _snowmanxx += _snowmanxxxx.a();
      }

      return _snowman;
   }

   public static cpf a(gm<bsv> var0, String var1, cpf var2) {
      Iterator<String> _snowman = Splitter.on(';').split(_snowman).iterator();
      if (!_snowman.hasNext()) {
         return cpf.a(_snowman);
      } else {
         List<cpe> _snowmanx = b(_snowman.next());
         if (_snowmanx.isEmpty()) {
            return cpf.a(_snowman);
         } else {
            cpf _snowmanxx = _snowman.a(_snowmanx, _snowman.d());
            vj<bsv> _snowmanxxx = btb.b;
            if (_snowman.hasNext()) {
               try {
                  vk _snowmanxxxx = new vk(_snowman.next());
                  _snowmanxxx = vj.a(gm.ay, _snowmanxxxx);
                  _snowman.c(_snowmanxxx).orElseThrow(() -> new IllegalArgumentException("Invalid Biome: " + _snowman));
               } catch (Exception var8) {
                  a.error("Error while parsing flat world string => {}", var8.getMessage());
               }
            }

            vj<bsv> _snowmanxxxx = _snowmanxxx;
            _snowmanxx.a(() -> _snowman.d(_snowman));
            return _snowmanxx;
         }
      }
   }

   private static String b(gm<bsv> var0, cpf var1) {
      StringBuilder _snowman = new StringBuilder();

      for (int _snowmanx = 0; _snowmanx < _snowman.f().size(); _snowmanx++) {
         if (_snowmanx > 0) {
            _snowman.append(",");
         }

         _snowman.append(_snowman.f().get(_snowmanx));
      }

      _snowman.append(";");
      _snowman.append(_snowman.b(_snowman.e()));
      return _snowman.toString();
   }

   @Override
   protected void b() {
      this.i.m.a(true);
      this.p = new of("createWorld.customize.presets.share");
      this.q = new of("createWorld.customize.presets.list");
      this.t = new dlq(this.o, 50, 40, this.k - 100, 20, this.p);
      this.t.k(1230);
      gm<bsv> _snowman = this.c.a.c.b().b(gm.ay);
      this.t.a(b(_snowman, this.c.h()));
      this.u = this.c.h();
      this.e.add(this.t);
      this.r = new doq.b();
      this.e.add(this.r);
      this.s = this.a((dlj)(new dlj(this.k / 2 - 155, this.l - 28, 150, 20, new of("createWorld.customize.presets.select"), var2 -> {
         cpf _snowmanx = a(_snowman, this.t.b(), this.u);
         this.c.a(_snowmanx);
         this.i.a(this.c);
      })));
      this.a((dlj)(new dlj(this.k / 2 + 5, this.l - 28, 150, 20, nq.d, var1x -> this.i.a(this.c))));
      this.c(this.r.h() != null);
   }

   @Override
   public boolean a(double var1, double var3, double var5) {
      return this.r.a(_snowman, _snowman, _snowman);
   }

   @Override
   public void a(djz var1, int var2, int var3) {
      String _snowman = this.t.b();
      this.b(_snowman, _snowman, _snowman);
      this.t.a(_snowman);
   }

   @Override
   public void at_() {
      this.i.a(this.c);
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.r.a(_snowman, _snowman, _snowman, _snowman);
      RenderSystem.pushMatrix();
      RenderSystem.translatef(0.0F, 0.0F, 400.0F);
      a(_snowman, this.o, this.d, this.k / 2, 8, 16777215);
      b(_snowman, this.o, this.p, 50, 30, 10526880);
      b(_snowman, this.o, this.q, 50, 70, 10526880);
      RenderSystem.popMatrix();
      this.t.a(_snowman, _snowman, _snowman, _snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void d() {
      this.t.a();
      super.d();
   }

   @Override
   public void c(boolean var1) {
      this.s.o = _snowman || this.t.b().length() > 1;
   }

   private static void a(nr var0, brw var1, vj<bsv> var2, List<cla<?>> var3, boolean var4, boolean var5, boolean var6, cpe... var7) {
      b.add(new doq.a(_snowman.h(), _snowman, var6x -> {
         Map<cla<?>, cmy> _snowman = Maps.newHashMap();

         for (cla<?> _snowmanx : _snowman) {
            _snowman.put(_snowmanx, (cmy)chv.b.get(_snowmanx));
         }

         chv _snowmanx = new chv(_snowman ? Optional.of(chv.c) : Optional.empty(), _snowman);
         cpf _snowmanxx = new cpf(_snowmanx, var6x);
         if (_snowman) {
            _snowmanxx.a();
         }

         if (_snowman) {
            _snowmanxx.b();
         }

         for (int _snowmanxxx = _snowman.length - 1; _snowmanxxx >= 0; _snowmanxxx--) {
            _snowmanxx.f().add(_snowman[_snowmanxxx]);
         }

         _snowmanxx.a(() -> var6x.d(_snowman));
         _snowmanxx.h();
         return _snowmanxx.a(_snowmanx);
      }));
   }

   static {
      a(
         new of("createWorld.customize.preset.classic_flat"),
         bup.i,
         btb.b,
         Arrays.asList(cla.q),
         false,
         false,
         false,
         new cpe(1, bup.i),
         new cpe(2, bup.j),
         new cpe(1, bup.z)
      );
      a(
         new of("createWorld.customize.preset.tunnelers_dream"),
         bup.b,
         btb.d,
         Arrays.asList(cla.c),
         true,
         true,
         false,
         new cpe(1, bup.i),
         new cpe(5, bup.j),
         new cpe(230, bup.b),
         new cpe(1, bup.z)
      );
      a(
         new of("createWorld.customize.preset.water_world"),
         bmd.lL,
         btb.y,
         Arrays.asList(cla.m, cla.i, cla.l),
         false,
         false,
         false,
         new cpe(90, bup.A),
         new cpe(5, bup.C),
         new cpe(5, bup.j),
         new cpe(5, bup.b),
         new cpe(1, bup.z)
      );
      a(
         new of("createWorld.customize.preset.overworld"),
         bup.aR,
         btb.b,
         Arrays.asList(cla.q, cla.c, cla.b, cla.h),
         true,
         true,
         true,
         new cpe(1, bup.i),
         new cpe(3, bup.j),
         new cpe(59, bup.b),
         new cpe(1, bup.z)
      );
      a(
         new of("createWorld.customize.preset.snowy_kingdom"),
         bup.cC,
         btb.m,
         Arrays.asList(cla.q, cla.g),
         false,
         false,
         false,
         new cpe(1, bup.cC),
         new cpe(1, bup.i),
         new cpe(3, bup.j),
         new cpe(59, bup.b),
         new cpe(1, bup.z)
      );
      a(
         new of("createWorld.customize.preset.bottomless_pit"),
         bmd.kT,
         btb.b,
         Arrays.asList(cla.q),
         false,
         false,
         false,
         new cpe(1, bup.i),
         new cpe(3, bup.j),
         new cpe(2, bup.m)
      );
      a(
         new of("createWorld.customize.preset.desert"),
         bup.C,
         btb.c,
         Arrays.asList(cla.q, cla.f, cla.c),
         true,
         true,
         false,
         new cpe(8, bup.C),
         new cpe(52, bup.at),
         new cpe(3, bup.b),
         new cpe(1, bup.z)
      );
      a(
         new of("createWorld.customize.preset.redstone_ready"),
         bmd.lP,
         btb.c,
         Collections.emptyList(),
         false,
         false,
         false,
         new cpe(52, bup.at),
         new cpe(3, bup.b),
         new cpe(1, bup.z)
      );
      a(new of("createWorld.customize.preset.the_void"), bup.go, btb.Z, Collections.emptyList(), false, true, false, new cpe(1, bup.a));
   }

   static class a {
      public final blx a;
      public final nr b;
      public final Function<gm<bsv>, cpf> c;

      public a(blx var1, nr var2, Function<gm<bsv>, cpf> var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public nr a() {
         return this.b;
      }
   }

   class b extends dlv<doq.b.a> {
      public b() {
         super(doq.this.i, doq.this.k, doq.this.l, 80, doq.this.l - 37, 24);

         for (int _snowman = 0; _snowman < doq.b.size(); _snowman++) {
            this.b(new doq.b.a());
         }
      }

      public void a(@Nullable doq.b.a var1) {
         super.a(_snowman);
         if (_snowman != null) {
            dkz.b.a(new of("narrator.select", doq.b.get(this.au_().indexOf(_snowman)).a()).getString());
         }

         doq.this.c(_snowman != null);
      }

      @Override
      protected boolean b() {
         return doq.this.aw_() == this;
      }

      @Override
      public boolean a(int var1, int var2, int var3) {
         if (super.a(_snowman, _snowman, _snowman)) {
            return true;
         } else {
            if ((_snowman == 257 || _snowman == 335) && this.h() != null) {
               this.h().a();
            }

            return false;
         }
      }

      public class a extends dlv.a<doq.b.a> {
         public a() {
         }

         @Override
         public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
            doq.a _snowman = doq.b.get(_snowman);
            this.a(_snowman, _snowman, _snowman, _snowman.a);
            doq.this.o.b(_snowman, _snowman.b, (float)(_snowman + 18 + 5), (float)(_snowman + 6), 16777215);
         }

         @Override
         public boolean a(double var1, double var3, int var5) {
            if (_snowman == 0) {
               this.a();
            }

            return false;
         }

         private void a() {
            b.this.a(this);
            doq.a _snowman = doq.b.get(b.this.au_().indexOf(this));
            gm<bsv> _snowmanx = doq.this.c.a.c.b().b(gm.ay);
            doq.this.u = _snowman.c.apply(_snowmanx);
            doq.this.t.a(doq.b(_snowmanx, doq.this.u));
            doq.this.t.k();
         }

         private void a(dfm var1, int var2, int var3, blx var4) {
            this.a(_snowman, _snowman + 1, _snowman + 1);
            RenderSystem.enableRescaleNormal();
            doq.this.j.a(new bmb(_snowman), _snowman + 2, _snowman + 2);
            RenderSystem.disableRescaleNormal();
         }

         private void a(dfm var1, int var2, int var3) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            b.this.b.M().a(dkw.g);
            dkw.a(_snowman, _snowman, _snowman, doq.this.v(), 0.0F, 0.0F, 18, 18, 128, 128);
         }
      }
   }
}
