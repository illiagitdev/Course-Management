package com.courses.management.homework;

import com.courses.management.common.exceptions.SQLUserException;
import com.courses.management.config.HibernateDatabaseConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class HomeworkDAOImpl implements HomeworkDAO {
    private static final Logger LOG = LogManager.getLogger(HomeworkDAOImpl.class);

   private JdbcTemplate template;

    public HomeworkDAOImpl() {
    }

    @Autowired
    public HomeworkDAOImpl(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

    @Override
    public void create(Homework homework) {
        LOG.info(String.format("create: homework.title=%s", homework.getTitle()));
        try {
            template.update("INSERT INTO home_work(title, text, file_path, course_id) VALUES (?, ?, ?, ?)",
                    homework.getTitle(), homework.getText(), homework.getPath(), homework.getCourse().getId());
        } catch (Exception e) {
            LOG.error(String.format("Error creating homework with title=%s", homework.getTitle()), e);
            throw new SQLUserException("Error occurred when creating a homework");
        }
    }

    @Override
    public void update(Homework homework) {
        LOG.info(String.format("update: homework.title=%s", homework.getTitle()));
        Transaction transaction = null;

        try (final Session session = HibernateDatabaseConnector.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(homework);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(String.format("Error updating homework with title=%s", homework.getTitle()), e);
            throw new SQLUserException("Error occurred when updating a homework");
        }
    }

    @Override
    public void delete(int id) {
        LOG.debug(String.format("delete: homework.id=%s", id));
        Transaction transaction = null;

        try (final Session session = HibernateDatabaseConnector.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createQuery("DELETE Homework h where h.id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(String.format("Error deleting homework with id: %s", id), e);
            throw new SQLUserException("Error occurred when removing a homework");
        }
    }

    @Override
    public Homework get(int id) {
        LOG.debug(String.format("get(id): homework.id=%s", id));
        try (final Session session = HibernateDatabaseConnector.getSessionFactory().openSession()){
            return session.createQuery("from Homework h where h.id=:id", Homework.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (Exception e) {
            LOG.error(String.format("Error retrieving course with id:%s", id), e);
            throw new SQLUserException("Error occurred when retrieving a homework");
        }
    }

    @Override
    public List<Homework> getAll() {
        try (final Session session = HibernateDatabaseConnector.getSessionFactory().openSession()){
            return session.createQuery("from Homework ", Homework.class).getResultList();
        } catch (Exception e) {
            LOG.error("Error retrieving courses.", e);
            throw new SQLUserException("Error occurred when retrieving all homeworks");
        }
    }

    @Override
    public List<Homework> getAll(int courseId) {
        LOG.debug(String.format("getAll(courseId): homework.courseId=%s", courseId));
        try (final Session session = HibernateDatabaseConnector.getSessionFactory().openSession()){
            return session.createQuery("from Homework h where h.course.id=:courseId", Homework.class)
                    .setParameter("courseId", courseId)
                    .getResultList();
        } catch (Exception e) {
            LOG.error(String.format("Error retrieving homework for course with id:%s", courseId), e);
            throw new SQLUserException("Error occurred when retrieving all homeworks by course");
        }
    }
}
