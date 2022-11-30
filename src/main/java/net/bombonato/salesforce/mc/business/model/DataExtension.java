package net.bombonato.salesforce.mc.business.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DataExtension {
    static final int DEFAULT_PAGE_SIZE = 2500;

    private String id = null;
    private String key = null;
    private String name = null;
    private String description = null;
    private Date createdDate = null;
    private Date modifiedDate = null;
    private Integer folderId = null;
    private Folder folder = null;
    private Boolean isSendable = null;
    private Boolean isTestable = null;
    private DataExtensionColumn sendableDataExtensionField = null;
    private List<DataExtensionColumn> columns = new ArrayList<DataExtensionColumn>();

//    private Attribute sendableSubscriberField;
//
//

}
