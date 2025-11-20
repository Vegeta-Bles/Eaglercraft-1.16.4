package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public abstract class AbstractFireballEntity extends ExplosiveProjectileEntity implements FlyingItemEntity {
   private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(AbstractFireballEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

   public AbstractFireballEntity(EntityType<? extends AbstractFireballEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public AbstractFireballEntity(EntityType<? extends AbstractFireballEntity> _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, World _snowman) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public AbstractFireballEntity(EntityType<? extends AbstractFireballEntity> _snowman, LivingEntity _snowman, double _snowman, double _snowman, double _snowman, World _snowman) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public void setItem(ItemStack stack) {
      if (stack.getItem() != Items.FIRE_CHARGE || stack.hasTag()) {
         this.getDataTracker().set(ITEM, Util.make(stack.copy(), _snowman -> _snowman.setCount(1)));
      }
   }

   protected ItemStack getItem() {
      return this.getDataTracker().get(ITEM);
   }

   @Override
   public ItemStack getStack() {
      ItemStack _snowman = this.getItem();
      return _snowman.isEmpty() ? new ItemStack(Items.FIRE_CHARGE) : _snowman;
   }

   @Override
   protected void initDataTracker() {
      this.getDataTracker().startTracking(ITEM, ItemStack.EMPTY);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      ItemStack _snowman = this.getItem();
      if (!_snowman.isEmpty()) {
         tag.put("Item", _snowman.toTag(new CompoundTag()));
      }
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      ItemStack _snowman = ItemStack.fromTag(tag.getCompound("Item"));
      this.setItem(_snowman);
   }
}
