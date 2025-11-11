import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.util.function.Function;
import java.util.function.Predicate;

public class cgq<T> implements bso<T> {
   protected final Predicate<T> a;
   private final brd b;
   private final ShortList[] c = new ShortList[16];

   public cgq(Predicate<T> var1, brd var2) {
      this(_snowman, _snowman, new mj());
   }

   public cgq(Predicate<T> var1, brd var2, mj var3) {
      this.a = _snowman;
      this.b = _snowman;

      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         mj _snowmanx = _snowman.b(_snowman);

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
            cfw.a(this.c, _snowman).add(_snowmanx.d(_snowmanxx));
         }
      }
   }

   public mj b() {
      return cgt.a(this.c);
   }

   public void a(bso<T> var1, Function<fx, T> var2) {
      for (int _snowman = 0; _snowman < this.c.length; _snowman++) {
         if (this.c[_snowman] != null) {
            ShortListIterator var4 = this.c[_snowman].iterator();

            while (var4.hasNext()) {
               Short _snowmanx = (Short)var4.next();
               fx _snowmanxx = cgp.a(_snowmanx, _snowman, this.b);
               _snowman.a(_snowmanxx, _snowman.apply(_snowmanxx), 0);
            }

            this.c[_snowman].clear();
         }
      }
   }

   @Override
   public boolean a(fx var1, T var2) {
      return false;
   }

   @Override
   public void a(fx var1, T var2, int var3, bsq var4) {
      cfw.a(this.c, _snowman.v() >> 4).add(cgp.l(_snowman));
   }

   @Override
   public boolean b(fx var1, T var2) {
      return false;
   }
}
