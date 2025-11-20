package net.minecraft.client.gui.screen;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.context.SuggestionContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.Rect2i;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;

public class CommandSuggestor {
   private static final Pattern BACKSLASH_S_PATTERN = Pattern.compile("(\\s+)");
   private static final Style field_25885 = Style.EMPTY.withColor(Formatting.RED);
   private static final Style field_25886 = Style.EMPTY.withColor(Formatting.GRAY);
   private static final List<Style> field_25887 = Stream.of(Formatting.AQUA, Formatting.YELLOW, Formatting.GREEN, Formatting.LIGHT_PURPLE, Formatting.GOLD)
      .map(Style.EMPTY::withColor)
      .collect(ImmutableList.toImmutableList());
   private final MinecraftClient client;
   private final Screen owner;
   private final TextFieldWidget textField;
   private final TextRenderer textRenderer;
   private final boolean slashOptional;
   private final boolean suggestingWhenEmpty;
   private final int inWindowIndexOffset;
   private final int maxSuggestionSize;
   private final boolean chatScreenSized;
   private final int color;
   private final List<OrderedText> messages = Lists.newArrayList();
   private int x;
   private int width;
   private ParseResults<CommandSource> parse;
   private CompletableFuture<Suggestions> pendingSuggestions;
   private CommandSuggestor.SuggestionWindow window;
   private boolean windowActive;
   private boolean completingSuggestions;

   public CommandSuggestor(
      MinecraftClient client,
      Screen owner,
      TextFieldWidget textField,
      TextRenderer textRenderer,
      boolean slashRequired,
      boolean suggestingWhenEmpty,
      int inWindowIndexOffset,
      int maxSuggestionSize,
      boolean chatScreenSized,
      int color
   ) {
      this.client = client;
      this.owner = owner;
      this.textField = textField;
      this.textRenderer = textRenderer;
      this.slashOptional = slashRequired;
      this.suggestingWhenEmpty = suggestingWhenEmpty;
      this.inWindowIndexOffset = inWindowIndexOffset;
      this.maxSuggestionSize = maxSuggestionSize;
      this.chatScreenSized = chatScreenSized;
      this.color = color;
      textField.setRenderTextProvider(this::provideRenderText);
   }

