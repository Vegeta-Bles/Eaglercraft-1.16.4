package net.minecraft.datafixer.fix;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.math.WordPackedArray;

public class LeavesFix extends DataFix {
   private static final int[][] field_5687 = new int[][]{{-1, 0, 0}, {1, 0, 0}, {0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}};
   private static final Object2IntMap<String> LEAVES_MAP = (Object2IntMap<String>)DataFixUtils.make(new Object2IntOpenHashMap(), _snowman -> {
      _snowman.put("minecraft:acacia_leaves", 0);
      _snowman.put("minecraft:birch_leaves", 1);
      _snowman.put("minecraft:dark_oak_leaves", 2);
      _snowman.put("minecraft:jungle_leaves", 3);
      _snowman.put("minecraft:oak_leaves", 4);
      _snowman.put("minecraft:spruce_leaves", 5);
   });
   private static final Set<String> LOGS_MAP = ImmutableSet.of(
      "minecraft:acacia_bark",
      "minecraft:birch_bark",
      "minecraft:dark_oak_bark",
      "minecraft:jungle_bark",
      "minecraft:oak_bark",
      "minecraft:spruce_bark",
      new String[]{
         "minecraft:acacia_log",
         "minecraft:birch_log",
         "minecraft:dark_oak_log",
         "minecraft:jungle_log",
         "minecraft:oak_log",
         "minecraft:spruce_log",
         "minecraft:stripped_acacia_log",
         "minecraft:stripped_birch_log",
         "minecraft:stripped_dark_oak_log",
         "minecraft:stripped_jungle_log",
         "minecraft:stripped_oak_log",
         "minecraft:stripped_spruce_log"
      }
   );

