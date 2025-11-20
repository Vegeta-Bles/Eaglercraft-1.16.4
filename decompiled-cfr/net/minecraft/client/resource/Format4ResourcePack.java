/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.client.resource;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class Format4ResourcePack
implements ResourcePack {
    private static final Map<String, Pair<ChestType, Identifier>> NEW_TO_OLD_CHEST_TEXTURES = Util.make(Maps.newHashMap(), hashMap -> {
        hashMap.put("textures/entity/chest/normal_left.png", new Pair((Object)ChestType.LEFT, (Object)new Identifier("textures/entity/chest/normal_double.png")));
        hashMap.put("textures/entity/chest/normal_right.png", new Pair((Object)ChestType.RIGHT, (Object)new Identifier("textures/entity/chest/normal_double.png")));
        hashMap.put("textures/entity/chest/normal.png", new Pair((Object)ChestType.SINGLE, (Object)new Identifier("textures/entity/chest/normal.png")));
        hashMap.put("textures/entity/chest/trapped_left.png", new Pair((Object)ChestType.LEFT, (Object)new Identifier("textures/entity/chest/trapped_double.png")));
        hashMap.put("textures/entity/chest/trapped_right.png", new Pair((Object)ChestType.RIGHT, (Object)new Identifier("textures/entity/chest/trapped_double.png")));
        hashMap.put("textures/entity/chest/trapped.png", new Pair((Object)ChestType.SINGLE, (Object)new Identifier("textures/entity/chest/trapped.png")));
        hashMap.put("textures/entity/chest/christmas_left.png", new Pair((Object)ChestType.LEFT, (Object)new Identifier("textures/entity/chest/christmas_double.png")));
        hashMap.put("textures/entity/chest/christmas_right.png", new Pair((Object)ChestType.RIGHT, (Object)new Identifier("textures/entity/chest/christmas_double.png")));
        hashMap.put("textures/entity/chest/christmas.png", new Pair((Object)ChestType.SINGLE, (Object)new Identifier("textures/entity/chest/christmas.png")));
        hashMap.put("textures/entity/chest/ender.png", new Pair((Object)ChestType.SINGLE, (Object)new Identifier("textures/entity/chest/ender.png")));
    });
    private static final List<String> BANNER_PATTERN_TYPES = Lists.newArrayList((Object[])new String[]{"base", "border", "bricks", "circle", "creeper", "cross", "curly_border", "diagonal_left", "diagonal_right", "diagonal_up_left", "diagonal_up_right", "flower", "globe", "gradient", "gradient_up", "half_horizontal", "half_horizontal_bottom", "half_vertical", "half_vertical_right", "mojang", "rhombus", "skull", "small_stripes", "square_bottom_left", "square_bottom_right", "square_top_left", "square_top_right", "straight_cross", "stripe_bottom", "stripe_center", "stripe_downleft", "stripe_downright", "stripe_left", "stripe_middle", "stripe_right", "stripe_top", "triangle_bottom", "triangle_top", "triangles_bottom", "triangles_top"});
    private static final Set<String> SHIELD_PATTERN_TEXTURES = BANNER_PATTERN_TYPES.stream().map(string -> "textures/entity/shield/" + string + ".png").collect(Collectors.toSet());
    private static final Set<String> BANNER_PATTERN_TEXTURES = BANNER_PATTERN_TYPES.stream().map(string -> "textures/entity/banner/" + string + ".png").collect(Collectors.toSet());
    public static final Identifier OLD_SHIELD_BASE_TEXTURE = new Identifier("textures/entity/shield_base.png");
    public static final Identifier OLD_BANNER_BASE_TEXTURE = new Identifier("textures/entity/banner_base.png");
    public static final Identifier IRON_GOLEM_TEXTURE = new Identifier("textures/entity/iron_golem.png");
    private final ResourcePack parent;

    public Format4ResourcePack(ResourcePack parent) {
        this.parent = parent;
    }

    @Override
    public InputStream openRoot(String fileName) throws IOException {
        return this.parent.openRoot(fileName);
    }

    @Override
    public boolean contains(ResourceType type, Identifier id) {
        if (!"minecraft".equals(id.getNamespace())) {
            return this.parent.contains(type, id);
        }
        String string = id.getPath();
        if ("textures/misc/enchanted_item_glint.png".equals(string)) {
            return false;
        }
        if ("textures/entity/iron_golem/iron_golem.png".equals(string)) {
            return this.parent.contains(type, IRON_GOLEM_TEXTURE);
        }
        if ("textures/entity/conduit/wind.png".equals(string) || "textures/entity/conduit/wind_vertical.png".equals(string)) {
            return false;
        }
        if (SHIELD_PATTERN_TEXTURES.contains(string)) {
            return this.parent.contains(type, OLD_SHIELD_BASE_TEXTURE) && this.parent.contains(type, id);
        }
        if (BANNER_PATTERN_TEXTURES.contains(string)) {
            return this.parent.contains(type, OLD_BANNER_BASE_TEXTURE) && this.parent.contains(type, id);
        }
        Pair<ChestType, Identifier> _snowman2 = NEW_TO_OLD_CHEST_TEXTURES.get(string);
        if (_snowman2 != null && this.parent.contains(type, (Identifier)_snowman2.getSecond())) {
            return true;
        }
        return this.parent.contains(type, id);
    }

    @Override
    public InputStream open(ResourceType type, Identifier id) throws IOException {
        if (!"minecraft".equals(id.getNamespace())) {
            return this.parent.open(type, id);
        }
        String string = id.getPath();
        if ("textures/entity/iron_golem/iron_golem.png".equals(string)) {
            return this.parent.open(type, IRON_GOLEM_TEXTURE);
        }
        if (SHIELD_PATTERN_TEXTURES.contains(string)) {
            InputStream inputStream = Format4ResourcePack.openCroppedStream(this.parent.open(type, OLD_SHIELD_BASE_TEXTURE), this.parent.open(type, id), 64, 2, 2, 12, 22);
            if (inputStream != null) {
                return inputStream;
            }
        } else if (BANNER_PATTERN_TEXTURES.contains(string)) {
            InputStream inputStream = Format4ResourcePack.openCroppedStream(this.parent.open(type, OLD_BANNER_BASE_TEXTURE), this.parent.open(type, id), 64, 0, 0, 42, 41);
            if (inputStream != null) {
                return inputStream;
            }
        } else {
            if ("textures/entity/enderdragon/dragon.png".equals(string) || "textures/entity/enderdragon/dragon_exploding.png".equals(string)) {
                try (NativeImage nativeImage = NativeImage.read(this.parent.open(type, id));){
                    int n = nativeImage.getWidth() / 256;
                    for (_snowman = 88 * n; _snowman < 200 * n; ++_snowman) {
                        for (_snowman = 56 * n; _snowman < 112 * n; ++_snowman) {
                            nativeImage.setPixelColor(_snowman, _snowman, 0);
                        }
                    }
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(nativeImage.getBytes());
                    return byteArrayInputStream;
                }
            }
            if ("textures/entity/conduit/closed_eye.png".equals(string) || "textures/entity/conduit/open_eye.png".equals(string)) {
                return Format4ResourcePack.method_24199(this.parent.open(type, id));
            }
            Pair<ChestType, Identifier> _snowman2 = NEW_TO_OLD_CHEST_TEXTURES.get(string);
            if (_snowman2 != null) {
                ChestType chestType = (ChestType)_snowman2.getFirst();
                InputStream _snowman3 = this.parent.open(type, (Identifier)_snowman2.getSecond());
                if (chestType == ChestType.SINGLE) {
                    return Format4ResourcePack.cropSingleChestTexture(_snowman3);
                }
                if (chestType == ChestType.LEFT) {
                    return Format4ResourcePack.cropLeftChestTexture(_snowman3);
                }
                if (chestType == ChestType.RIGHT) {
                    return Format4ResourcePack.cropRightChestTexture(_snowman3);
                }
            }
        }
        return this.parent.open(type, id);
    }

    /*
     * Loose catch block
     */
    @Nullable
    public static InputStream openCroppedStream(InputStream inputStream, InputStream inputStream2, int n, int n2, int n3, int n4, int n5) throws IOException {
        try (NativeImage nativeImage = NativeImage.read(inputStream);){
            nativeImage3 = NativeImage.read(inputStream2);
            Throwable throwable = null;
            try {
                int n6 = nativeImage.getWidth();
                n7 = nativeImage.getHeight();
                if (n6 == nativeImage3.getWidth()) {
                    if (n7 == nativeImage3.getHeight()) {
                        try (NativeImage nativeImage2 = new NativeImage(n6, n7, true);){
                            int n72 = n6 / n;
                            for (i = n3 * n72; i < n5 * n72; ++i) {
                                for (j = n2 * n72; j < n4 * n72; ++j) {
                                    _snowman = NativeImage.getRed(nativeImage3.getPixelColor(j, i));
                                    _snowman = nativeImage.getPixelColor(j, i);
                                    nativeImage2.setPixelColor(j, i, NativeImage.getAbgrColor(_snowman, NativeImage.getBlue(_snowman), NativeImage.getGreen(_snowman), NativeImage.getRed(_snowman)));
                                }
                            }
                            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(nativeImage2.getBytes());
                            return byteArrayInputStream;
                        }
                    }
                }
                {
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    catch (Throwable throwable3) {
                        throw throwable3;
                    }
                }
            }
            finally {
                NativeImage nativeImage3;
                if (nativeImage3 != null) {
                    if (throwable != null) {
                        try {
                            nativeImage3.close();
                        }
                        catch (Throwable throwable4) {
                            throwable.addSuppressed(throwable4);
                        }
                    } else {
                        nativeImage3.close();
                    }
                }
            }
        }
    }

    /*
     * Exception decompiling
     */
    public static InputStream method_24199(InputStream _snowman) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static InputStream cropLeftChestTexture(InputStream _snowman) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static InputStream cropRightChestTexture(InputStream _snowman) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static InputStream cropSingleChestTexture(InputStream _snowman) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
        return this.parent.findResources(type, namespace, prefix, maxDepth, pathFilter);
    }

    @Override
    public Set<String> getNamespaces(ResourceType type) {
        return this.parent.getNamespaces(type);
    }

    @Override
    @Nullable
    public <T> T parseMetadata(ResourceMetadataReader<T> metaReader) throws IOException {
        return this.parent.parseMetadata(metaReader);
    }

    @Override
    public String getName() {
        return this.parent.getName();
    }

    @Override
    public void close() {
        this.parent.close();
    }

    private static void loadBytes(NativeImage source, NativeImage target, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, boolean bl2) {
        n6 *= n7;
        n5 *= n7;
        n3 *= n7;
        n4 *= n7;
        n *= n7;
        n2 *= n7;
        for (int i = 0; i < n6; ++i) {
            for (_snowman = 0; _snowman < n5; ++_snowman) {
                target.setPixelColor(n3 + _snowman, n4 + i, source.getPixelColor(n + (bl ? n5 - 1 - _snowman : _snowman), n2 + (bl2 ? n6 - 1 - i : i)));
            }
        }
    }
}

