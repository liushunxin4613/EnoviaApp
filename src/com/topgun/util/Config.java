package com.topgun.util;

import com.topgun.enoviaapp.R;

public class Config {

	//MainActivity
	/**
	 * ������Ϣ�ļ���
	 */
	public static final String SHAREPREFERENCE_SETTING = "setting_info";

	public static final String SETTING_HOME_INDEX = "home_index_location";
	
	public static final String getRelatedDocumentMethodName = "getRelatedDocument";
	
	public static final String getObjectImageMethodName = "getObjectImage";

	//web service
	/**
	 * �����ռ�
	 */
	public static final String nameSpace = "http://42.121.2.234:8080/enovia/services/";

	/**
	 * �˵�
	 */
	public static final String endPoint = "http://42.121.2.234:8080/enovia/services/TopGunEnoviaAppWebServiceJPO";

	/**
	 * ��ȡ��������
	 */
	public static final String getMyTaskmethodName = "getMyTaskAndCompleteTask";
	
	public static final String getMyWBSTaskMethodName = "getMyWBSTask";

	public static final String getMyWBSTaskTypeArray[] = {
		"Assign"
		,"30dAll"
		,"Complete"
		,"NextWeekCreate"
		,"Create"
	};
	
	public static final String getMyWorkflowTaskMethodName = "getMyWorkflowTask";

	public static final String getMyWorkflowTaskTypeArray[] = {
		"30dAll"
		,"AllAssigned"
		,"30dAllComplete"
		,"AllPending"
	};
	
	
	public static final String doCompleteTaskMethodName = "doCompleteTask";

	/**
	 * Login
	 */
	public static final String isUsermethodName = "isUser";

	/**
	 * ��������
	 */
	public static final String createOjmethodName = "createObject";
	/**
	 * ����������Ϣ
	 */
	public static final String setObjectInfoMethodName = "setObjectInfo";

	/**
	 * ����������Ϣ
	 */
	public static final String updateIssueMethodName = "updateIssue";

	/**
	 * ����������Ϣ
	 */
	public static final String promoteIssueMethodName = "promoteIssue";

	/**
	 * ����������Ϣ
	 */
	public static final String createAndSetObjectInfoMethodName = "createAndSetObjectInfo";
	
	
	public static final String connectObjAndFileMethodName = "connectObjAndFile";

	public static final int idApptitle[] = {
		R.string.app_name
		
		,R.string.activity_login_title
		,R.string.user_info_title
		,R.string.inbox_task_title
		,R.string.inbox_task_detail_title
		,R.string.issue_title
		
		,R.string.issue_detail_title
		,R.string.issue_edit_title
		,R.string.issue_review_title
		,R.string.task_title
		,R.string.task_detail_title
		
		,R.string.publish_issue_title
	};
	
	
	/**
	 * ��ȡ������ϸ
	 */
	public static final String getOjIfthodName = "getObjectInfo";

	/**
	 * ��ȡ������ϸ
	 */
	public static final String getMyIssueMethodName = "getMyIssue";

	/**
	 * ��ȡ��Ŀ��Ϣ
	 */
	public static final String getProjectListMethodName = "getProjectList";

	/**
	 * ��ȡ��Ŀ��Ϣ
	 */
	public static final String deleteObjectMethodName = "deleteObject";

	/**
	 * �ֱ��ȡ���״̬(Complete)���״̬(Active)��ȡ��״̬(Cancelled)<br>
	 * ��ȡ��Ŀ����
	 */
	public static final String SWHERE_TYPE[] = {"Complete","Active","Cancelled"};

	//��ȡjson������
	
	/**
	 * �ϴ�ͼƬ�Ŀ��
	 */
	public static final int UPLOAD_IMAGE_WIDTH = 1200;

	/**
	 * �ϴ�ͼƬ�ĸ߶�
	 */
	public static final int UPLOAD_IMAGE_HEIGHT = 900;

	/**
	 * �û���
	 */
	public static final String USER_LAST_NAME = "Last Name";
	/**
	 * �û���
	 */
	public static final String USER_FIRST_NAME = "First Name";

	/**
	 * �û��� username
	 */
	public static final String USER_NAME = "name";
	/**
	 * ����
	 */
	public static final String USER_EMAIL = "Email Address";


