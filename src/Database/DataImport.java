package Database;

import utils.Helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author glukka, Arkadiusz Nowak
 *
 * Each of the XML data file's sheets need to be converted to Tabbed Sepparated Values files (.tsv)
 * and their file names:
 *
 * - CSV_DATABASE for database sheet (no 1)
 * - CSV_CONVERSATIONS for conversations sheet (no 2)
 * - CSV_CONTEXTS for contexts sheet (no 3)
 *
 * Database needs to be created with the attached database.sql file containing sqlite database schema
 * and the name of the database file stored in DB_FILE
 *
 * Messy data source (xml file), hence a messy code (also, created at the very beginning, even before The Big Bang)
 * Modify at your own risk!
 */

public class DataImport {
    public static final String DB_FILE = "langproject.db";
    public static final String CSV_DATABASE = "sheet1_database.tsv";
    public static final String CSV_CONVERSATIONS = "sheet2_conversations.tsv";
    public static final String CSV_CONTEXTS = "sheet3_contexts.tsv";

    public static void main (String[] arg) {
        // Import CSV_CONTEXTS
        System.out.println("Importing contexts from: " + CSV_CONTEXTS);
        DataImport sheet3 = new DataImport(DB_FILE, CSV_CONTEXTS);
        sheet3.importContexts();

        // Import CSV_DATABASE
        System.out.println("Importing context, subcontext from: " + CSV_DATABASE);
        DataImport sheet1 = new DataImport(DB_FILE, CSV_DATABASE);
        sheet1.importDatabase();

        // Import CSV_CONVERSATIONS
        System.out.println("Importing conversations from: " + CSV_CONVERSATIONS);
        DataImport sheet2 = new DataImport(DB_FILE, CSV_CONVERSATIONS);
        sheet2.importConversations();
    }

    ArrayList list = new ArrayList();
    Connection con;
    Integer idLanguage = 1; // Default language id (Spanish)

