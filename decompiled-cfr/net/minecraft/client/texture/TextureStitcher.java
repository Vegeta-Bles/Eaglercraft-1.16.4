/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 */
package net.minecraft.client.texture;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.TextureStitcherCannotFitException;
import net.minecraft.util.math.MathHelper;

public class TextureStitcher {
    private static final Comparator<Holder> COMPARATOR = Comparator.comparing(holder -> -holder.height).thenComparing(holder -> -holder.width).thenComparing(holder -> holder.sprite.getId());
    private final int mipLevel;
    private final Set<Holder> holders = Sets.newHashSetWithExpectedSize((int)256);
    private final List<Slot> slots = Lists.newArrayListWithCapacity((int)256);
    private int width;
    private int height;
    private final int maxWidth;
    private final int maxHeight;

    public TextureStitcher(int maxWidth, int maxHeight, int mipLevel) {
        this.mipLevel = mipLevel;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void add(Sprite.Info info) {
        Holder holder = new Holder(info, this.mipLevel);
        this.holders.add(holder);
    }

    public void stitch() {
        ArrayList arrayList = Lists.newArrayList(this.holders);
        arrayList.sort(COMPARATOR);
        for (Holder holder2 : arrayList) {
            if (this.fit(holder2)) continue;
            throw new TextureStitcherCannotFitException(holder2.sprite, (Collection)arrayList.stream().map(holder -> holder.sprite).collect(ImmutableList.toImmutableList()));
        }
        this.width = MathHelper.smallestEncompassingPowerOfTwo(this.width);
        this.height = MathHelper.smallestEncompassingPowerOfTwo(this.height);
    }

    public void getStitchedSprites(SpriteConsumer spriteConsumer) {
        for (Slot slot2 : this.slots) {
            slot2.addAllFilledSlots(slot -> {
                Holder holder = slot.getTexture();
                Sprite.Info _snowman2 = holder.sprite;
                spriteConsumer.load(_snowman2, this.width, this.height, slot.getX(), slot.getY());
            });
        }
    }

    private static int applyMipLevel(int size, int mipLevel) {
        return (size >> mipLevel) + ((size & (1 << mipLevel) - 1) == 0 ? 0 : 1) << mipLevel;
    }

    private boolean fit(Holder holder2) {
        Holder holder2;
        for (Slot slot : this.slots) {
            if (!slot.fit(holder2)) continue;
            return true;
        }
        return this.growAndFit(holder2);
    }

    private boolean growAndFit(Holder holder2) {
        Slot _snowman4;
        boolean bl;
        int n = MathHelper.smallestEncompassingPowerOfTwo(this.width);
        _snowman = MathHelper.smallestEncompassingPowerOfTwo(this.height);
        _snowman = MathHelper.smallestEncompassingPowerOfTwo(this.width + holder2.width);
        _snowman = MathHelper.smallestEncompassingPowerOfTwo(this.height + holder2.height);
        boolean _snowman2 = _snowman <= this.maxWidth;
        boolean bl2 = _snowman = _snowman <= this.maxHeight;
        if (!_snowman2 && !_snowman) {
            return false;
        }
        boolean _snowman3 = _snowman2 && n != _snowman;
        boolean bl3 = _snowman = _snowman && _snowman != _snowman;
        if (_snowman3 ^ _snowman) {
            bl = _snowman3;
        } else {
            boolean bl4 = bl = _snowman2 && n <= _snowman;
        }
        if (bl) {
            if (this.height == 0) {
                this.height = holder2.height;
            }
            _snowman4 = new Slot(this.width, 0, holder2.width, this.height);
            this.width += holder2.width;
        } else {
            Holder holder2;
            _snowman4 = new Slot(0, this.height, this.width, holder2.height);
            this.height += holder2.height;
        }
        _snowman4.fit(holder2);
        this.slots.add(_snowman4);
        return true;
    }

    public static class Slot {
        private final int x;
        private final int y;
        private final int width;
        private final int height;
        private List<Slot> subSlots;
        private Holder texture;

        public Slot(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public Holder getTexture() {
            return this.texture;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public boolean fit(Holder holder) {
            if (this.texture != null) {
                return false;
            }
            int n = holder.width;
            _snowman = holder.height;
            if (n > this.width || _snowman > this.height) {
                return false;
            }
            if (n == this.width && _snowman == this.height) {
                this.texture = holder;
                return true;
            }
            if (this.subSlots == null) {
                this.subSlots = Lists.newArrayListWithCapacity((int)1);
                this.subSlots.add(new Slot(this.x, this.y, n, _snowman));
                _snowman = this.width - n;
                _snowman = this.height - _snowman;
                if (_snowman > 0 && _snowman > 0) {
                    _snowman = Math.max(this.height, _snowman);
                    if (_snowman >= (_snowman = Math.max(this.width, _snowman))) {
                        this.subSlots.add(new Slot(this.x, this.y + _snowman, n, _snowman));
                        this.subSlots.add(new Slot(this.x + n, this.y, _snowman, this.height));
                    } else {
                        this.subSlots.add(new Slot(this.x + n, this.y, _snowman, _snowman));
                        this.subSlots.add(new Slot(this.x, this.y + _snowman, this.width, _snowman));
                    }
                } else if (_snowman == 0) {
                    this.subSlots.add(new Slot(this.x, this.y + _snowman, n, _snowman));
                } else if (_snowman == 0) {
                    this.subSlots.add(new Slot(this.x + n, this.y, _snowman, _snowman));
                }
            }
            for (Slot slot : this.subSlots) {
                if (!slot.fit(holder)) continue;
                return true;
            }
            return false;
        }

        public void addAllFilledSlots(Consumer<Slot> consumer) {
            if (this.texture != null) {
                consumer.accept(this);
            } else if (this.subSlots != null) {
                for (Slot slot : this.subSlots) {
                    slot.addAllFilledSlots(consumer);
                }
            }
        }

        public String toString() {
            return "Slot{originX=" + this.x + ", originY=" + this.y + ", width=" + this.width + ", height=" + this.height + ", texture=" + this.texture + ", subSlots=" + this.subSlots + '}';
        }
    }

    static class Holder {
        public final Sprite.Info sprite;
        public final int width;
        public final int height;

        public Holder(Sprite.Info sprite, int mipLevel) {
            this.sprite = sprite;
            this.width = TextureStitcher.applyMipLevel(sprite.getWidth(), mipLevel);
            this.height = TextureStitcher.applyMipLevel(sprite.getHeight(), mipLevel);
        }

        public String toString() {
            return "Holder{width=" + this.width + ", height=" + this.height + '}';
        }
    }

    public static interface SpriteConsumer {
        public void load(Sprite.Info var1, int var2, int var3, int var4, int var5);
    }
}

