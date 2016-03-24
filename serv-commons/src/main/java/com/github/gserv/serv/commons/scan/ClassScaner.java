package com.github.gserv.serv.commons.scan;

import java.io.IOException;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

/**
 * 类扫描器
 *
 * Created by shiying on 2016/3/24.
 */
public class ClassScaner {
	private static final Logger logger = LoggerFactory.getLogger(ClassScaner.class);

	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	private MetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory();

    /**
     * 扫描回掉方法
     */
    public interface ClassScanerAction<T> {
        void action(Class<T> cls);
    }
    
    /**
     * 
     * 扫描包下的类
     * @param basePackage	基础包
     * @param action	回掉方法
     * @param instantiable 可实例化选项，true：只包含可实例化的，false：只包含不可实例化的，null：全部
     * @param parentClass 父类型，传入该值，只扫描该类的子类
     * @throws IOException
     */
    public <T> void scan(
    		String basePackage, 
    		ClassScanerAction<T> action, 
    		Boolean instantiable, 
    		Class<T> parentClass
    		) throws IOException {
		String packageSearchPath = "classpath*:" + basePackage.replaceAll("\\.", "/") + "/**/*.class";
		Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
		for (Resource resource : resources) {
			if (resource.isReadable()) {
				try {
					MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
					Class target = this.getClass().getClassLoader().loadClass(metadataReader.getClassMetadata().getClassName());
					// 可实例化属性不一致
					if (instantiable != null && instantiable != (!target.isInterface() && !Modifier.isAbstract(target.getModifiers()))) {
						logger.debug("[{}] 可实例化属性不同，跳过", target);
						continue;
					}
					// 继承关系不一致
					if (parentClass != null && !parentClass.isAssignableFrom(target)) {
						logger.debug("[{}] 继承关系不一致，跳过", target);
						continue;
					}
					logger.debug("命中目标 [{}]", target);
					action.action((Class<T>) target);

				} catch (Throwable e) {
					logger.warn("scan package, load resources faild. resources[" + resource + "], message : " + e.getMessage());
				}
			}
		}
    }

}
