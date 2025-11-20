/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Multimap
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity.attribute;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AttributeContainer {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<EntityAttribute, EntityAttributeInstance> custom = Maps.newHashMap();
    private final Set<EntityAttributeInstance> tracked = Sets.newHashSet();
    private final DefaultAttributeContainer fallback;

    public AttributeContainer(DefaultAttributeContainer defaultAttributes) {
        this.fallback = defaultAttributes;
    }

    private void updateTrackedStatus(EntityAttributeInstance instance) {
        if (instance.getAttribute().isTracked()) {
            this.tracked.add(instance);
        }
    }

    public Set<EntityAttributeInstance> getTracked() {
        return this.tracked;
    }

    public Collection<EntityAttributeInstance> getAttributesToSend() {
        return this.custom.values().stream().filter(attribute -> attribute.getAttribute().isTracked()).collect(Collectors.toList());
    }

    @Nullable
    public EntityAttributeInstance getCustomInstance(EntityAttribute attribute2) {
        return this.custom.computeIfAbsent(attribute2, attribute -> this.fallback.createOverride(this::updateTrackedStatus, (EntityAttribute)attribute));
    }

    public boolean hasAttribute(EntityAttribute attribute) {
        return this.custom.get(attribute) != null || this.fallback.has(attribute);
    }

    public boolean hasModifierForAttribute(EntityAttribute attribute, UUID uuid) {
        EntityAttributeInstance entityAttributeInstance = this.custom.get(attribute);
        return entityAttributeInstance != null ? entityAttributeInstance.getModifier(uuid) != null : this.fallback.hasModifier(attribute, uuid);
    }

    public double getValue(EntityAttribute attribute) {
        EntityAttributeInstance entityAttributeInstance = this.custom.get(attribute);
        return entityAttributeInstance != null ? entityAttributeInstance.getValue() : this.fallback.getValue(attribute);
    }

    public double getBaseValue(EntityAttribute attribute) {
        EntityAttributeInstance entityAttributeInstance = this.custom.get(attribute);
        return entityAttributeInstance != null ? entityAttributeInstance.getBaseValue() : this.fallback.getBaseValue(attribute);
    }

    public double getModifierValue(EntityAttribute attribute, UUID uuid) {
        EntityAttributeInstance entityAttributeInstance = this.custom.get(attribute);
        return entityAttributeInstance != null ? entityAttributeInstance.getModifier(uuid).getValue() : this.fallback.getModifierValue(attribute, uuid);
    }

    public void removeModifiers(Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers) {
        attributeModifiers.asMap().forEach((entityAttribute, collection) -> {
            EntityAttributeInstance entityAttributeInstance = this.custom.get(entityAttribute);
            if (entityAttributeInstance != null) {
                collection.forEach(entityAttributeInstance::removeModifier);
            }
        });
    }

    public void addTemporaryModifiers(Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers) {
        attributeModifiers.forEach((entityAttribute, entityAttributeModifier) -> {
            EntityAttributeInstance entityAttributeInstance = this.getCustomInstance((EntityAttribute)entityAttribute);
            if (entityAttributeInstance != null) {
                entityAttributeInstance.removeModifier((EntityAttributeModifier)entityAttributeModifier);
                entityAttributeInstance.addTemporaryModifier((EntityAttributeModifier)entityAttributeModifier);
            }
        });
    }

    public void setFrom(AttributeContainer other) {
        other.custom.values().forEach(entityAttributeInstance -> {
            _snowman = this.getCustomInstance(entityAttributeInstance.getAttribute());
            if (_snowman != null) {
                _snowman.setFrom((EntityAttributeInstance)entityAttributeInstance);
            }
        });
    }

    public ListTag toTag() {
        ListTag listTag = new ListTag();
        for (EntityAttributeInstance entityAttributeInstance : this.custom.values()) {
            listTag.add(entityAttributeInstance.toTag());
        }
        return listTag;
    }

    public void fromTag(ListTag tag) {
        for (int i = 0; i < tag.size(); ++i) {
            CompoundTag compoundTag = tag.getCompound(i);
            String _snowman2 = compoundTag.getString("Name");
            Util.ifPresentOrElse(Registry.ATTRIBUTE.getOrEmpty(Identifier.tryParse(_snowman2)), entityAttribute -> {
                EntityAttributeInstance entityAttributeInstance = this.getCustomInstance((EntityAttribute)entityAttribute);
                if (entityAttributeInstance != null) {
                    entityAttributeInstance.fromTag(compoundTag);
                }
            }, () -> LOGGER.warn("Ignoring unknown attribute '{}'", (Object)_snowman2));
        }
    }
}

