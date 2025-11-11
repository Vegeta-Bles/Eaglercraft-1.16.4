import java.util.List;
import java.util.Optional;

public abstract class dkc {
   public static final dkf a = new dkf("options.biomeBlendRadius", 0.0, 7.0, 1.0F, var0 -> (double)var0.F, (var0, var1) -> {
      var0.F = afm.a((int)var1.doubleValue(), 0, 7);
      djz.C().e.e();
   }, (var0, var1) -> {
      double _snowman = var1.a(var0);
      int _snowmanx = (int)_snowman * 2 + 1;
      return var1.a(new of("options.biomeBlendRadius." + _snowmanx));
   });
   public static final dkf b = new dkf("options.chat.height.focused", 0.0, 1.0, 0.0F, var0 -> var0.y, (var0, var1) -> {
      var0.y = var1;
      djz.C().j.c().a();
   }, (var0, var1) -> {
      double _snowman = var1.a(var1.a(var0));
      return var1.a(dlk.c(_snowman));
   });
   public static final dkf c = new dkf("options.chat.height.unfocused", 0.0, 1.0, 0.0F, var0 -> var0.x, (var0, var1) -> {
      var0.x = var1;
      djz.C().j.c().a();
   }, (var0, var1) -> {
      double _snowman = var1.a(var1.a(var0));
      return var1.a(dlk.c(_snowman));
   });
   public static final dkf d = new dkf("options.chat.opacity", 0.0, 1.0, 0.0F, var0 -> var0.k, (var0, var1) -> {
      var0.k = var1;
      djz.C().j.c().a();
   }, (var0, var1) -> {
      double _snowman = var1.a(var1.a(var0));
      return var1.c(_snowman * 0.9 + 0.1);
   });
   public static final dkf e = new dkf("options.chat.scale", 0.0, 1.0, 0.0F, var0 -> var0.v, (var0, var1) -> {
      var0.v = var1;
      djz.C().j.c().a();
   }, (var0, var1) -> {
      double _snowman = var1.a(var1.a(var0));
      return (nr)(_snowman == 0.0 ? nq.a(var1.a(), false) : var1.c(_snowman));
   });
   public static final dkf f = new dkf("options.chat.width", 0.0, 1.0, 0.0F, var0 -> var0.w, (var0, var1) -> {
      var0.w = var1;
      djz.C().j.c().a();
   }, (var0, var1) -> {
      double _snowman = var1.a(var1.a(var0));
      return var1.a(dlk.b(_snowman));
   });
   public static final dkf g = new dkf(
      "options.chat.line_spacing", 0.0, 1.0, 0.0F, var0 -> var0.l, (var0, var1) -> var0.l = var1, (var0, var1) -> var1.c(var1.a(var1.a(var0)))
   );
   public static final dkf h = new dkf("options.chat.delay_instant", 0.0, 6.0, 0.1F, var0 -> var0.z, (var0, var1) -> var0.z = var1, (var0, var1) -> {
      double _snowman = var1.a(var0);
      return _snowman <= 0.0 ? new of("options.chat.delay_none") : new of("options.chat.delay", String.format("%.1f", _snowman));
   });
   public static final dkf i = new dkf("options.fov", 30.0, 110.0, 1.0F, var0 -> var0.aO, (var0, var1) -> var0.aO = var1, (var0, var1) -> {
      double _snowman = var1.a(var0);
      if (_snowman == 70.0) {
         return var1.a(new of("options.fov.min"));
      } else {
         return _snowman == var1.d() ? var1.a(new of("options.fov.max")) : var1.c((int)_snowman);
      }
   });
   private static final nr Y = new of("options.fovEffectScale.tooltip");
   public static final dkf j = new dkf(
      "options.fovEffectScale", 0.0, 1.0, 0.0F, var0 -> Math.pow((double)var0.aQ, 2.0), (var0, var1) -> var0.aQ = afm.a(var1), (var0, var1) -> {
         var1.a(djz.C().g.b(Y, 200));
         double _snowman = var1.a(var1.a(var0));
         return _snowman == 0.0 ? var1.a(new of("options.fovEffectScale.off")) : var1.c(_snowman);
      }
   );
   private static final nr Z = new of("options.screenEffectScale.tooltip");
   public static final dkf k = new dkf(
      "options.screenEffectScale", 0.0, 1.0, 0.0F, var0 -> (double)var0.aP, (var0, var1) -> var0.aP = var1.floatValue(), (var0, var1) -> {
         var1.a(djz.C().g.b(Z, 200));
         double _snowman = var1.a(var1.a(var0));
         return _snowman == 0.0 ? var1.a(new of("options.screenEffectScale.off")) : var1.c(_snowman);
      }
   );
   public static final dkf l = new dkf("options.framerateLimit", 10.0, 260.0, 10.0F, var0 -> (double)var0.d, (var0, var1) -> {
      var0.d = (int)var1.doubleValue();
      djz.C().aD().a(var0.d);
   }, (var0, var1) -> {
      double _snowman = var1.a(var0);
      return _snowman == var1.d() ? var1.a(new of("options.framerateLimit.max")) : var1.a(new of("options.framerate", (int)_snowman));
   });
   public static final dkf m = new dkf("options.gamma", 0.0, 1.0, 0.0F, var0 -> var0.aR, (var0, var1) -> var0.aR = var1, (var0, var1) -> {
      double _snowman = var1.a(var1.a(var0));
      if (_snowman == 0.0) {
         return var1.a(new of("options.gamma.min"));
      } else {
         return _snowman == 1.0 ? var1.a(new of("options.gamma.max")) : var1.b((int)(_snowman * 100.0));
      }
   });
   public static final dkf n = new dkf(
      "options.mipmapLevels", 0.0, 4.0, 1.0F, var0 -> (double)var0.A, (var0, var1) -> var0.A = (int)var1.doubleValue(), (var0, var1) -> {
         double _snowman = var1.a(var0);
         return (nr)(_snowman == 0.0 ? nq.a(var1.a(), false) : var1.c((int)_snowman));
      }
   );
   public static final dkf o = new djy("options.mouseWheelSensitivity", 0.01, 10.0, 0.01F, var0 -> var0.G, (var0, var1) -> var0.G = var1, (var0, var1) -> {
      double _snowman = var1.a(var1.a(var0));
      return var1.a(new oe(String.format("%.2f", var1.b(_snowman))));
   });
   public static final djj p = new djj("options.rawMouseInput", var0 -> var0.H, (var0, var1) -> {
      var0.H = var1;
      dez _snowman = djz.C().aD();
      if (_snowman != null) {
         _snowman.b(var1);
      }
   });
   public static final dkf q = new dkf("options.renderDistance", 2.0, 16.0, 1.0F, var0 -> (double)var0.b, (var0, var1) -> {
      var0.b = (int)var1.doubleValue();
      djz.C().e.o();
   }, (var0, var1) -> {
      double _snowman = var1.a(var0);
      return var1.a(new of("options.chunks", (int)_snowman));
   });
   public static final dkf r = new dkf(
      "options.entityDistanceScaling", 0.5, 5.0, 0.25F, var0 -> (double)var0.c, (var0, var1) -> var0.c = (float)var1.doubleValue(), (var0, var1) -> {
         double _snowman = var1.a(var0);
         return var1.c(_snowman);
      }
   );
   public static final dkf s = new dkf("options.sensitivity", 0.0, 1.0, 0.0F, var0 -> var0.a, (var0, var1) -> var0.a = var1, (var0, var1) -> {
      double _snowman = var1.a(var1.a(var0));
      if (_snowman == 0.0) {
         return var1.a(new of("options.sensitivity.min"));
      } else {
         return _snowman == 1.0 ? var1.a(new of("options.sensitivity.max")) : var1.c(2.0 * _snowman);
      }
   });
   public static final dkf t = new dkf("options.accessibility.text_background_opacity", 0.0, 1.0, 0.0F, var0 -> var0.m, (var0, var1) -> {
      var0.m = var1;
      djz.C().j.c().a();
   }, (var0, var1) -> var1.c(var1.a(var1.a(var0))));
   public static final djp u = new djp("options.ao", (var0, var1) -> {
      var0.g = djh.a(var0.g.a() + var1);
      djz.C().e.e();
   }, (var0, var1) -> var1.a(new of(var0.g.b())));
   public static final djp v = new djp("options.attackIndicator", (var0, var1) -> var0.C = dji.a(var0.C.a() + var1), (var0, var1) -> var1.a(new of(var0.C.b())));
   public static final djp w = new djp(
      "options.chat.visibility", (var0, var1) -> var0.j = bfu.a((var0.j.a() + var1) % 3), (var0, var1) -> var1.a(new of(var0.j.b()))
   );
   private static final nr aa = new of("options.graphics.fast.tooltip");
   private static final nr ab = new of("options.graphics.fabulous.tooltip", new of("options.graphics.fabulous").a(k.u));
   private static final nr ac = new of("options.graphics.fancy.tooltip");
   public static final djp x = new djp("options.graphics", (var0, var1) -> {
      djz _snowman = djz.C();
      eaa _snowmanx = _snowman.V();
      if (var0.f == djt.b && _snowmanx.b()) {
         _snowmanx.d();
      } else {
         var0.f = var0.f.c();
         if (var0.f == djt.c && (!dem.U() || _snowmanx.h())) {
            var0.f = djt.a;
         }

         _snowman.e.e();
      }
   }, (var0, var1) -> {
      switch (var0.f) {
         case a:
            var1.a(djz.C().g.b(aa, 200));
            break;
         case b:
            var1.a(djz.C().g.b(ac, 200));
            break;
         case c:
            var1.a(djz.C().g.b(ab, 200));
      }

      nx _snowman = new of(var0.f.b());
      return var0.f == djt.c ? var1.a(_snowman.a(k.u)) : var1.a(_snowman);
   });
   public static final djp y = new djp(
      "options.guiScale",
      (var0, var1) -> var0.aS = Integer.remainderUnsigned(var0.aS + var1, djz.C().aD().a(0, djz.C().i()) + 1),
      (var0, var1) -> var0.aS == 0 ? var1.a(new of("options.guiScale.auto")) : var1.c(var0.aS)
   );
   public static final djp z = new djp("options.mainHand", (var0, var1) -> var0.r = var0.r.a(), (var0, var1) -> var1.a(var0.r.b()));
   public static final djp A = new djp("options.narrator", (var0, var1) -> {
      if (dkz.b.a()) {
         var0.aU = dkb.a(var0.aU.a() + var1);
      } else {
         var0.aU = dkb.a;
      }

      dkz.b.a(var0.aU);
   }, (var0, var1) -> dkz.b.a() ? var1.a(var0.aU.b()) : var1.a(new of("options.narrator.notavailable")));
   public static final djp B = new djp("options.particles", (var0, var1) -> var0.aT = dke.a(var0.aT.b() + var1), (var0, var1) -> var1.a(new of(var0.aT.a())));
   public static final djp C = new djp("options.renderClouds", (var0, var1) -> {
      var0.e = djn.a(var0.e.a() + var1);
      if (djz.A()) {
         deg _snowman = djz.C().e.u();
         if (_snowman != null) {
            _snowman.b(djz.a);
         }
      }
   }, (var0, var1) -> var1.a(new of(var0.e.b())));
   public static final djp D = new djp(
      "options.accessibility.text_background",
      (var0, var1) -> var0.X = !var0.X,
      (var0, var1) -> var1.a(new of(var0.X ? "options.accessibility.text_background.chat" : "options.accessibility.text_background.everywhere"))
   );
   private static final nr ad = new of("options.hideMatchedNames.tooltip");
   public static final djj E = new djj("options.autoJump", var0 -> var0.J, (var0, var1) -> var0.J = var1);
   public static final djj F = new djj("options.autoSuggestCommands", var0 -> var0.K, (var0, var1) -> var0.K = var1);
   public static final djj G = new djj("options.hideMatchedNames", ad, var0 -> var0.ae, (var0, var1) -> var0.ae = var1);
   public static final djj H = new djj("options.chat.color", var0 -> var0.L, (var0, var1) -> var0.L = var1);
   public static final djj I = new djj("options.chat.links", var0 -> var0.M, (var0, var1) -> var0.M = var1);
   public static final djj J = new djj("options.chat.links.prompt", var0 -> var0.N, (var0, var1) -> var0.N = var1);
   public static final djj K = new djj("options.discrete_mouse_scroll", var0 -> var0.S, (var0, var1) -> var0.S = var1);
   public static final djj L = new djj("options.vsync", var0 -> var0.O, (var0, var1) -> {
      var0.O = var1;
      if (djz.C().aD() != null) {
         djz.C().aD().a(var0.O);
      }
   });
   public static final djj M = new djj("options.entityShadows", var0 -> var0.P, (var0, var1) -> var0.P = var1);
   public static final djj N = new djj("options.forceUnicodeFont", var0 -> var0.Q, (var0, var1) -> {
      var0.Q = var1;
      djz _snowman = djz.C();
      if (_snowman.aD() != null) {
         _snowman.b(var1);
      }
   });
   public static final djj O = new djj("options.invertMouse", var0 -> var0.R, (var0, var1) -> var0.R = var1);
   public static final djj P = new djj("options.realmsNotifications", var0 -> var0.T, (var0, var1) -> var0.T = var1);
   public static final djj Q = new djj("options.reducedDebugInfo", var0 -> var0.U, (var0, var1) -> var0.U = var1);
   public static final djj R = new djj("options.showSubtitles", var0 -> var0.W, (var0, var1) -> var0.W = var1);
   public static final djj S = new djj("options.snooper", var0 -> {
      if (var0.V) {
      }

      return false;
   }, (var0, var1) -> var0.V = var1);
   public static final djp T = new djp(
      "key.sneak", (var0, var1) -> var0.ab = !var0.ab, (var0, var1) -> var1.a(new of(var0.ab ? "options.key.toggle" : "options.key.hold"))
   );
   public static final djp U = new djp(
      "key.sprint", (var0, var1) -> var0.ac = !var0.ac, (var0, var1) -> var1.a(new of(var0.ac ? "options.key.toggle" : "options.key.hold"))
   );
   public static final djj V = new djj("options.touchscreen", var0 -> var0.Y, (var0, var1) -> var0.Y = var1);
   public static final djj W = new djj("options.fullscreen", var0 -> var0.Z, (var0, var1) -> {
      var0.Z = var1;
      djz _snowman = djz.C();
      if (_snowman.aD() != null && _snowman.aD().j() != var0.Z) {
         _snowman.aD().h();
         var0.Z = _snowman.aD().j();
      }
   });
   public static final djj X = new djj("options.viewBobbing", var0 -> var0.aa, (var0, var1) -> var0.aa = var1);
   private final nr ae;
   private Optional<List<afa>> af = Optional.empty();

   public dkc(String var1) {
      this.ae = new of(_snowman);
   }

   public abstract dlh a(dkd var1, int var2, int var3, int var4);

   protected nr a() {
      return this.ae;
   }

   public void a(List<afa> var1) {
      this.af = Optional.of(_snowman);
   }

   public Optional<List<afa>> b() {
      return this.af;
   }

   protected nr a(int var1) {
      return new of("options.pixel_value", this.a(), _snowman);
   }

   protected nr c(double var1) {
      return new of("options.percent_value", this.a(), (int)(_snowman * 100.0));
   }

   protected nr b(int var1) {
      return new of("options.percent_add_value", this.a(), _snowman);
   }

   protected nr a(nr var1) {
      return new of("options.generic_value", this.a(), _snowman);
   }

   protected nr c(int var1) {
      return this.a(new oe(Integer.toString(_snowman)));
   }
}
