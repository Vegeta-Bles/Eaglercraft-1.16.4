import java.io.File;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class cyh implements Comparable<cyh> {
   private final bsa a;
   private final cyi b;
   private final String c;
   private final boolean d;
   private final boolean e;
   private final File f;
   @Nullable
   private nr g;

   public cyh(bsa var1, cyi var2, String var3, boolean var4, boolean var5, File var6) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.d = _snowman;
   }

   public String a() {
      return this.c;
   }

   public String b() {
      return StringUtils.isEmpty(this.a.a()) ? this.c : this.a.a();
   }

   public File c() {
      return this.f;
   }

   public boolean d() {
      return this.d;
   }

   public long e() {
      return this.b.b();
   }

   public int a(cyh var1) {
      if (this.b.b() < _snowman.b.b()) {
         return 1;
      } else {
         return this.b.b() > _snowman.b.b() ? -1 : this.c.compareTo(_snowman.c);
      }
   }

   public bru g() {
      return this.a.b();
   }

   public boolean h() {
      return this.a.c();
   }

   public boolean i() {
      return this.a.e();
   }

   public nx j() {
      return (nx)(aft.b(this.b.c()) ? new of("selectWorld.versionUnknown") : new oe(this.b.c()));
   }

   public cyi k() {
      return this.b;
   }

   public boolean l() {
      return this.m() || !w.a().isStable() && !this.b.e() || this.n();
   }

   public boolean m() {
      return this.b.d() > w.a().getWorldVersion();
   }

   public boolean n() {
      return this.b.d() < w.a().getWorldVersion();
   }

   public boolean o() {
      return this.e;
   }

   public nr p() {
      if (this.g == null) {
         this.g = this.q();
      }

      return this.g;
   }

   private nr q() {
      if (this.o()) {
         return new of("selectWorld.locked").a(k.m);
      } else if (this.d()) {
         return new of("selectWorld.conversion");
      } else {
         nx _snowman = (nx)(this.h() ? new oe("").a(new of("gameMode.hardcore").a(k.e)) : new of("gameMode." + this.g().b()));
         if (this.i()) {
            _snowman.c(", ").a(new of("selectWorld.cheats"));
         }

         nx _snowmanx = this.j();
         nx _snowmanxx = new oe(", ").a(new of("selectWorld.version")).c(" ");
         if (this.l()) {
            _snowmanxx.a(_snowmanx.a(this.m() ? k.m : k.u));
         } else {
            _snowmanxx.a(_snowmanx);
         }

         _snowman.a(_snowmanxx);
         return _snowman;
      }
   }
}
