package net.minecraft.village;

import java.util.OptionalInt;
import javax.annotation.Nullable;
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

   default void sendOffers(PlayerEntity _snowman, Text _snowman, int _snowman) {
      OptionalInt _snowmanxxx = _snowman.openHandledScreen(new SimpleNamedScreenHandlerFactory((_snowmanxxxxx, _snowmanxxxx, _snowmanxxxx) -> new MerchantScreenHandler(_snowmanxxxxx, _snowmanxxxx, this), _snowman));
      if (_snowmanxxx.isPresent()) {
         TradeOfferList _snowmanxxxx = this.getOffers();
         if (!_snowmanxxxx.isEmpty()) {
            _snowman.sendTradeOffers(_snowmanxxx.getAsInt(), _snowmanxxxx, _snowman, this.getExperience(), this.isLeveledMerchant(), this.canRefreshTrades());
         }
      }
   }
}
