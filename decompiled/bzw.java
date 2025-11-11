import javax.annotation.Nullable;

public class bzw extends buo implements bzu {
   public static final cfe<cfm> a = cex.aK;
   public static final cey b = cex.C;
   protected static final ddh c = buo.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   protected static final ddh d = buo.a(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);

   public bzw(ceg.c var1) {
      super(_snowman);
      this.j(this.n().a(a, cfm.b).a(b, Boolean.valueOf(false)));
   }

   @Override
   public boolean c_(ceh var1) {
      return _snowman.c(a) != cfm.c;
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      cfm _snowman = _snowman.c(a);
      switch (_snowman) {
         case c:
            return dde.b();
         case a:
            return d;
         default:
            return c;
      }
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      fx _snowman = _snowman.a();
      ceh _snowmanx = _snowman.p().d_(_snowman);
      if (_snowmanx.a(this)) {
         return _snowmanx.a(a, cfm.c).a(b, Boolean.valueOf(false));
      } else {
         cux _snowmanxx = _snowman.p().b(_snowman);
         ceh _snowmanxxx = this.n().a(a, cfm.b).a(b, Boolean.valueOf(_snowmanxx.a() == cuy.c));
         gc _snowmanxxxx = _snowman.j();
         return _snowmanxxxx != gc.a && (_snowmanxxxx == gc.b || !(_snowman.k().c - (double)_snowman.v() > 0.5)) ? _snowmanxxx : _snowmanxxx.a(a, cfm.a);
      }
   }

   @Override
   public boolean a(ceh var1, bny var2) {
      bmb _snowman = _snowman.m();
      cfm _snowmanx = _snowman.c(a);
      if (_snowmanx == cfm.c || _snowman.b() != this.h()) {
         return false;
      } else if (_snowman.c()) {
         boolean _snowmanxx = _snowman.k().c - (double)_snowman.a().v() > 0.5;
         gc _snowmanxxx = _snowman.j();
         return _snowmanx == cfm.b ? _snowmanxxx == gc.b || _snowmanxx && _snowmanxxx.n().d() : _snowmanxxx == gc.a || !_snowmanxx && _snowmanxxx.n().d();
      } else {
         return true;
      }
   }

   @Override
   public cux d(ceh var1) {
      return _snowman.c(b) ? cuy.c.a(false) : super.d(_snowman);
   }

   @Override
   public boolean a(bry var1, fx var2, ceh var3, cux var4) {
      return _snowman.c(a) != cfm.c ? bzu.super.a(_snowman, _snowman, _snowman, _snowman) : false;
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, cuw var4) {
      return _snowman.c(a) != cfm.c ? bzu.super.a(_snowman, _snowman, _snowman, _snowman) : false;
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.c(b)) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      switch (_snowman) {
         case a:
            return false;
         case b:
            return _snowman.b(_snowman).a(aef.b);
         case c:
            return false;
         default:
            return false;
      }
   }
}
