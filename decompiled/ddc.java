import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public class ddc extends AbstractDoubleList implements dcz {
   private final DoubleList a;
   private final DoubleList b;
   private final boolean c;

   public ddc(DoubleList var1, DoubleList var2, boolean var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public int size() {
      return this.a.size() + this.b.size();
   }

   @Override
   public boolean a(dcz.a var1) {
      return this.c ? this.b((var1x, var2, var3) -> _snowman.merge(var2, var1x, var3)) : this.b(_snowman);
   }

   private boolean b(dcz.a var1) {
      int _snowman = this.a.size() - 1;

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         if (!_snowman.merge(_snowmanx, -1, _snowmanx)) {
            return false;
         }
      }

      if (!_snowman.merge(_snowman, -1, _snowman)) {
         return false;
      } else {
         for (int _snowmanxx = 0; _snowmanxx < this.b.size(); _snowmanxx++) {
            if (!_snowman.merge(_snowman, _snowmanxx, _snowman + 1 + _snowmanxx)) {
               return false;
            }
         }

         return true;
      }
   }

   public double getDouble(int var1) {
      return _snowman < this.a.size() ? this.a.getDouble(_snowman) : this.b.getDouble(_snowman - this.a.size());
   }

   @Override
   public DoubleList a() {
      return this;
   }
}
