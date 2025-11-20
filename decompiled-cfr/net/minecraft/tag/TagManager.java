/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.tag;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.tag.RequiredTagListRegistry;
import net.minecraft.tag.TagGroup;
import net.minecraft.util.registry.Registry;

public interface TagManager {
    public static final TagManager EMPTY = TagManager.create(TagGroup.createEmpty(), TagGroup.createEmpty(), TagGroup.createEmpty(), TagGroup.createEmpty());

    public TagGroup<Block> getBlocks();

    public TagGroup<Item> getItems();

    public TagGroup<Fluid> getFluids();

    public TagGroup<EntityType<?>> getEntityTypes();

    default public void apply() {
        RequiredTagListRegistry.updateTagManager(this);
        Blocks.refreshShapeCache();
    }

    default public void toPacket(PacketByteBuf buf) {
        this.getBlocks().toPacket(buf, Registry.BLOCK);
        this.getItems().toPacket(buf, Registry.ITEM);
        this.getFluids().toPacket(buf, Registry.FLUID);
        this.getEntityTypes().toPacket(buf, Registry.ENTITY_TYPE);
    }

    public static TagManager fromPacket(PacketByteBuf buf) {
        TagGroup<Block> tagGroup = TagGroup.fromPacket(buf, Registry.BLOCK);
        TagGroup<Item> _snowman2 = TagGroup.fromPacket(buf, Registry.ITEM);
        TagGroup<Fluid> _snowman3 = TagGroup.fromPacket(buf, Registry.FLUID);
        TagGroup<EntityType<?>> _snowman4 = TagGroup.fromPacket(buf, Registry.ENTITY_TYPE);
        return TagManager.create(tagGroup, _snowman2, _snowman3, _snowman4);
    }

    public static TagManager create(TagGroup<Block> blocks, TagGroup<Item> items, TagGroup<Fluid> fluids, TagGroup<EntityType<?>> entityTypes) {
        return new TagManager(blocks, items, fluids, entityTypes){
            final /* synthetic */ TagGroup field_25745;
            final /* synthetic */ TagGroup field_25746;
            final /* synthetic */ TagGroup field_25747;
            final /* synthetic */ TagGroup field_25748;
            {
                this.field_25745 = tagGroup;
                this.field_25746 = tagGroup2;
                this.field_25747 = tagGroup3;
                this.field_25748 = tagGroup4;
            }

            public TagGroup<Block> getBlocks() {
                return this.field_25745;
            }

            public TagGroup<Item> getItems() {
                return this.field_25746;
            }

            public TagGroup<Fluid> getFluids() {
                return this.field_25747;
            }

            public TagGroup<EntityType<?>> getEntityTypes() {
                return this.field_25748;
            }
        };
    }
}

