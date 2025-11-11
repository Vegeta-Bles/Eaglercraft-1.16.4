import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class bdt extends bcy implements bdd {
   private static final us<Boolean> b = uv.a(bdt.class, uu.i);
   private final apa bo = new apa(5);

   public bdt(aqe<? extends bdt> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void o() {
      super.o();
      this.bk.a(0, new avp(this));
      this.bk.a(2, new bhc.a(this, 10.0F));
      this.bk.a(3, new awx<>(this, 1.0, 8.0F));
      this.bk.a(8, new awt(this, 0.6));
      this.bk.a(9, new awd(this, bfw.class, 15.0F, 1.0F));
      this.bk.a(10, new awd(this, aqn.class, 15.0F));
      this.bl.a(1, new axp(this, bhc.class).a());
      this.bl.a(2, new axq<>(this, bfw.class, true));
      this.bl.a(3, new axq<>(this, bfe.class, false));
      this.bl.a(3, new axq<>(this, bai.class, true));
   }

   public static ark.a eK() {
      return bdq.eR().a(arl.d, 0.35F).a(arl.a, 24.0).a(arl.f, 5.0).a(arl.b, 32.0);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, false);
   }

   @Override
   public boolean a(bmo var1) {
      return _snowman == bmd.qQ;
   }

   public boolean eM() {
      return this.R.a(b);
   }

   @Override
   public void b(boolean var1) {
      this.R.b(b, _snowman);
   }

   @Override
   public void U_() {
      this.aI = 0;
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      mj _snowman = new mj();

      for (int _snowmanx = 0; _snowmanx < this.bo.Z_(); _snowmanx++) {
         bmb _snowmanxx = this.bo.a(_snowmanx);
         if (!_snowmanxx.a()) {
            _snowman.add(_snowmanxx.b(new md()));
         }
      }

      _snowman.a("Inventory", _snowman);
   }

   @Override
   public bcy.a m() {
      if (this.eM()) {
         return bcy.a.f;
      } else if (this.a(bmd.qQ)) {
         return bcy.a.e;
      } else {
         return this.eF() ? bcy.a.b : bcy.a.h;
      }
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      mj _snowman = _snowman.d("Inventory", 10);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         bmb _snowmanxx = bmb.a(_snowman.a(_snowmanx));
         if (!_snowmanxx.a()) {
            this.bo.a(_snowmanxx);
         }
      }

      this.p(true);
   }

   @Override
   public float a(fx var1, brz var2) {
      ceh _snowman = _snowman.d_(_snowman.c());
      return !_snowman.a(bup.i) && !_snowman.a(bup.C) ? 0.5F - _snowman.y(_snowman) : 10.0F;
   }

   @Override
   public int eq() {
      return 1;
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      this.a(_snowman);
      this.b(_snowman);
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected void a(aos var1) {
      this.a(aqf.a, new bmb(bmd.qQ));
   }

   @Override
   protected void w(float var1) {
      super.w(_snowman);
      if (this.J.nextInt(300) == 0) {
         bmb _snowman = this.dD();
         if (_snowman.b() == bmd.qQ) {
            Map<bps, Integer> _snowmanx = bpu.a(_snowman);
            _snowmanx.putIfAbsent(bpw.J, 1);
            bpu.a(_snowmanx, _snowman);
            this.a(aqf.a, _snowman);
         }
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
   protected adp I() {
      return adq.li;
   }

   @Override
   protected adp dq() {
      return adq.lk;
   }

   @Override
   protected adp e(apk var1) {
      return adq.ll;
   }

   @Override
   public void a(aqm var1, float var2) {
      this.b(this, 1.6F);
   }

   @Override
   public void a(aqm var1, bmb var2, bgm var3, float var4) {
      this.a(this, _snowman, _snowman, _snowman, 1.6F);
   }

   @Override
   protected void b(bcv var1) {
      bmb _snowman = _snowman.g();
      if (_snowman.b() instanceof bke) {
         super.b(_snowman);
      } else {
         blx _snowmanx = _snowman.b();
         if (this.b(_snowmanx)) {
            this.a(_snowman);
            bmb _snowmanxx = this.bo.a(_snowman);
            if (_snowmanxx.a()) {
               _snowman.ad();
            } else {
               _snowman.e(_snowmanxx.E());
            }
         }
      }
   }

   private boolean b(blx var1) {
      return this.fb() && _snowman == bmd.pM;
   }

   @Override
   public boolean a_(int var1, bmb var2) {
      if (super.a_(_snowman, _snowman)) {
         return true;
      } else {
         int _snowman = _snowman - 300;
         if (_snowman >= 0 && _snowman < this.bo.Z_()) {
            this.bo.a(_snowman, _snowman);
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public void a(int var1, boolean var2) {
      bhb _snowman = this.fa();
      boolean _snowmanx = this.J.nextFloat() <= _snowman.w();
      if (_snowmanx) {
         bmb _snowmanxx = new bmb(bmd.qQ);
         Map<bps, Integer> _snowmanxxx = Maps.newHashMap();
         if (_snowman > _snowman.a(aor.c)) {
            _snowmanxxx.put(bpw.I, 2);
         } else if (_snowman > _snowman.a(aor.b)) {
            _snowmanxxx.put(bpw.I, 1);
         }

         _snowmanxxx.put(bpw.H, 1);
         bpu.a(_snowmanxxx, _snowmanxx);
         this.a(aqf.a, _snowmanxx);
      }
   }

   @Override
   public adp eL() {
      return adq.lj;
   }
}
