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
      object -> new TranslatableText("arguments.block.tag.unknown", object)
   );

   public BlockPredicateArgumentType() {
   }

   public static BlockPredicateArgumentType blockPredicate() {
      return new BlockPredicateArgumentType();
   }

   public BlockPredicateArgumentType.BlockPredicate parse(StringReader stringReader) throws CommandSyntaxException {
      BlockArgumentParser lv = new BlockArgumentParser(stringReader, true).parse(true);
      if (lv.getBlockState() != null) {
         BlockPredicateArgumentType.StatePredicate lv2 = new BlockPredicateArgumentType.StatePredicate(
            lv.getBlockState(), lv.getBlockProperties().keySet(), lv.getNbtData()
         );
         return arg2 -> lv2;
      } else {
         Identifier lv3 = lv.getTagId();
         return arg3 -> {
            Tag<Block> lvx = arg3.getBlocks().getTag(lv3);
            if (lvx == null) {
               throw UNKNOWN_TAG_EXCEPTION.create(lv3.toString());
            } else {
               return new BlockPredicateArgumentType.TagPredicate(lvx, lv.getProperties(), lv.getNbtData());
            }
         };
      }
   }

   public static Predicate<CachedBlockPosition> getBlockPredicate(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      return ((BlockPredicateArgumentType.BlockPredicate)context.getArgument(name, BlockPredicateArgumentType.BlockPredicate.class))
         .create(((ServerCommandSource)context.getSource()).getMinecraftServer().getTagManager());
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      StringReader stringReader = new StringReader(builder.getInput());
      stringReader.setCursor(builder.getStart());
      BlockArgumentParser lv = new BlockArgumentParser(stringReader, true);

      try {
         lv.parse(true);
      } catch (CommandSyntaxException var6) {
      }

      return lv.getSuggestions(builder, BlockTags.getTagGroup());
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   public interface BlockPredicate {
      Predicate<CachedBlockPosition> create(TagManager arg) throws CommandSyntaxException;
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

      public boolean test(CachedBlockPosition arg) {
         BlockState lv = arg.getBlockState();
         if (!lv.isOf(this.state.getBlock())) {
            return false;
         } else {
            for (Property<?> lv2 : this.properties) {
               if (lv.get(lv2) != this.state.get(lv2)) {
                  return false;
               }
            }

            if (this.nbt == null) {
               return true;
            } else {
               BlockEntity lv3 = arg.getBlockEntity();
               return lv3 != null && NbtHelper.matches(this.nbt, lv3.toTag(new CompoundTag()), true);
            }
         }
      }
   }

   static class TagPredicate implements Predicate<CachedBlockPosition> {
      private final Tag<Block> tag;
      @Nullable
      private final CompoundTag nbt;
      private final Map<String, String> properties;

      private TagPredicate(Tag<Block> arg, Map<String, String> map, @Nullable CompoundTag nbt) {
         this.tag = arg;
         this.properties = map;
         this.nbt = nbt;
      }

      public boolean test(CachedBlockPosition arg) {
         BlockState lv = arg.getBlockState();
         if (!lv.isIn(this.tag)) {
            return false;
         } else {
            for (Entry<String, String> entry : this.properties.entrySet()) {
               Property<?> lv2 = lv.getBlock().getStateManager().getProperty(entry.getKey());
               if (lv2 == null) {
                  return false;
               }

               Comparable<?> comparable = (Comparable<?>)lv2.parse(entry.getValue()).orElse(null);
               if (comparable == null) {
                  return false;
               }

               if (lv.get(lv2) != comparable) {
                  return false;
               }
            }

            if (this.nbt == null) {
               return true;
            } else {
               BlockEntity lv3 = arg.getBlockEntity();
               return lv3 != null && NbtHelper.matches(this.nbt, lv3.toTag(new CompoundTag()), true);
            }
         }
      }
   }
}
