import Controller.SocialMediaController;
import Model.Message;
import Service.MessageService;
import io.javalin.Javalin;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);
        MessageService messageService = new MessageService();

        app.get("/messages/{id}", ctx ->{
            String stid = ctx.pathParam("id");
            int id = Integer.parseInt(stid);
            Message message = messageService.getMessageByID(id);
            System.out.println("Hello!");
            ctx.result("Hello, my ID is " + id + " and my message is " + message.toString());
            //System.out.println(message.toString());
            //ctx.json(message);
        });
        
    }
}
