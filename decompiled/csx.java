import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class csx {
   private byg a;
   private bzm b;
   private fx c;
   private boolean d;
   @Nullable
   private brd e;
   @Nullable
   private cra f;
   private boolean g;
   @Nullable
   private Random h;
   @Nullable
   private int i;
   private final List<csy> j;
   private boolean k;
   private boolean l;

   public csx() {
      this.a = byg.a;
      this.b = bzm.a;
      this.c = fx.b;
      this.g = true;
      this.j = Lists.newArrayList();
   }

   public csx a() {
      csx _snowman = new csx();
      _snowman.a = this.a;
      _snowman.b = this.b;
      _snowman.c = this.c;
      _snowman.d = this.d;
      _snowman.e = this.e;
      _snowman.f = this.f;
      _snowman.g = this.g;
      _snowman.h = this.h;
      _snowman.i = this.i;
      _snowman.j.addAll(this.j);
      _snowman.k = this.k;
      _snowman.l = this.l;
      return _snowman;
   }

   public csx a(byg var1) {
      this.a = _snowman;
      return this;
   }

   public csx a(bzm var1) {
      this.b = _snowman;
      return this;
   }

   public csx a(fx var1) {
      this.c = _snowman;
      return this;
   }

   public csx a(boolean var1) {
      this.d = _snowman;
      return this;
   }

   public csx a(brd var1) {
      this.e = _snowman;
      return this;
   }

   public csx a(cra var1) {
      this.f = _snowman;
      return this;
   }

   public csx a(@Nullable Random var1) {
      this.h = _snowman;
      return this;
   }

   public csx c(boolean var1) {
      this.k = _snowman;
      return this;
   }

   public csx b() {
      this.j.clear();
      return this;
   }

   public csx a(csy var1) {
      this.j.add(_snowman);
      return this;
   }

   public csx b(csy var1) {
      this.j.remove(_snowman);
      return this;
   }

   public byg c() {
      return this.a;
   }

   public bzm d() {
      return this.b;
   }

   public fx e() {
      return this.c;
   }

   public Random b(@Nullable fx var1) {
      if (this.h != null) {
         return this.h;
      } else {
         return _snowman == null ? new Random(x.b()) : new Random(afm.a(_snowman));
      }
   }

   public boolean g() {
      return this.d;
   }

   @Nullable
   public cra h() {
      if (this.f == null && this.e != null) {
         this.k();
      }

      return this.f;
   }

   public boolean i() {
      return this.k;
   }

   public List<csy> j() {
      return this.j;
   }

   void k() {
      if (this.e != null) {
         this.f = this.b(this.e);
      }
   }

   public boolean l() {
      return this.g;
   }

   public ctb.a a(List<ctb.a> var1, @Nullable fx var2) {
      int _snowman = _snowman.size();
      if (_snowman == 0) {
         throw new IllegalStateException("No palettes");
      } else {
         return _snowman.get(this.b(_snowman).nextInt(_snowman));
      }
   }

   @Nullable
   private cra b(@Nullable brd var1) {
      if (_snowman == null) {
         return this.f;
      } else {
         int _snowman = _snowman.b * 16;
         int _snowmanx = _snowman.c * 16;
         return new cra(_snowman, 0, _snowmanx, _snowman + 16 - 1, 255, _snowmanx + 16 - 1);
      }
   }

   public csx d(boolean var1) {
      this.l = _snowman;
      return this;
   }

   public boolean m() {
      return this.l;
   }
}
