package net.minecraft.client.resource.metadata;

import java.util.Collection;
import net.minecraft.client.resource.language.LanguageDefinition;

public class LanguageResourceMetadata {
   public static final LanguageResourceMetadataReader READER = new LanguageResourceMetadataReader();
   private final Collection<LanguageDefinition> definitions;

   public LanguageResourceMetadata(Collection<LanguageDefinition> _snowman) {
      this.definitions = _snowman;
   }

   public Collection<LanguageDefinition> getLanguageDefinitions() {
      return this.definitions;
   }
}
