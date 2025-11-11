import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class bps {
   private final aqf[] a;
   private final bps.a d;
   public final bpt b;
   @Nullable
   protected String c;

   @Nullable
   public static bps c(int var0) {
      return gm.R.a(_snowman);
   }

   protected bps(bps.a var1, bpt var2, aqf[] var3) {
      this.d = _snowman;
      this.b = _snowman;
      this.a = _snowman;
   }

   public Map<aqf, bmb> a(aqm var1) {
      Map<aqf, bmb> _snowman = Maps.newEnumMap(aqf.class);

      for (aqf _snowmanx : this.a) {
         bmb _snowmanxx = _snowman.b(_snowmanx);
         if (!_snowmanxx.a()) {
            _snowman.put(_snowmanx, _snowmanxx);
         }
      }

      return _snowman;
   }

   public bps.a d() {
      return this.d;
   }

   public int e() {
      return 1;
   }

   public int a() {
      return 1;
   }

   public int a(int var1) {
      return 1 + _snowman * 10;
   }

   public int b(int var1) {
      return this.a(_snowman) + 5;
   }

   public int a(int var1, apk var2) {
      return 0;
   }

   public float a(int var1, aqq var2) {
      return 0.0F;
   }

   public final boolean b(bps var1) {
      return this.a(_snowman) && _snowman.a(this);
   }

   protected boolean a(bps var1) {
      return this != _snowman;
   }

   protected String f() {
      if (this.c == null) {
         this.c = x.a("enchantment", gm.R.b(this));
      }

      return this.c;
   }

   public String g() {
      return this.f();
   }

   public nr d(int var1) {
      nx _snowman = new of(this.g());
      if (this.c()) {
         _snowman.a(k.m);
      } else {
         _snowman.a(k.h);
      }

      if (_snowman != 1 || this.a() != 1) {
         _snowman.c(" ").a(new of("enchantment.level." + _snowman));
      }

      return _snowman;
   }

   public boolean a(bmb var1) {
      return this.b.a(_snowman.b());
   }

   public void a(aqm var1, aqa var2, int var3) {
   }

   public void b(aqm var1, aqa var2, int var3) {
   }

   public boolean b() {
      return false;
   }

   public boolean c() {
      return false;
   }

   public boolean h() {
      return true;
   }

   public boolean i() {
      return true;
   }

   public static enum a {
      a(10),
      b(5),
      c(2),
      d(1);

      private final int e;

      private a(int var3) {
         this.e = _snowman;
      }

      public int a() {
         return this.e;
      }
   }
}
