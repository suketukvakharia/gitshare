package anthropic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import org.junit.Test;

/**
 * Write a program that scans the file system to identify and report duplicate files,
 * handling real files, extensions, and file reading operations.
 */
public class DuplicateFileFinderOuter {

  public class DuplicateFileFinder {

    public List<List<String>> findDuplicateFiles(String root)
        throws IOException, NoSuchAlgorithmException {

      // first scan root getting list of files
      // group by or only get hash of files that have identical sizes.

      Queue<String> directoriesToSearch = new LinkedList<>();
      directoriesToSearch.add(root);
      // keep track of files by their size
      Map<Long, List<File>> sizeToFiles = new HashMap<>();

      List<List<File>> duplicateFiles = new ArrayList<>();

      while (!directoriesToSearch.isEmpty()) {
        String directoryToSearch = directoriesToSearch.poll();

        // file system API, looking up**
        File[] listFiles = new File(directoryToSearch).listFiles();
        for(File listFile:listFiles) {
          if (listFile.isDirectory()) {
            // worry about sim links later
            if (listFile.getName().startsWith(".")) {
              continue;
            }
            directoriesToSearch.add(listFile.getAbsolutePath());
            continue;
          }

          Long fileSize = listFile.length();
          sizeToFiles.putIfAbsent(fileSize, new ArrayList<>());
          sizeToFiles.get(fileSize).add(listFile);
        }
      }

      // after iteration go through each size to files and only get hash if needed
      for(Entry<Long, List<File>> sizeToFileEntry: sizeToFiles.entrySet()) {
        Long size = sizeToFileEntry.getKey();
        List<File> files = sizeToFileEntry.getValue();

        if (files.size() == 1) {
          continue;
        }

        // if not get checksum of each file and compare
        Map<String, List<File>> checksumToFiles = new HashMap<>();
        for (File file: files) {
          MessageDigest md = MessageDigest.getInstance("MD5");
          md.update(Files.readAllBytes(Paths.get(file.getAbsoluteFile().toURI())));
          byte[] digest = md.digest();
          String myChecksum = HexFormat.of().withUpperCase().formatHex(digest);
          checksumToFiles.putIfAbsent(myChecksum, new ArrayList<>());
          checksumToFiles.get(myChecksum).add(file);
        }

        // find any duplicates and add them
        for (List<File> matchingFiles: checksumToFiles.values()) {
          if (matchingFiles.size() == 1) {
            continue;
          }
          duplicateFiles.add(matchingFiles);
        }
      }


      return duplicateFiles.stream().map(d -> d.stream().map(i -> i.getAbsolutePath()).toList()).toList();
    }
  }

  @Test
  public void test1() throws Exception {
    DuplicateFileFinder finder = new DuplicateFileFinder();
    List<List<String>> duplicatePaths = finder.findDuplicateFiles("C:\\Users\\suket\\test2");

    System.err.println(duplicatePaths);
  }
}
