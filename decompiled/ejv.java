import com.google.common.collect.Lists;
import javax.annotation.Nullable;

public final class ejv extends ekc {
   private static final vk b = new vk("missingno");
   @Nullable
   private static ejs c;
   private static final afi<det> d = new afi<>(() -> {
      det _snowman = new det(16, 16, false);
      int _snowmanx = -16777216;
      int _snowmanxx = -524040;

      for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
         for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
            if (_snowmanxxx < 8 ^ _snowmanxxxx < 8) {
               _snowman.a(_snowmanxxxx, _snowmanxxx, -524040);
            } else {
               _snowman.a(_snowmanxxxx, _snowmanxxx, -16777216);
            }
         }
      }

      _snowman.g();
      return _snowman;
   });
   private static final ekc.a e = new ekc.a(b, 16, 16, new elc(Lists.newArrayList(new elb[]{new elb(0, -1)}), 16, 16, 1, false));

   private ejv(ekb var1, int var2, int var3, int var4, int var5, int var6) {
      super(_snowman, e, _snowman, _snowman, _snowman, _snowman, _snowman, d.a());
   }

   public static ejv a(ekb var0, int var1, int var2, int var3, int var4, int var5) {
      return new ejv(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static vk a() {
      return b;
   }

   public static ekc.a b() {
      return e;
   }

   @Override
   public void close() {
      for (int _snowman = 1; _snowman < this.a.length; _snowman++) {
         this.a[_snowman].close();
      }
   }

   public static ejs c() {
      if (c == null) {
         c = new ejs(d.a());
         djz.C().M().a(b, c);
      }

      return c;
   }
}
