package net.bombonato.salesforce.mc.business.helper;

import com.exacttarget.fuelsdk.ETAsset;
import com.google.gson.internal.LinkedTreeMap;
import net.bombonato.salesforce.mc.business.model.Asset;
import net.bombonato.salesforce.mc.business.model.AssetCategory;
import net.bombonato.salesforce.mc.business.model.AssetType;
import org.springframework.stereotype.Component;

@Component
public class AssetHelper {

    public Asset toLocal(ETAsset remote) {
        Asset local = new Asset();

        if (remote != null) {
            local.setId(remote.getId());
            local.setDescription(remote.getDescription());
            local.setName(remote.getName());
            local.setCreatedDate(remote.getCreatedDate());
            local.setModifiedDate(remote.getModifiedDate());
            local.setCustomerKey(remote.getCustomerKey());
            local.setEnterpriseId(remote.getEnterpriseId());
            local.setContentType(remote.getContentType());
//            local.setContent(remote.getContent());
            local.setMemberId(remote.getMemberId());
            local.setObjectID(remote.getObjectID());
            local.setSuperContent(remote.getSuperContent());

            if (remote.getAssetType() != null) {
                local.setAssetType(new AssetType());
                LinkedTreeMap remoteAssetType = ((LinkedTreeMap) remote.getAssetType());

                local.getAssetType().setId(remoteAssetType.get(remoteAssetType.keySet().toArray()[0]).toString());
                local.getAssetType().setName(remoteAssetType.get(remoteAssetType.keySet().toArray()[1]).toString());
                local.getAssetType().setDisplayName(remoteAssetType.get(remoteAssetType.keySet().toArray()[2]).toString());
            }

            if (remote.getCategory() != null) {
                local.setCategory(new AssetCategory());

                LinkedTreeMap remoteCategory = ((LinkedTreeMap) remote.getCategory());
                local.getCategory().setId(remoteCategory.get(remoteCategory.keySet().toArray()[0]).toString());
                local.getCategory().setName(remoteCategory.get(remoteCategory.keySet().toArray()[1]).toString());
                local.getCategory().setParentId(remoteCategory.get(remoteCategory.keySet().toArray()[2]).toString());
            }
        }

        return local;
    }
}
