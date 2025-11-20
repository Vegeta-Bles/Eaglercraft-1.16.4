package net.minecraft.recipe;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

public class RecipeFinder {
   public final Int2IntMap idToAmountMap = new Int2IntOpenHashMap();

   public RecipeFinder() {
   }

   public void addNormalItem(ItemStack stack) {
      if (!stack.isDamaged() && !stack.hasEnchantments() && !stack.hasCustomName()) {
         this.addItem(stack);
      }
   }

   public void addItem(ItemStack arg) {
      this.method_20478(arg, 64);
   }

   public void method_20478(ItemStack arg, int i) {
      if (!arg.isEmpty()) {
         int j = getItemId(arg);
         int k = Math.min(i, arg.getCount());
         this.addItem(j, k);
      }
   }

   public static int getItemId(ItemStack arg) {
      return Registry.ITEM.getRawId(arg.getItem());
   }

   private boolean contains(int i) {
      return this.idToAmountMap.get(i) > 0;
   }

   private int take(int id, int amount) {
      int k = this.idToAmountMap.get(id);
      if (k >= amount) {
         this.idToAmountMap.put(id, k - amount);
         return id;
      } else {
         return 0;
      }
   }

   private void addItem(int id, int amount) {
      this.idToAmountMap.put(id, this.idToAmountMap.get(id) + amount);
   }

   public boolean findRecipe(Recipe<?> arg, @Nullable IntList outMatchingInputIds) {
      return this.findRecipe(arg, outMatchingInputIds, 1);
   }

   public boolean findRecipe(Recipe<?> arg, @Nullable IntList outMatchingInputIds, int amount) {
      return new RecipeFinder.Filter(arg).find(amount, outMatchingInputIds);
   }

   public int countRecipeCrafts(Recipe<?> arg, @Nullable IntList outMatchingInputIds) {
      return this.countRecipeCrafts(arg, Integer.MAX_VALUE, outMatchingInputIds);
   }

   public int countRecipeCrafts(Recipe<?> arg, int limit, @Nullable IntList outMatchingInputIds) {
      return new RecipeFinder.Filter(arg).countCrafts(limit, outMatchingInputIds);
   }

   public static ItemStack getStackFromId(int i) {
      return i == 0 ? ItemStack.EMPTY : new ItemStack(Item.byRawId(i));
   }

   public void clear() {
      this.idToAmountMap.clear();
   }

   class Filter {
      private final Recipe<?> recipe;
      private final List<Ingredient> ingredients = Lists.newArrayList();
      private final int ingredientCount;
      private final int[] field_7551;
      private final int field_7553;
      private final BitSet field_7558;
      private final IntList field_7557 = new IntArrayList();

      public Filter(Recipe<?> arg2) {
         this.recipe = arg2;
         this.ingredients.addAll(arg2.getPreviewInputs());
         this.ingredients.removeIf(Ingredient::isEmpty);
         this.ingredientCount = this.ingredients.size();
         this.field_7551 = this.method_7422();
         this.field_7553 = this.field_7551.length;
         this.field_7558 = new BitSet(this.ingredientCount + this.field_7553 + this.ingredientCount + this.ingredientCount * this.field_7553);

         for (int i = 0; i < this.ingredients.size(); i++) {
            IntList intList = this.ingredients.get(i).getIds();

            for (int j = 0; j < this.field_7553; j++) {
               if (intList.contains(this.field_7551[j])) {
                  this.field_7558.set(this.method_7420(true, j, i));
               }
            }
         }
      }

      public boolean find(int amount, @Nullable IntList outMatchingInputIds) {
         if (amount <= 0) {
            return true;
         } else {
            int j;
            for (j = 0; this.method_7423(amount); j++) {
               RecipeFinder.this.take(this.field_7551[this.field_7557.getInt(0)], amount);
               int k = this.field_7557.size() - 1;
               this.method_7421(this.field_7557.getInt(k));

               for (int l = 0; l < k; l++) {
                  this.method_7414((l & 1) == 0, this.field_7557.get(l), this.field_7557.get(l + 1));
               }

               this.field_7557.clear();
               this.field_7558.clear(0, this.ingredientCount + this.field_7553);
            }

            boolean bl = j == this.ingredientCount;
            boolean bl2 = bl && outMatchingInputIds != null;
            if (bl2) {
               outMatchingInputIds.clear();
            }

            this.field_7558.clear(0, this.ingredientCount + this.field_7553 + this.ingredientCount);
            int m = 0;
            List<Ingredient> list = this.recipe.getPreviewInputs();

            for (int n = 0; n < list.size(); n++) {
               if (bl2 && list.get(n).isEmpty()) {
                  outMatchingInputIds.add(0);
               } else {
                  for (int o = 0; o < this.field_7553; o++) {
                     if (this.method_7425(false, m, o)) {
                        this.method_7414(true, o, m);
                        RecipeFinder.this.addItem(this.field_7551[o], amount);
                        if (bl2) {
                           outMatchingInputIds.add(this.field_7551[o]);
                        }
                     }
                  }

                  m++;
               }
            }

            return bl;
         }
      }

