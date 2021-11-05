/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.esiee.noe4j;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;
import static org.neo4j.driver.Values.parameters;

/**
 *
 * @author ldumay
 */
public class Main implements AutoCloseable {
    
    private Driver driver;
    private static String connexion_url = "bolt://localhost:7687";
    private static String connexion_user = "neo4j";
    private static String connexion_password = "esiee95300";

    public Main( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public void printGreeting( final String message )
    {
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run( "CREATE (a:Greeting) "
                                                +"SET a.message = $message "
                                                +"RETURN a.message + ', from node ' + id(a)",
                                                parameters( "message", message )
                                            );
                    return result.single().get( 0 ).asString();
                }
            } );
            System.out.println( greeting );
        }
    }

    public static void main( String... args ) throws Exception
    {
        try ( Main greeter = new Main( connexion_url, connexion_user, connexion_password ) )
        {
            greeter.printGreeting( "hello, world" );
        }
    }
    
}
