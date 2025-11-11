import com.google.gson.annotations.SerializedName;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;

public class diq {
   private static final dgi a = new dgi();

   public static diq.a a() {
      File _snowman = b();

      try {
         return a.a(FileUtils.readFileToString(_snowman, StandardCharsets.UTF_8), diq.a.class);
      } catch (IOException var2) {
         return new diq.a();
      }
   }

   public static void a(diq.a var0) {
      File _snowman = b();

      try {
         FileUtils.writeStringToFile(_snowman, a.a(_snowman), StandardCharsets.UTF_8);
      } catch (IOException var3) {
      }
   }

   private static File b() {
      return new File(djz.C().n, "realms_persistence.json");
   }

   public static class a implements dgy {
      @SerializedName("newsLink")
      public String a;
      @SerializedName("hasUnreadNews")
      public boolean b;

      public a() {
      }
   }
}
