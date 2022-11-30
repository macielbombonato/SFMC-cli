package net.bombonato.salesforce.mc.business.service.impl;

import com.exacttarget.fuelsdk.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.bombonato.salesforce.mc.business.helper.DataExtensionColumnHelper;
import net.bombonato.salesforce.mc.business.helper.DataExtensionHelper;
import net.bombonato.salesforce.mc.business.model.DataExtension;
import net.bombonato.salesforce.mc.business.service.DataExtensionService;
import net.bombonato.salesforce.mc.business.service.FolderService;
import net.bombonato.salesforce.mc.business.service.LocalProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
@Slf4j
public class DataExtensionServiceImpl implements DataExtensionService {

    @Autowired
    private ETClient etClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FolderService folderService;

    @Autowired
    private DataExtensionHelper dataExtensionHelper;

    @Autowired
    private DataExtensionColumnHelper dataExtensionColumnHelper;

    @Autowired
    private LocalProjectService localProjectService;

    @Override
    public void downloadAll() {
        try {
            ETFilter etf = new ETFilter();
            ETResponse<ETDataExtension> response = etClient.retrieve(ETDataExtension.class, etf);

            DataExtension result = null;
            String folderName = "";
            for (ETDataExtension row : response.getObjects()) {
                result = null;
                folderName = "";

                result = dataExtensionHelper.toLocal(row);
                result.setColumns(dataExtensionColumnHelper.manyToLocal(row.retrieveColumns()));

                if (result.getFolderId() != null) {
                    result.setFolder(folderService.getById(result.getFolderId().toString()));
                }

                folderName = localProjectService.createFolder(result.getFolder());
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
    public DataExtension getByName(String name) {
        DataExtension result = null;

        try {
            ETFilter etf = new ETFilter();
            ETExpression exp = new ETExpression();

            exp.setProperty("name");
            exp.setOperator(ETExpression.Operator.EQUALS);
            exp.addValue(name);
            etf.setExpression(exp);

            ETResponse<ETDataExtension> response = etClient.retrieve(ETDataExtension.class, etf);
            String folderName = "";
            for (ETDataExtension row : response.getObjects()) {
                folderName = "";

                result = dataExtensionHelper.toLocal(row);

                result = dataExtensionHelper.toLocal(row);
                result.setColumns(dataExtensionColumnHelper.manyToLocal(row.retrieveColumns()));

                if (result.getFolderId() != null) {
                    result.setFolder(folderService.getById(result.getFolderId().toString()));
                }

                folderName = localProjectService.createFolder(result.getFolder());
                objectMapper.writeValue(new File(folderName + File.separator + result.getFolder().getName() + ".json"), result.getFolder() );
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

        return result;
    }

}
