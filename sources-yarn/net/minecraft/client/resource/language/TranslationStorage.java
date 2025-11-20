package net.minecraft.client.resource.language;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class TranslationStorage extends Language {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<String, String> translations;
   private final boolean rightToLeft;

   private TranslationStorage(Map<String, String> translations, boolean rightToLeft) {
      this.translations = translations;
      this.rightToLeft = rightToLeft;
   }

   public static TranslationStorage load(ResourceManager resourceManager, List<LanguageDefinition> definitions) {
      Map<String, String> map = Maps.newHashMap();
      boolean bl = false;

      for (LanguageDefinition lv : definitions) {
         bl |= lv.isRightToLeft();
         String string = String.format("lang/%s.json", lv.getCode());

         for (String string2 : resourceManager.getAllNamespaces()) {
            try {
               Identifier lv2 = new Identifier(string2, string);
               load(resourceManager.getAllResources(lv2), map);
            } catch (FileNotFoundException var10) {
            } catch (Exception var11) {
               LOGGER.warn("Skipped language file: {}:{} ({})", string2, string, var11.toString());
            }
         }
      }

      return new TranslationStorage(ImmutableMap.copyOf(map), bl);
   }

   private static void load(List<Resource> resources, Map<String, String> translationMap) {
      for (Resource lv : resources) {
         try (InputStream inputStream = lv.getInputStream()) {
            Language.load(inputStream, translationMap::put);
         } catch (IOException var17) {
            LOGGER.warn("Failed to load translations from {}", lv, var17);
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
