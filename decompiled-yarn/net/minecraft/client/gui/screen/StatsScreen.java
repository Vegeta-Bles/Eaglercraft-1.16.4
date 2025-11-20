package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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

public class StatsScreen extends Screen implements StatsListener {
   private static final Text field_26546 = new TranslatableText("multiplayer.downloadingStats");
   protected final Screen parent;
   private StatsScreen.GeneralStatsListWidget generalStats;
   private StatsScreen.ItemStatsListWidget itemStats;
   private StatsScreen.EntityStatsListWidget mobStats;
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
      this.generalStats = new StatsScreen.GeneralStatsListWidget(this.client);
      this.itemStats = new StatsScreen.ItemStatsListWidget(this.client);
      this.mobStats = new StatsScreen.EntityStatsListWidget(this.client);
   }

   public void createButtons() {
      this.addButton(
         new ButtonWidget(
            this.width / 2 - 120, this.height - 52, 80, 20, new TranslatableText("stat.generalButton"), _snowman -> this.selectStatList(this.generalStats)
         )
      );
      ButtonWidget _snowman = this.addButton(
         new ButtonWidget(this.width / 2 - 40, this.height - 52, 80, 20, new TranslatableText("stat.itemsButton"), _snowmanx -> this.selectStatList(this.itemStats))
      );
      ButtonWidget _snowmanx = this.addButton(
         new ButtonWidget(this.width / 2 + 40, this.height - 52, 80, 20, new TranslatableText("stat.mobsButton"), _snowmanxx -> this.selectStatList(this.mobStats))
      );
      this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 28, 200, 20, ScreenTexts.DONE, _snowmanxx -> this.client.openScreen(this.parent)));
      if (this.itemStats.children().isEmpty()) {
         _snowman.active = false;
      }

      if (this.mobStats.children().isEmpty()) {
         _snowmanx.active = false;
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      if (this.downloadingStats) {
         this.renderBackground(matrices);
         drawCenteredText(matrices, this.textRenderer, field_26546, this.width / 2, this.height / 2, 16777215);
         drawCenteredString(
            matrices,
            this.textRenderer,
            PROGRESS_BAR_STAGES[(int)(Util.getMeasuringTimeMs() / 150L % (long)PROGRESS_BAR_STAGES.length)],
            this.width / 2,
            this.height / 2 + 9 * 2,
            16777215
         );
      } else {
         this.getSelectedStatList().render(matrices, mouseX, mouseY, delta);
         drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 16777215);
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

   private static String method_27027(Stat<Identifier> _snowman) {
      return "stat." + _snowman.getValue().toString().replace(':', '.');
   }

   private int getColumnX(int index) {
      return 115 + 40 * index;
   }

   private void renderStatItem(MatrixStack _snowman, int _snowman, int _snowman, Item _snowman) {
      this.renderIcon(_snowman, _snowman + 1, _snowman + 1, 0, 0);
      RenderSystem.enableRescaleNormal();
      this.itemRenderer.renderGuiItemIcon(_snowman.getDefaultStack(), _snowman + 2, _snowman + 2);
      RenderSystem.disableRescaleNormal();
   }

   private void renderIcon(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(STATS_ICON_TEXTURE);
      drawTexture(_snowman, _snowman, _snowman, this.getZOffset(), (float)_snowman, (float)_snowman, 18, 18, 128, 128);
   }

   class EntityStatsListWidget extends AlwaysSelectedEntryListWidget<StatsScreen.EntityStatsListWidget.Entry> {
      public EntityStatsListWidget(MinecraftClient var2) {
         super(_snowman, StatsScreen.this.width, StatsScreen.this.height, 32, StatsScreen.this.height - 64, 9 * 4);

         for (EntityType<?> _snowman : Registry.ENTITY_TYPE) {
            if (StatsScreen.this.statHandler.getStat(Stats.KILLED.getOrCreateStat(_snowman)) > 0
               || StatsScreen.this.statHandler.getStat(Stats.KILLED_BY.getOrCreateStat(_snowman)) > 0) {
               this.addEntry(new StatsScreen.EntityStatsListWidget.Entry(_snowman));
            }
         }
      }

      @Override
      protected void renderBackground(MatrixStack matrices) {
         StatsScreen.this.renderBackground(matrices);
      }

      class Entry extends AlwaysSelectedEntryListWidget.Entry<StatsScreen.EntityStatsListWidget.Entry> {
         private final EntityType<?> entityType;
         private final Text field_26548;
         private final Text field_26549;
         private final boolean field_26550;
         private final Text field_26551;
         private final boolean field_26552;

         public Entry(EntityType<?> var2) {
            this.entityType = _snowman;
            this.field_26548 = _snowman.getName();
            int _snowman = StatsScreen.this.statHandler.getStat(Stats.KILLED.getOrCreateStat(_snowman));
            if (_snowman == 0) {
               this.field_26549 = new TranslatableText("stat_type.minecraft.killed.none", this.field_26548);
               this.field_26550 = false;
            } else {
               this.field_26549 = new TranslatableText("stat_type.minecraft.killed", _snowman, this.field_26548);
               this.field_26550 = true;
            }

            int _snowmanx = StatsScreen.this.statHandler.getStat(Stats.KILLED_BY.getOrCreateStat(_snowman));
            if (_snowmanx == 0) {
               this.field_26551 = new TranslatableText("stat_type.minecraft.killed_by.none", this.field_26548);
               this.field_26552 = false;
            } else {
               this.field_26551 = new TranslatableText("stat_type.minecraft.killed_by", this.field_26548, _snowmanx);
               this.field_26552 = true;
            }
         }

         @Override
         public void render(
            MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
         ) {
            DrawableHelper.drawTextWithShadow(matrices, StatsScreen.this.textRenderer, this.field_26548, x + 2, y + 1, 16777215);
            DrawableHelper.drawTextWithShadow(
               matrices, StatsScreen.this.textRenderer, this.field_26549, x + 2 + 10, y + 1 + 9, this.field_26550 ? 9474192 : 6316128
            );
            DrawableHelper.drawTextWithShadow(
               matrices, StatsScreen.this.textRenderer, this.field_26551, x + 2 + 10, y + 1 + 9 * 2, this.field_26552 ? 9474192 : 6316128
            );
         }
      }
   }

   class GeneralStatsListWidget extends AlwaysSelectedEntryListWidget<StatsScreen.GeneralStatsListWidget.Entry> {
      public GeneralStatsListWidget(MinecraftClient var2) {
         super(_snowman, StatsScreen.this.width, StatsScreen.this.height, 32, StatsScreen.this.height - 64, 10);
         ObjectArrayList<Stat<Identifier>> _snowman = new ObjectArrayList(Stats.CUSTOM.iterator());
         _snowman.sort(Comparator.comparing(_snowmanx -> I18n.translate(StatsScreen.method_27027((Stat<Identifier>)_snowmanx))));
         ObjectListIterator var4 = _snowman.iterator();

         while (var4.hasNext()) {
            Stat<Identifier> _snowmanx = (Stat<Identifier>)var4.next();
            this.addEntry(new StatsScreen.GeneralStatsListWidget.Entry(_snowmanx));
         }
      }

      @Override
      protected void renderBackground(MatrixStack matrices) {
         StatsScreen.this.renderBackground(matrices);
      }

      class Entry extends AlwaysSelectedEntryListWidget.Entry<StatsScreen.GeneralStatsListWidget.Entry> {
         private final Stat<Identifier> stat;
         private final Text field_26547;

         private Entry(Stat<Identifier> stat) {
            this.stat = stat;
            this.field_26547 = new TranslatableText(StatsScreen.method_27027(stat));
         }

         @Override
         public void render(
            MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
         ) {
            DrawableHelper.drawTextWithShadow(matrices, StatsScreen.this.textRenderer, this.field_26547, x + 2, y + 1, index % 2 == 0 ? 16777215 : 9474192);
            String _snowman = this.stat.format(StatsScreen.this.statHandler.getStat(this.stat));
            DrawableHelper.drawStringWithShadow(
               matrices, StatsScreen.this.textRenderer, _snowman, x + 2 + 213 - StatsScreen.this.textRenderer.getWidth(_snowman), y + 1, index % 2 == 0 ? 16777215 : 9474192
            );
         }
      }
   }

   class ItemStatsListWidget extends AlwaysSelectedEntryListWidget<StatsScreen.ItemStatsListWidget.Entry> {
      protected final List<StatType<Block>> blockStatTypes;
      protected final List<StatType<Item>> itemStatTypes;
      private final int[] HEADER_ICON_SPRITE_INDICES = new int[]{3, 4, 1, 2, 5, 6};
      protected int selectedHeaderColumn = -1;
      protected final List<Item> items;
      protected final Comparator<Item> comparator = new StatsScreen.ItemStatsListWidget.ItemComparator();
      @Nullable
      protected StatType<?> selectedStatType;
      protected int field_18760;

      public ItemStatsListWidget(MinecraftClient client) {
         super(client, StatsScreen.this.width, StatsScreen.this.height, 32, StatsScreen.this.height - 64, 20);
         this.blockStatTypes = Lists.newArrayList();
         this.blockStatTypes.add(Stats.MINED);
         this.itemStatTypes = Lists.newArrayList(new StatType[]{Stats.BROKEN, Stats.CRAFTED, Stats.USED, Stats.PICKED_UP, Stats.DROPPED});
         this.setRenderHeader(true, 20);
         Set<Item> _snowman = Sets.newIdentityHashSet();

         for (Item _snowmanx : Registry.ITEM) {
            boolean _snowmanxx = false;

            for (StatType<Item> _snowmanxxx : this.itemStatTypes) {
               if (_snowmanxxx.hasStat(_snowmanx) && StatsScreen.this.statHandler.getStat(_snowmanxxx.getOrCreateStat(_snowmanx)) > 0) {
                  _snowmanxx = true;
               }
            }

            if (_snowmanxx) {
               _snowman.add(_snowmanx);
            }
         }

         for (Block _snowmanx : Registry.BLOCK) {
            boolean _snowmanxx = false;

            for (StatType<Block> _snowmanxxxx : this.blockStatTypes) {
               if (_snowmanxxxx.hasStat(_snowmanx) && StatsScreen.this.statHandler.getStat(_snowmanxxxx.getOrCreateStat(_snowmanx)) > 0) {
                  _snowmanxx = true;
               }
            }

            if (_snowmanxx) {
               _snowman.add(_snowmanx.asItem());
            }
         }

         _snowman.remove(Items.AIR);
         this.items = Lists.newArrayList(_snowman);

         for (int _snowmanx = 0; _snowmanx < this.items.size(); _snowmanx++) {
            this.addEntry(new StatsScreen.ItemStatsListWidget.Entry());
         }
      }

      @Override
      protected void renderHeader(MatrixStack matrices, int x, int y, Tessellator _snowman) {
         if (!this.client.mouse.wasLeftButtonClicked()) {
            this.selectedHeaderColumn = -1;
         }

         for (int _snowmanx = 0; _snowmanx < this.HEADER_ICON_SPRITE_INDICES.length; _snowmanx++) {
            StatsScreen.this.renderIcon(matrices, x + StatsScreen.this.getColumnX(_snowmanx) - 18, y + 1, 0, this.selectedHeaderColumn == _snowmanx ? 0 : 18);
         }

         if (this.selectedStatType != null) {
            int _snowmanx = StatsScreen.this.getColumnX(this.getHeaderIndex(this.selectedStatType)) - 36;
            int _snowmanxx = this.field_18760 == 1 ? 2 : 1;
            StatsScreen.this.renderIcon(matrices, x + _snowmanx, y + 1, 18 * _snowmanxx, 0);
         }

         for (int _snowmanx = 0; _snowmanx < this.HEADER_ICON_SPRITE_INDICES.length; _snowmanx++) {
            int _snowmanxx = this.selectedHeaderColumn == _snowmanx ? 1 : 0;
            StatsScreen.this.renderIcon(matrices, x + StatsScreen.this.getColumnX(_snowmanx) - 18 + _snowmanxx, y + 1 + _snowmanxx, 18 * this.HEADER_ICON_SPRITE_INDICES[_snowmanx], 18);
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

         for (int _snowman = 0; _snowman < this.HEADER_ICON_SPRITE_INDICES.length; _snowman++) {
            int _snowmanx = x - StatsScreen.this.getColumnX(_snowman);
            if (_snowmanx >= -36 && _snowmanx <= 0) {
               this.selectedHeaderColumn = _snowman;
               break;
            }
         }

         if (this.selectedHeaderColumn >= 0) {
            this.selectStatType(this.getStatType(this.selectedHeaderColumn));
            this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         }
      }

      private StatType<?> getStatType(int headerColumn) {
         return headerColumn < this.blockStatTypes.size()
            ? this.blockStatTypes.get(headerColumn)
            : this.itemStatTypes.get(headerColumn - this.blockStatTypes.size());
      }

      private int getHeaderIndex(StatType<?> statType) {
         int _snowman = this.blockStatTypes.indexOf(statType);
         if (_snowman >= 0) {
            return _snowman;
         } else {
            int _snowmanx = this.itemStatTypes.indexOf(statType);
            return _snowmanx >= 0 ? _snowmanx + this.blockStatTypes.size() : -1;
         }
      }

      @Override
      protected void renderDecorations(MatrixStack _snowman, int _snowman, int _snowman) {
         if (_snowman >= this.top && _snowman <= this.bottom) {
            StatsScreen.ItemStatsListWidget.Entry _snowmanxxx = this.getEntryAtPosition((double)_snowman, (double)_snowman);
            int _snowmanxxxx = (this.width - this.getRowWidth()) / 2;
            if (_snowmanxxx != null) {
               if (_snowman < _snowmanxxxx + 40 || _snowman > _snowmanxxxx + 40 + 20) {
                  return;
               }

               Item _snowmanxxxxx = this.items.get(this.children().indexOf(_snowmanxxx));
               this.render(_snowman, this.getText(_snowmanxxxxx), _snowman, _snowman);
            } else {
               Text _snowmanxxxxx = null;
               int _snowmanxxxxxx = _snowman - _snowmanxxxx;

               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < this.HEADER_ICON_SPRITE_INDICES.length; _snowmanxxxxxxx++) {
                  int _snowmanxxxxxxxx = StatsScreen.this.getColumnX(_snowmanxxxxxxx);
                  if (_snowmanxxxxxx >= _snowmanxxxxxxxx - 18 && _snowmanxxxxxx <= _snowmanxxxxxxxx) {
                     _snowmanxxxxx = this.getStatType(_snowmanxxxxxxx).method_30739();
                     break;
                  }
               }

               this.render(_snowman, _snowmanxxxxx, _snowman, _snowman);
            }
         }
      }

      protected void render(MatrixStack _snowman, @Nullable Text _snowman, int _snowman, int _snowman) {
         if (_snowman != null) {
            int _snowmanxxxx = _snowman + 12;
            int _snowmanxxxxx = _snowman - 12;
            int _snowmanxxxxxx = StatsScreen.this.textRenderer.getWidth(_snowman);
            this.fillGradient(_snowman, _snowmanxxxx - 3, _snowmanxxxxx - 3, _snowmanxxxx + _snowmanxxxxxx + 3, _snowmanxxxxx + 8 + 3, -1073741824, -1073741824);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 400.0F);
            StatsScreen.this.textRenderer.drawWithShadow(_snowman, _snowman, (float)_snowmanxxxx, (float)_snowmanxxxxx, -1);
            RenderSystem.popMatrix();
         }
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

      class Entry extends AlwaysSelectedEntryListWidget.Entry<StatsScreen.ItemStatsListWidget.Entry> {
         private Entry() {
         }

         @Override
         public void render(
            MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
         ) {
            Item _snowman = StatsScreen.this.itemStats.items.get(index);
            StatsScreen.this.renderStatItem(matrices, x + 40, y, _snowman);

            for (int _snowmanx = 0; _snowmanx < StatsScreen.this.itemStats.blockStatTypes.size(); _snowmanx++) {
               Stat<Block> _snowmanxx;
               if (_snowman instanceof BlockItem) {
                  _snowmanxx = StatsScreen.this.itemStats.blockStatTypes.get(_snowmanx).getOrCreateStat(((BlockItem)_snowman).getBlock());
               } else {
                  _snowmanxx = null;
               }

               this.render(matrices, _snowmanxx, x + StatsScreen.this.getColumnX(_snowmanx), y, index % 2 == 0);
            }

            for (int _snowmanx = 0; _snowmanx < StatsScreen.this.itemStats.itemStatTypes.size(); _snowmanx++) {
               this.render(
                  matrices,
                  StatsScreen.this.itemStats.itemStatTypes.get(_snowmanx).getOrCreateStat(_snowman),
                  x + StatsScreen.this.getColumnX(_snowmanx + StatsScreen.this.itemStats.blockStatTypes.size()),
                  y,
                  index % 2 == 0
               );
            }
         }

         protected void render(MatrixStack _snowman, @Nullable Stat<?> _snowman, int _snowman, int _snowman, boolean _snowman) {
            String _snowmanxxxxx = _snowman == null ? "-" : _snowman.format(StatsScreen.this.statHandler.getStat(_snowman));
            DrawableHelper.drawStringWithShadow(
               _snowman, StatsScreen.this.textRenderer, _snowmanxxxxx, _snowman - StatsScreen.this.textRenderer.getWidth(_snowmanxxxxx), _snowman + 5, _snowman ? 16777215 : 9474192
            );
         }
      }

      class ItemComparator implements Comparator<Item> {
         private ItemComparator() {
         }

         public int compare(Item _snowman, Item _snowman) {
            int _snowmanxx;
            int _snowmanxxx;
            if (ItemStatsListWidget.this.selectedStatType == null) {
               _snowmanxx = 0;
               _snowmanxxx = 0;
            } else if (ItemStatsListWidget.this.blockStatTypes.contains(ItemStatsListWidget.this.selectedStatType)) {
               StatType<Block> _snowmanxxxx = (StatType<Block>)ItemStatsListWidget.this.selectedStatType;
               _snowmanxx = _snowman instanceof BlockItem ? StatsScreen.this.statHandler.getStat(_snowmanxxxx, ((BlockItem)_snowman).getBlock()) : -1;
               _snowmanxxx = _snowman instanceof BlockItem ? StatsScreen.this.statHandler.getStat(_snowmanxxxx, ((BlockItem)_snowman).getBlock()) : -1;
            } else {
               StatType<Item> _snowmanxxxx = (StatType<Item>)ItemStatsListWidget.this.selectedStatType;
               _snowmanxx = StatsScreen.this.statHandler.getStat(_snowmanxxxx, _snowman);
               _snowmanxxx = StatsScreen.this.statHandler.getStat(_snowmanxxxx, _snowman);
            }

            return _snowmanxx == _snowmanxxx
               ? ItemStatsListWidget.this.field_18760 * Integer.compare(Item.getRawId(_snowman), Item.getRawId(_snowman))
               : ItemStatsListWidget.this.field_18760 * Integer.compare(_snowmanxx, _snowmanxxx);
         }
      }
   }
}
