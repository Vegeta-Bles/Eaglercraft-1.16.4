import com.google.common.base.Predicates;

public class bwj extends buo {
   public static final cfb a = bxm.aq;
   public static final cey b = cex.h;
   protected static final ddh c = buo.a(0.0, 0.0, 0.0, 16.0, 13.0, 16.0);
   protected static final ddh d = buo.a(4.0, 13.0, 4.0, 12.0, 16.0, 12.0);
   protected static final ddh e = dde.a(c, d);
   private static cem f;

   public bwj(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.c).a(b, Boolean.valueOf(false)));
   }

   @Override
   public boolean c_(ceh var1) {
      return true;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return _snowman.c(b) ? e : c;
   }

   @Override
   public ceh a(bny var1) {
      return this.n().a(a, _snowman.f().f()).a(b, Boolean.valueOf(false));
   }

   @Override
   public boolean a(ceh var1) {
      return true;
   }

   @Override
   public int a(ceh var1, brx var2, fx var3) {
      return _snowman.c(b) ? 15 : 0;
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(a, _snowman.a(_snowman.c(a)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman.a(_snowman.a(_snowman.c(a)));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b);
   }

   public static cem c() {
      if (f == null) {
         f = cen.a()
            .a("?vvv?", ">???<", ">???<", ">???<", "?^^^?")
            .a('?', cel.a(cer.a))
            .a('^', cel.a(cer.a(bup.ed).a(b, Predicates.equalTo(true)).a(a, Predicates.equalTo(gc.d))))
            .a('>', cel.a(cer.a(bup.ed).a(b, Predicates.equalTo(true)).a(a, Predicates.equalTo(gc.e))))
            .a('v', cel.a(cer.a(bup.ed).a(b, Predicates.equalTo(true)).a(a, Predicates.equalTo(gc.c))))
            .a('<', cel.a(cer.a(bup.ed).a(b, Predicates.equalTo(true)).a(a, Predicates.equalTo(gc.f))))
            .b();
      }

      return f;
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
