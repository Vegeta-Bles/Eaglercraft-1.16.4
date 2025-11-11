import java.util.BitSet;

public class cgx {
   private final BitSet a = new BitSet();

   public cgx() {
   }

   public void a(int var1, int var2) {
      this.a.set(_snowman, _snowman + _snowman);
   }

   public void b(int var1, int var2) {
      this.a.clear(_snowman, _snowman + _snowman);
   }

   public int a(int var1) {
      int _snowman = 0;

      while (true) {
         int _snowmanx = this.a.nextClearBit(_snowman);
         int _snowmanxx = this.a.nextSetBit(_snowmanx);
         if (_snowmanxx == -1 || _snowmanxx - _snowmanx >= _snowman) {
            this.a(_snowmanx, _snowman);
            return _snowmanx;
         }

         _snowman = _snowmanxx;
      }
   }
}
