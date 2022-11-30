package net.bombonato.salesforce.mc.business.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Asset {
    private String id = null;
    private String name = null;
    private String description = null;
    private String customerKey = null;
    private String objectID = null;
    private String contentType = null;
    private String enterpriseId = null;
    private String memberId = null;
    private String content = null;
    private Date createdDate = null;
    private Date modifiedDate = null;
    private String superContent = null;
    private AssetType assetType = null;
    private AssetCategory category = null;
}
