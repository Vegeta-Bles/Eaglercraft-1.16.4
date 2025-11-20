package net.minecraft.block.entity;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;

public abstract class LootableContainerBlockEntity extends LockableContainerBlockEntity {
   @Nullable
   protected Identifier lootTableId;
   protected long lootTableSeed;

   protected LootableContainerBlockEntity(BlockEntityType<?> _snowman) {
      super(_snowman);
   }

   public static void setLootTable(BlockView world, Random random, BlockPos pos, Identifier id) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof LootableContainerBlockEntity) {
         ((LootableContainerBlockEntity)_snowman).setLootTable(id, random.nextLong());
      }
   }

   protected boolean deserializeLootTable(CompoundTag _snowman) {
      if (_snowman.contains("LootTable", 8)) {
         this.lootTableId = new Identifier(_snowman.getString("LootTable"));
         this.lootTableSeed = _snowman.getLong("LootTableSeed");
         return true;
      } else {
         return false;
      }
   }

   protected boolean serializeLootTable(CompoundTag _snowman) {
      if (this.lootTableId == null) {
         return false;
      } else {
         _snowman.putString("LootTable", this.lootTableId.toString());
         if (this.lootTableSeed != 0L) {
            _snowman.putLong("LootTableSeed", this.lootTableSeed);
         }

         return true;
      }
   }

   public void checkLootInteraction(@Nullable PlayerEntity player) {
      if (this.lootTableId != null && this.world.getServer() != null) {
         LootTable _snowman = this.world.getServer().getLootManager().getTable(this.lootTableId);
         if (player instanceof ServerPlayerEntity) {
            Criteria.PLAYER_GENERATES_CONTAINER_LOOT.test((ServerPlayerEntity)player, this.lootTableId);
         }

         this.lootTableId = null;
         LootContext.Builder _snowmanx = new LootContext.Builder((ServerWorld)this.world)
            .parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(this.pos))
            .random(this.lootTableSeed);
         if (player != null) {
            _snowmanx.luck(player.getLuck()).parameter(LootContextParameters.THIS_ENTITY, player);
         }

         _snowman.supplyInventory(this, _snowmanx.build(LootContextTypes.CHEST));
      }
   }

   public void setLootTable(Identifier id, long seed) {
      this.lootTableId = id;
      this.lootTableSeed = seed;
   }

   @Override
   public boolean isEmpty() {
      this.checkLootInteraction(null);
      return this.getInvStackList().stream().allMatch(ItemStack::isEmpty);
   }

   @Override
   public ItemStack getStack(int slot) {
      this.checkLootInteraction(null);
      return this.getInvStackList().get(slot);
   }

   @Override
   public ItemStack removeStack(int slot, int amount) {
      this.checkLootInteraction(null);
      ItemStack _snowman = Inventories.splitStack(this.getInvStackList(), slot, amount);
      if (!_snowman.isEmpty()) {
         this.markDirty();
      }

      return _snowman;
   }

   @Override
   public ItemStack removeStack(int slot) {
      this.checkLootInteraction(null);
      return Inventories.removeStack(this.getInvStackList(), slot);
   }

   @Override
   public void setStack(int slot, ItemStack stack) {
      this.checkLootInteraction(null);
      this.getInvStackList().set(slot, stack);
      if (stack.getCount() > this.getMaxCountPerStack()) {
         stack.setCount(this.getMaxCountPerStack());
      }

      this.markDirty();
   }

   @Override
   public boolean canPlayerUse(PlayerEntity player) {
      return this.world.getBlockEntity(this.pos) != this
         ? false
         : !(player.squaredDistanceTo((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) > 64.0);
   }

   @Override
   public void clear() {
      this.getInvStackList().clear();
   }

   protected abstract DefaultedList<ItemStack> getInvStackList();

   protected abstract void setInvStackList(DefaultedList<ItemStack> list);

   @Override
   public boolean checkUnlocked(PlayerEntity player) {
      return super.checkUnlocked(player) && (this.lootTableId == null || !player.isSpectator());
   }

   @Nullable
   @Override
   public ScreenHandler createMenu(int _snowman, PlayerInventory _snowman, PlayerEntity _snowman) {
      if (this.checkUnlocked(_snowman)) {
         this.checkLootInteraction(_snowman.player);
         return this.createScreenHandler(_snowman, _snowman);
      } else {
         return null;
      }
   }
}