	/**
	 * ��������
	 */
	public static final String ISSUE_NAME = "name";
	/**
	 * ��������
	 */
	public static final String ISSUE_DESCRIBE = "description";
	/**
	 * ����״̬
	 */
	public static final String ISSUE_STATE = "current";
	/**
	 * ������Դ
	 */
	public static final String ISSUE_HS_PRIORITY = "HS Issue Priority";
	public static final String ISSUE_PRIORITY = "Priority";
	/**
	 * ���������Ŀ
	 */
	public static final String ISSUE_RELATED_PROJECT = "relatedProject";
	/**
	 * Ҫ��ʼ����
	 */
	public static final String ISSUE_REQUIREMENTS_START_DATE = "Estimated Start Date";
	/**
	 * Ҫ���������
	 */
	public static final String ISSUE_REQUIREMENTS_DATE = "Estimated End Date";
	/**
	 * ʵ�ʿ�ʼ����
	 */
	public static final String ISSUE_START_DATE = "Actual Start Date";
	/**
	 * ʵ�ʽ�������
	 */
	public static final String ISSUE_FINISH_DATE = "Actual End Date";

	/**
	 * �ҵ�����
	 */
	public static final String ISSUE_USER = "owner";
	
	/**
	 * ������ӵ�е�����
	 */
	public static final String ISSUE_OWNER = "owner";
	/**
	 * ��ӵ�еĻ����
	 */
	public static final String ISSUE_OWNER_AND_ACTIVE = "ownerAndActive";

	/**
	 * ��ӵ�еĴ�������
	 */
	public static final String ISSUE_OWNER_AND_CREATE = "ownerAndCreate";
	/**
	 * ��Ҫ����˵�����
	 */
	public static final String ISSUE_OWNER_AND_REVIEW = "ownerAndReview";
	/**
	 * ָ�ɸ��ҵĻ����
	 */
	public static final String ISSUE_OWNER_AND_ASSING_TO_ME = "ownerAndAssignToMe";
	/**
	 * ����ָ�ɵ�����
	 */
	public static final String ISSUE_OWNER_AND_ALL_ASSIGNED = "ownerAndAllAssigned";
	
	/**
	 * ��ע�е�����
	 */
	public static final String ISSUE_OWNER_WATCH_ACTIVE = "watchedActive";

	/**
	 * ������
	 */
	public static final String ISSUE_ASSIGNEE = "assigner";
	/**
	 * TAG
	 */
	public static final String ISSUE_TAG = "Issue Classification";
	
	public static final String ISSUE_RESOLUTION = "ResolutionStatement";
	
	/**
	 * Watchers
	 */
	public static final String ISSUE_WATCHERS = "Co-Owners";
	/**
	 * TAG
	 */
	public static final String ISSUE_ATTACH_FILE = "";
	/**
	 * reportOrganization
	 */
	public static final String ISSUE_REPORT_ORGANIZATION = "reportOrganization";

	public static final String ISSUE_ID = "id";



	//��������
	/**
	 * ��������
	 */
	public static final String INBEX_NAME = "name";
	/**
	 * ��������˵��
	 */
	public static final String INBEX_TITLE = "Title";
	/**
	 * ���̲�������
	 */
	public static final String INBEX_ROUTE_ACTION = "Route Action";
	/**
	 * ����״̬
	 */
	public static final String INBEX_CURRENT = "current";
	/**
	 * ���̴�����
	 */
	public static final String INBEX_ORIGINATOR = "Originator";
	/**
	 * ���̴�����
	 */
	public static final String INBEX_SCHEDULED_COMPLETION_DATE = "Scheduled Completion Date";
	/**
	 * ���̴�����
	 */
	public static final String INBEX_REVISION = "revision";


