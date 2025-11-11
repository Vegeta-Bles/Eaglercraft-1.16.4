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

public class end<T> {
   private static final boolean b = Boolean.parseBoolean(System.getProperty("SuffixArray.printComparisons", "false"));
   private static final boolean c = Boolean.parseBoolean(System.getProperty("SuffixArray.printArray", "false"));
   private static final Logger d = LogManager.getLogger();
   protected final List<T> a = Lists.newArrayList();
   private final IntList e = new IntArrayList();
   private final IntList f = new IntArrayList();
   private IntList g = new IntArrayList();
   private IntList h = new IntArrayList();
   private int i;

   public end() {
   }

   public void a(T var1, String var2) {
      this.i = Math.max(this.i, _snowman.length());
      int _snowman = this.a.size();
      this.a.add(_snowman);
      this.f.add(this.e.size());

      for (int _snowmanx = 0; _snowmanx < _snowman.length(); _snowmanx++) {
         this.g.add(_snowman);
         this.h.add(_snowmanx);
         this.e.add(_snowman.charAt(_snowmanx));
      }

      this.g.add(_snowman);
      this.h.add(_snowman.length());
      this.e.add(-1);
   }

   public void a() {
      int _snowman = this.e.size();
      int[] _snowmanx = new int[_snowman];
      final int[] _snowmanxx = new int[_snowman];
      final int[] _snowmanxxx = new int[_snowman];
      int[] _snowmanxxxx = new int[_snowman];
      IntComparator _snowmanxxxxx = new IntComparator() {
         public int compare(int var1, int var2) {
            return _snowman[_snowman] == _snowman[_snowman] ? Integer.compare(_snowman[_snowman], _snowman[_snowman]) : Integer.compare(_snowman[_snowman], _snowman[_snowman]);
         }

         public int compare(Integer var1, Integer var2) {
            return this.compare(_snowman.intValue(), _snowman.intValue());
         }
      };
      Swapper _snowmanxxxxxx = (var3x, var4x) -> {
         if (var3x != var4x) {
            int _snowmanxxxxxxx = _snowman[var3x];
            _snowman[var3x] = _snowman[var4x];
            _snowman[var4x] = _snowmanxxxxxxx;
            _snowmanxxxxxxx = _snowman[var3x];
            _snowman[var3x] = _snowman[var4x];
            _snowman[var4x] = _snowmanxxxxxxx;
            _snowmanxxxxxxx = _snowman[var3x];
            _snowman[var3x] = _snowman[var4x];
            _snowman[var4x] = _snowmanxxxxxxx;
         }
      };

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowman; _snowmanxxxxxxx++) {
         _snowmanx[_snowmanxxxxxxx] = this.e.getInt(_snowmanxxxxxxx);
      }

      int _snowmanxxxxxxx = 1;

      for (int _snowmanxxxxxxxx = Math.min(_snowman, this.i); _snowmanxxxxxxx * 2 < _snowmanxxxxxxxx; _snowmanxxxxxxx *= 2) {
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

      IntList _snowmanxxxxxxxx = this.g;
      IntList _snowmanxxxxxxxxxx = this.h;
      this.g = new IntArrayList(_snowmanxxxxxxxx.size());
      this.h = new IntArrayList(_snowmanxxxxxxxxxx.size());

      for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < _snowman; _snowmanxxxxxxxxxxx++) {
         int _snowmanxxxxxxxxxxxx = _snowmanxxxx[_snowmanxxxxxxxxxxx];
         this.g.add(_snowmanxxxxxxxx.getInt(_snowmanxxxxxxxxxxxx));
         this.h.add(_snowmanxxxxxxxxxx.getInt(_snowmanxxxxxxxxxxxx));
      }

      if (c) {
         this.b();
      }
   }

   private void b() {
      for (int _snowman = 0; _snowman < this.g.size(); _snowman++) {
         d.debug("{} {}", _snowman, this.a(_snowman));
      }

      d.debug("");
   }

   private String a(int var1) {
      int _snowman = this.h.getInt(_snowman);
      int _snowmanx = this.f.getInt(this.g.getInt(_snowman));
      StringBuilder _snowmanxx = new StringBuilder();

      for (int _snowmanxxx = 0; _snowmanx + _snowmanxxx < this.e.size(); _snowmanxxx++) {
         if (_snowmanxxx == _snowman) {
            _snowmanxx.append('^');
         }

         int _snowmanxxxx = this.e.get(_snowmanx + _snowmanxxx);
         if (_snowmanxxxx == -1) {
            break;
         }

         _snowmanxx.append((char)_snowmanxxxx);
      }

      return _snowmanxx.toString();
   }

   private int a(String var1, int var2) {
      int _snowman = this.f.getInt(this.g.getInt(_snowman));
      int _snowmanx = this.h.getInt(_snowman);

      for (int _snowmanxx = 0; _snowmanxx < _snowman.length(); _snowmanxx++) {
         int _snowmanxxx = this.e.getInt(_snowman + _snowmanx + _snowmanxx);
         if (_snowmanxxx == -1) {
            return 1;
         }

         char _snowmanxxxx = _snowman.charAt(_snowmanxx);
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

   public List<T> a(String var1) {
      int _snowman = this.g.size();
      int _snowmanx = 0;
      int _snowmanxx = _snowman;

      while (_snowmanx < _snowmanxx) {
         int _snowmanxxx = _snowmanx + (_snowmanxx - _snowmanx) / 2;
         int _snowmanxxxx = this.a(_snowman, _snowmanxxx);
         if (b) {
            d.debug("comparing lower \"{}\" with {} \"{}\": {}", _snowman, _snowmanxxx, this.a(_snowmanxxx), _snowmanxxxx);
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
            int _snowmanxxxxxxx = this.a(_snowman, _snowmanxxxxxx);
            if (b) {
               d.debug("comparing upper \"{}\" with {} \"{}\": {}", _snowman, _snowmanxxxxxx, this.a(_snowmanxxxxxx), _snowmanxxxxxxx);
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
            _snowmanxxxxxxxxx.add(this.g.getInt(_snowmanxxxxxxxxxx));
         }

         int[] _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.toIntArray();
         java.util.Arrays.sort(_snowmanxxxxxxxxxx);
         Set<T> _snowmanxxxxxxxxxxx = Sets.newLinkedHashSet();

         for (int _snowmanxxxxxxxxxxxx : _snowmanxxxxxxxxxx) {
            _snowmanxxxxxxxxxxx.add(this.a.get(_snowmanxxxxxxxxxxxx));
         }

         return Lists.newArrayList(_snowmanxxxxxxxxxxx);
      } else {
         return Collections.emptyList();
      }
   }
}
