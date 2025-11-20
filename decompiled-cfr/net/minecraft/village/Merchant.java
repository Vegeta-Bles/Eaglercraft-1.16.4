/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.village;

import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.World;

public interface Merchant {
    public void setCurrentCustomer(@Nullable PlayerEntity var1);

    @Nullable
    public PlayerEntity getCurrentCustomer();

    public TradeOfferList getOffers();

    public void setOffersFromServer(@Nullable TradeOfferList var1);

    public void trade(TradeOffer var1);

    public void onSellingItem(ItemStack var1);

    public World getMerchantWorld();

    public int getExperience();

    public void setExperienceFromServer(int var1);

    public boolean isLeveledMerchant();

    public SoundEvent getYesSound();

    default public boolean canRefreshTrades() {
        return false;
    }

    default public void sendOffers(PlayerEntity playerEntity2, Text text, int n2) {
        OptionalInt optionalInt = playerEntity2.openHandledScreen(new SimpleNamedScreenHandlerFactory((n, playerInventory, playerEntity) -> new MerchantScreenHandler(n, playerInventory, this), text));
        if (optionalInt.isPresent() && !(_snowman = this.getOffers()).isEmpty()) {
            playerEntity2.sendTradeOffers(optionalInt.getAsInt(), _snowman, n2, this.getExperience(), this.isLeveledMerchant(), this.canRefreshTrades());
        }
    }
}

