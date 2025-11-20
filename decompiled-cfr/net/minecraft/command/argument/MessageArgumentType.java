/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.command.argument;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.EntitySelectorReader;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class MessageArgumentType
implements ArgumentType<MessageFormat> {
    private static final Collection<String> EXAMPLES = Arrays.asList("Hello world!", "foo", "@e", "Hello @p :)");

    public static MessageArgumentType message() {
        return new MessageArgumentType();
    }

    public static Text getMessage(CommandContext<ServerCommandSource> command, String name) throws CommandSyntaxException {
        return ((MessageFormat)command.getArgument(name, MessageFormat.class)).format((ServerCommandSource)command.getSource(), ((ServerCommandSource)command.getSource()).hasPermissionLevel(2));
    }

    public MessageFormat parse(StringReader stringReader) throws CommandSyntaxException {
        return MessageFormat.parse(stringReader, true);
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    public static class MessageSelector {
        private final int start;
        private final int end;
        private final EntitySelector selector;

        public MessageSelector(int n, int n2, EntitySelector entitySelector) {
            this.start = n;
            this.end = n2;
            this.selector = entitySelector;
        }

        public int getStart() {
            return this.start;
        }

        public int getEnd() {
            return this.end;
        }

        @Nullable
        public Text format(ServerCommandSource serverCommandSource) throws CommandSyntaxException {
            return EntitySelector.getNames(this.selector.getEntities(serverCommandSource));
        }
    }

    public static class MessageFormat {
        private final String contents;
        private final MessageSelector[] selectors;

        public MessageFormat(String string, MessageSelector[] messageSelectorArray) {
            this.contents = string;
            this.selectors = messageSelectorArray;
        }

        public Text format(ServerCommandSource serverCommandSource, boolean bl) throws CommandSyntaxException {
            if (this.selectors.length == 0 || !bl) {
                return new LiteralText(this.contents);
            }
            LiteralText literalText = new LiteralText(this.contents.substring(0, this.selectors[0].getStart()));
            int _snowman2 = this.selectors[0].getStart();
            for (MessageSelector messageSelector : this.selectors) {
                Text text = messageSelector.format(serverCommandSource);
                if (_snowman2 < messageSelector.getStart()) {
                    literalText.append(this.contents.substring(_snowman2, messageSelector.getStart()));
                }
                if (text != null) {
                    literalText.append(text);
                }
                _snowman2 = messageSelector.getEnd();
            }
            if (_snowman2 < this.contents.length()) {
                literalText.append(this.contents.substring(_snowman2, this.contents.length()));
            }
            return literalText;
        }

        public static MessageFormat parse(StringReader stringReader2, boolean bl) throws CommandSyntaxException {
            String string = stringReader2.getString().substring(stringReader2.getCursor(), stringReader2.getTotalLength());
            if (!bl) {
                stringReader2.setCursor(stringReader2.getTotalLength());
                return new MessageFormat(string, new MessageSelector[0]);
            }
            ArrayList _snowman2 = Lists.newArrayList();
            int _snowman3 = stringReader2.getCursor();
            while (stringReader2.canRead()) {
                StringReader stringReader2;
                if (stringReader2.peek() == '@') {
                    EntitySelector _snowman4;
                    int n = stringReader2.getCursor();
                    try {
                        EntitySelectorReader entitySelectorReader = new EntitySelectorReader(stringReader2);
                        _snowman4 = entitySelectorReader.read();
                    }
                    catch (CommandSyntaxException commandSyntaxException) {
                        if (commandSyntaxException.getType() == EntitySelectorReader.MISSING_EXCEPTION || commandSyntaxException.getType() == EntitySelectorReader.UNKNOWN_SELECTOR_EXCEPTION) {
                            stringReader2.setCursor(n + 1);
                            continue;
                        }
                        throw commandSyntaxException;
                    }
                    _snowman2.add(new MessageSelector(n - _snowman3, stringReader2.getCursor() - _snowman3, _snowman4));
                    continue;
                }
                stringReader2.skip();
            }
            return new MessageFormat(string, _snowman2.toArray(new MessageSelector[_snowman2.size()]));
        }
    }
}

