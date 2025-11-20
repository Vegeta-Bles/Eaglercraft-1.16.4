package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class ItemPredicateArgumentType implements ArgumentType<ItemPredicateArgumentType.ItemPredicateArgument> {
   private static final Collection<String> EXAMPLES = Arrays.asList("stick", "minecraft:stick", "#stick", "#stick{foo=bar}");
   private static final DynamicCommandExceptionType UNKNOWN_TAG_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("arguments.item.tag.unknown", object)
   );

   public ItemPredicateArgumentType() {
   }

   public static ItemPredicateArgumentType itemPredicate() {
      return new ItemPredicateArgumentType();
   }

   public ItemPredicateArgumentType.ItemPredicateArgument parse(StringReader stringReader) throws CommandSyntaxException {
      ItemStringReader lv = new ItemStringReader(stringReader, true).consume();
      if (lv.getItem() != null) {
         ItemPredicateArgumentType.ItemPredicate lv2 = new ItemPredicateArgumentType.ItemPredicate(lv.getItem(), lv.getTag());
         return commandContext -> lv2;
      } else {
         Identifier lv3 = lv.getId();
         return commandContext -> {
            Tag<Item> lvx = ((ServerCommandSource)commandContext.getSource()).getMinecraftServer().getTagManager().getItems().getTag(lv3);
            if (lvx == null) {
               throw UNKNOWN_TAG_EXCEPTION.create(lv3.toString());
            } else {
               return new ItemPredicateArgumentType.TagPredicate(lvx, lv.getTag());
            }
         };
      }
   }

   public static Predicate<ItemStack> getItemPredicate(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      return ((ItemPredicateArgumentType.ItemPredicateArgument)context.getArgument(name, ItemPredicateArgumentType.ItemPredicateArgument.class))
         .create(context);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      StringReader stringReader = new StringReader(builder.getInput());
      stringReader.setCursor(builder.getStart());
      ItemStringReader lv = new ItemStringReader(stringReader, true);

      try {
         lv.consume();
      } catch (CommandSyntaxException var6) {
      }

      return lv.getSuggestions(builder, ItemTags.getTagGroup());
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   static class ItemPredicate implements Predicate<ItemStack> {
      private final Item item;
      @Nullable
      private final CompoundTag compound;

      public ItemPredicate(Item arg, @Nullable CompoundTag arg2) {
         this.item = arg;
         this.compound = arg2;
      }

      public boolean test(ItemStack arg) {
         return arg.getItem() == this.item && NbtHelper.matches(this.compound, arg.getTag(), true);
      }
   }

   public interface ItemPredicateArgument {
      Predicate<ItemStack> create(CommandContext<ServerCommandSource> commandContext) throws CommandSyntaxException;
   }

   static class TagPredicate implements Predicate<ItemStack> {
      private final Tag<Item> tag;
      @Nullable
      private final CompoundTag compound;

      public TagPredicate(Tag<Item> arg, @Nullable CompoundTag arg2) {
         this.tag = arg;
         this.compound = arg2;
      }

      public boolean test(ItemStack arg) {
         return this.tag.contains(arg.getItem()) && NbtHelper.matches(this.compound, arg.getTag(), true);
      }
   }
}
