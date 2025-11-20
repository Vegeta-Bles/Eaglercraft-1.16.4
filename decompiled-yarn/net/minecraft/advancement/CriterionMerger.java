package net.minecraft.advancement;

import java.util.Collection;

public interface CriterionMerger {
   CriterionMerger AND = _snowman -> {
      String[][] _snowmanx = new String[_snowman.size()][];
      int _snowmanxx = 0;

      for (String _snowmanxxx : _snowman) {
         _snowmanx[_snowmanxx++] = new String[]{_snowmanxxx};
      }

      return _snowmanx;
   };
   CriterionMerger OR = _snowman -> new String[][]{_snowman.toArray(new String[0])};

   String[][] createRequirements(Collection<String> criteriaNames);
}