	/**
	 * WBS��������
	 */
	public static final String TASK_NAME = "name";
	/**
	 * WBS����
	 */
	public static final String TASK_STATE = "current";
	/**
	 * WBS����
	 */
	public static final String TASK_DESCRIPTION = "description";
	/**
	 * WBS����
	 */
	public static final String TASK_TYPE = "type";
	/**
	 * WBS����
	 */
	public static final String TASK_ESTIMATED_START = "Task Estimated Start Date";
	/**
	 * WBS����
	 */
	public static final String TASK_ESTIMATED_FINISH = "Task Estimated Finish Date";
	/**
	 * WBS����
	 */
	public static final String TASK_START = "Task Actual Start Date";
	/**
	 * WBS����
	 */
	public static final String TASK_FINISH = "Task Actual Finish Date";
	/**
	 * WBS����
	 */
	public static final String TASK_REVISION = "revision";
	/**
	 * WBS����
	 */
	public static final String TASK_ORIGINATOR = "Originator";
	/**
	 * WBS����
	 */
	public static final String TASK_OWNER = "owner";
	
	public static final String ID = "id";


	//��Ŀ
	/**
	 * ��ĿID
	 */
	public static final String PROJECT_ID = "id";
	/**
	 * ��Ŀ����
	 */
	public static final String PROJECT_NAME = "name";

	public static final int[] INCLUDE= {
		R.id.Tv_include_key
		,R.id.Tv_include_value
	};
	public static final int[] INCLUDE_PUBLISH_EDIT= {
		R.id.tv_include_publish_key
		,R.id.et_include_publish_value
	};
	public static final int[] INCLUDE_SP_PUBLISH_TV= {
		R.id.tv_include_spinner_tv_key
		,R.id.tv_include_spinner_tv_value
	};

	public static final int [] ITEM = {
		R.id.include_tv_issue_0
		,R.id.include_tv_issue_1
		,R.id.include_tv_issue_2
		,R.id.include_tv_issue_3
		,R.id.include_tv_issue_4
		,R.id.include_tv_issue_5
	};
	
	public static final int [] ITEM_TASK = {
		R.id.include_tv_issue_0
		,R.id.include_tv_issue_1
		,R.id.include_tv_issue_2
	};

	public static final int [] SINT_ISSUE = {
		R.string.issue_name
		,R.string.issue_describe
		,R.string.issue_state
		,R.string.issue_related_project
		,R.string.issue_user
		,R.string.issue_assignee
	};
	public static final String[] STR_ISSUE = {
		ISSUE_NAME
		,ISSUE_DESCRIBE
		,ISSUE_STATE
		,ISSUE_RELATED_PROJECT
		,ISSUE_USER
		,ISSUE_ASSIGNEE
	};

	public static final int [] SINT_INBOX = {
		R.string.inbox_task_name
		,R.string.inbox_task_state,
		R.string.inbox_task_titles
	};
	public static final String[] STR_INBOX = {
		INBEX_NAME
		,INBEX_CURRENT
		,INBEX_TITLE
	};

	public static final int [] SINT_TASK = {
		R.string.task_name
		,R.string.task_state
		,R.string.task_describe
	};
	public static final String[] STR_TASK = {
		TASK_NAME
		,TASK_STATE
		,TASK_DESCRIPTION
	};

	public static final int[] FM_ITEM_ISSUE = {
		R.id.fm_include_tv_issue_0,
		R.id.fm_include_tv_issue_1,
		R.id.fm_include_tv_issue_2,
		R.id.fm_include_tv_issue_3,
		R.id.fm_include_tv_issue_4,
		R.id.fm_include_tv_issue_5,
		R.id.fm_include_tv_issue_6,
		R.id.fm_include_tv_issue_7,
		R.id.fm_include_tv_issue_8,
		R.id.fm_include_tv_issue_9,
		R.id.fm_include_tv_issue_10,
		R.id.fm_include_tv_issue_11,
		R.id.fm_include_tv_issue_12
	};

	public static final int[] FM_SINT_ISSUE = {
		R.string.issue_name,
		R.string.issue_describe,
		R.string.issue_state,
		R.string.issue_priority,
		R.string.issue_tag,
		R.string.issue_resolution,
		R.string.issue_related_project,
		R.string.issue_requirements_start_date,
		R.string.issue_requirements_date,
		R.string.issue_start_date,
		R.string.issue_finish_date,
		R.string.issue_user,
		R.string.issue_assignee
	};

