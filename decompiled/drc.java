import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class drc extends dot {
   private static final Logger b = LogManager.getLogger();
   private final dxb c = new dxb();
   private final dot p;
   protected dre a;
   private dxa q;
   private dlj r;
   private dlj s;
   private dlj t;
   private List<nr> u;
   private dwz v;
   private eni.b w;
   private eni.a x;
   private boolean y;

   public drc(dot var1) {
      super(new of("multiplayer.title"));
      this.p = _snowman;
   }

   @Override
   protected void b() {
      super.b();
      this.i.m.a(true);
      if (this.y) {
         this.a.a(this.k, this.l, 32, this.l - 64);
      } else {
         this.y = true;
         this.q = new dxa(this.i);
         this.q.a();
         this.w = new eni.b();

         try {
            this.x = new eni.a(this.w);
            this.x.start();
         } catch (Exception var2) {
            b.warn("Unable to start LAN server detection: {}", var2.getMessage());
         }

         this.a = new dre(this, this.i, this.k, this.l, 32, this.l - 64, 36);
         this.a.a(this.q);
      }

      this.e.add(this.a);
      this.s = this.a((dlj)(new dlj(this.k / 2 - 154, this.l - 52, 100, 20, new of("selectServer.select"), var1 -> this.h())));
      this.a((dlj)(new dlj(this.k / 2 - 50, this.l - 52, 100, 20, new of("selectServer.direct"), var1 -> {
         this.v = new dwz(ekx.a("selectServer.defaultName"), "", false);
         this.i.a(new dnz(this, this::f, this.v));
      })));
      this.a((dlj)(new dlj(this.k / 2 + 4 + 50, this.l - 52, 100, 20, new of("selectServer.add"), var1 -> {
         this.v = new dwz(ekx.a("selectServer.defaultName"), "", false);
         this.i.a(new dob(this, this::e, this.v));
      })));
      this.r = this.a((dlj)(new dlj(this.k / 2 - 154, this.l - 28, 70, 20, new of("selectServer.edit"), var1 -> {
         dre.a _snowman = this.a.h();
         if (_snowman instanceof dre.d) {
            dwz _snowmanx = ((dre.d)_snowman).b();
            this.v = new dwz(_snowmanx.a, _snowmanx.b, false);
            this.v.a(_snowmanx);
            this.i.a(new dob(this, this::d, this.v));
         }
      })));
      this.t = this.a((dlj)(new dlj(this.k / 2 - 74, this.l - 28, 70, 20, new of("selectServer.delete"), var1 -> {
         dre.a _snowman = this.a.h();
         if (_snowman instanceof dre.d) {
            String _snowmanx = ((dre.d)_snowman).b().a;
            if (_snowmanx != null) {
               nr _snowmanxx = new of("selectServer.deleteQuestion");
               nr _snowmanxxx = new of("selectServer.deleteWarning", _snowmanx);
               nr _snowmanxxxx = new of("selectServer.deleteButton");
               nr _snowmanxxxxx = nq.d;
               this.i.a(new dns(this::c, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx));
            }
         }
      })));
      this.a((dlj)(new dlj(this.k / 2 + 4, this.l - 28, 70, 20, new of("selectServer.refresh"), var1 -> this.m())));
      this.a((dlj)(new dlj(this.k / 2 + 4 + 76, this.l - 28, 75, 20, nq.d, var1 -> this.i.a(this.p))));
      this.i();
   }

   @Override
   public void d() {
      super.d();
      if (this.w.a()) {
         List<enh> _snowman = this.w.c();
         this.w.b();
         this.a.a(_snowman);
      }

      this.c.a();
   }

   @Override
   public void e() {
      this.i.m.a(false);
      if (this.x != null) {
         this.x.interrupt();
         this.x = null;
      }

      this.c.b();
   }

   private void m() {
      this.i.a(new drc(this.p));
   }

   private void c(boolean var1) {
      dre.a _snowman = this.a.h();
      if (_snowman && _snowman instanceof dre.d) {
         this.q.a(((dre.d)_snowman).b());
         this.q.b();
         this.a.a(null);
         this.a.a(this.q);
      }

      this.i.a(this);
   }

   private void d(boolean var1) {
      dre.a _snowman = this.a.h();
      if (_snowman && _snowman instanceof dre.d) {
         dwz _snowmanx = ((dre.d)_snowman).b();
         _snowmanx.a = this.v.a;
         _snowmanx.b = this.v.b;
         _snowmanx.a(this.v);
         this.q.b();
         this.a.a(this.q);
      }

      this.i.a(this);
   }

   private void e(boolean var1) {
      if (_snowman) {
         this.q.b(this.v);
         this.q.b();
         this.a.a(null);
         this.a.a(this.q);
      }

      this.i.a(this);
   }

   private void f(boolean var1) {
      if (_snowman) {
         this.a(this.v);
      } else {
         this.i.a(this);
      }
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (super.a(_snowman, _snowman, _snowman)) {
         return true;
      } else if (_snowman == 294) {
         this.m();
         return true;
      } else if (this.a.h() != null) {
         if (_snowman != 257 && _snowman != 335) {
            return this.a.a(_snowman, _snowman, _snowman);
         } else {
            this.h();
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.u = null;
      this.a(_snowman);
      this.a.a(_snowman, _snowman, _snowman, _snowman);
      a(_snowman, this.o, this.d, this.k / 2, 20, 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
      if (this.u != null) {
         this.b(_snowman, this.u, _snowman, _snowman);
      }
   }

   public void h() {
      dre.a _snowman = this.a.h();
      if (_snowman instanceof dre.d) {
         this.a(((dre.d)_snowman).b());
      } else if (_snowman instanceof dre.c) {
         enh _snowmanx = ((dre.c)_snowman).a();
         this.a(new dwz(_snowmanx.a(), _snowmanx.b(), true));
      }
   }

   private void a(dwz var1) {
      this.i.a(new dnt(this, this.i, _snowman));
   }

   public void a(dre.a var1) {
      this.a.a(_snowman);
      this.i();
   }

   protected void i() {
      this.s.o = false;
      this.r.o = false;
      this.t.o = false;
      dre.a _snowman = this.a.h();
      if (_snowman != null && !(_snowman instanceof dre.b)) {
         this.s.o = true;
         if (_snowman instanceof dre.d) {
            this.r.o = true;
            this.t.o = true;
         }
      }
   }

   public dxb k() {
      return this.c;
   }

   public void b(List<nr> var1) {
      this.u = _snowman;
   }

   public dxa l() {
      return this.q;
   }
}
