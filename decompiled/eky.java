import com.mojang.bridge.game.Language;

public class eky implements Language, Comparable<eky> {
   private final String a;
   private final String b;
   private final String c;
   private final boolean d;

   public eky(String var1, String var2, String var3, boolean var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   public String getCode() {
      return this.a;
   }

   public String getName() {
      return this.c;
   }

   public String getRegion() {
      return this.b;
   }

   public boolean a() {
      return this.d;
   }

   @Override
   public String toString() {
      return String.format("%s (%s)", this.c, this.b);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else {
         return !(_snowman instanceof eky) ? false : this.a.equals(((eky)_snowman).a);
      }
   }

   @Override
   public int hashCode() {
      return this.a.hashCode();
   }

   public int a(eky var1) {
      return this.a.compareTo(_snowman.a);
   }
}
