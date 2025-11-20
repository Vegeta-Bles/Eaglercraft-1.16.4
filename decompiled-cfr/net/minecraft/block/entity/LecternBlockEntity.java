/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.block.entity;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.LecternScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class LecternBlockEntity
extends BlockEntity
implements Clearable,
NamedScreenHandlerFactory {
    private final Inventory inventory = new Inventory(this){
        final /* synthetic */ LecternBlockEntity field_17391;
        {
            this.field_17391 = lecternBlockEntity;
        }

        public int size() {
            return 1;
        }

        public boolean isEmpty() {
            return LecternBlockEntity.method_17515(this.field_17391).isEmpty();
        }

        public ItemStack getStack(int slot) {
            return slot == 0 ? LecternBlockEntity.method_17515(this.field_17391) : ItemStack.EMPTY;
        }

        public ItemStack removeStack(int slot, int amount) {
            if (slot == 0) {
                ItemStack itemStack = LecternBlockEntity.method_17515(this.field_17391).split(amount);
                if (LecternBlockEntity.method_17515(this.field_17391).isEmpty()) {
                    LecternBlockEntity.method_17519(this.field_17391);
                }
                return itemStack;
            }
            return ItemStack.EMPTY;
        }

        public ItemStack removeStack(int slot) {
            if (slot == 0) {
                ItemStack itemStack = LecternBlockEntity.method_17515(this.field_17391);
                LecternBlockEntity.method_17517(this.field_17391, ItemStack.EMPTY);
                LecternBlockEntity.method_17519(this.field_17391);
                return itemStack;
            }
            return ItemStack.EMPTY;
        }

        public void setStack(int slot, ItemStack stack) {
        }

        public int getMaxCountPerStack() {
            return 1;
        }

        public void markDirty() {
            this.field_17391.markDirty();
        }

        public boolean canPlayerUse(PlayerEntity player) {
            if (this.field_17391.world.getBlockEntity(this.field_17391.pos) != this.field_17391) {
                return false;
            }
            if (player.squaredDistanceTo((double)this.field_17391.pos.getX() + 0.5, (double)this.field_17391.pos.getY() + 0.5, (double)this.field_17391.pos.getZ() + 0.5) > 64.0) {
                return false;
            }
            return this.field_17391.hasBook();
        }

        public boolean isValid(int slot, ItemStack stack) {
            return false;
        }

        public void clear() {
        }
    };
    private final PropertyDelegate propertyDelegate = new PropertyDelegate(this){
        final /* synthetic */ LecternBlockEntity field_17392;
        {
            this.field_17392 = lecternBlockEntity;
        }

        public int get(int index) {
            return index == 0 ? LecternBlockEntity.method_17521(this.field_17392) : 0;
        }

        public void set(int index, int value) {
            if (index == 0) {
                LecternBlockEntity.method_17516(this.field_17392, value);
            }
        }

        public int size() {
            return 1;
        }
    };
    private ItemStack book = ItemStack.EMPTY;
    private int currentPage;
    private int pageCount;

    public LecternBlockEntity() {
        super(BlockEntityType.LECTERN);
    }

    public ItemStack getBook() {
        return this.book;
    }

    public boolean hasBook() {
        Item item = this.book.getItem();
        return item == Items.WRITABLE_BOOK || item == Items.WRITTEN_BOOK;
    }

    public void setBook(ItemStack book) {
        this.setBook(book, null);
    }

    private void onBookRemoved() {
        this.currentPage = 0;
        this.pageCount = 0;
        LecternBlock.setHasBook(this.getWorld(), this.getPos(), this.getCachedState(), false);
    }

    public void setBook(ItemStack book, @Nullable PlayerEntity player) {
        this.book = this.resolveBook(book, player);
        this.currentPage = 0;
        this.pageCount = WrittenBookItem.getPageCount(this.book);
        this.markDirty();
    }

    private void setCurrentPage(int currentPage) {
        int n = MathHelper.clamp(currentPage, 0, this.pageCount - 1);
        if (n != this.currentPage) {
            this.currentPage = n;
            this.markDirty();
            LecternBlock.setPowered(this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public int getComparatorOutput() {
        float f = this.pageCount > 1 ? (float)this.getCurrentPage() / ((float)this.pageCount - 1.0f) : 1.0f;
        return MathHelper.floor(f * 14.0f) + (this.hasBook() ? 1 : 0);
    }

    private ItemStack resolveBook(ItemStack book, @Nullable PlayerEntity player) {
        if (this.world instanceof ServerWorld && book.getItem() == Items.WRITTEN_BOOK) {
            WrittenBookItem.resolve(book, this.getCommandSource(player), player);
        }
        return book;
    }

    private ServerCommandSource getCommandSource(@Nullable PlayerEntity player) {
        Text _snowman2;
        if (player == null) {
            String string = "Lectern";
            _snowman2 = new LiteralText("Lectern");
        } else {
            string = player.getName().getString();
            _snowman2 = player.getDisplayName();
        }
        Vec3d vec3d = Vec3d.ofCenter(this.pos);
        return new ServerCommandSource(CommandOutput.DUMMY, vec3d, Vec2f.ZERO, (ServerWorld)this.world, 2, string, _snowman2, this.world.getServer(), player);
    }

    @Override
    public boolean copyItemDataRequiresOperator() {
        return true;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.book = tag.contains("Book", 10) ? this.resolveBook(ItemStack.fromTag(tag.getCompound("Book")), null) : ItemStack.EMPTY;
        this.pageCount = WrittenBookItem.getPageCount(this.book);
        this.currentPage = MathHelper.clamp(tag.getInt("Page"), 0, this.pageCount - 1);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        if (!this.getBook().isEmpty()) {
            tag.put("Book", this.getBook().toTag(new CompoundTag()));
            tag.putInt("Page", this.currentPage);
        }
        return tag;
    }

    @Override
    public void clear() {
        this.setBook(ItemStack.EMPTY);
    }

    @Override
    public ScreenHandler createMenu(int n, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new LecternScreenHandler(n, this.inventory, this.propertyDelegate);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("container.lectern");
    }

    static /* synthetic */ ItemStack method_17515(LecternBlockEntity lecternBlockEntity) {
        return lecternBlockEntity.book;
    }

    static /* synthetic */ void method_17519(LecternBlockEntity lecternBlockEntity) {
        lecternBlockEntity.onBookRemoved();
    }

    static /* synthetic */ ItemStack method_17517(LecternBlockEntity lecternBlockEntity, ItemStack itemStack) {
        lecternBlockEntity.book = itemStack;
        return lecternBlockEntity.book;
    }

    static /* synthetic */ int method_17521(LecternBlockEntity lecternBlockEntity) {
        return lecternBlockEntity.currentPage;
    }

    static /* synthetic */ void method_17516(LecternBlockEntity lecternBlockEntity, int n) {
        lecternBlockEntity.setCurrentPage(n);
    }
}

