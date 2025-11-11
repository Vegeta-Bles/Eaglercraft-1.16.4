import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Iterator;

public class vb<C extends aon> extends va<C> {
   private boolean e;

   public vb(bjj<C> var1) {
      super(_snowman);
   }

   @Override
   protected void a(boq<C> var1, boolean var2) {
      this.e = this.d.a(_snowman);
      int _snowman = this.b.b(_snowman, null);
      if (this.e) {
         bmb _snowmanx = this.d.a(0).e();
         if (_snowmanx.a() || _snowman <= _snowmanx.E()) {
            return;
         }
      }

      int _snowmanx = this.a(_snowman, _snowman, this.e);
      IntList _snowmanxx = new IntArrayList();
      if (this.b.a(_snowman, _snowmanxx, _snowmanx)) {
         if (!this.e) {
            this.a(this.d.f());
            this.a(0);
         }

         this.a(_snowmanx, _snowmanxx);
      }
   }

   @Override
   protected void a() {
      this.a(this.d.f());
      super.a();
   }

   protected void a(int var1, IntList var2) {
      Iterator<Integer> _snowman = _snowman.iterator();
      bjr _snowmanx = this.d.a(0);
      bmb _snowmanxx = bfy.a(_snowman.next());
      if (!_snowmanxx.a()) {
         int _snowmanxxx = Math.min(_snowmanxx.c(), _snowman);
         if (this.e) {
            _snowmanxxx -= _snowmanx.e().E();
         }

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
            this.a(_snowmanx, _snowmanxx);
         }
      }
   }
}
