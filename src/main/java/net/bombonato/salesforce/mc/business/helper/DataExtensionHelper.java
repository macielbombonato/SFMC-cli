package net.bombonato.salesforce.mc.business.helper;

import com.exacttarget.fuelsdk.ETDataExtension;
import net.bombonato.salesforce.mc.business.model.DataExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataExtensionHelper {

    @Autowired
    private DataExtensionColumnHelper dataExtensionColumnHelper;

    public DataExtension toLocal(ETDataExtension remote) {
        DataExtension local = new DataExtension();

        if (remote != null) {
            local.setId(remote.getId());
            local.setDescription(remote.getDescription());
            local.setKey(remote.getKey());
            local.setName(remote.getName());
            local.setFolderId(remote.getFolderId());
            local.setCreatedDate(remote.getCreatedDate());
            local.setIsSendable(remote.getIsSendable());
            local.setIsTestable(remote.getIsTestable());
            local.setModifiedDate(remote.getModifiedDate());

            if (remote.getIsSendable() || remote.getIsTestable()) {
                local.setSendableDataExtensionField(dataExtensionColumnHelper.toLocal(remote.getSendableDataExtensionField()));
            }

            local.setColumns(dataExtensionColumnHelper.manyToLocal(remote.getColumns()));
        }

        return local;
    }
}
