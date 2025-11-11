public class oe extends nn {
   public static final nr d = new oe("");
   private final String e;

   public oe(String var1) {
      this.e = _snowman;
   }

   public String h() {
      return this.e;
   }

   @Override
   public String a() {
      return this.e;
   }

   public oe i() {
      return new oe(this.e);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof oe)) {
         return false;
      } else {
         oe _snowman = (oe)_snowman;
         return this.e.equals(_snowman.h()) && super.equals(_snowman);
      }
   }

   @Override
   public String toString() {
      return "TextComponent{text='" + this.e + '\'' + ", siblings=" + this.a + ", style=" + this.c() + '}';
   }
}