   public LeavesFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.CHUNK);
      OpticFinder<?> _snowmanx = _snowman.findField("Level");
      OpticFinder<?> _snowmanxx = _snowmanx.type().findField("Sections");
      Type<?> _snowmanxxx = _snowmanxx.type();
      if (!(_snowmanxxx instanceof ListType)) {
         throw new IllegalStateException("Expecting sections to be a list.");
      } else {
         Type<?> _snowmanxxxx = ((ListType)_snowmanxxx).getElement();
         OpticFinder<?> _snowmanxxxxx = DSL.typeFinder(_snowmanxxxx);
         return this.fixTypeEverywhereTyped(
            "Leaves fix",
            _snowman,
            _snowmanxxxxxx -> _snowmanxxxxxx.updateTyped(
                  _snowman,
                  _snowmanxxxxxxx -> {
                     int[] _snowmanxxxxxxxx = new int[]{0};
                     Typed<?> _snowmanxxxxxxxxx = _snowmanxxxxxxx.updateTyped(
                        _snowman,
                        _snowmanxxxxxxxxxxxxxxxxxxxxx -> {
                           Int2ObjectMap<LeavesFix.LeavesLogFixer> _snowmanxxx = new Int2ObjectOpenHashMap(
                              _snowmanxxxxxxxxxxxxxxxxxxxxx.getAllTyped(_snowman)
                                 .stream()
                                 .map(
                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -> new LeavesFix.LeavesLogFixer(
                                          _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, this.getInputSchema()
                                       )
                                 )
                                 .collect(
                                    Collectors.toMap(
                                       LeavesFix.ListFixer::method_5077, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                    )
                                 )
                           );
                           if (_snowmanxxx.values().stream().allMatch(LeavesFix.ListFixer::isFixed)) {
                              return _snowmanxxxxxxxxxxxxxxxxxxxxx;
                           } else {
                              List<IntSet> _snowmanxxxx = Lists.newArrayList();

                              for (int _snowmanxxxxx = 0; _snowmanxxxxx < 7; _snowmanxxxxx++) {
                                 _snowmanxxxx.add(new IntOpenHashSet());
                              }

                              ObjectIterator var25 = _snowmanxxx.values().iterator();

                              while (var25.hasNext()) {
                                 LeavesFix.LeavesLogFixer _snowmanxxxxx = (LeavesFix.LeavesLogFixer)var25.next();
                                 if (!_snowmanxxxxx.isFixed()) {
                                    for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 4096; _snowmanxxxxxx++) {
                                       int _snowmanxxxxxxx = _snowmanxxxxx.needsFix(_snowmanxxxxxx);
                                       if (_snowmanxxxxx.isLog(_snowmanxxxxxxx)) {
                                          _snowmanxxxx.get(0).add(_snowmanxxxxx.method_5077() << 12 | _snowmanxxxxxx);
                                       } else if (_snowmanxxxxx.isLeaf(_snowmanxxxxxxx)) {
                                          int _snowmanxxxxxxxx = this.method_5052(_snowmanxxxxxx);
                                          int _snowmanxxxxxxxxx = this.method_5050(_snowmanxxxxxx);
                                          _snowmanxxxxx[0] |= method_5061(_snowmanxxxxxxxx == 0, _snowmanxxxxxxxx == 15, _snowmanxxxxxxxxx == 0, _snowmanxxxxxxxxx == 15);
                                       }
                                    }
                                 }
                              }

                              for (int _snowmanxxxxx = 1; _snowmanxxxxx < 7; _snowmanxxxxx++) {
                                 IntSet _snowmanxxxxxxx = _snowmanxxxx.get(_snowmanxxxxx - 1);
                                 IntSet _snowmanxxxxxxxx = _snowmanxxxx.get(_snowmanxxxxx);
                                 IntIterator _snowmanxxxxxxxxx = _snowmanxxxxxxx.iterator();

                                 while (_snowmanxxxxxxxxx.hasNext()) {
                                    int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.nextInt();
                                    int _snowmanxxxxxxxxxxxx = this.method_5052(_snowmanxxxxxxxxxx);
                                    int _snowmanxxxxxxxxxxxx = this.method_5062(_snowmanxxxxxxxxxx);
                                    int _snowmanxxxxxxxxxxxxx = this.method_5050(_snowmanxxxxxxxxxx);

                                    for (int[] _snowmanxxxxxxxxxxxxxx : field_5687) {
                                       int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx[0];
                                       int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx[1];
                                       int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx[2];
                                       if (_snowmanxxxxxxxxxxxxxxx >= 0
                                          && _snowmanxxxxxxxxxxxxxxx <= 15
                                          && _snowmanxxxxxxxxxxxxxxxxx >= 0
                                          && _snowmanxxxxxxxxxxxxxxxxx <= 15
                                          && _snowmanxxxxxxxxxxxxxxxx >= 0
                                          && _snowmanxxxxxxxxxxxxxxxx <= 255) {
                                          LeavesFix.LeavesLogFixer _snowmanxxxxxxxxxxxxxxxxxx = (LeavesFix.LeavesLogFixer)_snowmanxxx.get(_snowmanxxxxxxxxxxxxxxxx >> 4);
                                          if (_snowmanxxxxxxxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxxxxxxxx.isFixed()) {
                                             int _snowmanxxxxxxxxxxxxxxxxxxx = method_5051(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx & 15, _snowmanxxxxxxxxxxxxxxxxx);
                                             int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx.needsFix(_snowmanxxxxxxxxxxxxxxxxxxx);
                                             if (_snowmanxxxxxxxxxxxxxxxxxx.isLeaf(_snowmanxxxxxxxxxxxxxxxxx)) {
                                                int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx.getDistanceToLog(_snowmanxxxxxxxxxxxxxxxxx);
                                                if (_snowmanxxxxxxxxxxxxxxxxxxxxx > _snowmanxxxxx) {
                                                   _snowmanxxxxxxxxxxxxxxxxxx.computeLeafStates(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxx);
                                                   _snowmanxxxxxxxx.add(method_5051(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx));
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }

                              return _snowmanxxxxxxxxxxxxxxxxxxxxx.updateTyped(
                                 _snowman,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -> ((LeavesFix.LeavesLogFixer)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.get(
                                          ((Dynamic)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.get(DSL.remainderFinder())).get("Y").asInt(0)
                                       ))
                                       .method_5083(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                              );
                           }
                        }
                     );
                     if (_snowmanxxxxxxxx[0] != 0) {
                        _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.update(DSL.remainderFinder(), _snowmanxxxxxxxxxxx -> {
                           Dynamic<?> _snowmanxx = (Dynamic<?>)DataFixUtils.orElse(_snowmanxxxxxxxxxxx.get("UpgradeData").result(), _snowmanxxxxxxxxxxx.emptyMap());
                           return _snowmanxxxxxxxxxxx.set(
                              "UpgradeData", _snowmanxx.set("Sides", _snowmanxxxxxxxxxxx.createByte((byte)(_snowmanxx.get("Sides").asByte((byte)0) | _snowmanxxxxx[0])))
                           );
                        });
                     }

                     return _snowmanxxxxxxxxx;
                  }
               )
         );
      }
   }

   public static int method_5051(int _snowman, int _snowman, int _snowman) {
      return _snowman << 8 | _snowman << 4 | _snowman;
   }

   private int method_5052(int _snowman) {
      return _snowman & 15;
   }

   private int method_5062(int _snowman) {
      return _snowman >> 8 & 0xFF;
   }

   private int method_5050(int _snowman) {
      return _snowman >> 4 & 15;
   }

   public static int method_5061(boolean _snowman, boolean _snowman, boolean _snowman, boolean _snowman) {
      int _snowmanxxxx = 0;
      if (_snowman) {
         if (_snowman) {
            _snowmanxxxx |= 2;
         } else if (_snowman) {
            _snowmanxxxx |= 128;
         } else {
            _snowmanxxxx |= 1;
         }
      } else if (_snowman) {
         if (_snowman) {
            _snowmanxxxx |= 32;
         } else if (_snowman) {
            _snowmanxxxx |= 8;
         } else {
            _snowmanxxxx |= 16;
         }
      } else if (_snowman) {
         _snowmanxxxx |= 4;
      } else if (_snowman) {
         _snowmanxxxx |= 64;
      }

      return _snowmanxxxx;
   }

   public static final class LeavesLogFixer extends LeavesFix.ListFixer {
      @Nullable
      private IntSet leafIndices;
      @Nullable
      private IntSet logIndices;
      @Nullable
      private Int2IntMap leafStates;

      public LeavesLogFixer(Typed<?> _snowman, Schema _snowman) {
         super(_snowman, _snowman);
      }

      @Override
      protected boolean needsFix() {
         this.leafIndices = new IntOpenHashSet();
         this.logIndices = new IntOpenHashSet();
         this.leafStates = new Int2IntOpenHashMap();

         for (int _snowman = 0; _snowman < this.properties.size(); _snowman++) {
            Dynamic<?> _snowmanx = this.properties.get(_snowman);
            String _snowmanxx = _snowmanx.get("Name").asString("");
            if (LeavesFix.LEAVES_MAP.containsKey(_snowmanxx)) {
               boolean _snowmanxxx = Objects.equals(_snowmanx.get("Properties").get("decayable").asString(""), "false");
               this.leafIndices.add(_snowman);
               this.leafStates.put(this.computeFlags(_snowmanxx, _snowmanxxx, 7), _snowman);
               this.properties.set(_snowman, this.createLeafProperties(_snowmanx, _snowmanxx, _snowmanxxx, 7));
            }

            if (LeavesFix.LOGS_MAP.contains(_snowmanxx)) {
               this.logIndices.add(_snowman);
            }
         }

         return this.leafIndices.isEmpty() && this.logIndices.isEmpty();
      }

      private Dynamic<?> createLeafProperties(Dynamic<?> _snowman, String _snowman, boolean _snowman, int _snowman) {
         Dynamic<?> _snowmanxxxx = _snowman.emptyMap();
         _snowmanxxxx = _snowmanxxxx.set("persistent", _snowmanxxxx.createString(_snowman ? "true" : "false"));
         _snowmanxxxx = _snowmanxxxx.set("distance", _snowmanxxxx.createString(Integer.toString(_snowman)));
         Dynamic<?> _snowmanxxxxx = _snowman.emptyMap();
         _snowmanxxxxx = _snowmanxxxxx.set("Properties", _snowmanxxxx);
         return _snowmanxxxxx.set("Name", _snowmanxxxxx.createString(_snowman));
      }

      public boolean isLog(int _snowman) {
         return this.logIndices.contains(_snowman);
      }

      public boolean isLeaf(int _snowman) {
         return this.leafIndices.contains(_snowman);
      }

      private int getDistanceToLog(int _snowman) {
         return this.isLog(_snowman) ? 0 : Integer.parseInt(this.properties.get(_snowman).get("Properties").get("distance").asString(""));
      }

      private void computeLeafStates(int _snowman, int _snowman, int _snowman) {
         Dynamic<?> _snowmanxxx = this.properties.get(_snowman);
         String _snowmanxxxx = _snowmanxxx.get("Name").asString("");
         boolean _snowmanxxxxx = Objects.equals(_snowmanxxx.get("Properties").get("persistent").asString(""), "true");
         int _snowmanxxxxxx = this.computeFlags(_snowmanxxxx, _snowmanxxxxx, _snowman);
         if (!this.leafStates.containsKey(_snowmanxxxxxx)) {
            int _snowmanxxxxxxx = this.properties.size();
            this.leafIndices.add(_snowmanxxxxxxx);
            this.leafStates.put(_snowmanxxxxxx, _snowmanxxxxxxx);
            this.properties.add(this.createLeafProperties(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowman));
         }

         int _snowmanxxxxxxx = this.leafStates.get(_snowmanxxxxxx);
         if (1 << this.blockStateMap.getUnitSize() <= _snowmanxxxxxxx) {
            WordPackedArray _snowmanxxxxxxxx = new WordPackedArray(this.blockStateMap.getUnitSize() + 1, 4096);

            for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < 4096; _snowmanxxxxxxxxx++) {
               _snowmanxxxxxxxx.set(_snowmanxxxxxxxxx, this.blockStateMap.get(_snowmanxxxxxxxxx));
            }

            this.blockStateMap = _snowmanxxxxxxxx;
         }

         this.blockStateMap.set(_snowman, _snowmanxxxxxxx);
      }
   }

   public abstract static class ListFixer {
      private final Type<Pair<String, Dynamic<?>>> field_5695 = DSL.named(TypeReferences.BLOCK_STATE.typeName(), DSL.remainderType());
      protected final OpticFinder<List<Pair<String, Dynamic<?>>>> field_5693 = DSL.fieldFinder("Palette", DSL.list(this.field_5695));
      protected final List<Dynamic<?>> properties;
      protected final int field_5694;
      @Nullable
      protected WordPackedArray blockStateMap;

      public ListFixer(Typed<?> _snowman, Schema _snowman) {
         if (!Objects.equals(_snowman.getType(TypeReferences.BLOCK_STATE), this.field_5695)) {
            throw new IllegalStateException("Block state type is not what was expected.");
         } else {
            Optional<List<Pair<String, Dynamic<?>>>> _snowmanxx = _snowman.getOptional(this.field_5693);
            this.properties = _snowmanxx.<List<Dynamic<?>>>map(_snowmanxxx -> _snowmanxxx.stream().<Dynamic<?>>map(Pair::getSecond).collect(Collectors.toList()))
               .orElse(ImmutableList.of());
            Dynamic<?> _snowmanxxx = (Dynamic<?>)_snowman.get(DSL.remainderFinder());
            this.field_5694 = _snowmanxxx.get("Y").asInt(0);
            this.computeFixableBlockStates(_snowmanxxx);
         }
      }

      protected void computeFixableBlockStates(Dynamic<?> _snowman) {
         if (this.needsFix()) {
            this.blockStateMap = null;
         } else {
            long[] _snowmanx = _snowman.get("BlockStates").asLongStream().toArray();
            int _snowmanxx = Math.max(4, DataFixUtils.ceillog2(this.properties.size()));
            this.blockStateMap = new WordPackedArray(_snowmanxx, 4096, _snowmanx);
         }
      }

      public Typed<?> method_5083(Typed<?> _snowman) {
         return this.isFixed()
            ? _snowman
            : _snowman.update(DSL.remainderFinder(), _snowmanx -> _snowmanx.set("BlockStates", _snowmanx.createLongList(Arrays.stream(this.blockStateMap.getAlignedArray()))))
               .set(this.field_5693, this.properties.stream().map(_snowmanx -> Pair.of(TypeReferences.BLOCK_STATE.typeName(), _snowmanx)).collect(Collectors.toList()));
      }

      public boolean isFixed() {
         return this.blockStateMap == null;
      }

      public int needsFix(int _snowman) {
         return this.blockStateMap.get(_snowman);
      }

      protected int computeFlags(String leafBlockName, boolean persistent, int _snowman) {
         return LeavesFix.LEAVES_MAP.get(leafBlockName) << 5 | (persistent ? 16 : 0) | _snowman;
      }

      int method_5077() {
         return this.field_5694;
      }

      protected abstract boolean needsFix();
   }
}
