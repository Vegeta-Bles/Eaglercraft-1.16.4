package net.minecraft.client.resource.language;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TranslationStorage extends Language {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<String, String> translations;
   private final boolean rightToLeft;

   private TranslationStorage(Map<String, String> translations, boolean rightToLeft) {
      this.translations = translations;
      this.rightToLeft = rightToLeft;
   }

   public static TranslationStorage load(ResourceManager resourceManager, List<LanguageDefinition> definitions) {
      Map<String, String> _snowman = Maps.newHashMap();
      boolean _snowmanx = false;

      for (LanguageDefinition _snowmanxx : definitions) {
         _snowmanx |= _snowmanxx.isRightToLeft();
         String _snowmanxxx = String.format("lang/%s.json", _snowmanxx.getCode());

         for (String _snowmanxxxx : resourceManager.getAllNamespaces()) {
            try {
               Identifier _snowmanxxxxx = new Identifier(_snowmanxxxx, _snowmanxxx);
               load(resourceManager.getAllResources(_snowmanxxxxx), _snowman);
            } catch (FileNotFoundException var10) {
            } catch (Exception var11) {
               LOGGER.warn("Skipped language file: {}:{} ({})", _snowmanxxxx, _snowmanxxx, var11.toString());
            }
         }
      }

      return new TranslationStorage(ImmutableMap.copyOf(_snowman), _snowmanx);
   }

   private static void load(List<Resource> resources, Map<String, String> translationMap) {
      for (Resource _snowman : resources) {
         try (InputStream _snowmanx = _snowman.getInputStream()) {
            Language.load(_snowmanx, translationMap::put);
         } catch (IOException var17) {
            LOGGER.warn("Failed to load translations from {}", _snowman, var17);
         }
      }
   }

   @Override
   public String get(String key) {
      return this.translations.getOrDefault(key, key);
   }

   @Override
   public boolean hasTranslation(String key) {
      return this.translations.containsKey(key);
   }

   @Override
   public boolean isRightToLeft() {
      return this.rightToLeft;
   }

   @Override
   public OrderedText reorder(StringVisitable text) {
      return ReorderingUtil.reorder(text, this.rightToLeft);
   }
}
