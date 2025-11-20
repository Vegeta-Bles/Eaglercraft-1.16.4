/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Iterables
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.arguments.DoubleArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.command;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.command.BlockDataObject;
import net.minecraft.command.DataCommandObject;
import net.minecraft.command.EntityDataObject;
import net.minecraft.command.StorageDataObject;
import net.minecraft.command.argument.NbtCompoundTagArgumentType;
import net.minecraft.command.argument.NbtPathArgumentType;
import net.minecraft.command.argument.NbtTagArgumentType;
import net.minecraft.nbt.AbstractListTag;
import net.minecraft.nbt.AbstractNumberTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;

public class DataCommand {
    private static final SimpleCommandExceptionType MERGE_FAILED_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("commands.data.merge.failed"));
    private static final DynamicCommandExceptionType GET_INVALID_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("commands.data.get.invalid", object));
    private static final DynamicCommandExceptionType GET_UNKNOWN_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("commands.data.get.unknown", object));
    private static final SimpleCommandExceptionType GET_MULTIPLE_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("commands.data.get.multiple"));
    private static final DynamicCommandExceptionType MODIFY_EXPECTED_LIST_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("commands.data.modify.expected_list", object));
    private static final DynamicCommandExceptionType MODIFY_EXPECTED_OBJECT_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("commands.data.modify.expected_object", object));
    private static final DynamicCommandExceptionType MODIFY_INVALID_INDEX_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("commands.data.modify.invalid_index", object));
    public static final List<Function<String, ObjectType>> OBJECT_TYPE_FACTORIES = ImmutableList.of(EntityDataObject.TYPE_FACTORY, BlockDataObject.TYPE_FACTORY, StorageDataObject.TYPE_FACTORY);
    public static final List<ObjectType> TARGET_OBJECT_TYPES = (List)OBJECT_TYPE_FACTORIES.stream().map(function -> (ObjectType)function.apply("target")).collect(ImmutableList.toImmutableList());
    public static final List<ObjectType> SOURCE_OBJECT_TYPES = (List)OBJECT_TYPE_FACTORIES.stream().map(function -> (ObjectType)function.apply("source")).collect(ImmutableList.toImmutableList());

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder literalArgumentBuilder = (LiteralArgumentBuilder)CommandManager.literal("data").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2));
        for (ObjectType objectType : TARGET_OBJECT_TYPES) {
            ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(objectType.addArgumentsToBuilder((ArgumentBuilder<ServerCommandSource, ?>)CommandManager.literal("merge"), argumentBuilder -> argumentBuilder.then(CommandManager.argument("nbt", NbtCompoundTagArgumentType.nbtCompound()).executes(commandContext -> DataCommand.executeMerge((ServerCommandSource)commandContext.getSource(), objectType.getObject((CommandContext<ServerCommandSource>)commandContext), NbtCompoundTagArgumentType.getCompoundTag(commandContext, "nbt"))))))).then(objectType.addArgumentsToBuilder((ArgumentBuilder<ServerCommandSource, ?>)CommandManager.literal("get"), argumentBuilder -> argumentBuilder.executes(commandContext -> DataCommand.executeGet((ServerCommandSource)commandContext.getSource(), objectType.getObject((CommandContext<ServerCommandSource>)commandContext))).then(((RequiredArgumentBuilder)CommandManager.argument("path", NbtPathArgumentType.nbtPath()).executes(commandContext -> DataCommand.executeGet((ServerCommandSource)commandContext.getSource(), objectType.getObject((CommandContext<ServerCommandSource>)commandContext), NbtPathArgumentType.getNbtPath((CommandContext<ServerCommandSource>)commandContext, "path")))).then(CommandManager.argument("scale", DoubleArgumentType.doubleArg()).executes(commandContext -> DataCommand.executeGet((ServerCommandSource)commandContext.getSource(), objectType.getObject((CommandContext<ServerCommandSource>)commandContext), NbtPathArgumentType.getNbtPath((CommandContext<ServerCommandSource>)commandContext, "path"), DoubleArgumentType.getDouble((CommandContext)commandContext, (String)"scale")))))))).then(objectType.addArgumentsToBuilder((ArgumentBuilder<ServerCommandSource, ?>)CommandManager.literal("remove"), argumentBuilder -> argumentBuilder.then(CommandManager.argument("path", NbtPathArgumentType.nbtPath()).executes(commandContext -> DataCommand.executeRemove((ServerCommandSource)commandContext.getSource(), objectType.getObject((CommandContext<ServerCommandSource>)commandContext), NbtPathArgumentType.getNbtPath((CommandContext<ServerCommandSource>)commandContext, "path"))))))).then(DataCommand.addModifyArgument((argumentBuilder, modifyArgumentCreator) -> argumentBuilder.then(CommandManager.literal("insert").then(CommandManager.argument("index", IntegerArgumentType.integer()).then(modifyArgumentCreator.create((commandContext, compoundTag, nbtPath, list) -> {
                int n = IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"index");
                return DataCommand.executeInsert(n, compoundTag, nbtPath, list);
            })))).then(CommandManager.literal("prepend").then(modifyArgumentCreator.create((commandContext, compoundTag, nbtPath, list) -> DataCommand.executeInsert(0, compoundTag, nbtPath, list)))).then(CommandManager.literal("append").then(modifyArgumentCreator.create((commandContext, compoundTag, nbtPath, list) -> DataCommand.executeInsert(-1, compoundTag, nbtPath, list)))).then(CommandManager.literal("set").then(modifyArgumentCreator.create((commandContext, compoundTag, nbtPath, list) -> nbtPath.put(compoundTag, ((Tag)Iterables.getLast((Iterable)list))::copy)))).then(CommandManager.literal("merge").then(modifyArgumentCreator.create((commandContext, compoundTag, nbtPath, list) -> {
                List<Tag> list2 = nbtPath.getOrInit(compoundTag, CompoundTag::new);
                int _snowman2 = 0;
                for (Tag tag : list2) {
                    CompoundTag compoundTag2;
                    if (!(tag instanceof CompoundTag)) {
                        throw MODIFY_EXPECTED_OBJECT_EXCEPTION.create((Object)tag);
                    }
                    CompoundTag compoundTag3 = (CompoundTag)tag;
                    compoundTag2 = compoundTag3.copy();
                    for (Tag tag2 : list) {
                        if (!(tag2 instanceof CompoundTag)) {
                            throw MODIFY_EXPECTED_OBJECT_EXCEPTION.create((Object)tag2);
                        }
                        compoundTag3.copyFrom((CompoundTag)tag2);
                    }
                    _snowman2 += compoundTag2.equals(compoundTag3) ? 0 : 1;
                }
                return _snowman2;
            })))));
        }
        dispatcher.register(literalArgumentBuilder);
    }

    private static int executeInsert(int integer, CompoundTag sourceTag, NbtPathArgumentType.NbtPath path, List<Tag> tags) throws CommandSyntaxException {
        List<Tag> list = path.getOrInit(sourceTag, ListTag::new);
        int _snowman2 = 0;
        for (Tag tag : list) {
            if (!(tag instanceof AbstractListTag)) {
                throw MODIFY_EXPECTED_LIST_EXCEPTION.create((Object)tag);
            }
            boolean bl = false;
            AbstractListTag _snowman3 = (AbstractListTag)tag;
            int _snowman4 = integer < 0 ? _snowman3.size() + integer + 1 : integer;
            for (Tag tag2 : tags) {
                try {
                    if (!_snowman3.addTag(_snowman4, tag2.copy())) continue;
                    ++_snowman4;
                    bl = true;
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw MODIFY_INVALID_INDEX_EXCEPTION.create((Object)_snowman4);
                }
            }
            _snowman2 += bl ? 1 : 0;
        }
        return _snowman2;
    }

    private static ArgumentBuilder<ServerCommandSource, ?> addModifyArgument(BiConsumer<ArgumentBuilder<ServerCommandSource, ?>, ModifyArgumentCreator> subArgumentAdder) {
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = CommandManager.literal("modify");
        for (ObjectType objectType : TARGET_OBJECT_TYPES) {
            objectType.addArgumentsToBuilder((ArgumentBuilder<ServerCommandSource, ?>)literalArgumentBuilder, argumentBuilder -> {
                BiConsumer biConsumer2;
                RequiredArgumentBuilder<ServerCommandSource, NbtPathArgumentType.NbtPath> requiredArgumentBuilder = CommandManager.argument("targetPath", NbtPathArgumentType.nbtPath());
                for (ObjectType objectType2 : SOURCE_OBJECT_TYPES) {
                    subArgumentAdder.accept((ArgumentBuilder<ServerCommandSource, ?>)requiredArgumentBuilder, modifyOperation -> objectType2.addArgumentsToBuilder((ArgumentBuilder<ServerCommandSource, ?>)CommandManager.literal("from"), argumentBuilder -> argumentBuilder.executes(commandContext -> {
                        List<Tag> list = Collections.singletonList(objectType2.getObject((CommandContext<ServerCommandSource>)commandContext).getTag());
                        return DataCommand.executeModify((CommandContext<ServerCommandSource>)commandContext, objectType, modifyOperation, list);
                    }).then(CommandManager.argument("sourcePath", NbtPathArgumentType.nbtPath()).executes(commandContext -> {
                        DataCommandObject dataCommandObject = objectType2.getObject((CommandContext<ServerCommandSource>)commandContext);
                        NbtPathArgumentType.NbtPath _snowman2 = NbtPathArgumentType.getNbtPath((CommandContext<ServerCommandSource>)commandContext, "sourcePath");
                        List<Tag> _snowman3 = _snowman2.get(dataCommandObject.getTag());
                        return DataCommand.executeModify((CommandContext<ServerCommandSource>)commandContext, objectType, modifyOperation, _snowman3);
                    }))));
                }
                subArgumentAdder.accept((ArgumentBuilder<ServerCommandSource, ?>)requiredArgumentBuilder, modifyOperation -> (LiteralArgumentBuilder)CommandManager.literal("value").then(CommandManager.argument("value", NbtTagArgumentType.nbtTag()).executes(commandContext -> {
                    List<Tag> list = Collections.singletonList(NbtTagArgumentType.getTag(commandContext, "value"));
                    return DataCommand.executeModify((CommandContext<ServerCommandSource>)commandContext, objectType, modifyOperation, list);
                })));
                return argumentBuilder.then(requiredArgumentBuilder);
            });
        }
        return literalArgumentBuilder;
    }

    private static int executeModify(CommandContext<ServerCommandSource> context, ObjectType objectType, ModifyOperation modifier, List<Tag> tags) throws CommandSyntaxException {
        DataCommandObject dataCommandObject = objectType.getObject(context);
        NbtPathArgumentType.NbtPath _snowman2 = NbtPathArgumentType.getNbtPath(context, "targetPath");
        CompoundTag _snowman3 = dataCommandObject.getTag();
        int _snowman4 = modifier.modify(context, _snowman3, _snowman2, tags);
        if (_snowman4 == 0) {
            throw MERGE_FAILED_EXCEPTION.create();
        }
        dataCommandObject.setTag(_snowman3);
        ((ServerCommandSource)context.getSource()).sendFeedback(dataCommandObject.feedbackModify(), true);
        return _snowman4;
    }

    private static int executeRemove(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path) throws CommandSyntaxException {
        CompoundTag compoundTag = object.getTag();
        int _snowman2 = path.remove(compoundTag);
        if (_snowman2 == 0) {
            throw MERGE_FAILED_EXCEPTION.create();
        }
        object.setTag(compoundTag);
        source.sendFeedback(object.feedbackModify(), true);
        return _snowman2;
    }

    private static Tag getTag(NbtPathArgumentType.NbtPath path, DataCommandObject object) throws CommandSyntaxException {
        List<Tag> list = path.get(object.getTag());
        Iterator _snowman2 = list.iterator();
        Tag _snowman3 = (Tag)_snowman2.next();
        if (_snowman2.hasNext()) {
            throw GET_MULTIPLE_EXCEPTION.create();
        }
        return _snowman3;
    }

    private static int executeGet(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path) throws CommandSyntaxException {
        int n;
        Tag tag = DataCommand.getTag(path, object);
        if (tag instanceof AbstractNumberTag) {
            n = MathHelper.floor(((AbstractNumberTag)tag).getDouble());
        } else if (tag instanceof AbstractListTag) {
            n = ((AbstractListTag)tag).size();
        } else if (tag instanceof CompoundTag) {
            n = ((CompoundTag)tag).getSize();
        } else if (tag instanceof StringTag) {
            n = tag.asString().length();
        } else {
            throw GET_UNKNOWN_EXCEPTION.create((Object)path.toString());
        }
        source.sendFeedback(object.feedbackQuery(tag), false);
        return n;
    }

    private static int executeGet(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path, double scale) throws CommandSyntaxException {
        Tag tag = DataCommand.getTag(path, object);
        if (!(tag instanceof AbstractNumberTag)) {
            throw GET_INVALID_EXCEPTION.create((Object)path.toString());
        }
        int _snowman2 = MathHelper.floor(((AbstractNumberTag)tag).getDouble() * scale);
        source.sendFeedback(object.feedbackGet(path, scale, _snowman2), false);
        return _snowman2;
    }

    private static int executeGet(ServerCommandSource source, DataCommandObject object) throws CommandSyntaxException {
        source.sendFeedback(object.feedbackQuery(object.getTag()), false);
        return 1;
    }

    private static int executeMerge(ServerCommandSource source, DataCommandObject object, CompoundTag tag) throws CommandSyntaxException {
        CompoundTag compoundTag = object.getTag();
        if (compoundTag.equals(_snowman = compoundTag.copy().copyFrom(tag))) {
            throw MERGE_FAILED_EXCEPTION.create();
        }
        object.setTag(_snowman);
        source.sendFeedback(object.feedbackModify(), true);
        return 1;
    }

    public static interface ObjectType {
        public DataCommandObject getObject(CommandContext<ServerCommandSource> var1) throws CommandSyntaxException;

        public ArgumentBuilder<ServerCommandSource, ?> addArgumentsToBuilder(ArgumentBuilder<ServerCommandSource, ?> var1, Function<ArgumentBuilder<ServerCommandSource, ?>, ArgumentBuilder<ServerCommandSource, ?>> var2);
    }

    static interface ModifyArgumentCreator {
        public ArgumentBuilder<ServerCommandSource, ?> create(ModifyOperation var1);
    }

    static interface ModifyOperation {
        public int modify(CommandContext<ServerCommandSource> var1, CompoundTag var2, NbtPathArgumentType.NbtPath var3, List<Tag> var4) throws CommandSyntaxException;
    }
}

