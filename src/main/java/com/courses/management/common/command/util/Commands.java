package com.courses.management.common.command.util;

public class Commands {
    public static final String HELP = "help";
    public static final String EXIT = "exit";
    public static final String CREATE_COURSE = "create_course|title";
    public static final String DELETE_COURSE = "delete_course|title";
    public static final String UPDATE_COURSE_TITLE = "update_course_title|oldTitle|newTitle";
    public static final String UPDATE_COURSE_STATUS = "update_course_status|title|status";
    public static final String FIND_COURSE = "find_course|title";
    public static final String SHOW_COURSES = "show_courses";
    public static final String CREATE_USER = "create_user|firstName|lastName|email";
    public static final String FIND_USER = "find_user|email";
    public static final String DELETE_USER_COURSE = "delete_user_course|email";
    public static final String FIND_ALL_USERS_BY_COURSE = "find_all_users_by_course_title|courseTitle";
    public static final String FIND_ALL_USERS_BY_STATUS = "find_all_users_by_user_status|status";
    public static final String UPDATE_USER_COURSE = "update_user_course|userEmail|courseTitle";
    public static final String UPDATE_USER_EMAIL = "update_user_email|oldEmail|newEmail";

    private Commands() {
        throw new UnsupportedOperationException("Impossible to instantiate class!");
    }
}
