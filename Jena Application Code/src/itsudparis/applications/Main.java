package itsudparis.application;

import com.hp.hpl.jena.rdf.model.Model;
import itsudparis.tools.JenaEngine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static String inputDataOntology = "data/DishesAndIngredients.owl";

    public static String inputRule = "data/rules/rules.txt";

    public static HashMap<String, String> dishMap = new HashMap<String, String>();
    public static String dishQuery = "data/queries/";

    /**
     * @param args rhe command line arguments
     */

    public static void main(String[] args) throws IOException {
        FeedDishMap();

        Model model = JenaEngine.readModel(inputDataOntology);
        Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, inputRule);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("De quel type de plat avez-vous envie ? (calorique, pas cher, cher, fruité, sucré, salé, épicé)");
            // Reading data using readLine
            String name = reader.readLine().replace('é', 'e');

            if (name.equals("stop"))
                break;

            if (!dishMap.containsKey(name.toUpperCase())) {
                System.out.println("Ce type de plat est inconnu. Merci de rentrer un type connu.");
                continue;
            }

            String query = dishQuery + dishMap.get(name.toUpperCase()) + ".txt";
            String results = JenaEngine.executeQueryFile(inferedModel, query);
            if (results.contains(("ns:"))) {
                System.out.println("Plat " + name + " : ");
                System.out.println(results);
            }
            else {
                System.out.println(("Aucun résultat."));
            }
        }
    }

    private static void FeedDishMap() {
        dishMap.put("CALORIQUE", "calorificDish");
        dishMap.put("PAS CHER", "cheap");
        dishMap.put("CHER", "expensive");
        dishMap.put("FRUITE", "fruityDish");
        dishMap.put("SUCRE", "sweetDish");
        dishMap.put("SALE", "saltyDish");
        dishMap.put("EPICE", "spicedDish");
    }
}
