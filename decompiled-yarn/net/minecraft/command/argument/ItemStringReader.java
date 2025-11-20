package net.minecraft.command.argument;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.state.property.Property;
import net.minecraft.tag.TagGroup;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemStringReader {
   public static final SimpleCommandExceptionType TAG_DISALLOWED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("argument.item.tag.disallowed")
   );
   public static final DynamicCommandExceptionType ID_INVALID_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("argument.item.id.invalid", _snowman)
   );
   private static final BiFunction<SuggestionsBuilder, TagGroup<Item>, CompletableFuture<Suggestions>> NBT_SUGGESTION_PROVIDER = (_snowman, _snowmanx) -> _snowman.buildFuture();
   private final StringReader reader;
   private final boolean allowTag;
   private final Map<Property<?>, Comparable<?>> field_10801 = Maps.newHashMap();
   private Item item;
   @Nullable
   private CompoundTag tag;
   private Identifier id = new Identifier("");
   private int cursor;
   private BiFunction<SuggestionsBuilder, TagGroup<Item>, CompletableFuture<Suggestions>> suggestions = NBT_SUGGESTION_PROVIDER;

   public ItemStringReader(StringReader reader, boolean allowTag) {
      this.reader = reader;
      this.allowTag = allowTag;
   }

   public Item getItem() {
      return this.item;
   }

   @Nullable
   public CompoundTag getTag() {
      return this.tag;
   }

   public Identifier getId() {
      return this.id;
   }

   public void readItem() throws CommandSyntaxException {
      int _snowman = this.reader.getCursor();
      Identifier _snowmanx = Identifier.fromCommandInput(this.reader);
      this.item = Registry.ITEM.getOrEmpty(_snowmanx).orElseThrow(() -> {
         this.reader.setCursor(_snowman);
         return ID_INVALID_EXCEPTION.createWithContext(this.reader, _snowman.toString());
      });
   }

   public void readTag() throws CommandSyntaxException {
      if (!this.allowTag) {
         throw TAG_DISALLOWED_EXCEPTION.create();
      } else {
         this.suggestions = this::suggestTag;
         this.reader.expect('#');
         this.cursor = this.reader.getCursor();
         this.id = Identifier.fromCommandInput(this.reader);
      }
   }

   public void readNbt() throws CommandSyntaxException {
      this.tag = new StringNbtReader(this.reader).parseCompoundTag();
   }

   public ItemStringReader consume() throws CommandSyntaxException {
      this.suggestions = this::suggestAny;
      if (this.reader.canRead() && this.reader.peek() == '#') {
         this.readTag();
      } else {
         this.readItem();
         this.suggestions = this::suggestItem;
      }

      if (this.reader.canRead() && this.reader.peek() == '{') {
         this.suggestions = NBT_SUGGESTION_PROVIDER;
         this.readNbt();
      }

      return this;
   }

   private CompletableFuture<Suggestions> suggestItem(SuggestionsBuilder _snowman, TagGroup<Item> _snowman) {
      if (_snowman.getRemaining().isEmpty()) {
         _snowman.suggest(String.valueOf('{'));
      }

      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestTag(SuggestionsBuilder suggestionsBuilder, TagGroup<Item> _snowman) {
      return CommandSource.suggestIdentifiers(_snowman.getTagIds(), suggestionsBuilder.createOffset(this.cursor));
   }

   private CompletableFuture<Suggestions> suggestAny(SuggestionsBuilder _snowman, TagGroup<Item> _snowman) {
      if (this.allowTag) {
         CommandSource.suggestIdentifiers(_snowman.getTagIds(), _snowman, String.valueOf('#'));
      }

      return CommandSource.suggestIdentifiers(Registry.ITEM.getIds(), _snowman);
   }

   public CompletableFuture<Suggestions> getSuggestions(SuggestionsBuilder builder, TagGroup<Item> _snowman) {
      return this.suggestions.apply(builder.createOffset(this.reader.getCursor()), _snowman);
   }
}
