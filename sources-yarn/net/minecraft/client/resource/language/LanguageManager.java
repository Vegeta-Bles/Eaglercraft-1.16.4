package net.minecraft.client.resource.language;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Stream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.metadata.LanguageResourceMetadata;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.SynchronousResourceReloadListener;
import net.minecraft.util.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class LanguageManager implements SynchronousResourceReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final LanguageDefinition field_25291 = new LanguageDefinition("en_us", "US", "English", false);
   private Map<String, LanguageDefinition> languageDefs = ImmutableMap.of("en_us", field_25291);
   private String currentLanguageCode;
   private LanguageDefinition language = field_25291;

   public LanguageManager(String string) {
      this.currentLanguageCode = string;
   }

   private static Map<String, LanguageDefinition> method_29393(Stream<ResourcePack> stream) {
      Map<String, LanguageDefinition> map = Maps.newHashMap();
      stream.forEach(arg -> {
         try {
            LanguageResourceMetadata lv = arg.parseMetadata(LanguageResourceMetadata.READER);
            if (lv != null) {
               for (LanguageDefinition lv2 : lv.getLanguageDefinitions()) {
                  map.putIfAbsent(lv2.getCode(), lv2);
               }
            }
         } catch (IOException | RuntimeException var5) {
            LOGGER.warn("Unable to parse language metadata section of resourcepack: {}", arg.getName(), var5);
         }
      });
      return ImmutableMap.copyOf(map);
   }

   @Override
   public void apply(ResourceManager manager) {
      this.languageDefs = method_29393(manager.streamResourcePacks());
      LanguageDefinition lv = this.languageDefs.getOrDefault("en_us", field_25291);
      this.language = this.languageDefs.getOrDefault(this.currentLanguageCode, lv);
      List<LanguageDefinition> list = Lists.newArrayList(new LanguageDefinition[]{lv});
      if (this.language != lv) {
         list.add(this.language);
      }

      TranslationStorage lv2 = TranslationStorage.load(manager, list);
      I18n.method_29391(lv2);
      Language.setInstance(lv2);
   }

   public void setLanguage(LanguageDefinition arg) {
      this.currentLanguageCode = arg.getCode();
      this.language = arg;
   }

   public LanguageDefinition getLanguage() {
      return this.language;
   }

   public SortedSet<LanguageDefinition> getAllLanguages() {
      return Sets.newTreeSet(this.languageDefs.values());
   }

   public LanguageDefinition getLanguage(String code) {
      return this.languageDefs.get(code);
   }
}
