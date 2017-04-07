package top.zhacker.exporter.api.impl;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.PropertiesConfigurationFactory;
import org.springframework.boot.env.PropertySourcesLoader;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.PropertySources;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Constructor;

@Component
@Slf4j
public class PropertiesConfigurer {

    private final ResourceLoader resourceLoader;

    @Autowired
    public PropertiesConfigurer(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public  <T> T bindPropertiesToTarget(Class<T> clazz, String prefix, String... locations) {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            T newInstance = constructor.newInstance();

            PropertiesConfigurationFactory<Object> factory = new PropertiesConfigurationFactory<>(newInstance);
            factory.setPropertySources(loadPropertySources(locations));
            factory.setConversionService(new DefaultConversionService());
            if (!Strings.isNullOrEmpty(prefix)) {
                factory.setTargetName(prefix);
            }
            try {
                factory.bindPropertiesToTarget();
            } catch (Exception ex) {
                String targetClass = ClassUtils.getShortName(clazz);
                log.error("Could not bind properties to {}, cause={}",
                        targetClass, Throwables.getStackTraceAsString(ex));
            }
            return newInstance;

        } catch (Exception ex) {
            String targetClass = ClassUtils.getShortName(clazz);
            log.error("Could not bind properties to {}, cause={}",
                    targetClass, Throwables.getStackTraceAsString(ex));
            return null;
        }
    }

    private PropertySources loadPropertySources(String[] locations) {
        try {
            PropertySourcesLoader loader = new PropertySourcesLoader();
            for (String location : locations) {
                Resource resource = this.resourceLoader.getResource(location);
                loader.load(resource);
            }
            return loader.getPropertySources();
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}