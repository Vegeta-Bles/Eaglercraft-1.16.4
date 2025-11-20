/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Multimap
 */
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
import net.minecraft.tag.RequiredTagListRegistry;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroup;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.tag.TagManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;

public class TagManagerLoader
implements ResourceReloadListener {
    private final TagGroupLoader<Block> blocks = new TagGroupLoader(Registry.BLOCK::getOrEmpty, "tags/blocks", "block");
    private final TagGroupLoader<Item> items = new TagGroupLoader(Registry.ITEM::getOrEmpty, "tags/items", "item");
    private final TagGroupLoader<Fluid> fluids = new TagGroupLoader(Registry.FLUID::getOrEmpty, "tags/fluids", "fluid");
    private final TagGroupLoader<EntityType<?>> entityTypes = new TagGroupLoader(Registry.ENTITY_TYPE::getOrEmpty, "tags/entity_types", "entity_type");
    private TagManager tagManager = TagManager.EMPTY;

    public TagManager getTagManager() {
        return this.tagManager;
    }

    @Override
    public CompletableFuture<Void> reload(ResourceReloadListener.Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        CompletableFuture<Map<Identifier, Tag.Builder>> completableFuture = this.blocks.prepareReload(manager, prepareExecutor);
        _snowman = this.items.prepareReload(manager, prepareExecutor);
        _snowman = this.fluids.prepareReload(manager, prepareExecutor);
        _snowman = this.entityTypes.prepareReload(manager, prepareExecutor);
        return ((CompletableFuture)CompletableFuture.allOf(completableFuture, _snowman, _snowman, _snowman).thenCompose(synchronizer::whenPrepared)).thenAcceptAsync(void_ -> {
            TagGroup<Block> tagGroup = this.blocks.applyReload((Map)completableFuture.join());
            TagManager _snowman2 = TagManager.create(tagGroup, _snowman = this.items.applyReload((Map)_snowman.join()), _snowman = this.fluids.applyReload((Map)_snowman.join()), _snowman = this.entityTypes.applyReload((Map)_snowman.join()));
            Multimap<Identifier, Identifier> _snowman3 = RequiredTagListRegistry.getMissingTags(_snowman2);
            if (!_snowman3.isEmpty()) {
                throw new IllegalStateException("Missing required tags: " + _snowman3.entries().stream().map(entry -> entry.getKey() + ":" + entry.getValue()).sorted().collect(Collectors.joining(",")));
            }
            ServerTagManagerHolder.setTagManager(_snowman2);
            this.tagManager = _snowman2;
        }, applyExecutor);
    }
}

