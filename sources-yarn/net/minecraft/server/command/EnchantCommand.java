package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.ItemEnchantmentArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;

public class EnchantCommand {
   private static final DynamicCommandExceptionType FAILED_ENTITY_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("commands.enchant.failed.entity", object)
   );
   private static final DynamicCommandExceptionType FAILED_ITEMLESS_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("commands.enchant.failed.itemless", object)
   );
   private static final DynamicCommandExceptionType FAILED_INCOMPATIBLE_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("commands.enchant.failed.incompatible", object)
   );
   private static final Dynamic2CommandExceptionType FAILED_LEVEL_EXCEPTION = new Dynamic2CommandExceptionType(
      (object, object2) -> new TranslatableText("commands.enchant.failed.level", object, object2)
   );
   private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.enchant.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("enchant").requires(arg -> arg.hasPermissionLevel(2)))
            .then(
               CommandManager.argument("targets", EntityArgumentType.entities())
                  .then(
                     ((RequiredArgumentBuilder)CommandManager.argument("enchantment", ItemEnchantmentArgumentType.itemEnchantment())
                           .executes(
                              commandContext -> execute(
                                    (ServerCommandSource)commandContext.getSource(),
                                    EntityArgumentType.getEntities(commandContext, "targets"),
                                    ItemEnchantmentArgumentType.getEnchantment(commandContext, "enchantment"),
                                    1
                                 )
                           ))
                        .then(
                           CommandManager.argument("level", IntegerArgumentType.integer(0))
                              .executes(
                                 commandContext -> execute(
                                       (ServerCommandSource)commandContext.getSource(),
                                       EntityArgumentType.getEntities(commandContext, "targets"),
                                       ItemEnchantmentArgumentType.getEnchantment(commandContext, "enchantment"),
                                       IntegerArgumentType.getInteger(commandContext, "level")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int execute(ServerCommandSource source, Collection<? extends Entity> targets, Enchantment enchantment, int level) throws CommandSyntaxException {
      if (level > enchantment.getMaxLevel()) {
         throw FAILED_LEVEL_EXCEPTION.create(level, enchantment.getMaxLevel());
      } else {
         int j = 0;

         for (Entity lv : targets) {
            if (lv instanceof LivingEntity) {
               LivingEntity lv2 = (LivingEntity)lv;
               ItemStack lv3 = lv2.getMainHandStack();
               if (!lv3.isEmpty()) {
                  if (enchantment.isAcceptableItem(lv3) && EnchantmentHelper.isCompatible(EnchantmentHelper.get(lv3).keySet(), enchantment)) {
                     lv3.addEnchantment(enchantment, level);
                     j++;
                  } else if (targets.size() == 1) {
                     throw FAILED_INCOMPATIBLE_EXCEPTION.create(lv3.getItem().getName(lv3).getString());
                  }
               } else if (targets.size() == 1) {
                  throw FAILED_ITEMLESS_EXCEPTION.create(lv2.getName().getString());
               }
            } else if (targets.size() == 1) {
               throw FAILED_ENTITY_EXCEPTION.create(lv.getName().getString());
            }
         }

         if (j == 0) {
            throw FAILED_EXCEPTION.create();
         } else {
            if (targets.size() == 1) {
               source.sendFeedback(
                  new TranslatableText("commands.enchant.success.single", enchantment.getName(level), targets.iterator().next().getDisplayName()), true
               );
            } else {
               source.sendFeedback(new TranslatableText("commands.enchant.success.multiple", enchantment.getName(level), targets.size()), true);
            }

            return j;
         }
      }
   }
}