	public static final String[] FM_STR_ISSUE = {
		ISSUE_NAME,
		ISSUE_DESCRIBE,
		ISSUE_STATE,
		ISSUE_HS_PRIORITY,
		ISSUE_TAG,
		ISSUE_RESOLUTION,
		ISSUE_RELATED_PROJECT,
		ISSUE_REQUIREMENTS_START_DATE,
		ISSUE_REQUIREMENTS_DATE,
		ISSUE_START_DATE,
		ISSUE_FINISH_DATE,
		ISSUE_USER,
		ISSUE_ASSIGNEE
	};

	public static final int[] IF_ITEM_ISSUE_DATE = {
		6,
		7,
		8,
		9
	};

	public static final int[] FM_ITEM_INBOX = {R.id.fm_include_tv_inbox_0
		,R.id.fm_include_tv_inbox_1,R.id.fm_include_tv_inbox_2,R.id.fm_include_tv_inbox_3
		,R.id.fm_include_tv_inbox_4,R.id.fm_include_tv_inbox_5};

	public static final int[] FM_SINT_INBOX = {R.string.inbox_task_name
		,R.string.inbox_task_titles,R.string.inbox_task_route_action
		,R.string.inbox_task_state,R.string.inbox_task_version,R.string.inbox_task_originator
		,R.string.inbox_task_scheduled_completion_date};

	public static final String[] FM_STR_INBOX = {INBEX_NAME,INBEX_TITLE,INBEX_ROUTE_ACTION
		,INBEX_CURRENT,INBEX_REVISION,INBEX_ORIGINATOR,INBEX_SCHEDULED_COMPLETION_DATE};

	public static final int[] FM_ITEM_TASK = {R.id.fm_include_tv_task_0,R.id.fm_include_tv_task_1
		,R.id.fm_include_tv_task_2,R.id.fm_include_tv_task_3,R.id.fm_include_tv_task_4
		,R.id.fm_include_tv_task_5,R.id.fm_include_tv_task_6,R.id.fm_include_tv_task_7
		,R.id.fm_include_tv_task_8,R.id.fm_include_tv_task_9,R.id.fm_include_tv_task_10};

	public static final int[] FM_SINT_TASK = {R.string.task_name,R.string.task_describe
		,R.string.task_state,R.string.task_type,R.string.task_estimate_start_date
		,R.string.task_estimate_finish_date,R.string.task_start_date
		,R.string.task_finish_date,R.string.task_version,R.string.task_originator
		,R.string.task_owner};

	public static final String[] FM_STR_TASK = {
		TASK_NAME,
		TASK_DESCRIPTION,
		TASK_STATE,
		TASK_TYPE,
		TASK_ESTIMATED_START,
		TASK_ESTIMATED_FINISH,
		TASK_START,
		TASK_FINISH,
		TASK_REVISION,
		TASK_ORIGINATOR,
		TASK_OWNER
	};

	public static final int[] IF_ITEM_TASK_DATE = {
		4,
		5,
		6,
		7
	};

	public static final int[] FM_ITEM_TV_PUBLISH_ISSUE = {
		R.id.fm_include_publish_tv_start_date,
		R.id.fm_include_publish_tv
	};

	public static final int[] FM_SINT_TV_PUBLISH_ISSUE = {
		R.string.publish_issue_requirements_start_date,
		R.string.publish_issue_requirements_date
	};

	public static final String[] FM_STR_TV_PUBLISH_ISSUE = {
		ISSUE_REQUIREMENTS_START_DATE,
		ISSUE_REQUIREMENTS_DATE
	};

	public static final int[] FM_ITEM_SP_PUBLISH_ISSUE = {
		R.id.fm_include_publish_issue_sp_0,
		R.id.fm_include_publish_issue_sp_2
	};

	public static final int[] FM_SINT_SP_PUBLISH_ISSUE = {
		R.string.publish_issue_related_project,
		R.string.publish_issue_hs_priority,
	};

	public static final String[] FM_STR_SP_PUBLISH_ISSUE = {
		ISSUE_RELATED_PROJECT,
		ISSUE_HS_PRIORITY
	};

	public static final String[] FM_CHECK_DATE_PUBLISH_ISSUE = {
		ISSUE_DESCRIBE,
		ISSUE_RELATED_PROJECT,
		ISSUE_REQUIREMENTS_START_DATE,
		ISSUE_REQUIREMENTS_DATE,
		ISSUE_ASSIGNEE
	};

