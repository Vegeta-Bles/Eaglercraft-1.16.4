package net.minecraft.item;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.StringUtils;

public class SkullItem extends WallStandingBlockItem {
   public SkullItem(Block _snowman, Block _snowman, Item.Settings _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   public Text getName(ItemStack stack) {
      if (stack.getItem() == Items.PLAYER_HEAD && stack.hasTag()) {
         String _snowman = null;
         CompoundTag _snowmanx = stack.getTag();
         if (_snowmanx.contains("SkullOwner", 8)) {
            _snowman = _snowmanx.getString("SkullOwner");
         } else if (_snowmanx.contains("SkullOwner", 10)) {
            CompoundTag _snowmanxx = _snowmanx.getCompound("SkullOwner");
            if (_snowmanxx.contains("Name", 8)) {
               _snowman = _snowmanxx.getString("Name");
            }
         }

         if (_snowman != null) {
            return new TranslatableText(this.getTranslationKey() + ".named", _snowman);
         }
      }

      return super.getName(stack);
   }

   @Override
   public boolean postProcessTag(CompoundTag tag) {
      super.postProcessTag(tag);
      if (tag.contains("SkullOwner", 8) && !StringUtils.isBlank(tag.getString("SkullOwner"))) {
         GameProfile _snowman = new GameProfile(null, tag.getString("SkullOwner"));
         _snowman = SkullBlockEntity.loadProperties(_snowman);
         tag.put("SkullOwner", NbtHelper.fromGameProfile(new CompoundTag(), _snowman));
         return true;
      } else {
         return false;
      }
   }
}
