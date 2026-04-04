package ca.rafemurray;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static ca.rafemurray.ElytraRacing.MOD_ID;
import static ca.rafemurray.ModBlockEntities.START_BLOCK_ENTITY;

/**
 * This class represents an instance of a racecourse
 */
public class Course {
    private static Map<UUID, Course> COURSES = new HashMap<>();

    private UUID id;
    private StartBlockEntity start;
    private FinishBlockEntity finish;
    private final List<BlockState> checkPoints;

    public Course() {
        this.start = null;
        this.finish = null;
        this.checkPoints = new ArrayList<>();
        this.id = UUID.randomUUID();
        COURSES.put(this.id, this);
    }

    public Course getCourse(UUID id) {
        return COURSES.get(id);
    }

    public UUID getId() {
        return id;
    }

    public String getIdString() {
        return id.toString();
    }

    public StartBlockEntity getStart(){
        return start;
    }

    public FinishBlockEntity getFinish() {
        return finish;
    }

    public void setStart(@Nullable StartBlockEntity startBlockEntity) {
        this.start = startBlockEntity;
    }

    public void setFinish(@Nullable FinishBlockEntity blockEntity) {
        this.finish = blockEntity;
    }

    // TODO: check that these are of type Checkpoint
    public void addCheckpoint(BlockState checkpoint) {
        this.checkPoints.add(checkpoint);
    }

    public boolean isValid() {
        return (finish != null && start != null);
    }

//    public static final Codec<Course> CODEC = RecordCodecBuilder.create(instance -> instance.group(
//        Codec.STRING.fieldOf("id").forGetter(Course::getIdString),
//        Registries.BLOCK_ENTITY_TYPE.getCodec().fieldOf("start").forGetter(Course::getStart)
//    ().fieldOf("start").forGetter(Course::getStart)
//    ).apply(instance, Course::new));

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
