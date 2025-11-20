/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.color.block;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

public interface BlockColorProvider {
    public int getColor(BlockState var1, @Nullable BlockRenderView var2, @Nullable BlockPos var3, int var4);
}

