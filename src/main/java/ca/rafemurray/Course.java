package ca.rafemurray;

import net.minecraft.block.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents an instance of a racecourse
 */
public class Course {
    private StartBlockEntity start;
    private FinishBlockEntity finish;
    private final List<BlockState> checkPoints;

    public Course() {
        this.start = null;
        this.finish = null;
        this.checkPoints = new ArrayList<>();
    }

    public void setStart(StartBlockEntity startBlockEntity) {
        this.start = startBlockEntity;
    }

    public void setFinish(FinishBlockEntity blockEntity) {
        this.finish = blockEntity;
    }

    // TODO: check that these are of type Checkpoint
    public void addCheckpoint(BlockState checkpoint) {
        this.checkPoints.add(checkpoint);
    }

    public boolean isValid() {
        return (finish != null && start != null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, finish, checkPoints);
    }
}
