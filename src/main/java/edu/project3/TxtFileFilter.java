package edu.project3;

import java.io.File;

public interface TxtFileFilter extends java.io.FileFilter {
    @Override
    default boolean accept(File pathname) {
        return pathname.toPath().toString().endsWith(".txt");
    }
}
