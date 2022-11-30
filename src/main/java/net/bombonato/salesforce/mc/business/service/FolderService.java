package net.bombonato.salesforce.mc.business.service;

import net.bombonato.salesforce.mc.business.model.Folder;

public interface FolderService {

    void downloadAll();
    Folder getById(String id);
    Folder getByKey(String key);
    Folder getByName(String name);

}
