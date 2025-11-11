import com.google.common.collect.ImmutableMap;

public class asa<E extends aqn & bdd, T extends aqm> extends arv<E> {
   private int b;
   private asa.a c = asa.a.a;

   public asa() {
      super(ImmutableMap.of(ayd.n, aye.c, ayd.o, aye.a), 1200);
   }

   protected boolean a(aag var1, E var2) {
      aqm _snowman = a(_snowman);
      return _snowman.a(bmd.qQ) && arw.c(_snowman, _snowman) && arw.a(_snowman, _snowman, 0);
   }

   protected boolean a(aag var1, E var2, long var3) {
      return _snowman.cJ().a(ayd.o) && this.a(_snowman, _snowman);
   }

   protected void b(aag var1, E var2, long var3) {
      aqm _snowman = a(_snowman);
      this.b(_snowman, _snowman);
      this.a(_snowman, _snowman);
   }

   protected void c(aag var1, E var2, long var3) {
      if (_snowman.dW()) {
         _snowman.ec();
      }

      if (_snowman.a(bmd.qQ)) {
         _snowman.b(false);
         bkt.a(_snowman.dY(), false);
      }
   }

   private void a(E var1, aqm var2) {
      if (this.c == asa.a.a) {
         _snowman.c(bgn.a(_snowman, bmd.qQ));
         this.c = asa.a.b;
         _snowman.b(true);
      } else if (this.c == asa.a.b) {
         if (!_snowman.dW()) {
            this.c = asa.a.a;
         }

         int _snowman = _snowman.ea();
         bmb _snowmanx = _snowman.dY();
         if (_snowman >= bkt.g(_snowmanx)) {
            _snowman.eb();
            this.c = asa.a.c;
            this.b = 20 + _snowman.cY().nextInt(20);
            _snowman.b(false);
         }
      } else if (this.c == asa.a.c) {
         this.b--;
         if (this.b == 0) {
            this.c = asa.a.d;
         }
      } else if (this.c == asa.a.d) {
         _snowman.a(_snowman, 1.0F);
         bmb _snowman = _snowman.b(bgn.a(_snowman, bmd.qQ));
         bkt.a(_snowman, false);
         this.c = asa.a.a;
      }
   }

   private void b(aqn var1, aqm var2) {
      _snowman.cJ().a(ayd.n, new asd(_snowman, true));
   }

   private static aqm a(aqm var0) {
      return _snowman.cJ().c(ayd.o).get();
   }

   static enum a {
      a,
      b,
      c,
      d;

      private a() {
      }
   }
}
