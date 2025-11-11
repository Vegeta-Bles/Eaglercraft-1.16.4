import java.util.Collection;

public interface aj {
   aj a = var0 -> {
      String[][] _snowman = new String[var0.size()][];
      int _snowmanx = 0;

      for (String _snowmanxx : var0) {
         _snowman[_snowmanx++] = new String[]{_snowmanxx};
      }

      return _snowman;
   };
   aj b = var0 -> new String[][]{var0.toArray(new String[0])};

   String[][] createRequirements(Collection<String> var1);
}
