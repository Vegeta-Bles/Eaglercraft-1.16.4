package net.minecraft.entity.vehicle;

import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public abstract class StorageMinecartEntity extends AbstractMinecartEntity implements Inventory, NamedScreenHandlerFactory {
   private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(36, ItemStack.EMPTY);
   private boolean field_7733 = true;
   @Nullable
   private Identifier lootTableId;
   private long lootSeed;

   protected StorageMinecartEntity(EntityType<?> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   protected StorageMinecartEntity(EntityType<?> type, double x, double y, double z, World world) {
      super(type, world, x, y, z);
   }

   @Override
   public void dropItems(DamageSource damageSource) {
      super.dropItems(damageSource);
      if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
         ItemScatterer.spawn(this.world, this, this);
         if (!this.world.isClient) {
            Entity _snowman = damageSource.getSource();
            if (_snowman != null && _snowman.getType() == EntityType.PLAYER) {
               PiglinBrain.onGuardedBlockInteracted((PlayerEntity)_snowman, true);
            }
         }
      }
   }

   @Override
   public boolean isEmpty() {
      for (ItemStack _snowman : this.inventory) {
         if (!_snowman.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack getStack(int slot) {
      this.generateLoot(null);
      return this.inventory.get(slot);
   }

   @Override
   public ItemStack removeStack(int slot, int amount) {
      this.generateLoot(null);
      return Inventories.splitStack(this.inventory, slot, amount);
   }

   @Override
   public ItemStack removeStack(int slot) {
      this.generateLoot(null);
      ItemStack _snowman = this.inventory.get(slot);
      if (_snowman.isEmpty()) {
         return ItemStack.EMPTY;
      } else {
         this.inventory.set(slot, ItemStack.EMPTY);
         return _snowman;
      }
   }

   @Override
   public void setStack(int slot, ItemStack stack) {
      this.generateLoot(null);
      this.inventory.set(slot, stack);
      if (!stack.isEmpty() && stack.getCount() > this.getMaxCountPerStack()) {
         stack.setCount(this.getMaxCountPerStack());
      }
   }

   @Override
   public boolean equip(int slot, ItemStack item) {
      if (slot >= 0 && slot < this.size()) {
         this.setStack(slot, item);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void markDirty() {
   }

   @Override
   public boolean canPlayerUse(PlayerEntity player) {
      return this.removed ? false : !(player.squaredDistanceTo(this) > 64.0);
   }

   @Nullable
   @Override
   public Entity moveToWorld(ServerWorld destination) {
      this.field_7733 = false;
      return super.moveToWorld(destination);
   }

   @Override
   public void remove() {
      if (!this.world.isClient && this.field_7733) {
         ItemScatterer.spawn(this.world, this, this);
      }

      super.remove();
   }

   @Override
   protected void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      if (this.lootTableId != null) {
         tag.putString("LootTable", this.lootTableId.toString());
         if (this.lootSeed != 0L) {
            tag.putLong("LootTableSeed", this.lootSeed);
         }
      } else {
         Inventories.toTag(tag, this.inventory);
      }
   }

   @Override
   protected void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
      if (tag.contains("LootTable", 8)) {
         this.lootTableId = new Identifier(tag.getString("LootTable"));
         this.lootSeed = tag.getLong("LootTableSeed");
      } else {
         Inventories.fromTag(tag, this.inventory);
      }
   }

   @Override
   public ActionResult interact(PlayerEntity player, Hand hand) {
      player.openHandledScreen(this);
      if (!player.world.isClient) {
         PiglinBrain.onGuardedBlockInteracted(player, true);
         return ActionResult.CONSUME;
      } else {
         return ActionResult.SUCCESS;
      }
   }

   @Override
   protected void applySlowdown() {
      float _snowman = 0.98F;
      if (this.lootTableId == null) {
         int _snowmanx = 15 - ScreenHandler.calculateComparatorOutput(this);
         _snowman += (float)_snowmanx * 0.001F;
      }

      this.setVelocity(this.getVelocity().multiply((double)_snowman, 0.0, (double)_snowman));
   }

   public void generateLoot(@Nullable PlayerEntity player) {
      if (this.lootTableId != null && this.world.getServer() != null) {
         LootTable _snowman = this.world.getServer().getLootManager().getTable(this.lootTableId);
         if (player instanceof ServerPlayerEntity) {
            Criteria.PLAYER_GENERATES_CONTAINER_LOOT.test((ServerPlayerEntity)player, this.lootTableId);
         }

         this.lootTableId = null;
         LootContext.Builder _snowmanx = new LootContext.Builder((ServerWorld)this.world).parameter(LootContextParameters.ORIGIN, this.getPos()).random(this.lootSeed);
         if (player != null) {
            _snowmanx.luck(player.getLuck()).parameter(LootContextParameters.THIS_ENTITY, player);
         }

         _snowman.supplyInventory(this, _snowmanx.build(LootContextTypes.CHEST));
      }
   }

   @Override
   public void clear() {
      this.generateLoot(null);
      this.inventory.clear();
   }

   public void setLootTable(Identifier id, long lootSeed) {
      this.lootTableId = id;
      this.lootSeed = lootSeed;
   }

   @Nullable
   @Override
   public ScreenHandler createMenu(int _snowman, PlayerInventory _snowman, PlayerEntity _snowman) {
      if (this.lootTableId != null && _snowman.isSpectator()) {
         return null;
      } else {
         this.generateLoot(_snowman.player);
         return this.getScreenHandler(_snowman, _snowman);
      }
   }

   protected abstract ScreenHandler getScreenHandler(int syncId, PlayerInventory playerInventory);
}
