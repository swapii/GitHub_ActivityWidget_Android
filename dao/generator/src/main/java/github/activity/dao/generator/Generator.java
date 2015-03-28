package github.activity.dao.generator;

import java.io.File;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by asavinova on 28/03/15.
 */
public class Generator {

	public static void main(String[] args) throws Exception {

		Schema schema = new Schema(1, "github.activity.dao");

		Entity dayActivity = schema.addEntity("DayActivity");
		dayActivity.addStringProperty("user");
		dayActivity.addDateProperty("date");
		dayActivity.addIntProperty("count");

		Entity widgetSettings = schema.addEntity("WidgetSettings");
		widgetSettings.addIdProperty();
		widgetSettings.addStringProperty("user");

		String outDir = "../../dao/src/main/java";

		new File(outDir).mkdirs();

		new DaoGenerator().generateAll(schema, outDir);
	}

}
