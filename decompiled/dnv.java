import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class dnv extends dot {
   protected final dsf a;
   private final Consumer<cpf> b;
   private cpf c;
   private nr p;
   private nr q;
   private dnv.a r;
   private dlj s;

   public dnv(dsf var1, Consumer<cpf> var2, cpf var3) {
      super(new of("createWorld.customize.flat.title"));
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public cpf h() {
      return this.c;
   }

   public void a(cpf var1) {
      this.c = _snowman;
   }

   @Override
   protected void b() {
      this.p = new of("createWorld.customize.flat.tile");
      this.q = new of("createWorld.customize.flat.height");
      this.r = new dnv.a();
      this.e.add(this.r);
      this.s = this.a((dlj)(new dlj(this.k / 2 - 155, this.l - 52, 150, 20, new of("createWorld.customize.flat.removeLayer"), var1 -> {
         if (this.k()) {
            List<cpe> _snowman = this.c.f();
            int _snowmanx = this.r.au_().indexOf(this.r.h());
            int _snowmanxx = _snowman.size() - _snowmanx - 1;
            _snowman.remove(_snowmanxx);
            this.r.a(_snowman.isEmpty() ? null : this.r.au_().get(Math.min(_snowmanx, _snowman.size() - 1)));
            this.c.h();
            this.r.f();
            this.i();
         }
      })));
      this.a((dlj)(new dlj(this.k / 2 + 5, this.l - 52, 150, 20, new of("createWorld.customize.presets"), var1 -> {
         this.i.a(new doq(this));
         this.c.h();
         this.i();
      })));
      this.a((dlj)(new dlj(this.k / 2 - 155, this.l - 28, 150, 20, nq.c, var1 -> {
         this.b.accept(this.c);
         this.i.a(this.a);
         this.c.h();
      })));
      this.a((dlj)(new dlj(this.k / 2 + 5, this.l - 28, 150, 20, nq.d, var1 -> {
         this.i.a(this.a);
         this.c.h();
      })));
      this.c.h();
      this.i();
   }

   private void i() {
      this.s.o = this.k();
   }

   private boolean k() {
      return this.r.h() != null;
   }

   @Override
   public void at_() {
      this.i.a(this.a);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.r.a(_snowman, _snowman, _snowman, _snowman);
      a(_snowman, this.o, this.d, this.k / 2, 8, 16777215);
      int _snowman = this.k / 2 - 92 - 16;
      b(_snowman, this.o, this.p, _snowman, 32, 16777215);
      b(_snowman, this.o, this.q, _snowman + 2 + 213 - this.o.a(this.q), 32, 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   class a extends dlv<dnv.a.a> {
      public a() {
         super(dnv.this.i, dnv.this.k, dnv.this.l, 43, dnv.this.l - 60, 24);

         for (int _snowman = 0; _snowman < dnv.this.c.f().size(); _snowman++) {
            this.b(new dnv.a.a());
         }
      }

      public void a(@Nullable dnv.a.a var1) {
         super.a(_snowman);
         if (_snowman != null) {
            cpe _snowman = dnv.this.c.f().get(dnv.this.c.f().size() - this.au_().indexOf(_snowman) - 1);
            blx _snowmanx = _snowman.b().b().h();
            if (_snowmanx != bmd.a) {
               dkz.b.a(new of("narrator.select", _snowmanx.h(new bmb(_snowmanx))).getString());
            }
         }

         dnv.this.i();
      }

      @Override
      protected boolean b() {
         return dnv.this.aw_() == this;
      }

      @Override
      protected int e() {
         return this.d - 70;
      }

      public void f() {
         int _snowman = this.au_().indexOf(this.h());
         this.k();

         for (int _snowmanx = 0; _snowmanx < dnv.this.c.f().size(); _snowmanx++) {
            this.b(new dnv.a.a());
         }

         List<dnv.a.a> _snowmanx = this.au_();
         if (_snowman >= 0 && _snowman < _snowmanx.size()) {
            this.a(_snowmanx.get(_snowman));
         }
      }

      class a extends dlv.a<dnv.a.a> {
         private a() {
         }

         @Override
         public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
            cpe _snowman = dnv.this.c.f().get(dnv.this.c.f().size() - _snowman - 1);
            ceh _snowmanx = _snowman.b();
            blx _snowmanxx = _snowmanx.b().h();
            if (_snowmanxx == bmd.a) {
               if (_snowmanx.a(bup.A)) {
                  _snowmanxx = bmd.lL;
               } else if (_snowmanx.a(bup.B)) {
                  _snowmanxx = bmd.lM;
               }
            }

            bmb _snowmanxxx = new bmb(_snowmanxx);
            this.a(_snowman, _snowman, _snowman, _snowmanxxx);
            dnv.this.o.b(_snowman, _snowmanxx.h(_snowmanxxx), (float)(_snowman + 18 + 5), (float)(_snowman + 3), 16777215);
            String _snowmanxxxx;
            if (_snowman == 0) {
               _snowmanxxxx = ekx.a("createWorld.customize.flat.layer.top", _snowman.a());
            } else if (_snowman == dnv.this.c.f().size() - 1) {
               _snowmanxxxx = ekx.a("createWorld.customize.flat.layer.bottom", _snowman.a());
            } else {
               _snowmanxxxx = ekx.a("createWorld.customize.flat.layer", _snowman.a());
            }

            dnv.this.o.b(_snowman, _snowmanxxxx, (float)(_snowman + 2 + 213 - dnv.this.o.b(_snowmanxxxx)), (float)(_snowman + 3), 16777215);
         }

         @Override
         public boolean a(double var1, double var3, int var5) {
            if (_snowman == 0) {
               a.this.a(this);
               return true;
            } else {
               return false;
            }
         }

         private void a(dfm var1, int var2, int var3, bmb var4) {
            this.a(_snowman, _snowman + 1, _snowman + 1);
            RenderSystem.enableRescaleNormal();
            if (!_snowman.a()) {
               dnv.this.j.a(_snowman, _snowman + 2, _snowman + 2);
            }

            RenderSystem.disableRescaleNormal();
         }

         private void a(dfm var1, int var2, int var3) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            a.this.b.M().a(dkw.g);
            dkw.a(_snowman, _snowman, _snowman, dnv.this.v(), 0.0F, 0.0F, 18, 18, 128, 128);
         }
      }
   }
}
