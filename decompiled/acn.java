import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;

public class acn implements acg {
   private final String a;
   private final vk b;
   private final InputStream c;
   private final InputStream d;
   private boolean e;
   private JsonObject f;

   public acn(String var1, vk var2, InputStream var3, @Nullable InputStream var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   @Override
   public vk a() {
      return this.b;
   }

   @Override
   public InputStream b() {
      return this.c;
   }

   public boolean c() {
      return this.d != null;
   }

   @Nullable
   @Override
   public <T> T a(abn<T> var1) {
      if (!this.c()) {
         return null;
      } else {
         if (this.f == null && !this.e) {
            this.e = true;
            BufferedReader _snowman = null;

            try {
               _snowman = new BufferedReader(new InputStreamReader(this.d, StandardCharsets.UTF_8));
               this.f = afd.a(_snowman);
            } finally {
               IOUtils.closeQuietly(_snowman);
            }
         }

         if (this.f == null) {
            return null;
         } else {
            String _snowman = _snowman.a();
            return this.f.has(_snowman) ? _snowman.a(afd.t(this.f, _snowman)) : null;
         }
      }
   }

   @Override
   public String d() {
      return this.a;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof acn)) {
         return false;
      } else {
         acn _snowman = (acn)_snowman;
         if (this.b != null ? this.b.equals(_snowman.b) : _snowman.b == null) {
            return this.a != null ? this.a.equals(_snowman.a) : _snowman.a == null;
         } else {
            return false;
         }
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.a != null ? this.a.hashCode() : 0;
      return 31 * _snowman + (this.b != null ? this.b.hashCode() : 0);
   }

   @Override
   public void close() throws IOException {
      this.c.close();
      if (this.d != null) {
         this.d.close();
      }
   }
}
