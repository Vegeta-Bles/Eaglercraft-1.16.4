/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.client.item;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ModelPredicateProviderRegistry {
    private static final Map<Identifier, ModelPredicateProvider> GLOBAL = Maps.newHashMap();
    private static final Identifier DAMAGED_ID = new Identifier("damaged");
    private static final Identifier DAMAGE_ID = new Identifier("damage");
    private static final ModelPredicateProvider DAMAGED_PROVIDER = (itemStack, clientWorld, livingEntity) -> itemStack.isDamaged() ? 1.0f : 0.0f;
    private static final ModelPredicateProvider DAMAGE_PROVIDER = (itemStack, clientWorld, livingEntity) -> MathHelper.clamp((float)itemStack.getDamage() / (float)itemStack.getMaxDamage(), 0.0f, 1.0f);
    private static final Map<Item, Map<Identifier, ModelPredicateProvider>> ITEM_SPECIFIC = Maps.newHashMap();

    private static ModelPredicateProvider register(Identifier id, ModelPredicateProvider provider) {
        GLOBAL.put(id, provider);
        return provider;
    }

    private static void register(Item item2, Identifier id, ModelPredicateProvider provider) {
        ITEM_SPECIFIC.computeIfAbsent(item2, item -> Maps.newHashMap()).put(id, provider);
    }

    @Nullable
    public static ModelPredicateProvider get(Item item, Identifier id) {
        ModelPredicateProvider modelPredicateProvider;
        if (item.getMaxDamage() > 0) {
            if (DAMAGE_ID.equals(id)) {
                return DAMAGE_PROVIDER;
            }
            if (DAMAGED_ID.equals(id)) {
                return DAMAGED_PROVIDER;
            }
        }
        if ((modelPredicateProvider = GLOBAL.get(id)) != null) {
            return modelPredicateProvider;
        }
        Map<Identifier, ModelPredicateProvider> _snowman2 = ITEM_SPECIFIC.get(item);
        if (_snowman2 == null) {
            return null;
        }
        return _snowman2.get(id);
    }

    static {
        ModelPredicateProviderRegistry.register(new Identifier("lefthanded"), (itemStack, clientWorld, livingEntity) -> livingEntity == null || livingEntity.getMainArm() == Arm.RIGHT ? 0.0f : 1.0f);
        ModelPredicateProviderRegistry.register(new Identifier("cooldown"), (itemStack, clientWorld, livingEntity) -> livingEntity instanceof PlayerEntity ? ((PlayerEntity)livingEntity).getItemCooldownManager().getCooldownProgress(itemStack.getItem(), 0.0f) : 0.0f);
        ModelPredicateProviderRegistry.register(new Identifier("custom_model_data"), (itemStack, clientWorld, livingEntity) -> itemStack.hasTag() ? (float)itemStack.getTag().getInt("CustomModelData") : 0.0f);
        ModelPredicateProviderRegistry.register(Items.BOW, new Identifier("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0f;
            }
            if (livingEntity.getActiveItem() != itemStack) {
                return 0.0f;
            }
            return (float)(itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0f;
        });
        ModelPredicateProviderRegistry.register(Items.BOW, new Identifier("pulling"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0f : 0.0f);
        ModelPredicateProviderRegistry.register(Items.CLOCK, new Identifier("time"), new ModelPredicateProvider(){
            private double time;
            private double step;
            private long lastTick;

            @Override
            public float call(ItemStack itemStack, @Nullable ClientWorld clientWorld2, @Nullable LivingEntity livingEntity) {
                double _snowman2;
                Entity entity;
                Entity entity2 = entity = livingEntity != null ? livingEntity : itemStack.getHolder();
                if (entity == null) {
                    return 0.0f;
                }
                if (clientWorld2 == null && entity.world instanceof ClientWorld) {
                    clientWorld2 = (ClientWorld)entity.world;
                }
                if (clientWorld2 == null) {
                    return 0.0f;
                }
                if (clientWorld2.getDimension().isNatural()) {
                    double d = clientWorld2.getSkyAngle(1.0f);
                } else {
                    _snowman2 = Math.random();
                }
                _snowman2 = this.getTime(clientWorld2, _snowman2);
                return (float)_snowman2;
            }

            private double getTime(World world, double skyAngle) {
                if (world.getTime() != this.lastTick) {
                    this.lastTick = world.getTime();
                    double d = skyAngle - this.time;
                    d = MathHelper.floorMod(d + 0.5, 1.0) - 0.5;
                    this.step += d * 0.1;
                    this.step *= 0.9;
                    this.time = MathHelper.floorMod(this.time + this.step, 1.0);
                }
                return this.time;
            }
        });
        ModelPredicateProviderRegistry.register(Items.COMPASS, new Identifier("angle"), new ModelPredicateProvider(){
            private final AngleInterpolator value = new AngleInterpolator();
            private final AngleInterpolator speed = new AngleInterpolator();

            @Override
            public float call(ItemStack itemStack2, @Nullable ClientWorld clientWorld2, @Nullable LivingEntity livingEntity2) {
                double d;
                LivingEntity livingEntity2;
                ItemStack itemStack2;
                Entity entity = _snowman = livingEntity2 != null ? livingEntity2 : itemStack2.getHolder();
                if (_snowman == null) {
                    return 0.0f;
                }
                if (clientWorld2 == null && _snowman.world instanceof ClientWorld) {
                    ClientWorld clientWorld2 = (ClientWorld)_snowman.world;
                }
                BlockPos _snowman2 = CompassItem.hasLodestone(itemStack2) ? this.getLodestonePos(clientWorld2, itemStack2.getOrCreateTag()) : this.getSpawnPos(clientWorld2);
                long _snowman3 = clientWorld2.getTime();
                if (_snowman2 == null || _snowman.getPos().squaredDistanceTo((double)_snowman2.getX() + 0.5, _snowman.getPos().getY(), (double)_snowman2.getZ() + 0.5) < (double)1.0E-5f) {
                    if (this.speed.shouldUpdate(_snowman3)) {
                        this.speed.update(_snowman3, Math.random());
                    }
                    double d2 = this.speed.value + (double)((float)itemStack2.hashCode() / 2.1474836E9f);
                    return MathHelper.floorMod((float)d2, 1.0f);
                }
                boolean _snowman4 = livingEntity2 instanceof PlayerEntity && ((PlayerEntity)livingEntity2).isMainPlayer();
                double _snowman5 = 0.0;
                if (_snowman4) {
                    _snowman5 = livingEntity2.yaw;
                } else if (_snowman instanceof ItemFrameEntity) {
                    _snowman5 = this.getItemFrameAngleOffset((ItemFrameEntity)_snowman);
                } else if (_snowman instanceof ItemEntity) {
                    _snowman5 = 180.0f - ((ItemEntity)_snowman).method_27314(0.5f) / ((float)Math.PI * 2) * 360.0f;
                } else if (livingEntity2 != null) {
                    _snowman5 = livingEntity2.bodyYaw;
                }
                _snowman5 = MathHelper.floorMod(_snowman5 / 360.0, 1.0);
                double _snowman6 = this.getAngleToPos(Vec3d.ofCenter(_snowman2), _snowman) / 6.2831854820251465;
                if (_snowman4) {
                    if (this.value.shouldUpdate(_snowman3)) {
                        this.value.update(_snowman3, 0.5 - (_snowman5 - 0.25));
                    }
                    d = _snowman6 + this.value.value;
                } else {
                    d = 0.5 - (_snowman5 - 0.25 - _snowman6);
                }
                return MathHelper.floorMod((float)d, 1.0f);
            }

            @Nullable
            private BlockPos getSpawnPos(ClientWorld world) {
                return world.getDimension().isNatural() ? world.getSpawnPos() : null;
            }

            @Nullable
            private BlockPos getLodestonePos(World world, CompoundTag tag) {
                boolean bl = tag.contains("LodestonePos");
                _snowman = tag.contains("LodestoneDimension");
                if (bl && _snowman && (_snowman = CompassItem.getLodestoneDimension(tag)).isPresent() && world.getRegistryKey() == _snowman.get()) {
                    return NbtHelper.toBlockPos(tag.getCompound("LodestonePos"));
                }
                return null;
            }

            private double getItemFrameAngleOffset(ItemFrameEntity itemFrame) {
                Direction direction = itemFrame.getHorizontalFacing();
                int _snowman2 = direction.getAxis().isVertical() ? 90 * direction.getDirection().offset() : 0;
                return MathHelper.wrapDegrees(180 + direction.getHorizontal() * 90 + itemFrame.getRotation() * 45 + _snowman2);
            }

            private double getAngleToPos(Vec3d pos, Entity entity) {
                return Math.atan2(pos.getZ() - entity.getZ(), pos.getX() - entity.getX());
            }
        });
        ModelPredicateProviderRegistry.register(Items.CROSSBOW, new Identifier("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0f;
            }
            if (CrossbowItem.isCharged(itemStack)) {
                return 0.0f;
            }
            return (float)(itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / (float)CrossbowItem.getPullTime(itemStack);
        });
        ModelPredicateProviderRegistry.register(Items.CROSSBOW, new Identifier("pulling"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack && !CrossbowItem.isCharged(itemStack) ? 1.0f : 0.0f);
        ModelPredicateProviderRegistry.register(Items.CROSSBOW, new Identifier("charged"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && CrossbowItem.isCharged(itemStack) ? 1.0f : 0.0f);
        ModelPredicateProviderRegistry.register(Items.CROSSBOW, new Identifier("firework"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && CrossbowItem.isCharged(itemStack) && CrossbowItem.hasProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0f : 0.0f);
        ModelPredicateProviderRegistry.register(Items.ELYTRA, new Identifier("broken"), (itemStack, clientWorld, livingEntity) -> ElytraItem.isUsable(itemStack) ? 0.0f : 1.0f);
        ModelPredicateProviderRegistry.register(Items.FISHING_ROD, new Identifier("cast"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0f;
            }
            boolean bl = livingEntity.getMainHandStack() == itemStack;
            boolean bl2 = _snowman = livingEntity.getOffHandStack() == itemStack;
            if (livingEntity.getMainHandStack().getItem() instanceof FishingRodItem) {
                _snowman = false;
            }
            return (bl || _snowman) && livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).fishHook != null ? 1.0f : 0.0f;
        });
        ModelPredicateProviderRegistry.register(Items.SHIELD, new Identifier("blocking"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0f : 0.0f);
        ModelPredicateProviderRegistry.register(Items.TRIDENT, new Identifier("throwing"), (itemStack, clientWorld, livingEntity) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0f : 0.0f);
    }

    static class AngleInterpolator {
        private double value;
        private double speed;
        private long lastUpdateTime;

        private AngleInterpolator() {
        }

        private boolean shouldUpdate(long time) {
            return this.lastUpdateTime != time;
        }

        private void update(long time, double d) {
            this.lastUpdateTime = time;
            _snowman = d - this.value;
            _snowman = MathHelper.floorMod(_snowman + 0.5, 1.0) - 0.5;
            this.speed += _snowman * 0.1;
            this.speed *= 0.8;
            this.value = MathHelper.floorMod(this.value + this.speed, 1.0);
        }
    }
}

