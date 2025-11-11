import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Optional;

public class dpn extends dot {
   private static final vk a = new vk("textures/gui/container/gamemode_switcher.png");
   private static final int b = dpn.a.values().length * 30 - 5;
   private static final nr c = new of("debug.gamemodes.select_next", new of("debug.gamemodes.press_f4").a(k.l));
   private final Optional<dpn.a> p;
   private Optional<dpn.a> q = Optional.empty();
   private int r;
   private int s;
   private boolean t;
   private final List<dpn.b> u = Lists.newArrayList();

   public dpn() {
      super(dkz.a);
      this.p = dpn.a.b(this.i());
   }

   private bru i() {
      bru _snowman = djz.C().q.l();
      bru _snowmanx = djz.C().q.k();
      if (_snowmanx == bru.a) {
         if (_snowman == bru.c) {
            _snowmanx = bru.b;
         } else {
            _snowmanx = bru.c;
         }
      }

      return _snowmanx;
   }

   @Override
   protected void b() {
      super.b();
      this.q = this.p.isPresent() ? this.p : dpn.a.b(this.i.q.l());

      for (int _snowman = 0; _snowman < dpn.a.e.length; _snowman++) {
         dpn.a _snowmanx = dpn.a.e[_snowman];
         this.u.add(new dpn.b(_snowmanx, this.k / 2 - b / 2 + _snowman * 30, this.l / 2 - 30));
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      if (!this.l()) {
         _snowman.a();
         RenderSystem.enableBlend();
         this.i.M().a(a);
         int _snowman = this.k / 2 - 62;
         int _snowmanx = this.l / 2 - 30 - 27;
         a(_snowman, _snowman, _snowmanx, 0.0F, 0.0F, 125, 75, 128, 128);
         _snowman.b();
         super.a(_snowman, _snowman, _snowman, _snowman);
         this.q.ifPresent(var2x -> a(_snowman, this.o, var2x.a(), this.k / 2, this.l / 2 - 30 - 20, -1));
         a(_snowman, this.o, c, this.k / 2, this.l / 2 + 5, 16777215);
         if (!this.t) {
            this.r = _snowman;
            this.s = _snowman;
            this.t = true;
         }

         boolean _snowmanxx = this.r == _snowman && this.s == _snowman;

         for (dpn.b _snowmanxxx : this.u) {
            _snowmanxxx.a(_snowman, _snowman, _snowman, _snowman);
            this.q.ifPresent(var1x -> _snowman.e(var1x == _snowman.b));
            if (!_snowmanxx && _snowmanxxx.g()) {
               this.q = Optional.of(_snowmanxxx.b);
            }
         }
      }
   }

   private void k() {
      a(this.i, this.q);
   }

   private static void a(djz var0, Optional<dpn.a> var1) {
      if (_snowman.q != null && _snowman.s != null && _snowman.isPresent()) {
         Optional<dpn.a> _snowman = dpn.a.b(_snowman.q.l());
         dpn.a _snowmanx = _snowman.get();
         if (_snowman.isPresent() && _snowman.s.k(2) && _snowmanx != _snowman.get()) {
            _snowman.s.f(_snowmanx.b());
         }
      }
   }

   private boolean l() {
      if (!deo.a(this.i.aD().i(), 292)) {
         this.k();
         this.i.a(null);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 293 && this.q.isPresent()) {
         this.t = false;
         this.q = this.q.get().c();
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public boolean ay_() {
      return false;
   }

   static enum a {
      a(new of("gameMode.creative"), "/gamemode creative", new bmb(bup.i)),
      b(new of("gameMode.survival"), "/gamemode survival", new bmb(bmd.kA)),
      c(new of("gameMode.adventure"), "/gamemode adventure", new bmb(bmd.pc)),
      d(new of("gameMode.spectator"), "/gamemode spectator", new bmb(bmd.nD));

      protected static final dpn.a[] e = values();
      final nr f;
      final String g;
      final bmb h;

      private a(nr var3, String var4, bmb var5) {
         this.f = _snowman;
         this.g = _snowman;
         this.h = _snowman;
      }

      private void a(efo var1, int var2, int var3) {
         _snowman.b(this.h, _snowman, _snowman);
      }

      private nr a() {
         return this.f;
      }

      private String b() {
         return this.g;
      }

      private Optional<dpn.a> c() {
         switch (this) {
            case a:
               return Optional.of(b);
            case b:
               return Optional.of(c);
            case c:
               return Optional.of(d);
            default:
               return Optional.of(a);
         }
      }

      private static Optional<dpn.a> b(bru var0) {
         switch (_snowman) {
            case e:
               return Optional.of(d);
            case b:
               return Optional.of(b);
            case c:
               return Optional.of(a);
            case d:
               return Optional.of(c);
            default:
               return Optional.empty();
         }
      }
   }

   public class b extends dlh {
      private final dpn.a b;
      private boolean c;

      public b(dpn.a var2, int var3, int var4) {
         super(_snowman, _snowman, 25, 25, _snowman.a());
         this.b = _snowman;
      }

      @Override
      public void b(dfm var1, int var2, int var3, float var4) {
         djz _snowman = djz.C();
         this.a(_snowman, _snowman.M());
         this.b.a(dpn.this.j, this.l + 5, this.m + 5);
         if (this.c) {
            this.b(_snowman, _snowman.M());
         }
      }

      @Override
      public boolean g() {
         return super.g() || this.c;
      }

      public void e(boolean var1) {
         this.c = _snowman;
         this.f();
      }

      private void a(dfm var1, ekd var2) {
         _snowman.a(dpn.a);
         _snowman.a();
         _snowman.a((double)this.l, (double)this.m, 0.0);
         a(_snowman, 0, 0, 0.0F, 75.0F, 25, 25, 128, 128);
         _snowman.b();
      }

      private void b(dfm var1, ekd var2) {
         _snowman.a(dpn.a);
         _snowman.a();
         _snowman.a((double)this.l, (double)this.m, 0.0);
         a(_snowman, 0, 0, 25.0F, 75.0F, 25, 25, 128, 128);
         _snowman.b();
      }
   }
}
