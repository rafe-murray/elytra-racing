package ca.rafemurray.mixin;

import ca.rafemurray.Course;
import ca.rafemurray.ElytraRacingPlayerEntity;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
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
import java.util.Optional;
import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements ElytraRacingPlayerEntity {
    @Inject(at = @At("TAIL"), method = "<init>")
    private void initializeCourseRecords(World world, BlockPos pos, float yaw,
                                         GameProfile gameProfile, CallbackInfo info) {
        this.courseRecords = new HashMap<>();
    }

    // TODO: display the timer and ensure it is actually running for the current course
    @Inject(at = @At("TAIL"), method = "readCustomDataFromNbt")
    private void readCourseData(NbtCompound nbt, CallbackInfo info) {
        // Read our data from stored NBT
//        NbtList records = nbt.getListOrEmpty("courseRecords");
//        for (NbtElement recordElement : records) {
//            NbtCompound record = (NbtCompound) recordElement;
//            Optional<String> optionalUuid = record.getString("uuid");
//            Optional<Long> optionalDuration = record.getLong("duration");
//            if (optionalUuid.isPresent() && optionalDuration.isPresent()) {
//                UUID id = UUID.fromString(optionalUuid.get());
//                Duration d = Duration.ofMillis(optionalDuration.get());
//                this.courseRecords.put(id, d);
//            }
//        }
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    private void writeCourseData(NbtCompound nbt, CallbackInfo info) {
        // Write all the data we need per player into NBT when the game closes
        // TODO: use PersistentState to save all the course info to disk
//        PersistentState
//        NbtList records = new NbtList();
//        for (Map.Entry<UUID, Duration> courseRecord : courseRecords.entrySet()) {
//            NbtCompound record = new NbtCompound();
//            record.putString("uuid", courseRecord.getKey().toString());
//            record.putLong("duration", courseRecord.getValue().toMillis());
//            records.add(record);
//        }
//        nbt.put("courseRecords", records);
//        if (startTime != null) {
//            NbtCompound startTimeCompound = new NbtCompound();
//            startTimeCompound.putLong("seconds", startTime.getEpochSecond());
//            startTimeCompound.putLong("nano", startTime.getNano());
//            nbt.put("startTime", startTimeCompound);
//        }
//        if (course != null) {
//        }
    }

    // Entries only present for completed courses
    @Unique
    private Map<UUID, Duration> courseRecords;

    // Null if no current course
    // Using Instant instead of in game time because we want accurate real-world time, not game ticks
    @Unique
    private Instant startTime = null;

    // Null if no current course
    @Unique
    private Course course = null;

    // Returns false on failure; true on success
    public boolean startCourse(Course course) {
        if (!course.isValid()) {
            return false;
        }
        this.startTime = Instant.now();
        this.course = course;
        return true;
    }

    public void finishCourse() {
        Duration time = Duration.between(startTime, Instant.now());
        this.startTime = null;
        courseRecords.putIfAbsent(course.getId(), time);

        // Time is faster than record
        if (time.compareTo(courseRecords.get(course.getId())) < 0) {
            courseRecords.put(course.getId(), time);
        }
        this.course = null;
    }

    public Course getCourse() {
        return course;
    }
    // TODO: Add checks for death/ground collisions and if they happen teleport the player to the start when the course is active
    // TODO: Add statistics/achievements? for course records
    // TODO: add start/finish, course marker textures
    // TODO: render start/finish, checkpoint blocks differently when holding the corresponding course marker or when part of the current active course
}

