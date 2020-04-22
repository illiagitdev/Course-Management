package com.courses.management.course;

import com.courses.management.common.exceptions.SQLCourseException;
import com.courses.management.config.HibernateDatabaseConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CourseDAOImpl implements CourseDAO {
    private static final Logger LOG = LogManager.getLogger(CourseDAOImpl.class);

    public CourseDAOImpl() {
    }

    @Override
    public void create(Course course) {
        LOG.debug(String.format("create: course.title=%s", course.getTitle()));
        Transaction transaction = null;

        try (final Session session = HibernateDatabaseConnector.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(course);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(String.format("Error creating course with title: %s", course.getTitle()), e);
            throw new SQLCourseException("Error occurred when create a course.");
        }
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Course can't be deleted.");
    }

    @Override
    public Course get(int id) {
        LOG.debug(String.format("get: course.id=%s", id));
        Transaction transaction = null;
        Course course = null;

        try (final Session session = HibernateDatabaseConnector.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            course = session.createQuery("from Course c where c.id=:id", Course.class)
                    .setParameter("id", id)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null){
                transaction.rollback();
            }
            LOG.error(String.format("get: course.id=%s", id), e);
            throw new SQLCourseException("Error occurred when find a course.");
        }
        return course;
    }

    @Override
    public List<Course> getAll() {
        Transaction transaction = null;
        List<Course> courses = null;
        try (final Session session = HibernateDatabaseConnector.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            courses = session.createQuery("from Course ", Course.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error("getAll", e);
            throw new SQLCourseException("Error occurred when find all courses.");
        }
        return courses;
    }

    @Override
    public Course get(String title) {
        LOG.debug(String.format("get: course.title=%s", title));
        Transaction transaction = null;
        Course course = null;

        try (final Session session = HibernateDatabaseConnector.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            course = session.createQuery("from Course c where c.title=:title", Course.class)
                    .setParameter("title", title).uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(String.format("error:get: course.title=%s", title), e);
            throw new SQLCourseException("Error occurred when find a course.");
        }
        return course;
    }

    @Override
    public void update(Course course) {
        LOG.debug(String.format("update: course.title=%s, course.status=%s", course.getTitle(),
                course.getCourseStatus()));
        Transaction transaction = null;

        try (final Session session = HibernateDatabaseConnector.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(course);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(String.format("Error updating course with title: %s", course.getTitle()), e);
            throw new SQLCourseException("Error occurred when update a course.");
        }
    }
}
