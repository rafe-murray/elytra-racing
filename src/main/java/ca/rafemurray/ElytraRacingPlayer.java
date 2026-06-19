package ca.rafemurray;

import ca.rafemurray.course.Course;

public interface ElytraRacingPlayer {
    boolean startCourse(Course course);
    void finishCourse();
    Course getCourse();
}
