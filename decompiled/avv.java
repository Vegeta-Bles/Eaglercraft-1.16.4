import java.util.EnumSet;

public abstract class avv {
   private final EnumSet<avv.a> a = EnumSet.noneOf(avv.a.class);

   public avv() {
   }

   public abstract boolean a();

   public boolean b() {
      return this.a();
   }

   public boolean C_() {
      return true;
   }

   public void c() {
   }

   public void d() {
   }

   public void e() {
   }

   public void a(EnumSet<avv.a> var1) {
      this.a.clear();
      this.a.addAll(_snowman);
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName();
   }

   public EnumSet<avv.a> i() {
      return this.a;
   }

   public static enum a {
      a,
      b,
      c,
      d;

      private a() {
      }
   }
}
