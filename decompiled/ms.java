import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class ms implements mt {
   public static final mv<ms> a = new mv<ms>() {
      public ms a(DataInput var1, int var2, mm var3) throws IOException {
         _snowman.a(288L);
         String _snowman = _snowman.readUTF();
         _snowman.a((long)(16 * _snowman.length()));
         return ms.a(_snowman);
      }

      @Override
      public String a() {
         return "STRING";
      }

      @Override
      public String b() {
         return "TAG_String";
      }

      @Override
      public boolean c() {
         return true;
      }
   };
   private static final ms b = new ms("");
   private final String c;

   private ms(String var1) {
      Objects.requireNonNull(_snowman, "Null string not allowed");
      this.c = _snowman;
   }

   public static ms a(String var0) {
      return _snowman.isEmpty() ? b : new ms(_snowman);
   }

   @Override
   public void a(DataOutput var1) throws IOException {
      _snowman.writeUTF(this.c);
   }

   @Override
   public byte a() {
      return 8;
   }

   @Override
   public mv<ms> b() {
      return a;
   }

   @Override
   public String toString() {
      return b(this.c);
   }

   public ms d() {
      return this;
   }

   @Override
   public boolean equals(Object var1) {
      return this == _snowman ? true : _snowman instanceof ms && Objects.equals(this.c, ((ms)_snowman).c);
   }

   @Override
   public int hashCode() {
      return this.c.hashCode();
   }

   @Override
   public String f_() {
      return this.c;
   }

   @Override
   public nr a(String var1, int var2) {
      String _snowman = b(this.c);
      String _snowmanx = _snowman.substring(0, 1);
      nr _snowmanxx = new oe(_snowman.substring(1, _snowman.length() - 1)).a(e);
      return new oe(_snowmanx).a(_snowmanxx).c(_snowmanx);
   }

   public static String b(String var0) {
      StringBuilder _snowman = new StringBuilder(" ");
      char _snowmanx = 0;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.length(); _snowmanxx++) {
         char _snowmanxxx = _snowman.charAt(_snowmanxx);
         if (_snowmanxxx == '\\') {
            _snowman.append('\\');
         } else if (_snowmanxxx == '"' || _snowmanxxx == '\'') {
            if (_snowmanx == 0) {
               _snowmanx = (char)(_snowmanxxx == '"' ? 39 : 34);
            }

            if (_snowmanx == _snowmanxxx) {
               _snowman.append('\\');
            }
         }

         _snowman.append(_snowmanxxx);
      }

      if (_snowmanx == 0) {
         _snowmanx = '"';
      }

      _snowman.setCharAt(0, _snowmanx);
      _snowman.append(_snowmanx);
      return _snowman.toString();
   }
}