   public void setWindowActive(boolean windowActive) {
      this.windowActive = windowActive;
      if (!windowActive) {
         this.window = null;
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.window != null && this.window.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else if (this.owner.getFocused() == this.textField && keyCode == 258) {
         this.showSuggestions(true);
         return true;
      } else {
         return false;
      }
   }

   public boolean mouseScrolled(double amount) {
      return this.window != null && this.window.mouseScrolled(MathHelper.clamp(amount, -1.0, 1.0));
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      return this.window != null && this.window.mouseClicked((int)mouseX, (int)mouseY, button);
   }

   public void showSuggestions(boolean narrateFirstSuggestion) {
      if (this.pendingSuggestions != null && this.pendingSuggestions.isDone()) {
         Suggestions _snowman = this.pendingSuggestions.join();
         if (!_snowman.isEmpty()) {
            int _snowmanx = 0;

            for (Suggestion _snowmanxx : _snowman.getList()) {
               _snowmanx = Math.max(_snowmanx, this.textRenderer.getWidth(_snowmanxx.getText()));
            }

            int _snowmanxx = MathHelper.clamp(
               this.textField.getCharacterX(_snowman.getRange().getStart()), 0, this.textField.getCharacterX(0) + this.textField.getInnerWidth() - _snowmanx
            );
            int _snowmanxxx = this.chatScreenSized ? this.owner.height - 12 : 72;
            this.window = new CommandSuggestor.SuggestionWindow(_snowmanxx, _snowmanxxx, _snowmanx, this.method_30104(_snowman), narrateFirstSuggestion);
         }
      }
   }

   private List<Suggestion> method_30104(Suggestions _snowman) {
      String _snowmanx = this.textField.getText().substring(0, this.textField.getCursor());
      int _snowmanxx = getLastPlayerNameStart(_snowmanx);
      String _snowmanxxx = _snowmanx.substring(_snowmanxx).toLowerCase(Locale.ROOT);
      List<Suggestion> _snowmanxxxx = Lists.newArrayList();
      List<Suggestion> _snowmanxxxxx = Lists.newArrayList();

      for (Suggestion _snowmanxxxxxx : _snowman.getList()) {
         if (!_snowmanxxxxxx.getText().startsWith(_snowmanxxx) && !_snowmanxxxxxx.getText().startsWith("minecraft:" + _snowmanxxx)) {
            _snowmanxxxxx.add(_snowmanxxxxxx);
         } else {
            _snowmanxxxx.add(_snowmanxxxxxx);
         }
      }

      _snowmanxxxx.addAll(_snowmanxxxxx);
      return _snowmanxxxx;
   }

   public void refresh() {
      String _snowman = this.textField.getText();
      if (this.parse != null && !this.parse.getReader().getString().equals(_snowman)) {
         this.parse = null;
      }

      if (!this.completingSuggestions) {
         this.textField.setSuggestion(null);
         this.window = null;
      }

      this.messages.clear();
      StringReader _snowmanx = new StringReader(_snowman);
      boolean _snowmanxx = _snowmanx.canRead() && _snowmanx.peek() == '/';
      if (_snowmanxx) {
         _snowmanx.skip();
      }

      boolean _snowmanxxx = this.slashOptional || _snowmanxx;
      int _snowmanxxxx = this.textField.getCursor();
      if (_snowmanxxx) {
         CommandDispatcher<CommandSource> _snowmanxxxxx = this.client.player.networkHandler.getCommandDispatcher();
         if (this.parse == null) {
            this.parse = _snowmanxxxxx.parse(_snowmanx, this.client.player.networkHandler.getCommandSource());
         }

         int _snowmanxxxxxx = this.suggestingWhenEmpty ? _snowmanx.getCursor() : 1;
         if (_snowmanxxxx >= _snowmanxxxxxx && (this.window == null || !this.completingSuggestions)) {
            this.pendingSuggestions = _snowmanxxxxx.getCompletionSuggestions(this.parse, _snowmanxxxx);
            this.pendingSuggestions.thenRun(() -> {
               if (this.pendingSuggestions.isDone()) {
                  this.show();
               }
            });
         }
      } else {
         String _snowmanxxxxxx = _snowman.substring(0, _snowmanxxxx);
         int _snowmanxxxxxxx = getLastPlayerNameStart(_snowmanxxxxxx);
         Collection<String> _snowmanxxxxxxxx = this.client.player.networkHandler.getCommandSource().getPlayerNames();
         this.pendingSuggestions = CommandSource.suggestMatching(_snowmanxxxxxxxx, new SuggestionsBuilder(_snowmanxxxxxx, _snowmanxxxxxxx));
      }
   }

   private static int getLastPlayerNameStart(String input) {
      if (Strings.isNullOrEmpty(input)) {
         return 0;
      } else {
         int _snowman = 0;
         Matcher _snowmanx = BACKSLASH_S_PATTERN.matcher(input);

         while (_snowmanx.find()) {
            _snowman = _snowmanx.end();
         }

         return _snowman;
      }
   }

   private static OrderedText method_30505(CommandSyntaxException _snowman) {
      Text _snowmanx = Texts.toText(_snowman.getRawMessage());
      String _snowmanxx = _snowman.getContext();
      return _snowmanxx == null ? _snowmanx.asOrderedText() : new TranslatableText("command.context.parse_error", _snowmanx, _snowman.getCursor(), _snowmanxx).asOrderedText();
   }

   private void show() {
      if (this.textField.getCursor() == this.textField.getText().length()) {
         if (this.pendingSuggestions.join().isEmpty() && !this.parse.getExceptions().isEmpty()) {
            int _snowman = 0;

            for (Entry<CommandNode<CommandSource>, CommandSyntaxException> _snowmanx : this.parse.getExceptions().entrySet()) {
               CommandSyntaxException _snowmanxx = _snowmanx.getValue();
               if (_snowmanxx.getType() == CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect()) {
                  _snowman++;
               } else {
                  this.messages.add(method_30505(_snowmanxx));
               }
            }

            if (_snowman > 0) {
               this.messages.add(method_30505(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().create()));
            }
         } else if (this.parse.getReader().canRead()) {
            this.messages.add(method_30505(CommandManager.getException(this.parse)));
         }
      }

      this.x = 0;
      this.width = this.owner.width;
      if (this.messages.isEmpty()) {
         this.showUsages(Formatting.GRAY);
      }

      this.window = null;
      if (this.windowActive && this.client.options.autoSuggestions) {
         this.showSuggestions(false);
      }
   }

   private void showUsages(Formatting formatting) {
      CommandContextBuilder<CommandSource> _snowman = this.parse.getContext();
      SuggestionContext<CommandSource> _snowmanx = _snowman.findSuggestionContext(this.textField.getCursor());
      Map<CommandNode<CommandSource>, String> _snowmanxx = this.client
         .player
         .networkHandler
         .getCommandDispatcher()
         .getSmartUsage(_snowmanx.parent, this.client.player.networkHandler.getCommandSource());
      List<OrderedText> _snowmanxxx = Lists.newArrayList();
      int _snowmanxxxx = 0;
      Style _snowmanxxxxx = Style.EMPTY.withColor(formatting);

      for (Entry<CommandNode<CommandSource>, String> _snowmanxxxxxx : _snowmanxx.entrySet()) {
         if (!(_snowmanxxxxxx.getKey() instanceof LiteralCommandNode)) {
            _snowmanxxx.add(OrderedText.styledString(_snowmanxxxxxx.getValue(), _snowmanxxxxx));
            _snowmanxxxx = Math.max(_snowmanxxxx, this.textRenderer.getWidth(_snowmanxxxxxx.getValue()));
         }
      }

      if (!_snowmanxxx.isEmpty()) {
         this.messages.addAll(_snowmanxxx);
         this.x = MathHelper.clamp(this.textField.getCharacterX(_snowmanx.startPos), 0, this.textField.getCharacterX(0) + this.textField.getInnerWidth() - _snowmanxxxx);
         this.width = _snowmanxxxx;
      }
   }

   private OrderedText provideRenderText(String original, int firstCharacterIndex) {
      return this.parse != null ? highlight(this.parse, original, firstCharacterIndex) : OrderedText.styledString(original, Style.EMPTY);
   }

   @Nullable
   private static String getSuggestionSuffix(String original, String suggestion) {
      return suggestion.startsWith(original) ? suggestion.substring(original.length()) : null;
   }

   private static OrderedText highlight(ParseResults<CommandSource> parse, String original, int firstCharacterIndex) {
      List<OrderedText> _snowman = Lists.newArrayList();
      int _snowmanx = 0;
      int _snowmanxx = -1;
      CommandContextBuilder<CommandSource> _snowmanxxx = parse.getContext().getLastChild();

      for (ParsedArgument<CommandSource, ?> _snowmanxxxx : _snowmanxxx.getArguments().values()) {
         if (++_snowmanxx >= field_25887.size()) {
            _snowmanxx = 0;
         }

         int _snowmanxxxxx = Math.max(_snowmanxxxx.getRange().getStart() - firstCharacterIndex, 0);
         if (_snowmanxxxxx >= original.length()) {
            break;
         }

         int _snowmanxxxxxx = Math.min(_snowmanxxxx.getRange().getEnd() - firstCharacterIndex, original.length());
         if (_snowmanxxxxxx > 0) {
            _snowman.add(OrderedText.styledString(original.substring(_snowmanx, _snowmanxxxxx), field_25886));
            _snowman.add(OrderedText.styledString(original.substring(_snowmanxxxxx, _snowmanxxxxxx), field_25887.get(_snowmanxx)));
            _snowmanx = _snowmanxxxxxx;
         }
      }

      if (parse.getReader().canRead()) {
         int _snowmanxxxx = Math.max(parse.getReader().getCursor() - firstCharacterIndex, 0);
         if (_snowmanxxxx < original.length()) {
            int _snowmanxxxxxx = Math.min(_snowmanxxxx + parse.getReader().getRemainingLength(), original.length());
            _snowman.add(OrderedText.styledString(original.substring(_snowmanx, _snowmanxxxx), field_25886));
            _snowman.add(OrderedText.styledString(original.substring(_snowmanxxxx, _snowmanxxxxxx), field_25885));
            _snowmanx = _snowmanxxxxxx;
         }
      }

      _snowman.add(OrderedText.styledString(original.substring(_snowmanx), field_25886));
      return OrderedText.concat(_snowman);
   }

   public void render(MatrixStack _snowman, int _snowman, int _snowman) {
      if (this.window != null) {
         this.window.render(_snowman, _snowman, _snowman);
      } else {
         int _snowmanxxx = 0;

         for (OrderedText _snowmanxxxx : this.messages) {
            int _snowmanxxxxx = this.chatScreenSized ? this.owner.height - 14 - 13 - 12 * _snowmanxxx : 72 + 12 * _snowmanxxx;
            DrawableHelper.fill(_snowman, this.x - 1, _snowmanxxxxx, this.x + this.width + 1, _snowmanxxxxx + 12, this.color);
            this.textRenderer.drawWithShadow(_snowman, _snowmanxxxx, (float)this.x, (float)(_snowmanxxxxx + 2), -1);
            _snowmanxxx++;
         }
      }
   }

   public String method_23958() {
      return this.window != null ? "\n" + this.window.getNarration() : "";
   }

   public class SuggestionWindow {
      private final Rect2i area;
      private final String typedText;
      private final List<Suggestion> field_25709;
      private int inWindowIndex;
      private int selection;
      private Vec2f mouse = Vec2f.ZERO;
      private boolean completed;
      private int lastNarrationIndex;

      private SuggestionWindow(int x, int y, int width, List<Suggestion> _snowman, boolean narrateFirstSuggestion) {
         int _snowmanxx = x - 1;
         int _snowmanxxx = CommandSuggestor.this.chatScreenSized ? y - 3 - Math.min(_snowman.size(), CommandSuggestor.this.maxSuggestionSize) * 12 : y;
         this.area = new Rect2i(_snowmanxx, _snowmanxxx, width + 1, Math.min(_snowman.size(), CommandSuggestor.this.maxSuggestionSize) * 12);
         this.typedText = CommandSuggestor.this.textField.getText();
         this.lastNarrationIndex = narrateFirstSuggestion ? -1 : 0;
         this.field_25709 = _snowman;
         this.select(0);
      }

      public void render(MatrixStack _snowman, int _snowman, int _snowman) {
         int _snowmanxxx = Math.min(this.field_25709.size(), CommandSuggestor.this.maxSuggestionSize);
         int _snowmanxxxx = -5592406;
         boolean _snowmanxxxxx = this.inWindowIndex > 0;
         boolean _snowmanxxxxxx = this.field_25709.size() > this.inWindowIndex + _snowmanxxx;
         boolean _snowmanxxxxxxx = _snowmanxxxxx || _snowmanxxxxxx;
         boolean _snowmanxxxxxxxx = this.mouse.x != (float)_snowman || this.mouse.y != (float)_snowman;
         if (_snowmanxxxxxxxx) {
            this.mouse = new Vec2f((float)_snowman, (float)_snowman);
         }

         if (_snowmanxxxxxxx) {
            DrawableHelper.fill(
               _snowman, this.area.getX(), this.area.getY() - 1, this.area.getX() + this.area.getWidth(), this.area.getY(), CommandSuggestor.this.color
            );
            DrawableHelper.fill(
               _snowman,
               this.area.getX(),
               this.area.getY() + this.area.getHeight(),
               this.area.getX() + this.area.getWidth(),
               this.area.getY() + this.area.getHeight() + 1,
               CommandSuggestor.this.color
            );
            if (_snowmanxxxxx) {
               for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < this.area.getWidth(); _snowmanxxxxxxxxx++) {
                  if (_snowmanxxxxxxxxx % 2 == 0) {
                     DrawableHelper.fill(_snowman, this.area.getX() + _snowmanxxxxxxxxx, this.area.getY() - 1, this.area.getX() + _snowmanxxxxxxxxx + 1, this.area.getY(), -1);
                  }
               }
            }

            if (_snowmanxxxxxx) {
               for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < this.area.getWidth(); _snowmanxxxxxxxxxx++) {
                  if (_snowmanxxxxxxxxxx % 2 == 0) {
                     DrawableHelper.fill(
                        _snowman,
                        this.area.getX() + _snowmanxxxxxxxxxx,
                        this.area.getY() + this.area.getHeight(),
                        this.area.getX() + _snowmanxxxxxxxxxx + 1,
                        this.area.getY() + this.area.getHeight() + 1,
                        -1
                     );
                  }
               }
            }
         }

         boolean _snowmanxxxxxxxxxxx = false;

         for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < _snowmanxxx; _snowmanxxxxxxxxxxxx++) {
            Suggestion _snowmanxxxxxxxxxxxxx = this.field_25709.get(_snowmanxxxxxxxxxxxx + this.inWindowIndex);
            DrawableHelper.fill(
               _snowman,
               this.area.getX(),
               this.area.getY() + 12 * _snowmanxxxxxxxxxxxx,
               this.area.getX() + this.area.getWidth(),
               this.area.getY() + 12 * _snowmanxxxxxxxxxxxx + 12,
               CommandSuggestor.this.color
            );
            if (_snowman > this.area.getX()
               && _snowman < this.area.getX() + this.area.getWidth()
               && _snowman > this.area.getY() + 12 * _snowmanxxxxxxxxxxxx
               && _snowman < this.area.getY() + 12 * _snowmanxxxxxxxxxxxx + 12) {
               if (_snowmanxxxxxxxx) {
                  this.select(_snowmanxxxxxxxxxxxx + this.inWindowIndex);
               }

               _snowmanxxxxxxxxxxx = true;
            }

            CommandSuggestor.this.textRenderer
               .drawWithShadow(
                  _snowman,
                  _snowmanxxxxxxxxxxxxx.getText(),
                  (float)(this.area.getX() + 1),
                  (float)(this.area.getY() + 2 + 12 * _snowmanxxxxxxxxxxxx),
                  _snowmanxxxxxxxxxxxx + this.inWindowIndex == this.selection ? -256 : -5592406
               );
         }

