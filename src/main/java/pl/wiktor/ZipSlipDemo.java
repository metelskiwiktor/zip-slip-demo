package pl.wiktor;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipSlipDemo {
    private static final String UPLOAD_FOLDER = "output/";
    private static final String NORMAL_ZIP = "/zip_slip.zip";
    private static final String ATTACK_ZIP = "/zip_slip-attack.zip";

    public static void main(String[] args) {
        String zipFileName = ATTACK_ZIP;
        try (InputStream is = ZipSlipDemo.class.getResourceAsStream(zipFileName);
             ZipInputStream zis = new ZipInputStream(is)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File newFile = new File(UPLOAD_FOLDER + entry.getName());

                //zapobiegaj zip slip atak
//                preventZipSlipAttack(entry);

                // tworzenie katalogu
                Files.createDirectories(Paths.get(newFile.getParent()));

                // Zapisz plik
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    IOUtils.copy(zis, fos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void preventZipSlipAttack(ZipEntry entry) throws IOException {
        // Walidacja ścieżki
        String canonicalDestinationDir = new File(UPLOAD_FOLDER).getCanonicalPath();
        File newFile = new File(UPLOAD_FOLDER + entry.getName());
        String canonicalFilePath = newFile.getCanonicalPath();

        if (!canonicalFilePath.startsWith(canonicalDestinationDir + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + entry.getName());
        }
    }
}
