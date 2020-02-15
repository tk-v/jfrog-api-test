import java.util.*;
import org.json.*;

import org.jfrog.artifactory.client.*;
import org.jfrog.artifactory.client.impl.*;
import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.impl.ArtifactoryRequestImpl;
import org.jfrog.artifactory.client.model.RepoPath;

public class App {
    public static void main(String[] args) {
        String url = "http://jfrog.local/artifactory";
        String user = "";
        String password = "";
        
        Artifactory artifactory = ArtifactoryClientBuilder.create()
                                    .setUrl(url)
                                    .setUsername(user)
                                    .setPassword(password)
                                    .build();
        
        
        int firstCount = 0;
        String firstUri = "";
        int secondCount = 0;
        String secondUri = "";
        
        try {
            List<RepoPath> searchItems = artifactory.searches().repositories("jcenter-cache").artifactsByName("*.jar").doSearch();
            for (RepoPath item: searchItems) {
                ArtifactoryRequest info = new ArtifactoryRequestImpl()
                    .method(ArtifactoryRequest.Method.GET)
                    .apiUrl("api/storage/jcenter-cache/" + item.getItemPath() + "?stats")
                    .responseType(ArtifactoryRequest.ContentType.TEXT);
                  ArtifactoryResponse response = artifactory.restCall(info);
                JSONObject obj = new JSONObject(response.getRawBody()); 
                int count = obj.getInt("downloadCount");
                if( count > firstCount ) {
                    firstCount = count;
                    firstUri = obj.getString("uri");
                }
                else if( count > secondCount ) {
                    secondCount = count;
                    secondUri = obj.getString("uri");
                }
            }
            System.out.println("Most popular: " + firstUri + " " + firstCount);
            System.out.println("Second popular: " + secondUri + " " + secondCount);
        } 
        catch(Exception e) {
            System.out.println("Failed");
        }
    }
}
