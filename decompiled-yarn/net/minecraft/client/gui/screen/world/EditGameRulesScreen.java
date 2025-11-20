package net.minecraft.client.gui.screen.world;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;

public class EditGameRulesScreen extends Screen {
   private final Consumer<Optional<GameRules>> ruleSaver;
   private EditGameRulesScreen.RuleListWidget ruleListWidget;
   private final Set<EditGameRulesScreen.AbstractRuleWidget> invalidRuleWidgets = Sets.newHashSet();
   private ButtonWidget doneButton;
   @Nullable
   private List<OrderedText> tooltip;
   private final GameRules gameRules;

   public EditGameRulesScreen(GameRules gameRules, Consumer<Optional<GameRules>> ruleSaveConsumer) {
      super(new TranslatableText("editGamerule.title"));
      this.gameRules = gameRules;
      this.ruleSaver = ruleSaveConsumer;
   }

   @Override
   protected void init() {
      this.client.keyboard.setRepeatEvents(true);
      super.init();
      this.ruleListWidget = new EditGameRulesScreen.RuleListWidget(this.gameRules);
      this.children.add(this.ruleListWidget);
      this.addButton(new ButtonWidget(this.width / 2 - 155 + 160, this.height - 29, 150, 20, ScreenTexts.CANCEL, _snowman -> this.ruleSaver.accept(Optional.empty())));
      this.doneButton = this.addButton(
         new ButtonWidget(this.width / 2 - 155, this.height - 29, 150, 20, ScreenTexts.DONE, _snowman -> this.ruleSaver.accept(Optional.of(this.gameRules)))
      );
   }

   @Override
   public void removed() {
      this.client.keyboard.setRepeatEvents(false);
   }

