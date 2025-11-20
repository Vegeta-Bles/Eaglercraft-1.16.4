package net.minecraft.command.argument;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;

public class ItemSlotArgumentType implements ArgumentType<Integer> {
   private static final Collection<String> EXAMPLES = Arrays.asList("container.5", "12", "weapon");
   private static final DynamicCommandExceptionType UNKNOWN_SLOT_EXCEPTION = new DynamicCommandExceptionType(_snowman -> new TranslatableText("slot.unknown", _snowman));
   private static final Map<String, Integer> slotNamesToSlotCommandId = Util.make(Maps.newHashMap(), _snowman -> {
      for (int _snowmanx = 0; _snowmanx < 54; _snowmanx++) {
         _snowman.put("container." + _snowmanx, _snowmanx);
      }

      for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
         _snowman.put("hotbar." + _snowmanx, _snowmanx);
      }

      for (int _snowmanx = 0; _snowmanx < 27; _snowmanx++) {
         _snowman.put("inventory." + _snowmanx, 9 + _snowmanx);
      }

      for (int _snowmanx = 0; _snowmanx < 27; _snowmanx++) {
         _snowman.put("enderchest." + _snowmanx, 200 + _snowmanx);
      }

      for (int _snowmanx = 0; _snowmanx < 8; _snowmanx++) {
         _snowman.put("villager." + _snowmanx, 300 + _snowmanx);
      }

      for (int _snowmanx = 0; _snowmanx < 15; _snowmanx++) {
         _snowman.put("horse." + _snowmanx, 500 + _snowmanx);
      }

      _snowman.put("weapon", 98);
      _snowman.put("weapon.mainhand", 98);
      _snowman.put("weapon.offhand", 99);
      _snowman.put("armor.head", 100 + EquipmentSlot.HEAD.getEntitySlotId());
      _snowman.put("armor.chest", 100 + EquipmentSlot.CHEST.getEntitySlotId());
      _snowman.put("armor.legs", 100 + EquipmentSlot.LEGS.getEntitySlotId());
      _snowman.put("armor.feet", 100 + EquipmentSlot.FEET.getEntitySlotId());
      _snowman.put("horse.saddle", 400);
      _snowman.put("horse.armor", 401);
      _snowman.put("horse.chest", 499);
   });

   public ItemSlotArgumentType() {
   }

   public static ItemSlotArgumentType itemSlot() {
      return new ItemSlotArgumentType();
   }

   public static int getItemSlot(CommandContext<ServerCommandSource> context, String name) {
      return (Integer)context.getArgument(name, Integer.class);
   }

   public Integer parse(StringReader _snowman) throws CommandSyntaxException {
      String _snowmanx = _snowman.readUnquotedString();
      if (!slotNamesToSlotCommandId.containsKey(_snowmanx)) {
         throw UNKNOWN_SLOT_EXCEPTION.create(_snowmanx);
      } else {
         return slotNamesToSlotCommandId.get(_snowmanx);
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      return CommandSource.suggestMatching(slotNamesToSlotCommandId.keySet(), builder);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
