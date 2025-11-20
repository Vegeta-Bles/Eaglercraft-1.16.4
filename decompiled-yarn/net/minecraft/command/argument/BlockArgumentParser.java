package net.minecraft.command.argument;

import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroup;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockArgumentParser {
   public static final SimpleCommandExceptionType DISALLOWED_TAG_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("argument.block.tag.disallowed")
   );
   public static final DynamicCommandExceptionType INVALID_BLOCK_ID_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("argument.block.id.invalid", _snowman)
   );
   public static final Dynamic2CommandExceptionType UNKNOWN_PROPERTY_EXCEPTION = new Dynamic2CommandExceptionType(
      (_snowman, _snowmanx) -> new TranslatableText("argument.block.property.unknown", _snowman, _snowmanx)
   );
   public static final Dynamic2CommandExceptionType DUPLICATE_PROPERTY_EXCEPTION = new Dynamic2CommandExceptionType(
      (_snowman, _snowmanx) -> new TranslatableText("argument.block.property.duplicate", _snowmanx, _snowman)
   );
   public static final Dynamic3CommandExceptionType INVALID_PROPERTY_EXCEPTION = new Dynamic3CommandExceptionType(
      (_snowman, _snowmanx, _snowmanxx) -> new TranslatableText("argument.block.property.invalid", _snowman, _snowmanxx, _snowmanx)
   );
   public static final Dynamic2CommandExceptionType EMPTY_PROPERTY_EXCEPTION = new Dynamic2CommandExceptionType(
      (_snowman, _snowmanx) -> new TranslatableText("argument.block.property.novalue", _snowman, _snowmanx)
   );
   public static final SimpleCommandExceptionType UNCLOSED_PROPERTIES_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("argument.block.property.unclosed")
   );
   private static final BiFunction<SuggestionsBuilder, TagGroup<Block>, CompletableFuture<Suggestions>> SUGGEST_DEFAULT = (_snowman, _snowmanx) -> _snowman.buildFuture();
   private final StringReader reader;
   private final boolean allowTag;
   private final Map<Property<?>, Comparable<?>> blockProperties = Maps.newHashMap();
   private final Map<String, String> tagProperties = Maps.newHashMap();
   private Identifier blockId = new Identifier("");
   private StateManager<Block, BlockState> stateFactory;
   private BlockState blockState;
   @Nullable
   private CompoundTag data;
   private Identifier tagId = new Identifier("");
   private int cursorPos;
   private BiFunction<SuggestionsBuilder, TagGroup<Block>, CompletableFuture<Suggestions>> suggestions = SUGGEST_DEFAULT;

   public BlockArgumentParser(StringReader reader, boolean allowTag) {
      this.reader = reader;
      this.allowTag = allowTag;
   }

   public Map<Property<?>, Comparable<?>> getBlockProperties() {
      return this.blockProperties;
   }

   @Nullable
   public BlockState getBlockState() {
      return this.blockState;
   }

   @Nullable
   public CompoundTag getNbtData() {
      return this.data;
   }

   @Nullable
   public Identifier getTagId() {
      return this.tagId;
   }

   public BlockArgumentParser parse(boolean allowNbt) throws CommandSyntaxException {
      this.suggestions = this::suggestBlockOrTagId;
      if (this.reader.canRead() && this.reader.peek() == '#') {
         this.parseTagId();
         this.suggestions = this::suggestSnbtOrTagProperties;
         if (this.reader.canRead() && this.reader.peek() == '[') {
            this.parseTagProperties();
            this.suggestions = this::suggestSnbt;
         }
      } else {
         this.parseBlockId();
         this.suggestions = this::suggestSnbtOrBlockProperties;
         if (this.reader.canRead() && this.reader.peek() == '[') {
            this.parseBlockProperties();
            this.suggestions = this::suggestSnbt;
         }
      }

      if (allowNbt && this.reader.canRead() && this.reader.peek() == '{') {
         this.suggestions = SUGGEST_DEFAULT;
         this.parseSnbt();
      }

      return this;
   }

   private CompletableFuture<Suggestions> suggestBlockPropertiesOrEnd(SuggestionsBuilder _snowman, TagGroup<Block> _snowman) {
      if (_snowman.getRemaining().isEmpty()) {
         _snowman.suggest(String.valueOf(']'));
      }

      return this.suggestBlockProperties(_snowman, _snowman);
   }

   private CompletableFuture<Suggestions> suggestTagPropertiesOrEnd(SuggestionsBuilder _snowman, TagGroup<Block> _snowman) {
      if (_snowman.getRemaining().isEmpty()) {
         _snowman.suggest(String.valueOf(']'));
      }

      return this.suggestTagProperties(_snowman, _snowman);
   }

   private CompletableFuture<Suggestions> suggestBlockProperties(SuggestionsBuilder _snowman, TagGroup<Block> _snowman) {
      String _snowmanxx = _snowman.getRemaining().toLowerCase(Locale.ROOT);

      for (Property<?> _snowmanxxx : this.blockState.getProperties()) {
         if (!this.blockProperties.containsKey(_snowmanxxx) && _snowmanxxx.getName().startsWith(_snowmanxx)) {
            _snowman.suggest(_snowmanxxx.getName() + '=');
         }
      }

      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestTagProperties(SuggestionsBuilder _snowman, TagGroup<Block> _snowman) {
      String _snowmanxx = _snowman.getRemaining().toLowerCase(Locale.ROOT);
      if (this.tagId != null && !this.tagId.getPath().isEmpty()) {
         Tag<Block> _snowmanxxx = _snowman.getTag(this.tagId);
         if (_snowmanxxx != null) {
            for (Block _snowmanxxxx : _snowmanxxx.values()) {
               for (Property<?> _snowmanxxxxx : _snowmanxxxx.getStateManager().getProperties()) {
                  if (!this.tagProperties.containsKey(_snowmanxxxxx.getName()) && _snowmanxxxxx.getName().startsWith(_snowmanxx)) {
                     _snowman.suggest(_snowmanxxxxx.getName() + '=');
                  }
               }
            }
         }
      }

      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestSnbt(SuggestionsBuilder _snowman, TagGroup<Block> _snowman) {
      if (_snowman.getRemaining().isEmpty() && this.hasBlockEntity(_snowman)) {
         _snowman.suggest(String.valueOf('{'));
      }

      return _snowman.buildFuture();
   }

   private boolean hasBlockEntity(TagGroup<Block> _snowman) {
      if (this.blockState != null) {
         return this.blockState.getBlock().hasBlockEntity();
      } else {
         if (this.tagId != null) {
            Tag<Block> _snowmanx = _snowman.getTag(this.tagId);
            if (_snowmanx != null) {
               for (Block _snowmanxx : _snowmanx.values()) {
                  if (_snowmanxx.hasBlockEntity()) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   private CompletableFuture<Suggestions> suggestEqualsCharacter(SuggestionsBuilder _snowman, TagGroup<Block> _snowman) {
      if (_snowman.getRemaining().isEmpty()) {
         _snowman.suggest(String.valueOf('='));
      }

      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestCommaOrEnd(SuggestionsBuilder _snowman, TagGroup<Block> _snowman) {
      if (_snowman.getRemaining().isEmpty()) {
         _snowman.suggest(String.valueOf(']'));
      }

      if (_snowman.getRemaining().isEmpty() && this.blockProperties.size() < this.blockState.getProperties().size()) {
         _snowman.suggest(String.valueOf(','));
      }

      return _snowman.buildFuture();
   }

   private static <T extends Comparable<T>> SuggestionsBuilder suggestPropertyValues(SuggestionsBuilder _snowman, Property<T> _snowman) {
      for (T _snowmanxx : _snowman.getValues()) {
         if (_snowmanxx instanceof Integer) {
            _snowman.suggest((Integer)_snowmanxx);
         } else {
            _snowman.suggest(_snowman.name(_snowmanxx));
         }
      }

      return _snowman;
   }

   private CompletableFuture<Suggestions> suggestTagPropertyValues(SuggestionsBuilder _snowman, TagGroup<Block> _snowman, String _snowman) {
      boolean _snowmanxxx = false;
      if (this.tagId != null && !this.tagId.getPath().isEmpty()) {
         Tag<Block> _snowmanxxxx = _snowman.getTag(this.tagId);
         if (_snowmanxxxx != null) {
            for (Block _snowmanxxxxx : _snowmanxxxx.values()) {
               Property<?> _snowmanxxxxxx = _snowmanxxxxx.getStateManager().getProperty(_snowman);
               if (_snowmanxxxxxx != null) {
                  suggestPropertyValues(_snowman, _snowmanxxxxxx);
               }

               if (!_snowmanxxx) {
                  for (Property<?> _snowmanxxxxxxx : _snowmanxxxxx.getStateManager().getProperties()) {
                     if (!this.tagProperties.containsKey(_snowmanxxxxxxx.getName())) {
                        _snowmanxxx = true;
                        break;
                     }
                  }
               }
            }
         }
      }

      if (_snowmanxxx) {
         _snowman.suggest(String.valueOf(','));
      }

      _snowman.suggest(String.valueOf(']'));
      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestSnbtOrTagProperties(SuggestionsBuilder _snowman, TagGroup<Block> _snowman) {
      if (_snowman.getRemaining().isEmpty()) {
         Tag<Block> _snowmanxx = _snowman.getTag(this.tagId);
         if (_snowmanxx != null) {
            boolean _snowmanxxx = false;
            boolean _snowmanxxxx = false;

            for (Block _snowmanxxxxx : _snowmanxx.values()) {
               _snowmanxxx |= !_snowmanxxxxx.getStateManager().getProperties().isEmpty();
               _snowmanxxxx |= _snowmanxxxxx.hasBlockEntity();
               if (_snowmanxxx && _snowmanxxxx) {
                  break;
               }
            }

            if (_snowmanxxx) {
               _snowman.suggest(String.valueOf('['));
            }

            if (_snowmanxxxx) {
               _snowman.suggest(String.valueOf('{'));
            }
         }
      }

      return this.suggestIdentifiers(_snowman, _snowman);
   }

   private CompletableFuture<Suggestions> suggestSnbtOrBlockProperties(SuggestionsBuilder _snowman, TagGroup<Block> _snowman) {
      if (_snowman.getRemaining().isEmpty()) {
         if (!this.blockState.getBlock().getStateManager().getProperties().isEmpty()) {
            _snowman.suggest(String.valueOf('['));
         }

         if (this.blockState.getBlock().hasBlockEntity()) {
            _snowman.suggest(String.valueOf('{'));
         }
      }

      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> suggestIdentifiers(SuggestionsBuilder _snowman, TagGroup<Block> _snowman) {
      return CommandSource.suggestIdentifiers(_snowman.getTagIds(), _snowman.createOffset(this.cursorPos).add(_snowman));
   }

   private CompletableFuture<Suggestions> suggestBlockOrTagId(SuggestionsBuilder _snowman, TagGroup<Block> _snowman) {
      if (this.allowTag) {
         CommandSource.suggestIdentifiers(_snowman.getTagIds(), _snowman, String.valueOf('#'));
      }

      CommandSource.suggestIdentifiers(Registry.BLOCK.getIds(), _snowman);
      return _snowman.buildFuture();
   }

   public void parseBlockId() throws CommandSyntaxException {
      int _snowman = this.reader.getCursor();
      this.blockId = Identifier.fromCommandInput(this.reader);
      Block _snowmanx = Registry.BLOCK.getOrEmpty(this.blockId).orElseThrow(() -> {
         this.reader.setCursor(_snowman);
         return INVALID_BLOCK_ID_EXCEPTION.createWithContext(this.reader, this.blockId.toString());
      });
      this.stateFactory = _snowmanx.getStateManager();
      this.blockState = _snowmanx.getDefaultState();
   }

   public void parseTagId() throws CommandSyntaxException {
      if (!this.allowTag) {
         throw DISALLOWED_TAG_EXCEPTION.create();
      } else {
         this.suggestions = this::suggestIdentifiers;
         this.reader.expect('#');
         this.cursorPos = this.reader.getCursor();
         this.tagId = Identifier.fromCommandInput(this.reader);
      }
   }

   public void parseBlockProperties() throws CommandSyntaxException {
      this.reader.skip();
      this.suggestions = this::suggestBlockPropertiesOrEnd;
      this.reader.skipWhitespace();

      while (this.reader.canRead() && this.reader.peek() != ']') {
         this.reader.skipWhitespace();
         int _snowman = this.reader.getCursor();
         String _snowmanx = this.reader.readString();
         Property<?> _snowmanxx = this.stateFactory.getProperty(_snowmanx);
         if (_snowmanxx == null) {
            this.reader.setCursor(_snowman);
            throw UNKNOWN_PROPERTY_EXCEPTION.createWithContext(this.reader, this.blockId.toString(), _snowmanx);
         }

         if (this.blockProperties.containsKey(_snowmanxx)) {
            this.reader.setCursor(_snowman);
            throw DUPLICATE_PROPERTY_EXCEPTION.createWithContext(this.reader, this.blockId.toString(), _snowmanx);
         }

         this.reader.skipWhitespace();
         this.suggestions = this::suggestEqualsCharacter;
         if (!this.reader.canRead() || this.reader.peek() != '=') {
            throw EMPTY_PROPERTY_EXCEPTION.createWithContext(this.reader, this.blockId.toString(), _snowmanx);
         }

         this.reader.skip();
         this.reader.skipWhitespace();
         this.suggestions = (_snowmanxxx, _snowmanxxxx) -> suggestPropertyValues(_snowmanxxx, _snowman).buildFuture();
         int _snowmanxxx = this.reader.getCursor();
         this.parsePropertyValue(_snowmanxx, this.reader.readString(), _snowmanxxx);
         this.suggestions = this::suggestCommaOrEnd;
         this.reader.skipWhitespace();
         if (this.reader.canRead()) {
            if (this.reader.peek() != ',') {
               if (this.reader.peek() != ']') {
                  throw UNCLOSED_PROPERTIES_EXCEPTION.createWithContext(this.reader);
               }
               break;
            }

            this.reader.skip();
            this.suggestions = this::suggestBlockProperties;
         }
      }

      if (this.reader.canRead()) {
         this.reader.skip();
      } else {
         throw UNCLOSED_PROPERTIES_EXCEPTION.createWithContext(this.reader);
      }
   }

   public void parseTagProperties() throws CommandSyntaxException {
      this.reader.skip();
      this.suggestions = this::suggestTagPropertiesOrEnd;
      int _snowman = -1;
      this.reader.skipWhitespace();

      while (this.reader.canRead() && this.reader.peek() != ']') {
         this.reader.skipWhitespace();
         int _snowmanx = this.reader.getCursor();
         String _snowmanxx = this.reader.readString();
         if (this.tagProperties.containsKey(_snowmanxx)) {
            this.reader.setCursor(_snowmanx);
            throw DUPLICATE_PROPERTY_EXCEPTION.createWithContext(this.reader, this.blockId.toString(), _snowmanxx);
         }

         this.reader.skipWhitespace();
         if (!this.reader.canRead() || this.reader.peek() != '=') {
            this.reader.setCursor(_snowmanx);
            throw EMPTY_PROPERTY_EXCEPTION.createWithContext(this.reader, this.blockId.toString(), _snowmanxx);
         }

         this.reader.skip();
         this.reader.skipWhitespace();
         this.suggestions = (_snowmanxxx, _snowmanxxxx) -> this.suggestTagPropertyValues(_snowmanxxx, _snowmanxxxx, _snowman);
         _snowman = this.reader.getCursor();
         String _snowmanxxx = this.reader.readString();
         this.tagProperties.put(_snowmanxx, _snowmanxxx);
         this.reader.skipWhitespace();
         if (this.reader.canRead()) {
            _snowman = -1;
            if (this.reader.peek() != ',') {
               if (this.reader.peek() != ']') {
                  throw UNCLOSED_PROPERTIES_EXCEPTION.createWithContext(this.reader);
               }
               break;
            }

            this.reader.skip();
            this.suggestions = this::suggestTagProperties;
         }
      }

      if (this.reader.canRead()) {
         this.reader.skip();
      } else {
         if (_snowman >= 0) {
            this.reader.setCursor(_snowman);
         }

         throw UNCLOSED_PROPERTIES_EXCEPTION.createWithContext(this.reader);
      }
   }

   public void parseSnbt() throws CommandSyntaxException {
      this.data = new StringNbtReader(this.reader).parseCompoundTag();
   }

   private <T extends Comparable<T>> void parsePropertyValue(Property<T> _snowman, String _snowman, int _snowman) throws CommandSyntaxException {
      Optional<T> _snowmanxxx = _snowman.parse(_snowman);
      if (_snowmanxxx.isPresent()) {
         this.blockState = this.blockState.with(_snowman, _snowmanxxx.get());
         this.blockProperties.put(_snowman, _snowmanxxx.get());
      } else {
         this.reader.setCursor(_snowman);
         throw INVALID_PROPERTY_EXCEPTION.createWithContext(this.reader, this.blockId.toString(), _snowman.getName(), _snowman);
      }
   }

   public static String stringifyBlockState(BlockState _snowman) {
      StringBuilder _snowmanx = new StringBuilder(Registry.BLOCK.getId(_snowman.getBlock()).toString());
      if (!_snowman.getProperties().isEmpty()) {
         _snowmanx.append('[');
         boolean _snowmanxx = false;

         for (UnmodifiableIterator var3 = _snowman.getEntries().entrySet().iterator(); var3.hasNext(); _snowmanxx = true) {
            Entry<Property<?>, Comparable<?>> _snowmanxxx = (Entry<Property<?>, Comparable<?>>)var3.next();
            if (_snowmanxx) {
               _snowmanx.append(',');
            }

            stringifyProperty(_snowmanx, _snowmanxxx.getKey(), _snowmanxxx.getValue());
         }

         _snowmanx.append(']');
      }

      return _snowmanx.toString();
   }

   private static <T extends Comparable<T>> void stringifyProperty(StringBuilder _snowman, Property<T> _snowman, Comparable<?> _snowman) {
      _snowman.append(_snowman.getName());
      _snowman.append('=');
      _snowman.append(_snowman.name((T)_snowman));
   }

   public CompletableFuture<Suggestions> getSuggestions(SuggestionsBuilder _snowman, TagGroup<Block> _snowman) {
      return this.suggestions.apply(_snowman.createOffset(this.reader.getCursor()), _snowman);
   }

   public Map<String, String> getProperties() {
      return this.tagProperties;
   }
}
