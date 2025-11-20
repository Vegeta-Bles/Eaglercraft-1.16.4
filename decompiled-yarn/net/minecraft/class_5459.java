package net.minecraft;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntStack;
import java.util.function.Predicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class class_5459 {
   public static class_5459.class_5460 method_30574(BlockPos _snowman, Direction.Axis _snowman, int _snowman, Direction.Axis _snowman, int _snowman, Predicate<BlockPos> _snowman) {
      BlockPos.Mutable _snowmanxxxxxx = _snowman.mutableCopy();
      Direction _snowmanxxxxxxx = Direction.get(Direction.AxisDirection.NEGATIVE, _snowman);
      Direction _snowmanxxxxxxxx = _snowmanxxxxxxx.getOpposite();
      Direction _snowmanxxxxxxxxx = Direction.get(Direction.AxisDirection.NEGATIVE, _snowman);
      Direction _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.getOpposite();
      int _snowmanxxxxxxxxxxx = method_30575(_snowman, _snowmanxxxxxx.set(_snowman), _snowmanxxxxxxx, _snowman);
      int _snowmanxxxxxxxxxxxx = method_30575(_snowman, _snowmanxxxxxx.set(_snowman), _snowmanxxxxxxxx, _snowman);
      int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx;
      class_5459.IntBounds[] _snowmanxxxxxxxxxxxxxx = new class_5459.IntBounds[_snowmanxxxxxxxxxxx + 1 + _snowmanxxxxxxxxxxxx];
      _snowmanxxxxxxxxxxxxxx[_snowmanxxxxxxxxxxx] = new class_5459.IntBounds(method_30575(_snowman, _snowmanxxxxxx.set(_snowman), _snowmanxxxxxxxxx, _snowman), method_30575(_snowman, _snowmanxxxxxx.set(_snowman), _snowmanxxxxxxxxxx, _snowman));
      int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx[_snowmanxxxxxxxxxxx].min;

      for (int _snowmanxxxxxxxxxxxxxxxx = 1; _snowmanxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
         class_5459.IntBounds _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx[_snowmanxxxxxxxxxxxxx - (_snowmanxxxxxxxxxxxxxxxx - 1)];
         _snowmanxxxxxxxxxxxxxx[_snowmanxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxx] = new class_5459.IntBounds(
            method_30575(_snowman, _snowmanxxxxxx.set(_snowman).move(_snowmanxxxxxxx, _snowmanxxxxxxxxxxxxxxxx), _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx.min),
            method_30575(_snowman, _snowmanxxxxxx.set(_snowman).move(_snowmanxxxxxxx, _snowmanxxxxxxxxxxxxxxxx), _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx.max)
         );
      }

      for (int _snowmanxxxxxxxxxxxxxxxx = 1; _snowmanxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
         class_5459.IntBounds _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx[_snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxx - 1];
         _snowmanxxxxxxxxxxxxxx[_snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxx] = new class_5459.IntBounds(
            method_30575(_snowman, _snowmanxxxxxx.set(_snowman).move(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx), _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx.min),
            method_30575(_snowman, _snowmanxxxxxx.set(_snowman).move(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx), _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx.max)
         );
      }

      int _snowmanxxxxxxxxxxxxxxxx = 0;
      int _snowmanxxxxxxxxxxxxxxxxx = 0;
      int _snowmanxxxxxxxxxxxxxxxxxx = 0;
      int _snowmanxxxxxxxxxxxxxxxxxxx = 0;
      int[] _snowmanxxxxxxxxxxxxxxxxxxxx = new int[_snowmanxxxxxxxxxxxxxx.length];

      for (int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxxxxxxxx--) {
         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxx.length; _snowmanxxxxxxxxxxxxxxxxxxxxxx++) {
            class_5459.IntBounds _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx[_snowmanxxxxxxxxxxxxxxxxxxxxxx];
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxx.min;
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxx.max;
            _snowmanxxxxxxxxxxxxxxxxxxxx[_snowmanxxxxxxxxxxxxxxxxxxxxxx] = _snowmanxxxxxxxxxxxxxxxxxxxxx >= _snowmanxxxxxxxxxxxxxxxxxxxxxxxx
                  && _snowmanxxxxxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx
               ? _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 1 - _snowmanxxxxxxxxxxxxxxxxxxxxx
               : 0;
         }

         Pair<class_5459.IntBounds, Integer> _snowmanxxxxxxxxxxxxxxxxxxxxxx = method_30576(_snowmanxxxxxxxxxxxxxxxxxxxx);
         class_5459.IntBounds _snowmanxxxxxxxxxxxxxxxxxxxxxxx = (class_5459.IntBounds)_snowmanxxxxxxxxxxxxxxxxxxxxxx.getFirst();
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 1 + _snowmanxxxxxxxxxxxxxxxxxxxxxxx.max - _snowmanxxxxxxxxxxxxxxxxxxxxxxx.min;
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = (Integer)_snowmanxxxxxxxxxxxxxxxxxxxxxx.getSecond();
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx > _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxx.min;
            _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx;
         }
      }

      return new class_5459.class_5460(
         _snowman.offset(_snowman, _snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxx).offset(_snowman, _snowmanxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxx), _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx
      );
   }

   private static int method_30575(Predicate<BlockPos> _snowman, BlockPos.Mutable _snowman, Direction _snowman, int _snowman) {
      int _snowmanxxxx = 0;

      while (_snowmanxxxx < _snowman && _snowman.test(_snowman.move(_snowman))) {
         _snowmanxxxx++;
      }

      return _snowmanxxxx;
   }

   @VisibleForTesting
   static Pair<class_5459.IntBounds, Integer> method_30576(int[] _snowman) {
      int _snowmanx = 0;
      int _snowmanxx = 0;
      int _snowmanxxx = 0;
      IntStack _snowmanxxxx = new IntArrayList();
      _snowmanxxxx.push(0);

      for (int _snowmanxxxxx = 1; _snowmanxxxxx <= _snowman.length; _snowmanxxxxx++) {
         int _snowmanxxxxxx = _snowmanxxxxx == _snowman.length ? 0 : _snowman[_snowmanxxxxx];

         while (!_snowmanxxxx.isEmpty()) {
            int _snowmanxxxxxxx = _snowman[_snowmanxxxx.topInt()];
            if (_snowmanxxxxxx >= _snowmanxxxxxxx) {
               _snowmanxxxx.push(_snowmanxxxxx);
               break;
            }

            _snowmanxxxx.popInt();
            int _snowmanxxxxxxxx = _snowmanxxxx.isEmpty() ? 0 : _snowmanxxxx.topInt() + 1;
            if (_snowmanxxxxxxx * (_snowmanxxxxx - _snowmanxxxxxxxx) > _snowmanxxx * (_snowmanxx - _snowmanx)) {
               _snowmanxx = _snowmanxxxxx;
               _snowmanx = _snowmanxxxxxxxx;
               _snowmanxxx = _snowmanxxxxxxx;
            }
         }

         if (_snowmanxxxx.isEmpty()) {
            _snowmanxxxx.push(_snowmanxxxxx);
         }
      }

      return new Pair(new class_5459.IntBounds(_snowmanx, _snowmanxx - 1), _snowmanxxx);
   }

   public static class IntBounds {
      public final int min;
      public final int max;

      public IntBounds(int min, int max) {
         this.min = min;
         this.max = max;
      }

      @Override
      public String toString() {
         return "IntBounds{min=" + this.min + ", max=" + this.max + '}';
      }
   }

   public static class class_5460 {
      public final BlockPos field_25936;
      public final int field_25937;
      public final int field_25938;

      public class_5460(BlockPos _snowman, int _snowman, int _snowman) {
         this.field_25936 = _snowman;
         this.field_25937 = _snowman;
         this.field_25938 = _snowman;
      }
   }
}
