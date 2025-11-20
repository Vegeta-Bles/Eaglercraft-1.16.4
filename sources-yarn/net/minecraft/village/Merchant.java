package net.minecraft.village;

import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public interface Merchant {
   void setCurrentCustomer(@Nullable PlayerEntity customer);

   @Nullable
   PlayerEntity getCurrentCustomer();

   TradeOfferList getOffers();

   @Environment(EnvType.CLIENT)
   void setOffersFromServer(@Nullable TradeOfferList offers);

   void trade(TradeOffer offer);

   void onSellingItem(ItemStack stack);

   World getMerchantWorld();

   int getExperience();

   void setExperienceFromServer(int experience);

   boolean isLeveledMerchant();

   SoundEvent getYesSound();

   default boolean canRefreshTrades() {
      return false;
   }

   default void sendOffers(PlayerEntity arg, Text arg2, int i) {
      OptionalInt optionalInt = arg.openHandledScreen(new SimpleNamedScreenHandlerFactory((ix, argx, arg2x) -> new MerchantScreenHandler(ix, argx, this), arg2));
      if (optionalInt.isPresent()) {
         TradeOfferList lv = this.getOffers();
         if (!lv.isEmpty()) {
            arg.sendTradeOffers(optionalInt.getAsInt(), lv, i, this.getExperience(), this.isLeveledMerchant(), this.canRefreshTrades());
         }
      }
   }
}
