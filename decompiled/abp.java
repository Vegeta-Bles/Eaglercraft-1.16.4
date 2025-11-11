import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class abp implements abn<abo> {
   public abp() {
   }

   public abo b(JsonObject var1) {
      nr _snowman = nr.a.a(_snowman.get("description"));
      if (_snowman == null) {
         throw new JsonParseException("Invalid/missing description!");
      } else {
         int _snowmanx = afd.n(_snowman, "pack_format");
         return new abo(_snowman, _snowmanx);
      }
   }

   @Override
   public String a() {
      return "pack";
   }
}
