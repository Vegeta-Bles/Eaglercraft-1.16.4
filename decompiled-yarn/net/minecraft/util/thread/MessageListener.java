package net.minecraft.util.thread;

import com.mojang.datafixers.util.Either;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public interface MessageListener<Msg> extends AutoCloseable {
   String getName();

   void send(Msg message);

   @Override
   default void close() {
   }

   default <Source> CompletableFuture<Source> ask(Function<? super MessageListener<Source>, ? extends Msg> messageProvider) {
      CompletableFuture<Source> _snowman = new CompletableFuture<>();
      Msg _snowmanx = (Msg)messageProvider.apply(create("ask future procesor handle", _snowman::complete));
      this.send(_snowmanx);
      return _snowman;
   }

   default <Source> CompletableFuture<Source> method_27918(Function<? super MessageListener<Either<Source, Exception>>, ? extends Msg> _snowman) {
      CompletableFuture<Source> _snowmanx = new CompletableFuture<>();
      Msg _snowmanxx = (Msg)_snowman.apply(create("ask future procesor handle", _snowmanxxx -> {
         _snowmanxxx.ifLeft(_snowman::complete);
         _snowmanxxx.ifRight(_snowman::completeExceptionally);
      }));
      this.send(_snowmanxx);
      return _snowmanx;
   }

   static <Msg> MessageListener<Msg> create(String name, Consumer<Msg> action) {
      return new MessageListener<Msg>() {
         @Override
         public String getName() {
            return name;
         }

         @Override
         public void send(Msg message) {
            action.accept(message);
         }

         @Override
         public String toString() {
            return name;
         }
      };
   }
}
