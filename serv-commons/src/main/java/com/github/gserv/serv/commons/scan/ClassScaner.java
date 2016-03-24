package com.github.gserv.serv.commons.scan;

import java.io.IOException;
import java.lang.reflect.Modifier;

import com.google.common.reflect.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类扫描器
 *
 * Created by shiying on 2016/3/24.
 */
public class ClassScaner {
	private static final Logger logger = LoggerFactory.getLogger(ClassScaner.class);

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
        ClassPath classpath = ClassPath.from(ClassScaner.class.getClassLoader());
        for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses()) {
		    if (classInfo.getName().startsWith(basePackage)) {			// 处理的根目录
				try {
					Class<?> target = classInfo.load();
					// 可实例化属性不一致
					if (instantiable != null && instantiable != (!target.isInterface() && !Modifier.isAbstract(target.getModifiers()))) {
						logger.debug("[{}] 可实例化属性不同，跳过", classInfo.getName());
						continue;
					}
					// 继承关系不一致
					if (parentClass != null && !parentClass.isAssignableFrom(target)) {
						logger.debug("[{}] 继承关系不一致，跳过", classInfo.getName());
						continue;
					}
					logger.debug("命中目标 [{}]", classInfo.getName());
					action.action((Class<T>) target);
				} catch (Throwable e) {
					logger.warn("[{}] 检查类异常：[{}]", classInfo.getName(), e.getMessage());
				}
		    }
		}
    }

}
