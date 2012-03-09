package com.heke.framework.common.tree;

import java.util.List;

public class TreeUtil {

	public static Tree getTree(int id,List<Tree> treeList) {
		for (int i=0; i<treeList.size(); i++) {
			Tree t = treeList.get(i);
			if (t.getId() == id) {
				return t;
			}
		}
		return null;
	}
}
