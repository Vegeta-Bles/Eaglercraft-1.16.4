import javax.annotation.Nullable;

public class kz extends ky {
   private final fx a;
   private final fx b;
   private final long c;

   @Override
   public String getMessage() {
      String _snowman = "" + this.a.u() + "," + this.a.v() + "," + this.a.w() + " (relative: " + this.b.u() + "," + this.b.v() + "," + this.b.w() + ")";
      return super.getMessage() + " at " + _snowman + " (t=" + this.c + ")";
   }

   @Nullable
   public String a() {
      return super.getMessage() + " here";
   }

   @Nullable
   public fx c() {
      return this.a;
   }
}
