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

   public void addItem(ItemStack _snowman) {
      this.method_20478(_snowman, 64);
   }

   public void method_20478(ItemStack _snowman, int _snowman) {
      if (!_snowman.isEmpty()) {
         int _snowmanxx = getItemId(_snowman);
         int _snowmanxxx = Math.min(_snowman, _snowman.getCount());
         this.addItem(_snowmanxx, _snowmanxxx);
      }
   }

   public static int getItemId(ItemStack _snowman) {
      return Registry.ITEM.getRawId(_snowman.getItem());
   }

   private boolean contains(int _snowman) {
      return this.idToAmountMap.get(_snowman) > 0;
   }

   private int take(int id, int amount) {
      int _snowman = this.idToAmountMap.get(id);
      if (_snowman >= amount) {
         this.idToAmountMap.put(id, _snowman - amount);
         return id;
      } else {
         return 0;
      }
   }

   private void addItem(int id, int amount) {
      this.idToAmountMap.put(id, this.idToAmountMap.get(id) + amount);
   }

   public boolean findRecipe(Recipe<?> _snowman, @Nullable IntList outMatchingInputIds) {
      return this.findRecipe(_snowman, outMatchingInputIds, 1);
   }

   public boolean findRecipe(Recipe<?> _snowman, @Nullable IntList outMatchingInputIds, int amount) {
      return new RecipeFinder.Filter(_snowman).find(amount, outMatchingInputIds);
   }

   public int countRecipeCrafts(Recipe<?> _snowman, @Nullable IntList outMatchingInputIds) {
      return this.countRecipeCrafts(_snowman, Integer.MAX_VALUE, outMatchingInputIds);
   }

   public int countRecipeCrafts(Recipe<?> _snowman, int limit, @Nullable IntList outMatchingInputIds) {
      return new RecipeFinder.Filter(_snowman).countCrafts(limit, outMatchingInputIds);
   }

   public static ItemStack getStackFromId(int _snowman) {
      return _snowman == 0 ? ItemStack.EMPTY : new ItemStack(Item.byRawId(_snowman));
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

      public Filter(Recipe<?> _snowman) {
         this.recipe = _snowman;
         this.ingredients.addAll(_snowman.getPreviewInputs());
         this.ingredients.removeIf(Ingredient::isEmpty);
         this.ingredientCount = this.ingredients.size();
         this.field_7551 = this.method_7422();
         this.field_7553 = this.field_7551.length;
         this.field_7558 = new BitSet(this.ingredientCount + this.field_7553 + this.ingredientCount + this.ingredientCount * this.field_7553);

         for (int _snowmanx = 0; _snowmanx < this.ingredients.size(); _snowmanx++) {
            IntList _snowmanxx = this.ingredients.get(_snowmanx).getIds();

            for (int _snowmanxxx = 0; _snowmanxxx < this.field_7553; _snowmanxxx++) {
               if (_snowmanxx.contains(this.field_7551[_snowmanxxx])) {
                  this.field_7558.set(this.method_7420(true, _snowmanxxx, _snowmanx));
               }
            }
         }
      }

      public boolean find(int amount, @Nullable IntList outMatchingInputIds) {
         if (amount <= 0) {
            return true;
         } else {
            int _snowman;
            for (_snowman = 0; this.method_7423(amount); _snowman++) {
               RecipeFinder.this.take(this.field_7551[this.field_7557.getInt(0)], amount);
               int _snowmanx = this.field_7557.size() - 1;
               this.method_7421(this.field_7557.getInt(_snowmanx));

               for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
                  this.method_7414((_snowmanxx & 1) == 0, this.field_7557.get(_snowmanxx), this.field_7557.get(_snowmanxx + 1));
               }

               this.field_7557.clear();
               this.field_7558.clear(0, this.ingredientCount + this.field_7553);
            }

            boolean _snowmanx = _snowman == this.ingredientCount;
            boolean _snowmanxx = _snowmanx && outMatchingInputIds != null;
            if (_snowmanxx) {
               outMatchingInputIds.clear();
            }

            this.field_7558.clear(0, this.ingredientCount + this.field_7553 + this.ingredientCount);
            int _snowmanxxx = 0;
            List<Ingredient> _snowmanxxxx = this.recipe.getPreviewInputs();

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx.size(); _snowmanxxxxx++) {
               if (_snowmanxx && _snowmanxxxx.get(_snowmanxxxxx).isEmpty()) {
                  outMatchingInputIds.add(0);
               } else {
                  for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < this.field_7553; _snowmanxxxxxx++) {
                     if (this.method_7425(false, _snowmanxxx, _snowmanxxxxxx)) {
                        this.method_7414(true, _snowmanxxxxxx, _snowmanxxx);
                        RecipeFinder.this.addItem(this.field_7551[_snowmanxxxxxx], amount);
                        if (_snowmanxx) {
                           outMatchingInputIds.add(this.field_7551[_snowmanxxxxxx]);
                        }
                     }
                  }

                  _snowmanxxx++;
               }
            }

            return _snowmanx;
         }
      }

      private int[] method_7422() {
         IntCollection _snowman = new IntAVLTreeSet();

         for (Ingredient _snowmanx : this.ingredients) {
            _snowman.addAll(_snowmanx.getIds());
         }

         IntIterator _snowmanx = _snowman.iterator();

         while (_snowmanx.hasNext()) {
            if (!RecipeFinder.this.contains(_snowmanx.nextInt())) {
               _snowmanx.remove();
            }
         }

         return _snowman.toIntArray();
      }

      private boolean method_7423(int _snowman) {
         int _snowmanx = this.field_7553;

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
            if (RecipeFinder.this.idToAmountMap.get(this.field_7551[_snowmanxx]) >= _snowman) {
               this.method_7413(false, _snowmanxx);

               while (!this.field_7557.isEmpty()) {
                  int _snowmanxxx = this.field_7557.size();
                  boolean _snowmanxxxx = (_snowmanxxx & 1) == 1;
                  int _snowmanxxxxx = this.field_7557.getInt(_snowmanxxx - 1);
                  if (!_snowmanxxxx && !this.method_7416(_snowmanxxxxx)) {
                     break;
                  }

                  int _snowmanxxxxxx = _snowmanxxxx ? this.ingredientCount : _snowmanx;
                  int _snowmanxxxxxxx = 0;

                  while (true) {
                     if (_snowmanxxxxxxx < _snowmanxxxxxx) {
                        if (this.method_7426(_snowmanxxxx, _snowmanxxxxxxx) || !this.method_7418(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx) || !this.method_7425(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx)) {
                           _snowmanxxxxxxx++;
                           continue;
                        }

                        this.method_7413(_snowmanxxxx, _snowmanxxxxxxx);
                     }

                     _snowmanxxxxxxx = this.field_7557.size();
                     if (_snowmanxxxxxxx == _snowmanxxx) {
                        this.field_7557.removeInt(_snowmanxxxxxxx - 1);
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

      private boolean method_7416(int _snowman) {
         return this.field_7558.get(this.method_7419(_snowman));
      }

      private void method_7421(int _snowman) {
         this.field_7558.set(this.method_7419(_snowman));
      }

      private int method_7419(int _snowman) {
         return this.ingredientCount + this.field_7553 + _snowman;
      }

      private boolean method_7418(boolean _snowman, int _snowman, int _snowman) {
         return this.field_7558.get(this.method_7420(_snowman, _snowman, _snowman));
      }

      private boolean method_7425(boolean _snowman, int _snowman, int _snowman) {
         return _snowman != this.field_7558.get(1 + this.method_7420(_snowman, _snowman, _snowman));
      }

      private void method_7414(boolean _snowman, int _snowman, int _snowman) {
         this.field_7558.flip(1 + this.method_7420(_snowman, _snowman, _snowman));
      }

      private int method_7420(boolean _snowman, int _snowman, int _snowman) {
         int _snowmanxxx = _snowman ? _snowman * this.ingredientCount + _snowman : _snowman * this.ingredientCount + _snowman;
         return this.ingredientCount + this.field_7553 + this.ingredientCount + 2 * _snowmanxxx;
      }

      private void method_7413(boolean _snowman, int _snowman) {
         this.field_7558.set(this.method_7424(_snowman, _snowman));
         this.field_7557.add(_snowman);
      }

      private boolean method_7426(boolean _snowman, int _snowman) {
         return this.field_7558.get(this.method_7424(_snowman, _snowman));
      }

      private int method_7424(boolean _snowman, int _snowman) {
         return (_snowman ? 0 : this.ingredientCount) + _snowman;
      }

      public int countCrafts(int limit, @Nullable IntList outMatchingInputIds) {
         int _snowman = 0;
         int _snowmanx = Math.min(limit, this.method_7415()) + 1;

         while (true) {
            int _snowmanxx = (_snowman + _snowmanx) / 2;
            if (this.find(_snowmanxx, null)) {
               if (_snowmanx - _snowman <= 1) {
                  if (_snowmanxx > 0) {
                     this.find(_snowmanxx, outMatchingInputIds);
                  }

                  return _snowmanxx;
               }

               _snowman = _snowmanxx;
            } else {
               _snowmanx = _snowmanxx;
            }
         }
      }

      private int method_7415() {
         int _snowman = Integer.MAX_VALUE;

         for (Ingredient _snowmanx : this.ingredients) {
            int _snowmanxx = 0;
            IntListIterator var5 = _snowmanx.getIds().iterator();

            while (var5.hasNext()) {
               int _snowmanxxx = (Integer)var5.next();
               _snowmanxx = Math.max(_snowmanxx, RecipeFinder.this.idToAmountMap.get(_snowmanxxx));
            }

            if (_snowman > 0) {
               _snowman = Math.min(_snowman, _snowmanxx);
            }
         }

         return _snowman;
      }
   }
}
