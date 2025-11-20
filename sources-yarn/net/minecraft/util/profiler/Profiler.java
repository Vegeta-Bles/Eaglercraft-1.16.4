package net.minecraft.util.profiler;

import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface Profiler {
   void startTick();

   void endTick();

   void push(String location);

   void push(Supplier<String> locationGetter);

   void pop();

   void swap(String location);

   @Environment(EnvType.CLIENT)
   void swap(Supplier<String> locationGetter);

   void visit(String marker);

   void visit(Supplier<String> markerGetter);

   static Profiler union(final Profiler arg, final Profiler arg2) {
      if (arg == DummyProfiler.INSTANCE) {
         return arg2;
      } else {
         return arg2 == DummyProfiler.INSTANCE ? arg : new Profiler() {
            @Override
            public void startTick() {
               arg.startTick();
               arg2.startTick();
            }

            @Override
            public void endTick() {
               arg.endTick();
               arg2.endTick();
            }

            @Override
            public void push(String location) {
               arg.push(location);
               arg2.push(location);
            }

            @Override
            public void push(Supplier<String> locationGetter) {
               arg.push(locationGetter);
               arg2.push(locationGetter);
            }

            @Override
            public void pop() {
               arg.pop();
               arg2.pop();
            }

            @Override
            public void swap(String location) {
               arg.swap(location);
               arg2.swap(location);
            }

            @Environment(EnvType.CLIENT)
            @Override
            public void swap(Supplier<String> locationGetter) {
               arg.swap(locationGetter);
               arg2.swap(locationGetter);
            }

            @Override
            public void visit(String marker) {
               arg.visit(marker);
               arg2.visit(marker);
            }

            @Override
            public void visit(Supplier<String> markerGetter) {
               arg.visit(markerGetter);
               arg2.visit(markerGetter);
            }
         };
      }
   }
}
