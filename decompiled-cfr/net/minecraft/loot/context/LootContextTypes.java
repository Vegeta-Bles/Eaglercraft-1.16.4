/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.BiMap
 *  com.google.common.collect.HashBiMap
 *  javax.annotation.Nullable
 */
package net.minecraft.loot.context;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.util.Identifier;

public class LootContextTypes {
    private static final BiMap<Identifier, LootContextType> MAP = HashBiMap.create();
    public static final LootContextType EMPTY = LootContextTypes.register("empty", builder -> {});
    public static final LootContextType CHEST = LootContextTypes.register("chest", builder -> builder.require(LootContextParameters.ORIGIN).allow(LootContextParameters.THIS_ENTITY));
    public static final LootContextType COMMAND = LootContextTypes.register("command", builder -> builder.require(LootContextParameters.ORIGIN).allow(LootContextParameters.THIS_ENTITY));
    public static final LootContextType SELECTOR = LootContextTypes.register("selector", builder -> builder.require(LootContextParameters.ORIGIN).require(LootContextParameters.THIS_ENTITY));
    public static final LootContextType FISHING = LootContextTypes.register("fishing", builder -> builder.require(LootContextParameters.ORIGIN).require(LootContextParameters.TOOL).allow(LootContextParameters.THIS_ENTITY));
    public static final LootContextType ENTITY = LootContextTypes.register("entity", builder -> builder.require(LootContextParameters.THIS_ENTITY).require(LootContextParameters.ORIGIN).require(LootContextParameters.DAMAGE_SOURCE).allow(LootContextParameters.KILLER_ENTITY).allow(LootContextParameters.DIRECT_KILLER_ENTITY).allow(LootContextParameters.LAST_DAMAGE_PLAYER));
    public static final LootContextType GIFT = LootContextTypes.register("gift", builder -> builder.require(LootContextParameters.ORIGIN).require(LootContextParameters.THIS_ENTITY));
    public static final LootContextType BARTER = LootContextTypes.register("barter", builder -> builder.require(LootContextParameters.THIS_ENTITY));
    public static final LootContextType ADVANCEMENT_REWARD = LootContextTypes.register("advancement_reward", builder -> builder.require(LootContextParameters.THIS_ENTITY).require(LootContextParameters.ORIGIN));
    public static final LootContextType ADVANCEMENT_ENTITY = LootContextTypes.register("advancement_entity", builder -> builder.require(LootContextParameters.THIS_ENTITY).require(LootContextParameters.ORIGIN));
    public static final LootContextType GENERIC = LootContextTypes.register("generic", builder -> builder.require(LootContextParameters.THIS_ENTITY).require(LootContextParameters.LAST_DAMAGE_PLAYER).require(LootContextParameters.DAMAGE_SOURCE).require(LootContextParameters.KILLER_ENTITY).require(LootContextParameters.DIRECT_KILLER_ENTITY).require(LootContextParameters.ORIGIN).require(LootContextParameters.BLOCK_STATE).require(LootContextParameters.BLOCK_ENTITY).require(LootContextParameters.TOOL).require(LootContextParameters.EXPLOSION_RADIUS));
    public static final LootContextType BLOCK = LootContextTypes.register("block", builder -> builder.require(LootContextParameters.BLOCK_STATE).require(LootContextParameters.ORIGIN).require(LootContextParameters.TOOL).allow(LootContextParameters.THIS_ENTITY).allow(LootContextParameters.BLOCK_ENTITY).allow(LootContextParameters.EXPLOSION_RADIUS));

    private static LootContextType register(String name, Consumer<LootContextType.Builder> type) {
        LootContextType.Builder builder = new LootContextType.Builder();
        type.accept(builder);
        LootContextType _snowman2 = builder.build();
        Identifier _snowman3 = new Identifier(name);
        LootContextType _snowman4 = (LootContextType)MAP.put((Object)_snowman3, (Object)_snowman2);
        if (_snowman4 != null) {
            throw new IllegalStateException("Loot table parameter set " + _snowman3 + " is already registered");
        }
        return _snowman2;
    }

    @Nullable
    public static LootContextType get(Identifier id) {
        return (LootContextType)MAP.get((Object)id);
    }

    @Nullable
    public static Identifier getId(LootContextType type) {
        return (Identifier)MAP.inverse().get((Object)type);
    }
}

