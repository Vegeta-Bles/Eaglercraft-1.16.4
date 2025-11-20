/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiler;

import java.util.function.Supplier;
import net.minecraft.util.profiler.DummyProfiler;

public interface Profiler {
    public void startTick();

    public void endTick();

    public void push(String var1);

    public void push(Supplier<String> var1);

    public void pop();

    public void swap(String var1);

    public void swap(Supplier<String> var1);

    public void visit(String var1);

    public void visit(Supplier<String> var1);

    public static Profiler union(Profiler profiler, Profiler profiler2) {
        if (profiler == DummyProfiler.INSTANCE) {
            return profiler2;
        }
        if (profiler2 == DummyProfiler.INSTANCE) {
            return profiler;
        }
        return new Profiler(profiler, profiler2){
            final /* synthetic */ Profiler field_21965;
            final /* synthetic */ Profiler field_21966;
            {
                this.field_21965 = profiler;
                this.field_21966 = profiler2;
            }

            public void startTick() {
                this.field_21965.startTick();
                this.field_21966.startTick();
            }

            public void endTick() {
                this.field_21965.endTick();
                this.field_21966.endTick();
            }

            public void push(String location) {
                this.field_21965.push(location);
                this.field_21966.push(location);
            }

            public void push(Supplier<String> locationGetter) {
                this.field_21965.push(locationGetter);
                this.field_21966.push(locationGetter);
            }

            public void pop() {
                this.field_21965.pop();
                this.field_21966.pop();
            }

            public void swap(String location) {
                this.field_21965.swap(location);
                this.field_21966.swap(location);
            }

            public void swap(Supplier<String> locationGetter) {
                this.field_21965.swap(locationGetter);
                this.field_21966.swap(locationGetter);
            }

            public void visit(String marker) {
                this.field_21965.visit(marker);
                this.field_21966.visit(marker);
            }

            public void visit(Supplier<String> markerGetter) {
                this.field_21965.visit(markerGetter);
                this.field_21966.visit(markerGetter);
            }
        };
    }
}

