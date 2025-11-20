package net.minecraft.entity.ai.brain;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleBuilder {
   private final Schedule schedule;
   private final List<ScheduleBuilder.ActivityEntry> activities = Lists.newArrayList();

   public ScheduleBuilder(Schedule schedule) {
      this.schedule = schedule;
   }

   public ScheduleBuilder withActivity(int startTime, Activity activity) {
      this.activities.add(new ScheduleBuilder.ActivityEntry(startTime, activity));
      return this;
   }

   public Schedule build() {
      this.activities.stream().map(ScheduleBuilder.ActivityEntry::getActivity).collect(Collectors.toSet()).forEach(this.schedule::addActivity);
      this.activities.forEach(_snowman -> {
         Activity _snowmanx = _snowman.getActivity();
         this.schedule.getOtherRules(_snowmanx).forEach(_snowmanxx -> _snowmanxx.add(_snowman.getStartTime(), 0.0F));
         this.schedule.getRule(_snowmanx).add(_snowman.getStartTime(), 1.0F);
      });
      return this.schedule;
   }

   static class ActivityEntry {
      private final int startTime;
      private final Activity activity;

      public ActivityEntry(int startTime, Activity activity) {
         this.startTime = startTime;
         this.activity = activity;
      }

      public int getStartTime() {
         return this.startTime;
      }

      public Activity getActivity() {
         return this.activity;
      }
   }
}
