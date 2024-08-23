package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {
    
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postNewUserHandler);
        app.post("/login", this::postUserHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/accounts/{account_id}/messages", this::getAllUserMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.patch("/messages/{message_id}", this::patchUpdateMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);

        return app;
    }

    /**
     * Handler to create a new user account
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postNewUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(ctx.body(), Account.class);
        Account addedUser = accountService.userRegistration(user);
        if(addedUser != null){
            ctx.json(mapper.writeValueAsString(addedUser));
        }else{
            ctx.status(400);
        }
    }

    /**
     * Handler to login to a user
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account user = mapper.readValue(ctx.body(), Account.class);
        Account loggedUser = accountService.userLogin(user);
        if(loggedUser != null){
            ctx.json(mapper.writeValueAsString(loggedUser));
        }else{
            ctx.status(401);
        }
    }

    /**
     * Handler to create a new message.
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.createMessage(message);
        if(newMessage != null){
            ctx.json(mapper.writeValueAsString(newMessage));
        }else{
            ctx.status(400);
        }
    }

    /**
     * Handler to retrieve all messages
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /**
     * Handler to a specific message by ID
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageHandler(Context ctx) {
        String strid = ctx.pathParam("message_id");
        int id = Integer.parseInt(strid);
        Message message = messageService.getMessageByID(id);
        if(message != null){
            ctx.json(message);
        }
        ctx.status(200);
    }

    /**
     * Handler to retrieve all messages by a specific user
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllUserMessagesHandler(Context ctx) {
        String strid = ctx.pathParam("account_id");
        int id = Integer.parseInt(strid);
        List<Message> messages = messageService.getAllUserMessages(id);
        ctx.json(messages);
    }
    
    /**
     * Handler to update a message with a specific ID
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void patchUpdateMessageHandler(Context ctx) throws JsonProcessingException {
        String strid = ctx.pathParam("message_id");
        int id = Integer.parseInt(strid);
        ObjectMapper mapper = new ObjectMapper();
        Message newMessage = mapper.readValue(ctx.body(), Message.class);
        Message message = messageService.updateMessage(id, newMessage);
        if(message != null){
            ctx.json(message);
        }else{
            ctx.status(400);
        }
    }

    /**
     * Handler to delete a message with a specific ID
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageHandler(Context ctx) {
        String stid = ctx.pathParam("message_id");
        int id = Integer.parseInt(stid);
        Message message = messageService.deleteMessageByID(id);
        if(message != null){
            ctx.json(message);
        }
        ctx.status(200);
    }
}