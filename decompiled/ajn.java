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

public class ajn extends DataFix {
   private static final int[][] a = new int[][]{{-1, 0, 0}, {1, 0, 0}, {0, -1, 0}, {0, 1, 0}, {0, 0, -1}, {0, 0, 1}};
   private static final Object2IntMap<String> b = (Object2IntMap<String>)DataFixUtils.make(new Object2IntOpenHashMap(), var0 -> {
      var0.put("minecraft:acacia_leaves", 0);
      var0.put("minecraft:birch_leaves", 1);
      var0.put("minecraft:dark_oak_leaves", 2);
      var0.put("minecraft:jungle_leaves", 3);
      var0.put("minecraft:oak_leaves", 4);
      var0.put("minecraft:spruce_leaves", 5);
   });
   private static final Set<String> c = ImmutableSet.of(
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

   public ajn(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.c);
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
            var4x -> var4x.updateTyped(
                  _snowman,
                  var3x -> {
                     int[] _snowmanxxxxxx = new int[]{0};
                     Typed<?> _snowmanx = var3x.updateTyped(
                        _snowman,
                        var3xx -> {
                           Int2ObjectMap<ajn.a> _snowmanxx = new Int2ObjectOpenHashMap(
                              var3xx.getAllTyped(_snowman)
                                 .stream()
                                 .map(var1x -> new ajn.a((Typed<?>)var1x, this.getInputSchema()))
                                 .collect(Collectors.toMap(ajn.b::c, var0 -> (ajn.a)var0))
                           );
                           if (_snowmanxx.values().stream().allMatch(ajn.b::b)) {
                              return var3xx;
                           } else {
                              List<IntSet> _snowmanx = Lists.newArrayList();

                              for (int _snowmanxx = 0; _snowmanxx < 7; _snowmanxx++) {
                                 _snowmanx.add(new IntOpenHashSet());
                              }

                              ObjectIterator var25 = _snowmanxx.values().iterator();

                              while (var25.hasNext()) {
                                 ajn.a _snowmanxx = (ajn.a)var25.next();
                                 if (!_snowmanxx.b()) {
                                    for (int _snowmanxxx = 0; _snowmanxxx < 4096; _snowmanxxx++) {
                                       int _snowmanxxxx = _snowmanxx.c(_snowmanxxx);
                                       if (_snowmanxx.a(_snowmanxxxx)) {
                                          _snowmanx.get(0).add(_snowmanxx.c() << 12 | _snowmanxxx);
                                       } else if (_snowmanxx.b(_snowmanxxxx)) {
                                          int _snowmanxxxxx = this.a(_snowmanxxx);
                                          int _snowmanxxxxxx = this.c(_snowmanxxx);
                                          _snowman[0] |= a(_snowmanxxxxx == 0, _snowmanxxxxx == 15, _snowmanxxxxxx == 0, _snowmanxxxxxx == 15);
                                       }
                                    }
                                 }
                              }

                              for (int _snowmanxx = 1; _snowmanxx < 7; _snowmanxx++) {
                                 IntSet _snowmanxxxx = _snowmanx.get(_snowmanxx - 1);
                                 IntSet _snowmanxxxxx = _snowmanx.get(_snowmanxx);
                                 IntIterator _snowmanxxxxxx = _snowmanxxxx.iterator();

                                 while (_snowmanxxxxxx.hasNext()) {
                                    int _snowmanxxxxxxx = _snowmanxxxxxx.nextInt();
                                    int _snowmanxxxxxxxx = this.a(_snowmanxxxxxxx);
                                    int _snowmanxxxxxxxxx = this.b(_snowmanxxxxxxx);
                                    int _snowmanxxxxxxxxxx = this.c(_snowmanxxxxxxx);

                                    for (int[] _snowmanxxxxxxxxxxx : a) {
                                       int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxx + _snowmanxxxxxxxxxxx[0];
                                       int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxx[1];
                                       int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx[2];
                                       if (_snowmanxxxxxxxxxxxx >= 0
                                          && _snowmanxxxxxxxxxxxx <= 15
                                          && _snowmanxxxxxxxxxxxxxx >= 0
                                          && _snowmanxxxxxxxxxxxxxx <= 15
                                          && _snowmanxxxxxxxxxxxxx >= 0
                                          && _snowmanxxxxxxxxxxxxx <= 255) {
                                          ajn.a _snowmanxxxxxxxxxxxxxxx = (ajn.a)_snowmanxx.get(_snowmanxxxxxxxxxxxxx >> 4);
                                          if (_snowmanxxxxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxxxxx.b()) {
                                             int _snowmanxxxxxxxxxxxxxxxx = a(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx & 15, _snowmanxxxxxxxxxxxxxx);
                                             int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.c(_snowmanxxxxxxxxxxxxxxxx);
                                             if (_snowmanxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxxxxxxx)) {
                                                int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.d(_snowmanxxxxxxxxxxxxxxxxx);
                                                if (_snowmanxxxxxxxxxxxxxxxxxx > _snowmanxx) {
                                                   _snowmanxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxx);
                                                   _snowmanxxxxx.add(a(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx));
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }

                              return var3xx.updateTyped(_snowman, var1x -> ((ajn.a)_snowman.get(((Dynamic)var1x.get(DSL.remainderFinder())).get("Y").asInt(0))).a(var1x));
                           }
                        }
                     );
                     if (_snowmanxxxxxx[0] != 0) {
                        _snowmanx = _snowmanx.update(DSL.remainderFinder(), var1x -> {
                           Dynamic<?> _snowmanxx = (Dynamic<?>)DataFixUtils.orElse(var1x.get("UpgradeData").result(), var1x.emptyMap());
                           return var1x.set("UpgradeData", _snowmanxx.set("Sides", var1x.createByte((byte)(_snowmanxx.get("Sides").asByte((byte)0) | _snowman[0]))));
                        });
                     }

                     return _snowmanx;
                  }
               )
         );
      }
   }

   public static int a(int var0, int var1, int var2) {
      return _snowman << 8 | _snowman << 4 | _snowman;
   }

   private int a(int var1) {
      return _snowman & 15;
   }

   private int b(int var1) {
      return _snowman >> 8 & 0xFF;
   }

   private int c(int var1) {
      return _snowman >> 4 & 15;
   }

   public static int a(boolean var0, boolean var1, boolean var2, boolean var3) {
      int _snowman = 0;
      if (_snowman) {
         if (_snowman) {
            _snowman |= 2;
         } else if (_snowman) {
            _snowman |= 128;
         } else {
            _snowman |= 1;
         }
      } else if (_snowman) {
         if (_snowman) {
            _snowman |= 32;
         } else if (_snowman) {
            _snowman |= 8;
         } else {
            _snowman |= 16;
         }
      } else if (_snowman) {
         _snowman |= 4;
      } else if (_snowman) {
         _snowman |= 64;
      }

      return _snowman;
   }

   public static final class a extends ajn.b {
      @Nullable
      private IntSet e;
      @Nullable
      private IntSet f;
      @Nullable
      private Int2IntMap g;

      public a(Typed<?> var1, Schema var2) {
         super(_snowman, _snowman);
      }

      @Override
      protected boolean a() {
         this.e = new IntOpenHashSet();
         this.f = new IntOpenHashSet();
         this.g = new Int2IntOpenHashMap();

         for (int _snowman = 0; _snowman < this.b.size(); _snowman++) {
            Dynamic<?> _snowmanx = this.b.get(_snowman);
            String _snowmanxx = _snowmanx.get("Name").asString("");
            if (ajn.b.containsKey(_snowmanxx)) {
               boolean _snowmanxxx = Objects.equals(_snowmanx.get("Properties").get("decayable").asString(""), "false");
               this.e.add(_snowman);
               this.g.put(this.a(_snowmanxx, _snowmanxxx, 7), _snowman);
               this.b.set(_snowman, this.a(_snowmanx, _snowmanxx, _snowmanxxx, 7));
            }

            if (ajn.c.contains(_snowmanxx)) {
               this.f.add(_snowman);
            }
         }

         return this.e.isEmpty() && this.f.isEmpty();
      }

      private Dynamic<?> a(Dynamic<?> var1, String var2, boolean var3, int var4) {
         Dynamic<?> _snowman = _snowman.emptyMap();
         _snowman = _snowman.set("persistent", _snowman.createString(_snowman ? "true" : "false"));
         _snowman = _snowman.set("distance", _snowman.createString(Integer.toString(_snowman)));
         Dynamic<?> _snowmanx = _snowman.emptyMap();
         _snowmanx = _snowmanx.set("Properties", _snowman);
         return _snowmanx.set("Name", _snowmanx.createString(_snowman));
      }

      public boolean a(int var1) {
         return this.f.contains(_snowman);
      }

      public boolean b(int var1) {
         return this.e.contains(_snowman);
      }

      private int d(int var1) {
         return this.a(_snowman) ? 0 : Integer.parseInt(this.b.get(_snowman).get("Properties").get("distance").asString(""));
      }

      private void a(int var1, int var2, int var3) {
         Dynamic<?> _snowman = this.b.get(_snowman);
         String _snowmanx = _snowman.get("Name").asString("");
         boolean _snowmanxx = Objects.equals(_snowman.get("Properties").get("persistent").asString(""), "true");
         int _snowmanxxx = this.a(_snowmanx, _snowmanxx, _snowman);
         if (!this.g.containsKey(_snowmanxxx)) {
            int _snowmanxxxx = this.b.size();
            this.e.add(_snowmanxxxx);
            this.g.put(_snowmanxxx, _snowmanxxxx);
            this.b.add(this.a(_snowman, _snowmanx, _snowmanxx, _snowman));
         }

         int _snowmanxxxx = this.g.get(_snowmanxxx);
         if (1 << this.d.b() <= _snowmanxxxx) {
            agc _snowmanxxxxx = new agc(this.d.b() + 1, 4096);

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 4096; _snowmanxxxxxx++) {
               _snowmanxxxxx.a(_snowmanxxxxxx, this.d.a(_snowmanxxxxxx));
            }

            this.d = _snowmanxxxxx;
         }

         this.d.a(_snowman, _snowmanxxxx);
      }
   }

   public abstract static class b {
      private final Type<Pair<String, Dynamic<?>>> e = DSL.named(akn.m.typeName(), DSL.remainderType());
      protected final OpticFinder<List<Pair<String, Dynamic<?>>>> a = DSL.fieldFinder("Palette", DSL.list(this.e));
      protected final List<Dynamic<?>> b;
      protected final int c;
      @Nullable
      protected agc d;

      public b(Typed<?> var1, Schema var2) {
         if (!Objects.equals(_snowman.getType(akn.m), this.e)) {
            throw new IllegalStateException("Block state type is not what was expected.");
         } else {
            Optional<List<Pair<String, Dynamic<?>>>> _snowman = _snowman.getOptional(this.a);
            this.b = _snowman.<List<Dynamic<?>>>map(var0 -> var0.stream().<Dynamic<?>>map(Pair::getSecond).collect(Collectors.toList())).orElse(ImmutableList.of());
            Dynamic<?> _snowmanx = (Dynamic<?>)_snowman.get(DSL.remainderFinder());
            this.c = _snowmanx.get("Y").asInt(0);
            this.a(_snowmanx);
         }
      }

      protected void a(Dynamic<?> var1) {
         if (this.a()) {
            this.d = null;
         } else {
            long[] _snowman = _snowman.get("BlockStates").asLongStream().toArray();
            int _snowmanx = Math.max(4, DataFixUtils.ceillog2(this.b.size()));
            this.d = new agc(_snowmanx, 4096, _snowman);
         }
      }

      public Typed<?> a(Typed<?> var1) {
         return this.b()
            ? _snowman
            : _snowman.update(DSL.remainderFinder(), var1x -> var1x.set("BlockStates", var1x.createLongList(Arrays.stream(this.d.a()))))
               .set(this.a, this.b.stream().map(var0 -> Pair.of(akn.m.typeName(), var0)).collect(Collectors.toList()));
      }

      public boolean b() {
         return this.d == null;
      }

      public int c(int var1) {
         return this.d.a(_snowman);
      }

      protected int a(String var1, boolean var2, int var3) {
         return ajn.b.get(_snowman) << 5 | (_snowman ? 16 : 0) | _snowman;
      }

      int c() {
         return this.c;
      }

      protected abstract boolean a();
   }
}
