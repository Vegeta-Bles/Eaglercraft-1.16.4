/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.options;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.util.Monitor;
import net.minecraft.client.util.VideoMode;
import net.minecraft.client.util.Window;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class FullScreenOption
extends DoubleOption {
    public FullScreenOption(Window window) {
        this(window, window.getMonitor());
    }

    private FullScreenOption(Window window, @Nullable Monitor monitor) {
        super("options.fullscreen.resolution", -1.0, monitor != null ? (double)(monitor.getVideoModeCount() - 1) : -1.0, 1.0f, gameOptions -> {
            if (monitor == null) {
                return -1.0;
            }
            Optional<VideoMode> optional = window.getVideoMode();
            return optional.map(videoMode -> monitor.findClosestVideoModeIndex((VideoMode)videoMode)).orElse(-1.0);
        }, (gameOptions, d) -> {
            if (monitor == null) {
                return;
            }
            if (d == -1.0) {
                window.setVideoMode(Optional.empty());
            } else {
                window.setVideoMode(Optional.of(monitor.getVideoMode(d.intValue())));
            }
        }, (gameOptions, doubleOption) -> {
            if (monitor == null) {
                return new TranslatableText("options.fullscreen.unavailable");
            }
            double d = doubleOption.get((GameOptions)gameOptions);
            if (d == -1.0) {
                return doubleOption.getGenericLabel(new TranslatableText("options.fullscreen.current"));
            }
            return doubleOption.getGenericLabel(new LiteralText(monitor.getVideoMode((int)d).toString()));
        });
    }
}

