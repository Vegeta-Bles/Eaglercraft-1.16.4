/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.ints.Int2IntMap
 *  it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntAVLTreeSet
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntIterator
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntListIterator
 *  javax.annotation.Nullable
 */
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
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

public class RecipeFinder {
    public final Int2IntMap idToAmountMap = new Int2IntOpenHashMap();

    public void addNormalItem(ItemStack stack) {
        if (!(stack.isDamaged() || stack.hasEnchantments() || stack.hasCustomName())) {
            this.addItem(stack);
        }
    }

    public void addItem(ItemStack itemStack) {
        this.method_20478(itemStack, 64);
    }

    public void method_20478(ItemStack itemStack, int n) {
        if (!itemStack.isEmpty()) {
            _snowman = RecipeFinder.getItemId(itemStack);
            _snowman = Math.min(n, itemStack.getCount());
            this.addItem(_snowman, _snowman);
        }
    }

    public static int getItemId(ItemStack itemStack) {
        return Registry.ITEM.getRawId(itemStack.getItem());
    }

    private boolean contains(int n) {
        return this.idToAmountMap.get(n) > 0;
    }

    private int take(int id, int amount) {
        int n = this.idToAmountMap.get(id);
        if (n >= amount) {
            this.idToAmountMap.put(id, n - amount);
            return id;
        }
        return 0;
    }

    private void addItem(int id, int amount) {
        this.idToAmountMap.put(id, this.idToAmountMap.get(id) + amount);
    }

    public boolean findRecipe(Recipe<?> recipe, @Nullable IntList outMatchingInputIds) {
        return this.findRecipe(recipe, outMatchingInputIds, 1);
    }

    public boolean findRecipe(Recipe<?> recipe, @Nullable IntList outMatchingInputIds, int amount) {
        return new Filter(recipe).find(amount, outMatchingInputIds);
    }

    public int countRecipeCrafts(Recipe<?> recipe, @Nullable IntList outMatchingInputIds) {
        return this.countRecipeCrafts(recipe, Integer.MAX_VALUE, outMatchingInputIds);
    }

    public int countRecipeCrafts(Recipe<?> recipe, int limit, @Nullable IntList outMatchingInputIds) {
        return new Filter(recipe).countCrafts(limit, outMatchingInputIds);
    }

    public static ItemStack getStackFromId(int n) {
        if (n == 0) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(Item.byRawId(n));
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

        public Filter(Recipe<?> recipe2) {
            this.recipe = recipe2;
            this.ingredients.addAll(recipe2.getPreviewInputs());
            this.ingredients.removeIf(Ingredient::isEmpty);
            this.ingredientCount = this.ingredients.size();
            this.field_7551 = this.method_7422();
            this.field_7553 = this.field_7551.length;
            this.field_7558 = new BitSet(this.ingredientCount + this.field_7553 + this.ingredientCount + this.ingredientCount * this.field_7553);
            for (int i = 0; i < this.ingredients.size(); ++i) {
                IntList intList = this.ingredients.get(i).getIds();
                for (int j = 0; j < this.field_7553; ++j) {
                    if (!intList.contains(this.field_7551[j])) continue;
                    this.field_7558.set(this.method_7420(true, j, i));
                }
            }
        }

        public boolean find(int amount, @Nullable IntList outMatchingInputIds) {
            if (amount <= 0) {
                return true;
            }
            int n = 0;
            while (this.method_7423(amount)) {
                RecipeFinder.this.take(this.field_7551[this.field_7557.getInt(0)], amount);
                _snowman = this.field_7557.size() - 1;
                this.method_7421(this.field_7557.getInt(_snowman));
                for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                    this.method_7414((_snowman & 1) == 0, this.field_7557.get(_snowman), this.field_7557.get(_snowman + 1));
                }
                this.field_7557.clear();
                this.field_7558.clear(0, this.ingredientCount + this.field_7553);
                ++n;
            }
            _snowman = n == this.ingredientCount ? 1 : 0;
            int n2 = _snowman = _snowman != 0 && outMatchingInputIds != null ? 1 : 0;
            if (_snowman != 0) {
                outMatchingInputIds.clear();
            }
            this.field_7558.clear(0, this.ingredientCount + this.field_7553 + this.ingredientCount);
            _snowman = 0;
            DefaultedList<Ingredient> _snowman2 = this.recipe.getPreviewInputs();
            for (_snowman = 0; _snowman < _snowman2.size(); ++_snowman) {
                if (_snowman != 0 && ((Ingredient)_snowman2.get(_snowman)).isEmpty()) {
                    outMatchingInputIds.add(0);
                    continue;
                }
                for (_snowman = 0; _snowman < this.field_7553; ++_snowman) {
                    if (!this.method_7425(false, _snowman, _snowman)) continue;
                    this.method_7414(true, _snowman, _snowman);
                    RecipeFinder.this.addItem(this.field_7551[_snowman], amount);
                    if (_snowman == 0) continue;
                    outMatchingInputIds.add(this.field_7551[_snowman]);
                }
                ++_snowman;
            }
            return _snowman != 0;
        }

