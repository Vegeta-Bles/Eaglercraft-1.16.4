/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.StatsListener;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.StatType;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class StatsScreen
extends Screen
implements StatsListener {
    private static final Text field_26546 = new TranslatableText("multiplayer.downloadingStats");
    protected final Screen parent;
    private GeneralStatsListWidget generalStats;
    private ItemStatsListWidget itemStats;
    private EntityStatsListWidget mobStats;
    private final StatHandler statHandler;
    @Nullable
    private AlwaysSelectedEntryListWidget<?> selectedList;
    private boolean downloadingStats = true;

    public StatsScreen(Screen parent, StatHandler statHandler) {
        super(new TranslatableText("gui.stats"));
        this.parent = parent;
        this.statHandler = statHandler;
    }

    @Override
    protected void init() {
        this.downloadingStats = true;
        this.client.getNetworkHandler().sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.REQUEST_STATS));
    }

    public void createLists() {
        this.generalStats = new GeneralStatsListWidget(this.client);
        this.itemStats = new ItemStatsListWidget(this.client);
        this.mobStats = new EntityStatsListWidget(this.client);
    }

    public void createButtons() {
        this.addButton(new ButtonWidget(this.width / 2 - 120, this.height - 52, 80, 20, new TranslatableText("stat.generalButton"), buttonWidget -> this.selectStatList(this.generalStats)));
        ButtonWidget buttonWidget2 = this.addButton(new ButtonWidget(this.width / 2 - 40, this.height - 52, 80, 20, new TranslatableText("stat.itemsButton"), buttonWidget -> this.selectStatList(this.itemStats)));
        _snowman = this.addButton(new ButtonWidget(this.width / 2 + 40, this.height - 52, 80, 20, new TranslatableText("stat.mobsButton"), buttonWidget -> this.selectStatList(this.mobStats)));
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 28, 200, 20, ScreenTexts.DONE, buttonWidget -> this.client.openScreen(this.parent)));
        if (this.itemStats.children().isEmpty()) {
            buttonWidget2.active = false;
        }
        if (this.mobStats.children().isEmpty()) {
            _snowman.active = false;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.downloadingStats) {
            this.renderBackground(matrices);
            StatsScreen.drawCenteredText(matrices, this.textRenderer, field_26546, this.width / 2, this.height / 2, 0xFFFFFF);
            StatsScreen.drawCenteredString(matrices, this.textRenderer, PROGRESS_BAR_STAGES[(int)(Util.getMeasuringTimeMs() / 150L % (long)PROGRESS_BAR_STAGES.length)], this.width / 2, this.height / 2 + this.textRenderer.fontHeight * 2, 0xFFFFFF);
        } else {
            this.getSelectedStatList().render(matrices, mouseX, mouseY, delta);
            StatsScreen.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
            super.render(matrices, mouseX, mouseY, delta);
        }
    }

    @Override
    public void onStatsReady() {
        if (this.downloadingStats) {
            this.createLists();
            this.createButtons();
            this.selectStatList(this.generalStats);
            this.downloadingStats = false;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return !this.downloadingStats;
    }

    @Nullable
    public AlwaysSelectedEntryListWidget<?> getSelectedStatList() {
        return this.selectedList;
    }

    public void selectStatList(@Nullable AlwaysSelectedEntryListWidget<?> list) {
        this.children.remove(this.generalStats);
        this.children.remove(this.itemStats);
        this.children.remove(this.mobStats);
        if (list != null) {
            this.children.add(0, list);
            this.selectedList = list;
        }
    }

    private static String method_27027(Stat<Identifier> stat) {
        return "stat." + stat.getValue().toString().replace(':', '.');
    }

    private int getColumnX(int index) {
        return 115 + 40 * index;
    }

    private void renderStatItem(MatrixStack matrixStack, int n, int n2, Item item) {
        this.renderIcon(matrixStack, n + 1, n2 + 1, 0, 0);
        RenderSystem.enableRescaleNormal();
        this.itemRenderer.renderGuiItemIcon(item.getDefaultStack(), n + 2, n2 + 2);
        RenderSystem.disableRescaleNormal();
    }

    private void renderIcon(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(STATS_ICON_TEXTURE);
        StatsScreen.drawTexture(matrixStack, n, n2, this.getZOffset(), n3, n4, 18, 18, 128, 128);
    }

    class EntityStatsListWidget
    extends AlwaysSelectedEntryListWidget<Entry> {
        public EntityStatsListWidget(MinecraftClient minecraftClient) {
            super(minecraftClient, StatsScreen.this.width, StatsScreen.this.height, 32, StatsScreen.this.height - 64, ((StatsScreen)StatsScreen.this).textRenderer.fontHeight * 4);
            for (EntityType entityType : Registry.ENTITY_TYPE) {
                if (StatsScreen.this.statHandler.getStat(Stats.KILLED.getOrCreateStat(entityType)) <= 0 && StatsScreen.this.statHandler.getStat(Stats.KILLED_BY.getOrCreateStat(entityType)) <= 0) continue;
                this.addEntry(new Entry(entityType));
            }
        }

        @Override
        protected void renderBackground(MatrixStack matrices) {
            StatsScreen.this.renderBackground(matrices);
        }

        class Entry
        extends AlwaysSelectedEntryListWidget.Entry<Entry> {
            private final EntityType<?> entityType;
            private final Text field_26548;
            private final Text field_26549;
            private final boolean field_26550;
            private final Text field_26551;
            private final boolean field_26552;

            public Entry(EntityType<?> entityType) {
                this.entityType = entityType;
                this.field_26548 = entityType.getName();
                int n = StatsScreen.this.statHandler.getStat(Stats.KILLED.getOrCreateStat(entityType));
                if (n == 0) {
                    this.field_26549 = new TranslatableText("stat_type.minecraft.killed.none", this.field_26548);
                    this.field_26550 = false;
                } else {
                    this.field_26549 = new TranslatableText("stat_type.minecraft.killed", n, this.field_26548);
                    this.field_26550 = true;
                }
                _snowman = StatsScreen.this.statHandler.getStat(Stats.KILLED_BY.getOrCreateStat(entityType));
                if (_snowman == 0) {
                    this.field_26551 = new TranslatableText("stat_type.minecraft.killed_by.none", this.field_26548);
                    this.field_26552 = false;
                } else {
                    this.field_26551 = new TranslatableText("stat_type.minecraft.killed_by", this.field_26548, _snowman);
                    this.field_26552 = true;
                }
            }

            @Override
            public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                DrawableHelper.drawTextWithShadow(matrices, StatsScreen.this.textRenderer, this.field_26548, x + 2, y + 1, 0xFFFFFF);
                DrawableHelper.drawTextWithShadow(matrices, StatsScreen.this.textRenderer, this.field_26549, x + 2 + 10, y + 1 + ((StatsScreen)StatsScreen.this).textRenderer.fontHeight, this.field_26550 ? 0x909090 : 0x606060);
                DrawableHelper.drawTextWithShadow(matrices, StatsScreen.this.textRenderer, this.field_26551, x + 2 + 10, y + 1 + ((StatsScreen)StatsScreen.this).textRenderer.fontHeight * 2, this.field_26552 ? 0x909090 : 0x606060);
            }
        }
    }

    class ItemStatsListWidget
    extends AlwaysSelectedEntryListWidget<Entry> {
        protected final List<StatType<Block>> blockStatTypes;
        protected final List<StatType<Item>> itemStatTypes;
        private final int[] HEADER_ICON_SPRITE_INDICES;
        protected int selectedHeaderColumn;
        protected final List<Item> items;
        protected final Comparator<Item> comparator;
        @Nullable
        protected StatType<?> selectedStatType;
        protected int field_18760;

        public ItemStatsListWidget(MinecraftClient client) {
            super(client, StatsScreen.this.width, StatsScreen.this.height, 32, StatsScreen.this.height - 64, 20);
            this.HEADER_ICON_SPRITE_INDICES = new int[]{3, 4, 1, 2, 5, 6};
            this.selectedHeaderColumn = -1;
            this.comparator = new ItemComparator();
            this.blockStatTypes = Lists.newArrayList();
            this.blockStatTypes.add(Stats.MINED);
            this.itemStatTypes = Lists.newArrayList((Object[])new StatType[]{Stats.BROKEN, Stats.CRAFTED, Stats.USED, Stats.PICKED_UP, Stats.DROPPED});
            this.setRenderHeader(true, 20);
            Set set = Sets.newIdentityHashSet();
            for (Item item : Registry.ITEM) {
                boolean bl = false;
                for (StatType<Item> statType : this.itemStatTypes) {
                    if (!statType.hasStat(item) || StatsScreen.this.statHandler.getStat(statType.getOrCreateStat(item)) <= 0) continue;
                    bl = true;
                }
                if (!bl) continue;
                set.add(item);
            }
            for (Block block : Registry.BLOCK) {
                boolean bl = false;
                for (StatType<ItemConvertible> statType : this.blockStatTypes) {
                    if (!statType.hasStat(block) || StatsScreen.this.statHandler.getStat(statType.getOrCreateStat(block)) <= 0) continue;
                    bl = true;
                }
                if (!bl) continue;
                set.add(block.asItem());
            }
            set.remove(Items.AIR);
            this.items = Lists.newArrayList((Iterable)set);
            for (int i = 0; i < this.items.size(); ++i) {
                this.addEntry(new Entry());
            }
        }

        @Override
        protected void renderHeader(MatrixStack matrices, int x, int y, Tessellator tessellator) {
            int n;
            if (!this.client.mouse.wasLeftButtonClicked()) {
                this.selectedHeaderColumn = -1;
            }
            for (n = 0; n < this.HEADER_ICON_SPRITE_INDICES.length; ++n) {
                StatsScreen.this.renderIcon(matrices, x + StatsScreen.this.getColumnX(n) - 18, y + 1, 0, this.selectedHeaderColumn == n ? 0 : 18);
            }
            if (this.selectedStatType != null) {
                n = StatsScreen.this.getColumnX(this.getHeaderIndex(this.selectedStatType)) - 36;
                _snowman = this.field_18760 == 1 ? 2 : 1;
                StatsScreen.this.renderIcon(matrices, x + n, y + 1, 18 * _snowman, 0);
            }
            for (n = 0; n < this.HEADER_ICON_SPRITE_INDICES.length; ++n) {
                _snowman = this.selectedHeaderColumn == n ? 1 : 0;
                StatsScreen.this.renderIcon(matrices, x + StatsScreen.this.getColumnX(n) - 18 + _snowman, y + 1 + _snowman, 18 * this.HEADER_ICON_SPRITE_INDICES[n], 18);
            }
        }

        @Override
        public int getRowWidth() {
            return 375;
        }

        @Override
        protected int getScrollbarPositionX() {
            return this.width / 2 + 140;
        }

        @Override
        protected void renderBackground(MatrixStack matrices) {
            StatsScreen.this.renderBackground(matrices);
        }

        @Override
        protected void clickedHeader(int x, int y) {
            this.selectedHeaderColumn = -1;
            for (int i = 0; i < this.HEADER_ICON_SPRITE_INDICES.length; ++i) {
                _snowman = x - StatsScreen.this.getColumnX(i);
                if (_snowman < -36 || _snowman > 0) continue;
                this.selectedHeaderColumn = i;
                break;
            }
            if (this.selectedHeaderColumn >= 0) {
                this.selectStatType(this.getStatType(this.selectedHeaderColumn));
                this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            }
        }

        private StatType<?> getStatType(int headerColumn) {
            return headerColumn < this.blockStatTypes.size() ? this.blockStatTypes.get(headerColumn) : this.itemStatTypes.get(headerColumn - this.blockStatTypes.size());
        }

        private int getHeaderIndex(StatType<?> statType) {
            int n = this.blockStatTypes.indexOf(statType);
            if (n >= 0) {
                return n;
            }
            _snowman = this.itemStatTypes.indexOf(statType);
            if (_snowman >= 0) {
                return _snowman + this.blockStatTypes.size();
            }
            return -1;
        }

        @Override
        protected void renderDecorations(MatrixStack matrixStack2, int n, int n2) {
            if (n2 < this.top || n2 > this.bottom) {
                return;
            }
            Entry entry = (Entry)this.getEntryAtPosition(n, n2);
            int _snowman2 = (this.width - this.getRowWidth()) / 2;
            if (entry != null) {
                if (n < _snowman2 + 40 || n > _snowman2 + 40 + 20) {
                    return;
                }
                Item item = this.items.get(this.children().indexOf(entry));
                this.render(matrixStack2, this.getText(item), n, n2);
            } else {
                MatrixStack matrixStack2;
                Text text = null;
                int _snowman3 = n - _snowman2;
                for (int i = 0; i < this.HEADER_ICON_SPRITE_INDICES.length; ++i) {
                    _snowman = StatsScreen.this.getColumnX(i);
                    if (_snowman3 < _snowman - 18 || _snowman3 > _snowman) continue;
                    text = this.getStatType(i).method_30739();
                    break;
                }
                this.render(matrixStack2, text, n, n2);
            }
        }

        protected void render(MatrixStack matrixStack, @Nullable Text text, int n, int n2) {
            if (text == null) {
                return;
            }
            _snowman = n + 12;
            _snowman = n2 - 12;
            _snowman = StatsScreen.this.textRenderer.getWidth(text);
            this.fillGradient(matrixStack, _snowman - 3, _snowman - 3, _snowman + _snowman + 3, _snowman + 8 + 3, -1073741824, -1073741824);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0f, 0.0f, 400.0f);
            StatsScreen.this.textRenderer.drawWithShadow(matrixStack, text, (float)_snowman, (float)_snowman, -1);
            RenderSystem.popMatrix();
        }

        protected Text getText(Item item) {
            return item.getName();
        }

        protected void selectStatType(StatType<?> statType) {
            if (statType != this.selectedStatType) {
                this.selectedStatType = statType;
                this.field_18760 = -1;
            } else if (this.field_18760 == -1) {
                this.field_18760 = 1;
            } else {
                this.selectedStatType = null;
                this.field_18760 = 0;
            }
            this.items.sort(this.comparator);
        }

        class Entry
        extends AlwaysSelectedEntryListWidget.Entry<Entry> {
            private Entry() {
            }

            @Override
            public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                int n;
                Item item = ((StatsScreen)StatsScreen.this).itemStats.items.get(index);
                StatsScreen.this.renderStatItem(matrices, x + 40, y, item);
                for (n = 0; n < ((StatsScreen)StatsScreen.this).itemStats.blockStatTypes.size(); ++n) {
                    Stat<Block> stat = item instanceof BlockItem ? ((StatsScreen)StatsScreen.this).itemStats.blockStatTypes.get(n).getOrCreateStat(((BlockItem)item).getBlock()) : null;
                    this.render(matrices, stat, x + StatsScreen.this.getColumnX(n), y, index % 2 == 0);
                }
                for (n = 0; n < ((StatsScreen)StatsScreen.this).itemStats.itemStatTypes.size(); ++n) {
                    this.render(matrices, ((StatsScreen)StatsScreen.this).itemStats.itemStatTypes.get(n).getOrCreateStat(item), x + StatsScreen.this.getColumnX(n + ((StatsScreen)StatsScreen.this).itemStats.blockStatTypes.size()), y, index % 2 == 0);
                }
            }

            protected void render(MatrixStack matrixStack, @Nullable Stat<?> stat, int n, int n2, boolean bl) {
                String string = stat == null ? "-" : stat.format(StatsScreen.this.statHandler.getStat(stat));
                DrawableHelper.drawStringWithShadow(matrixStack, StatsScreen.this.textRenderer, string, n - StatsScreen.this.textRenderer.getWidth(string), n2 + 5, bl ? 0xFFFFFF : 0x909090);
            }
        }

        class ItemComparator
        implements Comparator<Item> {
            private ItemComparator() {
            }

            @Override
            public int compare(Item item, Item item2) {
                int _snowman3;
                int _snowman2;
                if (ItemStatsListWidget.this.selectedStatType == null) {
                    _snowman2 = 0;
                    _snowman3 = 0;
                } else if (ItemStatsListWidget.this.blockStatTypes.contains(ItemStatsListWidget.this.selectedStatType)) {
                    StatType<?> statType = ItemStatsListWidget.this.selectedStatType;
                    _snowman2 = item instanceof BlockItem ? StatsScreen.this.statHandler.getStat(statType, ((BlockItem)item).getBlock()) : -1;
                    _snowman3 = item2 instanceof BlockItem ? StatsScreen.this.statHandler.getStat(statType, ((BlockItem)item2).getBlock()) : -1;
                } else {
                    _snowman = ItemStatsListWidget.this.selectedStatType;
                    _snowman2 = StatsScreen.this.statHandler.getStat(_snowman, item);
                    _snowman3 = StatsScreen.this.statHandler.getStat(_snowman, item2);
                }
                if (_snowman2 == _snowman3) {
                    return ItemStatsListWidget.this.field_18760 * Integer.compare(Item.getRawId(item), Item.getRawId(item2));
                }
                return ItemStatsListWidget.this.field_18760 * Integer.compare(_snowman2, _snowman3);
            }

            @Override
            public /* synthetic */ int compare(Object object, Object object2) {
                return this.compare((Item)object, (Item)object2);
            }
        }
    }

    class GeneralStatsListWidget
    extends AlwaysSelectedEntryListWidget<Entry> {
        public GeneralStatsListWidget(MinecraftClient minecraftClient) {
            super(minecraftClient, StatsScreen.this.width, StatsScreen.this.height, 32, StatsScreen.this.height - 64, 10);
            ObjectArrayList objectArrayList = new ObjectArrayList(Stats.CUSTOM.iterator());
            objectArrayList.sort(Comparator.comparing(stat -> I18n.translate(StatsScreen.method_27027(stat), new Object[0])));
            for (Stat stat2 : objectArrayList) {
                this.addEntry(new Entry(stat2));
            }
        }

        @Override
        protected void renderBackground(MatrixStack matrices) {
            StatsScreen.this.renderBackground(matrices);
        }

        class Entry
        extends AlwaysSelectedEntryListWidget.Entry<Entry> {
            private final Stat<Identifier> stat;
            private final Text field_26547;

            private Entry(Stat<Identifier> stat) {
                this.stat = stat;
                this.field_26547 = new TranslatableText(StatsScreen.method_27027(stat));
            }

            @Override
            public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                DrawableHelper.drawTextWithShadow(matrices, StatsScreen.this.textRenderer, this.field_26547, x + 2, y + 1, index % 2 == 0 ? 0xFFFFFF : 0x909090);
                String string = this.stat.format(StatsScreen.this.statHandler.getStat(this.stat));
                DrawableHelper.drawStringWithShadow(matrices, StatsScreen.this.textRenderer, string, x + 2 + 213 - StatsScreen.this.textRenderer.getWidth(string), y + 1, index % 2 == 0 ? 0xFFFFFF : 0x909090);
            }
        }
    }
}

