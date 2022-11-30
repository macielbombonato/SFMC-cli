package net.bombonato.salesforce.mc.business.service.impl;

import com.exacttarget.fuelsdk.*;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.bombonato.salesforce.mc.business.helper.AssetHelper;
import net.bombonato.salesforce.mc.business.model.Asset;
import net.bombonato.salesforce.mc.business.model.DataExtension;
import net.bombonato.salesforce.mc.business.model.Folder;
import net.bombonato.salesforce.mc.business.service.AssetService;
import net.bombonato.salesforce.mc.business.service.FolderService;
import net.bombonato.salesforce.mc.business.service.LocalProjectService;
import net.bombonato.salesforce.mc.config.BatchConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
@Slf4j
public class AssetServiceImpl implements AssetService {

    @Autowired
    private ETClient etClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FolderService folderService;

    @Autowired
    private LocalProjectService localProjectService;

    @Autowired
    private AssetHelper assetHelper;

    @Override
    public void downloadAll() {
        try {
            ETFilter etf = new ETFilter();
            ETResponse<ETAsset> response = etClient.retrieve(ETAsset.class, etf);

            Asset result = null;
            String folderName = "";
            Folder folder = new Folder();
            folder.setContentType("asset");
            folder.setName("allAssets");

            for (ETAsset row : response.getObjects()) {
                result = null;
                folderName = "";

                result = assetHelper.toLocal(row);

                if (result != null && result.getCategory() != null) {
                    folder = folderService.getByName(result.getCategory().getName());
                    folderName = localProjectService.createFolder(folder);
                }

                objectMapper.writeValue(new File(folderName + File.separator + result.getName() + ".json"), result );

                if (row.getContent() != null) {
                    FileWriter writer = new FileWriter(
                            new File(
                                    folderName + File.separator + result.getName()
                            ),
                            false
                    );
                    writer.write(row.getContent());
                    writer.close();
                }
            }
        } catch (ETSdkException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (StreamWriteException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
