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
      _snowman -> new TranslatableText("arguments.item.tag.unknown", _snowman)
   );

   public ItemPredicateArgumentType() {
   }

   public static ItemPredicateArgumentType itemPredicate() {
      return new ItemPredicateArgumentType();
   }

   public ItemPredicateArgumentType.ItemPredicateArgument parse(StringReader _snowman) throws CommandSyntaxException {
      ItemStringReader _snowmanx = new ItemStringReader(_snowman, true).consume();
      if (_snowmanx.getItem() != null) {
         ItemPredicateArgumentType.ItemPredicate _snowmanxx = new ItemPredicateArgumentType.ItemPredicate(_snowmanx.getItem(), _snowmanx.getTag());
         return _snowmanxxx -> _snowman;
      } else {
         Identifier _snowmanxx = _snowmanx.getId();
         return _snowmanxxx -> {
            Tag<Item> _snowmanxxx = ((ServerCommandSource)_snowmanxxx.getSource()).getMinecraftServer().getTagManager().getItems().getTag(_snowman);
            if (_snowmanxxx == null) {
               throw UNKNOWN_TAG_EXCEPTION.create(_snowman.toString());
            } else {
               return new ItemPredicateArgumentType.TagPredicate(_snowmanxxx, _snowman.getTag());
            }
         };
      }
   }

   public static Predicate<ItemStack> getItemPredicate(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      return ((ItemPredicateArgumentType.ItemPredicateArgument)context.getArgument(name, ItemPredicateArgumentType.ItemPredicateArgument.class))
         .create(context);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      StringReader _snowman = new StringReader(builder.getInput());
      _snowman.setCursor(builder.getStart());
      ItemStringReader _snowmanx = new ItemStringReader(_snowman, true);

      try {
         _snowmanx.consume();
      } catch (CommandSyntaxException var6) {
      }

      return _snowmanx.getSuggestions(builder, ItemTags.getTagGroup());
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   static class ItemPredicate implements Predicate<ItemStack> {
      private final Item item;
      @Nullable
      private final CompoundTag compound;

      public ItemPredicate(Item _snowman, @Nullable CompoundTag _snowman) {
         this.item = _snowman;
         this.compound = _snowman;
      }

      public boolean test(ItemStack _snowman) {
         return _snowman.getItem() == this.item && NbtHelper.matches(this.compound, _snowman.getTag(), true);
      }
   }

   public interface ItemPredicateArgument {
      Predicate<ItemStack> create(CommandContext<ServerCommandSource> var1) throws CommandSyntaxException;
   }

   static class TagPredicate implements Predicate<ItemStack> {
      private final Tag<Item> tag;
      @Nullable
      private final CompoundTag compound;

      public TagPredicate(Tag<Item> _snowman, @Nullable CompoundTag _snowman) {
         this.tag = _snowman;
         this.compound = _snowman;
      }

      public boolean test(ItemStack _snowman) {
         return this.tag.contains(_snowman.getItem()) && NbtHelper.matches(this.compound, _snowman.getTag(), true);
      }
   }
}
