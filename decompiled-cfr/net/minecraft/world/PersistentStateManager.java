/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DataFixer
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import java.io.File;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.PersistentState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersistentStateManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<String, PersistentState> loadedStates = Maps.newHashMap();
    private final DataFixer dataFixer;
    private final File directory;

    public PersistentStateManager(File directory, DataFixer dataFixer) {
        this.dataFixer = dataFixer;
        this.directory = directory;
    }

    private File getFile(String id) {
        return new File(this.directory, id + ".dat");
    }

    public <T extends PersistentState> T getOrCreate(Supplier<T> factory, String id) {
        T t = this.get(factory, id);
        if (t != null) {
            return t;
        }
        PersistentState _snowman2 = (PersistentState)factory.get();
        this.set(_snowman2);
        return (T)_snowman2;
    }

    @Nullable
    public <T extends PersistentState> T get(Supplier<T> factory, String id) {
        PersistentState persistentState = this.loadedStates.get(id);
        if (persistentState == null && !this.loadedStates.containsKey(id)) {
            persistentState = this.readFromFile(factory, id);
            this.loadedStates.put(id, persistentState);
        }
        return (T)persistentState;
    }

    @Nullable
    private <T extends PersistentState> T readFromFile(Supplier<T> factory, String id) {
        try {
            File file = this.getFile(id);
            if (file.exists()) {
                PersistentState persistentState = (PersistentState)factory.get();
                CompoundTag _snowman2 = this.readTag(id, SharedConstants.getGameVersion().getWorldVersion());
                persistentState.fromTag(_snowman2.getCompound("data"));
                return (T)persistentState;
            }
        }
        catch (Exception exception) {
            LOGGER.error("Error loading saved data: {}", (Object)id, (Object)exception);
        }
        return null;
    }

    public void set(PersistentState state) {
        this.loadedStates.put(state.getId(), state);
    }

    /*
     * Exception decompiling
     */
    public CompoundTag readTag(String id, int dataVersion) throws IOException {
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

    private boolean isCompressed(PushbackInputStream pushbackInputStream) throws IOException {
        byte[] byArray = new byte[2];
        boolean _snowman2 = false;
        int _snowman3 = pushbackInputStream.read(byArray, 0, 2);
        if (_snowman3 == 2 && (_snowman = (byArray[1] & 0xFF) << 8 | byArray[0] & 0xFF) == 35615) {
            _snowman2 = true;
        }
        if (_snowman3 != 0) {
            pushbackInputStream.unread(byArray, 0, _snowman3);
        }
        return _snowman2;
    }

    public void save() {
        for (PersistentState persistentState : this.loadedStates.values()) {
            if (persistentState == null) continue;
            persistentState.save(this.getFile(persistentState.getId()));
        }
    }
}

