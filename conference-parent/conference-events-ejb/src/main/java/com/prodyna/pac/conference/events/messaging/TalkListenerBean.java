package com.prodyna.pac.conference.events.messaging;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
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
	
	@Resource(mappedName="java:/ConnectionFactory")
	QueueConnectionFactory connectionFactory;
	
	@Resource(mappedName="java:/" + DESTINATION_NAME)
	Queue queue;
	
	private QueueConnection connection;

	private QueueSession session;

	private QueueSender sender;

	/**
	 * Open connection.
	 */
	@PostConstruct
	private void openConnection() {
		try {
			connection = connectionFactory.createQueueConnection();
			session = connection.createQueueSession(true, 1);
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
	public void recieveTalkChanged(@Observes Talk talk) {
		if (talk != null) {
			sendMessage(talk, talk.getId() != null);
		} else {
			logger.warn("talkChangedEvent without content recieved");
		}
	}

	/**
	 * Send message.
	 * 
	 * @param talk
	 *            the talk
	 * @param updated
	 *            the deleted
	 */
	private void sendMessage(Talk talk, boolean updated) {
		try {
			Message message = session.createMessage();
			message.setLongProperty("talkId", talk.getId());
			message.setBooleanProperty("deleted", !updated);
			sender.send(message);
		} catch (JMSException e) {
			logger.error("could not send JMS message", e);
		}
	}

}
