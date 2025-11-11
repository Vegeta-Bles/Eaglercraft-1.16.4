import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class crm extends cla<cmi> {
   public crm(Codec<cmi> var1) {
      super(_snowman);
   }

   @Override
   public cla.a<cmi> a() {
      return crm.a::new;
   }

   public static class a extends crv<cmi> {
      public a(cla<cmi> var1, int var2, int var3, cra var4, int var5, long var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void a(gn var1, cfy var2, csw var3, int var4, int var5, bsv var6, cmi var7) {
         int _snowman = _snowman * 16;
         int _snowmanx = _snowman * 16;
         fx _snowmanxx = new fx(_snowman, 90, _snowmanx);
         bzm _snowmanxxx = bzm.a(this.d);
         crn.a(_snowman, _snowmanxx, _snowmanxxx, this.b, this.d, _snowman);
         this.b();
      }
   }

   public static enum b implements afs {
      a("warm"),
      b("cold");

      public static final Codec<crm.b> c = afs.a(crm.b::values, crm.b::a);
      private static final Map<String, crm.b> d = Arrays.stream(values()).collect(Collectors.toMap(crm.b::b, var0 -> (crm.b)var0));
      private final String e;

      private b(String var3) {
         this.e = _snowman;
      }

      public String b() {
         return this.e;
      }

      @Nullable
      public static crm.b a(String var0) {
         return d.get(_snowman);
      }

      @Override
      public String a() {
         return this.e;
      }
   }
}
