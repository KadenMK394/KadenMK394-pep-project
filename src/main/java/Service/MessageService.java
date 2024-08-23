package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO = new AccountDAO();
    
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message){
        int length = message.message_text.length();
        if(length > 0 && length <= 255 && accountDAO.getUserByID(message.posted_by) != null){
            return messageDAO.createMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public List<Message> getAllUserMessages(int user_id){
        return messageDAO.getAllUserMessages(user_id);
    }

    public Message getMessageByID(int message_id){
        return messageDAO.getMessageByID(message_id);
    }

    public Message updateMessage(int message_id, Message newMessage){
        String message = newMessage.getMessage_text();
        if(messageDAO.getMessageByID(message_id) != null && message.length() > 0 && message.length() <= 255) {
            return messageDAO.updateMessageByID(message_id, newMessage);
        }
        return null;
    }

    public Message deleteMessageByID(int message_id){
        return messageDAO.deleteMessageByID(message_id);
    }
}
