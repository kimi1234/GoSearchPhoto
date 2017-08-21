package com.fyp.gosearchphoto.services;

/**
 * Created by anamay on 8/19/17.
 */

public class ServiceHelper {
    public static final String ERROR_MSG = "Server error. Please try again later.";
    public static final String ERROR_NETWORK_MSG = "Network not available. Please try again later.";


    public static final String PAGE_LOGIN = "loginPage";
    public static final String PAGE_REGISTER_ADMIN = "registerAdminPage";
    public static final String PAGE_REGISTER_COMPANY = "registerCompanyPage";
    public static final String PAGE_CHANGE_PASSWORD = "changePassword";
    public static final String PAGE_PROFILE = "profilePage";

    public static final String PAGE_PUBLIC = "publicPage";
    public static final String PAGE_FAVOURITES = "favouritePage";
    public static final String PAGE_MYPHOTO = "myPhotoPage";

    public static final String PAGE_MANAGE_COMPANY = "manageCompanyPage";
    public static final String PAGE_MANAGE_USER = "manageUserPage";
    public static final String PAGE_MANAGE_PROJECT_ALBUM = "manageProjectAlbum";
    public static final String PAGE_MANAGE_DEPARTMENT = "manageDepartment";
    public static final String PAGE_MANAGE_GROUPS = "manageGroups";

    // Manage Department
    public static final String PAGE_MANAGE_DEPARTMENT_PROFILE = "manageDepartmentProfile";
    public static final String PAGE_MANAGE_DEPARTMENT_USERS = "manageDepartmentUsers";
    public static final String PAGE_MANAGE_DEPARTMENT_ALBUM = "manageDepartmentAlbum";

    // Manage Project Album
    public static final String PAGE_MANAGE_PROJECT_ALBUM_PROFILE = "manageProjectAlbumProfile";
    public static final String PAGE_MANAGE_PROJECT_ALBUM_USERS = "manageProjectAlbumUsers";

    // Manage Groups
    public static final String PAGE_MANAGE_GROUPS_PROFILE = "manageGroupsProfile";
    public static final String PAGE_MANAGE_GROUPS_ALBUM = "manageGroupsAlbum";

    //
    // Package & Payload
    //
    //#1
    public static final String PAYLOAD_CHECKUSER_EXIST = "payload_checkUserExist";
    public static final String REQUEST_CHECKUSER_EXIST = "request_checkUserExist";

    //#2.1
    public static final String PAYLOAD_CHECKCOMPANY_EXIST = "payload_checkCompanyrExist";
    public static final String REQUEST_CHECKCOMPANY_EXIST = "request_checkCompanyExist";

    //#2.2
    public static final String PAYLOAD_REGISTER_ADMIN = "payload_register_admin";
    public static final String REQUEST_REGISTER_ADMIN = "request_register_admin";

    //#3
    public static final String PAYLOAD_LOGIN = "payload_login";
    public static final String REQUEST_LOGIN = "request_login";

    //#4
    public static final String PAYLOAD_UPLOAD_PROFILE = "payload_upload_profile";
    public static final String REQUEST_UPLOAD_PROFILE = "request_upload_profile";

    //#5
    public static final String PAYLOAD_DEACTIVATE_ACCOUNT = "payload_deactivate_account";
    public static final String REQUEST_DEACTIVATE_ACCOUNT = "request_deactivate_account";

    //#6
    public static final String PAYLOAD_CHANGE_PASSWORD = "payload_change_password";
    public static final String REQUEST_CHANGE_PASSWORD = "request_change_password";

    //#7
    public static final String PAYLOAD_GET_COMPANY = "payload_get_company";
    public static final String REQUEST_GET_COMPANY = "request_get_company";

    //#8
    public static final String PAYLOAD_UPDATE_COMPANY = "payload_update_company";
    public static final String REQUEST_UPDATE_COMPANY = "request_update_company";

    //#9
    public static final String PAYLOAD_ADD_FAVOURITE = "payload_add_favourite";
    public static final String REQUEST_ADD_FAVOURITE = "request_add_favourite";

    //#10
    public static final String PAYLOAD_REMOVE_FAVOURITE = "payload_remove_favourite";
    public static final String REQUEST_REMOVE_FAVOURITE = "request_remove_favourite";

    //#11.1
    public static final String PAYLOAD_SEARCH_PUBLIC = "payload_search_public";
    public static final String REQUEST_SEARCH_PUBLIC = "request_search_public";

    //#11.2
    public static final String PAYLOAD_GET_ALBUM_INFO = "payload_get_album_info";
    public static final String REQUEST_GET_ALBUM_INFO = "request_get_album_info";

    //#12
    public static final String PAYLOAD_SEARCH_MY_INFO = "payload_search_my_info";
    public static final String REQUEST_SEARCH_MY_INFO = "request_search_my_info";

