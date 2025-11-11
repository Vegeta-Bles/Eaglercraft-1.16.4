import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Supplier;

public final class che {
   public static final Codec<che> a = RecordCodecBuilder.create(
      var0 -> var0.group(chd.n.fieldOf("type").forGetter(che::a), cfy.a.fieldOf("generator").forGetter(che::c)).apply(var0, var0.stable(che::new))
   );
   public static final vj<che> b = vj.a(gm.M, new vk("overworld"));
   public static final vj<che> c = vj.a(gm.M, new vk("the_nether"));
   public static final vj<che> d = vj.a(gm.M, new vk("the_end"));
   private static final LinkedHashSet<vj<che>> e = Sets.newLinkedHashSet(ImmutableList.of(b, c, d));
   private final Supplier<chd> f;
   private final cfy g;

   public che(Supplier<chd> var1, cfy var2) {
      this.f = _snowman;
      this.g = _snowman;
   }

   public Supplier<chd> a() {
      return this.f;
   }

   public chd b() {
      return this.f.get();
   }

   public cfy c() {
      return this.g;
   }

   public static gi<che> a(gi<che> var0) {
      gi<che> _snowman = new gi<>(gm.M, Lifecycle.experimental());

      for (vj<che> _snowmanx : e) {
         che _snowmanxx = _snowman.a(_snowmanx);
         if (_snowmanxx != null) {
            _snowman.a(_snowmanx, _snowmanxx, _snowman.d(_snowmanxx));
         }
      }

      for (Entry<vj<che>, che> _snowmanxx : _snowman.d()) {
         vj<che> _snowmanxxx = _snowmanxx.getKey();
         if (!e.contains(_snowmanxxx)) {
            _snowman.a(_snowmanxxx, _snowmanxx.getValue(), _snowman.d(_snowmanxx.getValue()));
         }
      }

      return _snowman;
   }

   public static boolean a(long var0, gi<che> var2) {
      List<Entry<vj<che>, che>> _snowman = Lists.newArrayList(_snowman.d());
      if (_snowman.size() != e.size()) {
         return false;
      } else {
         Entry<vj<che>, che> _snowmanx = _snowman.get(0);
         Entry<vj<che>, che> _snowmanxx = _snowman.get(1);
         Entry<vj<che>, che> _snowmanxxx = _snowman.get(2);
         if (_snowmanx.getKey() != b || _snowmanxx.getKey() != c || _snowmanxxx.getKey() != d) {
            return false;
         } else if (!_snowmanx.getValue().b().a(chd.i) && _snowmanx.getValue().b() != chd.m) {
            return false;
         } else if (!_snowmanxx.getValue().b().a(chd.j)) {
            return false;
         } else if (!_snowmanxxx.getValue().b().a(chd.k)) {
            return false;
         } else if (_snowmanxx.getValue().c() instanceof cho && _snowmanxxx.getValue().c() instanceof cho) {
            cho _snowmanxxxx = (cho)_snowmanxx.getValue().c();
            cho _snowmanxxxxx = (cho)_snowmanxxx.getValue().c();
            if (!_snowmanxxxx.a(_snowman, chp.e)) {
               return false;
            } else if (!_snowmanxxxxx.a(_snowman, chp.f)) {
               return false;
            } else if (!(_snowmanxxxx.d() instanceof bth)) {
               return false;
            } else {
               bth _snowmanxxxxxx = (bth)_snowmanxxxx.d();
               if (!_snowmanxxxxxx.b(_snowman)) {
                  return false;
               } else if (!(_snowmanxxxxx.d() instanceof btk)) {
                  return false;
               } else {
                  btk _snowmanxxxxxxx = (btk)_snowmanxxxxx.d();
                  return _snowmanxxxxxxx.b(_snowman);
               }
            }
         } else {
            return false;
         }
      }
   }
}
