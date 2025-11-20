package net.minecraft.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

public interface ProgressListener {
   void method_15412(Text arg);

   @Environment(EnvType.CLIENT)
   void method_15413(Text arg);

   void method_15414(Text arg);

   void progressStagePercentage(int i);

   @Environment(EnvType.CLIENT)
   void setDone();
}
