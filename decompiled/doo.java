public class doo extends dot {
   private final boolean a;

   public doo(boolean var1) {
      super(_snowman ? new of("menu.game") : new of("menu.paused"));
      this.a = _snowman;
   }

   @Override
   protected void b() {
      if (this.a) {
         this.h();
      }
   }

   private void h() {
      int _snowman = -16;
      int _snowmanx = 98;
      this.a(new dlj(this.k / 2 - 102, this.l / 4 + 24 + -16, 204, 20, new of("menu.returnToGame"), var1x -> {
         this.i.a(null);
         this.i.l.i();
      }));
      this.a(new dlj(this.k / 2 - 102, this.l / 4 + 48 + -16, 98, 20, new of("gui.advancements"), var1x -> this.i.a(new dpi(this.i.s.e.h()))));
      this.a(new dlj(this.k / 2 + 4, this.l / 4 + 48 + -16, 98, 20, new of("gui.stats"), var1x -> this.i.a(new dpb(this, this.i.s.D()))));
      String _snowmanxx = w.a().isStable() ? "https://aka.ms/javafeedback?ref=game" : "https://aka.ms/snapshotfeedback?ref=game";
      this.a(new dlj(this.k / 2 - 102, this.l / 4 + 72 + -16, 98, 20, new of("menu.sendFeedback"), var2x -> this.i.a(new dnr(var2xx -> {
            if (var2xx) {
               x.i().a(_snowman);
            }

            this.i.a(this);
         }, _snowman, true))));
      this.a(new dlj(this.k / 2 + 4, this.l / 4 + 72 + -16, 98, 20, new of("menu.reportBugs"), var1x -> this.i.a(new dnr(var1xx -> {
            if (var1xx) {
               x.i().a("https://aka.ms/snapshotbugs?ref=game");
            }

            this.i.a(this);
         }, "https://aka.ms/snapshotbugs?ref=game", true))));
      this.a(new dlj(this.k / 2 - 102, this.l / 4 + 96 + -16, 98, 20, new of("menu.options"), var1x -> this.i.a(new dok(this, this.i.k))));
      dlj _snowmanxxx = this.a(new dlj(this.k / 2 + 4, this.l / 4 + 96 + -16, 98, 20, new of("menu.shareToLan"), var1x -> this.i.a(new dou(this))));
      _snowmanxxx.o = this.i.G() && !this.i.H().n();
      dlj _snowmanxxxx = this.a(new dlj(this.k / 2 - 102, this.l / 4 + 120 + -16, 204, 20, new of("menu.returnToMenu"), var1x -> {
         boolean _snowmanxxxxx = this.i.F();
         boolean _snowmanx = this.i.ah();
         var1x.o = false;
         this.i.r.S();
         if (_snowmanxxxxx) {
            this.i.b(new dod(new of("menu.savingLevel")));
         } else {
            this.i.r();
         }

         if (_snowmanxxxxx) {
            this.i.a(new doy());
         } else if (_snowmanx) {
            eok _snowmanxx = new eok();
            _snowmanxx.a(new doy());
         } else {
            this.i.a(new drc(new doy()));
         }
      }));
      if (!this.i.F()) {
         _snowmanxxxx.a(new of("menu.disconnect"));
      }
   }

   @Override
   public void d() {
      super.d();
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      if (this.a) {
         this.a(_snowman);
         a(_snowman, this.o, this.d, this.k / 2, 40, 16777215);
      } else {
         a(_snowman, this.o, this.d, this.k / 2, 10, 16777215);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
