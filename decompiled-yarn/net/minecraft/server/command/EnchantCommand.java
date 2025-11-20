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
      _snowman -> new TranslatableText("commands.enchant.failed.entity", _snowman)
   );
   private static final DynamicCommandExceptionType FAILED_ITEMLESS_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.enchant.failed.itemless", _snowman)
   );
   private static final DynamicCommandExceptionType FAILED_INCOMPATIBLE_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.enchant.failed.incompatible", _snowman)
   );
   private static final Dynamic2CommandExceptionType FAILED_LEVEL_EXCEPTION = new Dynamic2CommandExceptionType(
      (_snowman, _snowmanx) -> new TranslatableText("commands.enchant.failed.level", _snowman, _snowmanx)
   );
   private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.enchant.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("enchant").requires(_snowman -> _snowman.hasPermissionLevel(2)))
            .then(
               CommandManager.argument("targets", EntityArgumentType.entities())
                  .then(
                     ((RequiredArgumentBuilder)CommandManager.argument("enchantment", ItemEnchantmentArgumentType.itemEnchantment())
                           .executes(
                              _snowman -> execute(
                                    (ServerCommandSource)_snowman.getSource(),
                                    EntityArgumentType.getEntities(_snowman, "targets"),
                                    ItemEnchantmentArgumentType.getEnchantment(_snowman, "enchantment"),
                                    1
                                 )
                           ))
                        .then(
                           CommandManager.argument("level", IntegerArgumentType.integer(0))
                              .executes(
                                 _snowman -> execute(
                                       (ServerCommandSource)_snowman.getSource(),
                                       EntityArgumentType.getEntities(_snowman, "targets"),
                                       ItemEnchantmentArgumentType.getEnchantment(_snowman, "enchantment"),
                                       IntegerArgumentType.getInteger(_snowman, "level")
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
         int _snowman = 0;

         for (Entity _snowmanx : targets) {
            if (_snowmanx instanceof LivingEntity) {
               LivingEntity _snowmanxx = (LivingEntity)_snowmanx;
               ItemStack _snowmanxxx = _snowmanxx.getMainHandStack();
               if (!_snowmanxxx.isEmpty()) {
                  if (enchantment.isAcceptableItem(_snowmanxxx) && EnchantmentHelper.isCompatible(EnchantmentHelper.get(_snowmanxxx).keySet(), enchantment)) {
                     _snowmanxxx.addEnchantment(enchantment, level);
                     _snowman++;
                  } else if (targets.size() == 1) {
                     throw FAILED_INCOMPATIBLE_EXCEPTION.create(_snowmanxxx.getItem().getName(_snowmanxxx).getString());
                  }
               } else if (targets.size() == 1) {
                  throw FAILED_ITEMLESS_EXCEPTION.create(_snowmanxx.getName().getString());
               }
            } else if (targets.size() == 1) {
               throw FAILED_ENTITY_EXCEPTION.create(_snowmanx.getName().getString());
            }
         }

         if (_snowman == 0) {
            throw FAILED_EXCEPTION.create();
         } else {
            if (targets.size() == 1) {
               source.sendFeedback(
                  new TranslatableText("commands.enchant.success.single", enchantment.getName(level), targets.iterator().next().getDisplayName()), true
               );
            } else {
               source.sendFeedback(new TranslatableText("commands.enchant.success.multiple", enchantment.getName(level), targets.size()), true);
            }

            return _snowman;
         }
      }
   }
}
