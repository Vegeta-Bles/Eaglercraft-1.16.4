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
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.state.property.Property;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagManager;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class BlockPredicateArgumentType implements ArgumentType<BlockPredicateArgumentType.BlockPredicate> {
   private static final Collection<String> EXAMPLES = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "#stone", "#stone[foo=bar]{baz=nbt}");
   private static final DynamicCommandExceptionType UNKNOWN_TAG_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("arguments.block.tag.unknown", _snowman)
   );

   public BlockPredicateArgumentType() {
   }

   public static BlockPredicateArgumentType blockPredicate() {
      return new BlockPredicateArgumentType();
   }

   public BlockPredicateArgumentType.BlockPredicate parse(StringReader _snowman) throws CommandSyntaxException {
      BlockArgumentParser _snowmanx = new BlockArgumentParser(_snowman, true).parse(true);
      if (_snowmanx.getBlockState() != null) {
         BlockPredicateArgumentType.StatePredicate _snowmanxx = new BlockPredicateArgumentType.StatePredicate(
            _snowmanx.getBlockState(), _snowmanx.getBlockProperties().keySet(), _snowmanx.getNbtData()
         );
         return _snowmanxxx -> _snowman;
      } else {
         Identifier _snowmanxx = _snowmanx.getTagId();
         return _snowmanxxx -> {
            Tag<Block> _snowmanxxx = _snowmanxxx.getBlocks().getTag(_snowman);
            if (_snowmanxxx == null) {
               throw UNKNOWN_TAG_EXCEPTION.create(_snowman.toString());
            } else {
               return new BlockPredicateArgumentType.TagPredicate(_snowmanxxx, _snowman.getProperties(), _snowman.getNbtData());
            }
         };
      }
   }

   public static Predicate<CachedBlockPosition> getBlockPredicate(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      return ((BlockPredicateArgumentType.BlockPredicate)context.getArgument(name, BlockPredicateArgumentType.BlockPredicate.class))
         .create(((ServerCommandSource)context.getSource()).getMinecraftServer().getTagManager());
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      StringReader _snowman = new StringReader(builder.getInput());
      _snowman.setCursor(builder.getStart());
      BlockArgumentParser _snowmanx = new BlockArgumentParser(_snowman, true);

      try {
         _snowmanx.parse(true);
      } catch (CommandSyntaxException var6) {
      }

      return _snowmanx.getSuggestions(builder, BlockTags.getTagGroup());
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   public interface BlockPredicate {
      Predicate<CachedBlockPosition> create(TagManager var1) throws CommandSyntaxException;
   }

   static class StatePredicate implements Predicate<CachedBlockPosition> {
      private final BlockState state;
      private final Set<Property<?>> properties;
      @Nullable
      private final CompoundTag nbt;

      public StatePredicate(BlockState state, Set<Property<?>> properties, @Nullable CompoundTag nbt) {
         this.state = state;
         this.properties = properties;
         this.nbt = nbt;
      }

      public boolean test(CachedBlockPosition _snowman) {
         BlockState _snowmanx = _snowman.getBlockState();
         if (!_snowmanx.isOf(this.state.getBlock())) {
            return false;
         } else {
            for (Property<?> _snowmanxx : this.properties) {
               if (_snowmanx.get(_snowmanxx) != this.state.get(_snowmanxx)) {
                  return false;
               }
            }

            if (this.nbt == null) {
               return true;
            } else {
               BlockEntity _snowmanxxx = _snowman.getBlockEntity();
               return _snowmanxxx != null && NbtHelper.matches(this.nbt, _snowmanxxx.toTag(new CompoundTag()), true);
            }
         }
      }
   }

   static class TagPredicate implements Predicate<CachedBlockPosition> {
      private final Tag<Block> tag;
      @Nullable
      private final CompoundTag nbt;
      private final Map<String, String> properties;

      private TagPredicate(Tag<Block> _snowman, Map<String, String> _snowman, @Nullable CompoundTag nbt) {
         this.tag = _snowman;
         this.properties = _snowman;
         this.nbt = nbt;
      }

      public boolean test(CachedBlockPosition _snowman) {
         BlockState _snowmanx = _snowman.getBlockState();
         if (!_snowmanx.isIn(this.tag)) {
            return false;
         } else {
            for (Entry<String, String> _snowmanxx : this.properties.entrySet()) {
               Property<?> _snowmanxxx = _snowmanx.getBlock().getStateManager().getProperty(_snowmanxx.getKey());
               if (_snowmanxxx == null) {
                  return false;
               }

               Comparable<?> _snowmanxxxx = (Comparable<?>)_snowmanxxx.parse(_snowmanxx.getValue()).orElse(null);
               if (_snowmanxxxx == null) {
                  return false;
               }

               if (_snowmanx.get(_snowmanxxx) != _snowmanxxxx) {
                  return false;
               }
            }

            if (this.nbt == null) {
               return true;
            } else {
               BlockEntity _snowmanxx = _snowman.getBlockEntity();
               return _snowmanxx != null && NbtHelper.matches(this.nbt, _snowmanxx.toTag(new CompoundTag()), true);
            }
         }
      }
   }
}