	public static final int[] FM_CHECK_DATE_PUBLISH_ISSUE_ERROR = {
		R.string.publish_issue_check_describe,
		R.string.publish_issue_check_related_project,
		R.string.publish_issue_check_requirements_start_date,
		R.string.publish_issue_check_requirements_date,
		R.string.publish_issue_check_user
	};

	public static final int[] FM_ITEM_PUBLISH_ISSUE = {
		R.id.fm_include_publish_issue_0,
		R.id.fm_include_publish_issue_1,
		R.id.fm_include_publish_issue_2,
		R.id.fm_include_publish_issue_3,
	};

	public static final int[] FM_SINT_PUBLISH_ISSUE = {
		R.string.publish_issue_describe,
		R.string.publish_issue_user,
		R.string.publish_issue_tag,
		R.string.publish_issue_watchers
	};

	public static final String[] FM_STR_PUBLISH_ISSUE = {
		ISSUE_DESCRIBE,
		ISSUE_ASSIGNEE,
		ISSUE_TAG,
		ISSUE_WATCHERS,
	};

	public static final int [] INCLUDE_ID_IMAGE_LIST= {
		R.id.user_imgBtn,
		R.id.user_name_TV,
		R.id.user_hello
	};

	public static final int [] FM_ITEM_IM_MAIN_ME = {
		R.id.fm_include_main_me_photo
	};

	public static final int [] INCLUDE_ID_PUBLISH_TEXTVIEW = {
		R.id.tv_include_tv_key,
		R.id.tv_include_tv_value
	};

	public static final int [] INCLUDE_ID_H_TV_IM = {
		R.id.tv_include_tv
	};

	public static final int [] FM_ITEM_TI_MIAN_ME = {
		R.id.fm_include_main_me_ti1,
		R.id.fm_include_main_me_ti2
	};

	public static final int [] FM_STINT_TTI_MIAN_ME = {
		R.string.user_info,
		R.string.user_login_out
	};


	//�û���Ϣ
	/**
	 * �û���Ϣfg�е�ͼ���ǩid��
	 */
	public static final int[] FM_ITEM_USER_INFO_PHOTO = {
		R.id.fm_include_user_info_photo
	};

	/**
	 * �û���Ϣfg�е�˫tv��ǩid��
	 */
	public static final int[] FM_ITEM_USER_INFO_TT = {
		R.id.fm_include_user_info_username,
		R.id.fm_include_user_info_name,
		R.id.fm_include_user_info_email
	};

	/**
	 * ˫tv��ǩIB id��
	 */
	public static final int[] ID_IB_INCLUDE_USER_PHOTO = {
		R.id.ib_include_user_photo
	};

	/**
	 * �û���Ϣ��ʾ
	 */
	public static final int[] FM_SINT_USER_INFO_TT = {
		R.string.user_info_username,
		R.string.user_info_name,
		R.string.user_info_email
	};

	//�༭����������
	/**
	 * et
	 */
	public static final int[] FM_ID_ET_EDITOR_ISSUE = {
		R.id.fg_ic_must_tv_et_0
		,R.id.fg_ic_must_tv_et_1
		,R.id.fg_ic_must_tv_et_2
		,R.id.fg_ic_tv_et_0
		,R.id.fg_ic_tv_et_1
		,R.id.fg_ic_tv_et_2
	};

	public static final int[] FM_STR_ET_EDITOR_ISSUE = {
		R.string.issue_edit_et_0
		,R.string.issue_edit_et_1
		,R.string.issue_edit_et_2
		,R.string.issue_edit_et_3
		,R.string.issue_edit_et_4
		,R.string.issue_edit_et_5
	};

	public static final String[] FM_KEY_ET_EDITOR_ISSUE = {
		//����
		"description"
		//������
		,"owner"
		//Assignee
		,"assigner"
		//Tag
		,"Issue Classification"
		//watchers
		,"Co-Owners"
		//�������
		,"ResolutionStatement"
	};

	/**
	 * sp
	 */
	public static final int[] FM_ID_SP_EDITOR_ISSUE = {
		R.id.fg_ic_tv_sp_0
	};

	public static final int[] FM_STR_SP_EDITOR_ISSUE = {
		R.string.issue_edit_sp_0
	};

