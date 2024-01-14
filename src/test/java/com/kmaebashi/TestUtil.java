package com.kmaebashi;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtil {
    private TestUtil() {}

    public static boolean compareFiles(String path1, String path2) throws Exception {
        byte[] bytes1 = Files.readAllBytes(Paths.get(path1));
        String str1 = new String(bytes1, StandardCharsets.UTF_8);
        byte[] bytes2 = Files.readAllBytes(Paths.get(path2));
        String str2 = new String(bytes2, StandardCharsets.UTF_8);

        return str1.equals(str2);
    }
}
