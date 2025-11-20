package net.minecraft.server.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.argument.ItemSlotArgumentType;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class LootCommand {
   public static final SuggestionProvider<ServerCommandSource> SUGGESTION_PROVIDER = (_snowman, _snowmanx) -> {
      LootManager _snowmanxx = ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getLootManager();
      return CommandSource.suggestIdentifiers(_snowmanxx.getTableIds(), _snowmanx);
   };
   private static final DynamicCommandExceptionType NO_HELD_ITEMS_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.drop.no_held_items", _snowman)
   );
   private static final DynamicCommandExceptionType NO_LOOT_TABLE_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.drop.no_loot_table", _snowman)
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         addTargetArguments(
            CommandManager.literal("loot").requires(_snowman -> _snowman.hasPermissionLevel(2)),
            (_snowman, _snowmanx) -> _snowman.then(
                     CommandManager.literal("fish")
                        .then(
                           CommandManager.argument("loot_table", IdentifierArgumentType.identifier())
                              .suggests(SUGGESTION_PROVIDER)
                              .then(
                                 ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                                "pos", BlockPosArgumentType.blockPos()
                                             )
                                             .executes(
                                                _snowmanxxx -> executeFish(
                                                      _snowmanxxx,
                                                      IdentifierArgumentType.getIdentifier(_snowmanxxx, "loot_table"),
                                                      BlockPosArgumentType.getLoadedBlockPos(_snowmanxxx, "pos"),
                                                      ItemStack.EMPTY,
                                                      _snowmanx
                                                   )
                                             ))
                                          .then(
                                             CommandManager.argument("tool", ItemStackArgumentType.itemStack())
                                                .executes(
                                                   _snowmanxxx -> executeFish(
                                                         _snowmanxxx,
                                                         IdentifierArgumentType.getIdentifier(_snowmanxxx, "loot_table"),
                                                         BlockPosArgumentType.getLoadedBlockPos(_snowmanxxx, "pos"),
                                                         ItemStackArgumentType.getItemStackArgument(_snowmanxxx, "tool").createStack(1, false),
                                                         _snowmanx
                                                      )
                                                )
                                          ))
                                       .then(
                                          CommandManager.literal("mainhand")
                                             .executes(
                                                _snowmanxxx -> executeFish(
                                                      _snowmanxxx,
                                                      IdentifierArgumentType.getIdentifier(_snowmanxxx, "loot_table"),
                                                      BlockPosArgumentType.getLoadedBlockPos(_snowmanxxx, "pos"),
                                                      getHeldItem((ServerCommandSource)_snowmanxxx.getSource(), EquipmentSlot.MAINHAND),
                                                      _snowmanx
                                                   )
                                             )
                                       ))
                                    .then(
                                       CommandManager.literal("offhand")
                                          .executes(
                                             _snowmanxxx -> executeFish(
                                                   _snowmanxxx,
                                                   IdentifierArgumentType.getIdentifier(_snowmanxxx, "loot_table"),
                                                   BlockPosArgumentType.getLoadedBlockPos(_snowmanxxx, "pos"),
                                                   getHeldItem((ServerCommandSource)_snowmanxxx.getSource(), EquipmentSlot.OFFHAND),
                                                   _snowmanx
                                                )
                                          )
                                    )
                              )
                        )
                  )
                  .then(
                     CommandManager.literal("loot")
                        .then(
                           CommandManager.argument("loot_table", IdentifierArgumentType.identifier())
                              .suggests(SUGGESTION_PROVIDER)
                              .executes(_snowmanxxx -> executeLoot(_snowmanxxx, IdentifierArgumentType.getIdentifier(_snowmanxxx, "loot_table"), _snowmanx))
                        )
                  )
                  .then(
                     CommandManager.literal("kill")
                        .then(
                           CommandManager.argument("target", EntityArgumentType.entity())
                              .executes(_snowmanxxx -> executeKill(_snowmanxxx, EntityArgumentType.getEntity(_snowmanxxx, "target"), _snowmanx))
                        )
                  )
                  .then(
                     CommandManager.literal("mine")
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                          "pos", BlockPosArgumentType.blockPos()
                                       )
                                       .executes(_snowmanxxx -> executeMine(_snowmanxxx, BlockPosArgumentType.getLoadedBlockPos(_snowmanxxx, "pos"), ItemStack.EMPTY, _snowmanx)))
                                    .then(
                                       CommandManager.argument("tool", ItemStackArgumentType.itemStack())
                                          .executes(
                                             _snowmanxxx -> executeMine(
                                                   _snowmanxxx,
                                                   BlockPosArgumentType.getLoadedBlockPos(_snowmanxxx, "pos"),
                                                   ItemStackArgumentType.getItemStackArgument(_snowmanxxx, "tool").createStack(1, false),
                                                   _snowmanx
                                                )
                                          )
                                    ))
                                 .then(
                                    CommandManager.literal("mainhand")
                                       .executes(
                                          _snowmanxxx -> executeMine(
                                                _snowmanxxx,
                                                BlockPosArgumentType.getLoadedBlockPos(_snowmanxxx, "pos"),
                                                getHeldItem((ServerCommandSource)_snowmanxxx.getSource(), EquipmentSlot.MAINHAND),
                                                _snowmanx
                                             )
                                       )
                                 ))
                              .then(
                                 CommandManager.literal("offhand")
                                    .executes(
                                       _snowmanxxx -> executeMine(
                                             _snowmanxxx,
                                             BlockPosArgumentType.getLoadedBlockPos(_snowmanxxx, "pos"),
                                             getHeldItem((ServerCommandSource)_snowmanxxx.getSource(), EquipmentSlot.OFFHAND),
                                             _snowmanx
                                          )
                                    )
                              )
                        )
                  )
         )
      );
   }

   private static <T extends ArgumentBuilder<ServerCommandSource, T>> T addTargetArguments(T rootArgument, LootCommand.SourceConstructor sourceConstructor) {
      return (T)rootArgument.then(
            ((LiteralArgumentBuilder)CommandManager.literal("replace")
                  .then(
                     CommandManager.literal("entity")
                        .then(
                           CommandManager.argument("entities", EntityArgumentType.entities())
                              .then(
                                 sourceConstructor.construct(
                                       CommandManager.argument("slot", ItemSlotArgumentType.itemSlot()),
                                       (_snowman, _snowmanx, _snowmanxx) -> executeReplace(
                                             EntityArgumentType.getEntities(_snowman, "entities"), ItemSlotArgumentType.getItemSlot(_snowman, "slot"), _snowmanx.size(), _snowmanx, _snowmanxx
                                          )
                                    )
                                    .then(
                                       sourceConstructor.construct(
                                          CommandManager.argument("count", IntegerArgumentType.integer(0)),
                                          (_snowman, _snowmanx, _snowmanxx) -> executeReplace(
                                                EntityArgumentType.getEntities(_snowman, "entities"),
                                                ItemSlotArgumentType.getItemSlot(_snowman, "slot"),
                                                IntegerArgumentType.getInteger(_snowman, "count"),
                                                _snowmanx,
                                                _snowmanxx
                                             )
                                       )
                                    )
                              )
                        )
                  ))
               .then(
                  CommandManager.literal("block")
                     .then(
                        CommandManager.argument("targetPos", BlockPosArgumentType.blockPos())
                           .then(
                              sourceConstructor.construct(
                                    CommandManager.argument("slot", ItemSlotArgumentType.itemSlot()),
                                    (_snowman, _snowmanx, _snowmanxx) -> executeBlock(
                                          (ServerCommandSource)_snowman.getSource(),
                                          BlockPosArgumentType.getLoadedBlockPos(_snowman, "targetPos"),
                                          ItemSlotArgumentType.getItemSlot(_snowman, "slot"),
                                          _snowmanx.size(),
                                          _snowmanx,
                                          _snowmanxx
                                       )
                                 )
                                 .then(
                                    sourceConstructor.construct(
                                       CommandManager.argument("count", IntegerArgumentType.integer(0)),
                                       (_snowman, _snowmanx, _snowmanxx) -> executeBlock(
                                             (ServerCommandSource)_snowman.getSource(),
                                             BlockPosArgumentType.getLoadedBlockPos(_snowman, "targetPos"),
                                             IntegerArgumentType.getInteger(_snowman, "slot"),
                                             IntegerArgumentType.getInteger(_snowman, "count"),
                                             _snowmanx,
                                             _snowmanxx
                                          )
                                    )
                                 )
                           )
                     )
               )
         )
         .then(
            CommandManager.literal("insert")
               .then(
                  sourceConstructor.construct(
                     CommandManager.argument("targetPos", BlockPosArgumentType.blockPos()),
                     (_snowman, _snowmanx, _snowmanxx) -> executeInsert((ServerCommandSource)_snowman.getSource(), BlockPosArgumentType.getLoadedBlockPos(_snowman, "targetPos"), _snowmanx, _snowmanxx)
                  )
               )
         )
         .then(
            CommandManager.literal("give")
               .then(
                  sourceConstructor.construct(
                     CommandManager.argument("players", EntityArgumentType.players()),
                     (_snowman, _snowmanx, _snowmanxx) -> executeGive(EntityArgumentType.getPlayers(_snowman, "players"), _snowmanx, _snowmanxx)
                  )
               )
         )
         .then(
            CommandManager.literal("spawn")
               .then(
                  sourceConstructor.construct(
                     CommandManager.argument("targetPos", Vec3ArgumentType.vec3()),
                     (_snowman, _snowmanx, _snowmanxx) -> executeSpawn((ServerCommandSource)_snowman.getSource(), Vec3ArgumentType.getVec3(_snowman, "targetPos"), _snowmanx, _snowmanxx)
                  )
               )
         );
   }

   private static Inventory getBlockInventory(ServerCommandSource source, BlockPos pos) throws CommandSyntaxException {
      BlockEntity _snowman = source.getWorld().getBlockEntity(pos);
      if (!(_snowman instanceof Inventory)) {
         throw ReplaceItemCommand.BLOCK_FAILED_EXCEPTION.create();
      } else {
         return (Inventory)_snowman;
      }
   }

   private static int executeInsert(ServerCommandSource source, BlockPos targetPos, List<ItemStack> stacks, LootCommand.FeedbackMessage messageSender) throws CommandSyntaxException {
      Inventory _snowman = getBlockInventory(source, targetPos);
      List<ItemStack> _snowmanx = Lists.newArrayListWithCapacity(stacks.size());

      for (ItemStack _snowmanxx : stacks) {
         if (insert(_snowman, _snowmanxx.copy())) {
            _snowman.markDirty();
            _snowmanx.add(_snowmanxx);
         }
      }

      messageSender.accept(_snowmanx);
      return _snowmanx.size();
   }

   private static boolean insert(Inventory inventory, ItemStack stack) {
      boolean _snowman = false;

      for (int _snowmanx = 0; _snowmanx < inventory.size() && !stack.isEmpty(); _snowmanx++) {
         ItemStack _snowmanxx = inventory.getStack(_snowmanx);
         if (inventory.isValid(_snowmanx, stack)) {
            if (_snowmanxx.isEmpty()) {
               inventory.setStack(_snowmanx, stack);
               _snowman = true;
               break;
            }

            if (itemsMatch(_snowmanxx, stack)) {
               int _snowmanxxx = stack.getMaxCount() - _snowmanxx.getCount();
               int _snowmanxxxx = Math.min(stack.getCount(), _snowmanxxx);
               stack.decrement(_snowmanxxxx);
               _snowmanxx.increment(_snowmanxxxx);
               _snowman = true;
            }
         }
      }

      return _snowman;
   }

   private static int executeBlock(
      ServerCommandSource source, BlockPos targetPos, int slot, int stackCount, List<ItemStack> stacks, LootCommand.FeedbackMessage messageSender
   ) throws CommandSyntaxException {
      Inventory _snowman = getBlockInventory(source, targetPos);
      int _snowmanx = _snowman.size();
      if (slot >= 0 && slot < _snowmanx) {
         List<ItemStack> _snowmanxx = Lists.newArrayListWithCapacity(stacks.size());

         for (int _snowmanxxx = 0; _snowmanxxx < stackCount; _snowmanxxx++) {
            int _snowmanxxxx = slot + _snowmanxxx;
            ItemStack _snowmanxxxxx = _snowmanxxx < stacks.size() ? stacks.get(_snowmanxxx) : ItemStack.EMPTY;
            if (_snowman.isValid(_snowmanxxxx, _snowmanxxxxx)) {
               _snowman.setStack(_snowmanxxxx, _snowmanxxxxx);
               _snowmanxx.add(_snowmanxxxxx);
            }
         }

         messageSender.accept(_snowmanxx);
         return _snowmanxx.size();
      } else {
         throw ReplaceItemCommand.SLOT_INAPPLICABLE_EXCEPTION.create(slot);
      }
   }

   private static boolean itemsMatch(ItemStack first, ItemStack second) {
      return first.getItem() == second.getItem()
         && first.getDamage() == second.getDamage()
         && first.getCount() <= first.getMaxCount()
         && Objects.equals(first.getTag(), second.getTag());
   }

   private static int executeGive(Collection<ServerPlayerEntity> players, List<ItemStack> stacks, LootCommand.FeedbackMessage messageSender) throws CommandSyntaxException {
      List<ItemStack> _snowman = Lists.newArrayListWithCapacity(stacks.size());

      for (ItemStack _snowmanx : stacks) {
         for (ServerPlayerEntity _snowmanxx : players) {
            if (_snowmanxx.inventory.insertStack(_snowmanx.copy())) {
               _snowman.add(_snowmanx);
            }
         }
      }

      messageSender.accept(_snowman);
      return _snowman.size();
   }

   private static void replace(Entity entity, List<ItemStack> stacks, int slot, int stackCount, List<ItemStack> addedStacks) {
      for (int _snowman = 0; _snowman < stackCount; _snowman++) {
         ItemStack _snowmanx = _snowman < stacks.size() ? stacks.get(_snowman) : ItemStack.EMPTY;
         if (entity.equip(slot + _snowman, _snowmanx.copy())) {
            addedStacks.add(_snowmanx);
         }
      }
   }

   private static int executeReplace(
      Collection<? extends Entity> targets, int slot, int stackCount, List<ItemStack> stacks, LootCommand.FeedbackMessage messageSender
   ) throws CommandSyntaxException {
      List<ItemStack> _snowman = Lists.newArrayListWithCapacity(stacks.size());

      for (Entity _snowmanx : targets) {
         if (_snowmanx instanceof ServerPlayerEntity) {
            ServerPlayerEntity _snowmanxx = (ServerPlayerEntity)_snowmanx;
            _snowmanxx.playerScreenHandler.sendContentUpdates();
            replace(_snowmanx, stacks, slot, stackCount, _snowman);
            _snowmanxx.playerScreenHandler.sendContentUpdates();
         } else {
            replace(_snowmanx, stacks, slot, stackCount, _snowman);
         }
      }

      messageSender.accept(_snowman);
      return _snowman.size();
   }

   private static int executeSpawn(ServerCommandSource source, Vec3d pos, List<ItemStack> stacks, LootCommand.FeedbackMessage messageSender) throws CommandSyntaxException {
      ServerWorld _snowman = source.getWorld();
      stacks.forEach(_snowmanxx -> {
         ItemEntity _snowmanx = new ItemEntity(_snowman, pos.x, pos.y, pos.z, _snowmanxx.copy());
         _snowmanx.setToDefaultPickupDelay();
         _snowman.spawnEntity(_snowmanx);
      });
      messageSender.accept(stacks);
      return stacks.size();
   }

   private static void sendDroppedFeedback(ServerCommandSource source, List<ItemStack> stacks) {
      if (stacks.size() == 1) {
         ItemStack _snowman = stacks.get(0);
         source.sendFeedback(new TranslatableText("commands.drop.success.single", _snowman.getCount(), _snowman.toHoverableText()), false);
      } else {
         source.sendFeedback(new TranslatableText("commands.drop.success.multiple", stacks.size()), false);
      }
   }

   private static void sendDroppedFeedback(ServerCommandSource source, List<ItemStack> stacks, Identifier lootTable) {
      if (stacks.size() == 1) {
         ItemStack _snowman = stacks.get(0);
         source.sendFeedback(new TranslatableText("commands.drop.success.single_with_table", _snowman.getCount(), _snowman.toHoverableText(), lootTable), false);
      } else {
         source.sendFeedback(new TranslatableText("commands.drop.success.multiple_with_table", stacks.size(), lootTable), false);
      }
   }

   private static ItemStack getHeldItem(ServerCommandSource source, EquipmentSlot slot) throws CommandSyntaxException {
      Entity _snowman = source.getEntityOrThrow();
      if (_snowman instanceof LivingEntity) {
         return ((LivingEntity)_snowman).getEquippedStack(slot);
      } else {
         throw NO_HELD_ITEMS_EXCEPTION.create(_snowman.getDisplayName());
      }
   }

   private static int executeMine(CommandContext<ServerCommandSource> context, BlockPos pos, ItemStack stack, LootCommand.Target constructor) throws CommandSyntaxException {
      ServerCommandSource _snowman = (ServerCommandSource)context.getSource();
      ServerWorld _snowmanx = _snowman.getWorld();
      BlockState _snowmanxx = _snowmanx.getBlockState(pos);
      BlockEntity _snowmanxxx = _snowmanx.getBlockEntity(pos);
      LootContext.Builder _snowmanxxxx = new LootContext.Builder(_snowmanx)
         .parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
         .parameter(LootContextParameters.BLOCK_STATE, _snowmanxx)
         .optionalParameter(LootContextParameters.BLOCK_ENTITY, _snowmanxxx)
         .optionalParameter(LootContextParameters.THIS_ENTITY, _snowman.getEntity())
         .parameter(LootContextParameters.TOOL, stack);
      List<ItemStack> _snowmanxxxxx = _snowmanxx.getDroppedStacks(_snowmanxxxx);
      return constructor.accept(context, _snowmanxxxxx, _snowmanxxxxxx -> sendDroppedFeedback(_snowman, _snowmanxxxxxx, _snowman.getBlock().getLootTableId()));
   }

   private static int executeKill(CommandContext<ServerCommandSource> context, Entity entity, LootCommand.Target constructor) throws CommandSyntaxException {
      if (!(entity instanceof LivingEntity)) {
         throw NO_LOOT_TABLE_EXCEPTION.create(entity.getDisplayName());
      } else {
         Identifier _snowman = ((LivingEntity)entity).getLootTable();
         ServerCommandSource _snowmanx = (ServerCommandSource)context.getSource();
         LootContext.Builder _snowmanxx = new LootContext.Builder(_snowmanx.getWorld());
         Entity _snowmanxxx = _snowmanx.getEntity();
         if (_snowmanxxx instanceof PlayerEntity) {
            _snowmanxx.parameter(LootContextParameters.LAST_DAMAGE_PLAYER, (PlayerEntity)_snowmanxxx);
         }

         _snowmanxx.parameter(LootContextParameters.DAMAGE_SOURCE, DamageSource.MAGIC);
         _snowmanxx.optionalParameter(LootContextParameters.DIRECT_KILLER_ENTITY, _snowmanxxx);
         _snowmanxx.optionalParameter(LootContextParameters.KILLER_ENTITY, _snowmanxxx);
         _snowmanxx.parameter(LootContextParameters.THIS_ENTITY, entity);
         _snowmanxx.parameter(LootContextParameters.ORIGIN, _snowmanx.getPosition());
         LootTable _snowmanxxxx = _snowmanx.getMinecraftServer().getLootManager().getTable(_snowman);
         List<ItemStack> _snowmanxxxxx = _snowmanxxxx.generateLoot(_snowmanxx.build(LootContextTypes.ENTITY));
         return constructor.accept(context, _snowmanxxxxx, _snowmanxxxxxx -> sendDroppedFeedback(_snowman, _snowmanxxxxxx, _snowman));
      }
   }

   private static int executeLoot(CommandContext<ServerCommandSource> context, Identifier lootTable, LootCommand.Target constructor) throws CommandSyntaxException {
      ServerCommandSource _snowman = (ServerCommandSource)context.getSource();
      LootContext.Builder _snowmanx = new LootContext.Builder(_snowman.getWorld())
         .optionalParameter(LootContextParameters.THIS_ENTITY, _snowman.getEntity())
         .parameter(LootContextParameters.ORIGIN, _snowman.getPosition());
      return getFeedbackMessageSingle(context, lootTable, _snowmanx.build(LootContextTypes.CHEST), constructor);
   }

   private static int executeFish(
      CommandContext<ServerCommandSource> context, Identifier lootTable, BlockPos pos, ItemStack stack, LootCommand.Target constructor
   ) throws CommandSyntaxException {
      ServerCommandSource _snowman = (ServerCommandSource)context.getSource();
      LootContext _snowmanx = new LootContext.Builder(_snowman.getWorld())
         .parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
         .parameter(LootContextParameters.TOOL, stack)
         .optionalParameter(LootContextParameters.THIS_ENTITY, _snowman.getEntity())
         .build(LootContextTypes.FISHING);
      return getFeedbackMessageSingle(context, lootTable, _snowmanx, constructor);
   }

   private static int getFeedbackMessageSingle(
      CommandContext<ServerCommandSource> context, Identifier lootTable, LootContext lootContext, LootCommand.Target constructor
   ) throws CommandSyntaxException {
      ServerCommandSource _snowman = (ServerCommandSource)context.getSource();
      LootTable _snowmanx = _snowman.getMinecraftServer().getLootManager().getTable(lootTable);
      List<ItemStack> _snowmanxx = _snowmanx.generateLoot(lootContext);
      return constructor.accept(context, _snowmanxx, _snowmanxxx -> sendDroppedFeedback(_snowman, _snowmanxxx));
   }

   @FunctionalInterface
   interface FeedbackMessage {
      void accept(List<ItemStack> items) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface SourceConstructor {
      ArgumentBuilder<ServerCommandSource, ?> construct(ArgumentBuilder<ServerCommandSource, ?> builder, LootCommand.Target target);
   }

   @FunctionalInterface
   interface Target {
      int accept(CommandContext<ServerCommandSource> context, List<ItemStack> items, LootCommand.FeedbackMessage messageSender) throws CommandSyntaxException;
   }
}
