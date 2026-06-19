package ca.rafemurray;

import ca.rafemurray.course.CourseChangedCallback;
import ca.rafemurray.course.Courses;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.world.InteractionResult;

public class GlobalSavedData {
    public static void initialize() {
        // We use events to create the global courses instance
        ServerLifecycleEvents.SERVER_STARTED.register(Courses::create);

        CourseChangedCallback.EVENT.register(course -> {
            Courses.getInstance().addOrUpdateCourse(course);
            return InteractionResult.PASS;
        });
    }
}
