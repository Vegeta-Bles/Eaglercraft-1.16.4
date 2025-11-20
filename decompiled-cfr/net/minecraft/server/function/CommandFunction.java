/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.server.function;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.util.Identifier;

public class CommandFunction {
    private final Element[] elements;
    private final Identifier id;

    public CommandFunction(Identifier id, Element[] elements) {
        this.id = id;
        this.elements = elements;
    }

    public Identifier getId() {
        return this.id;
    }

    public Element[] getElements() {
        return this.elements;
    }

    public static CommandFunction create(Identifier id, CommandDispatcher<ServerCommandSource> commandDispatcher2, ServerCommandSource serverCommandSource, List<String> list) {
        ArrayList arrayList = Lists.newArrayListWithCapacity((int)list.size());
        for (int i = 0; i < list.size(); ++i) {
            Object _snowman3;
            _snowman = i + 1;
            String string = list.get(i).trim();
            StringReader _snowman2 = new StringReader(string);
            if (!_snowman2.canRead() || _snowman2.peek() == '#') continue;
            if (_snowman2.peek() == '/') {
                _snowman2.skip();
                if (_snowman2.peek() == '/') {
                    throw new IllegalArgumentException("Unknown or invalid command '" + string + "' on line " + _snowman + " (if you intended to make a comment, use '#' not '//')");
                }
                _snowman3 = _snowman2.readUnquotedString();
                throw new IllegalArgumentException("Unknown or invalid command '" + string + "' on line " + _snowman + " (did you mean '" + (String)_snowman3 + "'? Do not use a preceding forwards slash.)");
            }
            try {
                CommandDispatcher<ServerCommandSource> commandDispatcher2;
                _snowman3 = commandDispatcher2.parse(_snowman2, (Object)serverCommandSource);
                if (_snowman3.getReader().canRead()) {
                    throw CommandManager.getException(_snowman3);
                }
                arrayList.add(new CommandElement((ParseResults<ServerCommandSource>)_snowman3));
                continue;
            }
            catch (CommandSyntaxException commandSyntaxException) {
                throw new IllegalArgumentException("Whilst parsing command on line " + _snowman + ": " + commandSyntaxException.getMessage());
            }
        }
        return new CommandFunction(id, arrayList.toArray(new Element[0]));
    }

    public static class LazyContainer {
        public static final LazyContainer EMPTY = new LazyContainer((Identifier)null);
        @Nullable
        private final Identifier id;
        private boolean initialized;
        private Optional<CommandFunction> function = Optional.empty();

        public LazyContainer(@Nullable Identifier id) {
            this.id = id;
        }

        public LazyContainer(CommandFunction function) {
            this.initialized = true;
            this.id = null;
            this.function = Optional.of(function);
        }

        public Optional<CommandFunction> get(CommandFunctionManager manager) {
            if (!this.initialized) {
                if (this.id != null) {
                    this.function = manager.getFunction(this.id);
                }
                this.initialized = true;
            }
            return this.function;
        }

        @Nullable
        public Identifier getId() {
            return this.function.map(commandFunction -> ((CommandFunction)commandFunction).id).orElse(this.id);
        }
    }

    public static class FunctionElement
    implements Element {
        private final LazyContainer function;

        public FunctionElement(CommandFunction commandFunction) {
            this.function = new LazyContainer(commandFunction);
        }

        @Override
        public void execute(CommandFunctionManager manager, ServerCommandSource source, ArrayDeque<CommandFunctionManager.Entry> stack, int maxChainLength) {
            this.function.get(manager).ifPresent(commandFunction -> {
                Element[] elementArray = commandFunction.getElements();
                int _snowman2 = maxChainLength - stack.size();
                int _snowman3 = Math.min(elementArray.length, _snowman2);
                for (int i = _snowman3 - 1; i >= 0; --i) {
                    stack.addFirst(new CommandFunctionManager.Entry(manager, source, elementArray[i]));
                }
            });
        }

        public String toString() {
            return "function " + this.function.getId();
        }
    }

    public static class CommandElement
    implements Element {
        private final ParseResults<ServerCommandSource> parsed;

        public CommandElement(ParseResults<ServerCommandSource> parsed) {
            this.parsed = parsed;
        }

        @Override
        public void execute(CommandFunctionManager manager, ServerCommandSource source, ArrayDeque<CommandFunctionManager.Entry> stack, int maxChainLength) throws CommandSyntaxException {
            manager.getDispatcher().execute(new ParseResults(this.parsed.getContext().withSource((Object)source), this.parsed.getReader(), this.parsed.getExceptions()));
        }

        public String toString() {
            return this.parsed.getReader().getString();
        }
    }

    public static interface Element {
        public void execute(CommandFunctionManager var1, ServerCommandSource var2, ArrayDeque<CommandFunctionManager.Entry> var3, int var4) throws CommandSyntaxException;
    }
}

