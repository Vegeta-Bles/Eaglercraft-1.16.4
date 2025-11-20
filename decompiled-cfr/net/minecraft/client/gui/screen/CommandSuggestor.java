/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.context.CommandContextBuilder
 *  com.mojang.brigadier.context.ParsedArgument
 *  com.mojang.brigadier.context.SuggestionContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.suggestion.Suggestion
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  com.mojang.brigadier.tree.LiteralCommandNode
 *  javax.annotation.Nullable
 */
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
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
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
    private static final List<Style> field_25887 = (List)Stream.of(Formatting.AQUA, Formatting.YELLOW, Formatting.GREEN, Formatting.LIGHT_PURPLE, Formatting.GOLD).map(Style.EMPTY::withColor).collect(ImmutableList.toImmutableList());
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
    private SuggestionWindow window;
    private boolean windowActive;
    private boolean completingSuggestions;

    public CommandSuggestor(MinecraftClient client, Screen owner, TextFieldWidget textField, TextRenderer textRenderer, boolean slashRequired, boolean suggestingWhenEmpty, int inWindowIndexOffset, int maxSuggestionSize, boolean chatScreenSized, int color) {
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
        }
        if (this.owner.getFocused() == this.textField && keyCode == 258) {
            this.showSuggestions(true);
            return true;
        }
        return false;
    }

    public boolean mouseScrolled(double amount) {
        return this.window != null && this.window.mouseScrolled(MathHelper.clamp(amount, -1.0, 1.0));
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return this.window != null && this.window.mouseClicked((int)mouseX, (int)mouseY, button);
    }

    public void showSuggestions(boolean narrateFirstSuggestion) {
        Suggestions suggestions;
        if (this.pendingSuggestions != null && this.pendingSuggestions.isDone() && !(suggestions = this.pendingSuggestions.join()).isEmpty()) {
            int n = 0;
            for (Suggestion suggestion : suggestions.getList()) {
                n = Math.max(n, this.textRenderer.getWidth(suggestion.getText()));
            }
            int _snowman2 = MathHelper.clamp(this.textField.getCharacterX(suggestions.getRange().getStart()), 0, this.textField.getCharacterX(0) + this.textField.getInnerWidth() - n);
            int _snowman3 = this.chatScreenSized ? this.owner.height - 12 : 72;
            this.window = new SuggestionWindow(_snowman2, _snowman3, n, this.method_30104(suggestions), narrateFirstSuggestion);
        }
    }

    private List<Suggestion> method_30104(Suggestions suggestions) {
        String string = this.textField.getText().substring(0, this.textField.getCursor());
        int _snowman2 = CommandSuggestor.getLastPlayerNameStart(string);
        _snowman = string.substring(_snowman2).toLowerCase(Locale.ROOT);
        ArrayList _snowman3 = Lists.newArrayList();
        ArrayList _snowman4 = Lists.newArrayList();
        for (Suggestion suggestion : suggestions.getList()) {
            if (suggestion.getText().startsWith(_snowman) || suggestion.getText().startsWith("minecraft:" + _snowman)) {
                _snowman3.add(suggestion);
                continue;
            }
            _snowman4.add(suggestion);
        }
        _snowman3.addAll(_snowman4);
        return _snowman3;
    }

    public void refresh() {
        String string = this.textField.getText();
        if (this.parse != null && !this.parse.getReader().getString().equals(string)) {
            this.parse = null;
        }
        if (!this.completingSuggestions) {
            this.textField.setSuggestion(null);
            this.window = null;
        }
        this.messages.clear();
        StringReader _snowman2 = new StringReader(string);
        boolean bl = _snowman = _snowman2.canRead() && _snowman2.peek() == '/';
        if (_snowman) {
            _snowman2.skip();
        }
        boolean _snowman3 = this.slashOptional || _snowman;
        int _snowman4 = this.textField.getCursor();
        if (_snowman3) {
            CommandDispatcher<CommandSource> commandDispatcher = this.client.player.networkHandler.getCommandDispatcher();
            if (this.parse == null) {
                this.parse = commandDispatcher.parse(_snowman2, (Object)this.client.player.networkHandler.getCommandSource());
            }
            int n = _snowman = this.suggestingWhenEmpty ? _snowman2.getCursor() : 1;
            if (!(_snowman4 < _snowman || this.window != null && this.completingSuggestions)) {
                this.pendingSuggestions = commandDispatcher.getCompletionSuggestions(this.parse, _snowman4);
                this.pendingSuggestions.thenRun(() -> {
                    if (!this.pendingSuggestions.isDone()) {
                        return;
                    }
                    this.show();
                });
            }
        } else {
            _snowman = string.substring(0, _snowman4);
            int _snowman5 = CommandSuggestor.getLastPlayerNameStart(_snowman);
            Collection<String> _snowman6 = this.client.player.networkHandler.getCommandSource().getPlayerNames();
            this.pendingSuggestions = CommandSource.suggestMatching(_snowman6, new SuggestionsBuilder(_snowman, _snowman5));
        }
    }

    private static int getLastPlayerNameStart(String input) {
        if (Strings.isNullOrEmpty((String)input)) {
            return 0;
        }
        int n = 0;
        Matcher _snowman2 = BACKSLASH_S_PATTERN.matcher(input);
        while (_snowman2.find()) {
            n = _snowman2.end();
        }
        return n;
    }

    private static OrderedText method_30505(CommandSyntaxException commandSyntaxException) {
        Text text = Texts.toText(commandSyntaxException.getRawMessage());
        String _snowman2 = commandSyntaxException.getContext();
        if (_snowman2 == null) {
            return text.asOrderedText();
        }
        return new TranslatableText("command.context.parse_error", text, commandSyntaxException.getCursor(), _snowman2).asOrderedText();
    }

    private void show() {
        if (this.textField.getCursor() == this.textField.getText().length()) {
            if (this.pendingSuggestions.join().isEmpty() && !this.parse.getExceptions().isEmpty()) {
                int n = 0;
                for (Map.Entry entry : this.parse.getExceptions().entrySet()) {
                    CommandSyntaxException commandSyntaxException = (CommandSyntaxException)((Object)entry.getValue());
                    if (commandSyntaxException.getType() == CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect()) {
                        ++n;
                        continue;
                    }
                    this.messages.add(CommandSuggestor.method_30505(commandSyntaxException));
                }
                if (n > 0) {
                    this.messages.add(CommandSuggestor.method_30505(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().create()));
                }
            } else if (this.parse.getReader().canRead()) {
                this.messages.add(CommandSuggestor.method_30505(CommandManager.getException(this.parse)));
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
        CommandContextBuilder commandContextBuilder = this.parse.getContext();
        SuggestionContext _snowman2 = commandContextBuilder.findSuggestionContext(this.textField.getCursor());
        Map _snowman3 = this.client.player.networkHandler.getCommandDispatcher().getSmartUsage(_snowman2.parent, (Object)this.client.player.networkHandler.getCommandSource());
        ArrayList _snowman4 = Lists.newArrayList();
        int _snowman5 = 0;
        Style _snowman6 = Style.EMPTY.withColor(formatting);
        for (Map.Entry entry : _snowman3.entrySet()) {
            if (entry.getKey() instanceof LiteralCommandNode) continue;
            _snowman4.add(OrderedText.styledString((String)entry.getValue(), _snowman6));
            _snowman5 = Math.max(_snowman5, this.textRenderer.getWidth((String)entry.getValue()));
        }
        if (!_snowman4.isEmpty()) {
            this.messages.addAll(_snowman4);
            this.x = MathHelper.clamp(this.textField.getCharacterX(_snowman2.startPos), 0, this.textField.getCharacterX(0) + this.textField.getInnerWidth() - _snowman5);
            this.width = _snowman5;
        }
    }

    private OrderedText provideRenderText(String original, int firstCharacterIndex) {
        if (this.parse != null) {
            return CommandSuggestor.highlight(this.parse, original, firstCharacterIndex);
        }
        return OrderedText.styledString(original, Style.EMPTY);
    }

    @Nullable
    private static String getSuggestionSuffix(String original, String suggestion) {
        if (suggestion.startsWith(original)) {
            return suggestion.substring(original.length());
        }
        return null;
    }

    private static OrderedText highlight(ParseResults<CommandSource> parse, String original, int firstCharacterIndex) {
        int n;
        ArrayList arrayList = Lists.newArrayList();
        int _snowman2 = 0;
        int _snowman3 = -1;
        CommandContextBuilder _snowman4 = parse.getContext().getLastChild();
        for (ParsedArgument parsedArgument : _snowman4.getArguments().values()) {
            if (++_snowman3 >= field_25887.size()) {
                _snowman3 = 0;
            }
            if ((_snowman = Math.max(parsedArgument.getRange().getStart() - firstCharacterIndex, 0)) >= original.length()) break;
            int n2 = Math.min(parsedArgument.getRange().getEnd() - firstCharacterIndex, original.length());
            if (n2 <= 0) continue;
            arrayList.add(OrderedText.styledString(original.substring(_snowman2, _snowman), field_25886));
            arrayList.add(OrderedText.styledString(original.substring(_snowman, n2), field_25887.get(_snowman3)));
            _snowman2 = n2;
        }
        if (parse.getReader().canRead() && (n = Math.max(parse.getReader().getCursor() - firstCharacterIndex, 0)) < original.length()) {
            _snowman = Math.min(n + parse.getReader().getRemainingLength(), original.length());
            arrayList.add(OrderedText.styledString(original.substring(_snowman2, n), field_25886));
            arrayList.add(OrderedText.styledString(original.substring(n, _snowman), field_25885));
            _snowman2 = _snowman;
        }
        arrayList.add(OrderedText.styledString(original.substring(_snowman2), field_25886));
        return OrderedText.concat(arrayList);
    }

    public void render(MatrixStack matrixStack, int n, int n2) {
        if (this.window != null) {
            this.window.render(matrixStack, n, n2);
        } else {
            _snowman = 0;
            for (OrderedText orderedText : this.messages) {
                int n3 = this.chatScreenSized ? this.owner.height - 14 - 13 - 12 * _snowman : 72 + 12 * _snowman;
                DrawableHelper.fill(matrixStack, this.x - 1, n3, this.x + this.width + 1, n3 + 12, this.color);
                this.textRenderer.drawWithShadow(matrixStack, orderedText, (float)this.x, (float)(n3 + 2), -1);
                ++_snowman;
            }
        }
    }

    public String method_23958() {
        if (this.window != null) {
            return "\n" + this.window.getNarration();
        }
        return "";
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

        private SuggestionWindow(int x, int y, int width, List<Suggestion> list, boolean narrateFirstSuggestion) {
            int n = x - 1;
            _snowman = CommandSuggestor.this.chatScreenSized ? y - 3 - Math.min(list.size(), CommandSuggestor.this.maxSuggestionSize) * 12 : y;
            this.area = new Rect2i(n, _snowman, width + 1, Math.min(list.size(), CommandSuggestor.this.maxSuggestionSize) * 12);
            this.typedText = CommandSuggestor.this.textField.getText();
            this.lastNarrationIndex = narrateFirstSuggestion ? -1 : 0;
            this.field_25709 = list;
            this.select(0);
        }

        public void render(MatrixStack matrixStack2, int n, int n2) {
            int n3;
            _snowman = Math.min(this.field_25709.size(), CommandSuggestor.this.maxSuggestionSize);
            _snowman = -5592406;
            boolean bl = this.inWindowIndex > 0;
            bl3 = this.field_25709.size() > this.inWindowIndex + _snowman;
            _snowman = bl || bl3;
            boolean bl2 = _snowman = this.mouse.x != (float)n || this.mouse.y != (float)n2;
            if (_snowman) {
                this.mouse = new Vec2f(n, n2);
            }
            if (_snowman) {
                boolean bl3;
                DrawableHelper.fill(matrixStack2, this.area.getX(), this.area.getY() - 1, this.area.getX() + this.area.getWidth(), this.area.getY(), CommandSuggestor.this.color);
                DrawableHelper.fill(matrixStack2, this.area.getX(), this.area.getY() + this.area.getHeight(), this.area.getX() + this.area.getWidth(), this.area.getY() + this.area.getHeight() + 1, CommandSuggestor.this.color);
                if (bl) {
                    for (n3 = 0; n3 < this.area.getWidth(); ++n3) {
                        if (n3 % 2 != 0) continue;
                        DrawableHelper.fill(matrixStack2, this.area.getX() + n3, this.area.getY() - 1, this.area.getX() + n3 + 1, this.area.getY(), -1);
                    }
                }
                if (bl3) {
                    for (n3 = 0; n3 < this.area.getWidth(); ++n3) {
                        if (n3 % 2 != 0) continue;
                        DrawableHelper.fill(matrixStack2, this.area.getX() + n3, this.area.getY() + this.area.getHeight(), this.area.getX() + n3 + 1, this.area.getY() + this.area.getHeight() + 1, -1);
                    }
                }
            }
            n3 = 0;
            for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                MatrixStack matrixStack2;
                Suggestion suggestion = this.field_25709.get(_snowman + this.inWindowIndex);
                DrawableHelper.fill(matrixStack2, this.area.getX(), this.area.getY() + 12 * _snowman, this.area.getX() + this.area.getWidth(), this.area.getY() + 12 * _snowman + 12, CommandSuggestor.this.color);
                if (n > this.area.getX() && n < this.area.getX() + this.area.getWidth() && n2 > this.area.getY() + 12 * _snowman && n2 < this.area.getY() + 12 * _snowman + 12) {
                    if (_snowman) {
                        this.select(_snowman + this.inWindowIndex);
                    }
                    n3 = 1;
                }
                CommandSuggestor.this.textRenderer.drawWithShadow(matrixStack2, suggestion.getText(), (float)(this.area.getX() + 1), (float)(this.area.getY() + 2 + 12 * _snowman), _snowman + this.inWindowIndex == this.selection ? -256 : -5592406);
            }
            if (n3 != 0 && (_snowman = this.field_25709.get(this.selection).getTooltip()) != null) {
                CommandSuggestor.this.owner.renderTooltip(matrixStack2, Texts.toText(_snowman), n, n2);
            }
        }

        public boolean mouseClicked(int x, int y, int button) {
            if (!this.area.contains(x, y)) {
                return false;
            }
            int n = (y - this.area.getY()) / 12 + this.inWindowIndex;
            if (n >= 0 && n < this.field_25709.size()) {
                this.select(n);
                this.complete();
            }
            return true;
        }

        public boolean mouseScrolled(double amount) {
            int n = (int)(((CommandSuggestor)CommandSuggestor.this).client.mouse.getX() * (double)CommandSuggestor.this.client.getWindow().getScaledWidth() / (double)CommandSuggestor.this.client.getWindow().getWidth());
            if (this.area.contains(n, _snowman = (int)(((CommandSuggestor)CommandSuggestor.this).client.mouse.getY() * (double)CommandSuggestor.this.client.getWindow().getScaledHeight() / (double)CommandSuggestor.this.client.getWindow().getHeight()))) {
                this.inWindowIndex = MathHelper.clamp((int)((double)this.inWindowIndex - amount), 0, Math.max(this.field_25709.size() - CommandSuggestor.this.maxSuggestionSize, 0));
                return true;
            }
            return false;
        }

        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (keyCode == 265) {
                this.scroll(-1);
                this.completed = false;
                return true;
            }
            if (keyCode == 264) {
                this.scroll(1);
                this.completed = false;
                return true;
            }
            if (keyCode == 258) {
                if (this.completed) {
                    this.scroll(Screen.hasShiftDown() ? -1 : 1);
                }
                this.complete();
                return true;
            }
            if (keyCode == 256) {
                this.discard();
                return true;
            }
            return false;
        }

        public void scroll(int offset) {
            this.select(this.selection + offset);
            int n = this.inWindowIndex;
            _snowman = this.inWindowIndex + CommandSuggestor.this.maxSuggestionSize - 1;
            if (this.selection < n) {
                this.inWindowIndex = MathHelper.clamp(this.selection, 0, Math.max(this.field_25709.size() - CommandSuggestor.this.maxSuggestionSize, 0));
            } else if (this.selection > _snowman) {
                this.inWindowIndex = MathHelper.clamp(this.selection + CommandSuggestor.this.inWindowIndexOffset - CommandSuggestor.this.maxSuggestionSize, 0, Math.max(this.field_25709.size() - CommandSuggestor.this.maxSuggestionSize, 0));
            }
        }

        public void select(int index) {
            this.selection = index;
            if (this.selection < 0) {
                this.selection += this.field_25709.size();
            }
            if (this.selection >= this.field_25709.size()) {
                this.selection -= this.field_25709.size();
            }
            Suggestion suggestion = this.field_25709.get(this.selection);
            CommandSuggestor.this.textField.setSuggestion(CommandSuggestor.getSuggestionSuffix(CommandSuggestor.this.textField.getText(), suggestion.apply(this.typedText)));
            if (NarratorManager.INSTANCE.isActive() && this.lastNarrationIndex != this.selection) {
                NarratorManager.INSTANCE.narrate(this.getNarration());
            }
        }

        public void complete() {
            Suggestion suggestion = this.field_25709.get(this.selection);
            CommandSuggestor.this.completingSuggestions = true;
            CommandSuggestor.this.textField.setText(suggestion.apply(this.typedText));
            int _snowman2 = suggestion.getRange().getStart() + suggestion.getText().length();
            CommandSuggestor.this.textField.setSelectionStart(_snowman2);
            CommandSuggestor.this.textField.setSelectionEnd(_snowman2);
            this.select(this.selection);
            CommandSuggestor.this.completingSuggestions = false;
            this.completed = true;
        }

        private String getNarration() {
            this.lastNarrationIndex = this.selection;
            Suggestion suggestion = this.field_25709.get(this.selection);
            Message _snowman2 = suggestion.getTooltip();
            if (_snowman2 != null) {
                return I18n.translate("narration.suggestion.tooltip", this.selection + 1, this.field_25709.size(), suggestion.getText(), _snowman2.getString());
            }
            return I18n.translate("narration.suggestion", this.selection + 1, this.field_25709.size(), suggestion.getText());
        }

        public void discard() {
            CommandSuggestor.this.window = null;
        }
    }
}

