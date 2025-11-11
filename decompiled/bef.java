import com.google.common.collect.Maps;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bef extends bcy {
   private static final Predicate<aor> b = var0 -> var0 == aor.c || var0 == aor.d;
   private boolean bo;

   public bef(aqe<? extends bef> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void o() {
      super.o();
      this.bk.a(0, new avp(this));
      this.bk.a(1, new bef.a(this));
      this.bk.a(2, new bcy.b(this));
      this.bk.a(3, new bhc.a(this, 10.0F));
      this.bk.a(4, new bef.c(this));
      this.bl.a(1, new axp(this, bhc.class).a());
      this.bl.a(2, new axq<>(this, bfw.class, true));
      this.bl.a(3, new axq<>(this, bfe.class, true));
      this.bl.a(3, new axq<>(this, bai.class, true));
      this.bl.a(4, new bef.b(this));
      this.bk.a(8, new awt(this, 0.6));
      this.bk.a(9, new awd(this, bfw.class, 3.0F, 1.0F));
      this.bk.a(10, new awd(this, aqn.class, 8.0F));
   }

   @Override
   protected void N() {
      if (!this.eD() && azi.a(this)) {
         boolean _snowman = ((aag)this.l).c_(this.cB());
         ((ayi)this.x()).a(_snowman);
      }

      super.N();
   }

   public static ark.a eK() {
      return bdq.eR().a(arl.d, 0.35F).a(arl.b, 12.0).a(arl.a, 24.0).a(arl.f, 5.0);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      if (this.bo) {
         _snowman.a("Johnny", true);
      }
   }

   @Override
   public bcy.a m() {
      if (this.eF()) {
         return bcy.a.b;
      } else {
         return this.fd() ? bcy.a.g : bcy.a.a;
      }
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("Johnny", 99)) {
         this.bo = _snowman.q("Johnny");
      }
   }

   @Override
   public adp eL() {
      return adq.qn;
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      arc _snowman = super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      ((ayi)this.x()).a(true);
      this.a(_snowman);
      this.b(_snowman);
      return _snowman;
   }

   @Override
   protected void a(aos var1) {
      if (this.fa() == null) {
         this.a(aqf.a, new bmb(bmd.kD));
      }
   }

   @Override
   public boolean r(aqa var1) {
      if (super.r(_snowman)) {
         return true;
      } else {
         return _snowman instanceof aqm && ((aqm)_snowman).dC() == aqq.d ? this.bG() == null && _snowman.bG() == null : false;
      }
   }

   @Override
   public void a(@Nullable nr var1) {
      super.a(_snowman);
      if (!this.bo && _snowman != null && _snowman.getString().equals("Johnny")) {
         this.bo = true;
      }
   }

   @Override
   protected adp I() {
      return adq.qm;
   }

   @Override
   protected adp dq() {
      return adq.qo;
   }

   @Override
   protected adp e(apk var1) {
      return adq.qp;
   }

   @Override
   public void a(int var1, boolean var2) {
      bmb _snowman = new bmb(bmd.kD);
      bhb _snowmanx = this.fa();
      int _snowmanxx = 1;
      if (_snowman > _snowmanx.a(aor.c)) {
         _snowmanxx = 2;
      }

      boolean _snowmanxxx = this.J.nextFloat() <= _snowmanx.w();
      if (_snowmanxxx) {
         Map<bps, Integer> _snowmanxxxx = Maps.newHashMap();
         _snowmanxxxx.put(bpw.m, _snowmanxx);
         bpu.a(_snowmanxxxx, _snowman);
      }

      this.a(aqf.a, _snowman);
   }

   static class a extends avg {
      public a(aqn var1) {
         super(_snowman, 6, bef.b);
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean b() {
         bef _snowman = (bef)this.d;
         return _snowman.fb() && super.b();
      }

      @Override
      public boolean a() {
         bef _snowman = (bef)this.d;
         return _snowman.fb() && _snowman.J.nextInt(10) == 0 && super.a();
      }

      @Override
      public void c() {
         super.c();
         this.d.n(0);
      }
   }

   static class b extends axq<aqm> {
      public b(bef var1) {
         super(_snowman, aqm.class, 0, true, true, aqm::ei);
      }

      @Override
      public boolean a() {
         return ((bef)this.e).bo && super.a();
      }

      @Override
      public void c() {
         super.c();
         this.e.n(0);
      }
   }

   class c extends awf {
      public c(bef var2) {
         super(_snowman, 1.0, false);
      }

      @Override
      protected double a(aqm var1) {
         if (this.a.ct() instanceof bdv) {
            float _snowman = this.a.ct().cy() - 0.1F;
            return (double)(_snowman * 2.0F * _snowman * 2.0F + _snowman.cy());
         } else {
            return super.a(_snowman);
         }
      }
   }
}