      private int[] method_7422() {
         IntCollection intCollection = new IntAVLTreeSet();

         for (Ingredient lv : this.ingredients) {
            intCollection.addAll(lv.getIds());
         }

         IntIterator intIterator = intCollection.iterator();

         while (intIterator.hasNext()) {
            if (!RecipeFinder.this.contains(intIterator.nextInt())) {
               intIterator.remove();
            }
         }

         return intCollection.toIntArray();
      }

      private boolean method_7423(int i) {
         int j = this.field_7553;

         for (int k = 0; k < j; k++) {
            if (RecipeFinder.this.idToAmountMap.get(this.field_7551[k]) >= i) {
               this.method_7413(false, k);

               while (!this.field_7557.isEmpty()) {
                  int l = this.field_7557.size();
                  boolean bl = (l & 1) == 1;
                  int m = this.field_7557.getInt(l - 1);
                  if (!bl && !this.method_7416(m)) {
                     break;
                  }

                  int n = bl ? this.ingredientCount : j;
                  int o = 0;

                  while (true) {
                     if (o < n) {
                        if (this.method_7426(bl, o) || !this.method_7418(bl, m, o) || !this.method_7425(bl, m, o)) {
                           o++;
                           continue;
                        }

                        this.method_7413(bl, o);
                     }

                     o = this.field_7557.size();
                     if (o == l) {
                        this.field_7557.removeInt(o - 1);
                     }
                     break;
                  }
               }

               if (!this.field_7557.isEmpty()) {
                  return true;
               }
            }
         }

         return false;
      }

      private boolean method_7416(int i) {
         return this.field_7558.get(this.method_7419(i));
      }

      private void method_7421(int i) {
         this.field_7558.set(this.method_7419(i));
      }

      private int method_7419(int i) {
         return this.ingredientCount + this.field_7553 + i;
      }

      private boolean method_7418(boolean bl, int i, int j) {
         return this.field_7558.get(this.method_7420(bl, i, j));
      }

      private boolean method_7425(boolean bl, int i, int j) {
         return bl != this.field_7558.get(1 + this.method_7420(bl, i, j));
      }

      private void method_7414(boolean bl, int i, int j) {
         this.field_7558.flip(1 + this.method_7420(bl, i, j));
      }

      private int method_7420(boolean bl, int i, int j) {
         int k = bl ? i * this.ingredientCount + j : j * this.ingredientCount + i;
         return this.ingredientCount + this.field_7553 + this.ingredientCount + 2 * k;
      }

      private void method_7413(boolean bl, int i) {
         this.field_7558.set(this.method_7424(bl, i));
         this.field_7557.add(i);
      }

      private boolean method_7426(boolean bl, int i) {
         return this.field_7558.get(this.method_7424(bl, i));
      }

      private int method_7424(boolean bl, int i) {
         return (bl ? 0 : this.ingredientCount) + i;
      }

      public int countCrafts(int limit, @Nullable IntList outMatchingInputIds) {
         int j = 0;
         int k = Math.min(limit, this.method_7415()) + 1;

         while (true) {
            int l = (j + k) / 2;
            if (this.find(l, null)) {
               if (k - j <= 1) {
                  if (l > 0) {
                     this.find(l, outMatchingInputIds);
                  }

                  return l;
               }

               j = l;
            } else {
               k = l;
            }
         }
      }

      private int method_7415() {
         int i = Integer.MAX_VALUE;

         for (Ingredient lv : this.ingredients) {
            int j = 0;
            IntListIterator var5 = lv.getIds().iterator();

            while (var5.hasNext()) {
               int k = (Integer)var5.next();
               j = Math.max(j, RecipeFinder.this.idToAmountMap.get(k));
            }

            if (i > 0) {
               i = Math.min(i, j);
            }
         }

         return i;
      }
   }
}
