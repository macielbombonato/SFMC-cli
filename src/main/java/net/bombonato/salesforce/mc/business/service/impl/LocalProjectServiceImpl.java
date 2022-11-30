package net.bombonato.salesforce.mc.business.service.impl;

import com.exacttarget.fuelsdk.ETClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.bombonato.salesforce.mc.business.model.Folder;
import net.bombonato.salesforce.mc.business.service.LocalProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class LocalProjectServiceImpl implements LocalProjectService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ETClient etClient;

    public String createFolder(Folder folder) {
        String folderStructure = "";

        if (folder != null) {
            // To prevent errors on folder structure creation
            if (folder.getName() != null) {
                folder.setName(folder.getName().replaceAll("/", ""));
            }
            if (folder.getKey() != null) {
                folder.setKey(folder.getKey().replaceAll("/", ""));
            }

            folderStructure += etClient.getConfiguration().get("accountId") + File.separator + folder.getContentType();
        } else {
            folderStructure += etClient.getConfiguration().get("accountId");
            folder = new Folder();
            folder.setContentType("not_defined");
            folder.setName("not_defined");
        }

        folderStructure = getFolderStructure(folder, folderStructure);
        return folderStructure;
    }

    private String getFolderStructure(Folder folder, String folderStructure) {
        if (folder.getParentFolder() != null) {
            folderStructure = getFolderStructure(folder.getParentFolder(), folderStructure);
        } else if (folder.getParentFolderKey() != null) {
            folderStructure += File.separator + folder.getParentFolderKey();
        }

        folderStructure += File.separator + folder.getName();

        try {
            File dir = new File(folderStructure);
            dir.mkdirs();
            File jsonFile = new File(dir.getParentFile() + File.separator + folder.getName() + ".json");
            objectMapper.writeValue(jsonFile, folder);
            log.info("File and path created: " + jsonFile.getAbsolutePath());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return folderStructure;
    }
}
