import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public class blx implements brw {
   public static final Map<buo, blx> e = Maps.newHashMap();
   protected static final UUID f = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
   protected static final UUID g = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
   protected static final Random h = new Random();
   protected final bks i;
   private final bmp a;
   private final int b;
   private final int c;
   private final boolean d;
   private final blx j;
   @Nullable
   private String k;
   @Nullable
   private final bhz l;

   public static int a(blx var0) {
      return _snowman == null ? 0 : gm.T.a(_snowman);
   }

   public static blx b(int var0) {
      return gm.T.a(_snowman);
   }

   @Deprecated
   public static blx a(buo var0) {
      return e.getOrDefault(_snowman, bmd.a);
   }

   public blx(blx.a var1) {
      this.i = _snowman.d;
      this.a = _snowman.e;
      this.j = _snowman.c;
      this.c = _snowman.b;
      this.b = _snowman.a;
      this.l = _snowman.f;
      this.d = _snowman.g;
   }

   public void a(brx var1, aqm var2, bmb var3, int var4) {
   }

   public boolean b(md var1) {
      return false;
   }

   public boolean a(ceh var1, brx var2, fx var3, bfw var4) {
      return true;
   }

   @Override
   public blx h() {
      return this;
   }

   public aou a(boa var1) {
      return aou.c;
   }

   public float a(bmb var1, ceh var2) {
      return 1.0F;
   }

   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      if (this.s()) {
         bmb _snowman = _snowman.b(_snowman);
         if (_snowman.q(this.t().d())) {
            _snowman.c(_snowman);
            return aov.b(_snowman);
         } else {
            return aov.d(_snowman);
         }
      } else {
         return aov.c(_snowman.b(_snowman));
      }
   }

   public bmb a(bmb var1, brx var2, aqm var3) {
      return this.s() ? _snowman.a(_snowman, _snowman) : _snowman;
   }

   public final int i() {
      return this.b;
   }

   public final int j() {
      return this.c;
   }

   public boolean k() {
      return this.c > 0;
   }

   public boolean a(bmb var1, aqm var2, aqm var3) {
      return false;
   }

   public boolean a(bmb var1, brx var2, ceh var3, fx var4, aqm var5) {
      return false;
   }

   public boolean b(ceh var1) {
      return false;
   }

   public aou a(bmb var1, bfw var2, aqm var3, aot var4) {
      return aou.c;
   }

   public nr l() {
      return new of(this.a());
   }

   @Override
   public String toString() {
      return gm.T.b(this).a();
   }

   protected String m() {
      if (this.k == null) {
         this.k = x.a("item", gm.T.b(this));
      }

      return this.k;
   }

   public String a() {
      return this.m();
   }

   public String f(bmb var1) {
      return this.a();
   }

   public boolean n() {
      return true;
   }

   @Nullable
   public final blx o() {
      return this.j;
   }

   public boolean p() {
      return this.j != null;
   }

   public void a(bmb var1, brx var2, aqa var3, int var4, boolean var5) {
   }

   public void b(bmb var1, brx var2, bfw var3) {
   }

   public boolean ac_() {
      return false;
   }

   public bnn d_(bmb var1) {
      return _snowman.b().s() ? bnn.b : bnn.a;
   }

   public int e_(bmb var1) {
      if (_snowman.b().s()) {
         return this.t().e() ? 16 : 32;
      } else {
         return 0;
      }
   }

   public void a(bmb var1, brx var2, aqm var3, int var4) {
   }

   public void a(bmb var1, @Nullable brx var2, List<nr> var3, bnl var4) {
   }

   public nr h(bmb var1) {
      return new of(this.f(_snowman));
   }

   public boolean e(bmb var1) {
      return _snowman.x();
   }

   public bmp i(bmb var1) {
      if (!_snowman.x()) {
         return this.a;
      } else {
         switch (this.a) {
            case a:
            case b:
               return bmp.c;
            case c:
               return bmp.d;
            case d:
            default:
               return this.a;
         }
      }
   }

   public boolean f_(bmb var1) {
      return this.i() == 1 && this.k();
   }

   protected static dcj a(brx var0, bfw var1, brf.b var2) {
      float _snowman = _snowman.q;
      float _snowmanx = _snowman.p;
      dcn _snowmanxx = _snowman.j(1.0F);
      float _snowmanxxx = afm.b(-_snowmanx * (float) (Math.PI / 180.0) - (float) Math.PI);
      float _snowmanxxxx = afm.a(-_snowmanx * (float) (Math.PI / 180.0) - (float) Math.PI);
      float _snowmanxxxxx = -afm.b(-_snowman * (float) (Math.PI / 180.0));
      float _snowmanxxxxxx = afm.a(-_snowman * (float) (Math.PI / 180.0));
      float _snowmanxxxxxxx = _snowmanxxxx * _snowmanxxxxx;
      float _snowmanxxxxxxxx = _snowmanxxx * _snowmanxxxxx;
      double _snowmanxxxxxxxxx = 5.0;
      dcn _snowmanxxxxxxxxxx = _snowmanxx.b((double)_snowmanxxxxxxx * 5.0, (double)_snowmanxxxxxx * 5.0, (double)_snowmanxxxxxxxx * 5.0);
      return _snowman.a(new brf(_snowmanxx, _snowmanxxxxxxxxxx, brf.a.b, _snowman, _snowman));
   }

   public int c() {
      return 0;
   }

   public void a(bks var1, gj<bmb> var2) {
      if (this.a(_snowman)) {
         _snowman.add(new bmb(this));
      }
   }

   protected boolean a(bks var1) {
      bks _snowman = this.q();
      return _snowman != null && (_snowman == bks.g || _snowman == _snowman);
   }

   @Nullable
   public final bks q() {
      return this.i;
   }

   public boolean a(bmb var1, bmb var2) {
      return false;
   }

   public Multimap<arg, arj> a(aqf var1) {
      return ImmutableMultimap.of();
   }

   public boolean j(bmb var1) {
      return _snowman.b() == bmd.qQ;
   }

   public bmb r() {
      return new bmb(this);
   }

   public boolean a(ael<blx> var1) {
      return _snowman.a(this);
   }

   public boolean s() {
      return this.l != null;
   }

   @Nullable
   public bhz t() {
      return this.l;
   }

   public adp ae_() {
      return adq.eJ;
   }

   public adp ad_() {
      return adq.eK;
   }

   public boolean u() {
      return this.d;
   }

   public boolean a(apk var1) {
      return !this.d || !_snowman.p();
   }

   public static class a {
      private int a = 64;
      private int b;
      private blx c;
      private bks d;
      private bmp e = bmp.a;
      private bhz f;
      private boolean g;

      public a() {
      }

      public blx.a a(bhz var1) {
         this.f = _snowman;
         return this;
      }

      public blx.a a(int var1) {
         if (this.b > 0) {
            throw new RuntimeException("Unable to have damage AND stack.");
         } else {
            this.a = _snowman;
            return this;
         }
      }

      public blx.a b(int var1) {
         return this.b == 0 ? this.c(_snowman) : this;
      }

      public blx.a c(int var1) {
         this.b = _snowman;
         this.a = 1;
         return this;
      }

      public blx.a a(blx var1) {
         this.c = _snowman;
         return this;
      }

      public blx.a a(bks var1) {
         this.d = _snowman;
         return this;
      }

      public blx.a a(bmp var1) {
         this.e = _snowman;
         return this;
      }

      public blx.a a() {
         this.g = true;
         return this;
      }
   }
}
