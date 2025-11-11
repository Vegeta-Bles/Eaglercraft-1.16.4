import com.google.gson.annotations.SerializedName;
import java.util.Locale;

public class dgz extends dhc implements dgy {
   @SerializedName("regionName")
   private final String a;
   @SerializedName("ping")
   private final int b;

   public dgz(String var1, int var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public int a() {
      return this.b;
   }

   @Override
   public String toString() {
      return String.format(Locale.ROOT, "%s --> %.2f ms", this.a, (float)this.b);
   }
}
