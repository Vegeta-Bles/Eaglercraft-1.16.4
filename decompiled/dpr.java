import com.mojang.blaze3d.systems.RenderSystem;

public class dpr extends dqm<bie> {
   private static final vk A = new vk("textures/gui/container/anvil.png");
   private static final nr B = new of("container.repair.expensive");
   private dlq C;

   public dpr(bie var1, bfv var2, nr var3) {
      super(_snowman, _snowman, _snowman, A);
      this.p = 60;
   }

   @Override
   public void d() {
      super.d();
      this.C.a();
   }

   @Override
   protected void i() {
      this.i.m.a(true);
      int _snowman = (this.k - this.b) / 2;
      int _snowmanx = (this.l - this.c) / 2;
      this.C = new dlq(this.o, _snowman + 62, _snowmanx + 24, 103, 12, new of("container.repair"));
      this.C.h(false);
      this.C.l(-1);
      this.C.m(-1);
      this.C.f(false);
      this.C.k(35);
      this.C.a(this::b);
      this.e.add(this.C);
      this.b(this.C);
   }

   @Override
   public void a(djz var1, int var2, int var3) {
      String _snowman = this.C.b();
      this.b(_snowman, _snowman, _snowman);
      this.C.a(_snowman);
   }

   @Override
   public void e() {
      super.e();
      this.i.m.a(false);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.i.s.m();
      }

      return !this.C.a(_snowman, _snowman, _snowman) && !this.C.m() ? super.a(_snowman, _snowman, _snowman) : true;
   }

   private void b(String var1) {
      if (!_snowman.isEmpty()) {
         String _snowman = _snowman;
         bjr _snowmanx = this.t.a(0);
         if (_snowmanx != null && _snowmanx.f() && !_snowmanx.e().t() && _snowman.equals(_snowmanx.e().r().getString())) {
            _snowman = "";
         }

         this.t.a(_snowman);
         this.i.s.e.a(new te(_snowman));
      }
   }

   @Override
   protected void b(dfm var1, int var2, int var3) {
      RenderSystem.disableBlend();
      super.b(_snowman, _snowman, _snowman);
      int _snowman = this.t.f();
      if (_snowman > 0) {
         int _snowmanx = 8453920;
         nr _snowmanxx;
         if (_snowman >= 40 && !this.i.s.bC.d) {
            _snowmanxx = B;
            _snowmanx = 16736352;
         } else if (!this.t.a(2).f()) {
            _snowmanxx = null;
         } else {
            _snowmanxx = new of("container.repair.cost", _snowman);
            if (!this.t.a(2).a(this.u.e)) {
               _snowmanx = 16736352;
            }
         }

         if (_snowmanxx != null) {
            int _snowmanxxx = this.b - 8 - this.o.a(_snowmanxx) - 2;
            int _snowmanxxxx = 69;
            a(_snowman, _snowmanxxx - 2, 67, this.b - 8, 79, 1325400064);
            this.o.a(_snowman, _snowmanxx, (float)_snowmanxxx, 69.0F, _snowmanx);
         }
      }
   }

   @Override
   public void b(dfm var1, int var2, int var3, float var4) {
      this.C.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(bic var1, int var2, bmb var3) {
      if (_snowman == 0) {
         this.C.a(_snowman.a() ? "" : _snowman.r().getString());
         this.C.g(!_snowman.a());
         this.a(this.C);
      }
   }
}
