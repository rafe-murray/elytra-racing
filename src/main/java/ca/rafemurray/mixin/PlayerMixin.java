package ca.rafemurray.mixin;

import ca.rafemurray.course.Course;
import ca.rafemurray.course.CourseProgress;
import ca.rafemurray.course.CourseRecords;
import ca.rafemurray.ElytraRacing;
import ca.rafemurray.ElytraRacingPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.Duration;
import java.util.Optional;

@Mixin(Player.class)
public abstract class PlayerMixin implements ElytraRacingPlayer {

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void init(Level level, GameProfile gameProfile, CallbackInfo ci) {
        courseRecords = new CourseRecords();
    }

    // TODO: display the timer and ensure it is actually running for the current course
    @Inject(at = @At("TAIL"), method = "readAdditionalSaveData")
    private void readCourseData(ValueInput input, CallbackInfo ci) {
        Optional<CourseRecords> optionalCourseRecords = input.read(CourseRecords.IDENTIFIER.toString(), CourseRecords.CODEC);
        courseRecords = optionalCourseRecords.orElseGet(CourseRecords::new);
        Optional<Course> optionalCourse = input.read(Course.IDENTIFIER.toString(), Course.CODEC);
        course = optionalCourse.orElse(null);
        Optional<CourseProgress> optionalCourseProgress = input.read(CourseProgress.IDENTIFIER.toString(), CourseProgress.CODEC);
        courseProgress = optionalCourseProgress.orElse(null);
        ElytraRacing.LOGGER.info("Loaded additional player data.");
        validateState();
    }

    @Inject(at = @At("TAIL"), method = "addAdditionalSaveData")
    private void addCourseData(ValueOutput output, CallbackInfo info) {
        validateState();
//        output.storeNullable(CourseProgress.IDENTIFIER.toString(), CourseProgress.CODEC, courseProgress);
//        DataResult<JsonElement> result = CourseRecords.CODEC.encodeStart(JsonOps.INSTANCE, courseRecords);
//        JsonElement json = result.resultOrPartial(ElytraRacing.LOGGER::error).orElseThrow();
//        output.storeNullable(CourseRecords.IDENTIFIER.toString(), CourseRecords.CODEC, courseRecords);
//        output.storeNullable(Course.IDENTIFIER.toString(), Course.CODEC, course);
    }

    @Unique
    private CourseRecords courseRecords;

    // Null if no current course
    @Unique
    private CourseProgress courseProgress = null;

    // Null if no current course
    @Unique
    private Course course = null;

    // Returns false on failure; true on success
    public boolean startCourse(Course course) {
        validateState();
        if (!course.isValid()) {
            return false;
        }
        ElytraRacing.LOGGER.info("Starting course {}", course.id);
        courseProgress = new CourseProgress();
        this.course = course;
        return true;
    }

    public void finishCourse() {
        validateState();
        if (courseProgress == null || course == null) {
            throw new IllegalStateException("Course not started");
        }
        Duration time = courseProgress.getElapsedTime();
        this.courseProgress = null;
        courseRecords.putIfFaster(course.id, time);
        ElytraRacing.LOGGER.info("Finished course {} with time {}", course.id, time);

        this.course = null;
    }

    public Course getCourse() {
        return course;
    }

    private void validateState() {
        if (this.courseRecords == null) {
            throw new IllegalStateException("Course records were null");
        }
    }
    // TODO: Add checks for death/ground collisions and if they happen teleport the player to the start when the course is active
    // TODO: Add statistics/achievements? for course records
    // TODO: add start/finish, course marker textures
    // TODO: render start/finish, checkpoint blocks differently when holding the corresponding course marker or when part of the current active course
}