   @Override
   public void onClose() {
      this.ruleSaver.accept(Optional.empty());
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.tooltip = null;
      this.ruleListWidget.render(matrices, mouseX, mouseY, delta);
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 16777215);
      super.render(matrices, mouseX, mouseY, delta);
      if (this.tooltip != null) {
         this.renderOrderedTooltip(matrices, this.tooltip, mouseX, mouseY);
      }
   }

   private void setTooltipDescription(@Nullable List<OrderedText> description) {
      this.tooltip = description;
   }

   private void updateDoneButton() {
      this.doneButton.active = this.invalidRuleWidgets.isEmpty();
   }

   private void markInvalid(EditGameRulesScreen.AbstractRuleWidget ruleWidget) {
      this.invalidRuleWidgets.add(ruleWidget);
      this.updateDoneButton();
   }

   private void markValid(EditGameRulesScreen.AbstractRuleWidget ruleWidget) {
      this.invalidRuleWidgets.remove(ruleWidget);
      this.updateDoneButton();
   }

   public abstract class AbstractRuleWidget extends ElementListWidget.Entry<EditGameRulesScreen.AbstractRuleWidget> {
      @Nullable
      private final List<OrderedText> description;

      public AbstractRuleWidget(List<OrderedText> description) {
         this.description = description;
      }
   }

   public class BooleanRuleWidget extends EditGameRulesScreen.NamedRuleWidget {
      private final ButtonWidget toggleButton;

      public BooleanRuleWidget(Text name, List<OrderedText> description, String ruleName, GameRules.BooleanRule _snowman) {
         super(description, name);
         this.toggleButton = new ButtonWidget(10, 5, 44, 20, ScreenTexts.getToggleText(_snowman.get()), _snowmanxxxx -> {
            boolean _snowmanxx = !_snowman.get();
            _snowman.set(_snowmanxx, null);
            _snowmanxxxx.setMessage(ScreenTexts.getToggleText(_snowman.get()));
         }) {
            @Override
            protected MutableText getNarrationMessage() {
               return ScreenTexts.composeToggleText(name, _snowman.get()).append("\n").append(ruleName);
            }
         };
         this.children.add(this.toggleButton);
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.drawName(matrices, y, x);
         this.toggleButton.x = x + entryWidth - 45;
         this.toggleButton.y = y;
         this.toggleButton.render(matrices, mouseX, mouseY, tickDelta);
      }
   }

   public class IntRuleWidget extends EditGameRulesScreen.NamedRuleWidget {
      private final TextFieldWidget valueWidget;

      public IntRuleWidget(Text name, List<OrderedText> description, String ruleName, GameRules.IntRule rule) {
         super(description, name);
         this.valueWidget = new TextFieldWidget(
            EditGameRulesScreen.this.client.textRenderer, 10, 5, 42, 20, name.shallowCopy().append("\n").append(ruleName).append("\n")
         );
         this.valueWidget.setText(Integer.toString(rule.get()));
         this.valueWidget.setChangedListener(_snowmanx -> {
            if (rule.validate(_snowmanx)) {
               this.valueWidget.setEditableColor(14737632);
               EditGameRulesScreen.this.markValid(this);
            } else {
               this.valueWidget.setEditableColor(16711680);
               EditGameRulesScreen.this.markInvalid(this);
            }
         });
         this.children.add(this.valueWidget);
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.drawName(matrices, y, x);
         this.valueWidget.x = x + entryWidth - 44;
         this.valueWidget.y = y;
         this.valueWidget.render(matrices, mouseX, mouseY, tickDelta);
      }
   }

   public abstract class NamedRuleWidget extends EditGameRulesScreen.AbstractRuleWidget {
      private final List<OrderedText> name;
      protected final List<Element> children = Lists.newArrayList();

      public NamedRuleWidget(List<OrderedText> description, @Nullable Text name) {
         super(description);
         this.name = EditGameRulesScreen.this.client.textRenderer.wrapLines(name, 175);
      }

      @Override
      public List<? extends Element> children() {
         return this.children;
      }

      protected void drawName(MatrixStack matrices, int x, int y) {
         if (this.name.size() == 1) {
            EditGameRulesScreen.this.client.textRenderer.draw(matrices, this.name.get(0), (float)y, (float)(x + 5), 16777215);
         } else if (this.name.size() >= 2) {
            EditGameRulesScreen.this.client.textRenderer.draw(matrices, this.name.get(0), (float)y, (float)x, 16777215);
            EditGameRulesScreen.this.client.textRenderer.draw(matrices, this.name.get(1), (float)y, (float)(x + 10), 16777215);
         }
      }
   }

   public class RuleCategoryWidget extends EditGameRulesScreen.AbstractRuleWidget {
      private final Text name;

      public RuleCategoryWidget(Text text) {
         super(null);
         this.name = text;
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         DrawableHelper.drawCenteredText(matrices, EditGameRulesScreen.this.client.textRenderer, this.name, x + entryWidth / 2, y + 5, 16777215);
      }

      @Override
      public List<? extends Element> children() {
         return ImmutableList.of();
      }
   }

   public class RuleListWidget extends ElementListWidget<EditGameRulesScreen.AbstractRuleWidget> {
      public RuleListWidget(GameRules _snowman) {
         super(EditGameRulesScreen.this.client, EditGameRulesScreen.this.width, EditGameRulesScreen.this.height, 43, EditGameRulesScreen.this.height - 32, 24);
         final Map<GameRules.Category, Map<GameRules.Key<?>, EditGameRulesScreen.AbstractRuleWidget>> _snowmanxx = Maps.newHashMap();
         GameRules.accept(new GameRules.Visitor() {
            @Override
            public void visitBoolean(GameRules.Key<GameRules.BooleanRule> key, GameRules.Type<GameRules.BooleanRule> type) {
               this.createRuleWidget(key, (_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxx) -> EditGameRulesScreen.this.new BooleanRuleWidget(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxx));
            }

            @Override
            public void visitInt(GameRules.Key<GameRules.IntRule> key, GameRules.Type<GameRules.IntRule> type) {
               this.createRuleWidget(key, (_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxx) -> EditGameRulesScreen.this.new IntRuleWidget(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxx));
            }

            private <T extends GameRules.Rule<T>> void createRuleWidget(GameRules.Key<T> key, EditGameRulesScreen.RuleWidgetFactory<T> widgetFactory) {
               Text _snowman = new TranslatableText(key.getTranslationKey());
               Text _snowmanx = new LiteralText(key.getName()).formatted(Formatting.YELLOW);
               T _snowmanxx = _snowman.get(key);
               String _snowmanxxx = _snowmanxx.serialize();
               Text _snowmanxxxx = new TranslatableText("editGamerule.default", new LiteralText(_snowmanxxx)).formatted(Formatting.GRAY);
               String _snowmanxxxxx = key.getTranslationKey() + ".description";
               List<OrderedText> _snowmanxxxxxx;
               String _snowmanxxxxxxx;
               if (I18n.hasTranslation(_snowmanxxxxx)) {
                  Builder<OrderedText> _snowmanxxxxxxxx = ImmutableList.builder().add(_snowmanx.asOrderedText());
                  Text _snowmanxxxxxxxxx = new TranslatableText(_snowmanxxxxx);
                  EditGameRulesScreen.this.textRenderer.wrapLines(_snowmanxxxxxxxxx, 150).forEach(_snowmanxxxxxxxx::add);
                  _snowmanxxxxxx = _snowmanxxxxxxxx.add(_snowmanxxxx.asOrderedText()).build();
                  _snowmanxxxxxxx = _snowmanxxxxxxxxx.getString() + "\n" + _snowmanxxxx.getString();
               } else {
                  _snowmanxxxxxx = ImmutableList.of(_snowmanx.asOrderedText(), _snowmanxxxx.asOrderedText());
                  _snowmanxxxxxxx = _snowmanxxxx.getString();
               }

               _snowman.computeIfAbsent(key.getCategory(), _snowmanxxxxxxxx -> Maps.newHashMap()).put(key, widgetFactory.create(_snowman, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxx));
            }
         });
         _snowmanxx.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(
               _snowmanxxx -> {
                  this.addEntry(
                     EditGameRulesScreen.this.new RuleCategoryWidget(
                        new TranslatableText(((GameRules.Category)_snowmanxxx.getKey()).getCategory()).formatted(new Formatting[]{Formatting.BOLD, Formatting.YELLOW})
                     )
                  );
                  ((Map)_snowmanxxx.getValue())
                     .entrySet()
                     .stream()
                     .sorted(Map.Entry.comparingByKey(Comparator.comparing(GameRules.Key::getName)))
                     .forEach(_snowmanxxxx -> this.addEntry((EntryListWidget.Entry)_snowmanxxxx.getValue()));
               }
            );
      }

      @Override
      public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
         super.render(matrices, mouseX, mouseY, delta);
         if (this.isMouseOver((double)mouseX, (double)mouseY)) {
            EditGameRulesScreen.AbstractRuleWidget _snowman = this.getEntryAtPosition((double)mouseX, (double)mouseY);
            if (_snowman != null) {
               EditGameRulesScreen.this.setTooltipDescription(_snowman.description);
            }
         }
      }
   }

   @FunctionalInterface
   interface RuleWidgetFactory<T extends GameRules.Rule<T>> {
      EditGameRulesScreen.AbstractRuleWidget create(Text name, List<OrderedText> description, String ruleName, T rule);
   }
}
