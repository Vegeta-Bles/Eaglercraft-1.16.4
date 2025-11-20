package net.minecraft.data.client.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public class Model {
   private final Optional<Identifier> parent;
   private final Set<TextureKey> requiredTextures;
   private Optional<String> variant;

   public Model(Optional<Identifier> parent, Optional<String> variant, TextureKey... requiredTextures) {
      this.parent = parent;
      this.variant = variant;
      this.requiredTextures = ImmutableSet.copyOf(requiredTextures);
   }

   public Identifier upload(Block block, Texture texture, BiConsumer<Identifier, Supplier<JsonElement>> modelCollector) {
      return this.upload(ModelIds.getBlockSubModelId(block, this.variant.orElse("")), texture, modelCollector);
   }

   public Identifier upload(Block block, String suffix, Texture texture, BiConsumer<Identifier, Supplier<JsonElement>> modelCollector) {
      return this.upload(ModelIds.getBlockSubModelId(block, suffix + this.variant.orElse("")), texture, modelCollector);
   }

   public Identifier uploadWithoutVariant(Block block, String suffix, Texture texture, BiConsumer<Identifier, Supplier<JsonElement>> modelCollector) {
      return this.upload(ModelIds.getBlockSubModelId(block, suffix), texture, modelCollector);
   }

   public Identifier upload(Identifier id, Texture texture, BiConsumer<Identifier, Supplier<JsonElement>> modelCollector) {
      Map<TextureKey, Identifier> _snowman = this.createTextureMap(texture);
      modelCollector.accept(id, () -> {
         JsonObject _snowmanx = new JsonObject();
         this.parent.ifPresent(_snowmanxx -> _snowman.addProperty("parent", _snowmanxx.toString()));
         if (!_snowman.isEmpty()) {
            JsonObject _snowmanx = new JsonObject();
            _snowman.forEach((_snowmanxxx, _snowmanxxx) -> _snowman.addProperty(_snowmanxxx.getName(), _snowmanxxx.toString()));
            _snowmanx.add("textures", _snowmanx);
         }

         return _snowmanx;
      });
      return id;
   }

   private Map<TextureKey, Identifier> createTextureMap(Texture texture) {
      return Streams.concat(new Stream[]{this.requiredTextures.stream(), texture.getInherited()})
         .collect(ImmutableMap.toImmutableMap(Function.identity(), texture::getTexture));
   }
}
