/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.mojang.authlib.properties.PropertyMap
 *  com.mojang.authlib.properties.PropertyMap$Serializer
 *  javax.annotation.Nullable
 *  joptsimple.ArgumentAcceptingOptionSpec
 *  joptsimple.NonOptionArgumentSpec
 *  joptsimple.OptionParser
 *  joptsimple.OptionSet
 *  joptsimple.OptionSpec
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.blaze3d.systems.RenderCallStorage;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.List;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.Bootstrap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.util.GlException;
import net.minecraft.client.util.Session;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void main(String[] args) {
        Thread thread;
        MinecraftClient minecraftClient;
        OptionParser optionParser = new OptionParser();
        optionParser.allowsUnrecognizedOptions();
        optionParser.accepts("demo");
        optionParser.accepts("disableMultiplayer");
        optionParser.accepts("disableChat");
        optionParser.accepts("fullscreen");
        optionParser.accepts("checkGlErrors");
        ArgumentAcceptingOptionSpec _snowman2 = optionParser.accepts("server").withRequiredArg();
        ArgumentAcceptingOptionSpec _snowman3 = optionParser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo((Object)25565, (Object[])new Integer[0]);
        ArgumentAcceptingOptionSpec _snowman4 = optionParser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo((Object)new File("."), (Object[])new File[0]);
        ArgumentAcceptingOptionSpec _snowman5 = optionParser.accepts("assetsDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec _snowman6 = optionParser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec _snowman7 = optionParser.accepts("dataPackDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec _snowman8 = optionParser.accepts("proxyHost").withRequiredArg();
        ArgumentAcceptingOptionSpec _snowman9 = optionParser.accepts("proxyPort").withRequiredArg().defaultsTo((Object)"8080", (Object[])new String[0]).ofType(Integer.class);
        ArgumentAcceptingOptionSpec _snowman10 = optionParser.accepts("proxyUser").withRequiredArg();
        ArgumentAcceptingOptionSpec _snowman11 = optionParser.accepts("proxyPass").withRequiredArg();
        ArgumentAcceptingOptionSpec _snowman12 = optionParser.accepts("username").withRequiredArg().defaultsTo((Object)("Player" + Util.getMeasuringTimeMs() % 1000L), (Object[])new String[0]);
        ArgumentAcceptingOptionSpec _snowman13 = optionParser.accepts("uuid").withRequiredArg();
        ArgumentAcceptingOptionSpec _snowman14 = optionParser.accepts("accessToken").withRequiredArg().required();
        ArgumentAcceptingOptionSpec _snowman15 = optionParser.accepts("version").withRequiredArg().required();
        ArgumentAcceptingOptionSpec _snowman16 = optionParser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo((Object)854, (Object[])new Integer[0]);
        ArgumentAcceptingOptionSpec _snowman17 = optionParser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo((Object)480, (Object[])new Integer[0]);
        ArgumentAcceptingOptionSpec _snowman18 = optionParser.accepts("fullscreenWidth").withRequiredArg().ofType(Integer.class);
        ArgumentAcceptingOptionSpec _snowman19 = optionParser.accepts("fullscreenHeight").withRequiredArg().ofType(Integer.class);
        ArgumentAcceptingOptionSpec _snowman20 = optionParser.accepts("userProperties").withRequiredArg().defaultsTo((Object)"{}", (Object[])new String[0]);
        ArgumentAcceptingOptionSpec _snowman21 = optionParser.accepts("profileProperties").withRequiredArg().defaultsTo((Object)"{}", (Object[])new String[0]);
        ArgumentAcceptingOptionSpec _snowman22 = optionParser.accepts("assetIndex").withRequiredArg();
        ArgumentAcceptingOptionSpec _snowman23 = optionParser.accepts("userType").withRequiredArg().defaultsTo((Object)"legacy", (Object[])new String[0]);
        ArgumentAcceptingOptionSpec _snowman24 = optionParser.accepts("versionType").withRequiredArg().defaultsTo((Object)"release", (Object[])new String[0]);
        NonOptionArgumentSpec _snowman25 = optionParser.nonOptions();
        OptionSet _snowman26 = optionParser.parse(args);
        List _snowman27 = _snowman26.valuesOf((OptionSpec)_snowman25);
        if (!_snowman27.isEmpty()) {
            System.out.println("Completely ignored arguments: " + _snowman27);
        }
        String _snowman28 = (String)Main.getOption(_snowman26, _snowman8);
        Proxy _snowman29 = Proxy.NO_PROXY;
        if (_snowman28 != null) {
            try {
                _snowman29 = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(_snowman28, (int)((Integer)Main.getOption(_snowman26, _snowman9))));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        final String _snowman30 = (String)Main.getOption(_snowman26, _snowman10);
        final String _snowman31 = (String)Main.getOption(_snowman26, _snowman11);
        if (!_snowman29.equals(Proxy.NO_PROXY) && Main.isNotNullOrEmpty(_snowman30) && Main.isNotNullOrEmpty(_snowman31)) {
            Authenticator.setDefault(new Authenticator(){

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(_snowman30, _snowman31.toCharArray());
                }
            });
        }
        int _snowman32 = (Integer)Main.getOption(_snowman26, _snowman16);
        int _snowman33 = (Integer)Main.getOption(_snowman26, _snowman17);
        OptionalInt _snowman34 = Main.toOptional((Integer)Main.getOption(_snowman26, _snowman18));
        OptionalInt _snowman35 = Main.toOptional((Integer)Main.getOption(_snowman26, _snowman19));
        boolean _snowman36 = _snowman26.has("fullscreen");
        boolean _snowman37 = _snowman26.has("demo");
        boolean _snowman38 = _snowman26.has("disableMultiplayer");
        boolean _snowman39 = _snowman26.has("disableChat");
        String _snowman40 = (String)Main.getOption(_snowman26, _snowman15);
        Gson _snowman41 = new GsonBuilder().registerTypeAdapter(PropertyMap.class, (Object)new PropertyMap.Serializer()).create();
        PropertyMap _snowman42 = JsonHelper.deserialize(_snowman41, (String)Main.getOption(_snowman26, _snowman20), PropertyMap.class);
        PropertyMap _snowman43 = JsonHelper.deserialize(_snowman41, (String)Main.getOption(_snowman26, _snowman21), PropertyMap.class);
        String _snowman44 = (String)Main.getOption(_snowman26, _snowman24);
        File _snowman45 = (File)Main.getOption(_snowman26, _snowman4);
        File _snowman46 = _snowman26.has((OptionSpec)_snowman5) ? (File)Main.getOption(_snowman26, _snowman5) : new File(_snowman45, "assets/");
        File _snowman47 = _snowman26.has((OptionSpec)_snowman6) ? (File)Main.getOption(_snowman26, _snowman6) : new File(_snowman45, "resourcepacks/");
        String _snowman48 = _snowman26.has((OptionSpec)_snowman13) ? (String)_snowman13.value(_snowman26) : PlayerEntity.getOfflinePlayerUuid((String)_snowman12.value(_snowman26)).toString();
        String _snowman49 = _snowman26.has((OptionSpec)_snowman22) ? (String)_snowman22.value(_snowman26) : null;
        String _snowman50 = (String)Main.getOption(_snowman26, _snowman2);
        Integer _snowman51 = (Integer)Main.getOption(_snowman26, _snowman3);
        CrashReport.initCrashReport();
        Bootstrap.initialize();
        Bootstrap.logMissing();
        Util.startTimerHack();
        Session _snowman52 = new Session((String)_snowman12.value(_snowman26), _snowman48, (String)_snowman14.value(_snowman26), (String)_snowman23.value(_snowman26));
        RunArgs _snowman53 = new RunArgs(new RunArgs.Network(_snowman52, _snowman42, _snowman43, _snowman29), new WindowSettings(_snowman32, _snowman33, _snowman34, _snowman35, _snowman36), new RunArgs.Directories(_snowman45, _snowman47, _snowman46, _snowman49), new RunArgs.Game(_snowman37, _snowman40, _snowman44, _snowman38, _snowman39), new RunArgs.AutoConnect(_snowman50, _snowman51));
        Thread _snowman54 = new Thread("Client Shutdown Thread"){

            @Override
            public void run() {
                MinecraftClient minecraftClient = MinecraftClient.getInstance();
                if (minecraftClient == null) {
                    return;
                }
                IntegratedServer _snowman2 = minecraftClient.getServer();
                if (_snowman2 != null) {
                    _snowman2.stop(true);
                }
            }
        };
        _snowman54.setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER));
        Runtime.getRuntime().addShutdownHook(_snowman54);
        RenderCallStorage _snowman55 = new RenderCallStorage();
        try {
            Thread.currentThread().setName("Render thread");
            RenderSystem.initRenderThread();
            RenderSystem.beginInitialization();
            minecraftClient = new MinecraftClient(_snowman53);
            RenderSystem.finishInitialization();
        }
        catch (GlException glException) {
            LOGGER.warn("Failed to create window: ", (Throwable)glException);
            return;
        }
        catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.create(throwable, "Initializing game");
            crashReport.addElement("Initialization");
            MinecraftClient.addSystemDetailsToCrashReport(null, _snowman53.game.version, null, crashReport);
            MinecraftClient.printCrashReport(crashReport);
            return;
        }
        if (minecraftClient.shouldRenderAsync()) {
            thread = new Thread("Game thread"){

                @Override
                public void run() {
                    try {
                        RenderSystem.initGameThread(true);
                        minecraftClient.run();
                    }
                    catch (Throwable throwable) {
                        LOGGER.error("Exception in client thread", throwable);
                    }
                }
            };
            thread.start();
            while (minecraftClient.isRunning()) {
            }
        } else {
            thread = null;
            try {
                RenderSystem.initGameThread(false);
                minecraftClient.run();
            }
            catch (Throwable _snowman56) {
                LOGGER.error("Unhandled game exception", _snowman56);
            }
        }
        try {
            minecraftClient.scheduleStop();
            if (thread != null) {
                thread.join();
            }
        }
        catch (InterruptedException interruptedException) {
            LOGGER.error("Exception during client thread shutdown", (Throwable)interruptedException);
        }
        finally {
            minecraftClient.stop();
        }
    }

    private static OptionalInt toOptional(@Nullable Integer i) {
        return i != null ? OptionalInt.of(i) : OptionalInt.empty();
    }

    @Nullable
    private static <T> T getOption(OptionSet optionSet, OptionSpec<T> optionSpec) {
        try {
            return (T)optionSet.valueOf(optionSpec);
        }
        catch (Throwable throwable) {
            if (optionSpec instanceof ArgumentAcceptingOptionSpec && !(_snowman = (_snowman = (ArgumentAcceptingOptionSpec)optionSpec).defaultValues()).isEmpty()) {
                return (T)_snowman.get(0);
            }
            throw throwable;
        }
    }

    private static boolean isNotNullOrEmpty(@Nullable String s) {
        return s != null && !s.isEmpty();
    }

    static {
        System.setProperty("java.awt.headless", "true");
    }
}

