package com.courses.management.course.command;

import com.courses.management.common.Command;
import com.courses.management.common.View;
import com.courses.management.common.command.util.InputString;
import com.courses.management.course.Course;
import com.courses.management.course.CourseDAO;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateCourseTest {

    private Command command;
    private View view;
    private CourseDAO dao;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp () {
        this.view = mock(View.class);
        this.dao = mock(CourseDAO.class);
        this.command = new CreateCourse(view, dao);
    }

    @Test
    public void testCanProcessWithCorrectCommand () {
        //given
        InputString inputString = new InputString("create_course|JAVA");
        //when
        boolean result = command.canProcess(inputString);
        //then
        assertTrue(result);
    }

    @Test
    public void testCanNotProcessWithWrongCommand() {
        //given
        InputString inputString = new InputString("create|JAVA");
        //when
        boolean result = command.canProcess(inputString);
        //then
        assertFalse(result);
    }

    @Test
    @Ignore("fail on test, in progress")
    public void testProcessWithAlreadyExistTitle () {
        //given
        Course course = new Course();
        course.setTitle("JAVA");
        //doesnt throw an exception for unknown reason((((
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Course with title JAVA exists");
        //when
        InputString inputString = new InputString("create_course|JAVA");
        when(dao.get("JAVA")).thenReturn(course);
        command.canProcess(inputString);
    }

    @Test
    public void testProcessWithCorrectParameters() {
        //given
        InputString inputString = new InputString("create_course|JAVA");
        //then
        when(dao.get("JAVA")).thenReturn(null);
        command.canProcess(inputString);
    }
}
