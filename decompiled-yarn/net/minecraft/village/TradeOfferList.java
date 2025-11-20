package net.minecraft.village;

import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;

public class TradeOfferList extends ArrayList<TradeOffer> {
   public TradeOfferList() {
   }

   public TradeOfferList(CompoundTag _snowman) {
      ListTag _snowmanx = _snowman.getList("Recipes", 10);

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
         this.add(new TradeOffer(_snowmanx.getCompound(_snowmanxx)));
      }
   }

   @Nullable
   public TradeOffer getValidOffer(ItemStack firstBuyItem, ItemStack secondBuyItem, int index) {
      if (index > 0 && index < this.size()) {
         TradeOffer _snowman = this.get(index);
         return _snowman.matchesBuyItems(firstBuyItem, secondBuyItem) ? _snowman : null;
      } else {
         for (int _snowman = 0; _snowman < this.size(); _snowman++) {
            TradeOffer _snowmanx = this.get(_snowman);
            if (_snowmanx.matchesBuyItems(firstBuyItem, secondBuyItem)) {
               return _snowmanx;
            }
         }

         return null;
      }
   }

   public void toPacket(PacketByteBuf buffer) {
      buffer.writeByte((byte)(this.size() & 0xFF));

      for (int _snowman = 0; _snowman < this.size(); _snowman++) {
         TradeOffer _snowmanx = this.get(_snowman);
         buffer.writeItemStack(_snowmanx.getOriginalFirstBuyItem());
         buffer.writeItemStack(_snowmanx.getMutableSellItem());
         ItemStack _snowmanxx = _snowmanx.getSecondBuyItem();
         buffer.writeBoolean(!_snowmanxx.isEmpty());
         if (!_snowmanxx.isEmpty()) {
            buffer.writeItemStack(_snowmanxx);
         }

         buffer.writeBoolean(_snowmanx.isDisabled());
         buffer.writeInt(_snowmanx.getUses());
         buffer.writeInt(_snowmanx.getMaxUses());
         buffer.writeInt(_snowmanx.getMerchantExperience());
         buffer.writeInt(_snowmanx.getSpecialPrice());
         buffer.writeFloat(_snowmanx.getPriceMultiplier());
         buffer.writeInt(_snowmanx.getDemandBonus());
      }
   }

   public static TradeOfferList fromPacket(PacketByteBuf byteBuf) {
      TradeOfferList _snowman = new TradeOfferList();
      int _snowmanx = byteBuf.readByte() & 255;

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         ItemStack _snowmanxxx = byteBuf.readItemStack();
         ItemStack _snowmanxxxx = byteBuf.readItemStack();
         ItemStack _snowmanxxxxx = ItemStack.EMPTY;
         if (byteBuf.readBoolean()) {
            _snowmanxxxxx = byteBuf.readItemStack();
         }

         boolean _snowmanxxxxxx = byteBuf.readBoolean();
         int _snowmanxxxxxxx = byteBuf.readInt();
         int _snowmanxxxxxxxx = byteBuf.readInt();
         int _snowmanxxxxxxxxx = byteBuf.readInt();
         int _snowmanxxxxxxxxxx = byteBuf.readInt();
         float _snowmanxxxxxxxxxxx = byteBuf.readFloat();
         int _snowmanxxxxxxxxxxxx = byteBuf.readInt();
         TradeOffer _snowmanxxxxxxxxxxxxx = new TradeOffer(_snowmanxxx, _snowmanxxxxx, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
         if (_snowmanxxxxxx) {
            _snowmanxxxxxxxxxxxxx.clearUses();
         }

         _snowmanxxxxxxxxxxxxx.setSpecialPrice(_snowmanxxxxxxxxxx);
         _snowman.add(_snowmanxxxxxxxxxxxxx);
      }

      return _snowman;
   }

   public CompoundTag toTag() {
      CompoundTag _snowman = new CompoundTag();
      ListTag _snowmanx = new ListTag();

      for (int _snowmanxx = 0; _snowmanxx < this.size(); _snowmanxx++) {
         TradeOffer _snowmanxxx = this.get(_snowmanxx);
         _snowmanx.add(_snowmanxxx.toTag());
      }

      _snowman.put("Recipes", _snowmanx);
      return _snowman;
   }
}
