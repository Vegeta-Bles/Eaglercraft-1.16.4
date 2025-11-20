/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.model.json;

import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;

@FunctionalInterface
public interface MultipartModelSelector {
    public static final MultipartModelSelector TRUE = stateManager -> blockState -> true;
    public static final MultipartModelSelector FALSE = stateManager -> blockState -> false;

    public Predicate<BlockState> getPredicate(StateManager<Block, BlockState> var1);
}

