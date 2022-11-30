package net.bombonato.salesforce.mc.business.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssetCategory {
    private String id = null;
    private String name = null;
    private String parentId = null;
}
