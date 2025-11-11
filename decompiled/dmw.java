import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class dmw implements AutoCloseable {
   private static final dna a = new dna();
   private static final dea b = () -> 4.0F;
   private static final Random c = new Random();
   private final ekd d;
   private final vk e;
   private dmz f;
   private dmz g;
   private final List<deb> h = Lists.newArrayList();
   private final Int2ObjectMap<dmz> i = new Int2ObjectOpenHashMap();
   private final Int2ObjectMap<dea> j = new Int2ObjectOpenHashMap();
   private final Int2ObjectMap<IntList> k = new Int2ObjectOpenHashMap();
   private final List<dmx> l = Lists.newArrayList();

   public dmw(ekd var1, vk var2) {
      this.d = _snowman;
      this.e = _snowman;
   }

   public void a(List<deb> var1) {
      this.b();
      this.c();
      this.i.clear();
      this.j.clear();
      this.k.clear();
      this.f = this.a((dec)dnb.a);
      this.g = this.a((dec)dnc.a);
      IntSet _snowman = new IntOpenHashSet();

      for (deb _snowmanx : _snowman) {
         _snowman.addAll(_snowmanx.a());
      }

      Set<deb> _snowmanx = Sets.newHashSet();
      _snowman.forEach(var3 -> {
         for (deb _snowmanxx : _snowman) {
            dea _snowmanx = (dea)(var3 == 32 ? b : _snowmanxx.a(var3));
            if (_snowmanx != null) {
               _snowman.add(_snowmanxx);
               if (_snowmanx != dnb.a) {
                  ((IntList)this.k.computeIfAbsent(afm.f(_snowmanx.a(false)), var0 -> new IntArrayList())).add(var3);
               }
               break;
            }
         }
      });
      _snowman.stream().filter(_snowmanx::contains).forEach(this.h::add);
   }

   @Override
   public void close() {
      this.b();
      this.c();
   }

   private void b() {
      for (deb _snowman : this.h) {
         _snowman.close();
      }

      this.h.clear();
   }

   private void c() {
      for (dmx _snowman : this.l) {
         _snowman.close();
      }

      this.l.clear();
   }

   public dea a(int var1) {
      return (dea)this.j.computeIfAbsent(_snowman, var1x -> (dea)(var1x == 32 ? b : this.c(var1x)));
   }

   private dec c(int var1) {
      for (deb _snowman : this.h) {
         dec _snowmanx = _snowman.a(_snowman);
         if (_snowmanx != null) {
            return _snowmanx;
         }
      }

      return dnb.a;
   }

   public dmz b(int var1) {
      return (dmz)this.i.computeIfAbsent(_snowman, var1x -> (dmz)(var1x == 32 ? a : this.a(this.c(var1x))));
   }

   private dmz a(dec var1) {
      for (dmx _snowman : this.l) {
         dmz _snowmanx = _snowman.a(_snowman);
         if (_snowmanx != null) {
            return _snowmanx;
         }
      }

      dmx _snowmanx = new dmx(new vk(this.e.b(), this.e.a() + "/" + this.l.size()), _snowman.f());
      this.l.add(_snowmanx);
      this.d.a(_snowmanx.a(), _snowmanx);
      dmz _snowmanxx = _snowmanx.a(_snowman);
      return _snowmanxx == null ? this.f : _snowmanxx;
   }

   public dmz a(dea var1) {
      IntList _snowman = (IntList)this.k.get(afm.f(_snowman.a(false)));
      return _snowman != null && !_snowman.isEmpty() ? this.b(_snowman.getInt(c.nextInt(_snowman.size()))) : this.f;
   }

   public dmz a() {
      return this.g;
   }
}
