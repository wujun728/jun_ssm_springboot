package com.ruoyi.project.system.user.controller;

import javax.servlet.http.HttpServletRequest;

import com.ruoyi.common.constant.UserConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ruoyi.common.utils.RequestUtil;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.shiro.service.PasswordService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.user.domain.User;
import com.ruoyi.project.system.user.service.ProfileService;
import com.ruoyi.project.system.user.service.UserService;

import cn.hutool.core.util.StrUtil;

import java.util.Date;

/**
 * 个人信息 业务处理
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/user/profile")
public class ProfileController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

	private String prefix = "system/user/profile";

	@Autowired
	private ProfileService profileService;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordService passwordService;

	/**
	 * 个人信息
	 */
	@GetMapping()
	public String profile(ModelMap mmap) {
		User user = getSysUser();
		mmap.put("user", user);
		mmap.put("roleGroup", userService.selectUserRoleGroup(String.valueOf(user.getUserId())));
		mmap.put("postGroup", userService.selectUserPostGroup(String.valueOf(user.getUserId())));
		return prefix + "/profile";
	}

	@GetMapping("/checkPassword")
	@ResponseBody
	public boolean checkPassword(String password) {
		User user = getSysUser();
		if (passwordService.matches(user, password)) {
			return true;
		}
		return false;
	}

	@GetMapping("/resetPwd")
	public String resetPwd(ModelMap mmap) {
		User user = getSysUser();
		mmap.put("user", userService.selectUserById(user.getUserId()));
		return prefix + "/resetPwd";
	}

	@Log(title = "重置密码", businessType = BusinessType.UPDATE)
	@PostMapping("/resetPwd")
	@ResponseBody
	public AjaxResult resetPwd(HttpServletRequest request) {
		String oldPassword = RequestUtil.getValue(request, "oldPassword");
		String newPassword = RequestUtil.getValue(request, "newPassword");
		User user = getSysUser();

        if (!passwordService.matches(user, oldPassword)) {
            return error("修改密码失败，旧密码错误");
        }
        if (passwordService.matches(user, newPassword)) {
            return error("新密码不能与旧密码相同");
        }

        if (profileService.resetUserPwd(user, newPassword) > 0) {
            setSysUser(userService.selectUserById(user.getUserId()));
            return success();
        }
        return error("修改密码异常，请联系管理员");
	}

	/**
	 * 修改用户
	 */
	@GetMapping("/edit")
	public String edit(ModelMap mmap) {
		User user = getSysUser();
		mmap.put("user", userService.selectUserById(user.getUserId()));
		return prefix + "/edit";
	}

	/**
	 * 修改头像
	 */
	@GetMapping("/avatar")
	public String avatar(ModelMap mmap) {
		User user = getSysUser();
		mmap.put("user", userService.selectUserById(user.getUserId()));
		return prefix + "/avatar";
	}

	/**
	 * 修改用户
	 */
	@Log(title = "个人信息", businessType = BusinessType.UPDATE)
	@PostMapping("/update")
	@ResponseBody
	public AjaxResult update(HttpServletRequest request) {
        User currentUser = getSysUser();
    	String userId = String.valueOf(currentUser.getUserId());
        String phonenumber = RequestUtil.getValue(request, "phonenumber");
        String email = RequestUtil.getValue(request, "email");
        if (StrUtil.isNotEmpty(phonenumber) && userService.checkPhoneUnique(phonenumber, userId) > 0) {
            return error("修改用户'" + currentUser.getLoginName() + "'失败，手机号码已存在");
        }
        else if (StrUtil.isNotEmpty(email) && userService.checkEmailUnique(email, userId) > 0) {
            return error("修改用户'" + currentUser.getLoginName() + "'失败，邮箱账号已存在");
        }
		if (profileService.updateUserInfo(request) > 0) {
			setSysUser(userService.selectUserById(userId));
			return success();
		}
		return error();
	}

	/**
	 * 保存头像
	 */
	@Log(title = "个人信息", businessType = BusinessType.UPDATE)
	@PostMapping("/updateAvatar")
	@ResponseBody
	public AjaxResult updateAvatar(@RequestParam("avatarfile") MultipartFile file) {
		User currentUser = getSysUser();
		try {
			if (!file.isEmpty()) {
				String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file);
				if (profileService.updateUserAvatar(avatar) > 0) {
					setSysUser(userService.selectUserById(currentUser.getUserId()));
					return success();
				}
			}
			return error();
		} catch (Exception e) {
			log.error("修改头像失败！", e);
			return error(e.getMessage());
		}
	}
}