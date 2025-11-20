package net.minecraft.tag;

import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;

public class TagManagerLoader implements ResourceReloadListener {
   private final TagGroupLoader<Block> blocks = new TagGroupLoader<>(Registry.BLOCK::getOrEmpty, "tags/blocks", "block");
   private final TagGroupLoader<Item> items = new TagGroupLoader<>(Registry.ITEM::getOrEmpty, "tags/items", "item");
   private final TagGroupLoader<Fluid> fluids = new TagGroupLoader<>(Registry.FLUID::getOrEmpty, "tags/fluids", "fluid");
   private final TagGroupLoader<EntityType<?>> entityTypes = new TagGroupLoader<>(Registry.ENTITY_TYPE::getOrEmpty, "tags/entity_types", "entity_type");
   private TagManager tagManager = TagManager.EMPTY;

   public TagManagerLoader() {
   }

   public TagManager getTagManager() {
      return this.tagManager;
   }

   @Override
   public CompletableFuture<Void> reload(
      ResourceReloadListener.Synchronizer synchronizer,
      ResourceManager manager,
      Profiler prepareProfiler,
      Profiler applyProfiler,
      Executor prepareExecutor,
      Executor applyExecutor
   ) {
      CompletableFuture<Map<Identifier, Tag.Builder>> _snowman = this.blocks.prepareReload(manager, prepareExecutor);
      CompletableFuture<Map<Identifier, Tag.Builder>> _snowmanx = this.items.prepareReload(manager, prepareExecutor);
      CompletableFuture<Map<Identifier, Tag.Builder>> _snowmanxx = this.fluids.prepareReload(manager, prepareExecutor);
      CompletableFuture<Map<Identifier, Tag.Builder>> _snowmanxxx = this.entityTypes.prepareReload(manager, prepareExecutor);
      return CompletableFuture.allOf(_snowman, _snowmanx, _snowmanxx, _snowmanxxx)
         .thenCompose(synchronizer::whenPrepared)
         .thenAcceptAsync(
            _snowmanxxxx -> {
               TagGroup<Block> _snowmanxxxx = this.blocks.applyReload(_snowman.join());
               TagGroup<Item> _snowmanx = this.items.applyReload(_snowman.join());
               TagGroup<Fluid> _snowmanxx = this.fluids.applyReload(_snowman.join());
               TagGroup<EntityType<?>> _snowmanxxx = this.entityTypes.applyReload(_snowman.join());
               TagManager _snowmanxxxxx = TagManager.create(_snowmanxxxx, _snowmanx, _snowmanxx, _snowmanxxx);
               Multimap<Identifier, Identifier> _snowmanxxxxxx = RequiredTagListRegistry.getMissingTags(_snowmanxxxxx);
               if (!_snowmanxxxxxx.isEmpty()) {
                  throw new IllegalStateException(
                     "Missing required tags: "
                        + _snowmanxxxxxx.entries().stream().map(_snowmanxxxxxxx -> _snowmanxxxxxxx.getKey() + ":" + _snowmanxxxxxxx.getValue()).sorted().collect(Collectors.joining(","))
                  );
               } else {
                  ServerTagManagerHolder.setTagManager(_snowmanxxxxx);
                  this.tagManager = _snowmanxxxxx;
               }
            },
            applyExecutor
         );
   }
}
