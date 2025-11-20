/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.TextureStitcher;
import net.minecraft.client.texture.TextureStitcherCannotFitException;
import net.minecraft.client.texture.TextureTickListener;
import net.minecraft.client.texture.TextureUtil;
import net.minecraft.client.util.PngFile;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpriteAtlasTexture
extends AbstractTexture
implements TextureTickListener {
    private static final Logger LOGGER = LogManager.getLogger();
    @Deprecated
    public static final Identifier BLOCK_ATLAS_TEXTURE = PlayerScreenHandler.BLOCK_ATLAS_TEXTURE;
    @Deprecated
    public static final Identifier PARTICLE_ATLAS_TEXTURE = new Identifier("textures/atlas/particles.png");
    private final List<Sprite> animatedSprites = Lists.newArrayList();
    private final Set<Identifier> spritesToLoad = Sets.newHashSet();
    private final Map<Identifier, Sprite> sprites = Maps.newHashMap();
    private final Identifier id;
    private final int maxTextureSize;

    public SpriteAtlasTexture(Identifier identifier) {
        this.id = identifier;
        this.maxTextureSize = RenderSystem.maxSupportedTextureSize();
    }

    @Override
    public void load(ResourceManager manager) throws IOException {
    }

    public void upload(Data data) {
        this.spritesToLoad.clear();
        this.spritesToLoad.addAll(data.spriteIds);
        LOGGER.info("Created: {}x{}x{} {}-atlas", (Object)data.width, (Object)data.height, (Object)data.maxLevel, (Object)this.id);
        TextureUtil.allocate(this.getGlId(), data.maxLevel, data.width, data.height);
        this.clear();
        for (Sprite sprite : data.sprites) {
            this.sprites.put(sprite.getId(), sprite);
            try {
                sprite.upload();
            }
            catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.create(throwable, "Stitching texture atlas");
                CrashReportSection _snowman2 = crashReport.addElement("Texture being stitched together");
                _snowman2.add("Atlas path", this.id);
                _snowman2.add("Sprite", sprite);
                throw new CrashException(crashReport);
            }
            if (!sprite.isAnimated()) continue;
            this.animatedSprites.add(sprite);
        }
    }

    public Data stitch(ResourceManager resourceManager, Stream<Identifier> idStream, Profiler profiler, int mipmapLevel) {
        profiler.push("preparing");
        Set<Identifier> set = idStream.peek(identifier -> {
            if (identifier == null) {
                throw new IllegalArgumentException("Location cannot be null!");
            }
        }).collect(Collectors.toSet());
        int _snowman2 = this.maxTextureSize;
        TextureStitcher _snowman3 = new TextureStitcher(_snowman2, _snowman2, mipmapLevel);
        int _snowman4 = Integer.MAX_VALUE;
        int _snowman5 = 1 << mipmapLevel;
        profiler.swap("extracting_frames");
        for (Sprite.Info info2 : this.loadSprites(resourceManager, set)) {
            _snowman4 = Math.min(_snowman4, Math.min(info2.getWidth(), info2.getHeight()));
            int n = Math.min(Integer.lowestOneBit(info2.getWidth()), Integer.lowestOneBit(info2.getHeight()));
            if (n < _snowman5) {
                LOGGER.warn("Texture {} with size {}x{} limits mip level from {} to {}", (Object)info2.getId(), (Object)info2.getWidth(), (Object)info2.getHeight(), (Object)MathHelper.log2(_snowman5), (Object)MathHelper.log2(n));
                _snowman5 = n;
            }
            _snowman3.add(info2);
        }
        int n = Math.min(_snowman4, _snowman5);
        _snowman = MathHelper.log2(n);
        if (_snowman < mipmapLevel) {
            LOGGER.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", (Object)this.id, (Object)mipmapLevel, (Object)_snowman, (Object)n);
            n = _snowman;
        } else {
            n = mipmapLevel;
        }
        profiler.swap("register");
        _snowman3.add(MissingSprite.getMissingInfo());
        profiler.swap("stitching");
        try {
            _snowman3.stitch();
        }
        catch (TextureStitcherCannotFitException _snowman6) {
            CrashReport crashReport = CrashReport.create(_snowman6, "Stitching");
            CrashReportSection _snowman7 = crashReport.addElement("Stitcher");
            _snowman7.add("Sprites", _snowman6.getSprites().stream().map(info -> String.format("%s[%dx%d]", info.getId(), info.getWidth(), info.getHeight())).collect(Collectors.joining(",")));
            _snowman7.add("Max Texture Size", _snowman2);
            throw new CrashException(crashReport);
        }
        profiler.swap("loading");
        List<Sprite> list = this.loadSprites(resourceManager, _snowman3, n);
        profiler.pop();
        return new Data(set, _snowman3.getWidth(), _snowman3.getHeight(), n, list);
    }

    private Collection<Sprite.Info> loadSprites(ResourceManager resourceManager, Set<Identifier> ids) {
        ArrayList arrayList = Lists.newArrayList();
        ConcurrentLinkedQueue<Sprite.Info> _snowman2 = new ConcurrentLinkedQueue<Sprite.Info>();
        for (Identifier identifier : ids) {
            if (MissingSprite.getMissingSpriteId().equals(identifier)) continue;
            arrayList.add(CompletableFuture.runAsync(() -> {
                Sprite.Info _snowman4;
                Identifier identifier2 = this.getTexturePath(identifier);
                try (Resource resource = resourceManager.getResource(identifier2);){
                    PngFile pngFile = new PngFile(resource.toString(), resource.getInputStream());
                    AnimationResourceMetadata _snowman2 = resource.getMetadata(AnimationResourceMetadata.READER);
                    if (_snowman2 == null) {
                        _snowman2 = AnimationResourceMetadata.EMPTY;
                    }
                    Pair<Integer, Integer> _snowman3 = _snowman2.method_24141(pngFile.width, pngFile.height);
                    _snowman4 = new Sprite.Info(identifier, (Integer)_snowman3.getFirst(), (Integer)_snowman3.getSecond(), _snowman2);
                }
                catch (RuntimeException runtimeException) {
                    LOGGER.error("Unable to parse metadata from {} : {}", (Object)identifier2, (Object)runtimeException);
                    return;
                }
                catch (IOException iOException) {
                    LOGGER.error("Using missing texture, unable to load {} : {}", (Object)identifier2, (Object)iOException);
                    return;
                }
                _snowman2.add(_snowman4);
            }, Util.getMainWorkerExecutor()));
        }
        CompletableFuture.allOf(arrayList.toArray(new CompletableFuture[0])).join();
        return _snowman2;
    }

    private List<Sprite> loadSprites(ResourceManager resourceManager, TextureStitcher textureStitcher, int maxLevel) {
        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        ArrayList _snowman2 = Lists.newArrayList();
        textureStitcher.getStitchedSprites((info, atlasWidth, atlasHeight, x, y) -> {
            if (info == MissingSprite.getMissingInfo()) {
                MissingSprite missingSprite = MissingSprite.getMissingSprite(this, maxLevel, atlasWidth, atlasHeight, x, y);
                concurrentLinkedQueue.add(missingSprite);
            } else {
                List list2;
                _snowman2.add(CompletableFuture.runAsync(() -> {
                    Sprite sprite = this.loadSprite(resourceManager, info, atlasWidth, atlasHeight, maxLevel, x, y);
                    if (sprite != null) {
                        concurrentLinkedQueue.add(sprite);
                    }
                }, Util.getMainWorkerExecutor()));
            }
        });
        CompletableFuture.allOf(_snowman2.toArray(new CompletableFuture[0])).join();
        return Lists.newArrayList(concurrentLinkedQueue);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    private Sprite loadSprite(ResourceManager container, Sprite.Info info, int atlasWidth, int atlasHeight, int maxLevel, int x, int y) {
        Identifier identifier = this.getTexturePath(info.getId());
        try (Resource _snowman2 = container.getResource(identifier);){
            NativeImage _snowman3 = NativeImage.read(_snowman2.getInputStream());
            Sprite sprite = new Sprite(this, info, maxLevel, atlasWidth, atlasHeight, x, y, _snowman3);
            return sprite;
        }
        catch (RuntimeException runtimeException) {
            LOGGER.error("Unable to parse metadata from {}", (Object)identifier, (Object)runtimeException);
            return null;
        }
        catch (IOException iOException) {
            LOGGER.error("Using missing texture, unable to load {}", (Object)identifier, (Object)iOException);
            return null;
        }
    }

    private Identifier getTexturePath(Identifier identifier) {
        return new Identifier(identifier.getNamespace(), String.format("textures/%s%s", identifier.getPath(), ".png"));
    }

    public void tickAnimatedSprites() {
        this.bindTexture();
        for (Sprite sprite : this.animatedSprites) {
            sprite.tickAnimation();
        }
    }

    @Override
    public void tick() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::tickAnimatedSprites);
        } else {
            this.tickAnimatedSprites();
        }
    }

    public Sprite getSprite(Identifier id) {
        Sprite sprite = this.sprites.get(id);
        if (sprite == null) {
            return this.sprites.get(MissingSprite.getMissingSpriteId());
        }
        return sprite;
    }

    public void clear() {
        for (Sprite sprite : this.sprites.values()) {
            sprite.close();
        }
        this.sprites.clear();
        this.animatedSprites.clear();
    }

    public Identifier getId() {
        return this.id;
    }

    public void applyTextureFilter(Data data) {
        this.setFilter(false, data.maxLevel > 0);
    }

    public static class Data {
        final Set<Identifier> spriteIds;
        final int width;
        final int height;
        final int maxLevel;
        final List<Sprite> sprites;

        public Data(Set<Identifier> spriteIds, int width, int height, int maxLevel, List<Sprite> sprites) {
            this.spriteIds = spriteIds;
            this.width = width;
            this.height = height;
            this.maxLevel = maxLevel;
            this.sprites = sprites;
        }
    }
}

