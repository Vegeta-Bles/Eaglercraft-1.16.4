import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Set;
import java.util.Map.Entry;

public class eli implements abn<elh> {
   public eli() {
   }

   public elh b(JsonObject var1) {
      Set<eky> _snowman = Sets.newHashSet();

      for (Entry<String, JsonElement> _snowmanx : _snowman.entrySet()) {
         String _snowmanxx = _snowmanx.getKey();
         if (_snowmanxx.length() > 16) {
            throw new JsonParseException("Invalid language->'" + _snowmanxx + "': language code must not be more than " + 16 + " characters long");
         }

         JsonObject _snowmanxxx = afd.m(_snowmanx.getValue(), "language");
         String _snowmanxxxx = afd.h(_snowmanxxx, "region");
         String _snowmanxxxxx = afd.h(_snowmanxxx, "name");
         boolean _snowmanxxxxxx = afd.a(_snowmanxxx, "bidirectional", false);
         if (_snowmanxxxx.isEmpty()) {
            throw new JsonParseException("Invalid language->'" + _snowmanxx + "'->region: empty value");
         }

         if (_snowmanxxxxx.isEmpty()) {
            throw new JsonParseException("Invalid language->'" + _snowmanxx + "'->name: empty value");
         }

         if (!_snowman.add(new eky(_snowmanxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx))) {
            throw new JsonParseException("Duplicate language->'" + _snowmanxx + "' defined");
         }
      }

      return new elh(_snowman);
   }

   @Override
   public String a() {
      return "language";
   }
}
