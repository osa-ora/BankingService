package osa.ora.route;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;

public class SwiftRouteBuilder extends RouteBuilder {
    private String message;
    public SwiftRouteBuilder(String message,CamelContext ctx){
        super();
        this.message=message;   
    }
    @Override
    public void configure() throws Exception {
        System.out.println("will send a message:"+message);
    	from("active:test").setBody(simple("Message")).to(
                "jms:queue:activemq/queue/swift");
        System.out.println("Transfer Message sent");
    }

}