package com.ruoyi.project.system.role.domain;

import javax.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.enums.RowType;
import com.ruoyi.framework.aspectj.lang.annotation.MapRow;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 角色表 sys_role
 * @author ruoyi
 */
public class Role extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 角色ID */
	@MapRow(column = "role_id", type = RowType.LONG)
	private Long roleId;

	/** 角色名称 */
	@MapRow(column = "role_name", type = RowType.STRING)
	private String roleName;

	/** 角色权限 */
	@MapRow(column = "role_key", type = RowType.STRING)
	private String roleKey;

	/** 角色排序 */
	@MapRow(column = "role_sort", type = RowType.STRING)
	private String roleSort;

	/** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限） */
	@MapRow(column = "data_scope", type = RowType.STRING)
	private String dataScope;

	/** 角色状态（0正常 1停用） */
	@MapRow(column = "status", type = RowType.STRING)
	private String status;

	/** 备注 */
	@MapRow(column = "remark", type = RowType.STRING)
	private String remark;

	/** 删除标志（0代表存在 2代表删除） */
	@MapRow(column = "del_flag", type = RowType.STRING)
	private String delFlag;

	/** 用户是否存在此角色标识 默认不存在，即是否需要选中 */
	@MapRow(column = "flag", type = RowType.BOOLEAN)
	private boolean flag = false;

	/** 菜单组 */
	private Long[] menuIds;

	/** 部门组（数据权限） */
	private Long[] deptIds;

	public Role() {

	}

	public Role(Long roleId) {
		this.roleId = roleId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public boolean isAdmin() {
		return isAdmin(this.roleId);
	}

	public static boolean isAdmin(Long roleId) {
		return roleId != null && 1L == roleId;
	}

	public String getDataScope() {
		return dataScope;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	@NotBlank(message = "角色名称不能为空")
	@Size(min = 0, max = 30, message = "角色名称长度不能超过30个字符")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@NotBlank(message = "权限字符不能为空")
	@Size(min = 0, max = 100, message = "权限字符长度不能超过100个字符")
	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	@NotBlank(message = "显示顺序不能为空")
	public String getRoleSort() {
		return roleSort;
	}

	public void setRoleSort(String roleSort) {
		this.roleSort = roleSort;
	}

	public String getStatus() {
		return status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Long[] getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(Long[] menuIds) {
		this.menuIds = menuIds;
	}

	public Long[] getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(Long[] deptIds) {
		this.deptIds = deptIds;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("roleId", getRoleId())
				.append("roleName", getRoleName()).append("roleKey", getRoleKey()).append("roleSort", getRoleSort())
				.append("dataScope", getDataScope()).append("status", getStatus()).append("delFlag", getDelFlag())
				.append("createBy", getCreateBy()).append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).append("remark", getRemark())
				.toString();
	}
}