    DataImport(String dbName, String csvFileName) {
        con = Database.getConnection(dbName);
        try {
            FileInputStream fstream = new FileInputStream(csvFileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            list.clear();
            while ((strLine = br.readLine()) != null) {
                list.add(strLine);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // @TODO user prep statements instead
    // Make sure the value of a string is trimmed and special characters are escaped
    private String sqlString(String value) {
        return "'" + value.trim().replace("'","''") + "'";
    }

    // Imports Database sheet
    // @TODO Works only for Language: Spanish
    // @TODO trim all strings
    void importDatabase() {
        list.remove(0); // remove column names
        Iterator itr;
        Statement stmt = null;
        ResultSet res = null;
        Integer skippedContext = 0;
        Integer skippedSubContext = 0;
        Integer newContext = 0;
        Integer newSubContext = 0;
        Integer total = 0;
        Integer contextId = 0;
        Integer subContextId = 0;

        Map<Integer, String> contextIds = new HashMap<>(); // holds contextNames with corresponding Ids
        Map<Integer, String> subContextIds = new HashMap<>(); // holds subContextNames with corresponding Ids

        // @TODO move into a separate method
        // Get all the contexts IDs
        try {
            stmt = con.createStatement();
            res = stmt.executeQuery("SELECT idContext, contextName FROM context");
            // Store results
            while (res.next()) {
                contextIds.put(res.getInt("idContext"), res.getString("contextName"));
                //System.out.println("row data :" + id + " " + name);
            }
            res.close();
            stmt.close();
        }  catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println("Context IDs MAP: " + contextIds);

        // Read csv data
        // Insert into subcontext
        // Insert into conversation
        for (itr = list.iterator(); itr.hasNext();) {
            ++total;

            String str = itr.next().toString();
            String[] row = str.split("\\t");

            System.out.println("-".repeat(80)); // Seperator

            System.out.println("Language level: " + row[0].trim() + " | Context: " + row[1].trim() + " | Subcontext: " +
                    row[2].trim() + " | Grammar/Structure: " + row[3].trim() + " | Vocabulary: " + row[4].trim() + " | Garbage: " + row[5].trim());

            contextId = Helper.getKeyIdByValue(contextIds, row[1].trim()); // Get id for the stored context; null if doesnt exist

            // @TODO unique idcontext, subcontextname
            // Check if subcontext exists, else create new record
            if (!subContextIds.containsValue(row[2].trim())) {
                System.out.println("New subcontext " + row[2].trim());
                System.out.println("contextId is " + contextId);
                // If it is a new context, add to database
                if (contextId == null) {
                    try {
                        stmt = con.createStatement();
                        stmt.executeUpdate("INSERT INTO context (contextName) VALUES ('" + row[1].trim().replace("'","''") + "')");
                        res = stmt.getGeneratedKeys();
                        contextId = res.getInt("last_insert_rowid()");
                        contextIds.put(contextId, row[1].trim());
                        System.out.println("Inserted new context " + row[1].trim() + " with id " + contextId);
                        res.close();
                        stmt.close();
                        ++newContext;
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                }

                // Insert new subcontext into database
                try {
                    stmt = con.createStatement();
                    stmt.executeUpdate("INSERT INTO subcontext (idContext, subcontextName) VALUES (" + contextId + ",'" + row[2].trim().replace("'","''") + "')");
                    res = stmt.getGeneratedKeys();
                    Integer id = res.getInt("last_insert_rowid()");
                    subContextIds.put(id, row[2].trim());
                    System.out.println("Inserted new subcontext " + row[2].trim() + " with id " + id);
                    res.close();
                    stmt.close();
                    ++newSubContext;
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }

            // --- Create new conversation
            // Get subcontextid matching contextid and subcontextname
            try {
                stmt = con.createStatement();
                res = stmt.executeQuery("SELECT idSubContext FROM subcontext WHERE idContext = " + contextId + "" +
                        " AND subContextName = '" + row[2].trim().replace("'","''") + "' LIMIT 1");
                if (res.next()) {
                    subContextId = res.getInt("idSubContext");
                    //System.out.println("Subcontext ID found:" + subContextId);
                } else {
                    //System.out.println("Subcontext ID not found!");
                    subContextId = null;
                    ++skippedSubContext;
                }
                res.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("subContextId is " + subContextId);
            if (subContextId != null) {
                try {
                    stmt = con.createStatement();
                    stmt.executeUpdate("INSERT INTO conversation " +
                            "(idSubContext, idLanguage, languageLevel, grammarStructure, vocabulary)" +
                            " VALUES (" + subContextId + ", " + idLanguage + ", '" + row[0].trim() + "', '"
                            + row[3].trim().replace("'","''") + "', '" + row[4].trim().replace("'","''") + "')");
                    res = stmt.getGeneratedKeys();
                    Integer id = res.getInt("last_insert_rowid()");
                    System.out.println("Inserted new conversation with context " + row[1].trim() + " and "
                            + row[2].trim() + " and id " + id);
                    res.close();
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                skippedSubContext++;
                System.out.println("skipping");
            }
        }

        // Close db connection
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // debug
        System.out.println("Total rows" + total + ", new contexts " + newContext + ", new subcontexts " + newSubContext + ", skipped contexts " + skippedContext + " and subcontexts " + skippedSubContext);
    }

    // Imports Contexts sheet
    void importContexts() {
        Iterator itr;
        Statement stmt = null;
        Statement stmt2 = null;
        ResultSet res = null;
        Integer contextId = 0;
        try {
            int total = 0;
            int added = 0;
            for (itr = list.iterator(); itr.hasNext(); ) {
                String str = itr.next().toString();

                total++; // context counter
                stmt = con.createStatement();
                res = stmt.executeQuery("SELECT idContext, contextName FROM context WHERE contextName = '" + str + "' LIMIT 1");
                // Store results
                if (!res.next()) { // Not found, new context
                    // Add new context with unique contextName
                    String sqlQuery = "INSERT INTO context (contextName) VALUES \n"
                            + "('" + str.replace("'","''") + "')";
                    added++;
                    con.setAutoCommit(false);
                    stmt2 = con.createStatement();
                    stmt2.executeUpdate(sqlQuery);
                    stmt2.close();
                    con.commit();
                }
                res.close();
                stmt.close();
            }
            System.out.println("Total " + total + ", added " + added + " and failed (duplicates) " + (total-added));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Close db connection
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Imports conversations sheet
    // @TODO add missing subcontexts
    public void importConversations() {
        list.remove(0); // remove column names
        Iterator itr;
        Statement stmt = null;
        ResultSet res = null;
        Integer skippedContext = 0;
        Integer skippedSubContext = 0;
        Integer skippedSpeechInserts = 0;
        Integer skipppedConversations = 0;

        Map<Integer, String> contextIds = new HashMap<>(); // holds contextNames with corresponding Ids
        Map<Integer, String> subContextIds = new HashMap<>(); // holds subContextNames with corresponding Ids

        // @TODO move into a separate method
        // Get all the contexts IDs
        try {
            stmt = con.createStatement();
            res = stmt.executeQuery("SELECT idContext, contextName FROM context");
            // Store results
            while (res.next()) {
                contextIds.put(res.getInt("idContext"), res.getString("contextName"));
                //System.out.println("row data :" + id + " " + name);
            }
            res.close();
            stmt.close();
        }  catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println("Context IDs MAP: " + contextIds);

        // Read tsv data
        Integer convId = 0;
        Integer subContextId = 0;
        Integer conversationId = 0;
        Integer contextId = 0;

        for (itr = list.iterator(); itr.hasNext();) {
            String str = itr.next().toString();
            if (str.equals("\n")) {
                System.out.println("Skipping row, new line");
                continue;
            }
            String[] row = str.split("\\t");

            if (row.length == 0) {
                System.out.println("Skipping row, empty");
                continue;
            }

            System.out.println("-".repeat(80)); // Separator
            // @TODO move to a separate method
            // New conversation, get conversation header data
            if (!row[0].trim().isEmpty() && !row[1].trim().isEmpty() && !row[2].trim().isEmpty()) {
                ++convId;
                System.out.println("+".repeat(80)); // Separator
                contextId = Helper.getKeyIdByValue(contextIds, row[1].trim()); // Get id for the stored context
                System.out.println("New conversation #" + convId + " (contextId " + contextId + "):");
                // Check subcontext id
                try {
                    stmt = con.createStatement();
                    res = stmt.executeQuery("SELECT idSubContext FROM subcontext WHERE idContext = " + contextId +
                            " AND subContextName = " + sqlString(row[2]) + " LIMIT 1");
                    // Store results
                    if (res.next()) {
                        subContextId = res.getInt("idSubContext");
                        System.out.println("Subcontext ID found:" + subContextId);
                    } else {
                        System.out.println("Subcontext ID not found!");
                        subContextId = 0;
                        ++skippedSubContext;
                    }
                    res.close();
                    stmt.close();
                }  catch (SQLException e) {
                    System.out.println(e);
                }
                System.out.println("SubContext ID: " + subContextId);

                // Get conversation ID
                if (subContextId > 0) {
                    try {
                        stmt = con.createStatement();
                        res = stmt.executeQuery("SELECT idConversation FROM conversation WHERE idSubContext = " + subContextId +
                                " AND idLanguage = " + idLanguage + " AND languageLevel = " + sqlString(row[0]) + " LIMIT 1");
                        //int rows = res.getFetchSize();
                        //System.out.println("Found rows: " + rows);
                        // Store results
                        if (res.next()) {
                            conversationId = res.getInt("idConversation");
                            System.out.println("Conversation ID found:" + conversationId);
                        } else {
                            System.out.println("Conversation ID not found!");
                            conversationId = 0;
                            ++skipppedConversations;
                        }
                        res.close();
                        stmt.close();
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
                    System.out.println("Conversation ID: " + conversationId);
                }
            }
            System.out.println("Row el count " + row.length + ", conversationId " + conversationId + ", contextId " + contextId + ", subcontextId " + subContextId);

            // @TODO check if each attribute exist, some are empty
            System.out.println("Language level: " + row[0].trim() +
                    " | Context: " + row[1].trim() + " | Subcontext: " + row[2].trim() +
                    " | Person: " +  (3 < row.length ? row[3].trim() : "-") + " | Text: " + (4 < row.length ? row[4].trim() : "-") + " | Translations: " +
                    (5 < row.length ? row[5].trim() : "-") + "");// | Garbage: " + row[6].trim());

            // If we have all necessary details, insert into db
            if (conversationId != null && conversationId > 0) {
                System.out.println("INSERT INTO speech: conversationId " + conversationId);
                // prep data
                String person = (3 < row.length ? row[3].trim().replace("'","''") : "");
                String message = (4 < row.length ? row[4].trim().replace("'","''") : "");
                String translation = (5 < row.length ? row[5].trim().replace("'","''") : "");
                try {
                    stmt = con.createStatement();
                    // execute query
                    //idSubContext, idContext
                    stmt.executeUpdate("INSERT INTO speech (idConversation, person, message, translation) " +
                            "VALUES (" + conversationId + ",'" + person + "','" + message + "','" + translation + "')");
                    //res = stmt.getGeneratedKeys();
                    //Integer id = res.getInt("last_insert_rowid()");
                    /*System.out.println("Inserted new conversation with context " + row[1].trim() + " and "
                            + row[2].trim() + " and id " + id);*/
                    //res.close();
                    System.out.println("Insert successful");
                    stmt.close();
                } catch (SQLException e) {
                    ++skippedSpeechInserts;
                    System.out.println("SQL ERROR for conversationId " + conversationId + ", " + person + ", " + message + "\n" + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                ++skippedSpeechInserts;
                System.out.println("Missing conversation ID. Skipping insert into speech");
            }
        }

        // debug
        System.out.println("Skipped speech inserts "+ skippedSpeechInserts+", Skipped conversations " + skipppedConversations + " and subcontexts " + skippedSubContext);

        // Close db connection
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}