package nju.software.util;

import java.util.Set;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

public class FileSystemXmlBeanDefinitionReader extends XmlBeanDefinitionReader {

	public FileSystemXmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
		super(registry);
	}

	@Override
	public int loadBeanDefinitions(String location, Set actualResources)
			throws BeanDefinitionStoreException {
		ResourceLoader resourceLoader = getResourceLoader();
		if (resourceLoader == null) {
			throw new BeanDefinitionStoreException(
					"Cannot import bean definitions from location [" + location
							+ "]: no ResourceLoader available");
		}

		Resource resource = new FileSystemResource(location);
		int loadCount = loadBeanDefinitions(resource);
		if (actualResources != null) {
			Assert.isNull(actualResources);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Loaded " + loadCount
					+ " bean definitions from location pattern [" + location
					+ "]");
		}
		return loadCount;

	}
}