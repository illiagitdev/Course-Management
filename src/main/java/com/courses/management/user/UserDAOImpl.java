package com.courses.management.user;

import com.courses.management.common.exceptions.SQLUserException;
import com.courses.management.solution.SolutionDAOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOG = LogManager.getLogger(SolutionDAOImpl.class);
    private SessionFactory sessionFactory;

    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(User user) {
        LOG.debug(String.format("create: user.firstName=%s, lastName=%s, email=%s", user.getFirstName(),
                user.getLastName(), user.getEmail()));
        Transaction transaction = null;

        try (final Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(String.format("Error creating user: %s", user.getFirstName()), e);
            throw new SQLUserException("Error occurred when creating user");
        }
    }

    @Override
    public void update(User user) {
        LOG.debug(String.format("update: user.firstName=%s, lastName=%s, email=%s", user.getFirstName(),
                user.getLastName(), user.getEmail()));
        Transaction transaction = null;
        try (final Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(String.format("Error creating user: %s", user.getFirstName()), e);
            throw new SQLUserException("Error occurred when updating user");
        }
    }

    @Override
    public void delete(int id) {
        LOG.debug(String.format("delete: user_id=%s", id));
        Transaction transaction = null;
        try (final Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createQuery("DELETE User u where u.id=:id").setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(String.format("Error deleting user with id: %s", id), e);
            throw new SQLUserException("Error occurred when deleting user");
        }
    }

    @Override
    public User get(int id) {
        LOG.debug(String.format("get: user.id=%d", id));
        Transaction transaction = null;
        try (final Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            return session.get(User.class, id);
        } catch (Exception e) {
            if (transaction != null) {
                transaction = null;
            }
            LOG.error(String.format("get: user.id=%d", id), e);
            throw new SQLUserException("Error occurred when retrieving user");
        }
    }

    @Override
    public User get(String email) {
        LOG.debug(String.format("get: user.email =%s", email));
        try (final Session session = sessionFactory.openSession()){
            return session.createQuery("from User u where u.email=:email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception e) {
            LOG.error(String.format("get: user.email =%s", email), e);
            throw new SQLUserException("Error occurred when retrieving user");
        }
    }

    @Override
    public List<User> getUsersByCourse(String courseTitle) {
        LOG.debug(String.format("getUsersByCourse: course.title=%s", courseTitle));
        try (final Session session = sessionFactory.openSession()){
            return session.createQuery("from User u where u.course.title=:courseTitle", User.class)
                    .setParameter("courseTitle", courseTitle)
                    .getResultList();
        } catch (Exception e) {
            LOG.error(String.format("getUsersByCourse: course.title=%s", courseTitle), e);
            throw new SQLUserException("Error occurred when retrieving users by course title");
        }
    }

    @Override
    public List<User> getAllByStatus(UserStatus status) {
        LOG.debug(String.format("getAllByStatus: course.status=%s", status.name()));
        try (final Session session = sessionFactory.openSession()){
            return session.createQuery("from User u where u.status=:status", User.class)
                    .setParameter("status", status)
                    .getResultList();
        } catch (Exception e) {
            LOG.error(String.format("getAllByStatus: course.status=%s", status.name()), e);
            throw new SQLUserException("Error occurred when retrieving users by status");
        }
    }

    @Override
    public List<User> getAll() {
        LOG.debug("getAll: ");
        try (final Session session = sessionFactory.openSession()){
            return session.createQuery("from User ", User.class).getResultList();
        } catch (Exception e) {
            LOG.error("getAll:", e);
            throw new SQLUserException("Error occurred when retrieving list of users");
        }
    }
}
