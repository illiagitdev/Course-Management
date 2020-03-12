package com.courses.management.common.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;

public class Help implements Command {
    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public String command() {
        return "help";
    }

    @Override
    public void process() {
        view.write("--------------------------------------------------------");
        view.write("-------------------HELP---------------------------------");
        view.write("--------------------------------------------------------");
        view.write("    Command         |   Description---------------------");
        view.write("");
        view.write("    create_course   |   create course-------------------");
        view.write("    update_course   |   Update existing course----------");
        view.write("    update_course_title    |   Update title in existing course-");
        view.write("    update_course_status    |   Update status in existing course-");
        view.write("    find_course_id  |   find course by id---------------");
        view.write("    find_all_courses |   returns all courses -----------");
        view.write("    find_courses_by_status  |   returns all courses by status-");
        view.write("    delete_course   |   delete course by indicated ID---");
        view.write("    delete_course_by_title  |   delete course by indicated title-");
        view.write("    find_course_title |   find course by title-----------");
        view.write("");
        view.write("    create_homework |   create homework-----------------");
        view.write("    update_homework |   update existing homework--------");
        view.write("    delete_homework |   delete homework by id-----------");
        view.write("    homework_by_id  |   get homework by id--------------");
        view.write("    get_all_homework    |   get all homework------------");
        view.write("    homework_by_course_id   |   return all homework by course id");
        view.write("");
        view.write("    create_solution |   create solution-----------------");
        view.write("    update_solution |   update existing solution--------");
        view.write("    delete_solution |   delete solution-----------------");
        view.write("    solution_by_id  |   return solution with id---------");
        view.write("    get_all_solutions   |   retrieve all solutions------");
        view.write("    all_solutions_by_user   |   shows all solutions by user");
        view.write("    solutions_by_homework   |   shows solutions by homework");
        view.write("    solutions_by_user_homework_id   |   solutions by user homework id");
        view.write("");
        view.write("    create_user     |   create user----------------------");
        view.write("    update_user     |   update existing user-------------");
        view.write("    delete_user     |   delete user by id----------------");
        view.write("    user_by_id      |   show user by id------------------");
        view.write("    find_user_by_email  |   find user by email------------------");
        view.write("    all_users       |   show all users-------------------");
        view.write("    find_users_by_course_status  |   show all users by course and status--");
        view.write("    get_all_users_by_course_active_status   |   show all users by their status--");
        view.write("    find_users_by_firstname_and_lastname    |   find user by their first and last names--");
        view.write("");
        view.write("    exit            |   exit application----------------");
        view.write("--------------------------------------------------------");
    }
}
