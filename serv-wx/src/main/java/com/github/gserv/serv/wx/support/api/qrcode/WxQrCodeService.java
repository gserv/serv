package com.github.gserv.serv.wx.support.api.qrcode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.github.gserv.serv.wx.service.manager.WxServiceManager;
import com.github.gserv.serv.wx.support.api.accesstoken.WxApiAccessTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gserv.serv.commons.HttpUtils;
import com.github.gserv.serv.commons.JsonUtils;
import com.github.gserv.serv.wx.service.manager.WxService;
import com.github.gserv.serv.wx.support.WxApiInvorkException;

/**
 * 动态二维码服务
 * 
 * @author shiying
 *
 */
public class WxQrCodeService implements WxService {
	private static final Logger logger = LoggerFactory.getLogger(WxQrCodeService.class);
	
	/**
	 * 二维码类型
	 * @author shiying
	 *
	 */
	public static enum QrCodeType {
		QR_SCENE, QR_LIMIT_SCENE
	}
	
	private WxServiceManager wxServiceManager;
	
	/**
	 * Ticket获取API
	 */
	private String qrCodeApiUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
	
	/**
	 * 二维码获取URL
	 */
	private String qrCodeGetApiUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
	
	/**
	 * 获得永久二维码地址
	 * @param scene_str
	 * @return
	 * @throws WxApiInvorkException
	 */
	public String getForeverQrCodeUrl(String scene_str) throws WxApiInvorkException {
		String ticket = (String) requestQrCodeTicket(QrCodeType.QR_LIMIT_SCENE, scene_str).get("ticket");
		try {
			return qrCodeGetApiUrl + "?ticket=" + URLEncoder.encode(ticket, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获得临时二维码地址
	 * @param ticket
	 * @return
	 * @throws WxApiInvorkException 
	 */
	public String getTempQrCodeUrl(Integer scene_id) throws WxApiInvorkException {
		String ticket = (String) requestQrCodeTicket(QrCodeType.QR_SCENE, scene_id).get("ticket");
		try {
			return qrCodeGetApiUrl + "?ticket=" + URLEncoder.encode(ticket, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获得场景二维码Ticket
	 * @param type
	 * @param scene_id
	 * @return
	 * @throws WxApiInvorkException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> requestQrCodeTicket(QrCodeType type, Object scene_id) throws WxApiInvorkException {
		try {
			String url = qrCodeApiUrl + "?access_token=" + wxServiceManager.getWxService(WxApiAccessTokenService.class).accessToken();
			Map<String, Object> data = new HashMap<String, Object>();
			if (type == QrCodeType.QR_SCENE) {
				data.put("expire_seconds", 604800);
			}
			data.put("action_name", "QR_SCENE");
			data.put("action_info", new HashMap<String, Object>());
			((Map<String, Object>) data.get("action_info")).put("scene", new HashMap<String, Object>());
			if (scene_id instanceof Integer) {
				((Map<String, Object>) ((Map<String, Object>) data.get("action_info")).get("scene")).put("scene_id", scene_id);
			} else if (scene_id instanceof String) {
				if (type != QrCodeType.QR_LIMIT_SCENE) {
					throw new WxApiInvorkException("scene_str only support type ["+QrCodeType.QR_LIMIT_SCENE+"]");
				}
				((Map<String, Object>) ((Map<String, Object>) data.get("action_info")).get("scene")).put("scene_str", scene_id);
			} else {
				throw new WxApiInvorkException("unknown scene_id type, scene_id type [" + scene_id.getClass() + "], value [" + scene_id + "]");
			}
			String res = HttpUtils.post(url, JsonUtils.toJson(data));
			return JsonUtils.parseJsonMap(res);
		} catch (Exception e) {
			throw new WxApiInvorkException("request temp qr code faild.", e);
		}
	}

	public void setQrCodeApiUrl(String qrCodeApiUrl) {
		this.qrCodeApiUrl = qrCodeApiUrl;
	}

	public WxServiceManager getWxServiceManager() {
		return wxServiceManager;
	}

	public void setWxServiceManager(WxServiceManager wxServiceManager) {
		this.wxServiceManager = wxServiceManager;
	}

	
	

}


