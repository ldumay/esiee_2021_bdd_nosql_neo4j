/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.esiee;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

/**
 *
 * @author ldumay
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
			Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("users", "esiee95300"));
			//Driver driver = GraphDatabase.driver("neo4j://localhost:7687", AuthTokens.basic("users", "esiee95300"));
			Session session = driver.session();
			session.run("CREATE (p:Person {name:\"Albert\"})"
					+ "-[:owns]-> (tesla:Car {make: 'tesla', model: 'modelX'})" + "RETURN baeldung, tesla");
			session.close();
			driver.close();
		} catch (Exception e) {
			System.err.println("uh oh - " + e.getMessage());
		}
    }
    
}
