/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.render.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.HorseBaseEntityRenderer;
import net.minecraft.client.render.entity.model.DonkeyEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.util.Identifier;

public class DonkeyEntityRenderer<T extends AbstractDonkeyEntity>
extends HorseBaseEntityRenderer<T, DonkeyEntityModel<T>> {
    private static final Map<EntityType<?>, Identifier> TEXTURES = Maps.newHashMap((Map)ImmutableMap.of(EntityType.DONKEY, (Object)new Identifier("textures/entity/horse/donkey.png"), EntityType.MULE, (Object)new Identifier("textures/entity/horse/mule.png")));

    public DonkeyEntityRenderer(EntityRenderDispatcher dispatcher, float scale) {
        super(dispatcher, new DonkeyEntityModel(0.0f), scale);
    }

    @Override
    public Identifier getTexture(T t) {
        return TEXTURES.get(((Entity)t).getType());
    }
}

