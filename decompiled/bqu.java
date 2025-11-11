import java.util.OptionalInt;
import javax.annotation.Nullable;

public interface bqu {
   void f(@Nullable bfw var1);

   @Nullable
   bfw eM();

   bqw eO();

   void a(@Nullable bqw var1);

   void a(bqv var1);

   void k(bmb var1);

   brx eV();

   int eL();

   void t(int var1);

   boolean eP();

   adp eQ();

   default boolean fa() {
      return false;
   }

   default void a(bfw var1, nr var2, int var3) {
      OptionalInt _snowman = _snowman.a(new apb((var1x, var2x, var3x) -> new bjg(var1x, var2x, this), _snowman));
      if (_snowman.isPresent()) {
         bqw _snowmanx = this.eO();
         if (!_snowmanx.isEmpty()) {
            _snowman.a(_snowman.getAsInt(), _snowmanx, _snowman, this.eL(), this.eP(), this.fa());
         }
      }
   }
}
