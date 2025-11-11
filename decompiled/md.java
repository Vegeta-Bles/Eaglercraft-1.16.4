import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class md implements mt {
   public static final Codec<md> a = Codec.PASSTHROUGH.comapFlatMap(var0 -> {
      mt _snowman = (mt)var0.convert(mo.a).getValue();
      return _snowman instanceof md ? DataResult.success((md)_snowman) : DataResult.error("Not a compound tag: " + _snowman);
   }, var0 -> new Dynamic(mo.a, var0));
   private static final Logger c = LogManager.getLogger();
   private static final Pattern h = Pattern.compile("[A-Za-z0-9._+-]+");
   public static final mv<md> b = new mv<md>() {
      public md a(DataInput var1, int var2, mm var3) throws IOException {
         _snowman.a(384L);
         if (_snowman > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
         } else {
            Map<String, mt> _snowman = Maps.newHashMap();

            byte _snowmanx;
            while ((_snowmanx = md.c(_snowman, _snowman)) != 0) {
               String _snowmanxx = md.d(_snowman, _snowman);
               _snowman.a((long)(224 + 16 * _snowmanxx.length()));
               mt _snowmanxxx = md.b(mw.a(_snowmanx), _snowmanxx, _snowman, _snowman + 1, _snowman);
               if (_snowman.put(_snowmanxx, _snowmanxxx) != null) {
                  _snowman.a(288L);
               }
            }

            return new md(_snowman);
         }
      }

      @Override
      public String a() {
         return "COMPOUND";
      }

      @Override
      public String b() {
         return "TAG_Compound";
      }
   };
   private final Map<String, mt> i;

   protected md(Map<String, mt> var1) {
      this.i = _snowman;
   }

   public md() {
      this(Maps.newHashMap());
   }

   @Override
   public void a(DataOutput var1) throws IOException {
      for (String _snowman : this.i.keySet()) {
         mt _snowmanx = this.i.get(_snowman);
         a(_snowman, _snowmanx, _snowman);
      }

      _snowman.writeByte(0);
   }

   public Set<String> d() {
      return this.i.keySet();
   }

   @Override
   public byte a() {
      return 10;
   }

   @Override
   public mv<md> b() {
      return b;
   }

   public int e() {
      return this.i.size();
   }

   @Nullable
   public mt a(String var1, mt var2) {
      return this.i.put(_snowman, _snowman);
   }

   public void a(String var1, byte var2) {
      this.i.put(_snowman, mb.a(_snowman));
   }

   public void a(String var1, short var2) {
      this.i.put(_snowman, mr.a(_snowman));
   }

   public void b(String var1, int var2) {
      this.i.put(_snowman, mi.a(_snowman));
   }

   public void a(String var1, long var2) {
      this.i.put(_snowman, ml.a(_snowman));
   }

   public void a(String var1, UUID var2) {
      this.i.put(_snowman, mp.a(_snowman));
   }

   public UUID a(String var1) {
      return mp.a(this.c(_snowman));
   }

   public boolean b(String var1) {
      mt _snowman = this.c(_snowman);
      return _snowman != null && _snowman.b() == mh.a && ((mh)_snowman).g().length == 4;
   }

   public void a(String var1, float var2) {
      this.i.put(_snowman, mg.a(_snowman));
   }

   public void a(String var1, double var2) {
      this.i.put(_snowman, me.a(_snowman));
   }

   public void a(String var1, String var2) {
      this.i.put(_snowman, ms.a(_snowman));
   }

   public void a(String var1, byte[] var2) {
      this.i.put(_snowman, new ma(_snowman));
   }

   public void a(String var1, int[] var2) {
      this.i.put(_snowman, new mh(_snowman));
   }

   public void b(String var1, List<Integer> var2) {
      this.i.put(_snowman, new mh(_snowman));
   }

   public void a(String var1, long[] var2) {
      this.i.put(_snowman, new mk(_snowman));
   }

   public void c(String var1, List<Long> var2) {
      this.i.put(_snowman, new mk(_snowman));
   }

   public void a(String var1, boolean var2) {
      this.i.put(_snowman, mb.a(_snowman));
   }

   @Nullable
   public mt c(String var1) {
      return this.i.get(_snowman);
   }

   public byte d(String var1) {
      mt _snowman = this.i.get(_snowman);
      return _snowman == null ? 0 : _snowman.a();
   }

   public boolean e(String var1) {
      return this.i.containsKey(_snowman);
   }

   public boolean c(String var1, int var2) {
      int _snowman = this.d(_snowman);
      if (_snowman == _snowman) {
         return true;
      } else {
         return _snowman != 99 ? false : _snowman == 1 || _snowman == 2 || _snowman == 3 || _snowman == 4 || _snowman == 5 || _snowman == 6;
      }
   }

   public byte f(String var1) {
      try {
         if (this.c(_snowman, 99)) {
            return ((mq)this.i.get(_snowman)).h();
         }
      } catch (ClassCastException var3) {
      }

      return 0;
   }

   public short g(String var1) {
      try {
         if (this.c(_snowman, 99)) {
            return ((mq)this.i.get(_snowman)).g();
         }
      } catch (ClassCastException var3) {
      }

      return 0;
   }

   public int h(String var1) {
      try {
         if (this.c(_snowman, 99)) {
            return ((mq)this.i.get(_snowman)).f();
         }
      } catch (ClassCastException var3) {
      }

      return 0;
   }

   public long i(String var1) {
      try {
         if (this.c(_snowman, 99)) {
            return ((mq)this.i.get(_snowman)).e();
         }
      } catch (ClassCastException var3) {
      }

      return 0L;
   }

   public float j(String var1) {
      try {
         if (this.c(_snowman, 99)) {
            return ((mq)this.i.get(_snowman)).j();
         }
      } catch (ClassCastException var3) {
      }

      return 0.0F;
   }

   public double k(String var1) {
      try {
         if (this.c(_snowman, 99)) {
            return ((mq)this.i.get(_snowman)).i();
         }
      } catch (ClassCastException var3) {
      }

      return 0.0;
   }

   public String l(String var1) {
      try {
         if (this.c(_snowman, 8)) {
            return this.i.get(_snowman).f_();
         }
      } catch (ClassCastException var3) {
      }

      return "";
   }

   public byte[] m(String var1) {
      try {
         if (this.c(_snowman, 7)) {
            return ((ma)this.i.get(_snowman)).d();
         }
      } catch (ClassCastException var3) {
         throw new u(this.a(_snowman, ma.a, var3));
      }

      return new byte[0];
   }

   public int[] n(String var1) {
      try {
         if (this.c(_snowman, 11)) {
            return ((mh)this.i.get(_snowman)).g();
         }
      } catch (ClassCastException var3) {
         throw new u(this.a(_snowman, mh.a, var3));
      }

      return new int[0];
   }

   public long[] o(String var1) {
      try {
         if (this.c(_snowman, 12)) {
            return ((mk)this.i.get(_snowman)).g();
         }
      } catch (ClassCastException var3) {
         throw new u(this.a(_snowman, mk.a, var3));
      }

      return new long[0];
   }

   public md p(String var1) {
      try {
         if (this.c(_snowman, 10)) {
            return (md)this.i.get(_snowman);
         }
      } catch (ClassCastException var3) {
         throw new u(this.a(_snowman, b, var3));
      }

      return new md();
   }

   public mj d(String var1, int var2) {
      try {
         if (this.d(_snowman) == 9) {
            mj _snowman = (mj)this.i.get(_snowman);
            if (!_snowman.isEmpty() && _snowman.d_() != _snowman) {
               return new mj();
            }

            return _snowman;
         }
      } catch (ClassCastException var4) {
         throw new u(this.a(_snowman, mj.a, var4));
      }

      return new mj();
   }

   public boolean q(String var1) {
      return this.f(_snowman) != 0;
   }

   public void r(String var1) {
      this.i.remove(_snowman);
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder("{");
      Collection<String> _snowmanx = this.i.keySet();
      if (c.isDebugEnabled()) {
         List<String> _snowmanxx = Lists.newArrayList(this.i.keySet());
         Collections.sort(_snowmanxx);
         _snowmanx = _snowmanxx;
      }

      for (String _snowmanxx : _snowmanx) {
         if (_snowman.length() != 1) {
            _snowman.append(',');
         }

         _snowman.append(s(_snowmanxx)).append(':').append(this.i.get(_snowmanxx));
      }

      return _snowman.append('}').toString();
   }

   public boolean isEmpty() {
      return this.i.isEmpty();
   }

   private l a(String var1, mv<?> var2, ClassCastException var3) {
      l _snowman = l.a(_snowman, "Reading NBT data");
      m _snowmanx = _snowman.a("Corrupt NBT tag", 1);
      _snowmanx.a("Tag type found", () -> this.i.get(_snowman).b().a());
      _snowmanx.a("Tag type expected", _snowman::a);
      _snowmanx.a("Tag name", _snowman);
      return _snowman;
   }

   public md g() {
      Map<String, mt> _snowman = Maps.newHashMap(Maps.transformValues(this.i, mt::c));
      return new md(_snowman);
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman ? true : _snowman instanceof md && Objects.equals(this.i, ((md)_snowman).i);
   }

   @Override
   public int hashCode() {
      return this.i.hashCode();
   }

   private static void a(String var0, mt var1, DataOutput var2) throws IOException {
      _snowman.writeByte(_snowman.a());
      if (_snowman.a() != 0) {
         _snowman.writeUTF(_snowman);
         _snowman.a(_snowman);
      }
   }

   private static byte c(DataInput var0, mm var1) throws IOException {
      return _snowman.readByte();
   }

   private static String d(DataInput var0, mm var1) throws IOException {
      return _snowman.readUTF();
   }

   private static mt b(mv<?> var0, String var1, DataInput var2, int var3, mm var4) {
      try {
         return _snowman.b(_snowman, _snowman, _snowman);
      } catch (IOException var8) {
         l _snowman = l.a(var8, "Loading NBT data");
         m _snowmanx = _snowman.a("NBT Tag");
         _snowmanx.a("Tag name", _snowman);
         _snowmanx.a("Tag type", _snowman.a());
         throw new u(_snowman);
      }
   }

   public md a(md var1) {
      for (String _snowman : _snowman.i.keySet()) {
         mt _snowmanx = _snowman.i.get(_snowman);
         if (_snowmanx.a() == 10) {
            if (this.c(_snowman, 10)) {
               md _snowmanxx = this.p(_snowman);
               _snowmanxx.a((md)_snowmanx);
            } else {
               this.a(_snowman, _snowmanx.c());
            }
         } else {
            this.a(_snowman, _snowmanx.c());
         }
      }

      return this;
   }

   protected static String s(String var0) {
      return h.matcher(_snowman).matches() ? _snowman : ms.b(_snowman);
   }

   protected static nr t(String var0) {
      if (h.matcher(_snowman).matches()) {
         return new oe(_snowman).a(d);
      } else {
         String _snowman = ms.b(_snowman);
         String _snowmanx = _snowman.substring(0, 1);
         nr _snowmanxx = new oe(_snowman.substring(1, _snowman.length() - 1)).a(d);
         return new oe(_snowmanx).a(_snowmanxx).c(_snowmanx);
      }
   }

   @Override
   public nr a(String var1, int var2) {
      if (this.i.isEmpty()) {
         return new oe("{}");
      } else {
         nx _snowman = new oe("{");
         Collection<String> _snowmanx = this.i.keySet();
         if (c.isDebugEnabled()) {
            List<String> _snowmanxx = Lists.newArrayList(this.i.keySet());
            Collections.sort(_snowmanxx);
            _snowmanx = _snowmanxx;
         }

         if (!_snowman.isEmpty()) {
            _snowman.c("\n");
         }

         Iterator<String> _snowmanxx = _snowmanx.iterator();

         while (_snowmanxx.hasNext()) {
            String _snowmanxxx = _snowmanxx.next();
            nx _snowmanxxxx = new oe(Strings.repeat(_snowman, _snowman + 1)).a(t(_snowmanxxx)).c(String.valueOf(':')).c(" ").a(this.i.get(_snowmanxxx).a(_snowman, _snowman + 1));
            if (_snowmanxx.hasNext()) {
               _snowmanxxxx.c(String.valueOf(',')).c(_snowman.isEmpty() ? " " : "\n");
            }

            _snowman.a(_snowmanxxxx);
         }

         if (!_snowman.isEmpty()) {
            _snowman.c("\n").c(Strings.repeat(_snowman, _snowman));
         }

         _snowman.c("}");
         return _snowman;
      }
   }

   protected Map<String, mt> h() {
      return Collections.unmodifiableMap(this.i);
   }
}
