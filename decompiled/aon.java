import java.util.Set;

public interface aon extends aol {
   int Z_();

   boolean c();

   bmb a(int var1);

   bmb a(int var1, int var2);

   bmb b(int var1);

   void a(int var1, bmb var2);

   default int V_() {
      return 64;
   }

   void X_();

   boolean a(bfw var1);

   default void c_(bfw var1) {
   }

   default void b_(bfw var1) {
   }

   default boolean b(int var1, bmb var2) {
      return true;
   }

   default int a(blx var1) {
      int _snowman = 0;

      for (int _snowmanx = 0; _snowmanx < this.Z_(); _snowmanx++) {
         bmb _snowmanxx = this.a(_snowmanx);
         if (_snowmanxx.b().equals(_snowman)) {
            _snowman += _snowmanxx.E();
         }
      }

      return _snowman;
   }

   default boolean a(Set<blx> var1) {
      for (int _snowman = 0; _snowman < this.Z_(); _snowman++) {
         bmb _snowmanx = this.a(_snowman);
         if (_snowman.contains(_snowmanx.b()) && _snowmanx.E() > 0) {
            return true;
         }
      }

      return false;
   }
}
