/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.openal.AL10
 *  org.lwjgl.openal.ALC10
 */
package net.minecraft.client.sound;

import javax.sound.sampled.AudioFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC10;

public class AlUtil {
    private static final Logger LOGGER = LogManager.getLogger();

    private static String getErrorMessage(int errorCode) {
        switch (errorCode) {
            case 40961: {
                return "Invalid name parameter.";
            }
            case 40962: {
                return "Invalid enumerated parameter value.";
            }
            case 40963: {
                return "Invalid parameter parameter value.";
            }
            case 40964: {
                return "Invalid operation.";
            }
            case 40965: {
                return "Unable to allocate memory.";
            }
        }
        return "An unrecognized error occurred.";
    }

    static boolean checkErrors(String sectionName) {
        int n = AL10.alGetError();
        if (n != 0) {
            LOGGER.error("{}: {}", (Object)sectionName, (Object)AlUtil.getErrorMessage(n));
            return true;
        }
        return false;
    }

    private static String getAlcErrorMessage(int errorCode) {
        switch (errorCode) {
            case 40961: {
                return "Invalid device.";
            }
            case 40962: {
                return "Invalid context.";
            }
            case 40964: {
                return "Invalid value.";
            }
            case 40963: {
                return "Illegal enum.";
            }
            case 40965: {
                return "Unable to allocate memory.";
            }
        }
        return "An unrecognized error occurred.";
    }

    static boolean checkAlcErrors(long deviceHandle, String sectionName) {
        int n = ALC10.alcGetError((long)deviceHandle);
        if (n != 0) {
            LOGGER.error("{}{}: {}", (Object)sectionName, (Object)deviceHandle, (Object)AlUtil.getAlcErrorMessage(n));
            return true;
        }
        return false;
    }

    static int getFormatId(AudioFormat format) {
        AudioFormat.Encoding encoding = format.getEncoding();
        int _snowman2 = format.getChannels();
        int _snowman3 = format.getSampleSizeInBits();
        if (encoding.equals(AudioFormat.Encoding.PCM_UNSIGNED) || encoding.equals(AudioFormat.Encoding.PCM_SIGNED)) {
            if (_snowman2 == 1) {
                if (_snowman3 == 8) {
                    return 4352;
                }
                if (_snowman3 == 16) {
                    return 4353;
                }
            } else if (_snowman2 == 2) {
                if (_snowman3 == 8) {
                    return 4354;
                }
                if (_snowman3 == 16) {
                    return 4355;
                }
            }
        }
        throw new IllegalArgumentException("Invalid audio format: " + format);
    }
}

