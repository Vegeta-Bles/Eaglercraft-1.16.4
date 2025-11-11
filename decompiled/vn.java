import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class vn extends IOException {
   private final List<vn.a> a = Lists.newArrayList();
   private final String b;

   public vn(String var1) {
      this.a.add(new vn.a());
      this.b = _snowman;
   }

   public vn(String var1, Throwable var2) {
      super(_snowman);
      this.a.add(new vn.a());
      this.b = _snowman;
   }

   public void a(String var1) {
      this.a.get(0).a(_snowman);
   }

   public void b(String var1) {
      this.a.get(0).a = _snowman;
      this.a.add(0, new vn.a());
   }

   @Override
   public String getMessage() {
      return "Invalid " + this.a.get(this.a.size() - 1) + ": " + this.b;
   }

   public static vn a(Exception var0) {
      if (_snowman instanceof vn) {
         return (vn)_snowman;
      } else {
         String _snowman = _snowman.getMessage();
         if (_snowman instanceof FileNotFoundException) {
            _snowman = "File not found";
         }

         return new vn(_snowman, _snowman);
      }
   }

   public static class a {
      @Nullable
      private String a;
      private final List<String> b = Lists.newArrayList();

      private a() {
      }

      private void a(String var1) {
         this.b.add(0, _snowman);
      }

      public String b() {
         return StringUtils.join(this.b, "->");
      }

      @Override
      public String toString() {
         if (this.a != null) {
            return this.b.isEmpty() ? this.a : this.a + " " + this.b();
         } else {
            return this.b.isEmpty() ? "(Unknown file)" : "(Unknown file) " + this.b();
         }
      }
   }
}
