package net.minecraft.client.resource.metadata;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.JsonHelper;

public class LanguageResourceMetadataReader implements ResourceMetadataReader<LanguageResourceMetadata> {
   public LanguageResourceMetadataReader() {
   }

   public LanguageResourceMetadata fromJson(JsonObject _snowman) {
      Set<LanguageDefinition> _snowmanx = Sets.newHashSet();

      for (Entry<String, JsonElement> _snowmanxx : _snowman.entrySet()) {
         String _snowmanxxx = _snowmanxx.getKey();
         if (_snowmanxxx.length() > 16) {
            throw new JsonParseException("Invalid language->'" + _snowmanxxx + "': language code must not be more than " + 16 + " characters long");
         }

         JsonObject _snowmanxxxx = JsonHelper.asObject(_snowmanxx.getValue(), "language");
         String _snowmanxxxxx = JsonHelper.getString(_snowmanxxxx, "region");
         String _snowmanxxxxxx = JsonHelper.getString(_snowmanxxxx, "name");
         boolean _snowmanxxxxxxx = JsonHelper.getBoolean(_snowmanxxxx, "bidirectional", false);
         if (_snowmanxxxxx.isEmpty()) {
            throw new JsonParseException("Invalid language->'" + _snowmanxxx + "'->region: empty value");
         }

         if (_snowmanxxxxxx.isEmpty()) {
            throw new JsonParseException("Invalid language->'" + _snowmanxxx + "'->name: empty value");
         }

         if (!_snowmanx.add(new LanguageDefinition(_snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx))) {
            throw new JsonParseException("Duplicate language->'" + _snowmanxxx + "' defined");
         }
      }

      return new LanguageResourceMetadata(_snowmanx);
   }

   @Override
   public String getKey() {
      return "language";
   }
}
