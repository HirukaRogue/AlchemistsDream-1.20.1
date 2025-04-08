package net.hirukarogue.alchemistsdream.miscellaneous;

public class IDTrimmer {
    public static String trim(String id) {
        int lastIndex = id.lastIndexOf(":");
        if (lastIndex == -1) {
            return id;  // Character not found, return the original string
        }
        return id.substring(lastIndex + 1);
    }
}
