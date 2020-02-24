package fr.esilv.s8.mobileappdev.github_api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface GitHubClient {

  @GET("/users/{user}/repos") // we want repo for users for a specific user and return a list of repos
  Call<List<GitHubRepo>> reposForUser(
          @Path("user") String user// Path in order to change the {user} we get // Call to do it async
  );

}
