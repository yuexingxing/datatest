package com.zd.common;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by zhangxiusheng on 17/3/21.
 */
public class FileNameSelector implements FilenameFilter {
    String extension = ".";
    public FileNameSelector(String fileExtensionNoDot) {
        extension += fileExtensionNoDot;
    }

    public boolean accept(File dir, String name) {
        return name.endsWith(extension);
    }
}