        private int[] method_7422() {
            IntAVLTreeSet intAVLTreeSet = new IntAVLTreeSet();
            for (Ingredient ingredient : this.ingredients) {
                intAVLTreeSet.addAll((IntCollection)ingredient.getIds());
            }
            IntIterator _snowman2 = intAVLTreeSet.iterator();
            while (_snowman2.hasNext()) {
                if (RecipeFinder.this.contains(_snowman2.nextInt())) continue;
                _snowman2.remove();
            }
            return intAVLTreeSet.toIntArray();
        }

        private boolean method_7423(int n) {
            _snowman = this.field_7553;
            for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                if (RecipeFinder.this.idToAmountMap.get(this.field_7551[_snowman]) < n) continue;
                this.method_7413(false, _snowman);
                while (!this.field_7557.isEmpty()) {
                    int n2;
                    _snowman = this.field_7557.size();
                    boolean bl = (_snowman & 1) == 1;
                    int _snowman2 = this.field_7557.getInt(_snowman - 1);
                    if (!bl && !this.method_7416(_snowman2)) break;
                    int _snowman3 = bl ? this.ingredientCount : _snowman;
                    for (n2 = 0; n2 < _snowman3; ++n2) {
                        if (this.method_7426(bl, n2) || !this.method_7418(bl, _snowman2, n2) || !this.method_7425(bl, _snowman2, n2)) continue;
                        this.method_7413(bl, n2);
                        break;
                    }
                    if ((n2 = this.field_7557.size()) != _snowman) continue;
                    this.field_7557.removeInt(n2 - 1);
                }
                if (this.field_7557.isEmpty()) continue;
                return true;
            }
            return false;
        }

        private boolean method_7416(int n) {
            return this.field_7558.get(this.method_7419(n));
        }

        private void method_7421(int n) {
            this.field_7558.set(this.method_7419(n));
        }

        private int method_7419(int n) {
            return this.ingredientCount + this.field_7553 + n;
        }

        private boolean method_7418(boolean bl, int n, int n2) {
            return this.field_7558.get(this.method_7420(bl, n, n2));
        }

        private boolean method_7425(boolean bl, int n, int n2) {
            return bl != this.field_7558.get(1 + this.method_7420(bl, n, n2));
        }

        private void method_7414(boolean bl, int n, int n2) {
            this.field_7558.flip(1 + this.method_7420(bl, n, n2));
        }

        private int method_7420(boolean bl, int n, int n2) {
            _snowman = bl ? n * this.ingredientCount + n2 : n2 * this.ingredientCount + n;
            return this.ingredientCount + this.field_7553 + this.ingredientCount + 2 * _snowman;
        }

        private void method_7413(boolean bl, int n) {
            this.field_7558.set(this.method_7424(bl, n));
            this.field_7557.add(n);
        }

        private boolean method_7426(boolean bl, int n) {
            return this.field_7558.get(this.method_7424(bl, n));
        }

        private int method_7424(boolean bl, int n) {
            return (bl ? 0 : this.ingredientCount) + n;
        }

        public int countCrafts(int limit, @Nullable IntList outMatchingInputIds) {
            int n = 0;
            _snowman = Math.min(limit, this.method_7415()) + 1;
            while (true) {
                if (this.find(_snowman = (n + _snowman) / 2, null)) {
                    if (_snowman - n <= 1) break;
                    n = _snowman;
                    continue;
                }
                _snowman = _snowman;
            }
            if (_snowman > 0) {
                this.find(_snowman, outMatchingInputIds);
            }
            return _snowman;
        }

        private int method_7415() {
            int n = Integer.MAX_VALUE;
            for (Ingredient ingredient : this.ingredients) {
                int n2 = 0;
                IntListIterator intListIterator = ingredient.getIds().iterator();
                while (intListIterator.hasNext()) {
                    _snowman = (Integer)intListIterator.next();
                    n2 = Math.max(n2, RecipeFinder.this.idToAmountMap.get(_snowman));
                }
                if (n <= 0) continue;
                n = Math.min(n, n2);
            }
            return n;
        }
    }
}

