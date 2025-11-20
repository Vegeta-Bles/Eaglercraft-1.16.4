/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public interface FeatureRendererContext<T extends Entity, M extends EntityModel<T>> {
    public M getModel();

    public Identifier getTexture(T var1);
}

