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

   static void writeToPath(Gson gson, DataCache cache, JsonElement output, Path path) throws IOException {
      String string = gson.toJson(output);
      String string2 = SHA1.hashUnencodedChars(string).toString();
      if (!Objects.equals(cache.getOldSha1(path), string2) || !Files.exists(path)) {
         Files.createDirectories(path.getParent());

         try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            bufferedWriter.write(string);
         }
      }

      cache.updateSha1(path, string2);
   }
}
