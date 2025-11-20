package net.minecraft.data;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public interface DataProvider {
   HashFunction SHA1 = Hashing.sha1();

   void run(DataCache cache) throws IOException;

   String getName();

   static void writeToPath(Gson gson, DataCache cache, JsonElement output, Path _snowman) throws IOException {
      String _snowmanx = gson.toJson(output);
      String _snowmanxx = SHA1.hashUnencodedChars(_snowmanx).toString();
      if (!Objects.equals(cache.getOldSha1(_snowman), _snowmanxx) || !Files.exists(_snowman)) {
         Files.createDirectories(_snowman.getParent());

         try (BufferedWriter _snowmanxxx = Files.newBufferedWriter(_snowman)) {
            _snowmanxxx.write(_snowmanx);
         }
      }

      cache.updateSha1(_snowman, _snowmanxx);
   }
}
