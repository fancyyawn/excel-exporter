package top.zhacker.exporter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import top.zhacker.exporter.api.impl.ExportTables;
import top.zhacker.exporter.api.impl.PropertiesConfigurer;

/**
 * DATE: 2017/4/7 <br>
 * MAIL: hechengopen@gmail.com <br>
 * AUTHOR: zhacker
 */
@ComponentScan("top.zhacker.exporter.api.impl")
@Configuration
public class ExporterConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ResourceLoader resourceLoader(){
        return new DefaultResourceLoader();
    }

    @Bean
    public ExportTables exportTables(PropertiesConfigurer propertiesConfigurer) {
        ExportTables tables =  propertiesConfigurer.bindPropertiesToTarget(ExportTables.class, "export", "classpath:export.yaml");
        return tables;
    }
}
