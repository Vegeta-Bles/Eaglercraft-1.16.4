import com.google.common.collect.ImmutableMap;
import java.util.function.Function;

public class atp<T> extends arv<aqu> {
   private final ayd<T> b;
   private final float c;
   private final int d;
   private final Function<T, dcn> e;

   public atp(ayd<T> var1, float var2, int var3, boolean var4, Function<T, dcn> var5) {
      super(ImmutableMap.of(ayd.m, _snowman ? aye.c : aye.b, _snowman, aye.a));
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   public static atp<fx> a(ayd<fx> var0, float var1, int var2, boolean var3) {
      return new atp<>(_snowman, _snowman, _snowman, _snowman, dcn::c);
   }

   public static atp<? extends aqa> b(ayd<? extends aqa> var0, float var1, int var2, boolean var3) {
      return new atp<>(_snowman, _snowman, _snowman, _snowman, aqa::cA);
   }

   protected boolean a(aag var1, aqu var2) {
      return this.b(_snowman) ? false : _snowman.cA().a(this.a(_snowman), (double)this.d);
   }

   private dcn a(aqu var1) {
      return this.e.apply(_snowman.cJ().c(this.b).get());
   }

   private boolean b(aqu var1) {
      if (!_snowman.cJ().a(ayd.m)) {
         return false;
      } else {
         ayf _snowman = _snowman.cJ().c(ayd.m).get();
         if (_snowman.b() != this.c) {
            return false;
         } else {
            dcn _snowmanx = _snowman.a().a().d(_snowman.cA());
            dcn _snowmanxx = this.a(_snowman).d(_snowman.cA());
            return _snowmanx.b(_snowmanxx) < 0.0;
         }
      }
   }

   protected void a(aag var1, aqu var2, long var3) {
      a(_snowman, this.a(_snowman), this.c);
   }

   private static void a(aqu var0, dcn var1, float var2) {
      for (int _snowman = 0; _snowman < 10; _snowman++) {
         dcn _snowmanx = azj.d(_snowman, 16, 7, _snowman);
         if (_snowmanx != null) {
            _snowman.cJ().a(ayd.m, new ayf(_snowmanx, _snowman, 0));
            return;
         }
      }
   }
}
