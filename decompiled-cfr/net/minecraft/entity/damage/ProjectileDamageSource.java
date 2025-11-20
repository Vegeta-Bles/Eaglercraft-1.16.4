/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.damage;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ProjectileDamageSource
extends EntityDamageSource {
    private final Entity attacker;

    public ProjectileDamageSource(String name, Entity projectile, @Nullable Entity attacker) {
        super(name, projectile);
        this.attacker = attacker;
    }

    @Override
    @Nullable
    public Entity getSource() {
        return this.source;
    }

    @Override
    @Nullable
    public Entity getAttacker() {
        return this.attacker;
    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        Text text = this.attacker == null ? this.source.getDisplayName() : this.attacker.getDisplayName();
        ItemStack _snowman2 = this.attacker instanceof LivingEntity ? ((LivingEntity)this.attacker).getMainHandStack() : ItemStack.EMPTY;
        String _snowman3 = "death.attack." + this.name;
        String _snowman4 = _snowman3 + ".item";
        if (!_snowman2.isEmpty() && _snowman2.hasCustomName()) {
            return new TranslatableText(_snowman4, entity.getDisplayName(), text, _snowman2.toHoverableText());
        }
        return new TranslatableText(_snowman3, entity.getDisplayName(), text);
    }
}

