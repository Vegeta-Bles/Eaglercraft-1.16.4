import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.List;
import java.util.UUID;

public class bjy extends blx implements bnq {
   private static final UUID[] j = new UUID[]{
      UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
      UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
      UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
      UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
   };
   public static final gw a = new gv() {
      @Override
      protected bmb a(fy var1, bmb var2) {
         return bjy.a(_snowman, _snowman) ? _snowman : super.a(_snowman, _snowman);
      }
   };
   protected final aqf b;
   private final int k;
   private final float l;
   protected final float c;
   protected final bjz d;
   private final Multimap<arg, arj> m;

   public static boolean a(fy var0, bmb var1) {
      fx _snowman = _snowman.d().a(_snowman.e().c(bwa.a));
      List<aqm> _snowmanx = _snowman.h().a(aqm.class, new dci(_snowman), aqd.g.and(new aqd.a(_snowman)));
      if (_snowmanx.isEmpty()) {
         return false;
      } else {
         aqm _snowmanxx = _snowmanx.get(0);
         aqf _snowmanxxx = aqn.j(_snowman);
         bmb _snowmanxxxx = _snowman.a(1);
         _snowmanxx.a(_snowmanxxx, _snowmanxxxx);
         if (_snowmanxx instanceof aqn) {
            ((aqn)_snowmanxx).a(_snowmanxxx, 2.0F);
            ((aqn)_snowmanxx).es();
         }

         return true;
      }
   }

   public bjy(bjz var1, aqf var2, blx.a var3) {
      super(_snowman.b(_snowman.a(_snowman)));
      this.d = _snowman;
      this.b = _snowman;
      this.k = _snowman.b(_snowman);
      this.l = _snowman.e();
      this.c = _snowman.f();
      bwa.a(this, a);
      Builder<arg, arj> _snowman = ImmutableMultimap.builder();
      UUID _snowmanx = j[_snowman.b()];
      _snowman.put(arl.i, new arj(_snowmanx, "Armor modifier", (double)this.k, arj.a.a));
      _snowman.put(arl.j, new arj(_snowmanx, "Armor toughness", (double)this.l, arj.a.a));
      if (_snowman == bka.g) {
         _snowman.put(arl.c, new arj(_snowmanx, "Armor knockback resistance", (double)this.c, arj.a.a));
      }

      this.m = _snowman.build();
   }

   public aqf b() {
      return this.b;
   }

   @Override
   public int c() {
      return this.d.a();
   }

   public bjz ab_() {
      return this.d;
   }

   @Override
   public boolean a(bmb var1, bmb var2) {
      return this.d.c().a(_snowman) || super.a(_snowman, _snowman);
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      aqf _snowmanx = aqn.j(_snowman);
      bmb _snowmanxx = _snowman.b(_snowmanx);
      if (_snowmanxx.a()) {
         _snowman.a(_snowmanx, _snowman.i());
         _snowman.e(0);
         return aov.a(_snowman, _snowman.s_());
      } else {
         return aov.d(_snowman);
      }
   }

   @Override
   public Multimap<arg, arj> a(aqf var1) {
      return _snowman == this.b ? this.m : super.a(_snowman);
   }

   public int e() {
      return this.k;
   }

   public float f() {
      return this.l;
   }
}
