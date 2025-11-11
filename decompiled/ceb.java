import java.util.Arrays;

public class ceb extends bvz {
   public static final cfe<cfi> b = cex.aJ;
   public static final cey c = cex.x;
   protected static final ddh d = buo.a(12.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final ddh e = buo.a(0.0, 0.0, 0.0, 4.0, 16.0, 16.0);
   protected static final ddh f = buo.a(0.0, 0.0, 12.0, 16.0, 16.0, 16.0);
   protected static final ddh g = buo.a(0.0, 0.0, 0.0, 16.0, 16.0, 4.0);
   protected static final ddh h = buo.a(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);
   protected static final ddh i = buo.a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
   protected static final ddh j = buo.a(6.0, -4.0, 6.0, 10.0, 12.0, 10.0);
   protected static final ddh k = buo.a(6.0, 4.0, 6.0, 10.0, 20.0, 10.0);
   protected static final ddh o = buo.a(6.0, 6.0, -4.0, 10.0, 10.0, 12.0);
   protected static final ddh p = buo.a(6.0, 6.0, 4.0, 10.0, 10.0, 20.0);
   protected static final ddh q = buo.a(-4.0, 6.0, 6.0, 12.0, 10.0, 10.0);
   protected static final ddh r = buo.a(4.0, 6.0, 6.0, 20.0, 10.0, 10.0);
   protected static final ddh s = buo.a(6.0, 0.0, 6.0, 10.0, 12.0, 10.0);
   protected static final ddh t = buo.a(6.0, 4.0, 6.0, 10.0, 16.0, 10.0);
   protected static final ddh u = buo.a(6.0, 6.0, 0.0, 10.0, 10.0, 12.0);
   protected static final ddh v = buo.a(6.0, 6.0, 4.0, 10.0, 10.0, 16.0);
   protected static final ddh w = buo.a(0.0, 6.0, 6.0, 12.0, 10.0, 10.0);
   protected static final ddh x = buo.a(4.0, 6.0, 6.0, 16.0, 10.0, 10.0);
   private static final ddh[] y = a(true);
   private static final ddh[] z = a(false);

   private static ddh[] a(boolean var0) {
      return Arrays.stream(gc.values()).map(var1 -> a(var1, _snowman)).toArray(ddh[]::new);
   }

   private static ddh a(gc var0, boolean var1) {
      switch (_snowman) {
         case a:
         default:
            return dde.a(i, _snowman ? t : k);
         case b:
            return dde.a(h, _snowman ? s : j);
         case c:
            return dde.a(g, _snowman ? v : p);
         case d:
            return dde.a(f, _snowman ? u : o);
         case e:
            return dde.a(e, _snowman ? x : r);
         case f:
            return dde.a(d, _snowman ? w : q);
      }
   }

   public ceb(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, gc.c).a(b, cfi.a).a(c, Boolean.valueOf(false)));
   }

   @Override
   public boolean c_(ceh var1) {
      return true;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return (_snowman.c(c) ? y : z)[_snowman.c(a).ordinal()];
   }

   private boolean a(ceh var1, ceh var2) {
      buo _snowman = _snowman.c(b) == cfi.a ? bup.aW : bup.aP;
      return _snowman.a(_snowman) && _snowman.c(cea.b) && _snowman.c(a) == _snowman.c(a);
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, bfw var4) {
      if (!_snowman.v && _snowman.bC.d) {
         fx _snowman = _snowman.a(_snowman.c(a).f());
         if (this.a(_snowman, _snowman.d_(_snowman))) {
            _snowman.b(_snowman, false);
         }
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b())) {
         super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
         fx _snowman = _snowman.a(_snowman.c(a).f());
         if (this.a(_snowman, _snowman.d_(_snowman))) {
            _snowman.b(_snowman, true);
         }
      }
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return _snowman.f() == _snowman.c(a) && !_snowman.a(_snowman, _snowman) ? bup.a.n() : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      ceh _snowman = _snowman.d_(_snowman.a(_snowman.c(a).f()));
      return this.a(_snowman, _snowman) || _snowman.a(bup.bo) && _snowman.c(a) == _snowman.c(a);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      if (_snowman.a((brz)_snowman, _snowman)) {
         fx _snowman = _snowman.a(_snowman.c(a).f());
         _snowman.d_(_snowman).a(_snowman, _snowman, _snowman, _snowman, false);
      }
   }

   @Override
   public bmb a(brc var1, fx var2, ceh var3) {
      return new bmb(_snowman.c(b) == cfi.b ? bup.aP : bup.aW);
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
      _snowman.a(a, b, c);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
