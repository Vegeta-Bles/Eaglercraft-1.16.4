/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 *  org.apache.commons.codec.digest.DigestUtils
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.comparator.LastModifiedFileComparator
 *  org.apache.commons.io.filefilter.IOFileFilter
 *  org.apache.commons.io.filefilter.TrueFileFilter
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ProgressScreen;
import net.minecraft.client.resource.DefaultClientResourcePack;
import net.minecraft.client.resource.ResourceIndex;
import net.minecraft.client.util.NetworkUtils;
import net.minecraft.resource.DefaultResourcePack;
import net.minecraft.resource.DirectoryResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackCompatibility;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackProvider;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ZipResourcePack;
import net.minecraft.resource.metadata.PackResourceMetadata;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientBuiltinResourcePackProvider
implements ResourcePackProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Pattern ALPHANUMERAL = Pattern.compile("^[a-fA-F0-9]{40}$");
    private final DefaultResourcePack pack;
    private final File serverPacksRoot;
    private final ReentrantLock lock = new ReentrantLock();
    private final ResourceIndex index;
    @Nullable
    private CompletableFuture<?> downloadTask;
    @Nullable
    private ResourcePackProfile serverContainer;

    public ClientBuiltinResourcePackProvider(File serverPacksRoot, ResourceIndex index) {
        this.serverPacksRoot = serverPacksRoot;
        this.index = index;
        this.pack = new DefaultClientResourcePack(index);
    }

    @Override
    public void register(Consumer<ResourcePackProfile> consumer, ResourcePackProfile.Factory factory) {
        ResourcePackProfile resourcePackProfile = ResourcePackProfile.of("vanilla", true, () -> this.pack, factory, ResourcePackProfile.InsertionPosition.BOTTOM, ResourcePackSource.PACK_SOURCE_BUILTIN);
        if (resourcePackProfile != null) {
            consumer.accept(resourcePackProfile);
        }
        if (this.serverContainer != null) {
            consumer.accept(this.serverContainer);
        }
        if ((_snowman = this.method_25454(factory)) != null) {
            consumer.accept(_snowman);
        }
    }

    public DefaultResourcePack getPack() {
        return this.pack;
    }

    private static Map<String, String> getDownloadHeaders() {
        HashMap hashMap = Maps.newHashMap();
        hashMap.put("X-Minecraft-Username", MinecraftClient.getInstance().getSession().getUsername());
        hashMap.put("X-Minecraft-UUID", MinecraftClient.getInstance().getSession().getUuid());
        hashMap.put("X-Minecraft-Version", SharedConstants.getGameVersion().getName());
        hashMap.put("X-Minecraft-Version-ID", SharedConstants.getGameVersion().getId());
        hashMap.put("X-Minecraft-Pack-Format", String.valueOf(SharedConstants.getGameVersion().getPackVersion()));
        hashMap.put("User-Agent", "Minecraft Java/" + SharedConstants.getGameVersion().getName());
        return hashMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CompletableFuture<?> download(String string, String string2) {
        _snowman = DigestUtils.sha1Hex((String)string);
        _snowman = ALPHANUMERAL.matcher(string2).matches() ? string2 : "";
        this.lock.lock();
        try {
            CompletableFuture<String> _snowman4;
            this.clear();
            this.deleteOldServerPack();
            File file = new File(this.serverPacksRoot, _snowman);
            if (file.exists()) {
                _snowman4 = CompletableFuture.completedFuture("");
            } else {
                ProgressScreen progressScreen = new ProgressScreen();
                Map<String, String> _snowman2 = ClientBuiltinResourcePackProvider.getDownloadHeaders();
                MinecraftClient _snowman3 = MinecraftClient.getInstance();
                _snowman3.submitAndJoin(() -> _snowman3.openScreen(progressScreen));
                _snowman4 = NetworkUtils.download(file, string, _snowman2, 0x6400000, progressScreen, _snowman3.getNetworkProxy());
            }
            CompletableFuture<?> completableFuture = this.downloadTask = ((CompletableFuture)_snowman4.thenCompose(object -> {
                if (!this.verifyFile(_snowman, file)) {
                    return Util.completeExceptionally(new RuntimeException("Hash check failure for file " + file + ", see log"));
                }
                return this.loadServerPack(file, ResourcePackSource.PACK_SOURCE_SERVER);
            })).whenComplete((void_, throwable) -> {
                if (throwable != null) {
                    LOGGER.warn("Pack application failed: {}, deleting file {}", (Object)throwable.getMessage(), (Object)file);
                    ClientBuiltinResourcePackProvider.delete(file);
                }
            });
            return completableFuture;
        }
        finally {
            this.lock.unlock();
        }
    }

    private static void delete(File file) {
        try {
            Files.delete(file.toPath());
        }
        catch (IOException iOException) {
            LOGGER.warn("Failed to delete file {}: {}", (Object)file, (Object)iOException.getMessage());
        }
    }

    public void clear() {
        this.lock.lock();
        try {
            if (this.downloadTask != null) {
                this.downloadTask.cancel(true);
            }
            this.downloadTask = null;
            if (this.serverContainer != null) {
                this.serverContainer = null;
                MinecraftClient.getInstance().reloadResourcesConcurrently();
            }
        }
        finally {
            this.lock.unlock();
        }
    }

    private boolean verifyFile(String expectedSha1, File file) {
        try {
            String string;
            try (FileInputStream fileInputStream = new FileInputStream(file);){
                string = DigestUtils.sha1Hex((InputStream)fileInputStream);
            }
            if (expectedSha1.isEmpty()) {
                LOGGER.info("Found file {} without verification hash", (Object)file);
                return true;
            }
            if (string.toLowerCase(Locale.ROOT).equals(expectedSha1.toLowerCase(Locale.ROOT))) {
                LOGGER.info("Found file {} matching requested hash {}", (Object)file, (Object)expectedSha1);
                return true;
            }
            LOGGER.warn("File {} had wrong hash (expected {}, found {}).", (Object)file, (Object)expectedSha1, (Object)string);
        }
        catch (IOException iOException) {
            LOGGER.warn("File {} couldn't be hashed.", (Object)file, (Object)iOException);
        }
        return false;
    }

    private void deleteOldServerPack() {
        try {
            ArrayList arrayList = Lists.newArrayList((Iterable)FileUtils.listFiles((File)this.serverPacksRoot, (IOFileFilter)TrueFileFilter.TRUE, null));
            arrayList.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            int _snowman2 = 0;
            for (File file : arrayList) {
                if (_snowman2++ < 10) continue;
                LOGGER.info("Deleting old server resource pack {}", (Object)file.getName());
                FileUtils.deleteQuietly((File)file);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            LOGGER.error("Error while deleting old server resource pack : {}", (Object)illegalArgumentException.getMessage());
        }
    }

    public CompletableFuture<Void> loadServerPack(File packZip, ResourcePackSource resourcePackSource) {
        try (ZipResourcePack zipResourcePack = new ZipResourcePack(packZip);){
            PackResourceMetadata packResourceMetadata = zipResourcePack.parseMetadata(PackResourceMetadata.READER);
        }
        catch (IOException iOException) {
            return Util.completeExceptionally(new IOException(String.format("Invalid resourcepack at %s", packZip), iOException));
        }
        LOGGER.info("Applying server pack {}", (Object)packZip);
        this.serverContainer = new ResourcePackProfile("server", true, () -> new ZipResourcePack(packZip), new TranslatableText("resourcePack.server.name"), packResourceMetadata.getDescription(), ResourcePackCompatibility.from(packResourceMetadata.getPackFormat()), ResourcePackProfile.InsertionPosition.TOP, true, resourcePackSource);
        return MinecraftClient.getInstance().reloadResourcesConcurrently();
    }

    @Nullable
    private ResourcePackProfile method_25454(ResourcePackProfile.Factory factory) {
        ResourcePackProfile resourcePackProfile = null;
        File _snowman2 = this.index.getResource(new Identifier("resourcepacks/programmer_art.zip"));
        if (_snowman2 != null && _snowman2.isFile()) {
            resourcePackProfile = ClientBuiltinResourcePackProvider.method_25453(factory, () -> ClientBuiltinResourcePackProvider.method_16048(_snowman2));
        }
        if (resourcePackProfile == null && SharedConstants.isDevelopment && (_snowman = this.index.findFile("../resourcepacks/programmer_art")) != null && _snowman.isDirectory()) {
            resourcePackProfile = ClientBuiltinResourcePackProvider.method_25453(factory, () -> ClientBuiltinResourcePackProvider.method_25455(_snowman));
        }
        return resourcePackProfile;
    }

    @Nullable
    private static ResourcePackProfile method_25453(ResourcePackProfile.Factory factory, Supplier<ResourcePack> supplier) {
        return ResourcePackProfile.of("programer_art", false, supplier, factory, ResourcePackProfile.InsertionPosition.TOP, ResourcePackSource.PACK_SOURCE_BUILTIN);
    }

    private static DirectoryResourcePack method_25455(File file) {
        return new DirectoryResourcePack(file){

            public String getName() {
                return "Programmer Art";
            }
        };
    }

    private static ResourcePack method_16048(File file) {
        return new ZipResourcePack(file){

            public String getName() {
                return "Programmer Art";
            }
        };
    }
}

