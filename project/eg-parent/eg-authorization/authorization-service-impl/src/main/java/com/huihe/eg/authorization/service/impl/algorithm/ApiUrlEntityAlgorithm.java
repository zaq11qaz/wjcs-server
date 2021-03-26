package com.huihe.eg.authorization.service.impl.algorithm;

import com.huihe.eg.authorization.model.ApiUrlEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ApiUrlEntityAlgorithm {

	/**
	 * 递归将数据tree存储结构
	 *
	 * @param @param allList
	 * @param @return 设定文件
	 * @return List<Department> 返回类型
	 *
	 */
	public static List<ApiUrlEntity> tree(List<ApiUrlEntity> allList) {
		if (allList == null || allList.isEmpty()) {
			return null;
		}
		Collections.sort(allList);
		List<ApiUrlEntity> tree = new ArrayList<ApiUrlEntity>();
		for (ApiUrlEntity node : allList) {
			if (node.isRoot()) {
				// 遍历根节点
				buildTreeNode(node, allList);
				tree.add(node);
			}
		}
		return tree;
	}

	/**
	 * 构建Node
	 * 
	 * @param node
	 */
	private static void buildTreeNode(ApiUrlEntity node, List<ApiUrlEntity> allList) {
		List<ApiUrlEntity> childrens = null;
		for (ApiUrlEntity child : allList) {
			// 获取子节点
			if (node.getId().equals(child.getParent_id())) {
				if (null == childrens) {
					childrens = new ArrayList<ApiUrlEntity>();
				}
				childrens.add(child);
				buildTreeNode(child, allList);
			}
		}
		if (null != childrens && !childrens.isEmpty()) {
			Collections.sort(childrens);
			node.setChildrens(childrens);
		}
	}

	/**
	 * 构建菜单
	 *
	 * @param @param menus
	 * @param @return 设定文件
	 * @return List<MenuVO> 返回类型
	 *
	 */
//	public static List<MenuVO> buildMenu(List<ApiUrlEntity> ApiUrlEntitys) {
//		if (ApiUrlEntitys == null || ApiUrlEntitys.isEmpty()) {
//			return null;
//		}
//		List<MenuVO> tree = new ArrayList<MenuVO>();
//		for (ApiUrlEntity menuDTO : ApiUrlEntitys) {
//			List<ApiUrlEntity> menuDTOList = menuDTO.getChildrens();
//			MenuVO menuVo = new MenuVO();
//			menuVo.setPath(menuDTO.getUrl());
//			menuVo.setHidden((menuDTO.getHidden() == 1) ? true : false);
//			menuVo.setMeta(new MenuMetaVO(menuDTO.getName(), menuDTO.getIcon(),
//					menuDTO.getCache() == 0 ? true : false));
//			menuVo.setRoot((menuDTO.getParent_id() == 0) ? true : false);
//			if (menuDTO.getParent_id() == 0 || menuDTO.getTypes() == 0) {
//				if (menuDTO.getI_frame() == 0) {
//					menuVo.setName(menuDTO.getName());
//					menuVo.setRedirect("noRedirect");
//					menuVo.setComponent("Layout");
//				}
//			} else {
//				menuVo.setName(menuDTO.getComponent_name());
//				menuVo.setComponent(menuDTO.getComponent_path());
//			}
//			if (menuDTOList != null && !menuDTOList.isEmpty()) {
//				menuVo.setChildren(buildMenu(menuDTOList));
//				// 处理是一级菜单并且没有子菜单的情况
//			} else if (menuDTO.getParent_id() == 0) {
//				MenuVO menuVo1 = new MenuVO();
//				menuVo1.setMeta(menuVo.getMeta());
//				// 非外链
//				if (menuDTO.getI_frame() == 0) {
//					menuVo.setComponent("Layout");
//					menuVo1.setName(menuDTO.getComponent_name());
//					menuVo1.setPath(menuDTO.getUrl());
//					menuVo1.setComponent(menuDTO.getComponent_path());
//					menuVo1.setHidden((menuDTO.getHidden() == 1) ? true : false);
//					menuVo1.setRoot((menuDTO.getParent_id() == 0) ? true : false);
//				} else {
//					menuVo1.setPath(menuDTO.getUrl());
//				}
//				List<MenuVO> list1 = new ArrayList<MenuVO>();
//				list1.add(menuVo1);
//
//				menuVo.setName(null);
//				menuVo.setMeta(null);
//				menuVo.setChildren(list1);
//			} else {
//				menuVo.setChildren(new ArrayList<MenuVO>());
//			}
//			tree.add(menuVo);
//		}
//		return tree;
//	}

}
