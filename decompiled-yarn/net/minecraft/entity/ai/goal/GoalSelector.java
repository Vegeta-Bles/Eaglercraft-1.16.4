package net.minecraft.entity.ai.goal;

import com.google.common.collect.Sets;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoalSelector {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final PrioritizedGoal REPLACEABLE_GOAL = new PrioritizedGoal(Integer.MAX_VALUE, new Goal() {
      @Override
      public boolean canStart() {
         return false;
      }
   }) {
      @Override
      public boolean isRunning() {
         return false;
      }
   };
   private final Map<Goal.Control, PrioritizedGoal> goalsByControl = new EnumMap<>(Goal.Control.class);
   private final Set<PrioritizedGoal> goals = Sets.newLinkedHashSet();
   private final Supplier<Profiler> profiler;
   private final EnumSet<Goal.Control> disabledControls = EnumSet.noneOf(Goal.Control.class);
   private int timeInterval = 3;

   public GoalSelector(Supplier<Profiler> profiler) {
      this.profiler = profiler;
   }

   public void add(int priority, Goal goal) {
      this.goals.add(new PrioritizedGoal(priority, goal));
   }

   public void remove(Goal goal) {
      this.goals.stream().filter(_snowmanx -> _snowmanx.getGoal() == goal).filter(PrioritizedGoal::isRunning).forEach(PrioritizedGoal::stop);
      this.goals.removeIf(_snowmanx -> _snowmanx.getGoal() == goal);
   }

   public void tick() {
      Profiler _snowman = this.profiler.get();
      _snowman.push("goalCleanup");
      this.getRunningGoals()
         .filter(_snowmanx -> !_snowmanx.isRunning() || _snowmanx.getControls().stream().anyMatch(this.disabledControls::contains) || !_snowmanx.shouldContinue())
         .forEach(Goal::stop);
      this.goalsByControl.forEach((_snowmanx, _snowmanxx) -> {
         if (!_snowmanxx.isRunning()) {
            this.goalsByControl.remove(_snowmanx);
         }
      });
      _snowman.pop();
      _snowman.push("goalUpdate");
      this.goals
         .stream()
         .filter(_snowmanx -> !_snowmanx.isRunning())
         .filter(_snowmanx -> _snowmanx.getControls().stream().noneMatch(this.disabledControls::contains))
         .filter(_snowmanx -> _snowmanx.getControls().stream().allMatch(_snowmanxx -> this.goalsByControl.getOrDefault(_snowmanxx, REPLACEABLE_GOAL).canBeReplacedBy(_snowman)))
         .filter(PrioritizedGoal::canStart)
         .forEach(_snowmanx -> {
            _snowmanx.getControls().forEach(_snowmanxx -> {
               PrioritizedGoal _snowmanxx = this.goalsByControl.getOrDefault(_snowmanxx, REPLACEABLE_GOAL);
               _snowmanxx.stop();
               this.goalsByControl.put(_snowmanxx, _snowman);
            });
            _snowmanx.start();
         });
      _snowman.pop();
      _snowman.push("goalTick");
      this.getRunningGoals().forEach(PrioritizedGoal::tick);
      _snowman.pop();
   }

   public Stream<PrioritizedGoal> getRunningGoals() {
      return this.goals.stream().filter(PrioritizedGoal::isRunning);
   }

   public void disableControl(Goal.Control control) {
      this.disabledControls.add(control);
   }

   public void enableControl(Goal.Control control) {
      this.disabledControls.remove(control);
   }

   public void setControlEnabled(Goal.Control control, boolean enabled) {
      if (enabled) {
         this.enableControl(control);
      } else {
         this.disableControl(control);
      }
   }
}
