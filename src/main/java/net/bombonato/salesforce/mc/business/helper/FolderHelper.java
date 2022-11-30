package net.bombonato.salesforce.mc.business.helper;

import com.exacttarget.fuelsdk.ETFolder;
import net.bombonato.salesforce.mc.business.model.Folder;
import org.springframework.stereotype.Component;

@Component
public class FolderHelper {

    public Folder toLocal(ETFolder remote) {
        Folder local = new Folder();

        if (remote != null) {
            local.setId(remote.getId());
            local.setDescription(remote.getDescription());
            local.setKey(remote.getKey());
            local.setName(remote.getName());
            local.setCreatedDate(remote.getCreatedDate());
            local.setModifiedDate(remote.getModifiedDate());
            local.setAllowChildren(remote.getAllowChildren());
            local.setContentType(remote.getContentType());
            local.setIsActive(remote.getIsActive());
            local.setIsEditable(remote.getIsEditable());

            local.setParentFolderKey(remote.getParentFolderKey());
        }

        return local;
    }
}
