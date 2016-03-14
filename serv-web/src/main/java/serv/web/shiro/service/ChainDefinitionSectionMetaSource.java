package serv.web.shiro.service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.factory.FactoryBean;

/**
 * 允许动态更改 filterChain
 * 
 * 
 * 可扩展
 * 
 * @author shiying
 *
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {
  
    /** 
     * 默认premission字符串 
     */  
    public static final String PREMISSION_STRING = "permsOr[role_admin, {0}]";

	@Override
	public Section getObject() throws Exception {
        Ini ini = new Ini();
        ini.load(filterChainDefinitions);  
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);  
//        //循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,  
//        //里面的键就是链接URL,值就是存在什么条件才能访问该链接
//        SysUserOptionMenuExample example = new SysUserOptionMenuExample();
//        List<SysUserOptionMenu> menus = sysUserOptionMenuMapper.selectByExample(example);
//        Collections.sort(menus, new Comparator<SysUserOptionMenu>() {
//			@Override
//			public int compare(SysUserOptionMenu o1, SysUserOptionMenu o2) {
//				return o2.getMenuUrlRegex().length() - o1.getMenuUrlRegex().length();
//			}
//		});
//        for (SysUserOptionMenu menu : menus) {
//        	if (menu.getMenuUrlRegex() == null) continue;
//        	String url = menu.getMenuUrlRegex().startsWith("/") ? menu.getMenuUrlRegex() : ("/"+menu.getMenuUrlRegex());
//        	url = url.replaceAll("\\.\\*", "\\*\\*");
//        	String per = MessageFormat.format(PREMISSION_STRING, menu.getOptCode());
//            if(StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(per)) {
//            	section.put(url, per);
//            }  
//        }
        return section;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}  
    

	
	/**
	 * 由Spring注入的 filterChain
	 */
    private String filterChainDefinitions;

	public String getFilterChainDefinitions() {
		return filterChainDefinitions;
	}

	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}
    
    
    

}
