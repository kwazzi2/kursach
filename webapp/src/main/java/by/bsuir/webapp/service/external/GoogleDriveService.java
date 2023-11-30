package by.bsuir.webapp.service.external;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Objects;

@Service
public class GoogleDriveService {

    private static final Log LOGGER = LogFactory.getLog(GoogleDriveService.class);
    private String SECRET_FILENAME = "/googleSecret.json";
    private Drive drive;

    private static HttpTransport getTransport() throws GeneralSecurityException, IOException {
        try {
            return GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException e) {
            LOGGER.error(e.getMessage(), e);
            throw new GeneralSecurityException("Unable to create new trusted http transport", e.getCause());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException("Unable to create new trusted http transport", e.getCause());
        }
    }

    private static JsonFactory getJsonFactory() {
        return JacksonFactory.getDefaultInstance();
    }

    public InputStream downloadPhoto(String fileId) throws IOException {

        return drive.files().get(fileId).executeMediaAsInputStream();

    }

    /**
     * Метод для загрузки файлов на GoogleDrive.
     *
     * @param fileMetadata Метаданные файла,например: имя, MIME тип и т.д.
     * @param fileContent  Содержимое файла
     * @return Метаданные файла после загрузки
     */
    public File uploadFile(File fileMetadata, AbstractInputStreamContent fileContent) throws IOException {
        try {
            if (fileContent != null) {
                return drive.files().create(fileMetadata, fileContent).execute();
            } else {
                return drive.files().create(fileMetadata).execute();
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IOException("Unable upload file on GoogleDrive", e);
        }
    }

    /**
     * Метод для удаления файла на GoogleDrive.
     *
     * @param deletedFileId Идентификатор удаляемого файла
     */
    public void deleteFile(String deletedFileId) {
        try {
            drive.files().delete(deletedFileId).execute();
        } catch (IOException e) {
            LOGGER.debug(e.getMessage(), e);
        }
    }


    /**
     * Метод для расширения доступа к файлу.
     *
     * @param sharedFile Метаданные файла к которому необходимо предоставить доступ
     * @return Новые метаданные файла после расширения доступа
     */
    public File shareFileToAnyone(File sharedFile) {
        try {
            Permission sharingPermission = new Permission();
            sharingPermission.setRole("reader").setType("anyone");
            drive.permissions().create(sharedFile.getId(), sharingPermission).execute();
            return drive.files().get(sharedFile.getId()).execute();
        } catch (IOException e) {
            LOGGER.debug(e.getMessage(), e);
            return null;
        }
    }

    @PostConstruct
    private void initDrive() {
        try {
            drive = new Drive.Builder(getTransport(), getJsonFactory(), authorize())
                    .setApplicationName("webapp")
                    .build();
        } catch (GeneralSecurityException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private GoogleCredential authorize() throws IOException, GeneralSecurityException {
        InputStream stream = this.getClass().getResourceAsStream(SECRET_FILENAME);
        if(stream == null)
            throw new FileNotFoundException("File " + SECRET_FILENAME + ", that contain google credentials, should be in resources folder");
        return GoogleCredential.fromStream(
                        Objects.requireNonNull(stream),
                        getTransport(),
                        getJsonFactory())
                .createScoped(Collections.singleton(DriveScopes.DRIVE_FILE));
    }
}
