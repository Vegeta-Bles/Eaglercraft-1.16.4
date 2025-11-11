import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;

public class asq extends arv<aqn> {
   private final float b;
   @Nullable
   private fx c;
   private int d;
   private int e;
   private int f;

   public asq(float var1) {
      super(ImmutableMap.of(ayd.w, aye.a, ayd.m, aye.b));
      this.b = _snowman;
   }

   protected boolean a(aag var1, aqn var2) {
      return _snowman.w_() && this.b(_snowman, _snowman);
   }

   protected void a(aag var1, aqn var2, long var3) {
      super.a(_snowman, _snowman, _snowman);
      this.a(_snowman).ifPresent(var3x -> {
         this.c = var3x;
         this.d = 100;
         this.e = 3 + _snowman.t.nextInt(4);
         this.f = 0;
         this.a(_snowman, var3x);
      });
   }

   protected void b(aag var1, aqn var2, long var3) {
      super.c(_snowman, _snowman, _snowman);
      this.c = null;
      this.d = 0;
      this.e = 0;
      this.f = 0;
   }

   protected boolean c(aag var1, aqn var2, long var3) {
      return _snowman.w_() && this.c != null && this.a(_snowman, this.c) && !this.e(_snowman, _snowman) && !this.f(_snowman, _snowman);
   }

   @Override
   protected boolean a(long var1) {
      return false;
   }

   protected void d(aag var1, aqn var2, long var3) {
      if (!this.c(_snowman, _snowman)) {
         this.d--;
      } else if (this.f > 0) {
         this.f--;
      } else {
         if (this.d(_snowman, _snowman)) {
            _snowman.v().a();
            this.e--;
            this.f = 5;
         }
      }
   }

   private void a(aqn var1, fx var2) {
      _snowman.cJ().a(ayd.m, new ayf(_snowman, this.b, 0));
   }

   private boolean b(aag var1, aqn var2) {
      return this.c(_snowman, _snowman) || this.a(_snowman).isPresent();
   }

   private boolean c(aag var1, aqn var2) {
      fx _snowman = _snowman.cB();
      fx _snowmanx = _snowman.c();
      return this.a(_snowman, _snowman) || this.a(_snowman, _snowmanx);
   }

   private boolean d(aag var1, aqn var2) {
      return this.a(_snowman, _snowman.cB());
   }

   private boolean a(aag var1, fx var2) {
      return _snowman.d_(_snowman).a(aed.L);
   }

   private Optional<fx> a(aqn var1) {
      return _snowman.cJ().c(ayd.w);
   }

   private boolean e(aag var1, aqn var2) {
      return !this.c(_snowman, _snowman) && this.d <= 0;
   }

   private boolean f(aag var1, aqn var2) {
      return this.c(_snowman, _snowman) && this.e <= 0;
   }
}
