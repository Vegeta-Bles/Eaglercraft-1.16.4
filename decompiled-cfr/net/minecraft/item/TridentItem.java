/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMultimap
 *  com.google.common.collect.ImmutableMultimap$Builder
 *  com.google.common.collect.Multimap
 */
package net.minecraft.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TridentItem
extends Item
implements Vanishable {
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public TridentItem(Item.Settings settings) {
        super(settings);
        ImmutableMultimap.Builder builder = ImmutableMultimap.builder();
        builder.put((Object)EntityAttributes.GENERIC_ATTACK_DAMAGE, (Object)new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", 8.0, EntityAttributeModifier.Operation.ADDITION));
        builder.put((Object)EntityAttributes.GENERIC_ATTACK_SPEED, (Object)new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", (double)-2.9f, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity playerEntity = (PlayerEntity)user;
        int _snowman2 = this.getMaxUseTime(stack) - remainingUseTicks;
        if (_snowman2 < 10) {
            return;
        }
        int _snowman3 = EnchantmentHelper.getRiptide(stack);
        if (_snowman3 > 0 && !playerEntity.isTouchingWaterOrRain()) {
            return;
        }
        if (!world.isClient) {
            stack.damage(1, playerEntity, p -> p.sendToolBreakStatus(user.getActiveHand()));
            if (_snowman3 == 0) {
                TridentEntity tridentEntity = new TridentEntity(world, (LivingEntity)playerEntity, stack);
                tridentEntity.setProperties(playerEntity, playerEntity.pitch, playerEntity.yaw, 0.0f, 2.5f + (float)_snowman3 * 0.5f, 1.0f);
                if (playerEntity.abilities.creativeMode) {
                    tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                }
                world.spawnEntity(tridentEntity);
                world.playSoundFromEntity(null, tridentEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);
                if (!playerEntity.abilities.creativeMode) {
                    playerEntity.inventory.removeOne(stack);
                }
            }
        }
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        if (_snowman3 > 0) {
            float f = playerEntity.yaw;
            _snowman = playerEntity.pitch;
            _snowman = -MathHelper.sin(f * ((float)Math.PI / 180)) * MathHelper.cos(_snowman * ((float)Math.PI / 180));
            _snowman = -MathHelper.sin(_snowman * ((float)Math.PI / 180));
            _snowman = MathHelper.cos(f * ((float)Math.PI / 180)) * MathHelper.cos(_snowman * ((float)Math.PI / 180));
            _snowman = MathHelper.sqrt(_snowman * _snowman + _snowman * _snowman + _snowman * _snowman);
            _snowman = 3.0f * ((1.0f + (float)_snowman3) / 4.0f);
            playerEntity.addVelocity(_snowman *= _snowman / _snowman, _snowman *= _snowman / _snowman, _snowman *= _snowman / _snowman);
            playerEntity.setRiptideTicks(20);
            if (playerEntity.isOnGround()) {
                _snowman = 1.1999999f;
                playerEntity.move(MovementType.SELF, new Vec3d(0.0, 1.1999999284744263, 0.0));
            }
            SoundEvent _snowman4 = _snowman3 >= 3 ? SoundEvents.ITEM_TRIDENT_RIPTIDE_3 : (_snowman3 == 2 ? SoundEvents.ITEM_TRIDENT_RIPTIDE_2 : SoundEvents.ITEM_TRIDENT_RIPTIDE_1);
            world.playSoundFromEntity(null, playerEntity, _snowman4, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        }
        if (EnchantmentHelper.getRiptide(itemStack) > 0 && !user.isTouchingWaterOrRain()) {
            return TypedActionResult.fail(itemStack);
        }
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if ((double)state.getHardness(world, pos) != 0.0) {
            stack.damage(2, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }

    @Override
    public int getEnchantability() {
        return 1;
    }
}

