package com.courses.management.solution;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SolutionDAOImpl implements SolutionDao {
    private static final Logger LOG = LogManager.getLogger(SolutionDAOImpl.class);
    private SessionFactory sessionFactory;

    public SolutionDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Solutions solutions) {
        LOG.debug(String.format("create: solutions.text=%s", solutions.getText()));
        Transaction transaction = null;

        try (final Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(solutions);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(String.format("Error creating solution: %s", solutions.getText()), e);
        }
    }

    @Override
    public void update(Solutions solutions) {
        LOG.debug(String.format("update: solutions.text=%s", solutions.getText()));
        Transaction transaction = null;

        try (final Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.update(solutions);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(String.format("Error creating solution: %s", solutions.getText()), e);
        }
    }

    @Override
    public void delete(int id) {
        LOG.debug(String.format("delete: solution.id=%s", id));
        Transaction transaction = null;

        try (final Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createQuery("DELETE Solutions s where s.id=:id").setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(String.format("Error deleting solution with id: %s", id), e);
        }
    }

    @Override
    public Solutions get(int id) {
        LOG.debug(String.format("get(id): solution.id=%s", id));
        try (final Session session = sessionFactory.openSession()){
            return session.createQuery("from Solutions s where s.id=:id", Solutions.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (Exception e) {
            LOG.error(String.format("Error retrieving Solution with id:%s", id), e);
        }
        return null;
    }

    @Override
    public List<Solutions> getAll() {
        try (final Session session = sessionFactory.openSession()){
            return session.createQuery("from Solutions ", Solutions.class).getResultList();
        } catch (Exception e) {
            LOG.error("Error retrieving solution.", e);
        }
        return null;
    }

    @Override
    public List<Solutions> getAllByUser(int id) {
        try (final Session session = sessionFactory.openSession()){
            return session.createQuery("from Solutions s where s.user.id=:id", Solutions.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception e) {
            LOG.error(String.format("Error retrieving solution by user id:%s", id), e);
        }
        return null;
    }

    @Override
    public List<Solutions> getAllByHomework(int id) {
        try (final Session session = sessionFactory.openSession()){
            return session.createQuery("from Solutions s where s.homework.id=:id", Solutions.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception e) {
            LOG.error(String.format("Error retrieving solution by homework id:%s", id), e);
        }
        return null;
    }

    @Override
    public Solutions get(int userId, int homeworkId) {
        LOG.debug(String.format("get(userId, homeworkId): solution.user=%d, homework=%d", userId, homeworkId));
        try (final Session session = sessionFactory.openSession()){
            return session.createQuery("from Solutions s where s.user.id=:userId and s.homework.id=:homeworkId", Solutions.class)
                    .setParameter("userId", userId)
                    .setParameter("homeworkId", homeworkId)
                    .uniqueResult();
        } catch (Exception e) {
            LOG.error("Error retrieving Solution.", e);
        }
        return null;
    }
}
