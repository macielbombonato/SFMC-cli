package net.bombonato.salesforce.mc;

import lombok.extern.slf4j.Slf4j;
import net.bombonato.salesforce.mc.business.service.AssetService;
import net.bombonato.salesforce.mc.business.service.DataExtensionService;
import net.bombonato.salesforce.mc.business.service.FolderService;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
@Slf4j
public class Main implements CommandLineRunner {

	@Autowired
	private AssetService assetService;

	@Autowired
	private FolderService folderService;

	@Autowired
	private DataExtensionService dataExtensionService;

	public static void main(String[] args) throws Exception {
		for(String arg:args) {
			log.info(arg);
		}
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		folderService.downloadAll();
		assetService.downloadAll();
		dataExtensionService.downloadAll();

//		folderService.getByName("test2");
//		dataExtensionService.getByName("otherDE");
	}

}
