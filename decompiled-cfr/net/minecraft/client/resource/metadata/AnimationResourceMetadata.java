/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.client.resource.metadata;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resource.metadata.AnimationFrameResourceMetadata;
import net.minecraft.client.resource.metadata.AnimationResourceMetadataReader;

public class AnimationResourceMetadata {
    public static final AnimationResourceMetadataReader READER = new AnimationResourceMetadataReader();
    public static final AnimationResourceMetadata EMPTY = new AnimationResourceMetadata((List)Lists.newArrayList(), -1, -1, 1, false){

        public Pair<Integer, Integer> method_24141(int n, int n2) {
            return Pair.of((Object)n, (Object)n2);
        }
    };
    private final List<AnimationFrameResourceMetadata> frames;
    private final int width;
    private final int height;
    private final int defaultFrameTime;
    private final boolean interpolate;

    public AnimationResourceMetadata(List<AnimationFrameResourceMetadata> frames, int width, int height, int defaultFrameTime, boolean interpolate) {
        this.frames = frames;
        this.width = width;
        this.height = height;
        this.defaultFrameTime = defaultFrameTime;
        this.interpolate = interpolate;
    }

    private static boolean method_24142(int n, int n2) {
        return n / n2 * n2 == n;
    }

    public Pair<Integer, Integer> method_24141(int n, int n2) {
        Pair<Integer, Integer> pair = this.method_24143(n, n2);
        int _snowman2 = (Integer)pair.getFirst();
        int _snowman3 = (Integer)pair.getSecond();
        if (!AnimationResourceMetadata.method_24142(n, _snowman2) || !AnimationResourceMetadata.method_24142(n2, _snowman3)) {
            throw new IllegalArgumentException(String.format("Image size %s,%s is not multiply of frame size %s,%s", n, n2, _snowman2, _snowman3));
        }
        return pair;
    }

    private Pair<Integer, Integer> method_24143(int n, int n2) {
        if (this.width != -1) {
            if (this.height != -1) {
                return Pair.of((Object)this.width, (Object)this.height);
            }
            return Pair.of((Object)this.width, (Object)n2);
        }
        if (this.height != -1) {
            return Pair.of((Object)n, (Object)this.height);
        }
        _snowman = Math.min(n, n2);
        return Pair.of((Object)_snowman, (Object)_snowman);
    }

    public int getHeight(int n) {
        return this.height == -1 ? n : this.height;
    }

    public int getWidth(int n) {
        return this.width == -1 ? n : this.width;
    }

    public int getFrameCount() {
        return this.frames.size();
    }

    public int getDefaultFrameTime() {
        return this.defaultFrameTime;
    }

    public boolean shouldInterpolate() {
        return this.interpolate;
    }

    private AnimationFrameResourceMetadata getFrame(int frameIndex) {
        return this.frames.get(frameIndex);
    }

    public int getFrameTime(int frameIndex) {
        AnimationFrameResourceMetadata animationFrameResourceMetadata = this.getFrame(frameIndex);
        if (animationFrameResourceMetadata.usesDefaultFrameTime()) {
            return this.defaultFrameTime;
        }
        return animationFrameResourceMetadata.getTime();
    }

    public int getFrameIndex(int frameIndex) {
        return this.frames.get(frameIndex).getIndex();
    }

    public Set<Integer> getFrameIndexSet() {
        HashSet hashSet = Sets.newHashSet();
        for (AnimationFrameResourceMetadata animationFrameResourceMetadata : this.frames) {
            hashSet.add(animationFrameResourceMetadata.getIndex());
        }
        return hashSet;
    }
}

