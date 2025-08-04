package ca.rafemurray.mixin;

import ca.rafemurray.Course;
import ca.rafemurray.ElytraRacingPlayerEntity;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements ElytraRacingPlayerEntity {
    @Inject(at = @At("TAIL"), method = "<init>")
    private void initializeCourseRecords(World world, BlockPos pos, float yaw, GameProfile gameProfile, CallbackInfo info) {
        this.courseRecords = new HashMap<>();
    }

	@Inject(at = @At("TAIL"), method = "readCustomDataFromNbt")
	private void readCourseData(NbtCompound nbt, CallbackInfo info) {
        // Read our data from stored NBT
        // TODO: Implement this method
	}

    @Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    private void writeCourseData(NbtCompound nbt, CallbackInfo info) {
        // Write all the data we need per player into NBT when the game closes
        // TODO: Implement this method
    }

    // Entries only present for completed courses
    @Unique
    private Map<Course, Duration> courseRecords;

    // Null if no current course
    // Using Instant instead of in game time because we want accurate real-world time, not game ticks
    @Unique
    private Instant startTime = null;

    // Null if no current course
    @Unique
    private Course course = null;

    // Returns false on failure; true on success
    public boolean startCourse(Course course) {
        if (!course.isValid()) { return false; }
        this.startTime = Instant.now();
        this.course = course;
        return true;
    }

    public void finishCourse() {
        Duration time = Duration.between(startTime, Instant.now());
        this.startTime = null;
        courseRecords.putIfAbsent(course, time);

        // Time is faster than record
        if (time.compareTo(courseRecords.get(course)) < 0) {
            courseRecords.put(course, time);
        }
        this.course = null;
    }

    public Course getCourse() {return course;}
    // TODO: Add checks for death/ground collisions and if the happen teleport the player to the start when the course is active
    // TODO: Add statistics/achievements? for course records
    // TODO: add start/finish, course marker textures
    // TODO: render start/finish, checkpoint blocks differently when holding the corresponding course marker or when part of the current active course
}

