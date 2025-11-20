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
      CompletableFuture<Map<Identifier, Tag.Builder>> completableFuture = this.blocks.prepareReload(manager, prepareExecutor);
      CompletableFuture<Map<Identifier, Tag.Builder>> completableFuture2 = this.items.prepareReload(manager, prepareExecutor);
      CompletableFuture<Map<Identifier, Tag.Builder>> completableFuture3 = this.fluids.prepareReload(manager, prepareExecutor);
      CompletableFuture<Map<Identifier, Tag.Builder>> completableFuture4 = this.entityTypes.prepareReload(manager, prepareExecutor);
      return CompletableFuture.allOf(completableFuture, completableFuture2, completableFuture3, completableFuture4)
         .thenCompose(synchronizer::whenPrepared)
         .thenAcceptAsync(
            void_ -> {
               TagGroup<Block> lv = this.blocks.applyReload(completableFuture.join());
               TagGroup<Item> lv2 = this.items.applyReload(completableFuture2.join());
               TagGroup<Fluid> lv3 = this.fluids.applyReload(completableFuture3.join());
               TagGroup<EntityType<?>> lv4 = this.entityTypes.applyReload(completableFuture4.join());
               TagManager lv5 = TagManager.create(lv, lv2, lv3, lv4);
               Multimap<Identifier, Identifier> multimap = RequiredTagListRegistry.getMissingTags(lv5);
               if (!multimap.isEmpty()) {
                  throw new IllegalStateException(
                     "Missing required tags: "
                        + multimap.entries().stream().map(entry -> entry.getKey() + ":" + entry.getValue()).sorted().collect(Collectors.joining(","))
                  );
               } else {
                  ServerTagManagerHolder.setTagManager(lv5);
                  this.tagManager = lv5;
               }
            },
            applyExecutor
         );
   }
}
