import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntArrayList;

public final class dda implements dcz {
   private final DoubleArrayList a;
   private final IntArrayList b;
   private final IntArrayList c;

   protected dda(DoubleList var1, DoubleList var2, boolean var3, boolean var4) {
      int _snowman = 0;
      int _snowmanx = 0;
      double _snowmanxx = Double.NaN;
      int _snowmanxxx = _snowman.size();
      int _snowmanxxxx = _snowman.size();
      int _snowmanxxxxx = _snowmanxxx + _snowmanxxxx;
      this.a = new DoubleArrayList(_snowmanxxxxx);
      this.b = new IntArrayList(_snowmanxxxxx);
      this.c = new IntArrayList(_snowmanxxxxx);

      while (true) {
         boolean _snowmanxxxxxx = _snowman < _snowmanxxx;
         boolean _snowmanxxxxxxx = _snowmanx < _snowmanxxxx;
         if (!_snowmanxxxxxx && !_snowmanxxxxxxx) {
            if (this.a.isEmpty()) {
               this.a.add(Math.min(_snowman.getDouble(_snowmanxxx - 1), _snowman.getDouble(_snowmanxxxx - 1)));
            }

            return;
         }

         boolean _snowmanxxxxxxxx = _snowmanxxxxxx && (!_snowmanxxxxxxx || _snowman.getDouble(_snowman) < _snowman.getDouble(_snowmanx) + 1.0E-7);
         double _snowmanxxxxxxxxx = _snowmanxxxxxxxx ? _snowman.getDouble(_snowman++) : _snowman.getDouble(_snowmanx++);
         if ((_snowman != 0 && _snowmanxxxxxx || _snowmanxxxxxxxx || _snowman) && (_snowmanx != 0 && _snowmanxxxxxxx || !_snowmanxxxxxxxx || _snowman)) {
            if (!(_snowmanxx >= _snowmanxxxxxxxxx - 1.0E-7)) {
               this.b.add(_snowman - 1);
               this.c.add(_snowmanx - 1);
               this.a.add(_snowmanxxxxxxxxx);
               _snowmanxx = _snowmanxxxxxxxxx;
            } else if (!this.a.isEmpty()) {
               this.b.set(this.b.size() - 1, _snowman - 1);
               this.c.set(this.c.size() - 1, _snowmanx - 1);
            }
         }
      }
   }

   @Override
   public boolean a(dcz.a var1) {
      for (int _snowman = 0; _snowman < this.a.size() - 1; _snowman++) {
         if (!_snowman.merge(this.b.getInt(_snowman), this.c.getInt(_snowman), _snowman)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public DoubleList a() {
      return this.a;
   }
}
