import java.util.Arrays;

public class dfv {
   private final char[] a;
   private int b;
   private final Runnable c;

   public dfv(char[] var1, Runnable var2) {
      this.c = _snowman;
      if (_snowman.length < 1) {
         throw new IllegalArgumentException("Must have at least one char");
      } else {
         this.a = _snowman;
      }
   }

   public boolean a(char var1) {
      if (_snowman == this.a[this.b++]) {
         if (this.b == this.a.length) {
            this.a();
            this.c.run();
            return true;
         }
      } else {
         this.a();
      }

      return false;
   }

   public void a() {
      this.b = 0;
   }

   @Override
   public String toString() {
      return "KeyCombo{chars=" + Arrays.toString(this.a) + ", matchIndex=" + this.b + '}';
   }
}
