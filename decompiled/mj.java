import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.bytes.ByteOpenHashSet;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class mj extends mc<mt> {
   public static final mv<mj> a = new mv<mj>() {
      public mj a(DataInput var1, int var2, mm var3) throws IOException {
         _snowman.a(296L);
         if (_snowman > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
         } else {
            byte _snowman = _snowman.readByte();
            int _snowmanx = _snowman.readInt();
            if (_snowman == 0 && _snowmanx > 0) {
               throw new RuntimeException("Missing type on ListTag");
            } else {
               _snowman.a(32L * (long)_snowmanx);
               mv<?> _snowmanxx = mw.a(_snowman);
               List<mt> _snowmanxxx = Lists.newArrayListWithCapacity(_snowmanx);

               for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanx; _snowmanxxxx++) {
                  _snowmanxxx.add(_snowmanxx.b(_snowman, _snowman + 1, _snowman));
               }

               return new mj(_snowmanxxx, _snowman);
            }
         }
      }

      @Override
      public String a() {
         return "LIST";
      }

      @Override
      public String b() {
         return "TAG_List";
      }
   };
   private static final ByteSet b = new ByteOpenHashSet(Arrays.asList((byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6));
   private final List<mt> c;
   private byte h;

   private mj(List<mt> var1, byte var2) {
      this.c = _snowman;
      this.h = _snowman;
   }

   public mj() {
      this(Lists.newArrayList(), (byte)0);
   }

   @Override
   public void a(DataOutput var1) throws IOException {
      if (this.c.isEmpty()) {
         this.h = 0;
      } else {
         this.h = this.c.get(0).a();
      }

      _snowman.writeByte(this.h);
      _snowman.writeInt(this.c.size());

      for (mt _snowman : this.c) {
         _snowman.a(_snowman);
      }
   }

   @Override
   public byte a() {
      return 9;
   }

   @Override
   public mv<mj> b() {
      return a;
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder("[");

      for (int _snowmanx = 0; _snowmanx < this.c.size(); _snowmanx++) {
         if (_snowmanx != 0) {
            _snowman.append(',');
         }

         _snowman.append(this.c.get(_snowmanx));
      }

      return _snowman.append(']').toString();
   }

   private void g() {
      if (this.c.isEmpty()) {
         this.h = 0;
      }
   }

   @Override
   public mt c(int var1) {
      mt _snowman = this.c.remove(_snowman);
      this.g();
      return _snowman;
   }

   @Override
   public boolean isEmpty() {
      return this.c.isEmpty();
   }

   public md a(int var1) {
      if (_snowman >= 0 && _snowman < this.c.size()) {
         mt _snowman = this.c.get(_snowman);
         if (_snowman.a() == 10) {
            return (md)_snowman;
         }
      }

      return new md();
   }

   public mj b(int var1) {
      if (_snowman >= 0 && _snowman < this.c.size()) {
         mt _snowman = this.c.get(_snowman);
         if (_snowman.a() == 9) {
            return (mj)_snowman;
         }
      }

      return new mj();
   }

   public short d(int var1) {
      if (_snowman >= 0 && _snowman < this.c.size()) {
         mt _snowman = this.c.get(_snowman);
         if (_snowman.a() == 2) {
            return ((mr)_snowman).g();
         }
      }

      return 0;
   }

   public int e(int var1) {
      if (_snowman >= 0 && _snowman < this.c.size()) {
         mt _snowman = this.c.get(_snowman);
         if (_snowman.a() == 3) {
            return ((mi)_snowman).f();
         }
      }

      return 0;
   }

   public int[] f(int var1) {
      if (_snowman >= 0 && _snowman < this.c.size()) {
         mt _snowman = this.c.get(_snowman);
         if (_snowman.a() == 11) {
            return ((mh)_snowman).g();
         }
      }

      return new int[0];
   }

   public double h(int var1) {
      if (_snowman >= 0 && _snowman < this.c.size()) {
         mt _snowman = this.c.get(_snowman);
         if (_snowman.a() == 6) {
            return ((me)_snowman).i();
         }
      }

      return 0.0;
   }

   public float i(int var1) {
      if (_snowman >= 0 && _snowman < this.c.size()) {
         mt _snowman = this.c.get(_snowman);
         if (_snowman.a() == 5) {
            return ((mg)_snowman).j();
         }
      }

      return 0.0F;
   }

   public String j(int var1) {
      if (_snowman >= 0 && _snowman < this.c.size()) {
         mt _snowman = this.c.get(_snowman);
         return _snowman.a() == 8 ? _snowman.f_() : _snowman.toString();
      } else {
         return "";
      }
   }

   @Override
   public int size() {
      return this.c.size();
   }

   public mt k(int var1) {
      return this.c.get(_snowman);
   }

   @Override
   public mt d(int var1, mt var2) {
      mt _snowman = this.k(_snowman);
      if (!this.a(_snowman, _snowman)) {
         throw new UnsupportedOperationException(String.format("Trying to add tag of type %d to list of %d", _snowman.a(), this.h));
      } else {
         return _snowman;
      }
   }

   @Override
   public void c(int var1, mt var2) {
      if (!this.b(_snowman, _snowman)) {
         throw new UnsupportedOperationException(String.format("Trying to add tag of type %d to list of %d", _snowman.a(), this.h));
      }
   }

   @Override
   public boolean a(int var1, mt var2) {
      if (this.a(_snowman)) {
         this.c.set(_snowman, _snowman);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean b(int var1, mt var2) {
      if (this.a(_snowman)) {
         this.c.add(_snowman, _snowman);
         return true;
      } else {
         return false;
      }
   }

   private boolean a(mt var1) {
      if (_snowman.a() == 0) {
         return false;
      } else if (this.h == 0) {
         this.h = _snowman.a();
         return true;
      } else {
         return this.h == _snowman.a();
      }
   }

   public mj d() {
      Iterable<mt> _snowman = (Iterable<mt>)(mw.a(this.h).c() ? this.c : Iterables.transform(this.c, mt::c));
      List<mt> _snowmanx = Lists.newArrayList(_snowman);
      return new mj(_snowmanx, this.h);
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman ? true : _snowman instanceof mj && Objects.equals(this.c, ((mj)_snowman).c);
   }

   @Override
   public int hashCode() {
      return this.c.hashCode();
   }

   @Override
   public nr a(String var1, int var2) {
      if (this.isEmpty()) {
         return new oe("[]");
      } else if (b.contains(this.h) && this.size() <= 8) {
         String _snowman = ", ";
         nx _snowmanx = new oe("[");

         for (int _snowmanxx = 0; _snowmanxx < this.c.size(); _snowmanxx++) {
            if (_snowmanxx != 0) {
               _snowmanx.c(", ");
            }

            _snowmanx.a(this.c.get(_snowmanxx).l());
         }

         _snowmanx.c("]");
         return _snowmanx;
      } else {
         nx _snowman = new oe("[");
         if (!_snowman.isEmpty()) {
            _snowman.c("\n");
         }

         String _snowmanx = String.valueOf(',');

         for (int _snowmanxx = 0; _snowmanxx < this.c.size(); _snowmanxx++) {
            nx _snowmanxxx = new oe(Strings.repeat(_snowman, _snowman + 1));
            _snowmanxxx.a(this.c.get(_snowmanxx).a(_snowman, _snowman + 1));
            if (_snowmanxx != this.c.size() - 1) {
               _snowmanxxx.c(_snowmanx).c(_snowman.isEmpty() ? " " : "\n");
            }

            _snowman.a(_snowmanxxx);
         }

         if (!_snowman.isEmpty()) {
            _snowman.c("\n").c(Strings.repeat(_snowman, _snowman));
         }

         _snowman.c("]");
         return _snowman;
      }
   }

   @Override
   public byte d_() {
      return this.h;
   }

   @Override
   public void clear() {
      this.c.clear();
      this.h = 0;
   }
}
