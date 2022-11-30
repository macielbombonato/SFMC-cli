package net.bombonato.salesforce.mc.business.helper;

import com.exacttarget.fuelsdk.ETDataExtensionColumn;
import net.bombonato.salesforce.mc.business.model.DataExtensionColumn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataExtensionColumnHelper {

    public DataExtensionColumn toLocal(ETDataExtensionColumn remote) {
        DataExtensionColumn local = new DataExtensionColumn();

        if (remote != null) {
            local.setId(remote.getId());
            local.setKey(remote.getKey());
            local.setDescription(remote.getDescription());
            local.setName(remote.getName());
            local.setCreatedDate(remote.getCreatedDate());
            local.setModifiedDate(remote.getModifiedDate());
//            local.setType(DataExtensionColumn.Type.valueOf(remote.getType().value()));
            local.setDefaultValue(remote.getDefaultValue());
            local.setIsPrimaryKey(remote.getIsPrimaryKey());
            local.setIsRequired(remote.getIsRequired());
            local.setLength(remote.getLength());
            local.setPrecision(remote.getPrecision());
            local.setScale(remote.getScale());
        }

        return local;
    }

    public List<DataExtensionColumn> manyToLocal(List<ETDataExtensionColumn> remote) {
        List<DataExtensionColumn> result = new ArrayList<DataExtensionColumn>();

        if (remote != null) {
            for (ETDataExtensionColumn row : remote) {
                result.add(toLocal(row));
            }
        }

        return result;
    }

}
