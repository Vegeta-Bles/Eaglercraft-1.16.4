import javax.annotation.Nullable;

public class bts extends bwo {
   public static final cfb a = bxm.aq;
   private static final ddh b = buo.a(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
   private static final ddh c = buo.a(3.0, 4.0, 4.0, 13.0, 5.0, 12.0);
   private static final ddh d = buo.a(4.0, 5.0, 6.0, 12.0, 10.0, 10.0);
   private static final ddh e = buo.a(0.0, 10.0, 3.0, 16.0, 16.0, 13.0);
   private static final ddh f = buo.a(4.0, 4.0, 3.0, 12.0, 5.0, 13.0);
   private static final ddh g = buo.a(6.0, 5.0, 4.0, 10.0, 10.0, 12.0);
   private static final ddh h = buo.a(3.0, 10.0, 0.0, 13.0, 16.0, 16.0);
   private static final ddh i = dde.a(b, c, d, e);
   private static final ddh j = dde.a(b, f, g, h);
   private static final nr k = new of("container.repair");

   public bts(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.c));
   }

   @Override
   public ceh a(bny var1) {
      return this.n().a(a, _snowman.f().g());
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (_snowman.v) {
         return aou.a;
      } else {
         _snowman.a(_snowman.b(_snowman, _snowman));
         _snowman.a(aea.aB);
         return aou.b;
      }
   }

   @Nullable
   @Override
   public aox b(ceh var1, brx var2, fx var3) {
      return new apb((var2x, var3x, var4) -> new bie(var2x, var3x, bim.a(_snowman, _snowman)), k);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      gc _snowman = _snowman.c(a);
      return _snowman.n() == gc.a.a ? i : j;
   }

   @Override
   protected void a(bcu var1) {
      _snowman.a(true);
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, ceh var4, bcu var5) {
      if (!_snowman.aA()) {
         _snowman.c(1031, _snowman, 0);
      }
   }

   @Override
   public void a(brx var1, fx var2, bcu var3) {
      if (!_snowman.aA()) {
         _snowman.c(1029, _snowman, 0);
      }
   }

   @Nullable
   public static ceh c(ceh var0) {
      if (_snowman.a(bup.fo)) {
         return bup.fp.n().a(a, _snowman.c(a));
      } else {
         return _snowman.a(bup.fp) ? bup.fq.n().a(a, _snowman.c(a)) : null;
      }
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(a, _snowman.a(_snowman.c(a)));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }

   @Override
   public int c(ceh var1, brc var2, fx var3) {
      return _snowman.d(_snowman, _snowman).ai;
   }
}
