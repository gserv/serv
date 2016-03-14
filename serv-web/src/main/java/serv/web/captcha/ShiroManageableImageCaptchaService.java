package serv.web.captcha;

import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

/**
 * 提供了判断仓库中是否有相应的验证码存在
 * 
 * @author shiying
 *
 */
public class ShiroManageableImageCaptchaService extends
		DefaultManageableImageCaptchaService {

	public ShiroManageableImageCaptchaService(
			com.octo.captcha.service.captchastore.CaptchaStore captchaStore,
			com.octo.captcha.engine.CaptchaEngine captchaEngine,
			int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize,
			int captchaStoreLoadBeforeGarbageCollection) {
		super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds,
				maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
	}

	public boolean hasCapcha(String id, String userCaptchaResponse) {
		return store.getCaptcha(id).validateResponse(userCaptchaResponse);
	}
	
}
