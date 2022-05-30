package com.datadriventest.tasks;

import com.google.gson.Gson;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.HashMap;
import java.util.Map;

public class CreateNew implements Task {
    private static final String ENDPOINT = "/api/users?page=2";
    private final String name;
    private final String job;

    public CreateNew(String name, String job) {
        this.name = name;
        this.job = job;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Map<String, String> body = new HashMap<>();
        body.put("name", name);
        body.put("job", job);
        Gson gsonObj = new Gson();
        String requestBody = gsonObj.toJson(body);
        actor.attemptsTo(Post.to(ENDPOINT)
                .with(
                        request ->
                                request.header("Content-Type", "application/json")
                                        .body(requestBody)
                ));
    }

    public static CreateNew user(String name, String job){
        return Tasks.instrumented(CreateNew.class, name, job);
    }
}
