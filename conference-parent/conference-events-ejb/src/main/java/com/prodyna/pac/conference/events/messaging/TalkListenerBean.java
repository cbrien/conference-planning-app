package com.prodyna.pac.conference.events.messaging;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.InitialContext;

import org.slf4j.Logger;

import com.prodyna.pac.conference.events.model.Talk;

/**
 * The listener bean for receiving talk events.
 * 
 * @see TalkEvent
 */
@Stateless
public class TalkListenerBean implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant DESTINATION_NAME. */
	public static final String DESTINATION_NAME = "conference/talkchanged";

	/** The logger. */
	@Inject
	Logger logger;

	QueueConnectionFactory connectionFactory;
	/** The connection. */
	private QueueConnection connection;

	private QueueSession session;

	private QueueSender sender;

	/**
	 * Open connection.
	 */
	@PostConstruct
	private void openConnection() {
		try {
			connectionFactory = (QueueConnectionFactory) InitialContext.doLookup("ConnectionFactory");
			connection = connectionFactory.createQueueConnection();
			session = connection.createQueueSession(true, 1);
			Queue queue = (Queue) InitialContext.doLookup(DESTINATION_NAME);
			sender = session.createSender(queue);
		} catch (Exception e) {
			logger.error("could not create JMS connection", e);
		}
	}

	/**
	 * Close connection.
	 */
	@PreDestroy
	private void closeConnection() {
		try {
			if (connection != null) {
				sender.close();
				session.commit();
				session.close();
				connection.close();
			}
		} catch (JMSException e) {
			logger.error("could not close JMS connection", e);
			;
		}
	}

	/**
	 * Recieve talk changed.
	 * 
	 * @param talk
	 *            the talk
	 */
	public void recieveTalkChanged(@Observes @TalkChanged Talk talk) {
		sendMessage(talk, false);
	}

	/**
	 * Recieve talk deleted.
	 * 
	 * @param talk
	 *            the talk
	 */
	public void recieveTalkDeleted(@Observes @TalkDeleted Talk talk) {
		sendMessage(talk, true);
	}

	/**
	 * Send message.
	 * 
	 * @param talk
	 *            the talk
	 * @param deleted
	 *            the deleted
	 */
	private void sendMessage(Talk talk, boolean deleted) {
		try {
			Message message = session.createMessage();
			message.setLongProperty("talkId", talk.getId());
			message.setBooleanProperty("deleted", deleted);
			sender.send(message);
		} catch (JMSException e) {
			logger.error("could not send JMS message", e);
		}
	}

}
