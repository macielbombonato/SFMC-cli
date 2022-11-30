package net.bombonato.salesforce.mc.business.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Folder {
    private String id = null;
    private String key = null;
    private String name = null;
    private String description = null;
    private Date createdDate = null;
    private Date modifiedDate = null;
    private String contentType = null;
    private Folder parentFolder = null;
    private String parentFolderKey = null;
    private Boolean isActive = null;
    private Boolean isEditable = null;
    private Boolean allowChildren = null;
}
