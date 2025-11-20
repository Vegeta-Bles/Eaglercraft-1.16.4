package net.minecraft.command.argument;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;

public class ItemStackArgument implements Predicate<ItemStack> {
   private static final Dynamic2CommandExceptionType OVERSTACKED_EXCEPTION = new Dynamic2CommandExceptionType(
      (_snowman, _snowmanx) -> new TranslatableText("arguments.item.overstacked", _snowman, _snowmanx)
   );
   private final Item item;
   @Nullable
   private final CompoundTag tag;

   public ItemStackArgument(Item _snowman, @Nullable CompoundTag tag) {
      this.item = _snowman;
      this.tag = tag;
   }

   public Item getItem() {
      return this.item;
   }

   public boolean test(ItemStack _snowman) {
      return _snowman.getItem() == this.item && NbtHelper.matches(this.tag, _snowman.getTag(), true);
   }

   public ItemStack createStack(int amount, boolean checkOverstack) throws CommandSyntaxException {
      ItemStack _snowman = new ItemStack(this.item, amount);
      if (this.tag != null) {
         _snowman.setTag(this.tag);
      }

      if (checkOverstack && amount > _snowman.getMaxCount()) {
         throw OVERSTACKED_EXCEPTION.create(Registry.ITEM.getId(this.item), _snowman.getMaxCount());
      } else {
         return _snowman;
      }
   }

   public String asString() {
      StringBuilder _snowman = new StringBuilder(Registry.ITEM.getRawId(this.item));
      if (this.tag != null) {
         _snowman.append(this.tag);
      }

      return _snowman.toString();
   }
}
