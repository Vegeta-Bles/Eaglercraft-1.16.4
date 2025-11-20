package net.minecraft.block.pattern;

import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class BlockPatternBuilder {
   private static final Joiner JOINER = Joiner.on(",");
   private final List<String[]> aisles = Lists.newArrayList();
   private final Map<Character, Predicate<CachedBlockPosition>> charMap = Maps.newHashMap();
   private int height;
   private int width;

   private BlockPatternBuilder() {
      this.charMap.put(' ', Predicates.alwaysTrue());
   }

   public BlockPatternBuilder aisle(String... pattern) {
      if (!ArrayUtils.isEmpty(pattern) && !StringUtils.isEmpty(pattern[0])) {
         if (this.aisles.isEmpty()) {
            this.height = pattern.length;
            this.width = pattern[0].length();
         }

         if (pattern.length != this.height) {
            throw new IllegalArgumentException("Expected aisle with height of " + this.height + ", but was given one with a height of " + pattern.length + ")");
         } else {
            for (String _snowman : pattern) {
               if (_snowman.length() != this.width) {
                  throw new IllegalArgumentException(
                     "Not all rows in the given aisle are the correct width (expected " + this.width + ", found one with " + _snowman.length() + ")"
                  );
               }

               for (char _snowmanx : _snowman.toCharArray()) {
                  if (!this.charMap.containsKey(_snowmanx)) {
                     this.charMap.put(_snowmanx, null);
                  }
               }
            }

            this.aisles.add(pattern);
            return this;
         }
      } else {
         throw new IllegalArgumentException("Empty pattern for aisle");
      }
   }

   public static BlockPatternBuilder start() {
      return new BlockPatternBuilder();
   }

   public BlockPatternBuilder where(char key, Predicate<CachedBlockPosition> _snowman) {
      this.charMap.put(key, _snowman);
      return this;
   }

   public BlockPattern build() {
      return new BlockPattern(this.bakePredicates());
   }

   private Predicate<CachedBlockPosition>[][][] bakePredicates() {
      this.validate();
      Predicate<CachedBlockPosition>[][][] _snowman = (Predicate<CachedBlockPosition>[][][])Array.newInstance(
         Predicate.class, this.aisles.size(), this.height, this.width
      );

      for (int _snowmanx = 0; _snowmanx < this.aisles.size(); _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx < this.height; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < this.width; _snowmanxxx++) {
               _snowman[_snowmanx][_snowmanxx][_snowmanxxx] = this.charMap.get(this.aisles.get(_snowmanx)[_snowmanxx].charAt(_snowmanxxx));
            }
         }
      }

      return _snowman;
   }

   private void validate() {
      List<Character> _snowman = Lists.newArrayList();

      for (Entry<Character, Predicate<CachedBlockPosition>> _snowmanx : this.charMap.entrySet()) {
         if (_snowmanx.getValue() == null) {
            _snowman.add(_snowmanx.getKey());
         }
      }

      if (!_snowman.isEmpty()) {
         throw new IllegalStateException("Predicates for character(s) " + JOINER.join(_snowman) + " are missing");
      }
   }
}
