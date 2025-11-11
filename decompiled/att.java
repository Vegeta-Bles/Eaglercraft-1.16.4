import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class att extends arv<bfj> {
   @Nullable
   private bmb b;
   private final List<bmb> c = Lists.newArrayList();
   private int d;
   private int e;
   private int f;

   public att(int var1, int var2) {
      super(ImmutableMap.of(ayd.q, aye.a), _snowman, _snowman);
   }

   public boolean a(aag var1, bfj var2) {
      arf<?> _snowman = _snowman.cJ();
      if (!_snowman.c(ayd.q).isPresent()) {
         return false;
      } else {
         aqm _snowmanx = _snowman.c(ayd.q).get();
         return _snowmanx.X() == aqe.bc && _snowman.aX() && _snowmanx.aX() && !_snowman.w_() && _snowman.h((aqa)_snowmanx) <= 17.0;
      }
   }

   public boolean a(aag var1, bfj var2, long var3) {
      return this.a(_snowman, _snowman) && this.f > 0 && _snowman.cJ().c(ayd.q).isPresent();
   }

   public void b(aag var1, bfj var2, long var3) {
      super.a(_snowman, _snowman, _snowman);
      this.c(_snowman);
      this.d = 0;
      this.e = 0;
      this.f = 40;
   }

   public void c(aag var1, bfj var2, long var3) {
      aqm _snowman = this.c(_snowman);
      this.a(_snowman, _snowman);
      if (!this.c.isEmpty()) {
         this.d(_snowman);
      } else {
         _snowman.a(aqf.a, bmb.b);
         this.f = Math.min(this.f, 40);
      }

      this.f--;
   }

   public void d(aag var1, bfj var2, long var3) {
      super.c(_snowman, _snowman, _snowman);
      _snowman.cJ().b(ayd.q);
      _snowman.a(aqf.a, bmb.b);
      this.b = null;
   }

   private void a(aqm var1, bfj var2) {
      boolean _snowman = false;
      bmb _snowmanx = _snowman.dD();
      if (this.b == null || !bmb.c(this.b, _snowmanx)) {
         this.b = _snowmanx;
         _snowman = true;
         this.c.clear();
      }

      if (_snowman && !this.b.a()) {
         this.b(_snowman);
         if (!this.c.isEmpty()) {
            this.f = 900;
            this.a(_snowman);
         }
      }
   }

   private void a(bfj var1) {
      _snowman.a(aqf.a, this.c.get(0));
   }

   private void b(bfj var1) {
      for (bqv _snowman : _snowman.eO()) {
         if (!_snowman.p() && this.a(_snowman)) {
            this.c.add(_snowman.d());
         }
      }
   }

   private boolean a(bqv var1) {
      return bmb.c(this.b, _snowman.b()) || bmb.c(this.b, _snowman.c());
   }

   private aqm c(bfj var1) {
      arf<?> _snowman = _snowman.cJ();
      aqm _snowmanx = _snowman.c(ayd.q).get();
      _snowman.a(ayd.n, new asd(_snowmanx, true));
      return _snowmanx;
   }

   private void d(bfj var1) {
      if (this.c.size() >= 2 && ++this.d >= 40) {
         this.e++;
         this.d = 0;
         if (this.e > this.c.size() - 1) {
            this.e = 0;
         }

         _snowman.a(aqf.a, this.c.get(this.e));
      }
   }
}