    //#13
    public static final String PAYLOAD_SEARCH_MY_FAVOURITES = "payload_search_my_favourites";
    public static final String REQUEST_SEARCH_MY_FAVOURITES = "request_search_my_favourites";

    //#14.1 and #14.2
    public static final String PAYLOAD_GET_COMPANY_USERS = "payload_get_company_users";
    public static final String REQUEST_GET_COMPANY_USERS = "request_get_company_users";

    //#15 and #32
    public static final String PAYLOAD_GET_COMPANY_DEPARTMENTS = "payload_get_company_departments";
    public static final String REQUEST_GET_COMPANY_DEPARTMENTS = "request_get_company_departments";

    //#16
    public static final String PAYLOAD_UPDATE_USER_PROFILE = "payload_update_user_profile";
    public static final String REQUEST_UPDATE_USER_PROFILE = "request_update_user_profile";

    //#17
    public static final String PAYLOAD_REGISTER_COMPANY_USER = "payload_register_company_user";
    public static final String REQUEST_REGISTER_COMPANY_USER = "request_register_company_user";

    //#18
    public static final String PAYLOAD_CHANGE_USER_PASSWORD = "payload_change_user_password";
    public static final String REQUEST_CHANGE_USER_PASSWORD = "request_change_user_password";

    //#19
    public static final String PAYLOAD_GET_ALBUM_LIST_BY_USER = "payload_get_album_list_by_user";
    public static final String REQUEST_GET_ALBUM_LIST_BY_USER = "request_get_album_list_by_user";

    //#20
    public static final String PAYLOAD_REMOVE_USER_ALBUM = "payload_remove_user_album";
    public static final String REQUEST_REMOVE_USER_ALBUM = "request_remove_user_album";

    //#21
    public static final String PAYLOAD_GET_ALBUM_LIST_BY_OWNER = "payload_get_album_list_by_owner";
    public static final String REQUEST_GET_ALBUM_LIST_BY_OWNER = "request_get_album_list_by_owner";

    //#22
    public static final String PAYLOAD_SHARE_ALBUM_TO_USER = "payload_share_album_to_user";
    public static final String REQUEST_SHARE_ALBUM_TO_USER = "request_share_album_to_user";

    //#23.1 and #23.2
    public static final String PAYLOAD_GET_GROUP_BY_USER = "payload_get_group_by_user";
    public static final String REQUEST_GET_GROUP_BY_USER = "request_get_group_by_user";

    //#24
    public static final String PAYLOAD_GET_GROUP_INFO = "payload_get_group_info";
    public static final String REQUEST_GET_GROUP_INFO = "request_get_group_info";

    //#25
    public static final String PAYLOAD_UPDATE_GROUP = "payload_update_group";
    public static final String REQUEST_UPDATE_GROUP = "request_update_group";

    //#26
    public static final String PAYLOAD_DELETE_GROUP_USER = "payload_delete_group_user";
    public static final String REQUEST_DELETE_GROUP_USER = "request_delete_group_user";

    //#27
    public static final String PAYLOAD_CREATE_GROUP = "payload_create_group";
    public static final String REQUEST_CREATE_GROUP = "request_create_group";

    //#28
    public static final String PAYLOAD_SHARE_GROUP_TO_USER = "payload_share_group_to_user";
    public static final String REQUEST_SHARE_GROUP_TO_USER = "request_share_group_to_user";

    //#29
    public static final String PAYLOAD_GET_GROUP_ALBUM = "payload_get_group_album";
    public static final String REQUEST_GET_GROUP_ALBUM = "request_get_group_album";

    //#30
    public static final String PAYLOAD_DELETE_GROUP_ALBUM = "payload_delete_group_album";
    public static final String REQUEST_DELETE_GROUP_ALBUM = "request_delete_group_album";

    //#31
    public static final String PAYLOAD_SHARE_ALBUM_TO_GROUP = "payload_share_album_to_group";
    public static final String REQUEST_SHARE_ALBUM_TO_GROUP = "request_share_album_to_group";

    //#33
    public static final String PAYLOAD_CREATE_DEPARTMENT = "payload_create_department";
    public static final String REQUEST_CREATE_DEPARTMENT = "request_create_department";

    //#34
    public static final String PAYLOAD_UPDATE_DEPARTMENT = "payload_update_department";
    public static final String REQUEST_UPDATE_DEPARTMENT = "request_update_department";

    //#35
    public static final String PAYLOAD_GET_DEPARTMENT_USER = "payload_get_department_user";
    public static final String REQUEST_GET_DEPARTMENT_USER = "request_get_department_user";

    //#36
    public static final String PAYLOAD_DELETE_DEPARTMENT_USER = "payload_delete_department_user";
    public static final String REQUEST_DELETE_DEPARTMENT_USER = "request_delete_department_user";

