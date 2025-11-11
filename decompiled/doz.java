import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;

public class doz extends dol {
   private static final nr c = new of("options.graphics.fabulous").a(k.u);
   private static final nr p = new of("options.graphics.warning.message", c, c);
   private static final nr q = new of("options.graphics.warning.title").a(k.m);
   private static final nr r = new of("options.graphics.warning.accept");
   private static final nr s = new of("options.graphics.warning.cancel");
   private static final nr t = new oe("\n");
   private static final dkc[] u = new dkc[]{
      dkc.x, dkc.q, dkc.u, dkc.l, dkc.L, dkc.X, dkc.y, dkc.v, dkc.m, dkc.C, dkc.W, dkc.B, dkc.n, dkc.M, dkc.k, dkc.r, dkc.j
   };
   private dlx v;
   private final eaa w;
   private final int x;

   public doz(dot var1, dkd var2) {
      super(_snowman, _snowman, new of("options.videoTitle"));
      this.w = _snowman.i.V();
      this.w.i();
      if (_snowman.f == djt.c) {
         this.w.e();
      }

      this.x = _snowman.A;
   }

   @Override
   protected void b() {
      this.v = new dlx(this.i, this.k, this.l, 32, this.l - 32, 25);
      this.v.a(new djr(this.i.aD()));
      this.v.a(dkc.a);
      this.v.a(u);
      this.e.add(this.v);
      this.a(new dlj(this.k / 2 - 100, this.l - 27, 200, 20, nq.c, var1 -> {
         this.i.k.b();
         this.i.aD().g();
         this.i.a(this.a);
      }));
   }

   @Override
   public void e() {
      if (this.b.A != this.x) {
         this.i.b(this.b.A);
         this.i.D();
      }

      super.e();
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      int _snowman = this.b.aS;
      if (super.a(_snowman, _snowman, _snowman)) {
         if (this.b.aS != _snowman) {
            this.i.a();
         }

         if (this.w.g()) {
            List<nu> _snowmanx = Lists.newArrayList(new nu[]{p, t});
            String _snowmanxx = this.w.j();
            if (_snowmanxx != null) {
               _snowmanx.add(t);
               _snowmanx.add(new of("options.graphics.warning.renderer", _snowmanxx).a(k.h));
            }

            String _snowmanxxx = this.w.l();
            if (_snowmanxxx != null) {
               _snowmanx.add(t);
               _snowmanx.add(new of("options.graphics.warning.vendor", _snowmanxxx).a(k.h));
            }

            String _snowmanxxxx = this.w.k();
            if (_snowmanxxxx != null) {
               _snowmanx.add(t);
               _snowmanx.add(new of("options.graphics.warning.version", _snowmanxxxx).a(k.h));
            }

            this.i.a(new dop(q, _snowmanx, ImmutableList.of(new dop.a(r, var1x -> {
               this.b.f = djt.c;
               djz.C().e.e();
               this.w.e();
               this.i.a(this);
            }), new dop.a(s, var1x -> {
               this.w.f();
               this.i.a(this);
            }))));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean c(double var1, double var3, int var5) {
      int _snowman = this.b.aS;
      if (super.c(_snowman, _snowman, _snowman)) {
         return true;
      } else if (this.v.c(_snowman, _snowman, _snowman)) {
         if (this.b.aS != _snowman) {
            this.i.a();
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.v.a(_snowman, _snowman, _snowman, _snowman);
      a(_snowman, this.o, this.d, this.k / 2, 5, 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
      List<afa> _snowman = a(this.v, _snowman, _snowman);
      if (_snowman != null) {
         this.c(_snowman, _snowman, _snowman, _snowman);
      }
   }
}
