import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

public class cxd {
   private final List<cxb> a;
   private cxb[] b = new cxb[0];
   private cxb[] c = new cxb[0];
   private Set<cxh> d;
   private int e;
   private final fx f;
   private final float g;
   private final boolean h;

   public cxd(List<cxb> var1, fx var2, boolean var3) {
      this.a = _snowman;
      this.f = _snowman;
      this.g = _snowman.isEmpty() ? Float.MAX_VALUE : this.a.get(this.a.size() - 1).c(this.f);
      this.h = _snowman;
   }

   public void a() {
      this.e++;
   }

   public boolean b() {
      return this.e <= 0;
   }

   public boolean c() {
      return this.e >= this.a.size();
   }

   @Nullable
   public cxb d() {
      return !this.a.isEmpty() ? this.a.get(this.a.size() - 1) : null;
   }

   public cxb a(int var1) {
      return this.a.get(_snowman);
   }

   public void b(int var1) {
      if (this.a.size() > _snowman) {
         this.a.subList(_snowman, this.a.size()).clear();
      }
   }

   public void a(int var1, cxb var2) {
      this.a.set(_snowman, _snowman);
   }

   public int e() {
      return this.a.size();
   }

   public int f() {
      return this.e;
   }

   public void c(int var1) {
      this.e = _snowman;
   }

   public dcn a(aqa var1, int var2) {
      cxb _snowman = this.a.get(_snowman);
      double _snowmanx = (double)_snowman.a + (double)((int)(_snowman.cy() + 1.0F)) * 0.5;
      double _snowmanxx = (double)_snowman.b;
      double _snowmanxxx = (double)_snowman.c + (double)((int)(_snowman.cy() + 1.0F)) * 0.5;
      return new dcn(_snowmanx, _snowmanxx, _snowmanxxx);
   }

   public fx d(int var1) {
      return this.a.get(_snowman).a();
   }

   public dcn a(aqa var1) {
      return this.a(_snowman, this.e);
   }

   public fx g() {
      return this.a.get(this.e).a();
   }

   public cxb h() {
      return this.a.get(this.e);
   }

   @Nullable
   public cxb i() {
      return this.e > 0 ? this.a.get(this.e - 1) : null;
   }

   public boolean a(@Nullable cxd var1) {
      if (_snowman == null) {
         return false;
      } else if (_snowman.a.size() != this.a.size()) {
         return false;
      } else {
         for (int _snowman = 0; _snowman < this.a.size(); _snowman++) {
            cxb _snowmanx = this.a.get(_snowman);
            cxb _snowmanxx = _snowman.a.get(_snowman);
            if (_snowmanx.a != _snowmanxx.a || _snowmanx.b != _snowmanxx.b || _snowmanx.c != _snowmanxx.c) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean j() {
      return this.h;
   }

   public cxb[] k() {
      return this.b;
   }

   public cxb[] l() {
      return this.c;
   }

   public static cxd b(nf var0) {
      boolean _snowman = _snowman.readBoolean();
      int _snowmanx = _snowman.readInt();
      int _snowmanxx = _snowman.readInt();
      Set<cxh> _snowmanxxx = Sets.newHashSet();

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxx; _snowmanxxxx++) {
         _snowmanxxx.add(cxh.c(_snowman));
      }

      fx _snowmanxxxx = new fx(_snowman.readInt(), _snowman.readInt(), _snowman.readInt());
      List<cxb> _snowmanxxxxx = Lists.newArrayList();
      int _snowmanxxxxxx = _snowman.readInt();

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxx++) {
         _snowmanxxxxx.add(cxb.b(_snowman));
      }

      cxb[] _snowmanxxxxxxx = new cxb[_snowman.readInt()];

      for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxxx.length; _snowmanxxxxxxxx++) {
         _snowmanxxxxxxx[_snowmanxxxxxxxx] = cxb.b(_snowman);
      }

      cxb[] _snowmanxxxxxxxx = new cxb[_snowman.readInt()];

      for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxxxxx.length; _snowmanxxxxxxxxx++) {
         _snowmanxxxxxxxx[_snowmanxxxxxxxxx] = cxb.b(_snowman);
      }

      cxd _snowmanxxxxxxxxx = new cxd(_snowmanxxxxx, _snowmanxxxx, _snowman);
      _snowmanxxxxxxxxx.b = _snowmanxxxxxxx;
      _snowmanxxxxxxxxx.c = _snowmanxxxxxxxx;
      _snowmanxxxxxxxxx.d = _snowmanxxx;
      _snowmanxxxxxxxxx.e = _snowmanx;
      return _snowmanxxxxxxxxx;
   }

   @Override
   public String toString() {
      return "Path(length=" + this.a.size() + ")";
   }

   public fx m() {
      return this.f;
   }

   public float n() {
      return this.g;
   }
}
