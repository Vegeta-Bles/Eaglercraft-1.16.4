import com.google.common.collect.Lists;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dih extends eoo {
   private static final Logger a = LogManager.getLogger();
   private static final nr b = new of("selectWorld.world");
   private static final nr c = new of("selectWorld.conversion");
   private static final nr p = new of("mco.upload.hardcore").a(k.e);
   private static final nr q = new of("selectWorld.cheats");
   private static final DateFormat r = new SimpleDateFormat();
   private final dif s;
   private final long t;
   private final int u;
   private dlj v;
   private List<cyh> w = Lists.newArrayList();
   private int x = -1;
   private dih.b y;
   private eom z;
   private eom A;
   private eom B;
   private final Runnable C;

   public dih(long var1, int var3, dif var4, Runnable var5) {
      this.s = _snowman;
      this.t = _snowman;
      this.u = _snowman;
      this.C = _snowman;
   }

   private void h() throws Exception {
      this.w = this.i.k().b().stream().sorted((var0, var1) -> {
         if (var0.e() < var1.e()) {
            return 1;
         } else {
            return var0.e() > var1.e() ? -1 : var0.a().compareTo(var1.a());
         }
      }).collect(Collectors.toList());

      for (cyh _snowman : this.w) {
         this.y.a(_snowman);
      }
   }

   @Override
   public void b() {
      this.i.m.a(true);
      this.y = new dih.b();

      try {
         this.h();
      } catch (Exception var2) {
         a.error("Couldn't load level list", var2);
         this.i.a(new dhw(new oe("Unable to load worlds"), nr.a(var2.getMessage()), this.s));
         return;
      }

      this.d(this.y);
      this.v = this.a((dlj)(new dlj(this.k / 2 - 154, this.l - 32, 153, 20, new of("mco.upload.button.name"), var1 -> this.i())));
      this.v.o = this.x >= 0 && this.x < this.w.size();
      this.a((dlj)(new dlj(this.k / 2 + 6, this.l - 32, 153, 20, nq.h, var1 -> this.i.a(this.s))));
      this.z = this.d(new eom(new of("mco.upload.select.world.title"), this.k / 2, 13, 16777215));
      this.A = this.d(new eom(new of("mco.upload.select.world.subtitle"), this.k / 2, j(-1), 10526880));
      if (this.w.isEmpty()) {
         this.B = this.d(new eom(new of("mco.upload.select.world.none"), this.k / 2, this.l / 2 - 20, 16777215));
      } else {
         this.B = null;
      }

      this.A();
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   private void i() {
      if (this.x != -1 && !this.w.get(this.x).h()) {
         cyh _snowman = this.w.get(this.x);
         this.i.a(new din(this.t, this.u, this.s, _snowman, this.C));
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.y.a(_snowman, _snowman, _snowman, _snowman);
      this.z.a(this, _snowman);
      this.A.a(this, _snowman);
      if (this.B != null) {
         this.B.a(this, _snowman);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.i.a(this.s);
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   private static nr c(cyh var0) {
      return _snowman.g().c();
   }

   private static String d(cyh var0) {
      return r.format(new Date(_snowman.e()));
   }

   class a extends dlv.a<dih.a> {
      private final cyh b;
      private final String c;
      private final String d;
      private final nr e;

      public a(cyh var2) {
         this.b = _snowman;
         this.c = _snowman.b();
         this.d = _snowman.a() + " (" + dih.d(_snowman) + ")";
         if (_snowman.d()) {
            this.e = dih.c;
         } else {
            nr _snowman;
            if (_snowman.h()) {
               _snowman = dih.p;
            } else {
               _snowman = dih.c(_snowman);
            }

            if (_snowman.i()) {
               _snowman = _snowman.e().c(", ").a(dih.q);
            }

            this.e = _snowman;
         }
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.a(_snowman, this.b, _snowman, _snowman, _snowman);
      }

      @Override
      public boolean a(double var1, double var3, int var5) {
         dih.this.y.a(dih.this.w.indexOf(this.b));
         return true;
      }

      protected void a(dfm var1, cyh var2, int var3, int var4, int var5) {
         String _snowman;
         if (this.c.isEmpty()) {
            _snowman = dih.b + " " + (_snowman + 1);
         } else {
            _snowman = this.c;
         }

         dih.this.o.b(_snowman, _snowman, (float)(_snowman + 2), (float)(_snowman + 1), 16777215);
         dih.this.o.b(_snowman, this.d, (float)(_snowman + 2), (float)(_snowman + 12), 8421504);
         dih.this.o.b(_snowman, this.e, (float)(_snowman + 2), (float)(_snowman + 12 + 10), 8421504);
      }
   }

   class b extends eon<dih.a> {
      public b() {
         super(dih.this.k, dih.this.l, dih.j(0), dih.this.l - 40, 36);
      }

      public void a(cyh var1) {
         this.a((dih.a)(dih.this.new a(_snowman)));
      }

      @Override
      public int c() {
         return dih.this.w.size() * 36;
      }

      @Override
      public boolean b() {
         return dih.this.aw_() == this;
      }

      @Override
      public void a(dfm var1) {
         dih.this.a(_snowman);
      }

      @Override
      public void a(int var1) {
         this.j(_snowman);
         if (_snowman != -1) {
            cyh _snowman = dih.this.w.get(_snowman);
            String _snowmanx = ekx.a("narrator.select.list.position", _snowman + 1, dih.this.w.size());
            String _snowmanxx = eoj.b(Arrays.asList(_snowman.b(), dih.d(_snowman), dih.c(_snowman).getString(), _snowmanx));
            eoj.a(ekx.a("narrator.select", _snowmanxx));
         }
      }

      public void a(@Nullable dih.a var1) {
         super.a(_snowman);
         dih.this.x = this.au_().indexOf(_snowman);
         dih.this.v.o = dih.this.x >= 0 && dih.this.x < this.l() && !dih.this.w.get(dih.this.x).h();
      }
   }
}
