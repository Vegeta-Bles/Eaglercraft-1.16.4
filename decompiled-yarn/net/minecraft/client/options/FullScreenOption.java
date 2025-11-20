package net.minecraft.client.options;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.util.Monitor;
import net.minecraft.client.util.VideoMode;
import net.minecraft.client.util.Window;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class FullScreenOption extends DoubleOption {
   public FullScreenOption(Window _snowman) {
      this(_snowman, _snowman.getMonitor());
   }

   private FullScreenOption(Window _snowman, @Nullable Monitor _snowman) {
      super(
         "options.fullscreen.resolution",
         -1.0,
         _snowman != null ? (double)(_snowman.getVideoModeCount() - 1) : -1.0,
         1.0F,
         _snowmanxxxx -> {
            if (_snowman == null) {
               return -1.0;
            } else {
               Optional<VideoMode> _snowmanxxx = _snowman.getVideoMode();
               return _snowmanxxx.<Double>map(_snowmanxxxxxxx -> (double)_snowman.findClosestVideoModeIndex(_snowmanxxxxxxx)).orElse(-1.0);
            }
         },
         (_snowmanxxxx, _snowmanxxx) -> {
            if (_snowman != null) {
               if (_snowmanxxx == -1.0) {
                  _snowman.setVideoMode(Optional.empty());
               } else {
                  _snowman.setVideoMode(Optional.of(_snowman.getVideoMode(_snowmanxxx.intValue())));
               }
            }
         },
         (_snowmanxxxxx, _snowmanxxxx) -> {
            if (_snowman == null) {
               return new TranslatableText("options.fullscreen.unavailable");
            } else {
               double _snowmanxxx = _snowmanxxxx.get(_snowmanxxxxx);
               return _snowmanxxx == -1.0
                  ? _snowmanxxxx.getGenericLabel(new TranslatableText("options.fullscreen.current"))
                  : _snowmanxxxx.getGenericLabel(new LiteralText(_snowman.getVideoMode((int)_snowmanxxx).toString()));
            }
         }
      );
   }
}
