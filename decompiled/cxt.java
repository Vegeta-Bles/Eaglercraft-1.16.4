import java.util.Objects;
import javax.annotation.Nullable;

public class cxt {
   private final fx a;
   private final bkx b;
   @Nullable
   private final nr c;

   public cxt(fx var1, bkx var2, @Nullable nr var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public static cxt a(md var0) {
      fx _snowman = mp.b(_snowman.p("Pos"));
      bkx _snowmanx = bkx.a(_snowman.l("Color"), bkx.a);
      nr _snowmanxx = _snowman.e("Name") ? nr.a.a(_snowman.l("Name")) : null;
      return new cxt(_snowman, _snowmanx, _snowmanxx);
   }

   @Nullable
   public static cxt a(brc var0, fx var1) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof cca) {
         cca _snowmanx = (cca)_snowman;
         bkx _snowmanxx = _snowmanx.a(() -> _snowman.d_(_snowman));
         nr _snowmanxxx = _snowmanx.S() ? _snowmanx.T() : null;
         return new cxt(_snowman, _snowmanxx, _snowmanxxx);
      } else {
         return null;
      }
   }

   public fx a() {
      return this.a;
   }

   public cxu.a c() {
      switch (this.b) {
         case a:
            return cxu.a.k;
         case b:
            return cxu.a.l;
         case c:
            return cxu.a.m;
         case d:
            return cxu.a.n;
         case e:
            return cxu.a.o;
         case f:
            return cxu.a.p;
         case g:
            return cxu.a.q;
         case h:
            return cxu.a.r;
         case i:
            return cxu.a.s;
         case j:
            return cxu.a.t;
         case k:
            return cxu.a.u;
         case l:
            return cxu.a.v;
         case m:
            return cxu.a.w;
         case n:
            return cxu.a.x;
         case o:
            return cxu.a.y;
         case p:
         default:
            return cxu.a.z;
      }
   }

   @Nullable
   public nr d() {
      return this.c;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         cxt _snowman = (cxt)_snowman;
         return Objects.equals(this.a, _snowman.a) && this.b == _snowman.b && Objects.equals(this.c, _snowman.c);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.a, this.b, this.c);
   }

   public md e() {
      md _snowman = new md();
      _snowman.a("Pos", mp.a(this.a));
      _snowman.a("Color", this.b.c());
      if (this.c != null) {
         _snowman.a("Name", nr.a.a(this.c));
      }

      return _snowman;
   }

   public String f() {
      return "banner-" + this.a.u() + "," + this.a.v() + "," + this.a.w();
   }
}
