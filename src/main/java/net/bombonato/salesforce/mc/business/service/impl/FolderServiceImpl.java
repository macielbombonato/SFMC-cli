package net.bombonato.salesforce.mc.business.service.impl;

import com.exacttarget.fuelsdk.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import net.bombonato.salesforce.mc.business.helper.FolderHelper;
import net.bombonato.salesforce.mc.business.model.Folder;
import net.bombonato.salesforce.mc.business.service.AssetService;
import net.bombonato.salesforce.mc.business.service.FolderService;
import net.bombonato.salesforce.mc.business.service.LocalProjectService;
import net.bombonato.salesforce.mc.config.BatchConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class FolderServiceImpl implements FolderService {

    @Autowired
    private ETClient etClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FolderHelper folderHelper;

    @Autowired
    private LocalProjectService localProjectService;

    @Override
    public void downloadAll() {
        try {
            ETFilter etf = new ETFilter();
            ETResponse<ETFolder> response = etClient.retrieve(ETFolder.class, etf);

            String json = "";

            Folder result = null;
            String folderName = "";
            for (ETFolder row : response.getObjects()) {
                result = null;
                folderName = "";
                result = folderHelper.toLocal(row);

                if (row.getParentFolderKey() != null && !row.getParentFolderKey().endsWith("_default")) {
                    result.setParentFolder(getByKey(row.getParentFolderKey()));
                }

                folderName = localProjectService.createFolder(result);
                objectMapper.writeValue(new File(folderName + File.separator + result.getName() + ".json"), result );
            }
        } catch (ETSdkException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Folder getById(String id) {
        Folder result = null;

        try {
            ETFilter etf = new ETFilter();
            ETExpression exp = new ETExpression();

            exp.setProperty("id");
            exp.setOperator(ETExpression.Operator.EQUALS);
            exp.addValue(id);
            etf.setExpression(exp);

            ETResponse<ETFolder> response = etClient.retrieve(ETFolder.class, etf);
            for (ETFolder row : response.getObjects()) {
                result = folderHelper.toLocal(row);

                if (row.getParentFolderKey() != null && !row.getParentFolderKey().endsWith("_default")) {
                    result.setParentFolder(getByKey(row.getParentFolderKey()));
                }
            }
        } catch (ETSdkException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public Folder getByKey(String key) {
        Folder result = null;

        try {
            ETFilter etf = new ETFilter();
            ETExpression exp = new ETExpression();

            exp.setProperty("key");
            exp.setOperator(ETExpression.Operator.EQUALS);
            exp.addValue(key);
            etf.setExpression(exp);

            ETResponse<ETFolder> response = etClient.retrieve(ETFolder.class, etf);
            for (ETFolder row : response.getObjects()) {
                result = folderHelper.toLocal(row);

                if (row.getParentFolderKey() != null && !row.getParentFolderKey().endsWith("_default")) {
                    result.setParentFolder(getByKey(row.getParentFolderKey()));
                }
            }
        } catch (ETSdkException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public Folder getByName(String name) {
        Folder result = null;

        try {
            ETFilter etf = new ETFilter();
            ETExpression exp = new ETExpression();

            exp.setProperty("name");
            exp.setOperator(ETExpression.Operator.EQUALS);
            exp.addValue(name);
            etf.setExpression(exp);

            ETResponse<ETFolder> response = etClient.retrieve(ETFolder.class, etf);
            for (ETFolder row : response.getObjects()) {
                result = folderHelper.toLocal(row);

                if (row.getParentFolderKey() != null && !row.getParentFolderKey().endsWith("_default")) {
                    result.setParentFolder(getByKey(row.getParentFolderKey()));
                }
            }
        } catch (ETSdkException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return result;
    }
}
