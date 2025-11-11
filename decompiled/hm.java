import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public interface hm {
   HashFunction a = Hashing.sha1();

   void a(hn var1) throws IOException;

   String a();

   static void a(Gson var0, hn var1, JsonElement var2, Path var3) throws IOException {
      String _snowman = _snowman.toJson(_snowman);
      String _snowmanx = a.hashUnencodedChars(_snowman).toString();
      if (!Objects.equals(_snowman.a(_snowman), _snowmanx) || !Files.exists(_snowman)) {
         Files.createDirectories(_snowman.getParent());

         try (BufferedWriter _snowmanxx = Files.newBufferedWriter(_snowman)) {
            _snowmanxx.write(_snowman);
         }
      }

      _snowman.a(_snowman, _snowmanx);
   }
}
