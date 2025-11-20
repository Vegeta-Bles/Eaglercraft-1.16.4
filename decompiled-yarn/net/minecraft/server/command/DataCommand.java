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
      _snowman -> new TranslatableText("commands.data.get.invalid", _snowman)
   );
   private static final DynamicCommandExceptionType GET_UNKNOWN_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.data.get.unknown", _snowman)
   );
   private static final SimpleCommandExceptionType GET_MULTIPLE_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.data.get.multiple"));
   private static final DynamicCommandExceptionType MODIFY_EXPECTED_LIST_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.data.modify.expected_list", _snowman)
   );
   private static final DynamicCommandExceptionType MODIFY_EXPECTED_OBJECT_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.data.modify.expected_object", _snowman)
   );
   private static final DynamicCommandExceptionType MODIFY_INVALID_INDEX_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.data.modify.invalid_index", _snowman)
   );
   public static final List<Function<String, DataCommand.ObjectType>> OBJECT_TYPE_FACTORIES = ImmutableList.of(
      EntityDataObject.TYPE_FACTORY, BlockDataObject.TYPE_FACTORY, StorageDataObject.TYPE_FACTORY
   );
   public static final List<DataCommand.ObjectType> TARGET_OBJECT_TYPES = OBJECT_TYPE_FACTORIES.stream()
      .map(_snowman -> _snowman.apply("target"))
      .collect(ImmutableList.toImmutableList());
   public static final List<DataCommand.ObjectType> SOURCE_OBJECT_TYPES = OBJECT_TYPE_FACTORIES.stream()
      .map(_snowman -> _snowman.apply("source"))
      .collect(ImmutableList.toImmutableList());

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      LiteralArgumentBuilder<ServerCommandSource> _snowman = (LiteralArgumentBuilder<ServerCommandSource>)CommandManager.literal("data")
         .requires(_snowmanx -> _snowmanx.hasPermissionLevel(2));

      for (DataCommand.ObjectType _snowmanx : TARGET_OBJECT_TYPES) {
         ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)_snowman.then(
                     _snowmanx.addArgumentsToBuilder(
                        CommandManager.literal("merge"),
                        _snowmanxx -> _snowmanxx.then(
                              CommandManager.argument("nbt", NbtCompoundTagArgumentType.nbtCompound())
                                 .executes(
                                    _snowmanxxx -> executeMerge(
                                          (ServerCommandSource)_snowmanxxx.getSource(), _snowman.getObject(_snowmanxxx), NbtCompoundTagArgumentType.getCompoundTag(_snowmanxxx, "nbt")
                                       )
                                 )
                           )
                     )
                  ))
                  .then(
                     _snowmanx.addArgumentsToBuilder(
                        CommandManager.literal("get"),
                        _snowmanxx -> _snowmanxx.executes(_snowmanxxx -> executeGet((ServerCommandSource)_snowmanxxx.getSource(), _snowman.getObject(_snowmanxxx)))
                              .then(
                                 ((RequiredArgumentBuilder)CommandManager.argument("path", NbtPathArgumentType.nbtPath())
                                       .executes(
                                          _snowmanxxx -> executeGet(
                                                (ServerCommandSource)_snowmanxxx.getSource(), _snowman.getObject(_snowmanxxx), NbtPathArgumentType.getNbtPath(_snowmanxxx, "path")
                                             )
                                       ))
                                    .then(
                                       CommandManager.argument("scale", DoubleArgumentType.doubleArg())
                                          .executes(
                                             _snowmanxxx -> executeGet(
                                                   (ServerCommandSource)_snowmanxxx.getSource(),
                                                   _snowman.getObject(_snowmanxxx),
                                                   NbtPathArgumentType.getNbtPath(_snowmanxxx, "path"),
                                                   DoubleArgumentType.getDouble(_snowmanxxx, "scale")
                                                )
                                          )
                                    )
                              )
                     )
                  ))
               .then(
                  _snowmanx.addArgumentsToBuilder(
                     CommandManager.literal("remove"),
                     _snowmanxx -> _snowmanxx.then(
                           CommandManager.argument("path", NbtPathArgumentType.nbtPath())
                              .executes(
                                 _snowmanxxx -> executeRemove((ServerCommandSource)_snowmanxxx.getSource(), _snowman.getObject(_snowmanxxx), NbtPathArgumentType.getNbtPath(_snowmanxxx, "path"))
                              )
                        )
                  )
               ))
            .then(
               addModifyArgument(
                  (_snowmanxx, _snowmanxxx) -> _snowmanxx.then(
                           CommandManager.literal("insert")
                              .then(CommandManager.argument("index", IntegerArgumentType.integer()).then(_snowmanxxx.create((_snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxx, _snowmanxxxxx) -> {
                                 int _snowmanxxxxxx = IntegerArgumentType.getInteger(_snowmanxxxx, "index");
                                 return executeInsert(_snowmanxxxxxx, _snowmanxxxxxx, _snowmanxxxxx, _snowmanxxxxx);
                              })))
                        )
                        .then(
                           CommandManager.literal("prepend").then(_snowmanxxx.create((_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxx, _snowmanxxxxxx) -> executeInsert(0, _snowmanxxxxx, _snowmanxxxxx, _snowmanxxxxxx)))
                        )
                        .then(
                           CommandManager.literal("append").then(_snowmanxxx.create((_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxx, _snowmanxxxxxx) -> executeInsert(-1, _snowmanxxxxx, _snowmanxxxxx, _snowmanxxxxxx)))
                        )
                        .then(
                           CommandManager.literal("set")
                              .then(_snowmanxxx.create((_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxx, _snowmanxxxxxx) -> _snowmanxxxxx.put(_snowmanxxxxx, ((Tag)Iterables.getLast(_snowmanxxxxxx))::copy)))
                        )
                        .then(CommandManager.literal("merge").then(_snowmanxxx.create((_snowmanxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx) -> {
                           Collection<Tag> _snowmanxxxx = _snowmanxxxxxxxxxx.getOrInit(_snowmanxxxxxxxxxxx, CompoundTag::new);
                           int _snowmanxxxxx = 0;

                           for (Tag _snowmanxxxxxx : _snowmanxxxx) {
                              if (!(_snowmanxxxxxx instanceof CompoundTag)) {
                                 throw MODIFY_EXPECTED_OBJECT_EXCEPTION.create(_snowmanxxxxxx);
                              }

                              CompoundTag _snowmanxxxxxxx = (CompoundTag)_snowmanxxxxxx;
                              CompoundTag _snowmanxxxxxxxxx = _snowmanxxxxxxx.copy();

                              for (Tag _snowmanxxxxxxxxxx : _snowmanxxxxxxxxx) {
                                 if (!(_snowmanxxxxxxxxxx instanceof CompoundTag)) {
                                    throw MODIFY_EXPECTED_OBJECT_EXCEPTION.create(_snowmanxxxxxxxxxx);
                                 }

                                 _snowmanxxxxxxx.copyFrom((CompoundTag)_snowmanxxxxxxxxxx);
                              }

                              _snowmanxxxxx += _snowmanxxxxxxxxx.equals(_snowmanxxxxxxx) ? 0 : 1;
                           }

                           return _snowmanxxxxx;
                        })))
               )
            );
      }

      dispatcher.register(_snowman);
   }

   private static int executeInsert(int integer, CompoundTag sourceTag, NbtPathArgumentType.NbtPath path, List<Tag> tags) throws CommandSyntaxException {
      Collection<Tag> _snowman = path.getOrInit(sourceTag, ListTag::new);
      int _snowmanx = 0;

      for (Tag _snowmanxx : _snowman) {
         if (!(_snowmanxx instanceof AbstractListTag)) {
            throw MODIFY_EXPECTED_LIST_EXCEPTION.create(_snowmanxx);
         }

         boolean _snowmanxxx = false;
         AbstractListTag<?> _snowmanxxxx = (AbstractListTag<?>)_snowmanxx;
         int _snowmanxxxxx = integer < 0 ? _snowmanxxxx.size() + integer + 1 : integer;

         for (Tag _snowmanxxxxxx : tags) {
            try {
               if (_snowmanxxxx.addTag(_snowmanxxxxx, _snowmanxxxxxx.copy())) {
                  _snowmanxxxxx++;
                  _snowmanxxx = true;
               }
            } catch (IndexOutOfBoundsException var14) {
               throw MODIFY_INVALID_INDEX_EXCEPTION.create(_snowmanxxxxx);
            }
         }

         _snowmanx += _snowmanxxx ? 1 : 0;
      }

      return _snowmanx;
   }

   private static ArgumentBuilder<ServerCommandSource, ?> addModifyArgument(
      BiConsumer<ArgumentBuilder<ServerCommandSource, ?>, DataCommand.ModifyArgumentCreator> subArgumentAdder
   ) {
      LiteralArgumentBuilder<ServerCommandSource> _snowman = CommandManager.literal("modify");

      for (DataCommand.ObjectType _snowmanx : TARGET_OBJECT_TYPES) {
         _snowmanx.addArgumentsToBuilder(
            _snowman,
            _snowmanxx -> {
               ArgumentBuilder<ServerCommandSource, ?> _snowmanxx = CommandManager.argument("targetPath", NbtPathArgumentType.nbtPath());

               for (DataCommand.ObjectType _snowmanx : SOURCE_OBJECT_TYPES) {
                  subArgumentAdder.accept(
                     _snowmanxx, _snowmanxxxx -> _snowman.addArgumentsToBuilder(CommandManager.literal("from"), _snowmanxxxxxxx -> _snowmanxxxxxxx.executes(_snowmanxxxxxxxxxxxx -> {
                              List<Tag> _snowmanxxxx = Collections.singletonList(_snowman.getObject(_snowmanxxxxxxxxxxxx).getTag());
                              return executeModify(_snowmanxxxxxxxxxxxx, _snowman, _snowmanxxxx, _snowmanxxxx);
                           }).then(CommandManager.argument("sourcePath", NbtPathArgumentType.nbtPath()).executes(_snowmanxxxxxxxxxxxxxx -> {
                              DataCommandObject _snowmanxxxx = _snowman.getObject(_snowmanxxxxxxxxxxxxxx);
                              NbtPathArgumentType.NbtPath _snowmanxxxxx = NbtPathArgumentType.getNbtPath(_snowmanxxxxxxxxxxxxxx, "sourcePath");
                              List<Tag> _snowmanxxxxxx = _snowmanxxxxx.get(_snowmanxxxx.getTag());
                              return executeModify(_snowmanxxxxxxxxxxxxxx, _snowman, _snowmanxxxx, _snowmanxxxxxx);
                           })))
                  );
               }

               subArgumentAdder.accept(
                  _snowmanxx, _snowmanxxxx -> CommandManager.literal("value").then(CommandManager.argument("value", NbtTagArgumentType.nbtTag()).executes(_snowmanxxxxxxx -> {
                        List<Tag> _snowmanxxx = Collections.singletonList(NbtTagArgumentType.getTag(_snowmanxxxxxxx, "value"));
                        return executeModify(_snowmanxxxxxxx, _snowman, _snowmanxxxx, _snowmanxxx);
                     }))
               );
               return _snowmanxx.then(_snowmanxx);
            }
         );
      }

      return _snowman;
   }

   private static int executeModify(
      CommandContext<ServerCommandSource> context, DataCommand.ObjectType objectType, DataCommand.ModifyOperation modifier, List<Tag> tags
   ) throws CommandSyntaxException {
      DataCommandObject _snowman = objectType.getObject(context);
      NbtPathArgumentType.NbtPath _snowmanx = NbtPathArgumentType.getNbtPath(context, "targetPath");
      CompoundTag _snowmanxx = _snowman.getTag();
      int _snowmanxxx = modifier.modify(context, _snowmanxx, _snowmanx, tags);
      if (_snowmanxxx == 0) {
         throw MERGE_FAILED_EXCEPTION.create();
      } else {
         _snowman.setTag(_snowmanxx);
         ((ServerCommandSource)context.getSource()).sendFeedback(_snowman.feedbackModify(), true);
         return _snowmanxxx;
      }
   }

   private static int executeRemove(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path) throws CommandSyntaxException {
      CompoundTag _snowman = object.getTag();
      int _snowmanx = path.remove(_snowman);
      if (_snowmanx == 0) {
         throw MERGE_FAILED_EXCEPTION.create();
      } else {
         object.setTag(_snowman);
         source.sendFeedback(object.feedbackModify(), true);
         return _snowmanx;
      }
   }

   private static Tag getTag(NbtPathArgumentType.NbtPath path, DataCommandObject object) throws CommandSyntaxException {
      Collection<Tag> _snowman = path.get(object.getTag());
      Iterator<Tag> _snowmanx = _snowman.iterator();
      Tag _snowmanxx = _snowmanx.next();
      if (_snowmanx.hasNext()) {
         throw GET_MULTIPLE_EXCEPTION.create();
      } else {
         return _snowmanxx;
      }
   }

   private static int executeGet(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path) throws CommandSyntaxException {
      Tag _snowman = getTag(path, object);
      int _snowmanx;
      if (_snowman instanceof AbstractNumberTag) {
         _snowmanx = MathHelper.floor(((AbstractNumberTag)_snowman).getDouble());
      } else if (_snowman instanceof AbstractListTag) {
         _snowmanx = ((AbstractListTag)_snowman).size();
      } else if (_snowman instanceof CompoundTag) {
         _snowmanx = ((CompoundTag)_snowman).getSize();
      } else {
         if (!(_snowman instanceof StringTag)) {
            throw GET_UNKNOWN_EXCEPTION.create(path.toString());
         }

         _snowmanx = _snowman.asString().length();
      }

      source.sendFeedback(object.feedbackQuery(_snowman), false);
      return _snowmanx;
   }

   private static int executeGet(ServerCommandSource source, DataCommandObject object, NbtPathArgumentType.NbtPath path, double scale) throws CommandSyntaxException {
      Tag _snowman = getTag(path, object);
      if (!(_snowman instanceof AbstractNumberTag)) {
         throw GET_INVALID_EXCEPTION.create(path.toString());
      } else {
         int _snowmanx = MathHelper.floor(((AbstractNumberTag)_snowman).getDouble() * scale);
         source.sendFeedback(object.feedbackGet(path, scale, _snowmanx), false);
         return _snowmanx;
      }
   }

   private static int executeGet(ServerCommandSource source, DataCommandObject object) throws CommandSyntaxException {
      source.sendFeedback(object.feedbackQuery(object.getTag()), false);
      return 1;
   }

   private static int executeMerge(ServerCommandSource source, DataCommandObject object, CompoundTag tag) throws CommandSyntaxException {
      CompoundTag _snowman = object.getTag();
      CompoundTag _snowmanx = _snowman.copy().copyFrom(tag);
      if (_snowman.equals(_snowmanx)) {
         throw MERGE_FAILED_EXCEPTION.create();
      } else {
         object.setTag(_snowmanx);
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
