/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.attribute;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.util.registry.Registry;

public class DefaultAttributeContainer {
    private final Map<EntityAttribute, EntityAttributeInstance> instances;

    public DefaultAttributeContainer(Map<EntityAttribute, EntityAttributeInstance> instances) {
        this.instances = ImmutableMap.copyOf(instances);
    }

    private EntityAttributeInstance require(EntityAttribute attribute) {
        EntityAttributeInstance entityAttributeInstance = this.instances.get(attribute);
        if (entityAttributeInstance == null) {
            throw new IllegalArgumentException("Can't find attribute " + Registry.ATTRIBUTE.getId(attribute));
        }
        return entityAttributeInstance;
    }

    public double getValue(EntityAttribute attribute) {
        return this.require(attribute).getValue();
    }

    public double getBaseValue(EntityAttribute attribute) {
        return this.require(attribute).getBaseValue();
    }

    public double getModifierValue(EntityAttribute attribute, UUID uuid) {
        EntityAttributeModifier entityAttributeModifier = this.require(attribute).getModifier(uuid);
        if (entityAttributeModifier == null) {
            throw new IllegalArgumentException("Can't find modifier " + uuid + " on attribute " + Registry.ATTRIBUTE.getId(attribute));
        }
        return entityAttributeModifier.getValue();
    }

    @Nullable
    public EntityAttributeInstance createOverride(Consumer<EntityAttributeInstance> updateCallback, EntityAttribute attribute) {
        EntityAttributeInstance entityAttributeInstance = this.instances.get(attribute);
        if (entityAttributeInstance == null) {
            return null;
        }
        _snowman = new EntityAttributeInstance(attribute, updateCallback);
        _snowman.setFrom(entityAttributeInstance);
        return _snowman;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean has(EntityAttribute type) {
        return this.instances.containsKey(type);
    }

    public boolean hasModifier(EntityAttribute type, UUID uuid) {
        EntityAttributeInstance entityAttributeInstance = this.instances.get(type);
        return entityAttributeInstance != null && entityAttributeInstance.getModifier(uuid) != null;
    }

    public static class Builder {
        private final Map<EntityAttribute, EntityAttributeInstance> instances = Maps.newHashMap();
        private boolean unmodifiable;

        private EntityAttributeInstance checkedAdd(EntityAttribute attribute2) {
            EntityAttributeInstance entityAttributeInstance = new EntityAttributeInstance(attribute2, attribute -> {
                if (this.unmodifiable) {
                    throw new UnsupportedOperationException("Tried to change value for default attribute instance: " + Registry.ATTRIBUTE.getId(attribute2));
                }
            });
            this.instances.put(attribute2, entityAttributeInstance);
            return entityAttributeInstance;
        }

        public Builder add(EntityAttribute attribute) {
            this.checkedAdd(attribute);
            return this;
        }

        public Builder add(EntityAttribute attribute, double baseValue) {
            EntityAttributeInstance entityAttributeInstance = this.checkedAdd(attribute);
            entityAttributeInstance.setBaseValue(baseValue);
            return this;
        }

        public DefaultAttributeContainer build() {
            this.unmodifiable = true;
            return new DefaultAttributeContainer(this.instances);
        }
    }
}