	public static final String[] FM_KEY_SP_EDITOR_ISSUE = {
		"HS Issue Priority"
	};

	/**
	 * next
	 */
	public static final int[] FM_ID_NEXT_EDITOR_ISSUE = {
		R.id.fg_ic_tv_tv_0
		,R.id.fg_ic_tv_tv_1
	};

	public static final int[] FM_STR_NEXT_EDITOR_ISSUE = {
		R.string.issue_edit_next_0
		,R.string.issue_edit_next_1
	};

	public static final String[] FM_KEY_NEXT_EDITOR_ISSUE = {
		//�������
		"Resolution Date"
		//Ҫ���������
		,"Estimated End Date"
	};

	/**
	 * tv
	 */
	public static final int[] FM_ID_TV_EDITOR_ISSUE = {
		R.id.fg_ic_tv_two_0
		,R.id.fg_ic_tv_two_1
		,R.id.fg_ic_tv_two_2
		,R.id.fg_ic_tv_two_3
		,R.id.fg_ic_tv_two_4
		,R.id.fg_ic_tv_two_5
		,R.id.fg_ic_tv_two_6
		,R.id.fg_ic_tv_two_7
		,R.id.fg_ic_tv_two_8
	};

	public static final int[] FM_STR_TV_EDITOR_ISSUE = {
		R.string.issue_edit_tv_0
		,R.string.issue_edit_tv_1
		,R.string.issue_edit_tv_2
		,R.string.issue_edit_tv_3
		,R.string.issue_edit_tv_4
		,R.string.issue_edit_tv_5
		,R.string.issue_edit_tv_6
		,R.string.issue_edit_tv_7
		,R.string.issue_edit_tv_8
	};

	public static final String[] FM_KEY_TV_EDITOR_ISSUE = {
		//����
		"name"
		//״̬
		,"current"
		//����
		,"type"
		//ĩ��״̬�޸�ʱ��
		,"actual"
		//������
		,"owner"
		//��������
		,"originated"
		//����޸�ʱ��
		,"modified"
		//ʵ�ʿ�ʼ����
		,"Actual Start Date"
		//ʵ�ʽ�������
		,"Actual End Date"
	};

	//issue Activityҳ�����issueArrayFragment����������
	public static int[] AC_FM_STR = {
		R.string.issue_ownerAndAssignToMe
		,R.string.issue_ownerAndAllAssigned
		,R.string.issue_ownerAndReview
		,R.string.issue_ownerAndActive
		,R.string.issue_ownerAndCreate
		,R.string.issue_owner
		,R.string.issue_ownerAndWatchActive
	};
	
	public static String[] AC_FM_KEY = {
		ISSUE_OWNER_AND_ASSING_TO_ME
		,ISSUE_OWNER_AND_ALL_ASSIGNED
		,ISSUE_OWNER_AND_REVIEW
		,ISSUE_OWNER_AND_ACTIVE
		,ISSUE_OWNER_AND_CREATE
		,ISSUE_OWNER
		,ISSUE_OWNER_WATCH_ACTIVE
	};


	/**
	 * Inbox����
	 */
	public static final String TYPE = "Task";
	/**
	 * Task����
	 */
	public static final String TYPE_TASK = "Inbox Task";
	/**
	 * Task����
	 */
	public static final String TYPE_ISSUE = "Issue";

	//JsonHttpUtil
	/**
	 * in���ǰ׺
	 */
	public static final String SIGN_IN = "in";

	/**
	 * �ɹ���ȡ����
	 */
	public static final String SUCCESS = "OK";

	/**
	 * δ�ɹ���ȡ����
	 */
	public static final String ERROR = "ERROR";

	/**
	 * δ�ɹ���ȡ����
	 */
	public static final String ERROR_NET = "netError";
	public static final String ERROR_SERVICE = "serviceError";

	/**
	 * �������id
	 */
	public static final int ERROR_OTHER_ID = 0x999;
	public static final int ERROR_NET_ID = 0x998;
	public static final int ERROR_SERVICE_ID = 0x997;
	public static final int ERROR_NET_LONG_ID = 0x996;

	/**
	 * ��ҳ���ݴ�С
	 */
	public static final int PAGE_SIZE = 10;

}
