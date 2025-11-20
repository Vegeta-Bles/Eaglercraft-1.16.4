package net.minecraft.village;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.MathHelper;

public class TradeOffer {
   private final ItemStack firstBuyItem;
   private final ItemStack secondBuyItem;
   private final ItemStack sellItem;
   private int uses;
   private final int maxUses;
   private boolean rewardingPlayerExperience = true;
   private int specialPrice;
   private int demandBonus;
   private float priceMultiplier;
   private int merchantExperience = 1;

   public TradeOffer(CompoundTag _snowman) {
      this.firstBuyItem = ItemStack.fromTag(_snowman.getCompound("buy"));
      this.secondBuyItem = ItemStack.fromTag(_snowman.getCompound("buyB"));
      this.sellItem = ItemStack.fromTag(_snowman.getCompound("sell"));
      this.uses = _snowman.getInt("uses");
      if (_snowman.contains("maxUses", 99)) {
         this.maxUses = _snowman.getInt("maxUses");
      } else {
         this.maxUses = 4;
      }

      if (_snowman.contains("rewardExp", 1)) {
         this.rewardingPlayerExperience = _snowman.getBoolean("rewardExp");
      }

      if (_snowman.contains("xp", 3)) {
         this.merchantExperience = _snowman.getInt("xp");
      }

      if (_snowman.contains("priceMultiplier", 5)) {
         this.priceMultiplier = _snowman.getFloat("priceMultiplier");
      }

      this.specialPrice = _snowman.getInt("specialPrice");
      this.demandBonus = _snowman.getInt("demand");
   }

   public TradeOffer(ItemStack buyItem, ItemStack sellItem, int maxUses, int rewardedExp, float priceMultiplier) {
      this(buyItem, ItemStack.EMPTY, sellItem, maxUses, rewardedExp, priceMultiplier);
   }

   public TradeOffer(ItemStack firstBuyItem, ItemStack secondBuyItem, ItemStack sellItem, int maxUses, int rewardedExp, float priceMultiplier) {
      this(firstBuyItem, secondBuyItem, sellItem, 0, maxUses, rewardedExp, priceMultiplier);
   }

   public TradeOffer(ItemStack firstBuyItem, ItemStack secondBuyItem, ItemStack sellItem, int uses, int maxUses, int rewardedExp, float priceMultiplier) {
      this(firstBuyItem, secondBuyItem, sellItem, uses, maxUses, rewardedExp, priceMultiplier, 0);
   }

   public TradeOffer(ItemStack _snowman, ItemStack _snowman, ItemStack _snowman, int _snowman, int _snowman, int _snowman, float _snowman, int _snowman) {
      this.firstBuyItem = _snowman;
      this.secondBuyItem = _snowman;
      this.sellItem = _snowman;
      this.uses = _snowman;
      this.maxUses = _snowman;
      this.merchantExperience = _snowman;
      this.priceMultiplier = _snowman;
      this.demandBonus = _snowman;
   }

   public ItemStack getOriginalFirstBuyItem() {
      return this.firstBuyItem;
   }

   public ItemStack getAdjustedFirstBuyItem() {
      int _snowman = this.firstBuyItem.getCount();
      ItemStack _snowmanx = this.firstBuyItem.copy();
      int _snowmanxx = Math.max(0, MathHelper.floor((float)(_snowman * this.demandBonus) * this.priceMultiplier));
      _snowmanx.setCount(MathHelper.clamp(_snowman + _snowmanxx + this.specialPrice, 1, this.firstBuyItem.getItem().getMaxCount()));
      return _snowmanx;
   }

   public ItemStack getSecondBuyItem() {
      return this.secondBuyItem;
   }

   public ItemStack getMutableSellItem() {
      return this.sellItem;
   }

   public void updatePriceOnDemand() {
      this.demandBonus = this.demandBonus + this.uses - (this.maxUses - this.uses);
   }

   public ItemStack getSellItem() {
      return this.sellItem.copy();
   }

   public int getUses() {
      return this.uses;
   }

   public void resetUses() {
      this.uses = 0;
   }

   public int getMaxUses() {
      return this.maxUses;
   }

   public void use() {
      this.uses++;
   }

   public int getDemandBonus() {
      return this.demandBonus;
   }

   public void increaseSpecialPrice(int _snowman) {
      this.specialPrice += _snowman;
   }

   public void clearSpecialPrice() {
      this.specialPrice = 0;
   }

   public int getSpecialPrice() {
      return this.specialPrice;
   }

   public void setSpecialPrice(int _snowman) {
      this.specialPrice = _snowman;
   }

   public float getPriceMultiplier() {
      return this.priceMultiplier;
   }

   public int getMerchantExperience() {
      return this.merchantExperience;
   }

   public boolean isDisabled() {
      return this.uses >= this.maxUses;
   }

   public void clearUses() {
      this.uses = this.maxUses;
   }

   public boolean method_21834() {
      return this.uses > 0;
   }

   public boolean shouldRewardPlayerExperience() {
      return this.rewardingPlayerExperience;
   }

   public CompoundTag toTag() {
      CompoundTag _snowman = new CompoundTag();
      _snowman.put("buy", this.firstBuyItem.toTag(new CompoundTag()));
      _snowman.put("sell", this.sellItem.toTag(new CompoundTag()));
      _snowman.put("buyB", this.secondBuyItem.toTag(new CompoundTag()));
      _snowman.putInt("uses", this.uses);
      _snowman.putInt("maxUses", this.maxUses);
      _snowman.putBoolean("rewardExp", this.rewardingPlayerExperience);
      _snowman.putInt("xp", this.merchantExperience);
      _snowman.putFloat("priceMultiplier", this.priceMultiplier);
      _snowman.putInt("specialPrice", this.specialPrice);
      _snowman.putInt("demand", this.demandBonus);
      return _snowman;
   }

   public boolean matchesBuyItems(ItemStack first, ItemStack second) {
      return this.acceptsBuy(first, this.getAdjustedFirstBuyItem())
         && first.getCount() >= this.getAdjustedFirstBuyItem().getCount()
         && this.acceptsBuy(second, this.secondBuyItem)
         && second.getCount() >= this.secondBuyItem.getCount();
   }

   private boolean acceptsBuy(ItemStack given, ItemStack sample) {
      if (sample.isEmpty() && given.isEmpty()) {
         return true;
      } else {
         ItemStack _snowman = given.copy();
         if (_snowman.getItem().isDamageable()) {
            _snowman.setDamage(_snowman.getDamage());
         }

         return ItemStack.areItemsEqualIgnoreDamage(_snowman, sample) && (!sample.hasTag() || _snowman.hasTag() && NbtHelper.matches(sample.getTag(), _snowman.getTag(), false));
      }
   }

   public boolean depleteBuyItems(ItemStack _snowman, ItemStack _snowman) {
      if (!this.matchesBuyItems(_snowman, _snowman)) {
         return false;
      } else {
         _snowman.decrement(this.getAdjustedFirstBuyItem().getCount());
         if (!this.getSecondBuyItem().isEmpty()) {
            _snowman.decrement(this.getSecondBuyItem().getCount());
         }

         return true;
      }
   }
}
