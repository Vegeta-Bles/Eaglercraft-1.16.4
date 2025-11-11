import java.util.Map;

public class bxn extends buo {
   public static final cey a = bys.a;
   public static final cey b = bys.b;
   public static final cey c = bys.c;
   public static final cey d = bys.d;
   public static final cey e = bys.e;
   public static final cey f = bys.f;
   private static final Map<gc, cey> g = bys.g;

   public bxn(ceg.c var1) {
      super(_snowman);
      this.j(
         this.n
            .b()
            .a(a, Boolean.valueOf(true))
            .a(b, Boolean.valueOf(true))
            .a(c, Boolean.valueOf(true))
            .a(d, Boolean.valueOf(true))
            .a(e, Boolean.valueOf(true))
            .a(f, Boolean.valueOf(true))
      );
   }

   @Override
   public ceh a(bny var1) {
      brc _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      return this.n()
         .a(f, Boolean.valueOf(this != _snowman.d_(_snowmanx.c()).b()))
         .a(e, Boolean.valueOf(this != _snowman.d_(_snowmanx.b()).b()))
         .a(a, Boolean.valueOf(this != _snowman.d_(_snowmanx.d()).b()))
         .a(b, Boolean.valueOf(this != _snowman.d_(_snowmanx.g()).b()))
         .a(c, Boolean.valueOf(this != _snowman.d_(_snowmanx.e()).b()))
         .a(d, Boolean.valueOf(this != _snowman.d_(_snowmanx.f()).b()));
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return _snowman.a(this) ? _snowman.a(g.get(_snowman), Boolean.valueOf(false)) : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(g.get(_snowman.a(gc.c)), _snowman.c(a))
         .a(g.get(_snowman.a(gc.d)), _snowman.c(c))
         .a(g.get(_snowman.a(gc.f)), _snowman.c(b))
         .a(g.get(_snowman.a(gc.e)), _snowman.c(d))
         .a(g.get(_snowman.a(gc.b)), _snowman.c(e))
         .a(g.get(_snowman.a(gc.a)), _snowman.c(f));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman.a(g.get(_snowman.b(gc.c)), _snowman.c(a))
         .a(g.get(_snowman.b(gc.d)), _snowman.c(c))
         .a(g.get(_snowman.b(gc.f)), _snowman.c(b))
         .a(g.get(_snowman.b(gc.e)), _snowman.c(d))
         .a(g.get(_snowman.b(gc.b)), _snowman.c(e))
         .a(g.get(_snowman.b(gc.a)), _snowman.c(f));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(e, f, a, b, c, d);
   }
}
