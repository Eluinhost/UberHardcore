package gg.uhc.uberhardcore.api;

public interface EntityClassReplacer {
    /**
     * Replaces the current class with the replacement
     *
     * Runs for EntityTypes and spawn lists
     *
     * @param current the class to replace
     * @param replacement the class to replace with
     */
    void replaceClasses(Class current, Class replacement);

    /**
     * Run nms initialization e.t.c. Any exception thrown assumes failure and plugin will fail to load
     */
    void initialize() throws Exception;
}
