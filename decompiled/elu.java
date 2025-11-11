import java.util.Locale;

public class elu extends vk {
   private final String d;

   protected elu(String[] var1) {
      super(_snowman);
      this.d = _snowman[2].toLowerCase(Locale.ROOT);
   }

   public elu(String var1) {
      this(c(_snowman));
   }

   public elu(vk var1, String var2) {
      this(_snowman.toString(), _snowman);
   }

   public elu(String var1, String var2) {
      this(c(_snowman + '#' + _snowman));
   }

   protected static String[] c(String var0) {
      String[] _snowman = new String[]{null, _snowman, ""};
      int _snowmanx = _snowman.indexOf(35);
      String _snowmanxx = _snowman;
      if (_snowmanx >= 0) {
         _snowman[2] = _snowman.substring(_snowmanx + 1, _snowman.length());
         if (_snowmanx > 1) {
            _snowmanxx = _snowman.substring(0, _snowmanx);
         }
      }

      System.arraycopy(vk.b(_snowmanxx, ':'), 0, _snowman, 0, 2);
      return _snowman;
   }

   public String d() {
      return this.d;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman instanceof elu && super.equals(_snowman)) {
         elu _snowman = (elu)_snowman;
         return this.d.equals(_snowman.d);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return 31 * super.hashCode() + this.d.hashCode();
   }

   @Override
   public String toString() {
      return super.toString() + '#' + this.d;
   }
}
