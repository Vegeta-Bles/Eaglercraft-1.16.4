package net.minecraft.resource;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public interface ResourcePackSource {
   ResourcePackSource field_25347 = method_29485();
   ResourcePackSource PACK_SOURCE_BUILTIN = method_29486("pack.source.builtin");
   ResourcePackSource PACK_SOURCE_WORLD = method_29486("pack.source.world");
   ResourcePackSource PACK_SOURCE_SERVER = method_29486("pack.source.server");

   Text decorate(Text var1);

   static ResourcePackSource method_29485() {
      return _snowman -> _snowman;
   }

   static ResourcePackSource method_29486(String _snowman) {
      Text _snowmanx = new TranslatableText(_snowman);
      return _snowmanxx -> new TranslatableText("pack.nameAndSource", _snowmanxx, _snowman).formatted(Formatting.GRAY);
   }
}
