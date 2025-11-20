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
   public static final SuggestionProvider<ServerCommandSource> SUGGESTION_PROVIDER = (commandContext, suggestionsBuilder) -> {
      LootManager lv = ((ServerCommandSource)commandContext.getSource()).getMinecraftServer().getLootManager();
      return CommandSource.suggestIdentifiers(lv.getTableIds(), suggestionsBuilder);
   };
   private static final DynamicCommandExceptionType NO_HELD_ITEMS_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("commands.drop.no_held_items", object)
   );
   private static final DynamicCommandExceptionType NO_LOOT_TABLE_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("commands.drop.no_loot_table", object)
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         addTargetArguments(
            CommandManager.literal("loot").requires(arg -> arg.hasPermissionLevel(2)),
            (argumentBuilder, arg) -> argumentBuilder.then(
                     CommandManager.literal("fish")
                        .then(
                           CommandManager.argument("loot_table", IdentifierArgumentType.identifier())
                              .suggests(SUGGESTION_PROVIDER)
                              .then(
                                 ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                                "pos", BlockPosArgumentType.blockPos()
                                             )
                                             .executes(
                                                commandContext -> executeFish(
                                                      commandContext,
                                                      IdentifierArgumentType.getIdentifier(commandContext, "loot_table"),
                                                      BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos"),
                                                      ItemStack.EMPTY,
                                                      arg
                                                   )
                                             ))
                                          .then(
                                             CommandManager.argument("tool", ItemStackArgumentType.itemStack())
                                                .executes(
                                                   commandContext -> executeFish(
                                                         commandContext,
                                                         IdentifierArgumentType.getIdentifier(commandContext, "loot_table"),
                                                         BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos"),
                                                         ItemStackArgumentType.getItemStackArgument(commandContext, "tool").createStack(1, false),
                                                         arg
                                                      )
                                                )
                                          ))
                                       .then(
                                          CommandManager.literal("mainhand")
                                             .executes(
                                                commandContext -> executeFish(
                                                      commandContext,
                                                      IdentifierArgumentType.getIdentifier(commandContext, "loot_table"),
                                                      BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos"),
                                                      getHeldItem((ServerCommandSource)commandContext.getSource(), EquipmentSlot.MAINHAND),
                                                      arg
                                                   )
                                             )
                                       ))
                                    .then(
                                       CommandManager.literal("offhand")
                                          .executes(
                                             commandContext -> executeFish(
                                                   commandContext,
                                                   IdentifierArgumentType.getIdentifier(commandContext, "loot_table"),
                                                   BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos"),
                                                   getHeldItem((ServerCommandSource)commandContext.getSource(), EquipmentSlot.OFFHAND),
                                                   arg
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
                              .executes(commandContext -> executeLoot(commandContext, IdentifierArgumentType.getIdentifier(commandContext, "loot_table"), arg))
                        )
                  )
                  .then(
                     CommandManager.literal("kill")
                        .then(
                           CommandManager.argument("target", EntityArgumentType.entity())
                              .executes(commandContext -> executeKill(commandContext, EntityArgumentType.getEntity(commandContext, "target"), arg))
                        )
                  )
                  .then(
                     CommandManager.literal("mine")
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                          "pos", BlockPosArgumentType.blockPos()
                                       )
                                       .executes(
                                          commandContext -> executeMine(
                                                commandContext, BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos"), ItemStack.EMPTY, arg
                                             )
                                       ))
                                    .then(
                                       CommandManager.argument("tool", ItemStackArgumentType.itemStack())
                                          .executes(
                                             commandContext -> executeMine(
                                                   commandContext,
                                                   BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos"),
                                                   ItemStackArgumentType.getItemStackArgument(commandContext, "tool").createStack(1, false),
                                                   arg
                                                )
                                          )
                                    ))
                                 .then(
                                    CommandManager.literal("mainhand")
                                       .executes(
                                          commandContext -> executeMine(
                                                commandContext,
                                                BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos"),
                                                getHeldItem((ServerCommandSource)commandContext.getSource(), EquipmentSlot.MAINHAND),
                                                arg
                                             )
                                       )
                                 ))
                              .then(
                                 CommandManager.literal("offhand")
                                    .executes(
                                       commandContext -> executeMine(
                                             commandContext,
                                             BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos"),
                                             getHeldItem((ServerCommandSource)commandContext.getSource(), EquipmentSlot.OFFHAND),
                                             arg
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
                                       (commandContext, list, arg) -> executeReplace(
                                             EntityArgumentType.getEntities(commandContext, "entities"),
                                             ItemSlotArgumentType.getItemSlot(commandContext, "slot"),
                                             list.size(),
                                             list,
                                             arg
                                          )
                                    )
                                    .then(
                                       sourceConstructor.construct(
                                          CommandManager.argument("count", IntegerArgumentType.integer(0)),
                                          (commandContext, list, arg) -> executeReplace(
                                                EntityArgumentType.getEntities(commandContext, "entities"),
                                                ItemSlotArgumentType.getItemSlot(commandContext, "slot"),
                                                IntegerArgumentType.getInteger(commandContext, "count"),
                                                list,
                                                arg
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
                                    (commandContext, list, arg) -> executeBlock(
                                          (ServerCommandSource)commandContext.getSource(),
                                          BlockPosArgumentType.getLoadedBlockPos(commandContext, "targetPos"),
                                          ItemSlotArgumentType.getItemSlot(commandContext, "slot"),
                                          list.size(),
                                          list,
                                          arg
                                       )
                                 )
                                 .then(
                                    sourceConstructor.construct(
                                       CommandManager.argument("count", IntegerArgumentType.integer(0)),
                                       (commandContext, list, arg) -> executeBlock(
                                             (ServerCommandSource)commandContext.getSource(),
                                             BlockPosArgumentType.getLoadedBlockPos(commandContext, "targetPos"),
                                             IntegerArgumentType.getInteger(commandContext, "slot"),
                                             IntegerArgumentType.getInteger(commandContext, "count"),
                                             list,
                                             arg
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
                     (commandContext, list, arg) -> executeInsert(
                           (ServerCommandSource)commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos(commandContext, "targetPos"), list, arg
                        )
                  )
               )
         )
         .then(
            CommandManager.literal("give")
               .then(
                  sourceConstructor.construct(
                     CommandManager.argument("players", EntityArgumentType.players()),
                     (commandContext, list, arg) -> executeGive(EntityArgumentType.getPlayers(commandContext, "players"), list, arg)
                  )
               )
         )
         .then(
            CommandManager.literal("spawn")
               .then(
                  sourceConstructor.construct(
                     CommandManager.argument("targetPos", Vec3ArgumentType.vec3()),
                     (commandContext, list, arg) -> executeSpawn(
                           (ServerCommandSource)commandContext.getSource(), Vec3ArgumentType.getVec3(commandContext, "targetPos"), list, arg
                        )
                  )
               )
         );
   }

   private static Inventory getBlockInventory(ServerCommandSource source, BlockPos pos) throws CommandSyntaxException {
      BlockEntity lv = source.getWorld().getBlockEntity(pos);
      if (!(lv instanceof Inventory)) {
         throw ReplaceItemCommand.BLOCK_FAILED_EXCEPTION.create();
      } else {
         return (Inventory)lv;
      }
   }

   private static int executeInsert(ServerCommandSource source, BlockPos targetPos, List<ItemStack> stacks, LootCommand.FeedbackMessage messageSender) throws CommandSyntaxException {
      Inventory lv = getBlockInventory(source, targetPos);
      List<ItemStack> list2 = Lists.newArrayListWithCapacity(stacks.size());

      for (ItemStack lv2 : stacks) {
         if (insert(lv, lv2.copy())) {
            lv.markDirty();
            list2.add(lv2);
         }
      }

      messageSender.accept(list2);
      return list2.size();
   }

   private static boolean insert(Inventory inventory, ItemStack stack) {
      boolean bl = false;

      for (int i = 0; i < inventory.size() && !stack.isEmpty(); i++) {
         ItemStack lv = inventory.getStack(i);
         if (inventory.isValid(i, stack)) {
            if (lv.isEmpty()) {
               inventory.setStack(i, stack);
               bl = true;
               break;
            }

            if (itemsMatch(lv, stack)) {
               int j = stack.getMaxCount() - lv.getCount();
               int k = Math.min(stack.getCount(), j);
               stack.decrement(k);
               lv.increment(k);
               bl = true;
            }
         }
      }

      return bl;
   }

   private static int executeBlock(
      ServerCommandSource source, BlockPos targetPos, int slot, int stackCount, List<ItemStack> stacks, LootCommand.FeedbackMessage messageSender
   ) throws CommandSyntaxException {
      Inventory lv = getBlockInventory(source, targetPos);
      int k = lv.size();
      if (slot >= 0 && slot < k) {
         List<ItemStack> list2 = Lists.newArrayListWithCapacity(stacks.size());

         for (int l = 0; l < stackCount; l++) {
            int m = slot + l;
            ItemStack lv2 = l < stacks.size() ? stacks.get(l) : ItemStack.EMPTY;
            if (lv.isValid(m, lv2)) {
               lv.setStack(m, lv2);
               list2.add(lv2);
            }
         }

         messageSender.accept(list2);
         return list2.size();
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
      List<ItemStack> list2 = Lists.newArrayListWithCapacity(stacks.size());

      for (ItemStack lv : stacks) {
         for (ServerPlayerEntity lv2 : players) {
            if (lv2.inventory.insertStack(lv.copy())) {
               list2.add(lv);
            }
         }
      }

      messageSender.accept(list2);
      return list2.size();
   }

   private static void replace(Entity entity, List<ItemStack> stacks, int slot, int stackCount, List<ItemStack> addedStacks) {
      for (int k = 0; k < stackCount; k++) {
         ItemStack lv = k < stacks.size() ? stacks.get(k) : ItemStack.EMPTY;
         if (entity.equip(slot + k, lv.copy())) {
            addedStacks.add(lv);
         }
      }
   }

   private static int executeReplace(
      Collection<? extends Entity> targets, int slot, int stackCount, List<ItemStack> stacks, LootCommand.FeedbackMessage messageSender
   ) throws CommandSyntaxException {
      List<ItemStack> list2 = Lists.newArrayListWithCapacity(stacks.size());

      for (Entity lv : targets) {
         if (lv instanceof ServerPlayerEntity) {
            ServerPlayerEntity lv2 = (ServerPlayerEntity)lv;
            lv2.playerScreenHandler.sendContentUpdates();
            replace(lv, stacks, slot, stackCount, list2);
            lv2.playerScreenHandler.sendContentUpdates();
         } else {
            replace(lv, stacks, slot, stackCount, list2);
         }
      }

      messageSender.accept(list2);
      return list2.size();
   }

   private static int executeSpawn(ServerCommandSource source, Vec3d pos, List<ItemStack> stacks, LootCommand.FeedbackMessage messageSender) throws CommandSyntaxException {
      ServerWorld lv = source.getWorld();
      stacks.forEach(arg3 -> {
         ItemEntity lvx = new ItemEntity(lv, pos.x, pos.y, pos.z, arg3.copy());
         lvx.setToDefaultPickupDelay();
         lv.spawnEntity(lvx);
      });
      messageSender.accept(stacks);
      return stacks.size();
   }

   private static void sendDroppedFeedback(ServerCommandSource source, List<ItemStack> stacks) {
      if (stacks.size() == 1) {
         ItemStack lv = stacks.get(0);
         source.sendFeedback(new TranslatableText("commands.drop.success.single", lv.getCount(), lv.toHoverableText()), false);
      } else {
         source.sendFeedback(new TranslatableText("commands.drop.success.multiple", stacks.size()), false);
      }
   }

   private static void sendDroppedFeedback(ServerCommandSource source, List<ItemStack> stacks, Identifier lootTable) {
      if (stacks.size() == 1) {
         ItemStack lv = stacks.get(0);
         source.sendFeedback(new TranslatableText("commands.drop.success.single_with_table", lv.getCount(), lv.toHoverableText(), lootTable), false);
      } else {
         source.sendFeedback(new TranslatableText("commands.drop.success.multiple_with_table", stacks.size(), lootTable), false);
      }
   }

   private static ItemStack getHeldItem(ServerCommandSource source, EquipmentSlot slot) throws CommandSyntaxException {
      Entity lv = source.getEntityOrThrow();
      if (lv instanceof LivingEntity) {
         return ((LivingEntity)lv).getEquippedStack(slot);
      } else {
         throw NO_HELD_ITEMS_EXCEPTION.create(lv.getDisplayName());
      }
   }

   private static int executeMine(CommandContext<ServerCommandSource> context, BlockPos pos, ItemStack stack, LootCommand.Target constructor) throws CommandSyntaxException {
      ServerCommandSource lv = (ServerCommandSource)context.getSource();
      ServerWorld lv2 = lv.getWorld();
      BlockState lv3 = lv2.getBlockState(pos);
      BlockEntity lv4 = lv2.getBlockEntity(pos);
      LootContext.Builder lv5 = new LootContext.Builder(lv2)
         .parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
         .parameter(LootContextParameters.BLOCK_STATE, lv3)
         .optionalParameter(LootContextParameters.BLOCK_ENTITY, lv4)
         .optionalParameter(LootContextParameters.THIS_ENTITY, lv.getEntity())
         .parameter(LootContextParameters.TOOL, stack);
      List<ItemStack> list = lv3.getDroppedStacks(lv5);
      return constructor.accept(context, list, listx -> sendDroppedFeedback(lv, listx, lv3.getBlock().getLootTableId()));
   }

   private static int executeKill(CommandContext<ServerCommandSource> context, Entity entity, LootCommand.Target constructor) throws CommandSyntaxException {
      if (!(entity instanceof LivingEntity)) {
         throw NO_LOOT_TABLE_EXCEPTION.create(entity.getDisplayName());
      } else {
         Identifier lv = ((LivingEntity)entity).getLootTable();
         ServerCommandSource lv2 = (ServerCommandSource)context.getSource();
         LootContext.Builder lv3 = new LootContext.Builder(lv2.getWorld());
         Entity lv4 = lv2.getEntity();
         if (lv4 instanceof PlayerEntity) {
            lv3.parameter(LootContextParameters.LAST_DAMAGE_PLAYER, (PlayerEntity)lv4);
         }

         lv3.parameter(LootContextParameters.DAMAGE_SOURCE, DamageSource.MAGIC);
         lv3.optionalParameter(LootContextParameters.DIRECT_KILLER_ENTITY, lv4);
         lv3.optionalParameter(LootContextParameters.KILLER_ENTITY, lv4);
         lv3.parameter(LootContextParameters.THIS_ENTITY, entity);
         lv3.parameter(LootContextParameters.ORIGIN, lv2.getPosition());
         LootTable lv5 = lv2.getMinecraftServer().getLootManager().getTable(lv);
         List<ItemStack> list = lv5.generateLoot(lv3.build(LootContextTypes.ENTITY));
         return constructor.accept(context, list, listx -> sendDroppedFeedback(lv2, listx, lv));
      }
   }

   private static int executeLoot(CommandContext<ServerCommandSource> context, Identifier lootTable, LootCommand.Target constructor) throws CommandSyntaxException {
      ServerCommandSource lv = (ServerCommandSource)context.getSource();
      LootContext.Builder lv2 = new LootContext.Builder(lv.getWorld())
         .optionalParameter(LootContextParameters.THIS_ENTITY, lv.getEntity())
         .parameter(LootContextParameters.ORIGIN, lv.getPosition());
      return getFeedbackMessageSingle(context, lootTable, lv2.build(LootContextTypes.CHEST), constructor);
   }

   private static int executeFish(
      CommandContext<ServerCommandSource> context, Identifier lootTable, BlockPos pos, ItemStack stack, LootCommand.Target constructor
   ) throws CommandSyntaxException {
      ServerCommandSource lv = (ServerCommandSource)context.getSource();
      LootContext lv2 = new LootContext.Builder(lv.getWorld())
         .parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
         .parameter(LootContextParameters.TOOL, stack)
         .optionalParameter(LootContextParameters.THIS_ENTITY, lv.getEntity())
         .build(LootContextTypes.FISHING);
      return getFeedbackMessageSingle(context, lootTable, lv2, constructor);
   }

   private static int getFeedbackMessageSingle(
      CommandContext<ServerCommandSource> context, Identifier lootTable, LootContext lootContext, LootCommand.Target constructor
   ) throws CommandSyntaxException {
      ServerCommandSource lv = (ServerCommandSource)context.getSource();
      LootTable lv2 = lv.getMinecraftServer().getLootManager().getTable(lootTable);
      List<ItemStack> list = lv2.generateLoot(lootContext);
      return constructor.accept(context, list, listx -> sendDroppedFeedback(lv, listx));
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