         if (_snowmanxxxxxxxxxxx) {
            Message _snowmanxxxxxxxxxxxx = this.field_25709.get(this.selection).getTooltip();
            if (_snowmanxxxxxxxxxxxx != null) {
               CommandSuggestor.this.owner.renderTooltip(_snowman, Texts.toText(_snowmanxxxxxxxxxxxx), _snowman, _snowman);
            }
         }
      }

      public boolean mouseClicked(int x, int y, int button) {
         if (!this.area.contains(x, y)) {
            return false;
         } else {
            int _snowman = (y - this.area.getY()) / 12 + this.inWindowIndex;
            if (_snowman >= 0 && _snowman < this.field_25709.size()) {
               this.select(_snowman);
               this.complete();
            }

            return true;
         }
      }

      public boolean mouseScrolled(double amount) {
         int _snowman = (int)(
            CommandSuggestor.this.client.mouse.getX()
               * (double)CommandSuggestor.this.client.getWindow().getScaledWidth()
               / (double)CommandSuggestor.this.client.getWindow().getWidth()
         );
         int _snowmanx = (int)(
            CommandSuggestor.this.client.mouse.getY()
               * (double)CommandSuggestor.this.client.getWindow().getScaledHeight()
               / (double)CommandSuggestor.this.client.getWindow().getHeight()
         );
         if (this.area.contains(_snowman, _snowmanx)) {
            this.inWindowIndex = MathHelper.clamp(
               (int)((double)this.inWindowIndex - amount), 0, Math.max(this.field_25709.size() - CommandSuggestor.this.maxSuggestionSize, 0)
            );
            return true;
         } else {
            return false;
         }
      }

      public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
         if (keyCode == 265) {
            this.scroll(-1);
            this.completed = false;
            return true;
         } else if (keyCode == 264) {
            this.scroll(1);
            this.completed = false;
            return true;
         } else if (keyCode == 258) {
            if (this.completed) {
               this.scroll(Screen.hasShiftDown() ? -1 : 1);
            }

            this.complete();
            return true;
         } else if (keyCode == 256) {
            this.discard();
            return true;
         } else {
            return false;
         }
      }

      public void scroll(int offset) {
         this.select(this.selection + offset);
         int _snowman = this.inWindowIndex;
         int _snowmanx = this.inWindowIndex + CommandSuggestor.this.maxSuggestionSize - 1;
         if (this.selection < _snowman) {
            this.inWindowIndex = MathHelper.clamp(this.selection, 0, Math.max(this.field_25709.size() - CommandSuggestor.this.maxSuggestionSize, 0));
         } else if (this.selection > _snowmanx) {
            this.inWindowIndex = MathHelper.clamp(
               this.selection + CommandSuggestor.this.inWindowIndexOffset - CommandSuggestor.this.maxSuggestionSize,
               0,
               Math.max(this.field_25709.size() - CommandSuggestor.this.maxSuggestionSize, 0)
            );
         }
      }

      public void select(int index) {
         this.selection = index;
         if (this.selection < 0) {
            this.selection = this.selection + this.field_25709.size();
         }

         if (this.selection >= this.field_25709.size()) {
            this.selection = this.selection - this.field_25709.size();
         }

         Suggestion _snowman = this.field_25709.get(this.selection);
         CommandSuggestor.this.textField
            .setSuggestion(CommandSuggestor.getSuggestionSuffix(CommandSuggestor.this.textField.getText(), _snowman.apply(this.typedText)));
         if (NarratorManager.INSTANCE.isActive() && this.lastNarrationIndex != this.selection) {
            NarratorManager.INSTANCE.narrate(this.getNarration());
         }
      }

      public void complete() {
         Suggestion _snowman = this.field_25709.get(this.selection);
         CommandSuggestor.this.completingSuggestions = true;
         CommandSuggestor.this.textField.setText(_snowman.apply(this.typedText));
         int _snowmanx = _snowman.getRange().getStart() + _snowman.getText().length();
         CommandSuggestor.this.textField.setSelectionStart(_snowmanx);
         CommandSuggestor.this.textField.setSelectionEnd(_snowmanx);
         this.select(this.selection);
         CommandSuggestor.this.completingSuggestions = false;
         this.completed = true;
      }

      private String getNarration() {
         this.lastNarrationIndex = this.selection;
         Suggestion _snowman = this.field_25709.get(this.selection);
         Message _snowmanx = _snowman.getTooltip();
         return _snowmanx != null
            ? I18n.translate("narration.suggestion.tooltip", this.selection + 1, this.field_25709.size(), _snowman.getText(), _snowmanx.getString())
            : I18n.translate("narration.suggestion", this.selection + 1, this.field_25709.size(), _snowman.getText());
      }

      public void discard() {
         CommandSuggestor.this.window = null;
      }
   }
}
