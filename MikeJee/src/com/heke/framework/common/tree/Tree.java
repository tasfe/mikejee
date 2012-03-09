package com.heke.framework.common.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tree implements Comparable<Tree> {

	//node id, which is important to load remote data
	private int id;
	private int pid;
	//node text to show
	private String text; 
	//node state, 'open' or 'closed', default is 'open'. When set to 'closed', the node have children nodes and will load them from remote site
	private String state;
	//Indicate whether the node is checked selected.
	private boolean checked; 
	//custom attributes can be added to a node	
	private Attribute attributes = new Attribute(); 
	//an array nodes defines some children nodes	
	private List<Tree> children = new ArrayList<Tree>();
	//排序
	private int sortNo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public Attribute getAttributes() {
		return attributes;
	}
	public void setAttributes(Attribute attributes) {
		this.attributes = attributes;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	@Override
	public int compareTo(Tree o) {
		return this.sortNo - o.sortNo;
	}
	
	public void addChild(Tree tree) {
		this.children.add(tree);
		//排序
		Collections.sort(children);
	}
}
