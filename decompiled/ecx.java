import java.util.BitSet;
import java.util.Set;

public class ecx {
   private static final int a = gc.values().length;
   private final BitSet b = new BitSet(a * a);

   public ecx() {
   }

   public void a(Set<gc> var1) {
      for (gc _snowman : _snowman) {
         for (gc _snowmanx : _snowman) {
            this.a(_snowman, _snowmanx, true);
         }
      }
   }

   public void a(gc var1, gc var2, boolean var3) {
      this.b.set(_snowman.ordinal() + _snowman.ordinal() * a, _snowman);
      this.b.set(_snowman.ordinal() + _snowman.ordinal() * a, _snowman);
   }

   public void a(boolean var1) {
      this.b.set(0, this.b.size(), _snowman);
   }

   public boolean a(gc var1, gc var2) {
      return this.b.get(_snowman.ordinal() + _snowman.ordinal() * a);
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder();
      _snowman.append(' ');

      for (gc _snowmanx : gc.values()) {
         _snowman.append(' ').append(_snowmanx.toString().toUpperCase().charAt(0));
      }

      _snowman.append('\n');

      for (gc _snowmanx : gc.values()) {
         _snowman.append(_snowmanx.toString().toUpperCase().charAt(0));

         for (gc _snowmanxx : gc.values()) {
            if (_snowmanx == _snowmanxx) {
               _snowman.append("  ");
            } else {
               boolean _snowmanxxx = this.a(_snowmanx, _snowmanxx);
               _snowman.append(' ').append((char)(_snowmanxxx ? 'Y' : 'n'));
            }
         }

         _snowman.append('\n');
      }

      return _snowman.toString();
   }
}
