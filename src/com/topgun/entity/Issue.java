package com.topgun.entity;

public class Issue {
	/**
	 * ����
	 */
	private String name;
	/**
	 * ����
	 */
	private String description;
	/**
	 * ״̬
	 */
	private String current;
	/**
	 * ������Դ
	 */
	private String HSIssuePriority;

	/**
	 * ��ػ���
	 */
	private String relatedObject;
	/**
	 * �����Ŀ
	 */
	private String relatedProject;
	/**
	 * Ҫ���������
	 */
	private String EstimatedEndDate;
	/**
	 * ʵ�ʿ�ʼ����
	 */
	private String ActualStartDate;
	/**
	 * ʵ�ʽ�������
	 */
	private String ActualEndDate;
	/**
	 * ������
	 */
	private String owner;
	/**
	 * ������
	 */
	private String assigner;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	public String getHSIssuePriority() {
		return HSIssuePriority;
	}
	public void setHSIssuePriority(String hSIssuePriority) {
		HSIssuePriority = hSIssuePriority;
	}
	public String getRelatedObject() {
		return relatedObject;
	}
	public void setRelatedObject(String relatedObject) {
		this.relatedObject = relatedObject;
	}
	public String getRelatedProject() {
		return relatedProject;
	}
	public void setRelatedProject(String relatedProject) {
		this.relatedProject = relatedProject;
	}
	public String getEstimatedEndDate() {
		return EstimatedEndDate;
	}
	public void setEstimatedEndDate(String estimatedEndDate) {
		EstimatedEndDate = estimatedEndDate;
	}
	public String getActualStartDate() {
		return ActualStartDate;
	}
	public void setActualStartDate(String actualStartDate) {
		ActualStartDate = actualStartDate;
	}
	public String getActualEndDate() {
		return ActualEndDate;
	}
	public void setActualEndDate(String actualEndDate) {
		ActualEndDate = actualEndDate;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getAssigner() {
		return assigner;
	}
	public void setAssigner(String assigner) {
		this.assigner = assigner;
	}

}
