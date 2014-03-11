package nju.software.util;

import java.io.File;

import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.support.GenericXmlContextLoader;

public class FileSystemXmlContextLoader extends GenericXmlContextLoader {
	@Override
	protected String[] modifyLocations(Class<?> clazz, String... locations) {
		String[] modifiedLocations = new String[locations.length];
		for (int i = 0; i < locations.length; i++) {
			String path = locations[i];
			if (path != null && path.startsWith("/")) {
				path = path.substring(1);
			}
			modifiedLocations[i] = new File(path).getAbsolutePath();
		}
		return modifiedLocations;
	}

	@Override
	protected BeanDefinitionReader createBeanDefinitionReader(
			GenericApplicationContext context) {
		return new FileSystemXmlBeanDefinitionReader(context);
	}
}
