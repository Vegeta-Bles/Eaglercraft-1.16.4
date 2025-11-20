package net.minecraft.item;

import java.util.Optional;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompassItem extends Item implements Vanishable {
   private static final Logger field_24670 = LogManager.getLogger();

   public CompassItem(Item.Settings _snowman) {
      super(_snowman);
   }

   public static boolean hasLodestone(ItemStack stack) {
      CompoundTag _snowman = stack.getTag();
      return _snowman != null && (_snowman.contains("LodestoneDimension") || _snowman.contains("LodestonePos"));
   }

   @Override
   public boolean hasGlint(ItemStack stack) {
      return hasLodestone(stack) || super.hasGlint(stack);
   }

   public static Optional<RegistryKey<World>> getLodestoneDimension(CompoundTag tag) {
      return World.CODEC.parse(NbtOps.INSTANCE, tag.get("LodestoneDimension")).result();
   }

   @Override
   public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
      if (!world.isClient) {
         if (hasLodestone(stack)) {
            CompoundTag _snowman = stack.getOrCreateTag();
            if (_snowman.contains("LodestoneTracked") && !_snowman.getBoolean("LodestoneTracked")) {
               return;
            }

            Optional<RegistryKey<World>> _snowmanx = getLodestoneDimension(_snowman);
            if (_snowmanx.isPresent()
               && _snowmanx.get() == world.getRegistryKey()
               && _snowman.contains("LodestonePos")
               && !((ServerWorld)world)
                  .getPointOfInterestStorage()
                  .hasTypeAt(PointOfInterestType.LODESTONE, NbtHelper.toBlockPos(_snowman.getCompound("LodestonePos")))) {
               _snowman.remove("LodestonePos");
            }
         }
      }
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      BlockPos _snowman = context.getBlockPos();
      World _snowmanx = context.getWorld();
      if (!_snowmanx.getBlockState(_snowman).isOf(Blocks.LODESTONE)) {
         return super.useOnBlock(context);
      } else {
         _snowmanx.playSound(null, _snowman, SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.PLAYERS, 1.0F, 1.0F);
         PlayerEntity _snowmanxx = context.getPlayer();
         ItemStack _snowmanxxx = context.getStack();
         boolean _snowmanxxxx = !_snowmanxx.abilities.creativeMode && _snowmanxxx.getCount() == 1;
         if (_snowmanxxxx) {
            this.method_27315(_snowmanx.getRegistryKey(), _snowman, _snowmanxxx.getOrCreateTag());
         } else {
            ItemStack _snowmanxxxxx = new ItemStack(Items.COMPASS, 1);
            CompoundTag _snowmanxxxxxx = _snowmanxxx.hasTag() ? _snowmanxxx.getTag().copy() : new CompoundTag();
            _snowmanxxxxx.setTag(_snowmanxxxxxx);
            if (!_snowmanxx.abilities.creativeMode) {
               _snowmanxxx.decrement(1);
            }

            this.method_27315(_snowmanx.getRegistryKey(), _snowman, _snowmanxxxxxx);
            if (!_snowmanxx.inventory.insertStack(_snowmanxxxxx)) {
               _snowmanxx.dropItem(_snowmanxxxxx, false);
            }
         }

         return ActionResult.success(_snowmanx.isClient);
      }
   }

   private void method_27315(RegistryKey<World> _snowman, BlockPos _snowman, CompoundTag _snowman) {
      _snowman.put("LodestonePos", NbtHelper.fromBlockPos(_snowman));
      World.CODEC.encodeStart(NbtOps.INSTANCE, _snowman).resultOrPartial(field_24670::error).ifPresent(_snowmanxxxx -> _snowman.put("LodestoneDimension", _snowmanxxxx));
      _snowman.putBoolean("LodestoneTracked", true);
   }

   @Override
   public String getTranslationKey(ItemStack stack) {
      return hasLodestone(stack) ? "item.minecraft.lodestone_compass" : super.getTranslationKey(stack);
   }
}
