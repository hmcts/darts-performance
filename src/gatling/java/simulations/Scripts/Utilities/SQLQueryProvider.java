package simulations.Scripts.Utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SQLQueryProvider {

    private static final String SQL_DIRECTORY = "src/main/resources/sql/";

    // Method to read the SQL file
    public static String loadSQL(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(SQL_DIRECTORY + fileName)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load SQL file: " + fileName, e);
        }
    }
    
    public static String getHearingQuery() {
        return loadSQL("hearing_query.sql");
    }

    public static String getTransformedMediaToDeleteQuery() {
        return loadSQL("transformed_media_delete_query.sql");
    }

    public static String getCaseDetailsForRetentionQuery() {
        return loadSQL("case_details_for_retention_query.sql");
    }

    public static String getTransformedMediaIdForDownloadQuery() {
        return loadSQL("transformed_media_download_query.sql");
    }

    public static String getTransformedMediaIdForPlayBackQuery() {
        return loadSQL("transformed_media_playback_query.sql");
    }

    public static String getLanguageShopUsersQuery() {
        return loadSQL("language_shop_users_query.sql");
    }

    public static String getCourtManagerUsersQuery() {
        return loadSQL("court_manager_users_query.sql");
    }

    public static String getCourtClerkUsersQuery() {
        return loadSQL("court_clerk_users_query.sql");
    }

    public static String getTranscriberUsersQuery() {
        return loadSQL("transcriber_users_query.sql");
    }

    public static String getJudgeUsersQuery() {
        return loadSQL("judge_users_query.sql");
    }

}
