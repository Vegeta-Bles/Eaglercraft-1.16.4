/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.Vanishable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CrossbowItem
extends RangedWeaponItem
implements Vanishable {
    private boolean charged = false;
    private boolean loaded = false;

    public CrossbowItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public Predicate<ItemStack> getHeldProjectiles() {
        return CROSSBOW_HELD_PROJECTILES;
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return BOW_PROJECTILES;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (CrossbowItem.isCharged(itemStack)) {
            CrossbowItem.shootAll(world, user, hand, itemStack, CrossbowItem.getSpeed(itemStack), 1.0f);
            CrossbowItem.setCharged(itemStack, false);
            return TypedActionResult.consume(itemStack);
        }
        if (!user.getArrowType(itemStack).isEmpty()) {
            if (!CrossbowItem.isCharged(itemStack)) {
                this.charged = false;
                this.loaded = false;
                user.setCurrentHand(hand);
            }
            return TypedActionResult.consume(itemStack);
        }
        return TypedActionResult.fail(itemStack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int n = this.getMaxUseTime(stack) - remainingUseTicks;
        float _snowman2 = CrossbowItem.getPullProgress(n, stack);
        if (_snowman2 >= 1.0f && !CrossbowItem.isCharged(stack) && CrossbowItem.loadProjectiles(user, stack)) {
            CrossbowItem.setCharged(stack, true);
            SoundCategory soundCategory = user instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_CROSSBOW_LOADING_END, soundCategory, 1.0f, 1.0f / (RANDOM.nextFloat() * 0.5f + 1.0f) + 0.2f);
        }
    }

    private static boolean loadProjectiles(LivingEntity shooter, ItemStack projectile) {
        int n = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, projectile);
        _snowman = n == 0 ? 1 : 3;
        boolean _snowman2 = shooter instanceof PlayerEntity && ((PlayerEntity)shooter).abilities.creativeMode;
        ItemStack _snowman3 = shooter.getArrowType(projectile);
        ItemStack _snowman4 = _snowman3.copy();
        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
            if (_snowman > 0) {
                _snowman3 = _snowman4.copy();
            }
            if (_snowman3.isEmpty() && _snowman2) {
                _snowman3 = new ItemStack(Items.ARROW);
                _snowman4 = _snowman3.copy();
            }
            if (CrossbowItem.loadProjectile(shooter, projectile, _snowman3, _snowman > 0, _snowman2)) continue;
            return false;
        }
        return true;
    }

    private static boolean loadProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative) {
        ItemStack itemStack;
        boolean bl;
        if (projectile.isEmpty()) {
            return false;
        }
        boolean bl2 = bl = creative && projectile.getItem() instanceof ArrowItem;
        if (!(bl || creative || simulated)) {
            itemStack = projectile.split(1);
            if (projectile.isEmpty() && shooter instanceof PlayerEntity) {
                ((PlayerEntity)shooter).inventory.removeOne(projectile);
            }
        } else {
            itemStack = projectile.copy();
        }
        CrossbowItem.putProjectile(crossbow, itemStack);
        return true;
    }

    public static boolean isCharged(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        return compoundTag != null && compoundTag.getBoolean("Charged");
    }

    public static void setCharged(ItemStack stack, boolean charged) {
        CompoundTag compoundTag = stack.getOrCreateTag();
        compoundTag.putBoolean("Charged", charged);
    }

    private static void putProjectile(ItemStack crossbow, ItemStack projectile) {
        CompoundTag compoundTag = crossbow.getOrCreateTag();
        ListTag _snowman2 = compoundTag.contains("ChargedProjectiles", 9) ? compoundTag.getList("ChargedProjectiles", 10) : new ListTag();
        _snowman = new CompoundTag();
        projectile.toTag(_snowman);
        _snowman2.add(_snowman);
        compoundTag.put("ChargedProjectiles", _snowman2);
    }

    private static List<ItemStack> getProjectiles(ItemStack crossbow) {
        ArrayList arrayList = Lists.newArrayList();
        CompoundTag _snowman2 = crossbow.getTag();
        if (_snowman2 != null && _snowman2.contains("ChargedProjectiles", 9) && (_snowman = _snowman2.getList("ChargedProjectiles", 10)) != null) {
            for (int i = 0; i < _snowman.size(); ++i) {
                CompoundTag compoundTag = _snowman.getCompound(i);
                arrayList.add(ItemStack.fromTag(compoundTag));
            }
        }
        return arrayList;
    }

    private static void clearProjectiles(ItemStack crossbow) {
        CompoundTag compoundTag = crossbow.getTag();
        if (compoundTag != null) {
            ListTag listTag = compoundTag.getList("ChargedProjectiles", 9);
            listTag.clear();
            compoundTag.put("ChargedProjectiles", listTag);
        }
    }

    public static boolean hasProjectile(ItemStack crossbow, Item projectile) {
        return CrossbowItem.getProjectiles(crossbow).stream().anyMatch(s -> s.getItem() == projectile);
    }

    private static void shoot(World world, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated) {
        ProjectileEntity projectileEntity;
        boolean bl;
        if (world.isClient) {
            return;
        }
        boolean bl2 = bl = projectile.getItem() == Items.FIREWORK_ROCKET;
        if (bl) {
            projectileEntity = new FireworkRocketEntity(world, projectile, shooter, shooter.getX(), shooter.getEyeY() - (double)0.15f, shooter.getZ(), true);
        } else {
            projectileEntity = CrossbowItem.createArrow(world, shooter, crossbow, projectile);
            if (creative || simulated != 0.0f) {
                ((PersistentProjectileEntity)projectileEntity).pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            }
        }
        if (shooter instanceof CrossbowUser) {
            CrossbowUser crossbowUser = (CrossbowUser)((Object)shooter);
            crossbowUser.shoot(crossbowUser.getTarget(), crossbow, projectileEntity, simulated);
        } else {
            Vec3d vec3d = shooter.getOppositeRotationVector(1.0f);
            Quaternion _snowman2 = new Quaternion(new Vector3f(vec3d), simulated, true);
            _snowman = shooter.getRotationVec(1.0f);
            Vector3f _snowman3 = new Vector3f(_snowman);
            _snowman3.rotate(_snowman2);
            projectileEntity.setVelocity(_snowman3.getX(), _snowman3.getY(), _snowman3.getZ(), speed, divergence);
        }
        crossbow.damage(bl ? 3 : 1, shooter, e -> e.sendToolBreakStatus(hand));
        world.spawnEntity(projectileEntity);
        world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0f, soundPitch);
    }

    private static PersistentProjectileEntity createArrow(World world, LivingEntity entity, ItemStack crossbow, ItemStack arrow) {
        ArrowItem arrowItem = (ArrowItem)(arrow.getItem() instanceof ArrowItem ? arrow.getItem() : Items.ARROW);
        PersistentProjectileEntity _snowman2 = arrowItem.createArrow(world, arrow, entity);
        if (entity instanceof PlayerEntity) {
            _snowman2.setCritical(true);
        }
        _snowman2.setSound(SoundEvents.ITEM_CROSSBOW_HIT);
        _snowman2.setShotFromCrossbow(true);
        int _snowman3 = EnchantmentHelper.getLevel(Enchantments.PIERCING, crossbow);
        if (_snowman3 > 0) {
            _snowman2.setPierceLevel((byte)_snowman3);
        }
        return _snowman2;
    }

    public static void shootAll(World world, LivingEntity entity, Hand hand, ItemStack stack, float speed, float divergence) {
        List<ItemStack> list = CrossbowItem.getProjectiles(stack);
        float[] _snowman2 = CrossbowItem.getSoundPitches(entity.getRandom());
        for (int i = 0; i < list.size(); ++i) {
            ItemStack itemStack = list.get(i);
            boolean bl = _snowman = entity instanceof PlayerEntity && ((PlayerEntity)entity).abilities.creativeMode;
            if (itemStack.isEmpty()) continue;
            if (i == 0) {
                CrossbowItem.shoot(world, entity, hand, stack, itemStack, _snowman2[i], _snowman, speed, divergence, 0.0f);
                continue;
            }
            if (i == 1) {
                CrossbowItem.shoot(world, entity, hand, stack, itemStack, _snowman2[i], _snowman, speed, divergence, -10.0f);
                continue;
            }
            if (i != 2) continue;
            CrossbowItem.shoot(world, entity, hand, stack, itemStack, _snowman2[i], _snowman, speed, divergence, 10.0f);
        }
        CrossbowItem.postShoot(world, entity, stack);
    }

    private static float[] getSoundPitches(Random random) {
        boolean bl = random.nextBoolean();
        return new float[]{1.0f, CrossbowItem.getSoundPitch(bl), CrossbowItem.getSoundPitch(!bl)};
    }

    private static float getSoundPitch(boolean flag) {
        float f = flag ? 0.63f : 0.43f;
        return 1.0f / (RANDOM.nextFloat() * 0.5f + 1.8f) + f;
    }

    private static void postShoot(World world, LivingEntity entity, ItemStack stack) {
        if (entity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
            if (!world.isClient) {
                Criteria.SHOT_CROSSBOW.trigger(serverPlayerEntity, stack);
            }
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
        }
        CrossbowItem.clearProjectiles(stack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!world.isClient) {
            int n = EnchantmentHelper.getLevel(Enchantments.QUICK_CHARGE, stack);
            SoundEvent _snowman2 = this.getQuickChargeSound(n);
            SoundEvent _snowman3 = n == 0 ? SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE : null;
            float _snowman4 = (float)(stack.getMaxUseTime() - remainingUseTicks) / (float)CrossbowItem.getPullTime(stack);
            if (_snowman4 < 0.2f) {
                this.charged = false;
                this.loaded = false;
            }
            if (_snowman4 >= 0.2f && !this.charged) {
                this.charged = true;
                world.playSound(null, user.getX(), user.getY(), user.getZ(), _snowman2, SoundCategory.PLAYERS, 0.5f, 1.0f);
            }
            if (_snowman4 >= 0.5f && _snowman3 != null && !this.loaded) {
                this.loaded = true;
                world.playSound(null, user.getX(), user.getY(), user.getZ(), _snowman3, SoundCategory.PLAYERS, 0.5f, 1.0f);
            }
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return CrossbowItem.getPullTime(stack) + 3;
    }

    public static int getPullTime(ItemStack stack) {
        int n = EnchantmentHelper.getLevel(Enchantments.QUICK_CHARGE, stack);
        return n == 0 ? 25 : 25 - 5 * n;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    private SoundEvent getQuickChargeSound(int stage) {
        switch (stage) {
            case 1: {
                return SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_1;
            }
            case 2: {
                return SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_2;
            }
            case 3: {
                return SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_3;
            }
        }
        return SoundEvents.ITEM_CROSSBOW_LOADING_START;
    }

    private static float getPullProgress(int useTicks, ItemStack stack) {
        float f = (float)useTicks / (float)CrossbowItem.getPullTime(stack);
        if (f > 1.0f) {
            f = 1.0f;
        }
        return f;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        List<ItemStack> list = CrossbowItem.getProjectiles(stack);
        if (!CrossbowItem.isCharged(stack) || list.isEmpty()) {
            return;
        }
        ItemStack _snowman2 = list.get(0);
        tooltip.add(new TranslatableText("item.minecraft.crossbow.projectile").append(" ").append(_snowman2.toHoverableText()));
        if (context.isAdvanced() && _snowman2.getItem() == Items.FIREWORK_ROCKET) {
            ArrayList arrayList = Lists.newArrayList();
            Items.FIREWORK_ROCKET.appendTooltip(_snowman2, world, arrayList, context);
            if (!arrayList.isEmpty()) {
                for (int i = 0; i < arrayList.size(); ++i) {
                    arrayList.set(i, new LiteralText("  ").append((Text)arrayList.get(i)).formatted(Formatting.GRAY));
                }
                tooltip.addAll(arrayList);
            }
        }
    }

    private static float getSpeed(ItemStack stack) {
        if (stack.getItem() == Items.CROSSBOW && CrossbowItem.hasProjectile(stack, Items.FIREWORK_ROCKET)) {
            return 1.6f;
        }
        return 3.15f;
    }

    @Override
    public int getRange() {
        return 8;
    }
}

