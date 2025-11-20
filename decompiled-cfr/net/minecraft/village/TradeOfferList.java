/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.village;

import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.village.TradeOffer;

public class TradeOfferList
extends ArrayList<TradeOffer> {
    public TradeOfferList() {
    }

    public TradeOfferList(CompoundTag compoundTag) {
        ListTag listTag = compoundTag.getList("Recipes", 10);
        for (int i = 0; i < listTag.size(); ++i) {
            this.add(new TradeOffer(listTag.getCompound(i)));
        }
    }

    @Nullable
    public TradeOffer getValidOffer(ItemStack firstBuyItem, ItemStack secondBuyItem, int index) {
        if (index > 0 && index < this.size()) {
            TradeOffer tradeOffer = (TradeOffer)this.get(index);
            if (tradeOffer.matchesBuyItems(firstBuyItem, secondBuyItem)) {
                return tradeOffer;
            }
            return null;
        }
        for (int i = 0; i < this.size(); ++i) {
            TradeOffer tradeOffer = (TradeOffer)this.get(i);
            if (!tradeOffer.matchesBuyItems(firstBuyItem, secondBuyItem)) continue;
            return tradeOffer;
        }
        return null;
    }

    public void toPacket(PacketByteBuf buffer) {
        buffer.writeByte((byte)(this.size() & 0xFF));
        for (int i = 0; i < this.size(); ++i) {
            TradeOffer tradeOffer = (TradeOffer)this.get(i);
            buffer.writeItemStack(tradeOffer.getOriginalFirstBuyItem());
            buffer.writeItemStack(tradeOffer.getMutableSellItem());
            ItemStack _snowman2 = tradeOffer.getSecondBuyItem();
            buffer.writeBoolean(!_snowman2.isEmpty());
            if (!_snowman2.isEmpty()) {
                buffer.writeItemStack(_snowman2);
            }
            buffer.writeBoolean(tradeOffer.isDisabled());
            buffer.writeInt(tradeOffer.getUses());
            buffer.writeInt(tradeOffer.getMaxUses());
            buffer.writeInt(tradeOffer.getMerchantExperience());
            buffer.writeInt(tradeOffer.getSpecialPrice());
            buffer.writeFloat(tradeOffer.getPriceMultiplier());
            buffer.writeInt(tradeOffer.getDemandBonus());
        }
    }

    public static TradeOfferList fromPacket(PacketByteBuf byteBuf) {
        TradeOfferList tradeOfferList = new TradeOfferList();
        int _snowman2 = byteBuf.readByte() & 0xFF;
        for (int i = 0; i < _snowman2; ++i) {
            ItemStack itemStack = byteBuf.readItemStack();
            _snowman = byteBuf.readItemStack();
            _snowman = ItemStack.EMPTY;
            if (byteBuf.readBoolean()) {
                _snowman = byteBuf.readItemStack();
            }
            boolean _snowman3 = byteBuf.readBoolean();
            int _snowman4 = byteBuf.readInt();
            int _snowman5 = byteBuf.readInt();
            int _snowman6 = byteBuf.readInt();
            int _snowman7 = byteBuf.readInt();
            float _snowman8 = byteBuf.readFloat();
            int _snowman9 = byteBuf.readInt();
            TradeOffer _snowman10 = new TradeOffer(itemStack, _snowman, _snowman, _snowman4, _snowman5, _snowman6, _snowman8, _snowman9);
            if (_snowman3) {
                _snowman10.clearUses();
            }
            _snowman10.setSpecialPrice(_snowman7);
            tradeOfferList.add(_snowman10);
        }
        return tradeOfferList;
    }

    public CompoundTag toTag() {
        CompoundTag compoundTag = new CompoundTag();
        ListTag _snowman2 = new ListTag();
        for (int i = 0; i < this.size(); ++i) {
            TradeOffer tradeOffer = (TradeOffer)this.get(i);
            _snowman2.add(tradeOffer.toTag());
        }
        compoundTag.put("Recipes", _snowman2);
        return compoundTag;
    }
}

