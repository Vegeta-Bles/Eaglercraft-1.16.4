package net.minecraft.client.options;

import java.util.Optional;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.Monitor;
import net.minecraft.client.util.VideoMode;
import net.minecraft.client.util.Window;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class FullScreenOption extends DoubleOption {
   public FullScreenOption(Window arg) {
      this(arg, arg.getMonitor());
   }

   private FullScreenOption(Window arg, @Nullable Monitor arg2) {
      super(
         "options.fullscreen.resolution",
         -1.0,
         arg2 != null ? (double)(arg2.getVideoModeCount() - 1) : -1.0,
         1.0F,
         arg3 -> {
            if (arg2 == null) {
               return -1.0;
            } else {
               Optional<VideoMode> optional = arg.getVideoMode();
               return optional.<Double>map(arg2xx -> (double)arg2.findClosestVideoModeIndex(arg2xx)).orElse(-1.0);
            }
         },
         (arg3, double_) -> {
            if (arg2 != null) {
               if (double_ == -1.0) {
                  arg.setVideoMode(Optional.empty());
               } else {
                  arg.setVideoMode(Optional.of(arg2.getVideoMode(double_.intValue())));
               }
            }
         },
         (arg2x, arg3) -> {
            if (arg2 == null) {
               return new TranslatableText("options.fullscreen.unavailable");
            } else {
               double d = arg3.get(arg2x);
               return d == -1.0
                  ? arg3.getGenericLabel(new TranslatableText("options.fullscreen.current"))
                  : arg3.getGenericLabel(new LiteralText(arg2.getVideoMode((int)d).toString()));
            }
         }
      );
   }
}
