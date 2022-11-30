package net.bombonato.salesforce.mc.business.service;

import net.bombonato.salesforce.mc.business.model.DataExtension;
import org.springframework.batch.core.Step;

public interface DataExtensionService {

    void downloadAll();
    DataExtension getByName(String name);

}
