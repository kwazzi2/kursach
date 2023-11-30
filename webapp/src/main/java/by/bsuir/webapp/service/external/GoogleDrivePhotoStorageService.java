package by.bsuir.webapp.service.external;

import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.model.File;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GoogleDrivePhotoStorageService {

    private static final Log LOGGER = LogFactory.getLog(GoogleDriveService.class);

    @Autowired
    private GoogleDriveService drive;

    public String savePhotoAndGetDirectLink(String fileName, byte[] fileContent) {
        File uploadFileMetadata = new File().setName(fileName);
        AbstractInputStreamContent uploadFileContent = new ByteArrayContent("*/*", fileContent);
        try {
            File uploadedFile = drive.uploadFile(uploadFileMetadata, uploadFileContent);
            File sharedFile = drive.shareFileToAnyone(uploadedFile);
            return getWebContentLink(sharedFile.getId());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "";
    }

    private String getWebContentLink(String id) {
        return "https://drive.google.com/uc?id="+ id;
    }

    public void deletePhotoByDirectLink(String photoLink) {
        if(photoLink != null && !photoLink.isEmpty()){
            String deleteFileId = getFileIdFromPhotoLink(photoLink);
            drive.deleteFile(deleteFileId);
        }
    }

    private static String getFileIdFromPhotoLink(String photoLink) {
        String idWithOtherParam = photoLink.split("id=")[1];
        return idWithOtherParam.split("&")[0];
    }

}