    //#37
    public static final String PAYLOAD_ADD_DEPARTMENT_USER = "payload_add_department_user";
    public static final String REQUEST_ADD_DEPARTMENT_USER = "request_add_department_user";

    //#38
    public static final String PAYLOAD_GET_DEPARTMENT_ALBUM = "payload_get_department_album";
    public static final String REQUEST_GET_DEPARTMENT_ALBUM = "request_get_department_album";

    //#39
    public static final String PAYLOAD_DELETE_DEPARTMENT_ALBUM = "payload_delete_department_album";
    public static final String REQUEST_DELETE_DEPARTMENT_ALBUM = "request_delete_department_album";

    //#40
    public static final String PAYLOAD_SHARE_ALBUM_TO_DEPARTMENT = "payload_share_album_to_department";
    public static final String REQUEST_SHARE_ALBUM_TO_DEPARTMENT = "request_share_album_to_deptartment";

    //#41
    public static final String PAYLOAD_CREATE_ALBUM = "payload_create_album";
    public static final String REQUEST_CREATE_ALBUM = "request_create_album";

    //#42
    public static final String PAYLOAD_UPDATE_ALBUM_INFO = "payload_update_album_info";
    public static final String REQUEST_UPDATE_ALBUM_INFO = "request_update_album_info";

    //#43
    public static final String PAYLOAD_GET_ALBUM_USERS = "payload_get_album_users";
    public static final String REQUEST_GET_ALBUM_USERS = "request_get_album_users";

    //#44
    public static final String PAYLOAD_DELETE_ALBUM_USER = "payload_delete_album_user";
    public static final String REQUEST_DELETE_ALBUM_USER = "request_delte_album_user";

    //#45
    public static final String PAYLOAD_ADD_ALBUM_USER = "payload_add_album_user";
    public static final String REQUEST_ADD_ALBUM_USER = "request_add_album_user";

    //#46
    public static final String PAYLOAD_GET_ALBUM_GROUPS = "payload_get_album_groups";
    public static final String REQUEST_GET_ALBUM_GROUPS = "request_get_album_groups";

    //#47
    public static final String PAYLOAD_DELETE_ALBUM_GROUP = "payload_delete_album_group";
    public static final String REQUEST_DELETE_ALBUM_GROUP = "request_delete_album_group";

    //#48
    public static final String PAYLOAD_ADD_ALBUM_GROUP = "payload_add_album_group";
    public static final String REQUEST_ADD_ALBUM_GROUP = "request_add_album_group";

    //#49
    public static final String PAYLOAD_GET_ALBUM_DEPARTMENT = "payload_get_album_department";
    public static final String REQUEST_GET_ALBUM_DEPARTMENT = "request_get_album_department";

    //#50
    public static final String PAYLOAD_DELETE_ALBUM_DEPARTMENT = "payload_delete_album_department";
    public static final String REQUEST_DELETE_ALBUM_DEPARTMENT = "request_delete_album_department";

    //#51
    public static final String PAYLOAD_ADD_ALBUM_DEPARTMENT = "payload_add_album_department";
    public static final String REQUEST_ADD_ALBUM_DEPARMENT = "request_add_album_department";

    //#52
    public static final String PAYLOAD_ADV_PUBLIC_SEARCH = "payload_adv_public_search";
    public static final String REQUEST_ADV_PUBLIC_SEARCH = "request_adv_public_search";

    //#53
    public static final String PAYLOAD_ADV_FAVOURITE_SEARCH = "payload_adv_favourite_search";
    public static final String REQUEST_ADV_FAVOURITE_SEARCH = "request_adv_favourite_search";

    //#54
    public static final String PAYLOAD_ADV_MYPHOTO_SEARCH = "payload_adv_myphoto_search";
    public static final String REQUEST_ADV_MYPHOTO_SEARCH = "request_adv_myphoto_search";

    //#55
    public static final String PAYLOAD_DELETE_GROUP = "payload_adv_delete_group";
    public static final String REQUEST_DELETE_GROUP = "request_adv_delete_group";

    //#56
    public static final String PAYLOAD_DELETE_PROJECT_ALBUM = "payload_adv_project_album";
    public static final String REQUEST_DELETE_PROJECT_ALBUM = "request_adv_project_album";

    //#57
    public static final String PAYLOAD_DELETE_DEPARMENT = "payload_delete_department";
    public static final String REQUEST_DELETE_DEPARMENT = "request_delete_department";

    //#58
    public static final String PAYLOAD_UPLOAD_PHOTO = "payload_upload_photo";
    public static final String REQUEST_UPLOAD_PHOTO = "request_upload_photo";

    //#59
    public static final String PAYLOAD_EDIT_PHOTO_INFO_ = "payload_edit_photo_info";
    public static final String REQUEST_EDIT_PHOTO_INFO_ = "payload_edit_photo_info";


}
