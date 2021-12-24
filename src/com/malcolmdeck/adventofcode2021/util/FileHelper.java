package com.malcolmdeck.adventofcode2021.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

public class FileHelper {

    public static File getFile(String fileName) throws FileNotFoundException {
        // Desktop
//        return new File("C:\\Users\\malco\\IdeaProjects\\AdventOfCode2021\\src\\com\\malcolmdeck\\adventofcode2021\\levels\\" + fileName);
        // Laptop
        return new File("C:\\Users\\Malcolm_razer\\IdeaProjects\\AdventOfCode2021\\src\\com\\malcolmdeck\\adventofcode2021\\levels\\" + fileName);
    }

}
