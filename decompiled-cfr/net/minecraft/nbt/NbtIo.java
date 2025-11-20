/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.PositionTracker;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagReaders;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;

public class NbtIo {
    public static CompoundTag readCompressed(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file);){
            CompoundTag compoundTag = NbtIo.readCompressed(fileInputStream);
            return compoundTag;
        }
    }

    public static CompoundTag readCompressed(InputStream stream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(stream)));){
            CompoundTag compoundTag = NbtIo.read(dataInputStream, PositionTracker.DEFAULT);
            return compoundTag;
        }
    }

    public static void writeCompressed(CompoundTag tag, File file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            NbtIo.writeCompressed(tag, fileOutputStream);
        }
    }

    public static void writeCompressed(CompoundTag tag, OutputStream stream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(stream)));){
            NbtIo.write(tag, (DataOutput)dataOutputStream);
        }
    }

    public static void write(CompoundTag tag, File file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);){
            NbtIo.write(tag, (DataOutput)dataOutputStream);
        }
    }

    /*
     * Exception decompiling
     */
    @Nullable
    public static CompoundTag read(File file) throws IOException {
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

    public static CompoundTag read(DataInput input) throws IOException {
        return NbtIo.read(input, PositionTracker.DEFAULT);
    }

    public static CompoundTag read(DataInput input, PositionTracker tracker) throws IOException {
        Tag tag = NbtIo.read(input, 0, tracker);
        if (tag instanceof CompoundTag) {
            return (CompoundTag)tag;
        }
        throw new IOException("Root tag must be a named compound tag");
    }

    public static void write(CompoundTag tag, DataOutput output) throws IOException {
        NbtIo.write((Tag)tag, output);
    }

    private static void write(Tag tag, DataOutput output) throws IOException {
        output.writeByte(tag.getType());
        if (tag.getType() == 0) {
            return;
        }
        output.writeUTF("");
        tag.write(output);
    }

    private static Tag read(DataInput input, int depth, PositionTracker tracker) throws IOException {
        byte by = input.readByte();
        if (by == 0) {
            return EndTag.INSTANCE;
        }
        input.readUTF();
        try {
            return TagReaders.of(by).read(input, depth, tracker);
        }
        catch (IOException _snowman2) {
            CrashReport crashReport = CrashReport.create(_snowman2, "Loading NBT data");
            CrashReportSection _snowman3 = crashReport.addElement("NBT Tag");
            _snowman3.add("Tag type", by);
            throw new CrashException(crashReport);
        }
    }
}

