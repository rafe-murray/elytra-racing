package ca.rafemurray;

public interface ElytraRacingPlayerEntity {
    boolean startCourse(Course course);
    void finishCourse();
    Course getCourse();
}
