package com.prodyna.pac.conference.events.messaging;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = TalkListenerBean.DESTINATION_NAME) }, mappedName = TalkListenerBean.DESTINATION_NAME)
public class TalkChangedMessageBean implements MessageListener {

	@Inject
	private Logger logger;

	@Override
	public void onMessage(Message message) {
		logger.info("Talk has changed: [" + message + "]");
	}
}