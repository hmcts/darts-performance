package simulations.Scripts.Scenario.DartsApi;

import io.gatling.javaapi.core.*;
import lombok.extern.slf4j.Slf4j;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static io.gatling.javaapi.core.CoreDsl.*;

@Slf4j
public final class InseartAudioIntoHearingScenario {

    private InseartAudioIntoHearingScenario() {}

    public static ChainBuilder InseartAudioIntoHearing() {

        // Merged SQL query returning 100000 aligned rows
        String mergedSql = "SELECT h.hea_id, m.med_id " +
                "FROM (SELECT hea_id, ROW_NUMBER() OVER () AS rn FROM darts.hearing " +
                "WHERE NOT EXISTS (SELECT 1 FROM darts.hearing_media_ae hm WHERE hm.hea_id = darts.hearing.hea_id) LIMIT 100000) h " +
                "JOIN (SELECT med_id, ROW_NUMBER() OVER () AS rn FROM darts.media ORDER BY med_id DESC LIMIT 100000) m " +
                "ON h.rn = m.rn";

        // Load data once and iterate through each row
        FeederBuilder<Object> feeder = Feeders.jdbcFeeder(mergedSql).queue(); // important: use .queue()

        return feed(feeder)
        .exec(session -> {
            int heaId = session.getInt("hea_id");
            int medId = session.getInt("med_id");
    
            try (Connection conn = DriverManager.getConnection(
                    AppConfig.DB_URL, AppConfig.DB_USERNAME, AppConfig.DB_PASSWORD)) {
    
                String insertSql = "INSERT INTO darts.hearing_media_ae (hea_id, med_id) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                    stmt.setInt(1, heaId);
                    stmt.setInt(2, medId);
                    int result = stmt.executeUpdate();
    
                    // ✅ Output the inserted values
                    if (result > 0) {
                        log.info("Inserted hea_id: " + heaId + ", med_id: " + medId);
                    } else {
                        log.info("Insert skipped or failed for hea_id: " + heaId + ", med_id: " + medId);
                    }
                }
    
            } catch (Exception e) {
                System.err.println("Insert failed for hea_id: " + heaId + ", med_id: " + medId + ". Error: " + e.getMessage());
            }
    
            return session;
        });
    
    }
}
