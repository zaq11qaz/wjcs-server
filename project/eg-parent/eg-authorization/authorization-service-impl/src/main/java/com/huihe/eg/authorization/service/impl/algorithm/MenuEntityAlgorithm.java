package com.huihe.eg.authorization.service.impl.algorithm;

import com.huihe.eg.authorization.model.MenuEntity;
import com.huihe.eg.authorization.mybatis.dao.MenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 菜单类相关算法
 * 
 * @ClassName: MenuEntityRecursion
 * @Description: 菜单类相关算法
 * @author 252956
 * @date 2019年11月11日 下午3:34:26
 *
 */
@Service
public class MenuEntityAlgorithm {

	@Resource
	private MenuMapper mapper;



	/**
	 * 去重复
	 *
	 * @param @param allList 设定文件
	 * @return void 返回类型
	 *
	 */
	/*
	 * public static void mergeRepeat(List<MenuEntity> allList) { if (allList ==
	 * null || allList.isEmpty()) { return; } Set<MenuEntity> ownedSet = new
	 * HashSet<MenuEntity>(); for (MenuEntity MenuEntity : allList) {
	 * ownedSet.add(MenuEntity); } allList = new ArrayList<MenuEntity>(ownedSet); }
	 */

	/**
	 * 递归将数据tree存储结构
	 *
	 * @param @param allList
	 * @param @return 设定文件
	 * @return List<MenuEntity> 返回类型
	 *
	 */
	public static List<MenuEntity> tree(List<MenuEntity> allList) {
		if (allList == null || allList.isEmpty()) {
			return null;
		}
		Collections.sort(allList);
		List<MenuEntity> tree = new ArrayList<MenuEntity>();
		for (MenuEntity node : allList) {
			if (node.isRoot()) {
				// 遍历根节点
				buildTreeNode(node, allList);
				tree.add(node);
			}
		}
		return tree;
	}

	/**
	 * 构建下级菜单
	 *
	 * @param node
	 */
	private static void buildTreeNode(MenuEntity node, List<MenuEntity> allList) {
		List<MenuEntity> childrens = null;
		for (MenuEntity child : allList) {
			// 获取子节点
			if (node.getId().equals(child.getParent_id())) {
				if (null == childrens) {
					childrens = new ArrayList<MenuEntity>();
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
	 * 根据菜单id查找所有下级菜单(包含当前菜单)
	 *
	 * @param @param id
	 * @param @return 设定文件
	 * @return List<MenuEntity> 返回类型
	 *
	 */
	public static List<MenuEntity> findSelfAndAllChild(Integer id,
													   List<MenuEntity> allList) {
		if (allList == null || allList.isEmpty() || id == null) {
			return null;
		}
		MenuEntity current = null;
		for (MenuEntity dept : allList) {
			if (dept.getId().equals(id)) {
				current = dept;
				break;
			}
		}
		if (current == null) {
			return null;
		}
		List<MenuEntity> resultList = findAllChild(current, allList);
		if (resultList == null) {
			resultList = new ArrayList<MenuEntity>();
		}
		resultList.add(current);
		return resultList;
	}

	/**
	 * 根据菜单id查找所有下级菜单
	 *
	 * @param @param id
	 * @param @return 设定文件
	 * @return List<MenuEntity> 返回类型
	 *
	 */
	public static List<MenuEntity> findAllChild(Integer id,
												List<MenuEntity> allList) {
		if (allList == null || allList.isEmpty() || id == null) {
			return null;
		}
		MenuEntity current = null;
		for (MenuEntity dept : allList) {
			if (dept.getId().equals(id)) {
				current = dept;
				break;
			}
		}
		return findAllChild(current, allList);
	}

	/**
	 * 根据菜单查找所有下级菜单
	 *
	 * @param @param id
	 * @param @return 设定文件
	 * @return List<MenuEntity> 返回类型
	 *
	 */
	public static List<MenuEntity> findAllChild(MenuEntity current,
												List<MenuEntity> allList) {
		if (allList == null || allList.isEmpty() || current == null) {
			return null;
		}
		List<MenuEntity> resultList = new ArrayList<MenuEntity>();
		for (MenuEntity dept : allList) {
			if (dept.getParent_id().equals(current.getId())) {

				resultList.add(dept);
				resultList.addAll(findAllChild(dept, allList));
			}
		}
		return resultList;
	}

//	/**
//	 * 根据菜单id查询公司
//	 *
//	 * @param id
//	 * @return
//	 */
//	public static MenuEntity findCompany(Integer id, List<MenuEntity> allList) {
//		if (allList == null || allList.isEmpty() || id == null) {
//			return null;
//		}
//		MenuEntity target = new MenuEntity();
//		findCompany(allList, id, target);
//		return target;
//	}
//
//	private static void findCompany(List<MenuEntity> allList, Integer parentId,
//									MenuEntity target) {
//		for (MenuEntity MenuEntity : allList) {
//			if (MenuEntity.getId().equals(parentId)) {
//				if (MenuEntity.getLevels().equals(1) || MenuEntity.getLevels().equals(0)) {
//					BeanUtils.copyObject(target, MenuEntity);
//					break;
//				}
//				findCompany(allList, MenuEntity.getParent_id(), target);
//			}
//		}
//	}
}
