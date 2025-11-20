package net.minecraft.server.command;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
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
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;

public class DataCommand {
   private static final SimpleCommandExceptionType MERGE_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.data.merge.failed"));
   private static final DynamicCommandExceptionType GET_INVALID_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("commands.data.get.invalid", object)
   );
   private static final DynamicCommandExceptionType GET_UNKNOWN_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("commands.data.get.unknown", object)
   );
   private static final SimpleCommandExceptionType GET_MULTIPLE_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.data.get.multiple"));
   private static final DynamicCommandExceptionType MODIFY_EXPECTED_LIST_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("commands.data.modify.expected_list", object)
   );
   private static final DynamicCommandExceptionType MODIFY_EXPECTED_OBJECT_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("commands.data.modify.expected_object", object)
   );
   private static final DynamicCommandExceptionType MODIFY_INVALID_INDEX_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("commands.data.modify.invalid_index", object)
   );
   public static final List<Function<String, DataCommand.ObjectType>> OBJECT_TYPE_FACTORIES = ImmutableList.of(
      EntityDataObject.TYPE_FACTORY, BlockDataObject.TYPE_FACTORY, StorageDataObject.TYPE_FACTORY
   );
   public static final List<DataCommand.ObjectType> TARGET_OBJECT_TYPES = OBJECT_TYPE_FACTORIES.stream()
      .map(function -> function.apply("target"))
      .collect(ImmutableList.toImmutableList());
   public static final List<DataCommand.ObjectType> SOURCE_OBJECT_TYPES = OBJECT_TYPE_FACTORIES.stream()
      .map(function -> function.apply("source"))
      .collect(ImmutableList.toImmutableList());

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = (LiteralArgumentBuilder<ServerCommandSource>)CommandManager.literal("data")
         .requires(arg -> arg.hasPermissionLevel(2));

      for (DataCommand.ObjectType lv : TARGET_OBJECT_TYPES) {
         ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.then(
                     lv.addArgumentsToBuilder(
                        CommandManager.literal("merge"),
                        argumentBuilder -> argumentBuilder.then(
                              CommandManager.argument("nbt", NbtCompoundTagArgumentType.nbtCompound())
                                 .executes(
                                    commandContext -> executeMerge(
                                          (ServerCommandSource)commandContext.getSource(),
                                          lv.getObject(commandContext),
                                          NbtCompoundTagArgumentType.getCompoundTag(commandContext, "nbt")
                                       )
                                 )
                           )
                     )
                  ))
                  .then(
                     lv.addArgumentsToBuilder(
                        CommandManager.literal("get"),
                        argumentBuilder -> argumentBuilder.executes(
                                 commandContext -> executeGet((ServerCommandSource)commandContext.getSource(), lv.getObject(commandContext))
                              )
                              .then(
                                 ((RequiredArgumentBuilder)CommandManager.argument("path", NbtPathArgumentType.nbtPath())
                                       .executes(
                                          commandContext -> executeGet(
                                                (ServerCommandSource)commandContext.getSource(),
                                                lv.getObject(commandContext),
                                                NbtPathArgumentType.getNbtPath(commandContext, "path")
                                             )
                                       ))
                                    .then(
                                       CommandManager.argument("scale", DoubleArgumentType.doubleArg())
                                          .executes(
                                             commandContext -> executeGet(
                                                   (ServerCommandSource)commandContext.getSource(),
                                                   lv.getObject(commandContext),
                                                   NbtPathArgumentType.getNbtPath(commandContext, "path"),
                                                   DoubleArgumentType.getDouble(commandContext, "scale")
                                                )
                                          )
                                    )
                              )
                     )
                  ))
               .then(
                  lv.addArgumentsToBuilder(
                     CommandManager.literal("remove"),
                     argumentBuilder -> argumentBuilder.then(
                           CommandManager.argument("path", NbtPathArgumentType.nbtPath())
                              .executes(
                                 commandContext -> executeRemove(
                                       (ServerCommandSource)commandContext.getSource(),
                                       lv.getObject(commandContext),
                                       NbtPathArgumentType.getNbtPath(commandContext, "path")
                                    )
                              )
                        )
                  )
               ))
            .then(
               addModifyArgument(
                  (argumentBuilder, arg) -> argumentBuilder.then(
                           CommandManager.literal("insert")
                              .then(CommandManager.argument("index", IntegerArgumentType.integer()).then(arg.create((commandContext, argx, arg2, list) -> {
                                 int i = IntegerArgumentType.getInteger(commandContext, "index");
                                 return executeInsert(i, argx, arg2, list);
                              })))
                        )
                        .then(CommandManager.literal("prepend").then(arg.create((commandContext, argx, arg2, list) -> executeInsert(0, argx, arg2, list))))
                        .then(CommandManager.literal("append").then(arg.create((commandContext, argx, arg2, list) -> executeInsert(-1, argx, arg2, list))))
                        .then(
                           CommandManager.literal("set")
                              .then(arg.create((commandContext, argx, arg2, list) -> arg2.put(argx, ((Tag)Iterables.getLast(list))::copy)))
                        )
                        .then(CommandManager.literal("merge").then(arg.create((commandContext, argx, arg2, list) -> {
                           Collection<Tag> collection = arg2.getOrInit(argx, CompoundTag::new);
                           int i = 0;

                           for (Tag lvx : collection) {
                              if (!(lvx instanceof CompoundTag)) {
                                 throw MODIFY_EXPECTED_OBJECT_EXCEPTION.create(lvx);
                              }

                              CompoundTag lv2 = (CompoundTag)lvx;
                              CompoundTag lv3 = lv2.copy();

                              for (Tag lv4 : list) {
                                 if (!(lv4 instanceof CompoundTag)) {
                                    throw MODIFY_EXPECTED_OBJECT_EXCEPTION.create(lv4);
                                 }

                                 lv2.copyFrom((CompoundTag)lv4);
                              }

                              i += lv3.equals(lv2) ? 0 : 1;
                           }

                           return i;
                        })))
               )
            );
      }

      dispatcher.register(literalArgumentBuilder);
   }

   private static int executeInsert(int integer, CompoundTag sourceTag, NbtPathArgumentType.NbtPath path, List<Tag> tags) throws CommandSyntaxException {
      Collection<Tag> collection = path.getOrInit(sourceTag, ListTag::new);
      int j = 0;

      for (Tag lv : collection) {
         if (!(lv instanceof AbstractListTag)) {
            throw MODIFY_EXPECTED_LIST_EXCEPTION.create(lv);
         }

         boolean bl = false;
         AbstractListTag<?> lv2 = (AbstractListTag<?>)lv;
         int k = integer < 0 ? lv2.size() + integer + 1 : integer;

         for (Tag lv3 : tags) {
            try {
               if (lv2.addTag(k, lv3.copy())) {
                  k++;
                  bl = true;
               }
            } catch (IndexOutOfBoundsException var14) {
               throw MODIFY_INVALID_INDEX_EXCEPTION.create(k);
            }
         }

         j += bl ? 1 : 0;
      }

      return j;
   }

   private static ArgumentBuilder<ServerCommandSource, ?> addModifyArgument(
      BiConsumer<ArgumentBuilder<ServerCommandSource, ?>, DataCommand.ModifyArgumentCreator> subArgumentAdder
   ) {
      LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = CommandManager.literal("modify");

      for (DataCommand.ObjectType lv : TARGET_OBJECT_TYPES) {
         lv.addArgumentsToBuilder(
            literalArgumentBuilder,
            argumentBuilder -> {
               ArgumentBuilder<ServerCommandSource, ?> argumentBuilder2 = CommandManager.argument("targetPath", NbtPathArgumentType.nbtPath());

               for (DataCommand.ObjectType lvx : SOURCE_OBJECT_TYPES) {
                  subArgumentAdder.accept(
                     argumentBuilder2,
                     arg3 -> lvx.addArgumentsToBuilder(CommandManager.literal("from"), argumentBuilderx -> argumentBuilderx.executes(commandContext -> {
                              List<Tag> list = Collections.singletonList(lvx.getObject(commandContext).getTag());
                              return executeModify(commandContext, lv, arg3, list);
                           }).then(CommandManager.argument("sourcePath", NbtPathArgumentType.nbtPath()).executes(commandContext -> {
                              DataCommandObject lvxx = lvx.getObject(commandContext);
                              NbtPathArgumentType.NbtPath lv2 = NbtPathArgumentType.getNbtPath(commandContext, "sourcePath");
                              List<Tag> list = lv2.get(lvxx.getTag());
                              return executeModify(commandContext, lv, arg3, list);
                           })))
                  );
               }

               subArgumentAdder.accept(
                  argumentBuilder2,
                  arg2 -> CommandManager.literal("value").then(CommandManager.argument("value", NbtTagArgumentType.nbtTag()).executes(commandContext -> {
                        List<Tag> list = Collections.singletonList(NbtTagArgumentType.getTag(commandContext, "value"));
                        return executeModify(commandContext, lv, arg2, list);
                     }))
               );
               return argumentBuilder.then(argumentBuilder2);
            }
         );
      }

      return literalArgumentBuilder;
   }

   private static int executeModify(
      CommandContext<ServerCommandSource> context, DataCommand.ObjectType objectType, DataCommand.ModifyOperation modifier, List<Tag> tags
   ) throws CommandSyntaxException {
      DataCommandObject lv = objectType.getObject(context);
      NbtPathArgumentType.NbtPath lv2 = NbtPathArgumentType.getNbtPath(context, "targetPath");
      CompoundTag lv3 = lv.getTag();
      int i = modifier.modify(context, lv3, lv2, tags);
      if (i == 0) {
         throw MERGE_FAILED_EXCEPTION.create();
      } else {
         lv.setTag(lv3);
         ((ServerCommandSource)context.getSource()).sendFeedback(lv.feedbackModify(), true);
         return i;
      }
   }

   private static int executeRemove(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path) throws CommandSyntaxException {
      CompoundTag lv = object.getTag();
      int i = path.remove(lv);
      if (i == 0) {
         throw MERGE_FAILED_EXCEPTION.create();
      } else {
         object.setTag(lv);
         source.sendFeedback(object.feedbackModify(), true);
         return i;
      }
   }

   private static Tag getTag(NbtPathArgumentType.NbtPath path, DataCommandObject object) throws CommandSyntaxException {
      Collection<Tag> collection = path.get(object.getTag());
      Iterator<Tag> iterator = collection.iterator();
      Tag lv = iterator.next();
      if (iterator.hasNext()) {
         throw GET_MULTIPLE_EXCEPTION.create();
      } else {
         return lv;
      }
   }

   private static int executeGet(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path) throws CommandSyntaxException {
      Tag lv = getTag(path, object);
      int i;
      if (lv instanceof AbstractNumberTag) {
         i = MathHelper.floor(((AbstractNumberTag)lv).getDouble());
      } else if (lv instanceof AbstractListTag) {
         i = ((AbstractListTag)lv).size();
      } else if (lv instanceof CompoundTag) {
         i = ((CompoundTag)lv).getSize();
      } else {
         if (!(lv instanceof StringTag)) {
            throw GET_UNKNOWN_EXCEPTION.create(path.toString());
         }

         i = lv.asString().length();
      }

      source.sendFeedback(object.feedbackQuery(lv), false);
      return i;
   }

   private static int executeGet(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path, double scale) throws CommandSyntaxException {
      Tag lv = getTag(path, object);
      if (!(lv instanceof AbstractNumberTag)) {
         throw GET_INVALID_EXCEPTION.create(path.toString());
      } else {
         int i = MathHelper.floor(((AbstractNumberTag)lv).getDouble() * scale);
         source.sendFeedback(object.feedbackGet(path, scale, i), false);
         return i;
      }
   }

   private static int executeGet(ServerCommandSource source, DataCommandObject object) throws CommandSyntaxException {
      source.sendFeedback(object.feedbackQuery(object.getTag()), false);
      return 1;
   }

   private static int executeMerge(ServerCommandSource source, DataCommandObject object, CompoundTag tag) throws CommandSyntaxException {
      CompoundTag lv = object.getTag();
      CompoundTag lv2 = lv.copy().copyFrom(tag);
      if (lv.equals(lv2)) {
         throw MERGE_FAILED_EXCEPTION.create();
      } else {
         object.setTag(lv2);
         source.sendFeedback(object.feedbackModify(), true);
         return 1;
      }
   }

   interface ModifyArgumentCreator {
      ArgumentBuilder<ServerCommandSource, ?> create(DataCommand.ModifyOperation modifier);
   }

   interface ModifyOperation {
      int modify(CommandContext<ServerCommandSource> context, CompoundTag sourceTag, NbtPathArgumentType.NbtPath path, List<Tag> tags) throws CommandSyntaxException;
   }

   public interface ObjectType {
      DataCommandObject getObject(CommandContext<ServerCommandSource> context) throws CommandSyntaxException;

      ArgumentBuilder<ServerCommandSource, ?> addArgumentsToBuilder(
         ArgumentBuilder<ServerCommandSource, ?> argument,
         Function<ArgumentBuilder<ServerCommandSource, ?>, ArgumentBuilder<ServerCommandSource, ?>> argumentAdder
      );
   }
}
