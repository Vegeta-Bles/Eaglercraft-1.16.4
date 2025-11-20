package net.minecraft.client.search;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.Swapper;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SuffixArray<T> {
   private static final boolean PRINT_COMPARISONS = Boolean.parseBoolean(System.getProperty("SuffixArray.printComparisons", "false"));
   private static final boolean PRINT_ARRAY = Boolean.parseBoolean(System.getProperty("SuffixArray.printArray", "false"));
   private static final Logger LOGGER = LogManager.getLogger();
   protected final List<T> objects = Lists.newArrayList();
   private final IntList characters = new IntArrayList();
   private final IntList textStarts = new IntArrayList();
   private IntList suffixIndexToObjectIndex = new IntArrayList();
   private IntList offsetInText = new IntArrayList();
   private int maxTextLength;

   public SuffixArray() {
   }

   public void add(T object, String text) {
      this.maxTextLength = Math.max(this.maxTextLength, text.length());
      int _snowman = this.objects.size();
      this.objects.add(object);
      this.textStarts.add(this.characters.size());

      for (int _snowmanx = 0; _snowmanx < text.length(); _snowmanx++) {
         this.suffixIndexToObjectIndex.add(_snowman);
         this.offsetInText.add(_snowmanx);
         this.characters.add(text.charAt(_snowmanx));
      }

      this.suffixIndexToObjectIndex.add(_snowman);
      this.offsetInText.add(text.length());
      this.characters.add(-1);
   }

   public void build() {
      int _snowman = this.characters.size();
      int[] _snowmanx = new int[_snowman];
      final int[] _snowmanxx = new int[_snowman];
      final int[] _snowmanxxx = new int[_snowman];
      int[] _snowmanxxxx = new int[_snowman];
      IntComparator _snowmanxxxxx = new IntComparator() {
         public int compare(int i, int j) {
            return _snowman[i] == _snowman[j] ? Integer.compare(_snowman[i], _snowman[j]) : Integer.compare(_snowman[i], _snowman[j]);
         }

         public int compare(Integer _snowman, Integer _snowman) {
            return this.compare(_snowman.intValue(), _snowman.intValue());
         }
      };
      Swapper _snowmanxxxxxx = (i, j) -> {
         if (i != j) {
            int _snowmanxxxxxxx = _snowman[i];
            _snowman[i] = _snowman[j];
            _snowman[j] = _snowmanxxxxxxx;
            _snowmanxxxxxxx = _snowman[i];
            _snowman[i] = _snowman[j];
            _snowman[j] = _snowmanxxxxxxx;
            _snowmanxxxxxxx = _snowman[i];
            _snowman[i] = _snowman[j];
            _snowman[j] = _snowmanxxxxxxx;
         }
      };

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowman; _snowmanxxxxxxx++) {
         _snowmanx[_snowmanxxxxxxx] = this.characters.getInt(_snowmanxxxxxxx);
      }

      int _snowmanxxxxxxx = 1;

      for (int _snowmanxxxxxxxx = Math.min(_snowman, this.maxTextLength); _snowmanxxxxxxx * 2 < _snowmanxxxxxxxx; _snowmanxxxxxxx *= 2) {
         for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowman; _snowmanxxxx[_snowmanxxxxxxxxx] = _snowmanxxxxxxxxx++) {
            _snowmanxx[_snowmanxxxxxxxxx] = _snowmanx[_snowmanxxxxxxxxx];
            _snowmanxxx[_snowmanxxxxxxxxx] = _snowmanxxxxxxxxx + _snowmanxxxxxxx < _snowman ? _snowmanx[_snowmanxxxxxxxxx + _snowmanxxxxxxx] : -2;
         }

         Arrays.quickSort(0, _snowman, _snowmanxxxxx, _snowmanxxxxxx);

         for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowman; _snowmanxxxxxxxxx++) {
            if (_snowmanxxxxxxxxx > 0 && _snowmanxx[_snowmanxxxxxxxxx] == _snowmanxx[_snowmanxxxxxxxxx - 1] && _snowmanxxx[_snowmanxxxxxxxxx] == _snowmanxxx[_snowmanxxxxxxxxx - 1]) {
               _snowmanx[_snowmanxxxx[_snowmanxxxxxxxxx]] = _snowmanx[_snowmanxxxx[_snowmanxxxxxxxxx - 1]];
            } else {
               _snowmanx[_snowmanxxxx[_snowmanxxxxxxxxx]] = _snowmanxxxxxxxxx;
            }
         }
      }

      IntList _snowmanxxxxxxxx = this.suffixIndexToObjectIndex;
      IntList _snowmanxxxxxxxxxx = this.offsetInText;
      this.suffixIndexToObjectIndex = new IntArrayList(_snowmanxxxxxxxx.size());
      this.offsetInText = new IntArrayList(_snowmanxxxxxxxxxx.size());

      for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < _snowman; _snowmanxxxxxxxxxxx++) {
         int _snowmanxxxxxxxxxxxx = _snowmanxxxx[_snowmanxxxxxxxxxxx];
         this.suffixIndexToObjectIndex.add(_snowmanxxxxxxxx.getInt(_snowmanxxxxxxxxxxxx));
         this.offsetInText.add(_snowmanxxxxxxxxxx.getInt(_snowmanxxxxxxxxxxxx));
      }

      if (PRINT_ARRAY) {
         this.printArray();
      }
   }

   private void printArray() {
      for (int _snowman = 0; _snowman < this.suffixIndexToObjectIndex.size(); _snowman++) {
         LOGGER.debug("{} {}", _snowman, this.getDebugString(_snowman));
      }

      LOGGER.debug("");
   }

   private String getDebugString(int suffixIndex) {
      int _snowman = this.offsetInText.getInt(suffixIndex);
      int _snowmanx = this.textStarts.getInt(this.suffixIndexToObjectIndex.getInt(suffixIndex));
      StringBuilder _snowmanxx = new StringBuilder();

      for (int _snowmanxxx = 0; _snowmanx + _snowmanxxx < this.characters.size(); _snowmanxxx++) {
         if (_snowmanxxx == _snowman) {
            _snowmanxx.append('^');
         }

         int _snowmanxxxx = this.characters.get(_snowmanx + _snowmanxxx);
         if (_snowmanxxxx == -1) {
            break;
         }

         _snowmanxx.append((char)_snowmanxxxx);
      }

      return _snowmanxx.toString();
   }

   private int compare(String string, int suffixIndex) {
      int _snowman = this.textStarts.getInt(this.suffixIndexToObjectIndex.getInt(suffixIndex));
      int _snowmanx = this.offsetInText.getInt(suffixIndex);

      for (int _snowmanxx = 0; _snowmanxx < string.length(); _snowmanxx++) {
         int _snowmanxxx = this.characters.getInt(_snowman + _snowmanx + _snowmanxx);
         if (_snowmanxxx == -1) {
            return 1;
         }

         char _snowmanxxxx = string.charAt(_snowmanxx);
         char _snowmanxxxxx = (char)_snowmanxxx;
         if (_snowmanxxxx < _snowmanxxxxx) {
            return -1;
         }

         if (_snowmanxxxx > _snowmanxxxxx) {
            return 1;
         }
      }

      return 0;
   }

   public List<T> findAll(String text) {
      int _snowman = this.suffixIndexToObjectIndex.size();
      int _snowmanx = 0;
      int _snowmanxx = _snowman;

      while (_snowmanx < _snowmanxx) {
         int _snowmanxxx = _snowmanx + (_snowmanxx - _snowmanx) / 2;
         int _snowmanxxxx = this.compare(text, _snowmanxxx);
         if (PRINT_COMPARISONS) {
            LOGGER.debug("comparing lower \"{}\" with {} \"{}\": {}", text, _snowmanxxx, this.getDebugString(_snowmanxxx), _snowmanxxxx);
         }

         if (_snowmanxxxx > 0) {
            _snowmanx = _snowmanxxx + 1;
         } else {
            _snowmanxx = _snowmanxxx;
         }
      }

      if (_snowmanx >= 0 && _snowmanx < _snowman) {
         int _snowmanxxxxx = _snowmanx;
         _snowmanxx = _snowman;

         while (_snowmanx < _snowmanxx) {
            int _snowmanxxxxxx = _snowmanx + (_snowmanxx - _snowmanx) / 2;
            int _snowmanxxxxxxx = this.compare(text, _snowmanxxxxxx);
            if (PRINT_COMPARISONS) {
               LOGGER.debug("comparing upper \"{}\" with {} \"{}\": {}", text, _snowmanxxxxxx, this.getDebugString(_snowmanxxxxxx), _snowmanxxxxxxx);
            }

            if (_snowmanxxxxxxx >= 0) {
               _snowmanx = _snowmanxxxxxx + 1;
            } else {
               _snowmanxx = _snowmanxxxxxx;
            }
         }

         int _snowmanxxxxxxxx = _snowmanx;
         IntSet _snowmanxxxxxxxxx = new IntOpenHashSet();

         for (int _snowmanxxxxxxxxxx = _snowmanxxxxx; _snowmanxxxxxxxxxx < _snowmanxxxxxxxx; _snowmanxxxxxxxxxx++) {
            _snowmanxxxxxxxxx.add(this.suffixIndexToObjectIndex.getInt(_snowmanxxxxxxxxxx));
         }

         int[] _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.toIntArray();
         java.util.Arrays.sort(_snowmanxxxxxxxxxx);
         Set<T> _snowmanxxxxxxxxxxx = Sets.newLinkedHashSet();

         for (int _snowmanxxxxxxxxxxxx : _snowmanxxxxxxxxxx) {
            _snowmanxxxxxxxxxxx.add(this.objects.get(_snowmanxxxxxxxxxxxx));
         }

         return Lists.newArrayList(_snowmanxxxxxxxxxxx);
      } else {
         return Collections.emptyList();
      }
   }
}
