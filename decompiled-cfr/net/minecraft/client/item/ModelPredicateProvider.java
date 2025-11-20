/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.item;

import javax.annotation.Nullable;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface ModelPredicateProvider {
    public float call(ItemStack var1, @Nullable ClientWorld var2, @Nullable LivingEntity var3);
}

