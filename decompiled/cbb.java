import javax.annotation.Nullable;

public class cbb extends bxm implements bzu {
   public static final cey a = cex.u;
   public static final cfe<cff> b = cex.ab;
   public static final cey c = cex.w;
   public static final cey d = cex.C;
   protected static final ddh e = buo.a(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
   protected static final ddh f = buo.a(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final ddh g = buo.a(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   protected static final ddh h = buo.a(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
   protected static final ddh i = buo.a(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
   protected static final ddh j = buo.a(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);

   protected cbb(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(aq, gc.c).a(a, Boolean.valueOf(false)).a(b, cff.b).a(c, Boolean.valueOf(false)).a(d, Boolean.valueOf(false)));
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      if (!_snowman.c(a)) {
         return _snowman.c(b) == cff.a ? j : i;
      } else {
         switch ((gc)_snowman.c(aq)) {
            case c:
            default:
               return h;
            case d:
               return g;
            case e:
               return f;
            case f:
               return e;
         }
      }
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      switch (_snowman) {
         case a:
            return _snowman.c(a);
         case b:
            return _snowman.c(d);
         case c:
            return _snowman.c(a);
         default:
            return false;
      }
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      if (this.as == cva.J) {
         return aou.c;
      } else {
         _snowman = _snowman.a(a);
         _snowman.a(_snowman, _snowman, 2);
         if (_snowman.c(d)) {
            _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
         }

         this.a(_snowman, _snowman, _snowman, _snowman.c(a));
         return aou.a(_snowman.v);
      }
   }

   protected void a(@Nullable bfw var1, brx var2, fx var3, boolean var4) {
      if (_snowman) {
         int _snowman = this.as == cva.J ? 1037 : 1007;
         _snowman.a(_snowman, _snowman, _snowman, 0);
      } else {
         int _snowman = this.as == cva.J ? 1036 : 1013;
         _snowman.a(_snowman, _snowman, _snowman, 0);
      }
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      if (!_snowman.v) {
         boolean _snowman = _snowman.r(_snowman);
         if (_snowman != _snowman.c(c)) {
            if (_snowman.c(a) != _snowman) {
               _snowman = _snowman.a(a, Boolean.valueOf(_snowman));
               this.a(null, _snowman, _snowman, _snowman);
            }

            _snowman.a(_snowman, _snowman.a(c, Boolean.valueOf(_snowman)), 2);
            if (_snowman.c(d)) {
               _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
            }
         }
      }
   }

   @Override
   public ceh a(bny var1) {
      ceh _snowman = this.n();
      cux _snowmanx = _snowman.p().b(_snowman.a());
      gc _snowmanxx = _snowman.j();
      if (!_snowman.c() && _snowmanxx.n().d()) {
         _snowman = _snowman.a(aq, _snowmanxx).a(b, _snowman.k().c - (double)_snowman.a().v() > 0.5 ? cff.a : cff.b);
      } else {
         _snowman = _snowman.a(aq, _snowman.f().f()).a(b, _snowmanxx == gc.b ? cff.b : cff.a);
      }

      if (_snowman.p().r(_snowman.a())) {
         _snowman = _snowman.a(a, Boolean.valueOf(true)).a(c, Boolean.valueOf(true));
      }

      return _snowman.a(d, Boolean.valueOf(_snowmanx.a() == cuy.c));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(aq, a, b, c, d);
   }

   @Override
   public cux d(ceh var1) {
      return _snowman.c(d) ? cuy.c.a(false) : super.d(_snowman);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.c(d)) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
