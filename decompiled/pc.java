import com.google.common.collect.Lists;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.io.IOException;
import java.util.List;

public class pc implements oj<om> {
   private int a;
   private Suggestions b;

   public pc() {
   }

   public pc(int var1, Suggestions var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      int _snowman = _snowman.i();
      int _snowmanx = _snowman.i();
      StringRange _snowmanxx = StringRange.between(_snowman, _snowman + _snowmanx);
      int _snowmanxxx = _snowman.i();
      List<Suggestion> _snowmanxxxx = Lists.newArrayListWithCapacity(_snowmanxxx);

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxx; _snowmanxxxxx++) {
         String _snowmanxxxxxx = _snowman.e(32767);
         nr _snowmanxxxxxxx = _snowman.readBoolean() ? _snowman.h() : null;
         _snowmanxxxx.add(new Suggestion(_snowmanxx, _snowmanxxxxxx, _snowmanxxxxxxx));
      }

      this.b = new Suggestions(_snowmanxx, _snowmanxxxx);
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.d(this.b.getRange().getStart());
      _snowman.d(this.b.getRange().getLength());
      _snowman.d(this.b.getList().size());

      for (Suggestion _snowman : this.b.getList()) {
         _snowman.a(_snowman.getText());
         _snowman.writeBoolean(_snowman.getTooltip() != null);
         if (_snowman.getTooltip() != null) {
            _snowman.a(ns.a(_snowman.getTooltip()));
         }
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   public Suggestions c() {
      return this.b;
   }
}
