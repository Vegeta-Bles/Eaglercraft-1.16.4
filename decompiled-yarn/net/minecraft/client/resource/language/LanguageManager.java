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
import net.minecraft.client.resource.metadata.LanguageResourceMetadata;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.SynchronousResourceReloadListener;
import net.minecraft.util.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanguageManager implements SynchronousResourceReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final LanguageDefinition field_25291 = new LanguageDefinition("en_us", "US", "English", false);
   private Map<String, LanguageDefinition> languageDefs = ImmutableMap.of("en_us", field_25291);
   private String currentLanguageCode;
   private LanguageDefinition language = field_25291;

   public LanguageManager(String _snowman) {
      this.currentLanguageCode = _snowman;
   }

   private static Map<String, LanguageDefinition> method_29393(Stream<ResourcePack> _snowman) {
      Map<String, LanguageDefinition> _snowmanx = Maps.newHashMap();
      _snowman.forEach(_snowmanxxxx -> {
         try {
            LanguageResourceMetadata _snowmanxx = _snowmanxxxx.parseMetadata(LanguageResourceMetadata.READER);
            if (_snowmanxx != null) {
               for (LanguageDefinition _snowmanxxx : _snowmanxx.getLanguageDefinitions()) {
                  _snowman.putIfAbsent(_snowmanxxx.getCode(), _snowmanxxx);
               }
            }
         } catch (IOException | RuntimeException var5) {
            LOGGER.warn("Unable to parse language metadata section of resourcepack: {}", _snowmanxxxx.getName(), var5);
         }
      });
      return ImmutableMap.copyOf(_snowmanx);
   }

   @Override
   public void apply(ResourceManager manager) {
      this.languageDefs = method_29393(manager.streamResourcePacks());
      LanguageDefinition _snowman = this.languageDefs.getOrDefault("en_us", field_25291);
      this.language = this.languageDefs.getOrDefault(this.currentLanguageCode, _snowman);
      List<LanguageDefinition> _snowmanx = Lists.newArrayList(new LanguageDefinition[]{_snowman});
      if (this.language != _snowman) {
         _snowmanx.add(this.language);
      }

      TranslationStorage _snowmanxx = TranslationStorage.load(manager, _snowmanx);
      I18n.method_29391(_snowmanxx);
      Language.setInstance(_snowmanxx);
   }

   public void setLanguage(LanguageDefinition _snowman) {
      this.currentLanguageCode = _snowman.getCode();
      this.language = _snowman;
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
