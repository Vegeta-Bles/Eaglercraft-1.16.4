package net.minecraft.block;

import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

public class PlayerSkullBlock extends SkullBlock {
   protected PlayerSkullBlock(AbstractBlock.Settings arg) {
      super(SkullBlock.Type.PLAYER, arg);
   }

   @Override
   public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
      super.onPlaced(world, pos, state, placer, itemStack);
      BlockEntity lv = world.getBlockEntity(pos);
      if (lv instanceof SkullBlockEntity) {
         SkullBlockEntity lv2 = (SkullBlockEntity)lv;
         GameProfile gameProfile = null;
         if (itemStack.hasTag()) {
            CompoundTag lv3 = itemStack.getTag();
            if (lv3.contains("SkullOwner", 10)) {
               gameProfile = NbtHelper.toGameProfile(lv3.getCompound("SkullOwner"));
            } else if (lv3.contains("SkullOwner", 8) && !StringUtils.isBlank(lv3.getString("SkullOwner"))) {
               gameProfile = new GameProfile(null, lv3.getString("SkullOwner"));
            }
         }

         lv2.setOwnerAndType(gameProfile);
      }
   }
}
