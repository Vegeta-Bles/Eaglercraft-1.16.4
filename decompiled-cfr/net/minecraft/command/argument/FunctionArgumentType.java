/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.tag.Tag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class FunctionArgumentType
implements ArgumentType<FunctionArgument> {
    private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo:bar", "#foo");
    private static final DynamicCommandExceptionType UNKNOWN_FUNCTION_TAG_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("arguments.function.tag.unknown", object));
    private static final DynamicCommandExceptionType UNKNOWN_FUNCTION_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("arguments.function.unknown", object));

    public static FunctionArgumentType function() {
        return new FunctionArgumentType();
    }

    public FunctionArgument parse(StringReader stringReader2) throws CommandSyntaxException {
        StringReader stringReader2;
        if (stringReader2.canRead() && stringReader2.peek() == '#') {
            stringReader2.skip();
            Identifier identifier = Identifier.fromCommandInput(stringReader2);
            return new FunctionArgument(this, identifier){
                final /* synthetic */ Identifier field_10785;
                final /* synthetic */ FunctionArgumentType field_10786;
                {
                    this.field_10786 = functionArgumentType;
                    this.field_10785 = identifier;
                }

                public Collection<CommandFunction> getFunctions(CommandContext<ServerCommandSource> commandContext) throws CommandSyntaxException {
                    Tag tag = FunctionArgumentType.method_9766(commandContext, this.field_10785);
                    return tag.values();
                }

                public Pair<Identifier, Either<CommandFunction, Tag<CommandFunction>>> getFunctionOrTag(CommandContext<ServerCommandSource> commandContext) throws CommandSyntaxException {
                    return Pair.of((Object)this.field_10785, (Object)Either.right((Object)FunctionArgumentType.method_9766(commandContext, this.field_10785)));
                }
            };
        }
        Identifier _snowman2 = Identifier.fromCommandInput(stringReader2);
        return new FunctionArgument(this, _snowman2){
            final /* synthetic */ Identifier field_10787;
            final /* synthetic */ FunctionArgumentType field_10788;
            {
                this.field_10788 = functionArgumentType;
                this.field_10787 = identifier;
            }

            public Collection<CommandFunction> getFunctions(CommandContext<ServerCommandSource> commandContext) throws CommandSyntaxException {
                return Collections.singleton(FunctionArgumentType.method_9763(commandContext, this.field_10787));
            }

            public Pair<Identifier, Either<CommandFunction, Tag<CommandFunction>>> getFunctionOrTag(CommandContext<ServerCommandSource> commandContext) throws CommandSyntaxException {
                return Pair.of((Object)this.field_10787, (Object)Either.left((Object)FunctionArgumentType.method_9763(commandContext, this.field_10787)));
            }
        };
    }

    private static CommandFunction getFunction(CommandContext<ServerCommandSource> context, Identifier id) throws CommandSyntaxException {
        return ((ServerCommandSource)context.getSource()).getMinecraftServer().getCommandFunctionManager().getFunction(id).orElseThrow(() -> UNKNOWN_FUNCTION_EXCEPTION.create((Object)id.toString()));
    }

    private static Tag<CommandFunction> getFunctionTag(CommandContext<ServerCommandSource> context, Identifier id) throws CommandSyntaxException {
        Tag<CommandFunction> tag = ((ServerCommandSource)context.getSource()).getMinecraftServer().getCommandFunctionManager().method_29462(id);
        if (tag == null) {
            throw UNKNOWN_FUNCTION_TAG_EXCEPTION.create((Object)id.toString());
        }
        return tag;
    }

    public static Collection<CommandFunction> getFunctions(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
        return ((FunctionArgument)context.getArgument(name, FunctionArgument.class)).getFunctions(context);
    }

    public static Pair<Identifier, Either<CommandFunction, Tag<CommandFunction>>> getFunctionOrTag(CommandContext<ServerCommandSource> commandContext, String string) throws CommandSyntaxException {
        return ((FunctionArgument)commandContext.getArgument(string, FunctionArgument.class)).getFunctionOrTag(commandContext);
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }

    static /* synthetic */ Tag method_9766(CommandContext commandContext, Identifier identifier) throws CommandSyntaxException {
        return FunctionArgumentType.getFunctionTag((CommandContext<ServerCommandSource>)commandContext, identifier);
    }

    static /* synthetic */ CommandFunction method_9763(CommandContext commandContext, Identifier identifier) throws CommandSyntaxException {
        return FunctionArgumentType.getFunction((CommandContext<ServerCommandSource>)commandContext, identifier);
    }

    public static interface FunctionArgument {
        public Collection<CommandFunction> getFunctions(CommandContext<ServerCommandSource> var1) throws CommandSyntaxException;

        public Pair<Identifier, Either<CommandFunction, Tag<CommandFunction>>> getFunctionOrTag(CommandContext<ServerCommandSource> var1) throws CommandSyntaxException;
    }
}

